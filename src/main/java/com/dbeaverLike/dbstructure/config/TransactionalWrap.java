package com.dbeaverLike.dbstructure.config;

import com.dbeaverLike.dbstructure.config.ReqSpecDataAccessUser;
import com.dbeaverLike.exception.AppConflictException;
import com.dbeaverLike.exception.AppIntServerErrorException;
import com.dbeaverLike.exception.AppNotFoundException;
import javax.inject.Inject;
import javax.inject.Provider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
@Aspect
public class TransactionalWrap {

    private Provider<ReqSpecDataAccessUser> reqSpecDataAccessUserProvider;
    @Inject
    public void setReqSpecDataAccessUser(Provider<ReqSpecDataAccessUser> reqSpecDataAccessUserProvider){
        this.reqSpecDataAccessUserProvider = reqSpecDataAccessUserProvider;
    }
    
    @Around("@annotation(com.dbeaverLike.RequestSpecifiedTransactional)")
    public Object provideTransactionalWrap(ProceedingJoinPoint pjp) throws Throwable {
    
        ReqSpecDataAccessUser reqSpecDataAccessUser = reqSpecDataAccessUserProvider.get();
        JpaTransactionManager transactionManager = reqSpecDataAccessUser.provideReqSpecTransactionManager();
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setReadOnly(true);
        return (transactionTemplate.execute(status ->
            { 
                try {
                    return pjp.proceed();
                } catch ( AppConflictException ex){
                        throw new AppConflictException(ex.getMessage());
                } catch ( InvalidDataAccessResourceUsageException ex){
                    ex.printStackTrace();
                    throw new AppNotFoundException(ex.getMessage());
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    throw new AppIntServerErrorException(ex.getMessage());
                } finally{
                    reqSpecDataAccessUser.releaseReqSpecDataAccess();
                }
            }
        ));
    }
}