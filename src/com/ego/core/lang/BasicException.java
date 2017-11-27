/*----------异常--------
 * @功能说明：ego系统顶级异常
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.lang;

/**
 * 定义一个ego系统的最顶顶层（最基础）的受检异常
 *
 * @author Administrator
 */
public class BasicException extends Exception {

    /**
     * 默认构造器
     *
     */
    public BasicException() {
    }

    /**
     * 带有字符串消息的异常构造器
     *
     * @param msg the detail message.
     */
    public BasicException(String msg) {
        super(msg);
    }

    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicException(Throwable cause) {
        super(cause);
    }
}
