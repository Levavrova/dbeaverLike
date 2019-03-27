package com.dbeaverLike.dbBrowser.config;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Getter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Getter
public class ReqSpecDataAccessConfigAndUsers {

    private final String databaseName;
    private final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
    private final JpaTransactionManager transactionManager;
    private final List<ReqSpecDataAccessUser> users = new LinkedList<>();
    
    private final ReentrantLock UserListLock = new ReentrantLock(true);
    
    public ReqSpecDataAccessConfigAndUsers(String databaseName,
            LocalContainerEntityManagerFactoryBean entityManagerFactoryBean,
            JpaTransactionManager transactionManager){
        this.databaseName = databaseName;
        this.entityManagerFactoryBean = entityManagerFactoryBean;
        this.transactionManager = transactionManager;
    }
    
    public void addUser(ReqSpecDataAccessUser reqSpecDataAccessUser){
        UserListLock.lock();
        try{
            users.add(reqSpecDataAccessUser);
        } finally{
            UserListLock.unlock();
        }    
    }
    public void removeUser(ReqSpecDataAccessUser reqSpecDataAccessUser){
        UserListLock.lock();
        try{
            users.remove(reqSpecDataAccessUser);
        } finally{
            UserListLock.unlock();
        }
    }
    public boolean isUsersListEmpty(){
        boolean result;
        UserListLock.lock();
        try{
            result = users.isEmpty();
        } finally{
            UserListLock.unlock();
        }
        return result;
    }
    public boolean isNotUsersListEmpty(){
        boolean result;
        UserListLock.lock();
        try{
            result = !users.isEmpty();
        } finally{
            UserListLock.unlock();
        }
        return result;
    }
}