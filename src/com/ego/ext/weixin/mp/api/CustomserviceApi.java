package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.model.Customservice;
import com.ego.ext.weixin.mp.model.CustomserviceStatus;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客服接口
 *
 * @see http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html
 * @description
 * 当用户主动发消息给公众号的时候（包括发送信息、点击自定义菜单、订阅事件、扫描二维码事件、支付成功事件、用户维权），微信将会把消息数据推送给开发者，开发者在一段时间内（目前修改为48小时）可以调用客服消息接口，通过POST一个JSON数据包来发送消息给普通用户，在48小时内不限制发送次数。此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
 *
 * 为了帮助公众号使用不同的客服身份服务不同的用户群体，客服接口进行了升级，开发者可以管理客服账号，并设置客服账号的头像和昵称。该能力针对所有拥有客服接口权限的公众号开放。
 */
public class CustomserviceApi {

    public static final String CUSTOM_KFACCOUNT_ADD_URL = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token={0}";
    public static final String CUSTOM_KFACCOUNT_UPDATE_URL = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token={0}";
    public static final String CUSTOM_KFACCOUNT_DEL_URL = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token={0}";
    public static final String CUSTOM_KFACCOUNT_UPLOADHEADIMG_URL = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token={0}&kf_account={1}";
    public static final String CUSTOM_SERVICE_GETKFLIST_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token={0}";
    public static final String CUSTOM_GET_ONLINE_KF_LIST_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token={0}";

    /**
     * 添加客服帐号
     *
     * @param accessToken
     * @param kf_account 完整客服账号，格式为：账号前缀@公众号微信号
     * @param nickname 客服昵称
     * @param password 非必须。
     * 客服账号登录密码，格式为密码明文的32位加密MD5值。该密码仅用于在公众平台官网的多客服功能中使用，若不使用多客服功能，则不必设置密码
     * @return
     * @throws WxException
     */
    public static Result add(String accessToken, String kf_account, String nickname, String password) throws WxException {
        try {
            Map<String, String> json = new HashMap();
            json.put("kf_account", kf_account);
            json.put("nickname", nickname);
            json.put("password", password);
            String jsonStr = UtilHttp.post(MessageFormat.format(CustomserviceApi.CUSTOM_KFACCOUNT_ADD_URL, accessToken), true, JSON.toJSONString(json));
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    public static Result add(String accessToken, Customservice customservice) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(CustomserviceApi.CUSTOM_KFACCOUNT_ADD_URL, accessToken), true, customservice.toJson());
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 修改客服帐号
     *
     * @param accessToken
     * @param kf_account
     * @param nickname
     * @param password
     * @return
     * @throws WxException
     */
    public static Result update(String accessToken, String kf_account, String nickname, String password) throws WxException {
        try {
            Map<String, String> json = new HashMap();
            json.put("kf_account", kf_account);
            json.put("nickname", nickname);
            json.put("password", password);
            String jsonStr = UtilHttp.post(MessageFormat.format(CustomserviceApi.CUSTOM_KFACCOUNT_UPDATE_URL, accessToken), true, JSON.toJSONString(json));
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    public static Result update(String accessToken, Customservice customservice) throws WxException {
        try {

            String jsonStr = UtilHttp.post(MessageFormat.format(CustomserviceApi.CUSTOM_KFACCOUNT_UPDATE_URL, accessToken), true, customservice.toJson());
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 删除客服帐号
     *
     * @param accessToken
     * @param kf_account
     * @param nickname
     * @param password
     * @return
     * @throws WxException
     */
    public static Result delete(String accessToken, Customservice customservice) throws WxException {
        try {

            String jsonStr = UtilHttp.post(MessageFormat.format(CustomserviceApi.CUSTOM_KFACCOUNT_DEL_URL, accessToken), true, customservice.toJson());
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    public static Result delete(String accessToken, String kf_account, String nickname, String password) throws WxException {
        try {
            Map<String, String> json = new HashMap();
            json.put("kf_account", kf_account);
            json.put("nickname", nickname);
            json.put("password", password);
            String jsonStr = UtilHttp.post(MessageFormat.format(CustomserviceApi.CUSTOM_KFACCOUNT_DEL_URL, accessToken), true, JSON.toJSONString(json));
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 获取所有客服账号.开发者通过本接口，获取公众号中所设置的客服基本信息，包括客服工号、客服昵称、客服登录账号。
     *
     * @param accessToken
     * @param result
     * @return 返回说明（正确时的JSON返回结果）：
     *
     * {
     * "kf_list": [ { "kf_account": "test1@test", "kf_nick": "ntest1", "kf_id":
     * "1001" "kf_headimg": "
     * http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjfUS8Ym0GSaLic0FD3vN0V8PILcibEGb2fPfEOmw/0"
     * }, { "kf_account": "test2@test", "kf_nick": "ntest2", "kf_id": "1002"
     * "kf_headimg": "
     * http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjfUS8Ym0GSaLic0FD3vN0V8PILcibEGb2fPfEOmw
     * /0" }, { "kf_account": "test3@test", "kf_nick": "ntest3", "kf_id": "1003"
     * "kf_headimg": "
     * http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjfUS8Ym0GSaLic0FD3vN0V8PILcibEGb2fPfEOmw
     * /0" } ] }
     * @throws WxException
     */
    public static List<Customservice> list(String accessToken, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(CustomserviceApi.CUSTOM_SERVICE_GETKFLIST_URL, accessToken), true);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return new ArrayList();
            } else {
                Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
                Object kf_list = map.get("kf_list");
                return JSON.parseArray(jsonStr, Customservice.class);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 获取在线客服接待信息
     *
     * 开发者通过本接口，根据AppID获取公众号中当前在线的客服的接待信息，包括客服工号、客服登录账号、客服在线状态（手机在线、PC客户端在线、手机和PC客户端全都在线）、客服自动接入最大值、客服当前接待客户数。开发者利用本接口提供的信息，结合客服基本信息，可以开发例如“指定客服接待”等功能；结合会话记录，可以开发”在线客服实时服务质量监控“等功能。
     *
     * @param accessToken
     * @param result
     * @return
     * @throws WxException
     */
    public static CustomserviceStatus onlineList(String accessToken, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(CustomserviceApi.CUSTOM_GET_ONLINE_KF_LIST_URL, accessToken), true);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
                String kf_online_list = map.get("kf_online_list");
                return CustomserviceStatus.fromJson(kf_online_list);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 开发者可调用本接口来上传图片作为客服人员的头像，头像图片文件必须是jpg格式，推荐使用640*640大小的图片以达到最佳效果
     *
     * @param accessToken
     * @param kf_account
     * @param file
     * @see
     * http://mp.weixin.qq.com/wiki/9/6fff6f191ef92c126b043ada035cc935.html#.E6.B7.BB.E5.8A.A0.E5.AE.A2.E6.9C.8D.E8.B4.A6.E5.8F.B7
     * @return
     * @throws WxException
     */
    public static Result setHeadImg(String accessToken, String kf_account, File file) throws WxException {
        try {
            String url = MessageFormat.format(CustomserviceApi.CUSTOM_KFACCOUNT_UPLOADHEADIMG_URL, accessToken, kf_account);
            String jsonStr = UtilHttp.upload(url, file);
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

}
