
package com.ego.ext.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ego.core.util.UtilValidate;
import com.ego.ext.weixin.common.model.Attachment;
import com.ego.ext.weixin.common.util.Tools;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.api.CustomserviceApi;
import com.ego.ext.weixin.mp.api.GroupApi;
import com.ego.ext.weixin.mp.api.MenuApi;
import com.ego.ext.weixin.mp.api.MessageApi;
import com.ego.ext.weixin.mp.api.QrcodApi;
import com.ego.ext.weixin.mp.api.UserApi;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * 微信公众号常用的API
 *
 */
public class WXMP {

    //private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
    //获取微信服务器IP地址
    private static final String GETCALLBACKIP = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token={0}";
    private static final String PAYFEEDBACK_URL = "https://api.weixin.qq.com/payfeedback/update";
    // private static final String DEFAULT_HANDLER = "com.gson.inf.DefaultMessageProcessingHandlerImpl";
    //private static final String DOWNLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";
  

    /**
     * 消息操作接口
     */
    public static final MessageApi message = new MessageApi();
    /**
     * 菜单操作接口
     */
    public static final MenuApi menu = new MenuApi();
    /**
     * 用户操作接口
     */
    public static final UserApi user = new UserApi();
    /**
     * 分组操作接口
     */
    public static final GroupApi group = new GroupApi();

    /**
     * 二维码操作接口
     */
    public static final QrcodApi qrcod = new QrcodApi();
    /**
     * 公众号客服接口
     */
    public static final CustomserviceApi customservice = new CustomserviceApi();

    /**
     * 获取access_token
     * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。正常情况下access_token有效期为7200秒，
     *
     * 重复获取将导致上次获取的access_token失效。由于获取access_token的api调用次数非常有限，
     *
     * 建议开发者全局存储与更新access_token，频繁刷新access_token会导致api调用受限，影响自身业务。
     *
     * @param appid
     * @param appSecret
     * @param errorRet
     * 错误时微信会返回错误码等信息，JSON数据包示例如下（该示例为AppID无效错误）:{"errcode":40013,"errmsg":"invalid
     * appid"}
     * @return 错误返回null
     * @throws Exception
     * @see http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
     *
     *
     */
    public static String getAccessToken(String appid, String appSecret, Map... errorRet) throws Exception {
        //String appid = Config.get("AppId");
        //String secret = Config.get("AppSecret");
        String url = MessageFormat.format(ACCESS_TOKEN_URL, appid, appSecret);
        // String jsonStr = UtilHttp.get(ACCESS_TOKEN_URL.concat("&appid=") + appid + "&secret=" + secret, true);
        String jsonStr = UtilHttp.get(url, true);
        Map<String, Object> map = JSONObject.parseObject(jsonStr);
        Object ret = map.get("access_token");
        if (ret != null) {
            return ret.toString();
        } else {
            if (errorRet != null && errorRet.length > 0) {
                errorRet[0] = map;
            }
            return null;
        }

    }

    /**
     * 获取微信服务器IP地址
     *
     * @param access_token
     * @param errorRet
     * @see http://mp.weixin.qq.com/wiki/index.php?title=获取微信服务器IP地址
     * @return
     * @throws Exception
     */
    public static List<String> getCallbackIp(String access_token, Map... errorRet) throws Exception {
        String url = MessageFormat.format(GETCALLBACKIP, access_token);
        String jsonStr = UtilHttp.get(url, true);
        Map<String, Object> map = JSONObject.parseObject(jsonStr);
        Object ret = map.get("ip_list");
        if (ret != null) {
            return JSONObject.parseArray(ret.toString(), String.class);
        } else {
            if (errorRet != null && errorRet.length > 0) {
                errorRet[0] = map;
            }
            return null;
        }

    }

    /**
     * 支付反馈
     *
     * @param openid
     * @param feedbackid
     * @return
     * @throws Exception
     */
    public static boolean payfeedback(String appid, String appSecret, String openid, String feedbackid) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String accessToken = getAccessToken(appid, appSecret);
        map.put("access_token", accessToken);
        map.put("openid", openid);
        map.put("feedbackid", feedbackid);
        String jsonStr = UtilHttp.get(PAYFEEDBACK_URL, true, map);
        Map<String, Object> jsonMap = JSONObject.parseObject(jsonStr);
        return "0".equals(jsonMap.get("errcode").toString());
    }

    /**
     * 签名检查
     *
     * @param token Token由开发者任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）。
     * @param signature
     * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp
     * @param nonce
     * @return
     */
    public static Boolean checkSignature(String signature, String token, String timestamp, String nonce) {
        return Tools.checkSignature(signature, token, timestamp, nonce);
    }

    /**
     * 判断是否来自微信, 5.0 之后的支持微信支付
     *
     * @param request
     * @return
     */
    public static boolean isWeiXin5(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (UtilValidate.isNotEmpty(userAgent)) {
            Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
            Matcher m = p.matcher(userAgent);
            String version = null;
            if (m.find()) {
                version = m.group(1);
            }
            return (null != version && Integer.valueOf(version) >= 5);
        }
        return false;
    }

    public static boolean isWeiXin(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (UtilValidate.isNotEmpty(userAgent)) {
            Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
            Matcher m = p.matcher(userAgent);
            return m.matches();
        }
        return false;
    }


}
