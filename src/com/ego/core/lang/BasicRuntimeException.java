/*----------javabean异常---------
 * @javabean
 * @功能说明：定义一个ego系统的最顶顶层（最基础）的非受检异常
 * @**方法列表**
 * 最后修改日期：2013-3-14:22:55
 */
package com.ego.core.lang;

/**
 * 定义一个ego系统的最顶顶层（最基础）的非受检异常
 *
 * @author Administrator
 */
public class BasicRuntimeException extends RuntimeException {

    /**
     * 默认构造器
     */
    public BasicRuntimeException() {
    }

    /**
     * 带有字符串消息的异常构造器
     *
     * @param msg the detail message.
     */
    public BasicRuntimeException(String msg) {
        super(msg);
    }

    public BasicRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicRuntimeException(Throwable cause) {
        super(cause);
    }
}
