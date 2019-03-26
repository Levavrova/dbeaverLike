package com.dbeaverLike.dbstructure.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

@Component
public class EntityManagerFactoryBeanFactory {
 
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateDdlAuto;
    @Value("${spring.jpa.hibernate.dialect}")
    private String hibernateDialect;
    
    private final EntityManagerFactoryBuilder entityManagerFactoryBuilder;
    
    EntityManagerFactoryBeanFactory(EntityManagerFactoryBuilder entityManagerFactoryBuilder){
        this.entityManagerFactoryBuilder = entityManagerFactoryBuilder;
    }
    
    public LocalContainerEntityManagerFactoryBean createReqEntityManagerFactory(HikariDataSource dataSource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages(new String[] {"com.dbeaverLike.dbstructure.domain"})
                .persistenceUnit("dbStructure")
                .build();
    
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.ddl-auto", hibernateDdlAuto);
        properties.put("hibernate.dialect", hibernateDialect);
        entityManagerFactoryBean.setJpaPropertyMap(properties);
        entityManagerFactoryBean.afterPropertiesSet();
        
        return entityManagerFactoryBean;
    }
}