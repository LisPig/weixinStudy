/**
 * 微信公众平台开发模式(JAVA) SDK http://www.jeasyuicn.com/wechat
 */
package com.ego.ext.weixin.mp.api;


import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.model.User;
import com.ego.ext.weixin.mp.model.result.Oauth2AccessToken;
import java.text.MessageFormat;

/**
 * 微信Oauth.验证授权。网页授权获取用户基本信息
 *
 * 关于网页授权回调域名的说明
 *
 * 1、在微信公众号请求用户网页授权之前，开发者需要先到公众平台官网中的开发者中心页配置授权回调域名。请注意，这里填写的是域名（是一个字符串），而不是URL，因此请勿加http://等协议头；
 * 2、授权回调域名配置规范为全域名，比如需要网页授权的域名为：www.qq.com，配置以后此域名下面的页面http://www.qq.com/music.html
 * 、 http://www.qq.com/login.html 都可以进行OAuth2.0鉴权。但http://pay.qq.com 、
 * http://music.qq.com 、 http://qq.com无法进行OAuth2.0鉴权
 * 3、如果公众号登录授权给了第三方开发者来进行管理，则不必做任何设置，由第三方代替公众号实现网页授权即可。
 * 
*
 * 关于网页授权access_token和普通access_token的区别
 *
 * 1、微信网页授权是通过OAuth2.0机制实现的，在用户授权给公众号后，公众号可以获取到一个网页授权特有的接口调用凭证（网页授权access_token），通过网页授权access_token可以进行授权后接口调用，如获取用户基本信息；
 * 2、其他微信接口，需要通过基础支持中的“获取access_token”接口来获取到的普通access_token调用。
 *
 * @author
 * @see http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
 * @aee http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
 */
public class Oauth2Api {

    public static final String DEFAULT_LANGUAGE = "zh_CN";
    public static final String AUTHORIZE_URI = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type={2}&scope={3}&state={4}#wechat_redirect";
    public static final String ACCESS_TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type={3}";
    public static final String REFRESH_TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid={0}&grant_type={1}&refresh_token={2}";
    public static final String USER_INF_URI = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang={2}";

    private String appid;
    private String secret;
    private String language = "zh_CN";
/*
    public Oauth2Api() {
        super();
        //this.appid = Config.get("AppId");
        // this.secret = Config.get("AppSecret");
        //this.language = Config.get("language");
    }
*/
    public Oauth2Api(String appid, String secret) {
        super();
        this.appid = appid;
        this.secret = secret;
    }

    /**
     * 第一步：构造授权连接。引导关注者打开页面进行授权
     *
     * 如果用户同意授权，页面将跳转至
     * language/?code=CODE&state=STATE。若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数redirect_uri?state=STATE
     *
     * code说明 ：
     * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     *
     * @param appid 公众号的唯一标识
     * @param redirect_uri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
     * @param scope 应用授权作用域，snsapi_base
     * （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
     * （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值
     * @see
     * http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
     * @return 构造的授权URL
     */
    public static String getAuthorizeUrl(String appid, String redirect_uri, String scope, String state) {
        //Assert.notEmpty(appid, "必填参数appid");
       // Assert.notEmpty(redirect_uri, "必填参数redirect_uri");
       // Assert.notEmpty(scope, "必填参数scope");
        return MessageFormat.format(Oauth2Api.AUTHORIZE_URI, appid, redirect_uri, "code", scope, state);
    }

    /**
     *
     * 第二步：通过code换取网页授权access_token.请注意，这里通过code换取的网页授权access_token,与基础支持中的access_token不同
     *
     * .如果网页授权的作用域为snsapi_base，则本步骤中获取到网页授权access_token的同时，也获取到了openid，snsapi_base式的网页授权流程即到此为止。
     *
     *
     *
     * 通过code 换取 access_token
     *
     * @param code
     * @param result
     * @return 正确时返回的JSON数据包如下：
     *
     * {
     * "access_token":"ACCESS_TOKEN", "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN", "openid":"OPENID", "scope":"SCOPE" }
     *
     * 错误时微信会返回JSON数据包如下（示例为Code无效错误）:
     *
     * {"errcode":40029,"errmsg":"invalid code"}
     * @throws com.ego.ext.weixin.common.WxException
     * @see
     * http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
     */
    public Oauth2AccessToken getAccessToken(String code, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(Oauth2Api.ACCESS_TOKEN_URI, this.getAppid(), this.getSecret(), code, "authorization_code"), true);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return Oauth2AccessToken.fromJson(jsonStr);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * 第三步：如果需要，开发者可以刷新网页授权access_token，避免过期
     *
     *
     * 刷新 access_token
     *
     * @param refreshToken 填写通过access_token获取到的refresh_token参数
     * @param result
     * @return 正确时返回的JSON数据包如下：
     *
     * {
     * "access_token":"ACCESS_TOKEN", "expires_in":7200,
     * "refresh_token":"REFRESH_TOKEN", "openid":"OPENID", "scope":"SCOPE" }
     * @throws com.ego.ext.weixin.common.WxException
     * @see
     * http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息#.E7.AC.AC.E4.B8.80.E6.AD.A5.EF.BC.9A.E7.94.A8.E6.88.B7.E5.90.8C.E6.84.8F.E6.8E.88.E6.9D.83.EF.BC.8C.E8.8E.B7.E5.8F.96code
     */
    public Oauth2AccessToken getRefreshToken(String refreshToken, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(Oauth2Api.REFRESH_TOKEN_URI, this.getAppid(), "refresh_token", refreshToken), true);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return Oauth2AccessToken.fromJson(jsonStr);
            }
            /*
             Map<String, String> params = new HashMap<String, String>();
             params.put("appid", getAppid());
             params.put("grant_type", "refresh_token");
             params.put("refresh_token", refreshToken);
             String jsonStr = UtilHttp.get(Oauth2Api.REFRESH_TOKEN_URI, true);
             if (UtilValidate.isNotEmpty(jsonStr)) {
             return JSONObject.parseObject(jsonStr);
             }
             return null;
             */
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }

    }

    /**
     * 第四部：通过网页授权access_token和openid获取用户基本信息（支持UnionID机制）
     *
     * @param accessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     * @param openid 用户的唯一标识
     * @param result
     * @return
     * @throws WxException
     */
    public User getUserInfo(String accessToken, String openid, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(USER_INF_URI, accessToken, openid, this.language), true);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return User.fromJson(jsonStr);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    public String getAppid() {
        return appid;
    }

    /**
     * 公众号的唯一标识
     *
     * @param appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 公众号的appsecret
     *
     * @return
     */
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
