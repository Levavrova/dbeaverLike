package com.dbeaverLike.dbstructure.controller;

import com.dbeaverLike.dbstructure.domain.DbColumn;
import com.dbeaverLike.dbstructure.domain.DbTable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbeaverLike.dbstructure.domain.Schema;
import com.dbeaverLike.dbstructure.service.ListingService;
import com.dbeaverLike.dbstructure.config.ReqSpecDataAccessUser;
import com.dbeaverLike.exception.AppNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;

@RestController
@RequestMapping("/connection-details/{id}")
public class DbStructureController {

    private final ListingService listingService;
    private Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser;

    DbStructureController(ListingService listingService){
        this.listingService = listingService;
    }

    @Inject
    public void setReqSpecDataAccessUser(Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser) {
        this.reqSpecDataAccessUser = reqSpecDataAccessUser;
    }
    
    @GetMapping("/schemas")
    public List<Schema> getAllSchemas(@PathVariable Long id) {
        initializeReqSpecDataAccessUser(id);
        return listingService.getAllSchemas();
    }
    
    @GetMapping("/tables")
    public List<DbTable> getAllTables(@PathVariable Long id) {
        initializeReqSpecDataAccessUser(id);
        return listingService.getAllTables();
    }
    
    @GetMapping("/schemas/{schemaName}/tables")
    public List<DbTable> getTablesBySchemaName(@PathVariable Long id,
            @PathVariable String schemaName) {
        initializeReqSpecDataAccessUser(id);
        return Optional.ofNullable(
                listingService.getTablesBySchemaName(schemaName))
                .orElseThrow(() -> new AppNotFoundException(String.format(
                    "Schema %s does not exist.",schemaName)));
    }
    
     @GetMapping("/columns")
    public List<DbColumn> getAllColumns(@PathVariable Long id) {
        initializeReqSpecDataAccessUser(id);
        return listingService.getAllColumns();
    }

    @GetMapping("/schemas/{schemaName}/tables/{tableName}/columns")    
    public List<DbColumn> getColumnsBySchemaNameAndTableName(@PathVariable Long id,
            @PathVariable String schemaName, @PathVariable String tableName) {
        initializeReqSpecDataAccessUser(id);
        return Optional.ofNullable(
                listingService.getColumnsBySchemaNameAndTableName(schemaName, tableName))
                .orElseThrow(() -> new AppNotFoundException(String.format(
                        "Schema %s or table %s do not exist.", schemaName, tableName)));
    }
    
    private void initializeReqSpecDataAccessUser(Long id){
        ReqSpecDataAccessUser reqSpecDataAccessUser = this.reqSpecDataAccessUser.get();
        reqSpecDataAccessUser.setConnDetailsId(id);       
    } 
}