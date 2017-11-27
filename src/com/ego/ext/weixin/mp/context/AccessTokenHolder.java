package com.ego.ext.weixin.mp.context;

import com.ego.core.lang.cache.CacheManager;
import com.ego.core.util.UtilString;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.context.WxConfigHolder;
import com.ego.ext.weixin.common.model.AccessToken;
import com.ego.ext.weixin.common.model.WxConfig;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.mp.api.BaseApi;
import com.ego.ext.weixin.mp.api.Oauth2Api;
import com.ego.ext.weixin.mp.model.result.Oauth2AccessToken;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class AccessTokenHolder {

	// private static final ThreadLocal<AccessToken>
	// accessTokenThreadLocalHolder = new ThreadLocal();
	/*
	 * public static void setAccessToken(AccessToken accessToken) {
	 * accessTokenThreadLocalHolder.set(accessToken); }
	 */
	public static AccessToken getAccessToken() {
		String appid = WxConfigHolder.getConfig().getAppId();

		AccessToken token = (AccessToken) CacheManager.get(appid);
		// 为空等他获取token后返回
		if (token == null) {
			return refreshAccessToken();
			// 如果失效了
		} else if (!token.isAvailable()) {
			// 异步刷新token，并返回老的token
			return refreshAccessToken();
		} else {
			return token;
		}

	}

	/**
	 * 
	 * 强制更新 access token 值
	 * 
	 */
	public static synchronized AccessToken refreshAccessToken() {
		WxConfig cfg = WxConfigHolder.getConfig();
		String appId = cfg.getAppId();
		String appSecret = cfg.getSecret();
		AccessToken token = null;
		for (int i = 0; i < 3; i++) {
			try {
				// 最多三次请求
				token = BaseApi.getAccessToken(appId, appSecret, null);
			} catch (WxException ex) {
				continue;
			}
			if (token != null && token.isAvailable()) {
				break;
			}
		}

		// 三次请求如果仍然返回了不可用的 access token 仍然 put 进去，便于上层通过 AccessToken 中的属性判断底层的情况
		CacheManager.put(appId, token);

		return token;
	}

	public static Oauth2AccessToken getOauth2AccessToken(String code,
			Result... result) throws WxException {

		WxConfig cfg = WxConfigHolder.getConfig();
		String appId = cfg.getAppId();
		String appSecret = cfg.getSecret();
		String key = "oauth2_" + appId + "_" + UtilString.trim(code);
		Oauth2AccessToken token = (Oauth2AccessToken) CacheManager.get(key);
		Oauth2Api outh2 = new Oauth2Api(appId, appSecret);
		Result ret = Result.getInstance();
		// 为空等他获取token后返回
		if (token == null) {
			for (int i = 0; i < 3; i++) {
				// 最多三次请求
				token = outh2.getAccessToken(code, ret);
				if (token != null && ret.isSuccess()) {
					CacheManager.put(key, token);
					break;
				}
			}
			result[0].copy(ret);
			return token;
			// 如果失效了
		} else if (!token.isAvailable()) {
			for (int i = 0; i < 3; i++) {
				// 最多三次请求
				token = outh2.getRefreshToken(token.getRefresh_token(), ret);
				if (token != null && ret.isSuccess()) {
					CacheManager.put(key, token);
					break;
				}
			}
			result[0].copy(ret);
			return token;
		} else {
			result[0].copy(ret);
			return token;
		}

	}
	/**
	 * 移除
	 */
	/*
	 * public static void remove() { accessTokenThreadLocalHolder.remove(); }
	 */
}
