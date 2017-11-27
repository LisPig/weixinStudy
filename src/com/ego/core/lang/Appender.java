/*----------接口---------
 * @功能说明：可追加
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.lang;

/**
 * 可追加
 *
 * @author Administrator
 * @param <T>
 */
public interface Appender<T> {

    T appendTo(T target);
}
