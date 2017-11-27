package com.ego.core.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;               
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class HttpGet {

    private ArrayList<Param> heads;
    public String encoding;
    private URL url;
    private static CookieManager cm;
    private HttpURLConnection httpConnection;
    private HttpsURLConnection httpsConnection;
    private int connectTimeout = 25000;
    private int readTimeout = 25000;

    public HttpGet(String urlstr) throws IOException {
        this.init(urlstr);
        this.httpConnection = (HttpURLConnection) this.url.openConnection();
        this.initHttpConn();
    }

    public HttpGet(String urlstr, SSLSocketFactory ssf) throws IOException {
        this.init(urlstr);
        this.httpsConnection = (HttpsURLConnection) url.openConnection();
        this.httpsConnection.setSSLSocketFactory(ssf);
        this.initHttpConn();
    }

    private void init(String urlstr) throws MalformedURLException {
        cm = new CookieManager();
        cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cm);
        this.heads = new ArrayList();
        this.encoding = "UTF-8";
        this.url = new URL(urlstr);
    }

    private void initHttpConn() throws ProtocolException {
        if (this.httpConnection != null) {
            this.httpConnection.setConnectTimeout(connectTimeout);
            this.httpConnection.setReadTimeout(readTimeout);
            this.httpConnection.setRequestMethod("GET");
            this.httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            this.httpConnection.setDoOutput(true);
            this.httpConnection.setDoInput(true);
        } else {
            this.httpsConnection.setConnectTimeout(connectTimeout);
            this.httpsConnection.setReadTimeout(readTimeout);
            this.httpsConnection.setRequestMethod("GET");
            this.httpsConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            this.httpsConnection.setDoOutput(true);
            this.httpsConnection.setDoInput(true);
        }

    }

    public HttpGet setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public String getEncoding() {
        return this.encoding;
    }

    /**
     * 设置连接超时
     *
     * @param connectTimeout 默认25000
     * @return
     */
    public HttpGet setCnnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getcCnnectTimeout() {
        return this.connectTimeout;
    }

    /**
     * 读取超时 --服务器响应比较慢，增大时间
     *
     * @param readTimeout
     * @return
     */
    public HttpGet setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public HttpURLConnection getHttpConnectiont() {
        return this.httpConnection;
    }

    public HttpsURLConnection getHttpsConnectiont() {
        return this.httpsConnection;
    }

    public HttpGet addHead(String name, String value) {
        Param p = new Param(name, value);
        this.heads.add(p);
        return this;
    }

    private void connect() throws IOException {
        int headlen = this.heads.size();
        if (this.httpConnection != null) {
            for (int i = 0; i < headlen; i++) {
                Param h = this.heads.get(i);
                this.httpConnection.addRequestProperty((String) h.getName(), (String) h.getValue());
            }
            //  this.httpConnection.connect();
        } else {
            for (int i = 0; i < headlen; i++) {
                Param h = this.heads.get(i);
                this.httpsConnection.addRequestProperty((String) h.getName(), (String) h.getValue());
            }
            // this.httpsConnection.connect();
        }

    }

    private InputStream getInputStream() throws IOException {
        InputStream inputStream;
        if (this.httpConnection != null) {
            this.httpConnection.connect();
            inputStream = this.httpConnection.getInputStream();
        } else {
            this.httpsConnection.connect();
            inputStream = this.httpsConnection.getInputStream();
        }
        return inputStream;

    }

    /**
     *
     */
    public String submit() throws IOException {
        this.connect();
        // connection.addRequestProperty("Cookie", "acc=0&lgn=Htv_admin;expires=2014-12-12 12:12:12");
        // connection.setRequestProperty("User-Agent", "Mozilla/6.0 (compatible; MSIE 7.0; Windows 2000)");
        //  HttpURLConnection.setFollowRedirects(true);
        //  connection.setConnectTimeout(5000);
        //  connection.setUseCaches(false);
        //  connection.setAllowUserInteraction(false);
        //  connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //  connection.setRequestProperty("Accept-Language", "zh-cn");
        //   connection.setRequestProperty("Cache-Control", "no-cache");
        //  connection.setRequestProperty("Pragma", "no-cache");
        // connection.setRequestProperty("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
        //  connection.setRequestProperty("Connection", "keep-alive");
        //  connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //  connection.setRequestProperty("Content-Length", String.valueOf(postData.length()));
        //  connection.setUseCaches(false);
        InputStream inputStream = this.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(this.encoding)));
        String line;
        StringBuilder ret = new StringBuilder();
        while ((line = in.readLine()) != null) {
            ret.append(line);
            ret.append("\r\n");
        }
        in.close();

        return ret.toString();
    }

    public InputStream submit1() throws IOException {
        this.connect();
        return this.getInputStream();
    }

    public void close() {
        if (this.httpConnection != null) {
            // 关闭连接
            this.httpConnection.disconnect();
        } else {
            this.httpsConnection.disconnect();
        }
    }

    public static void main(String[] argv)
            throws Exception {

        HttpGet p = new HttpGet("http://wap.zdic.net/s/?q=汉");

        p.setEncoding("utf-8");
        System.out.println(p.submit());
        /*
         String url = "http://www.19wang.com.cn/";
         HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
         connection.setRequestMethod("GET");
         connection.setDoOutput(true);
         connection.setDoInput(true);
         InputStream os = connection.getInputStream();
         BufferedReader b = new BufferedReader(new InputStreamReader(os, Charset.forName("gbk")));
         String s = "", line;
         ;
         while ((line = b.readLine()) != null) {
         // s = s + line + "\\n\\r";
         System.out.println(7);
         }

         //  LoggerManager.debug(b.readLine());
         b.close();
         connection.disconnect();
         */
    }
}
