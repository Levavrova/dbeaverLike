package com.dbeaverLike.dbstructure.repository;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RepositoryUtils {
    
    public static Boolean IsValidSchemaName(String schemaName, EntityManager entityManager) {

        Query query = entityManager.createNativeQuery("show schemas");
        List<String> result = query.getResultList();
 
        result = result.stream()
                .map(t -> t.toLowerCase())
                .collect(Collectors.toList());
        return result.contains(schemaName);
    }    
    
    public static Boolean IsValidTableName(String dbName, String tableName, EntityManager entityManager) {

        Query query = entityManager.createNativeQuery("show tables from " + dbName);
        List<String> result = query.getResultList();
 
        result = result.stream()
                .map(t -> t.toLowerCase())
                .collect(Collectors.toList());
        return result.contains(tableName);
    }

    public static Boolean IsValidColumnName(String tableName, String columnName, EntityManager entityManager) {

        Query query = entityManager.createNativeQuery("show columns from " + tableName);
        List<Object[]> result = query.getResultList();
        List<String> columnsName = result
                .stream()
                .map(c -> c[0].toString().toLowerCase())
                .collect(Collectors.toList());

        return columnsName.contains(columnName.toLowerCase());
    }   
}