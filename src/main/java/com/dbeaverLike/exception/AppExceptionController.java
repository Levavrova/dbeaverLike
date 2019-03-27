package com.dbeaverLike.exception;

import com.dbeaverLike.dbBrowser.config.ReqSpecDataAccessUser;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class AppExceptionController {

    private Logger logger = LoggerFactory.getLogger(AppExceptionController.class);

    private Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser;
    @Inject
    public void setReqSpecDataAccessUser(Provider<ReqSpecDataAccessUser> reqSpecDataAccessUser) {
        this.reqSpecDataAccessUser = reqSpecDataAccessUser;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)    
    public String badRequestException(BadRequestException ex) {
        logger.info(ex.getMessage());
        return String.format("%s: %s",
                HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ConnectionDetailsNotFoundException.class)
    public String notFoundException(ConnectionDetailsNotFoundException ex) {
        logger.info(ex.getMessage());
        return String.format("%s: %s",
                HttpStatus.NOT_FOUND.toString(), ex.getMessage());
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = AppNotFoundException.class)
    public String notFoundException(AppNotFoundException ex) {
        reqSpecDataAccessUser.get().releaseReqSpecDataAccess();
        logger.info(ex.getMessage());
        return String.format("%s: %s",
                HttpStatus.NOT_FOUND.toString(), ex.getMessage());
    }
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    public String notFoundException(EmptyResultDataAccessException ex) {
        reqSpecDataAccessUser.get().releaseReqSpecDataAccess();
        ex.printStackTrace();
        return String.format("%s: %s",
                HttpStatus.NOT_FOUND.toString(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = {URICreationException.class, NoDbConnectionException.class,
            AppConflictException.class})
    public String conflictException(RuntimeException ex) {
        return String.format("%s: %s",
                HttpStatus.CONFLICT.toString(), ex.getMessage());
    }
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = SQLException.class)
    public String conflictException(SQLException ex) {
        reqSpecDataAccessUser.get().releaseReqSpecDataAccess();
        ex.printStackTrace();
        return String.format("%s: %s",
                HttpStatus.CONFLICT.toString(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(value = ConnectionPoolOverloadException.class)
    public String overlaodException(ConnectionPoolOverloadException ex) {
        logger.warn(ex.getMessage());
        return String.format("%s: %s",
                HttpStatus.SERVICE_UNAVAILABLE.toString(), ex.getMessage());
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {DataAccessException.class, AppIntServerErrorException.class})
    public String internalServerErrorException(RuntimeException ex) {
        reqSpecDataAccessUser.get().releaseReqSpecDataAccess();
        ex.printStackTrace();
        return String.format("%s: %s",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
    }
}