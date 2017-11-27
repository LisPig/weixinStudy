package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.AccessToken;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import java.text.MessageFormat;

/**
 * 基础接口
 *
 * @description
 *
 *
 */
public class BaseApi {

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

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
    public static AccessToken getAccessToken(String appid, String appSecret, Result... result) throws WxException {
        try {
            String url = MessageFormat.format(ACCESS_TOKEN_URL, appid, appSecret);
            // String jsonStr = UtilHttp.get(ACCESS_TOKEN_URL.concat("&appid=") + appid + "&secret=" + secret, true);
            String jsonStr = UtilHttp.get(url, true);
            Result ret = JSON.parseObject(jsonStr, Result.class);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return AccessToken.fromJson(jsonStr);
            }
        } catch (Exception ex) {
            throw new WxException(ex);
        }

    }

}
