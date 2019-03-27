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
import lombok.EqualsAndHashCode;

@Entity
@PersistenceUnit(name = "dbBrowser")
@Table(name = "columns")
@Data
@EqualsAndHashCode(callSuper = true)
public class DbColumn extends CatalogSchemaTable implements Serializable {

    @Id
    @Column(name = "column_name")
    private String columnName;
    @Column(name = "column_type")
    private String columnType;
    @Column(name = "column_key")
    private String columnKey;
    @Column(name = "ordinal_position")
    private Long ordinalPosition;
    @Column(name = "column_default")
    private String columnDefault;
    @Column(name = "is_nullable")
    private String isNullable;
    @Column(name = "data_type")
    private String dataType;
    @Column(name = "character_maximum_length")
    private Long characterMaximumLength;
    @Column(name = "character_octet_length")
    private Long characterOctetLength;
    @Column(name = "numeric_precision")
    private Long numericPrecision;
    @Column(name = "numeric_scale")
    private Long numericScale;
    @Column(name = "datetime_precision")
    private Long datetimePrecision;
    @Column(name = "character_set_name")
    private String characterSetName;
    @Column(name = "collation_name")
    private String collationName;
    @Column
    private String extra;
    @Column
    private String privileges;
    @Column(name = "column_comment")
    private String columnComment;
    @Column(name = "generation_expression")
    private String generationExpression;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumns({
        @JoinColumn(name = "table_catalog", referencedColumnName = "table_catalog"),
        @JoinColumn(name = "table_schema", referencedColumnName = "table_schema"),
        @JoinColumn(name = "table_name", referencedColumnName = "table_name"),
        @JoinColumn(name = "column_name", referencedColumnName = "column_name")
    })
    private List<DbIndex> dbIndexes = new LinkedList<>();
}