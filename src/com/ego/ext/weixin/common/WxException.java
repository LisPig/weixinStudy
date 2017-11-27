package com.ego.ext.weixin.common;



/**
 * wx受检异常
 *
 * @author Administrator
 */
public class WxException extends Exception {

    public WxException(Throwable cause) {
        super(cause);
    }

    /**
     * 默认构造器
     */
    public WxException() {
    }

    /**
     * 带有字符串消息的异常构造器
     *
     * @param msg the detail message.
     */
    public WxException(String msg) {
        super(msg);
    }

    public WxException(String message, Throwable cause) {
        super(message, cause);
    }
}
