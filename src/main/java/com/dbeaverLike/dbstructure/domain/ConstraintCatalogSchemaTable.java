package com.dbeaverLike.dbstructure.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class ConstraintCatalogSchemaTable  implements Serializable{
    
    @Id
    @Column(name = "constraint_catalog")
    private String constraintCatalog;
    @Id
    @Column(name = "constraint_schema")
    private String constraintSchema;
    @Id
    @Column(name = "table_name")
    private String tableName;
}