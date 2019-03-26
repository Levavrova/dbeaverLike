package com.dbeaverLike.dbstructure.repository;

import com.dbeaverLike.dbstructure.config.ReqSpecDataAccessUser;
import com.dbeaverLike.exception.AppConflictException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract class GenericDbStructureRepository<E> implements Serializable {

    @Value("${appl.database-structure.database}")
    private String dbStructure;
    
    private Class<E> entityClass;
    
    protected Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser;
    
    public GenericDbStructureRepository(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Inject
    void setReqSpecDataAccessUser(Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser) {
        this.reqSpecDataAccessUser = reqSpecDataAccessUser;
    }

    public List<E> findAll() {

        verifyDbForStructureQuery(reqSpecDataAccessUser.get().getDatabaseName());            
        EntityManager entityManager = reqSpecDataAccessUser.get().provideReqSpecEntityManager();
        
        CriteriaQuery<E> criteriaQuery = entityManager.getCriteriaBuilder()
                .createQuery(this.entityClass);
        TypedQuery<E> query = entityManager.createQuery(
                criteriaQuery.select(criteriaQuery.from(this.entityClass)));
    
        return query.getResultList();
    }
    public List<E> findByOneAttribute(String attributeName, String attributeValue) {
       
        EntityManager entityManager = reqSpecDataAccessUser.get().provideReqSpecEntityManager();
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
        Root<E> root = criteriaQuery.from(this.entityClass);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get(attributeName), attributeValue));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    
    public List<E> findByTwoAttributes(String firstAttributeName, String firstAttributeValue,
            String secondAttributeName, String secondAttributeValue) {

        EntityManager entityManager = reqSpecDataAccessUser.get().provideReqSpecEntityManager();
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
        Root<E> root = criteriaQuery.from(this.entityClass);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get(firstAttributeName), firstAttributeValue),
                criteriaBuilder.equal(root.get(secondAttributeName), secondAttributeValue)));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    
    protected void verifyDbForStructureQuery(String databaseName){
        if (!(databaseName.equalsIgnoreCase(dbStructure)))
            throw new AppConflictException(String.format(
                    "Wrong connection details configuration. The operation is supported for database %s only.", dbStructure));
    } 
}