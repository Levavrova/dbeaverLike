package com.dbeaverLike.dbstructure.repository;
import com.dbeaverLike.dbstructure.domain.DbTable;
import static com.dbeaverLike.dbstructure.repository.RepositoryUtils.IsValidSchemaName;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class DbTableRepository extends GenericDbStructureRepository<DbTable> {
    
    DbTableRepository(){
        super(DbTable.class);
    }
        
    public List<DbTable> findBySchemaName(String schemaName) {
        
        verifyDbForStructureQuery(reqSpecDataAccessUser.get().getDatabaseName());
        EntityManager entityManager = reqSpecDataAccessUser.get()
                .provideReqSpecEntityManager();
        if (IsValidSchemaName(schemaName, entityManager)){
            
            return super.findByOneAttribute("tableSchema", schemaName);
        } else{
            return null;
        }
    }
}