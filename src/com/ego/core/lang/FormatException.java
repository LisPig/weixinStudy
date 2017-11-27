
/*-----------JAVABEAN异常----------
 * @功能说明：格式非法的受检异常
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-04-04:09:23
 */
package com.ego.core.lang;

/**
 * 格式非法的受检异常
 *
 * @author Administrator
 */
public class FormatException extends BasicException {

    /**
     * Creates a new instance of
     * <code>FormatException</code> without detail message.
     */
    public FormatException() {
    }

    /**
     * 带有字符串消息的异常构造器
     *
     * @param msg the detail message.
     */
    public FormatException(String msg) {
        super(msg);
    }

    /**
     *
     *
     * @param msg the detail message.
     */
    public FormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormatException(Throwable cause) {
        super(cause);
    }
}
