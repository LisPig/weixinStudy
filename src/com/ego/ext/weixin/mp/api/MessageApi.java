package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ego.core.util.UtilValidate;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.News;
import com.ego.ext.weixin.common.model.CustomNews;
import com.ego.ext.weixin.common.model.result.MediaUploadResult;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.model.UploadedVideo;
import com.ego.ext.weixin.mp.model.massmsg.PreviewMassMsg;
import com.ego.ext.weixin.mp.model.massmsg.SendedMassMsg;
import com.ego.ext.weixin.mp.model.custommsg.SendedCustomMsg;
import com.ego.ext.weixin.mp.model.result.MassPreviewResult;
import com.ego.ext.weixin.mp.model.result.MassSendedResult;
import com.ego.ext.weixin.mp.model.result.MassSendedStatus;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息发送接口
 *
 * @see http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html
 * @description
 * 在公众平台网站上，为订阅号提供了每天一条的群发权限，为服务号提供每月（自然月）4条的群发权限。而对于某些具备开发能力的公众号运营者，可以通过高级群发接口，实现更灵活的群发能力。
 *
 * 请注意：
 *
 * 1、对于认证订阅号，群发接口每天可成功调用1次，此次群发可选择发送给全部用户或某个分组；
 * 2、对于认证服务号虽然开发者使用高级群发接口的每日调用限制为100次，但是用户每月只能接收4条，无论在公众平台网站上，还是使用接口群发，用户每月只能接收4条群发消息，多于4条的群发将对该用户发送失败；
 * 3、具备微信支付权限的公众号，在使用群发接口上传、群发图文消息类型时，可使用<a>标签加入外链；
 * 4、开发者可以使用预览接口校对消息样式和排版，通过预览接口可发送编辑好的消息给指定用户校验效果。
 */
public class MessageApi {

    /**
     * 客服接口-发消息
     *
     * http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html
     */
    public static final String MESSAGE_CUSTOM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}";
    /**
     * 上传图文消息素材【订阅号与服务号认证后均可用】
     */
    public static final String MDEIA_UPLOAD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token={0}";
    public static final String MEDIA_UPLOAD_VIEDO_URL = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token={0}";
    public static final String MASS_SEND_ALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token={0}";
    public static final String MASS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token={0}";
    public static final String MASS_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token={0}";
    public static final String MASS_PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token={0}";
    public static final String MASS_GET_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token={0}";

    /**
     * 发送客服信息
     *
     * @param accessToken
     * @param message
     * @return
     * @throws WxException
     */
    public static Result sendCustomMsg(String accessToken, SendedCustomMsg message) throws WxException {
        try {
            String json = UtilHttp.post(MessageFormat.format(MessageApi.MESSAGE_CUSTOM_SEND_URL, accessToken), true, message.toJson());
            return Result.fromJson(json);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

   

    /**
     * 上传图文消息素材【订阅号与服务号认证后均可用】
     *
     * @param accessToken
     * @param articles
     * @param result
     * @return 返回数据示例（正确时的JSON返回结果）：
     *
     * {
     * "type":"news",
     * "media_id":"CsEf3ldqkAYJAU6EJeIkStVDSvffUJ54vqbThMgplD-VJXXof6ctX5fI6-aYyUiQ",
     * "created_at":1391857799 }
     * @throws WxException
     */
    public static MediaUploadResult uploadNews(String accessToken, List<News> articles, Result... result) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("articles", articles);
            String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MDEIA_UPLOAD_NEWS_URL, accessToken), true, JSON.toJSONString(json));
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return MediaUploadResult.fromJson(jsonStr);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }

    }

    /**
     * 上传视频。群发视频需要通过此接口获取media_id
     *
     *
     *
     * @param accessToken
     *
     * @param result
     * @return
     * @throws WxException
     */
    public static MediaUploadResult uploadVideo(String accessToken, UploadedVideo uploadedVideo, Result... result) throws WxException {
        try {

            String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MEDIA_UPLOAD_VIEDO_URL, accessToken), true, uploadedVideo.toJson());
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return MediaUploadResult.fromJson(jsonStr);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }

    }

    /**
     * 根据分组进行群发【订阅号与服务号认证后均可用】
     *
     * @param accessToken
     * @param msg
     * @return
     * @throws WxException
     */
    public static MassSendedResult sendMassMsgByGroupid(String accessToken, SendedMassMsg msg) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, msg.toJson());
            return MassSendedResult.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据OpenID列表群发
     *
     * @param accessToken
     * @param msg
     * @return
     * @throws WxException
     */
    public static MassSendedResult sendMassMsgByOpenids(String accessToken, SendedMassMsg msg) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_URL, accessToken), true, msg.toJson());
            return MassSendedResult.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 预览接口【订阅号与服务号认证后均可用】
     * http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html
     *
     *
     * @param accessToken
     * @param msg
     * @return
     * @throws WxException
     */
    public static MassPreviewResult previewMassMsg(String accessToken, PreviewMassMsg msg) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_PREVIEW_URL, accessToken), true, msg.toJson());
            return MassPreviewResult.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 查询群发消息发送状态【订阅号与服务号认证后均可用】
     *
     * http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html
     *
     * @param accessToken
     * @param msg_id
     * @return
     * @throws WxException
     */
    public static MassSendedStatus getStatus(String accessToken, String msg_id) throws WxException {
        try {
            Map<String, Object> json = new HashMap();
            json.put("msg_id", msg_id);
            String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_GET_URL, accessToken), true, JSON.toJSONString(json));
            return MassSendedStatus.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 删除群发【订阅号与服务号认证后均可用】.
     * 请注意，只有已经发送成功的消息才能删除删除消息只是将消息的图文详情页失效，已经收到的用户，还是能在其本地看到消息卡片。
     * 另外，删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
     *
     * @see
     * http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html
     * @param accessToken
     * @param msg_id 发送出去的消息ID
     * @return
     * @throws WxException
     */
    public static Result delete(String accessToken, String msg_id) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("msg_id", msg_id);
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_DELETE_URL, accessToken), true, JSON.toJSONString(json));
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /*
     public static MassSendedResult sendNewsByGroupId(String accessToken, String groupId, String mpNewsMediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "false");
     filter.put("group_id", groupId);
     mpnews.put("media_id", mpNewsMediaId);

     json.put("mpnews", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "mpnews");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));

     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendNewsToAll(String accessToken, String mpNewsMediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "true");
     mpnews.put("media_id", mpNewsMediaId);

     json.put("mpnews", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "mpnews");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));

     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendTextByGroupId(String accessToken, String groupId, String content) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "false");
     filter.put("group_id", groupId);
     mpnews.put("content", content);

     json.put("text", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "text");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);

     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendTextToAll(String accessToken, String content) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "true");
     mpnews.put("content", content);

     json.put("text", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "text");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);

     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

  
     public static MassSendedResult sendVoiceByGroupId(String accessToken, String groupId, String MediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "false");
     filter.put("group_id", groupId);
     mpnews.put("media_id", MediaId);

     json.put("voice", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "voice");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendVoiceToAll(String accessToken, String MediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "true");
     mpnews.put("media_id", MediaId);

     json.put("voice", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "voice");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendImageByGroupId(String accessToken, String groupId, String MediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "false");
     filter.put("group_id", groupId);
     mpnews.put("media_id", MediaId);

     json.put("image", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "image");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendImageToAll(String accessToken, String MediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "true");
     mpnews.put("media_id", MediaId);

     json.put("image", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "image");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendVideoByGroupId(String accessToken, String groupId, String MediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "false");
     filter.put("group_id", groupId);
     mpnews.put("media_id", MediaId);

     json.put("mpvideo", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "mpvideo");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendVideoToAll(String accessToken, String MediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> filter = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     filter.put("is_to_all", "true");
     mpnews.put("media_id", MediaId);

     json.put("mpvideo", mpnews);
     json.put("filter", filter);
     json.put("msgtype", "mpvideo");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_ALL_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendNewsByOpenids(String accessToken, String[] openids, String mpNewsMediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("media_id", mpNewsMediaId);
     json.put("touser", openids);
     json.put("mpnews", mpnews);
     json.put("msgtype", "mpnews");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_URL, accessToken), true, JSON.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendTextByOpenids(String accessToken, String[] openids, String content) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("content", content);
     json.put("touser", openids);
     json.put("text", mpnews);
     json.put("msgtype", "text");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendVoiceByOpenids(String accessToken, String[] openids, String MediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("media_id", MediaId);
     json.put("touser", openids);
     json.put("voice", mpnews);
     json.put("msgtype", "voice");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassSendedResult sendImageByOpenids(String accessToken, String[] openids, String MediaId) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("media_id", MediaId);
     json.put("touser", openids);
     json.put("image", mpnews);
     json.put("msgtype", "image");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

 
     public static MassSendedResult sendVideoByOpenids(String accessToken, String[] openids, String media_id, String title, String description) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("media_id", media_id);
     mpnews.put("title", title);
     mpnews.put("description", description);

     json.put("touser", openids);
     json.put("video", mpnews);
     json.put("msgtype", "video");
     String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
     return MassSendedResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }
     */
    /*
     public static MassPreviewResult previewMpnews(String accessToken, String openid, String media_id) throws WxException {
     try {
     Map<String, Object> json = new HashMap();
     Map<String, Object> mpnews = new HashMap();
     mpnews.put("media_id", media_id);
     json.put("touser", openid);
     json.put("mpnews", mpnews);
     json.put("msgtype", "mpnews");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_PREVIEW_URL, accessToken), true, JSON.toJSONString(json));
     return MassPreviewResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassPreviewResult previewText(String accessToken, String openid, String content) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("content", content);

     json.put("text", mpnews);
     json.put("touser", openid);
     json.put("msgtype", "text");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_PREVIEW_URL, accessToken), true, JSON.toJSONString(json));
     return MassPreviewResult.fromJson(jsonStr);

     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassPreviewResult previewVoice(String accessToken, String openid, String media_id) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("media_id", media_id);

     json.put("voice", mpnews);
     json.put("touser", openid);
     json.put("msgtype", Msg.MsgType.VOICE.getType());
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_PREVIEW_URL, accessToken), true, JSON.toJSONString(json));
     return MassPreviewResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassPreviewResult previewImage(String accessToken, String openid, String media_id) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("media_id", media_id);

     json.put("image", mpnews);
     json.put("touser", openid);
     json.put("msgtype", Msg.MsgType.IMAGE.getType());
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_PREVIEW_URL, accessToken), true, JSON.toJSONString(json));
     return MassPreviewResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }

     public static MassPreviewResult previewVideo(String accessToken, String openid, String media_id) throws WxException {
     try {
     Map<String, Object> json = new HashMap<String, Object>();
     Map<String, Object> mpnews = new HashMap<String, Object>();
     mpnews.put("media_id", media_id);

     json.put("mpvideo", mpnews);
     json.put("touser", openid);
     json.put("msgtype", "mpvideo");
     String jsonStr = UtilHttp.post(MessageFormat.format(MessageApi.MASS_PREVIEW_URL, accessToken), true, JSON.toJSONString(json));
     return MassPreviewResult.fromJson(jsonStr);
     } catch (Exception ex) {
     throw new WxException(ex.getMessage(), ex);
     }
     }
     */
    /**
     * 上传图文消息素材
     *
     * @param accessToken
     * @param articles
     * @deprecated
     * @return 返回数据示例（正确时的JSON返回结果）：{ "type":"news",
     * "media_id":"CsEf3ldqkAYJAU6EJeIkStVDSvffUJ54vqbThMgplD-VJXXof6ctX5fI6-aYyUiQ",
     * "created_at":1391857799 } 错误时微信会返回错误码等信息，请根据错误码查询错误信息
     * @throws java.lang.WxException
     */
    public static JSONObject uploadNews1(String accessToken, List<News> articles) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("articles", articles);
            String jsonStr = UtilHttp.post(MessageFormat.format(MDEIA_UPLOAD_NEWS_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据分组进行群发图文消息（注意图文消息的media_id需要通过上述方法来得到）：
     *
     * @param accessToken
     * @param groupId
     * @deprecated
     * @param mpNewsMediaId
     *
     * @throws WxException
     */
    public static JSONObject massSendNewsByGroupId(String accessToken, String groupId, String mpNewsMediaId) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> filter = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            filter.put("group_id", groupId);
            mpnews.put("media_id", mpNewsMediaId);

            json.put("mpnews", mpnews);
            json.put("filter", filter);
            json.put("msgtype", "mpnews");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_ALL_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据分组进行群发文本
     *
     * @param accessToken
     * @param groupId
     * @param content
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     *
     * @throws WxException
     */
    public static JSONObject massSendTextByGroupId(String accessToken, String groupId, String content) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> filter = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            filter.put("group_id", groupId);
            mpnews.put("content", content);

            json.put("text", mpnews);
            json.put("filter", filter);
            json.put("msgtype", "text");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_ALL_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据分组进行群发语音（注意此处media_id需通过基础支持中的上传下载多媒体文件来得到）：
     *
     * @param accessToken
     * @param groupId
     * @param MediaId
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     *
     * @throws WxException
     */
    public static JSONObject massSendVoiceByGroupId(String accessToken, String groupId, String MediaId) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> filter = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            filter.put("group_id", groupId);
            mpnews.put("media_id", MediaId);

            json.put("voice", mpnews);
            json.put("filter", filter);
            json.put("msgtype", "voice");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_ALL_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据分组进行群发图片（注意此处media_id需通过基础支持中的上传下载多媒体文件来得到）
     *
     * @param accessToken
     * @param groupId
     * @param MediaId
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     *
     * @throws WxException
     */
    public static JSONObject massSendImageByGroupId(String accessToken, String groupId, String MediaId) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> filter = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            filter.put("group_id", groupId);
            mpnews.put("media_id", MediaId);

            json.put("image", mpnews);
            json.put("filter", filter);
            json.put("msgtype", "image");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_ALL_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据分组进行群发视频 请注意，此处视频的media_id需通过POST请求到下述接口特别地得到：
     * https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN
     * POST数据如下（此处media_id需通过基础支持中的上传下载多媒体文件来得到）：
     *
     * {
     * "media_id":
     * "rF4UdIMfYK3efUfyoddYRMU50zMiRmmt_l0kszupYh_SzrcW5Gaheq05p_lHuOTQ",
     * "title": "TITLE", "description": "Description" } 返回将为
     *
     * {
     * "type":"video",
     * "media_id":"IhdaAQXuvJtGzwwc0abfXnzeezfO0NgPK6AQYShD8RQYMTtfzbLdBIQkQziv2XJc",
     * "created_at":1398848981 } 然后，POST下述数据（将media_id改为上一步中得到的media_id），即可进行发送
     *
     * @param accessToken
     * @param groupId
     * @param MediaId
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     * @see
     * http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口#.E4.B8.8A.E4.BC.A0.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF.E7.B4.A0.E6.9D.90
     * @throws WxException
     */
    public static JSONObject massSendVideoByGroupId(String accessToken, String groupId, String MediaId) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> filter = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            filter.put("group_id", groupId);
            mpnews.put("media_id", MediaId);

            json.put("mpvideo", mpnews);
            json.put("filter", filter);
            json.put("msgtype", "mpvideo");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_ALL_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据OpenID列表群发图文消息（注意图文消息的media_id需要通过上述方法来得到）：
     *
     * @param accessToken
     * @param openids
     * @param mpNewsMediaId
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     *
     */
    public static JSONObject massSendNewsByOpenids(String accessToken, String[] openids, String mpNewsMediaId) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            mpnews.put("media_id", mpNewsMediaId);
            json.put("touser", openids);
            json.put("mpnews", mpnews);
            json.put("msgtype", "mpnews");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据OpenID列表群发文本：
     *
     * @param accessToken
     * @param openids
     * @param content
     * @param mpNewsMediaId
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     * @throws java.lang.WxException
     *
     */
    public static JSONObject massSendTextByOpenids(String accessToken, String[] openids, String content) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            mpnews.put("content", content);
            json.put("touser", openids);
            json.put("text", mpnews);
            json.put("msgtype", "text");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据OpenID列表群发语音
     *
     * @param accessToken
     * @param openids
     * @param MediaId
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     *
     */
    public static JSONObject massSendVoiceByOpenids(String accessToken, String[] openids, String MediaId) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            mpnews.put("media_id", MediaId);
            json.put("touser", openids);
            json.put("voice", mpnews);
            json.put("msgtype", "voice");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据OpenID列表群发图片
     *
     * @param accessToken
     * @param openids
     * @param MediaId
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     *
     */
    public static JSONObject massSendImageByOpenids(String accessToken, String[] openids, String MediaId) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            mpnews.put("media_id", MediaId);
            json.put("touser", openids);
            json.put("image", mpnews);
            json.put("msgtype", "image");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 根据OpenID列表群发视频
     *
     *
     * 请注意，此处视频的media_id需通过POST请求到下述接口特别地得到：
     * https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN
     * POST数据如下（此处media_id需通过基础支持中的上传下载多媒体文件来得到）：
     *
     * {
     * "media_id":
     * "rF4UdIMfYK3efUfyoddYRMU50zMiRmmt_l0kszupYh_SzrcW5Gaheq05p_lHuOTQ",
     * "title": "TITLE", "description": "Description" } 返回将为
     *
     * {
     * "type":"video",
     * "media_id":"IhdaAQXuvJtGzwwc0abfXnzeezfO0NgPK6AQYShD8RQYMTtfzbLdBIQkQziv2XJc",
     * "created_at":1398848981 } 然后，POST下述数据（将media_id改为上一步中得到的media_id），即可进行发送
     *
     * @param accessToken
     * @param openids
     * @param MediaId
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"send job submission
     * success", "msg_id":34182 }
     * @see
     * http://mp.weixin.qq.com/wiki/index.php?title=高级群发接口#.E4.B8.8A.E4.BC.A0.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF.E7.B4.A0.E6.9D.90
     */
    public static JSONObject massSendVideoByOpenids(String accessToken, String[] openids, String MediaId) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            Map<String, Object> mpnews = new HashMap<String, Object>();
            mpnews.put("media_id", MediaId);
            json.put("touser", openids);
            json.put("video", mpnews);
            json.put("msgtype", "video");
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_SEND_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 删除群发
     *
     * 请注意，只有已经发送成功的消息才能删除删除消息只是将消息的图文详情页失效，已经收到的用户，还是能在其本地看到消息卡片。
     * 另外，删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
     *
     * 返回说明
     *
     * 返回数据示例（正确时的JSON返回结果）：
     *
     * {
     * "errcode":0, "errmsg":"ok" }
     *
     * @param accessToken
     * @param msgid
     * @deprecated
     * @return 正确时的JSON返回结果{ "errcode":0, "errmsg":"ok"}
     *
     */
    public static JSONObject massDelete(String accessToken, String msgid) throws WxException {
        try {
            Map<String, Object> json = new HashMap<String, Object>();
            json.put("msgid", msgid);
            String jsonStr = UtilHttp.post(MessageFormat.format(MASS_DELETE_URL, accessToken), true, JSONObject.toJSONString(json));
            if (UtilValidate.isNotEmpty(jsonStr)) {
                return JSONObject.parseObject(jsonStr);
            }
            return null;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 发送客服消息
     *
     * @param accessToken
     * @param message
     * @return
     * @deprecated
     * @throws Exception
     */
    public static String sendMsg(String accessToken, Map<String, Object> message) throws Exception {
        //   String reslut = UtilHttp.post(MESSAGE_CUSTOM_SEND_URL.concat(accessToken), JSONObject.toJSONString(message));
        String reslut = UtilHttp.post(MessageFormat.format(MessageApi.MESSAGE_CUSTOM_SEND_URL, accessToken), true, JSONObject.toJSONString(message));
        return reslut;
    }

    /**
     * 发送文本客服消息
     *
     * @param accessToken
     * @param openId
     * @param text
     * @return
     * @deprecated
     * @throws Exception
     */
    public static String sendText(String accessToken, String openId, String text) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("content", text);
        json.put("touser", openId);
        json.put("msgtype", "text");
        json.put("text", textObj);
        String reslut = sendMsg(accessToken, json);
        return reslut;
    }

    /**
     * 发送图片消息
     *
     * @param accessToken
     * @param openId
     * @param media_id
     * @deprecated
     * @return
     * @throws Exception
     */
    public static String sendImage(String accessToken, String openId, String media_id) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("media_id", media_id);
        json.put("touser", openId);
        json.put("msgtype", "image");
        json.put("image", textObj);
        String reslut = sendMsg(accessToken, json);
        return reslut;
    }

    /**
     * 发送语言回复
     *
     * @param accessToken
     * @param openId
     * @param media_id
     * @deprecated
     * @return
     * @throws Exception
     */
    public static String sendVoice(String accessToken, String openId, String media_id) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("media_id", media_id);
        json.put("touser", openId);
        json.put("msgtype", "voice");
        json.put("voice", textObj);
        String reslut = sendMsg(accessToken, json);
        return reslut;
    }

    /**
     * 发送视频回复
     *
     * @param accessToken
     * @param openId
     * @param media_id
     * @param title
     * @param description
     * @deprecated
     * @return
     * @throws Exception
     */
    public static String sendVideo(String accessToken, String openId, String media_id, String title, String description) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("media_id", media_id);
        textObj.put("title", title);
        textObj.put("description", description);

        json.put("touser", openId);
        json.put("msgtype", "video");
        json.put("video", textObj);
        String reslut = sendMsg(accessToken, json);
        return reslut;
    }

    /**
     * 发送音乐回复
     *
     * @param accessToken
     * @param openId
     * @param musicurl
     * @param hqmusicurl
     * @param thumb_media_id
     * @param title
     * @param description
     * @deprecated
     * @return
     * @throws Exception
     */
    public static String sendMusic(String accessToken, String openId, String musicurl, String hqmusicurl, String thumb_media_id, String title, String description) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("musicurl", musicurl);
        textObj.put("hqmusicurl", hqmusicurl);
        textObj.put("thumb_media_id", thumb_media_id);
        textObj.put("title", title);
        textObj.put("description", description);

        json.put("touser", openId);
        json.put("msgtype", "music");
        json.put("music", textObj);
        String reslut = sendMsg(accessToken, json);
        return reslut;
    }

    /**
     * 发送图文回复
     *
     * @param accessToken
     * @param openId
     * @param articles
     * @deprecated
     * @return
     * @throws Exception
     */
    public static String sendNews(String accessToken, String openId, List<CustomNews> articles) throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        List news = new ArrayList();;
        for (int i = 0, len = articles.size(); i < len; i++) {
            CustomNews d = articles.get(i);
            Map<String, Object> a = new HashMap<String, Object>();
            a.put("title", d.getTitle());
            a.put("description", d.getDescription());
            a.put("url", d.getUrl());
            a.put("picurl", d.getPicurl());
            news.add(a);
        }
        json.put("touser", openId);
        json.put("msgtype", "news");
        json.put("news", news);
        String reslut = sendMsg(accessToken, json);
        return reslut;
    }

}
