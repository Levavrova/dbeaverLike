package com.dbeaverLike.dbBrowser.config;

import com.dbeaverLike.exception.NoDbConnectionException;
import com.dbeaverLike.exception.URICreationException;
import com.dbeaverLike.connectionDetails.ConnectionDetails;
import com.zaxxer.hikari.HikariDataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactory {

    @Value("${appl.uri.jdbc.mysql.scheme}")
    private String uriScheme;
    @Value("${appl.uri.jdbc.mysql.query}")
    private String uriQuery;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${appl.data-source.configuration.maximum-pool-size}")
    private int maximumPoolSize;
    
    public HikariDataSource createDataSource(ConnectionDetails connDetails) {

        URI uri = null;
        try {
            uri = new URI(uriScheme, null, connDetails.getHostname(), connDetails.getPort(),
                    "/" + connDetails.getDatabaseName(),uriQuery, null);
        } catch (URISyntaxException e) {
            throw new URICreationException("Error when creating URI for connection details: " + connDetails.getId().toString());
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(uri.toString());
        dataSource.setUsername(connDetails.getUsername());
        dataSource.setPassword(String.valueOf(connDetails.getPassword()));
        dataSource.setDriverClassName(driverClassName);
        dataSource.setMaximumPoolSize(maximumPoolSize);
        connDetails.clearPassword();
        try {
            dataSource.getConnection();
        } catch (SQLException ex) {
            throw new NoDbConnectionException(ex.getMessage(), ex.getCause());
        }

        return dataSource;
    }
}