package com.dbeaverLike.dbBrowser.service;

import com.dbeaverLike.RequestSpecifiedTransactional;
import com.dbeaverLike.dbBrowser.repository.TableDataRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TableDataService {

private final TableDataRepository genericDataRepository;  

 TableDataService(TableDataRepository genericDataRepository){
    this.genericDataRepository = genericDataRepository;
}

@RequestSpecifiedTransactional
public List<Object> getAllDataByTableName(String table){   
    return genericDataRepository.findAllDataByTableName(table);
    }
}