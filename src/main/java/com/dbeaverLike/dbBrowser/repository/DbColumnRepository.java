package com.dbeaverLike.dbBrowser.repository;
import com.dbeaverLike.dbBrowser.domain.DbColumn;
import static com.dbeaverLike.dbBrowser.repository.RepositoryUtils.*;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class DbColumnRepository extends GenericDbStructureRepository<DbColumn> {

    DbColumnRepository(){
        super(DbColumn.class);
    }
        
    public List<DbColumn> findBySchemaNameAndTableName(String schemaName, String tableName) {
        
        verifyDbForStructureQuery(reqSpecDataAccessUser.get().getDatabaseName());
        EntityManager entityManager = reqSpecDataAccessUser.get().provideReqSpecEntityManager();
        if ((IsValidSchemaName(schemaName, entityManager)) && 
                (IsValidTableName(schemaName, tableName, entityManager))){
        
            return super.findByTwoAttributes("tableSchema", schemaName, "tableName", tableName);
        } else {
            return null;
        }
    }
}