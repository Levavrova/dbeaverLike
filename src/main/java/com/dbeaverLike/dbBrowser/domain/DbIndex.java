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
@Table(name="statistics")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"tableCatalog", "tableSchema", "tableName", "columnName"})
public class DbIndex extends CatalogSchemaTable implements Serializable{

    @Id
    @Column(name = "index_name")
    private String indexName;
    @Id
    @Column(name = "seq_in_index")
    private Byte seqInIndex;
    @Column(name = "column_name")
    private String columnName;
    @Column(name = "non_unique")
    private Byte nonUnique;
    @Column
    private String collation;
    @Column
    private Integer cardinality;
    @Column(name = "sub_part")
    private String subPart;
    @Column
    private String packed;
    private String nullable;
    @Column(name = "index_type")
    private String indexType;
    @Column
    private String Comment;
    @Column(name = "index_comment")
    private String indexComment;
}