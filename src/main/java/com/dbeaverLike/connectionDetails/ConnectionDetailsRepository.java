package com.dbeaverLike.connectionDetails;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionDetailsRepository extends 
        SimpleJpaRepository<ConnectionDetails, Long> implements Serializable {

    EntityManager entityManager;
    
    ConnectionDetailsRepository(
            @Qualifier("connDetailsEntityManagerFactory") EntityManager entityManager) {
        super(ConnectionDetails.class, entityManager);
        this.entityManager = entityManager;
    }
}