package com.dbeaverLike.dbstructure.repository;
import com.dbeaverLike.dbstructure.domain.Schema;
import org.springframework.stereotype.Repository;

@Repository
public class SchemaRepository extends GenericDbStructureRepository<Schema> {

    SchemaRepository(){
        super(Schema.class);
    }
}
