/*----------javabean---------
 * @功能说明：URL工具
 * @创建日期：2013-04-08:09:23
 * @最后修改日期：2013-04-08:15:23
 */
package com.ego.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class UtilURL {

    /**
     *
     * 对URL编码.如果不支持解码返回原始字符串。
     *
     * @param url
     * @param charset 如果charset为null或为空字符串。默认为utf-8
     * @return
     */
    public static String encode(String url, String charset) {
        charset = UtilValidate.isEmpty(charset) ? "utf-8" : charset;
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {

            return url;
        }
    }

    /**
     * url解码。如果不支持解码返回原始字符串。
     *
     * @param url
     * @param charset 如果charset为null或为空字符串。默认为utf-8
     * @return
     */
    public static String decode(String url, String charset) {
        charset = UtilValidate.isEmpty(charset) ? "utf-8" : charset;
        try {
            return URLDecoder.decode(url, charset);
        } catch (UnsupportedEncodingException e) {
            return url;
        }

    }

    /**
     * 构造url
     *
     * @param url
     * @param params
     * @return
     */
    public static String buildWithParams(String url, Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (url.indexOf("?") == -1) {
            sb.append("?");
        } else if (!url.endsWith("&")) {
            sb.append("&");
        }
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=");
            if (UtilValidate.isNotEmpty(value)) {
                sb.append(encode(value, "utf-8"));
            }
        }
        return sb.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
