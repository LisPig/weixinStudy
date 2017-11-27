/*---------异常---------
 * @功能说明：XML文件异常
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.file.xml;

import com.ego.core.file.FileException;

/**
 * XML文件受检异常
 *
 * @author Administrator
 */
public class XmlFileParseException extends FileException {

    public XmlFileParseException(Throwable cause) {
        super(cause);
    }

    /**
     * 默认构造器
     */
    public XmlFileParseException() {
    }

    /**
     * 带有字符串消息的异常构造器
     *
     * @param msg the detail message.
     */
    public XmlFileParseException(String msg) {
        super(msg);
    }

    public XmlFileParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
