/*----------接口--------
 * @功能说明：资源存储
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-4-31:09:23
 */
package com.ego.core.lang;

/**
 * 资源存储 *interface taken from Apache JCI
 */
public interface ResourceStore {

    /**
     * 写入字节数组到默认的资源文件
     *
     * @param pResourceData
     */
    void write(final byte[] pResourceData);

    /**
     * 写入字节数组到指定的的资源文件
     *
     * @param pResourceName 资源文件名，不包括路径。即资源文件名加后缀。
     * @param pResourceData
     * @deprecated
     */
    //void write(final byte[] pResourceData, String pResourceName);
    /*
     * 根据指定的资源文件名读取资源文件为字节数组
     *
     * @param pResourceName 资源文件名，不包括路径。即资源文件名加后缀。
     * @deprecated
     * @return
     */
    // byte[] read(String pResourceName);
    /**
     * 读取资源文件为字节数组
     *
     * @return
     */
    byte[] read();
}
