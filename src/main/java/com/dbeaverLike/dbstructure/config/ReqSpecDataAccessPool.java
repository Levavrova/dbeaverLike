package com.dbeaverLike.dbstructure.config;

import com.dbeaverLike.exception.ConnectionPoolOverloadException;
import com.dbeaverLike.connectionDetails.ConnectionDetails;
import com.dbeaverLike.connectionDetails.ConnectionDetailsService;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ReqSpecDataAccessPool {

    @Value("${appl.data-access.configuration.maximum-pool-size}")
    private int connPoolLimit;

    private final Map<Long, ReqSpecDataAccessConfigAndUsers> dataAccessConfigAndUsersMap = new HashMap<>();
    private List<ReqSpecDataAccessConfigAndUsers> outdatedDataAccessConfigAndUsersList = new LinkedList<>();

    private final ReentrantLock lock = new ReentrantLock(true);
    
    private final ConnectionDetailsService connectionDetailsService;
    private final DataSourceFactory dataSourceFactory;
    private final EntityManagerFactoryBeanFactory entityManagerFactoryBeanFactory;
    private final TransactionManagerFactory transactionManagerFactory;
    
    ReqSpecDataAccessPool(ConnectionDetailsService connectionDetailsService,
            DataSourceFactory dataSourceFactory,
            EntityManagerFactoryBeanFactory entityManagerFactoryBeanFactory,
            TransactionManagerFactory transactionManagerFactory) {
        this.connectionDetailsService = connectionDetailsService;
        this.dataSourceFactory = dataSourceFactory;
        this.entityManagerFactoryBeanFactory = entityManagerFactoryBeanFactory;
        this.transactionManagerFactory = transactionManagerFactory;
    }

    public ReqSpecDataAccessConfigAndUsers provideReqSpecDataAccessConfig(ReqSpecDataAccessUser reqSpecDataAccessUser) {

        Long connDetailsId = reqSpecDataAccessUser.getConnDetailsId();
        ConnectionDetails connectionDetails = connectionDetailsService.findById(connDetailsId);
        lock.lock();
        try {
            if (dataAccessConfigAndUsersMap.containsKey(connDetailsId)) {
                    return updateUserListAndProvideDataAccessConfigLink(
                            connDetailsId, reqSpecDataAccessUser);
            } else {
                return provideNewDataAccessIfUnderlimit(
                        connDetailsId, reqSpecDataAccessUser, connectionDetails);
            }
        } finally {
            lock.unlock();
        }
    }

    public void removeChangedConnectionFromDataAccessPool(Long connDetailsId){
        lock.lock();
        try {
            if (dataAccessConfigAndUsersMap.containsKey(connDetailsId)) {
                ReqSpecDataAccessConfigAndUsers dataAccessConfigAndUsers = dataAccessConfigAndUsersMap.get(connDetailsId);
                if (dataAccessConfigAndUsers.isNotUsersListEmpty()){
                    outdatedDataAccessConfigAndUsersList.add(dataAccessConfigAndUsers);
                }
                dataAccessConfigAndUsersMap.remove(connDetailsId);
            }
        } finally {
            lock.unlock();
        }
    }
    
    private ReqSpecDataAccessConfigAndUsers updateUserListAndProvideDataAccessConfigLink(
            Long connDetailsId, ReqSpecDataAccessUser reqSpecDataAccessUser) {
        ReqSpecDataAccessConfigAndUsers dataAccessConfigAndUsers = dataAccessConfigAndUsersMap.get(connDetailsId);
        dataAccessConfigAndUsers.addUser(reqSpecDataAccessUser);
        return dataAccessConfigAndUsers;
    }

    private ReqSpecDataAccessConfigAndUsers provideNewDataAccessIfUnderlimit(
            Long connDetailsId,ReqSpecDataAccessUser reqSpecDataAccessUser,
            ConnectionDetails connectionDetails){
        
        if ((dataAccessConfigAndUsersMap.size() + outdatedDataAccessConfigAndUsersList.size()) >= connPoolLimit) {
            removeIddleDataAccessConfigs();

            if ((dataAccessConfigAndUsersMap.size() + outdatedDataAccessConfigAndUsersList.size()) >= connPoolLimit) {
                throw new ConnectionPoolOverloadException("The data access pool of the database connections is overloaded.");
            }
        }
        ReqSpecDataAccessConfigAndUsers newDataAccessConfig = createReqSpecDataAccessConfig(connectionDetails);
        newDataAccessConfig.addUser(reqSpecDataAccessUser);
        dataAccessConfigAndUsersMap.put(connDetailsId, newDataAccessConfig);
        
        return newDataAccessConfig;
    }
    
    private void removeIddleDataAccessConfigs() {
        outdatedDataAccessConfigAndUsersList.stream()
                .filter(p -> p.isUsersListEmpty())
                .forEach(p -> p.getEntityManagerFactoryBean().destroy());
        outdatedDataAccessConfigAndUsersList
                .removeIf(p -> p.isUsersListEmpty());
        dataAccessConfigAndUsersMap.entrySet().stream()
                .filter(p -> p.getValue().isUsersListEmpty())
                .forEach(p -> p.getValue().getEntityManagerFactoryBean().destroy());
        dataAccessConfigAndUsersMap.entrySet()
                .removeIf(p -> p.getValue().isUsersListEmpty());
    }

    private ReqSpecDataAccessConfigAndUsers createReqSpecDataAccessConfig(ConnectionDetails connectionDetails) {
        
    HikariDataSource datasource = dataSourceFactory.createDataSource(connectionDetails);
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = entityManagerFactoryBeanFactory
                .createReqEntityManagerFactory(datasource);
    JpaTransactionManager transactionManager = transactionManagerFactory.createTransactionManager(entityManagerFactoryBean);

    return new ReqSpecDataAccessConfigAndUsers(connectionDetails.getDatabaseName(),
            entityManagerFactoryBean,transactionManager);
    }
}