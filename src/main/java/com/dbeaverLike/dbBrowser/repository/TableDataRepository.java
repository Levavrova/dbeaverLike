package com.dbeaverLike.dbBrowser.repository;

import com.dbeaverLike.dbBrowser.config.ReqSpecDataAccessUser;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.dbeaverLike.dbBrowser.repository.RepositoryUtils.*;
import javax.inject.Provider;

@Repository
public class TableDataRepository {
    
    private Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser;

    @Inject
    public void setReqSpecDataAccessUser(Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser) {
        this.reqSpecDataAccessUser = reqSpecDataAccessUser;
    }

    public List<Object> findAllDataByTableName(String tableName) {

        EntityManager entityManager = reqSpecDataAccessUser.get().provideReqSpecEntityManager();
        List<Object> result = null;

        if (IsValidTableName(
                reqSpecDataAccessUser.get().getDatabaseName(),
                tableName, entityManager)){

            result = new LinkedList<>();
            List<Object[]> tableColumns = findColumnsForTable(tableName, entityManager);
            result.add(createColumnsNameRow(tableColumns));
            
            String selectStatement = createSelectFromColumnsOfTable(tableColumns, tableName);
            result.addAll(entityManager.createNativeQuery(selectStatement)
                    .getResultList());
        }
        return result;
    }

    private List<Object[]> findColumnsForTable(String tableName, EntityManager entityManager) {

        return entityManager.createNativeQuery("show columns from " + tableName)
                .getResultList();
    }

    private String createSelectFromColumnsOfTable(List<Object[]> tableColumns, String tableName) {

        String selectPart = tableColumns
                .stream()
                .map(c -> c[0].toString())
                .collect(Collectors.joining(","));
        return String.format("select %s from %s", selectPart, tableName);
    }

    private Object createColumnsNameRow(List<Object[]> tableColumns) {

        Object columnsNameObject = tableColumns
                .stream()
                .map(c -> c[0].toString())
                .toArray();
        return columnsNameObject;
    }
}