package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;



import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.AccessToken;
import pojo.WeixinUserInfo;

public class WeixinUtil {
	
	private static Logger log=LoggerFactory.getLogger(WeixinUtil.class);
	
	//获取access_token的接口地址
	public final static String access_token_url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx53777eaf2019be5f&secret=07b03dfb59ad277b85b305f8dba2aaaf";
	
	//菜单创建post
	public static String menu_create_url= "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
//	public static int createMenu(Menu menu,String accessToken){
//	int result=0;
//	//拼装创建菜单的url
//	String url=menu_create_url.replace("ACCESS_TOKEN", accessToken);
//	//将菜单对象转换为json字符串
//	String jsonMenu=JSONObject.fromObject(menu).toString();
//	//调用接口创建菜单
//	JSONObject jsonObject=httpRequest(url,"POST",jsonMenu);
//	if(null!=jsonObject){
//		if(0!=jsonObject.getInt("errcode")){
//			result=jsonObject.getInt("errcode");
//			log.error("创建菜单失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
//		}
//	}
//	return result;
//	}
	
	/**
	 * 获取access_token
	 * @param appid
	 * @param appsecret
	 * @return
	 */
	public static AccessToken getAccessToken(String appid,String appsecret){
		AccessToken accessToken=null;
		
		String requestUrl=access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject=httpRequest(requestUrl,"GET",null);
		//如果请求成功
		if(null!=jsonObject){
			try{
				accessToken=new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			}catch(JSONException e){
				accessToken=null;
				//获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"),jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	
	public static JSONObject httpRequest(String requestUrl,String requestMethod,String outputStr){
		JSONObject jsonObject=null;
		StringBuffer buffer=new StringBuffer();
		try{
			//创建SSLContext对象，并使用我们制定的信任管理器初始化
			TrustManager[] tm={new MyX509TrustManager()};
			SSLContext sslContext=SSLContext.getInstance("SSL","SunJSSE");
			sslContext.init(null, (TrustManager[]) tm, new java.security.SecureRandom());
			//从上述sslcontext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf=sslContext.getSocketFactory();
			
			URL url=new URL(requestUrl);
			HttpsURLConnection httpUrlConn=(HttpsURLConnection)url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			
			//设置请求方式
			httpUrlConn.setRequestMethod(requestMethod);
			
			if("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			
			//当有数据需要提交时
			if(null !=outputStr){
				OutputStream outputStream=httpUrlConn.getOutputStream();
				//注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			
			//将返回的输入流转换成字符串
			InputStream inputStream=httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			
			String str=null;
			while((str=bufferedReader.readLine())!=null){
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			//释放资源
			inputStream.close();
			inputStream=null;
			httpUrlConn.disconnect();
			jsonObject=JSONObject.fromObject(buffer.toString());
		}catch(ConnectException ce){
			log.error("Weixin server connection timed out.");
		}catch (Exception e) {
			log.error("https request error:{}",e);
		}
		return jsonObject;
		
	}
	
	/**
	 * 获取用户信息
	 * 
	 */
	public static WeixinUserInfo getUserInfo(String accessToken,String openId){
		WeixinUserInfo weixinUserInfo=null;
		
		//拼接请求地址
		String requestUrl="https://api.weixin.qq.com/cgi-bin/user/info?access_token=3mEA_upHd-UY8mnGxHxXCef83XgcAws47Q-WdDJuWs77RzZZRcN54w6QKWmumlaPKjPA4niSZqEqGhWhH0Ruk4QS8m3hHxu1qZNCvjRuKn4bC98lelemnEtr6ueKhQRoZGYeAIACJB&openid=oTCM70d2FSZWgTeoLQYBwn9t1eJg";
		requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		//获取用户信息
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		if(null!=jsonObject){
			try {
				weixinUserInfo=new WeixinUserInfo();
				//用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				//关注状态
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				//用户关注时间
				
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				//昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				//用户的性别
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				//用户所在的国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				//用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				//用户的语言
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				//用户的头像
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				if(0==weixinUserInfo.getSubscribe()){
					log.error("用户{}已取消关注",weixinUserInfo.getOpenId());
				}else{
					int errorCode=jsonObject.getInt("errcode");
					String errorMsg=jsonObject.getString("errcode");
					log.error("获取用户信息失败 errcode:{} errmsg:{}",errorCode,errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

}
