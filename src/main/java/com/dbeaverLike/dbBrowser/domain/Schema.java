package com.dbeaverLike.dbBrowser.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import lombok.Data;

@Entity
@PersistenceUnit(name = "dbBrowser")
@Table(name = "schemata")
@Data
public class Schema implements Serializable {
         
    @Id
    @Column(name = "schema_name")
    private String schemaName;
    @Id
    @Column(name = "catalog_name")
    private String catalogName;
    @Column(name = "default_character_set_name")
    private String defaultCharacterSetName;
    @Column(name = "default_collation_name")
    private String defaultCollationName;
    @Column(name = "sql_path")
    private String sqlPath;
    
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    @JoinColumns({
        @JoinColumn(name = "table_schema", referencedColumnName = "schema_name"),
        @JoinColumn(name = "table_catalog", referencedColumnName = "catalog_name")
    })
    private List<SchemaPrivilege> schemaPrivileges = new LinkedList<>();
}