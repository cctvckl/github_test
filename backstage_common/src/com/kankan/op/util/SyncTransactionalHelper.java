package com.kankan.op.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.xunlei.netty.httpserver.exception.ProcessFinishedError;

/**
 * 事务方法调用区
 * 
 * @since 2013-5-30
 * @author hujiachao
 */
@Service
public class SyncTransactionalHelper {

    /**
     * 执行事务块，无返回值
     */
    @Transactional(readOnly = false, propagation = Propagation.NESTED, rollbackFor = Throwable.class, noRollbackFor = {
        ProcessFinishedError.class,
        NoRollbackMsg.class
    })
    public void transactionalRun(SyncTransactionRunnalable st) throws Throwable {
        st.transactionalRun();
    }

    /**
     * 执行事务块，可返回返回值
     */
    @Transactional(readOnly = false, propagation = Propagation.NESTED, rollbackFor = Throwable.class, noRollbackFor = {
        ProcessFinishedError.class,
        NoRollbackMsg.class
    })
    public <T> T transactionalCall(SyncTransactionCallalable<T> st) throws Throwable {
        return st.transactionalCall();
    }
}
