package com.dbeaverLike.connectionDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import lombok.Data;

@Entity()
@PersistenceUnit(name = "connectionDetails")
@Table(name = "connection_details")
@Data
@JsonIgnoreProperties(value = {"password"}, allowSetters=true)
public class ConnectionDetails implements java.io.Serializable {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column 
    private String name;
    @Column 
    private String hostname;
    @Column 
    private int port;
    @Column 
    private String databaseName;
    @Column 
    private String username;
    @Column 
    private char[] password;
    
    public void clearPassword(){
        Arrays.fill(password,'x' );
    }
}