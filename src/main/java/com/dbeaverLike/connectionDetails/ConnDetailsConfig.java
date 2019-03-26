package com.dbeaverLike.connectionDetails;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        repositoryBaseClass = ConnectionDetailsRepository.class,
        entityManagerFactoryRef = "connDetailsEntityManagerFactory",
        transactionManagerRef = "connDetailsTransactionManager"
)
public class ConnDetailsConfig {

    @Bean("connDetailsDataSourceProperties")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties connDetailsDataSourceProperties() {
        return new DataSourceProperties();
    }
        
    @Bean("connDetailsDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.configuration")
    public HikariDataSource connDetailsDataSource(
            @Qualifier("connDetailsDataSourceProperties") DataSourceProperties connDetailsDataSourceProperties)
    {
        return connDetailsDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean("connDetailsEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean connDetailsEntityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("connDetailsDataSource")HikariDataSource connDetailsDataSource) {
        
        LocalContainerEntityManagerFactoryBean entityManagerFactory =  builder.dataSource(connDetailsDataSource)               
                .packages(new String[] {"com.dbeaverLike.connectionDetails"})
                .persistenceUnit("connectionDetails")
                .build();
        
        return entityManagerFactory;
    }

    @Bean("connDetailsTransactionManager")
    @Primary
    public PlatformTransactionManager connDetailsTransactionManager(
            @Qualifier("connDetailsEntityManagerFactory") LocalContainerEntityManagerFactoryBean connDetailsEntityManagerFactory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(connDetailsEntityManagerFactory.getObject());

        return transactionManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslatorPostProcessor(){
   return new PersistenceExceptionTranslationPostProcessor();
}
}