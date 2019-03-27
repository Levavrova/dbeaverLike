package com.dbeaverLike.dbBrowser.service;

import com.dbeaverLike.RequestSpecifiedTransactional;
import com.dbeaverLike.dbBrowser.domain.DbColumn;
import com.dbeaverLike.dbBrowser.domain.Schema;
import com.dbeaverLike.dbBrowser.domain.DbTable;
import com.dbeaverLike.dbBrowser.repository.SchemaRepository;
import com.dbeaverLike.dbBrowser.repository.DbTableRepository;
import com.dbeaverLike.dbBrowser.repository.DbColumnRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListingService {

    private final SchemaRepository schemaRepository;
    private final DbTableRepository dbTableRepository;
    private final DbColumnRepository dbColumnRepository;

    ListingService(SchemaRepository schemaRepository, DbTableRepository dbTableRepository,
            DbColumnRepository dbColumnRepository){
        this.schemaRepository = schemaRepository;
        this.dbTableRepository = dbTableRepository;
        this.dbColumnRepository = dbColumnRepository;
    }
    
    @RequestSpecifiedTransactional
    public List<Schema> getAllSchemas() {
        return schemaRepository.findAll();
    }

    @RequestSpecifiedTransactional
    public List<DbTable> getAllTables() {
        return dbTableRepository.findAll();
    }

    @RequestSpecifiedTransactional
    public List<DbTable> getTablesBySchemaName(String schemaName) {
        return dbTableRepository.findBySchemaName(schemaName);
    }

    @RequestSpecifiedTransactional
    public List<DbColumn> getAllColumns() {
        return dbColumnRepository.findAll();
    }
        @RequestSpecifiedTransactional
    public List<DbColumn> getColumnsBySchemaNameAndTableName(String schemaName, String tableName) {
        return dbColumnRepository.findBySchemaNameAndTableName(schemaName, tableName);
    }
}