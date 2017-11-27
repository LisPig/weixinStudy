
/*-----------接口---------
 * @功能说明：用于回调处理的接口
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.lang;

/**
 * 用于回调处理的接口。比如在另外的方法内需要执行未知的方法，那么就可以用回调技术，传递一个回调对象并调用回调对象的指定方法。
 *
 *
 * @author Administrator
 * @param <T>
 */
public interface CallBack<T> {

    /**
     * 回调方法，用于传递该接口类型的对象执行此方法
     *
     * @param arg 调用此方法传递的参数数组
     * @return
     * @throws BasicException
     */
    public T invoke(Object... arg) ;
    
}
