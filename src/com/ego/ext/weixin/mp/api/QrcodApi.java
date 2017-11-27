package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.ego.core.util.UtilURL;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.model.Qrcod;
import com.ego.ext.weixin.mp.model.result.LongUrl2shortUrlResult;
import com.ego.ext.weixin.mp.model.result.TicketCreateResult;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建二维码
 *
 * 获取带参数的二维码的过程包括两步，首先创建二维码ticket，然后凭借ticket到指定URL换取二维码
 *
 * @see http://mp.weixin.qq.com/wiki/18/28fc21e7ed87bec960651f0ce873ef8a.html
 */
public class QrcodApi {

    public static final String QRCOD_CREATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={0}";
    public static final String QRCOD_SHOW = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket={0}";
    public static final String SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token={0}";

    /**
     * 第一步、创建二维码ticket
     *
     * 1创建临时二维码ticket
     *
     * @param accessToken
     * @param expireSeconds 最大不超过1800
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param result
     * @return 错误返回null。正确的Json返回结果:
     *
     * {"ticket":"gQG28DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0FuWC1DNmZuVEhvMVp4NDNMRnNRAAIEesLvUQMECAcAAA==","expire_seconds":1800}
     * @throws com.ego.ext.weixin.common.WxException
     *
     */
    public static TicketCreateResult createTicket(String accessToken, int expireSeconds, int sceneId, Result... result) throws WxException {
        try {
            Qrcod q = new Qrcod()
                    .setAction_name(Qrcod.QrcodType.QR_SCENE.getType())
                    .setExpire_seconds(expireSeconds)
                    .setAction_info(new Qrcod.ActionInfo()
                            .setScene(new Qrcod.Scene()
                                    .setScene_id(sceneId)));
            String jsonStr = UtilHttp.post(MessageFormat.format(QrcodApi.QRCOD_CREATE, accessToken), true, q.toJson());
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return TicketCreateResult.fromJson(jsonStr);
            }
            /*
             Map<String, Object> params = new HashMap<String, Object>();
             Map<String, Object> actionInfo = new HashMap<String, Object>();
             Map<String, Object> scene = new HashMap<String, Object>();
             params.put("expire_seconds", expireSeconds);
             params.put("action_name", "QR_SCENE");
             scene.put("scene_id", sceneId);
             actionInfo.put("scene", scene);
             params.put("action_info", actionInfo);
             String post = JSONObject.toJSONString(params);
             String jsonStr = UtilHttp.post(QRCOD_CREATE.concat(accessToken), true, post);
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
     * 第一步、创建二维码ticket
     *
     * 2创建永久二维码ticket.通过整型类型场景值ID
     *
     * @param accessToken
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param result
     * @return 正确的Json返回结果:
     *
     * {"ticket":"gQG28DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0FuWC1DNmZuVEhvMVp4NDNMRnNRAAIEesLvUQMECAcAAA==","expire_seconds":1800}
     * @throws com.ego.ext.weixin.common.WxException
     *
     */
    public static TicketCreateResult createLimitTicket(String accessToken, int sceneId, Result... result) throws WxException {
        try {
            Qrcod q = new Qrcod()
                    .setAction_name(Qrcod.QrcodType.QR_LIMIT_SCENE.getType())
                    .setAction_info(new Qrcod.ActionInfo()
                            .setScene(new Qrcod.Scene()
                                    .setScene_id(sceneId)));
            String jsonStr = UtilHttp.post(MessageFormat.format(QrcodApi.QRCOD_CREATE, accessToken), true, q.toJson());
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return TicketCreateResult.fromJson(jsonStr);
            }
            /*
             Map<String, Object> params = new HashMap<String, Object>();
             Map<String, Object> actionInfo = new HashMap<String, Object>();
             Map<String, Object> scene = new HashMap<String, Object>();
             params.put("action_name", "QR_LIMIT_SCENE");
             scene.put("scene_id", sceneId);
             actionInfo.put("scene", scene);
             params.put("action_info", actionInfo);
             String post = JSONObject.toJSONString(params);
             String jsonStr = UtilHttp.post(QRCOD_CREATE.concat(accessToken), true, post);
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
     * 第一步、创建二维码ticket
     *
     * 2创建永久二维码ticket.通过字符串类型场景值ID
     *
     * @param accessToken
     * @param scene_str
     * @param result
     * @return
     * @throws WxException
     */
    public static TicketCreateResult createLimitTicket(String accessToken, String scene_str, Result... result) throws WxException {
        try {
            Qrcod q = new Qrcod()
                    .setAction_name(Qrcod.QrcodType.QR_LIMIT_STR_SCENE.getType())
                    .setAction_info(new Qrcod.ActionInfo()
                            .setScene(new Qrcod.Scene()
                                    .setScene_str(scene_str)));
            String jsonStr = UtilHttp.post(MessageFormat.format(QrcodApi.QRCOD_CREATE, accessToken), true, q.toJson());
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return TicketCreateResult.fromJson(jsonStr);
            }

        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 第二步、通过ticket换取二维码，即获取查看二维码链接
     *
     * @param ticket 第一步获取到的二维码ticket提醒：TICKET在本方法中已进行UrlEncode，不再需要编码
     * @param result
     * @return ticket正确情况下，http 返回码是200，是一张图片，可以直接展示或者下载。
     *
     * HTTP头（示例）如下： Accept-Ranges:bytes Cache-control:max-age=604800
     * Connection:keep-alive Content-Length:28026 Content-Type:image/jpg
     * Date:Wed, 16 Oct 2013 06:37:10 GMT Expires:Wed, 23 Oct 2013 14:37:10
     * +0800 Server:nginx/1.4.1
     *
     * 错误情况下（如ticket非法）返回HTTP错误码404。
     * @throws com.ego.ext.weixin.common.WxException
     */
    public static String getQrcodePic(String ticket, Result... result) throws WxException {
        try {
            String str = UtilHttp.get(MessageFormat.format(QrcodApi.QRCOD_SHOW, UtilURL.encode(ticket, null)), true);
            return str;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 将一条长链接转成短链接。
     *
     * 主要使用场景：
     * 开发者用于生成二维码的原链接（商品、支付二维码等）太长导致扫码速度和成功率下降，将原长链接通过此接口转成短链接再生成二维码将大大提升扫码速度和成功率。
     *
     * @param accessToken
     * @param long_url 需要转换的长链接，支持http://、https://、weixin://wxpay 格式的url
     * @see
     * http://mp.weixin.qq.com/wiki/10/165c9b15eddcfbd8699ac12b0bd89ae6.html
     * @return
     * @throws WxException
     */
    public static LongUrl2shortUrlResult getShortUrl(String accessToken, String long_url) throws WxException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("action", "long2short");
            params.put("long_url", long_url);
            String jsonStr = UtilHttp.post(MessageFormat.format(QrcodApi.SHORT_URL, accessToken), true, JSON.toJSONString(params));
            return LongUrl2shortUrlResult.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

}
