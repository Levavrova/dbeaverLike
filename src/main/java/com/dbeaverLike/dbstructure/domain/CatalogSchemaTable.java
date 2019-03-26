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
public abstract class CatalogSchemaTable extends CatalogSchema implements Serializable{
    
    @Id
    @Column(name = "table_name")
    private String tableName;
}
