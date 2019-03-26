package com.dbeaverLike.dbstructure.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbeaverLike.dbstructure.config.ReqSpecDataAccessUser;
import com.dbeaverLike.dbstructure.service.StatisticsService;
import com.dbeaverLike.dbstructure.domain.ColumnStatistics;
import com.dbeaverLike.dbstructure.domain.TableStatistics;
import com.dbeaverLike.exception.AppNotFoundException;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/connection-details/{id}")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser;
    
    StatisticsController(StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }
    
    @Inject
    public void setReqSpecDataAccessUser(Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser) {
        this.reqSpecDataAccessUser = reqSpecDataAccessUser;
    }    
    
    @GetMapping("/tables/{tableName}/columns/{columnName}/column-statistics")
    public ColumnStatistics getColumnStatistics(@PathVariable Long id,
            @PathVariable String tableName,@PathVariable String columnName) {
        initializeReqSpecDataAccessUser(id);     
        return Optional.ofNullable(
                statisticsService.getColumnStatistics(tableName, columnName))
                .orElseThrow(() -> new AppNotFoundException(
                        String.format("Table %s or column %s do not exist.", tableName, columnName)));
    }
      
    @GetMapping("/tables/{tableName}/table-statistics")
    public TableStatistics getTableStatistics(@PathVariable Long id,
            @PathVariable String tableName) {
        initializeReqSpecDataAccessUser(id);        
        return Optional.ofNullable(
                statisticsService.getTableStatistics(tableName))
                .orElseThrow(() -> new AppNotFoundException(
                        String.format("Table %s does not exist.", tableName)));
    }
        
    private void initializeReqSpecDataAccessUser(Long id){
        this.reqSpecDataAccessUser.get().setConnDetailsId(id);
    } 
}