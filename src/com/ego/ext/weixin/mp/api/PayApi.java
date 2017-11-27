/**
 * 微信公众平台开发模式(JAVA) SDK (c) http://www.jeasyuicn.com/wechat
 */
package com.ego.ext.weixin.mp.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 支付相关方法
 *
 *
 */
public class PayApi {

    public static final String deliver_notify_URI = "https://api.weixin.qq.com/pay/delivernotify?access_token={0}";
    public static final String payfeed_back_update_URI = "https://api.weixin.qq.com/payfeedback/update?access_token={0}";
    public static final String order_query_URI = "https://api.weixin.qq.com/pay/orderquery?access_token={0}";

    /**
     * 参与 paySign 签名的字段包括：appid、timestamp、noncestr、package 以及 appkey。 这里
     * signType 并不参与签名微信的Package参数
     *
     * @param partnerKey
     * @param partnerId
     * @param notifyUrl
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getPackage(String partnerKey, String partnerId, String notifyUrl, Map<String, String> params) throws UnsupportedEncodingException {
        //  String partnerKey = Config.get("partnerKey");
        // String partnerId = Config.get("partnerId");
        // String notifyUrl = Config.get("notify_url");
        // 公共参数
        params.put("bank_type", "WX");
        params.put("attach", "yongle");
        params.put("partner", partnerId);
        params.put("notify_url", notifyUrl);
        params.put("input_charset", "UTF-8");
        return packageSign(params, partnerKey);
    }

    /**
     * 构造签名
     *
     * @param params
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String createSign(Map<String, String> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = value.toString();
            }
            if (encode) {
                temp.append(URLEncoder.encode(valueString, "UTF-8"));
            } else {
                temp.append(valueString);
            }
        }
        return temp.toString();
    }

    /**
     * 构造package, 这是我见到的最草蛋的加密，尼玛文档还有错
     *
     * @param params
     * @param paternerKey
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String packageSign(Map<String, String> params, String paternerKey) throws UnsupportedEncodingException {
        String string1 = createSign(params, false);
        String stringSignTemp = string1 + "&key=" + paternerKey;
        String signValue = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        String string2 = createSign(params, true);
        return string2 + "&sign=" + signValue;
    }

    /**
     * 支付签名
     *
     * @param timestamp
     * @param noncestr
     * @param packages
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String paySign(String timestamp, String noncestr, String packages, String AppId, String paySignKey) throws UnsupportedEncodingException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put("appid", AppId);
        paras.put("timestamp", timestamp);
        paras.put("noncestr", noncestr);
        paras.put("package", packages);
        paras.put("appkey", paySignKey);
        // appid、timestamp、noncestr、package 以及 appkey。
        String string1 = createSign(paras, false);
        String paySign = DigestUtils.shaHex(string1);
        return paySign;
    }

}
