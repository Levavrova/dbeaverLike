package com.dbeaverLike.connectionDetails;

import com.dbeaverLike.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection-details")
public class ConnectionDetailsController {

    private final ConnectionDetailsService connectionDetailsService;
    
    ConnectionDetailsController(ConnectionDetailsService connectionDetailsService) {
        this.connectionDetailsService = connectionDetailsService;
    }

    @GetMapping("/{id}")
    ConnectionDetails getById(@PathVariable Long id) {
        return connectionDetailsService.findById(id);
    }

    @GetMapping("")
    Iterable<ConnectionDetails> getAll() {
        return connectionDetailsService.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    ConnectionDetails post(@RequestBody ConnectionDetails connectionDetails) {
        if (connectionDetails.getId() != null){
            throw new BadRequestException("Invalid data - Id must not be specified."); 
        }
        return connectionDetailsService.create(connectionDetails);
    }

    @PutMapping("/{id}")
    ConnectionDetails put(@PathVariable Long id, @RequestBody ConnectionDetails connectionDetails) {
        connectionDetails.setId(id);
        return connectionDetailsService.save(connectionDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        connectionDetailsService.deleteById(id);
    }
}