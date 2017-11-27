/*----------javabean异常---------
 * @javabean
 * @功能说明：超出范围的异常
 * @**方法列表**
 * 最后修改日期：2013-3-14:22:55
 */
package com.ego.core.lang;

/**
 * 超出范围的异常
 *
 * @author Administrator
 */
public class OutOfRangeException extends BasicException {

    /**
     * Creates a new instance of
     * <code>OutOfRangeException</code> without detail message.
     */
    public OutOfRangeException() {
    }

    /**
     * 带有字符串消息的异常构造器
     *
     * @param msg the detail message.
     */
    public OutOfRangeException(String msg) {
        super(msg);
    }

    /**
     *
     *
     * @param msg the detail message.
     */
    public OutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfRangeException(Throwable cause) {
        super(cause);
    }
}
