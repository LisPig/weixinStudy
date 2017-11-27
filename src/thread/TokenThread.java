package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pojo.Token;

import util.CommonUtil;
import util.TokenUtil;

public class TokenThread implements Runnable {
		private static Logger log=LoggerFactory.getLogger(TokenThread.class);
		//第三方用户唯一凭证
		public static String appid="";
		//唯一凭证密钥
		public static String appsecret="";
		public static Token accessToken=null;
	@Override
	public void run() {
		while(true){
			try {
				accessToken=CommonUtil.getToken(appid, appsecret);
				if(null !=accessToken){
					//调用存储到数据库
					TokenUtil.saveToken(accessToken);
					log.info("获取access_token成功，有效时长{}秒 token:{}",accessToken.getExpiresIn(),accessToken.getAccessToken());
					//休眠7000秒
					Thread.sleep((accessToken.getExpiresIn()-200)*1000);
				}else{
					//如果access_token为空，60秒后再获取
					Thread.sleep(6*1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60*1000);
				} catch (InterruptedException e2) {
					log.error("{}",e2);
				}
				log.error("{}",e);
			}
		}
	}

}
