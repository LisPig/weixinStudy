/*-----------JAVABEAN异常----------
 * @功能说明：转换失败受检异常
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-04-04:09:23
 */
package com.ego.core.lang;

/**
 * 转换失败受检异常
 *
 * @author EGO@QIN
 */
public class ConvertException extends BasicException {

    /**
     * Creates a new instance of
     * <code>ConvertException</code> without detail message.
     */
    public ConvertException() {
    }

    /**
     * 带有字符串消息的异常构造器
     *
     * @param msg the detail message.
     */
    public ConvertException(String msg) {
        super(msg);
    }

    /**
     *
     *
     * @param msg the detail message.
     */
    public ConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertException(Throwable cause) {
        super(cause);
    }
}
