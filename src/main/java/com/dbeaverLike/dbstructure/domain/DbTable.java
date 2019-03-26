package com.dbeaverLike.dbstructure.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@PersistenceUnit(name = "dbStructure")
@Table(name = "tables")
@Data
@EqualsAndHashCode(callSuper = true)
public class DbTable extends CatalogSchema implements Serializable {

    @Id
    @Column(name = "table_name")
    private String tableName;
    @Column(name = "table_type")
    private String tableType;
    @Column
    private String engine;
    @Column
    private Long version;
    @Column(name = "row_format")
    private String rowFormat;
    @Column(name = "table_rows")
    private Long tableRows;
    @Column(name = "avg_row_length")
    private Long avgRowLength;
    @Column(name = "data_length")
    private Long dataLength;
    @Column(name = "max_data_length")
    private Long maxDataLength;
    @Column(name = "index_length")
    private Long indexLength;
    @Column(name = "data_free")
    private Long dataFree;
    @Column(name = "auto_increment")
    private Long autoIncrement;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    @Column(name = "check_time")
    private LocalDateTime checkTime;
    @Column(name = "table_collation")
    private String tableCollation;
    @Column
    private Long checksum;
    @Column(name = "create_options")
    private String createOptions;
    @Column(name = "table_comment")
    private String tableComment;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumns({
        @JoinColumn(name = "constraint_catalog", referencedColumnName = "table_catalog"),
        @JoinColumn(name = "constraint_schema", referencedColumnName = "table_schema"),
        @JoinColumn(name = "table_name", referencedColumnName = "table_name")
    })
    private List<DbReferentialConstraint> referentialConstraints = new LinkedList<>();
}