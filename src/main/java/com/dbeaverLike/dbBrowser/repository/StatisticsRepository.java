package com.dbeaverLike.dbBrowser.repository;

import com.dbeaverLike.dbBrowser.config.ReqSpecDataAccessUser;
import com.dbeaverLike.dbBrowser.domain.ColumnStatistics;
import com.dbeaverLike.dbBrowser.domain.TableStatistics;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import static com.dbeaverLike.dbBrowser.repository.RepositoryUtils.*;
import javax.inject.Provider;

@Repository
public class StatisticsRepository {

    private Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser;
    @Inject
    public void setReqSpecDataAccessUser(Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser) {
        this.reqSpecDataAccessUser = reqSpecDataAccessUser;
    }

    public ColumnStatistics findColumnStatistics(String tableName, String columnName) {
     
        EntityManager entityManager = reqSpecDataAccessUser.get().provideReqSpecEntityManager();
        
        ColumnStatistics columnStatistics = null;
        if ((IsValidTableName(
                reqSpecDataAccessUser.get().getDatabaseName(),
                tableName, entityManager)) && 
                (IsValidColumnName(tableName, columnName, entityManager))) {

            columnStatistics = new ColumnStatistics();

            columnStatistics.setMinValue(
                    entityManager.createNativeQuery(String.format(
                            "select MIN(%s) from %s", columnName, tableName))
                            .getSingleResult());

            Object result = entityManager.createNativeQuery(String.format(
                    "select MAX(%s) from %s", columnName, tableName))
                    .getSingleResult();
            columnStatistics.setMaxValue(result);

            if ((result instanceof Number) && (!(result instanceof Date))) {

                columnStatistics.setAvgValue(
                        entityManager.createNativeQuery(String.format(
                                "select AVG(%s) from %s", columnName, tableName))
                                .getSingleResult());

                long number = ((BigInteger) entityManager.createNativeQuery(
                        "select count(" + columnName + ") from " + tableName + " where "
                        + columnName + " is not null")
                        .getSingleResult()).longValue();

                if (number % 2 == 0) {
                    columnStatistics.setMedianValue(
                            entityManager.createNativeQuery(String.format(
                                    "select sum(p_c)/2 from (select %1$s as p_c from %2$s where %1$s is not null order by %1$s limit %3$s,2) AdHoc",
                                    columnName, tableName, number / 2 - 1))
                                    .getSingleResult());

                } else {
                    columnStatistics.setMedianValue(
                            entityManager.createNativeQuery(String.format(
                                    "select %1$s from %2$s where %1$s is not null order by %1$s limit %3$s,1",
                                    columnName, tableName, number / 2)) // the result stays long, so no need to apply Math.floor
                                    .getSingleResult());
                }
            }
        }
        return columnStatistics;
    }

    public TableStatistics findTableStatistics(String tableName) {

        EntityManager entityManager = reqSpecDataAccessUser.get().provideReqSpecEntityManager();
        TableStatistics tableStatistics = null;
        
        if (IsValidTableName(
                reqSpecDataAccessUser.get().getDatabaseName(),
                tableName, entityManager)){

            tableStatistics = new TableStatistics();

            Long result = ((BigInteger) entityManager.createNativeQuery(
                    "select COUNT(*) from " + tableName)
                    .getSingleResult()).longValue();
            tableStatistics.setRecordNumber(result);

            List<Object[]> results = entityManager.createNativeQuery("show columns from " + tableName)
                    .getResultList();
            tableStatistics.setAttributeNumber(results.size());
        }
        return tableStatistics;
    }
}