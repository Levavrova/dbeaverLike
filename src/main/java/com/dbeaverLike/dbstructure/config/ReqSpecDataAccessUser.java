package com.dbeaverLike.dbstructure.config;

import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class ReqSpecDataAccessUser {

    @Setter
    @Getter
    private Long connDetailsId;
    private ReqSpecDataAccessConfigAndUsers dataAccessConfigAndUsers;
    
    private final ReqSpecDataAccessPool dataAccessPool;
      
    ReqSpecDataAccessUser(ReqSpecDataAccessPool dataAccessPool) {
        this.dataAccessPool = dataAccessPool;
    }
    
    public JpaTransactionManager provideReqSpecTransactionManager(){
        dataAccessConfigAndUsers = dataAccessPool.provideReqSpecDataAccessConfig(this);
        return dataAccessConfigAndUsers.getTransactionManager();
    }
    public EntityManager provideReqSpecEntityManager(){
        return dataAccessConfigAndUsers.getEntityManagerFactoryBean()
                .getObject().createEntityManager();
    }
    public String getDatabaseName(){
        return dataAccessConfigAndUsers.getDatabaseName();
    }
    
    public void releaseReqSpecDataAccess(){
        if (dataAccessConfigAndUsers != null)
            dataAccessConfigAndUsers.removeUser(this);
    }
}