package test;

import java.io.UnsupportedEncodingException;

import util.CommonUtil;

public class TestUrl {
	/**
	 * url编码（utf-8)
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source){
		String result=source;
		try {
			result=java.net.URLEncoder.encode(source,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 生成url编码
	 * @param args
	 */
	public static void main(String[] args){
		String source="https://trying.ngrok.xiaomiqiu.cn/OAuthServlet";
		System.out.println(urlEncodeUTF8(source));
	}
}
