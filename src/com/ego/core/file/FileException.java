/*---------异常---------
 * @功能说明：文件异常
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.file;

import com.ego.core.lang.BasicException;

/**
 * 文件受检异常
 *
 * @author Administrator
 */
public class FileException extends BasicException {

    public FileException(Throwable cause) {
        super(cause);
    }

    /**
     * 默认构造器
     */
    public FileException() {
    }

    /**
     * 带有字符串消息的异常构造器
     *
     * @param msg the detail message.
     */
    public FileException(String msg) {
        super(msg);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
