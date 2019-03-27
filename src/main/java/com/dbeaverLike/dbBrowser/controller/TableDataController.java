package com.dbeaverLike.dbBrowser.controller;

import com.dbeaverLike.exception.AppNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbeaverLike.dbBrowser.config.ReqSpecDataAccessUser;
import com.dbeaverLike.dbBrowser.service.TableDataService;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;

@RestController
public class TableDataController {

    private final TableDataService tableDataService;
    private Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser;

    TableDataController(TableDataService tableDataService){
        this.tableDataService = tableDataService;
    }
    
    @Inject
    public void setReqSpecDataAccessUser(Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser) {
        this.reqSpecDataAccessUser = reqSpecDataAccessUser;
    }    
    
    @GetMapping("/connection-details/{id}/tables/{tableName}")
    public List<Object> getAllDataByTableName(@PathVariable Long id,
            @PathVariable String tableName) {
        initializeReqSpecDataAccessUser(id);       
        return Optional.ofNullable(
                tableDataService.getAllDataByTableName(tableName))
                .orElseThrow(() -> new AppNotFoundException(String.format("Table %s does not exist.",tableName)));
    }
      
    private void initializeReqSpecDataAccessUser(Long id){
        this.reqSpecDataAccessUser.get().setConnDetailsId(id);
    } 
}