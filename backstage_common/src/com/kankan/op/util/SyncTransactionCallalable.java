package com.kankan.op.util;

/**
 * 需要事务处理的模块统一处理类，支持返回值
 * 
 * @since 2013-5-30
 * @author hujiachao
 */
public interface SyncTransactionCallalable<T> {

    public T transactionalCall() throws Throwable;
}
