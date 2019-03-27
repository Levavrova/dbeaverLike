package com.dbeaverLike.dbBrowser.domain;

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
@PersistenceUnit(name = "dbBrowser")
@Table(name="referential_constraints")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"constraintCatalog", "constraintSchema", "tableName"})
public class DbReferentialConstraint extends ConstraintCatalogSchemaTable implements Serializable {
    @Id
    @Column(name = "constraint_name")
    private String constraintName;
    @Column(name = "unique_constraint_name")
    private String uniqueConstraintName;   
    @Column(name = "match_option")
    private String matchOption;
    @Column(name = "update_rule")
    private String updateRule;
    @Column(name = "delete_rule")
    private String deleteRule;
    @Column(name = "referenced_table_name")
    private String referencedTableName;
}