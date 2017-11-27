/**
 * 微信公众平台开发模式(JAVA) SDK (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.ego.ext.weixin.common.util;

import com.ego.core.net.HttpGet;
import com.ego.core.net.HttpPost;
import com.ego.core.net.Param;
import com.ego.core.util.UtilURL;
import com.ego.core.util.UtilValidate;
import com.ego.ext.weixin.common.model.Attachment;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UtilHttp {

    public static String DEFAULT_CHARSET = "utf-8";

    /**
     * 发送get请求
     *
     * @param url 请求URL
     * @param https 是否通过https连接
     * @return 请求获得的字符串
     * @throws Exception
     */
    public static String get(String url, boolean https) throws Exception {
        HttpGet get;
        if (https) {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            get = new HttpGet(url, ssf);
        } else {
            get = new HttpGet(url);
        }
        String ret = get.submit();
        get.close();
        return ret;
    }

    /**
     * 发送get请求
     *
     * @param url 是否通过https连接
     * @param https
     * @param params 请求参数
     * @return
     * @throws Exception
     */
    public static String get(String url, boolean https, Map<String, String> params) throws Exception {
        return get(UtilURL.buildWithParams(url, params), https);
    }

    /**
     * 发送Post请求 通过https
     *
     * @param url
     * @param https
     * @param params
     * @return
     * @throws java.lang.Exception
     *
     */
    public static String post(String url, boolean https, String params) throws Exception {
        HttpPost post;
        if (https) {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            post = new HttpPost(url, ssf);
        } else {
            post = new HttpPost(url);
        }
        post.setPostData(params);
        String ret = post.Submit();
        post.close();

        return ret;
    }

    /**
     * 上传媒体文件
     *
     * @param url
     * @param file
     * @return
     * 正确情况下的返回JSON数据包结果如下：{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
     *
     * 错误情况下的返回JSON数据包示例如下（示例为无效媒体类型错误）:{"errcode":40004,"errmsg":"invalid media
     * type"}
     * @throws java.lang.Exception
     * @see http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
     */
    public static String upload(String url, File file, Param... params) throws Exception {
        String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL"; // 定义数据分隔线  
        StringBuffer bufferRes = null;
        URL urlGet = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlGet.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        OutputStream out = new DataOutputStream(conn.getOutputStream());
        byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线  
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] data = sb.toString().getBytes();
        out.write(data);
        DataInputStream fs = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = fs.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        out.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个  

        if (params != null) {
            StringBuilder _postData = new StringBuilder();
            int paramLen = params.length;
            for (int i = 0; i < paramLen; i++) {
                Param p = params[i];
                _postData.append(p.getName());
                _postData.append("=");
                _postData.append(URLEncoder.encode(new String(((String) p.getValue()).getBytes("utf-8"), "utf-8"), "utf-8"));
                if (i != paramLen - 1) {
                    _postData.append("&");
                }
            }
            out.write(_postData.toString().getBytes("utf-8"));
        }

        fs.close();
        out.write(end_data);
        out.flush();
        out.close();

        // 定义BufferedReader输入流来读取URL的响应  
        InputStream in = conn.getInputStream();

        BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
        String valueString = null;
        bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null) {
            bufferRes.append(valueString);
        }
        in.close();
        if (conn != null) {
            // 关闭连接
            conn.disconnect();
        }
        return bufferRes.toString();
    }

    /**
     * 下载资源
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Attachment download(String url) throws IOException {
        Attachment att = new Attachment();
        URL urlGet = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlGet.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");
        conn.connect();
        //
        if (conn.getContentType().equalsIgnoreCase("text/plain")) {
            // 定义BufferedReader输入流来读取URL的响应  
            InputStream in = conn.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
            String valueString = null;
            StringBuffer bufferRes = new StringBuffer();
            while ((valueString = read.readLine()) != null) {
                bufferRes.append(valueString);
            }
            in.close();
            att.setError(bufferRes.toString());
        } else {
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            String ds = conn.getHeaderField("Content-disposition");
            String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
            String relName = fullName.substring(0, fullName.lastIndexOf("."));
            String suffix = fullName.substring(relName.length() + 1);

            att.setFullName(fullName);
            att.setFileName(relName);
            att.setSuffix(suffix);
            att.setContentLength(conn.getHeaderField("Content-Length"));
            att.setContentType(conn.getHeaderField("Content-Type"));

            att.setFileStream(bis);
        }
        return att;
    }

    /**
     * 构造url
     *
     * @param url
     * @param params
     * @return
     */
    private static String initParams(String url, Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (url.indexOf("?") == -1) {
            sb.append("?");
        } else {
            sb.append("&");
        }
        boolean first = true;
        for (Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=");
            if (UtilValidate.isNotEmpty(value)) {
                try {
                    sb.append(URLEncoder.encode(value, DEFAULT_CHARSET));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String fname = "dsasdas.mp4";
        String s = fname.substring(0, fname.lastIndexOf("."));
        String f = fname.substring(s.length() + 1);
        System.out.println(f);
    }
}

/**
 * 证书管理
 */
class MyX509TrustManager implements X509TrustManager {

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }
}
