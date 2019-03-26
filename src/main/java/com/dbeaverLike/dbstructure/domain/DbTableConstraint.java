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
@Table(name = "table_constraints")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"constraintCatalog", "constraintSchema", "tableName", "constraintName"})
public class DbTableConstraint extends ConstraintCatalogSchemaTable implements Serializable {

    @Id
    @Column(name = "constraint_name")
    private String constraintName;
    @Column(name = "constraint_type")
    private String constraintType;
}