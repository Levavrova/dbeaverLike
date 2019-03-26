package com.dbeaverLike.dbstructure.config;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class TransactionManagerFactory {

     public  JpaTransactionManager createTransactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {

         JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    } 
}