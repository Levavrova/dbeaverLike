package com.dbeaverLike.dbstructure.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@PersistenceUnit(name = "dbStructure")
@Table(name = "schema_privileges")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"tableCatalog", "tableSchema"})
public class SchemaPrivilege extends CatalogSchema implements Serializable {
    
    @Id
    @Column
    private String grantee;
    @Id
    @Column(name = "privilege_type")
    private String privilegeType;
    @Column(name = "is_grantable")
    private String isGrantable;
}
