package com.dbeaverLike.connectionDetails;

import com.dbeaverLike.dbBrowser.config.ReqSpecDataAccessPool;
import com.dbeaverLike.exception.ConnectionDetailsNotFoundException;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "connDetailsTransactionManager")
public class ConnectionDetailsService {

    private final ConnectionDetailsRepository connDetailsRepository;
    private ReqSpecDataAccessPool dataAccessPool; 

    ConnectionDetailsService(
            ConnectionDetailsRepository connDetailsRepository) {
        this.connDetailsRepository = connDetailsRepository;
    }
    
    @Inject
    public void setReqSpecDataAccessPool(ReqSpecDataAccessPool dataAccessPool){
      this.dataAccessPool = dataAccessPool;  
    } 

    public ConnectionDetails findById(Long id) {
        return connDetailsRepository.findById(id)
                .orElseThrow(() -> new ConnectionDetailsNotFoundException(
                        "Connection details for Id " + id + " not found."));
    }

    public List<ConnectionDetails> findAll() {
        return connDetailsRepository.findAll();
    }

    public ConnectionDetails create(ConnectionDetails connectionDetails) {
        return connDetailsRepository.save(connectionDetails);
    }

    public ConnectionDetails save(ConnectionDetails connectionDetails) {
        ConnectionDetails updatedConnectionDetails = connDetailsRepository.save(connectionDetails);
        dataAccessPool.removeChangedConnectionFromDataAccessPool(connectionDetails.getId());
        return updatedConnectionDetails;
    }
   
    public void deleteById(Long id) {
        connDetailsRepository.deleteById(id);
        dataAccessPool.removeChangedConnectionFromDataAccessPool(id);
    }
}