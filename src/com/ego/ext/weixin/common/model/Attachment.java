/**
 * 微信公众平台开发模式(JAVA) SDK http://www.jeasyuicn.com/wechat
 */
package com.ego.ext.weixin.common.model;

import java.io.BufferedInputStream;

/**
 * 多媒体文件对象。下载文件对象
 *
 *
 */
public class Attachment {

    private String fileName;
    private String fullName;
    private String suffix;
    private String contentLength;
    private String contentType;
    private BufferedInputStream fileStream;
    private String error;

    /**
     *
     * @return
     */
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 不包括扩展名
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public BufferedInputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(BufferedInputStream fileStream) {
        this.fileStream = fileStream;
    }

    /**
     * 错误情况下的返回JSON数据包示例如下（示例为无效媒体ID错误）：
     *
     * : 如：{"errcode":40007,"errmsg":"invalid media_id"}
     *
     * @return
     */
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
