package com.dbeaverLike.dbBrowser.repository;
import com.dbeaverLike.dbBrowser.domain.Schema;
import org.springframework.stereotype.Repository;

@Repository
public class SchemaRepository extends GenericDbStructureRepository<Schema> {

    SchemaRepository(){
        super(Schema.class);
    }
}
