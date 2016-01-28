package com.kankan.op.util;

/**
 * 需要事务处理的模块统一处理类
 * 
 * @since 2013-5-20
 * @author hujiachao
 */
public interface SyncTransactionRunnalable {

    public void transactionalRun() throws Throwable;
}
