/**
 * 微信公众平台开发模式(JAVA) SDK (c) 2012-2014 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ego.core.util.UtilValidate;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.model.User;
import com.ego.ext.weixin.mp.model.UserBatchGetInfo;
import com.ego.ext.weixin.mp.model.result.UserFollwerList;
import com.ego.ext.weixin.mp.model.result.UserInfoList;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户操作接口
 *
 * 在关注者与公众号产生消息交互后，公众号可获得关注者的OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的。
 * 对于不同公众号，同一用户的openid不同）。公众号可通过本接口来根据OpenID获取用户基本信息，包括昵称、头像、性别、所在城市、
 *
 * 语言和关注时间
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=获取用户基本信息
 */
public class UserApi {

    public static final String DEFAULT_LANGUAGE = "zh_CN";
    public static final String USER_INFO_URI = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang={2}";
    public static final String USER_GET_URI = "https://api.weixin.qq.com/cgi-bin/user/get?access_token={0}&next_openid={1}";
    public static final String USER_INFO_UPDATE_REMARK_URI = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token={0}";
    public static final String USER_INFO_BATCHGET_URI = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token={0}";

    /**
     * 设置备注名
     *
     * @param accessToken
     * @param openid 用户标识
     * @param remark 新的备注名，长度必须小于30字符
     * @return
     * @throws WxException
     */
    public static Result updateRemark(String accessToken, String openid, String remark) throws WxException {
        try {
            Map<String, Object> json = new HashMap();
            json.put("openid", openid);
            json.put("remark", "remark");
            String jsonStr = UtilHttp.post(MessageFormat.format(UserApi.USER_INFO_UPDATE_REMARK_URI, accessToken), true, JSON.toJSONString(json));
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 拉取用户信息
     *
     * @param accessToken
     * @param openid FromUserName就是OpenID
     * @param error
     * @param languages
     * @return 获取失败返回null，正常情况下，微信会返回下述JSON数据包给公众号：
     * @deprecated { "subscribe": 1, "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
     * "nickname": "Band", "sex": 1, "language": "zh_CN", "city": "广州",
     * "province": "广东", "country": "中国", "headimgurl":
     * "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     * "subscribe_time": 1382694957 }
     * @throws WxException
     *
     */
    public static User getUserInfo(String accessToken, String openid, Map<String, Object> error, String... languages) throws WxException {
        try {
            String lang = languages != null && languages.length > 0 ? languages[0] : DEFAULT_LANGUAGE;
            // Map<String, String> params = new HashMap<String, String>();
            // params.put("access_token", accessToken);
            // params.put("openid", openid);
            String jsonStr = UtilHttp.get(MessageFormat.format(USER_INFO_URI, accessToken, openid, lang), true);
            if (UtilValidate.isNotEmpty(jsonStr)) {
                JSONObject obj = JSONObject.parseObject(jsonStr);
                if (obj.get("errcode") != null && error != null) {
                    error.put("errcode", obj.get("errcode"));
                    return null;
                    //  throw new WxException(obj.getString("errmsg"));
                }
                User user = JSONObject.toJavaObject(obj, User.class);
                return user;
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 普通用户的标识，对当前公众号唯一
     *
     * @param accessToken
     * @param openid
     * @param lang
     * @param result
     * @return
     * @throws WxException
     */
    public static User get(String accessToken, String openid, String lang, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(USER_INFO_URI, accessToken, openid, UtilValidate.isEmpty(lang) ? DEFAULT_LANGUAGE : lang), true);
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

    /**
     * 获取帐号的关注者列表
     *
     * 公众号可通过本接口来获取帐号的关注者列表，关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）
     *
     * 组成。一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
     *
     * @param accessToken
     * @param next_openid
     * @param result
     * @return 正确时返回JSON数据包：
     *
     * {"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
     *
     * 当公众号关注者数量超过10000时，可通过填写next_openid的值，从而多次拉取列表的方式来满足需求。
     *
     * 具体而言，就是在调用接口时，将上一次调用得到的返回中的next_openid值，作为下一次调用中的next_openid值。
     * @throws com.ego.ext.weixin.common.WxException
     * @see http://mp.weixin.qq.com/wiki/index.php?title=获取关注者列表
     */
    public static UserFollwerList list(String accessToken, String next_openid, Result... result) throws WxException {

        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(USER_GET_URI, accessToken, next_openid), true);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return UserFollwerList.fromJson(jsonStr);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 批量获取用户基本信息
     *
     * @see
     * http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     * @param accessToken
     * @param userBatchGetInfo
     * @param result
     * @return
     * @throws WxException
     */
    public static UserInfoList batchGet(String accessToken, UserBatchGetInfo userBatchGetInfo, Result... result) throws WxException {

        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(UserApi.USER_INFO_BATCHGET_URI, accessToken), true, userBatchGetInfo.toJson());
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return UserInfoList.fromJson(jsonStr);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

}
