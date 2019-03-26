package com.dbeaverLike.dbstructure.service;

import com.dbeaverLike.RequestSpecifiedTransactional;
import com.dbeaverLike.dbstructure.repository.StatisticsRepository;
import com.dbeaverLike.dbstructure.domain.ColumnStatistics;
import com.dbeaverLike.dbstructure.domain.TableStatistics;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }
    
    @RequestSpecifiedTransactional
    public ColumnStatistics getColumnStatistics(String tableName, String columnName) {
        return statisticsRepository.findColumnStatistics(tableName, columnName);
    }
    @RequestSpecifiedTransactional
    public TableStatistics getTableStatistics(String tableName) {
        return statisticsRepository.findTableStatistics(tableName);
    }
}