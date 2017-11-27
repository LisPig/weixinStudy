package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.ego.core.net.Param;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.AccessToken;
import com.ego.ext.weixin.common.model.Attachment;
import com.ego.ext.weixin.common.model.News;
import com.ego.ext.weixin.common.model.result.MediaUploadResult;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.msg.Msg;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.model.result.NewsMaterialGet;
import com.ego.ext.weixin.mp.model.result.ViedoMaterialGet;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 素材接口
 *
 * @description
 *
 *
 */
public class MaterialApi {

    private static final String DOWNLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token={0}&media_id={1}";
    private static final String UPLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token={0}&type={1}";
    public static final String ADD_NEWS_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token={0}";
    /**
     * 传图文消息内的图片获取URL【订阅号与服务号认证后均可用】 上传图文消息内的图片获取URL
     * 请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。
     *
     */
    public static final String UPLOAD_IMG_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token={0}";
    private static final String ADD_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token={0}";
    private static final String DOWNLOAD_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token={0}";

    /**
     * 下载多媒体文件
     *
     * @param accessToken
     * @param mediaId
     * @return
     * @throws IOException
     * @see http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
     */
    public static Attachment downloadMedia(String accessToken, String mediaId, Result... result) throws IOException {
        String url = MessageFormat.format(DOWNLOAD_MEDIA_URL, accessToken, mediaId);
        return UtilHttp.download(url);
    }

    /**
     * 新增临时素材
     *
     *
     * 注意事项
     *
     * 上传的临时多媒体文件有格式和大小限制，如下：
     *
     * 图片（image）: 1M，支持JPG格式 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
     * 视频（video）：10MB，支持MP4格式 缩略图（thumb）：64KB，支持JPG格式
     * 媒体文件在后台保存时间为3天，即3天后media_id失效。
     *
     * @param accessToken
     * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @param file
     * @param result
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static MediaUploadResult uploadMedia(String accessToken, String type, File file, Result... result) throws Exception {
        String url = MessageFormat.format(UPLOAD_MEDIA_URL, accessToken, type);
        String jsonStr = UtilHttp.upload(url, file);
        Result ret = JSON.parseObject(jsonStr, Result.class);
        if (!ret.isSuccess()) {
            if (result != null) {
                result[0].copy(ret);
            }
            return null;
        } else {
            return MediaUploadResult.fromJson(jsonStr);
        }

    }

    /**
     * 新增永久图文素材
     *
     * 请注意，在图文消息的具体内容中，将过滤外部的图片链接，开发者可以通过下述接口上传图片得到URL，放到图文内容中使用。
     *
     * 上传图文消息内的图片获取URL
     * 请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下
     *
     * @param accessToken
     * @param articles
     * @param result
     * @return
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
     * 上传图文消息内的图片获取URL【订阅号与服务号认证后均可用】
     *
     * 请注意，本接口所上传的图片不占用公众号的素材库中图片数量的5000个的限制。图片仅支持jpg/png格式，大小必须在1MB以下。
     * http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html
     *
     * @param accessToken
     * @param file
     * @param result
     * @return 返回图片在微信服务器的url，错误返回null
     * @throws Exception
     */
    public static String uploadImg(String accessToken, File file, Result... result) throws Exception {
        String url = MessageFormat.format(UPLOAD_IMG_URL, accessToken);
        String jsonStr = UtilHttp.upload(url, file);
        Result ret = Result.fromJson(jsonStr);
        if (!ret.isSuccess()) {
            if (result != null) {
                result[0].copy(ret);
            }
            return null;
        } else {
            return (String) JSON.parseObject(jsonStr, Map.class).get("url");
        }

    }

    /**
     * 新增其他类型永久素材（除视频外）
     *
     * @param accessToken
     * @param type
     * @param file
     * @param result
     * @return
     * @throws Exception
     */
    public static MediaUploadResult uploadMaterial(String accessToken, String type, File file, Result... result) throws Exception {
        String url = MessageFormat.format(ADD_MATERIAL_URL, accessToken);
        String jsonStr = UtilHttp.upload(url, file, new Param("type", type));
        Result ret = JSON.parseObject(jsonStr, Result.class);
        if (!ret.isSuccess()) {
            if (result != null) {
                result[0].copy(ret);
            }
            return null;
        } else {
            return MediaUploadResult.fromJson(jsonStr);
        }

    }

    /**
     * 上传视频永久素材
     *
     * @param accessToken
     * @param type
     * @param file
     * @param title 视频素材的标题
     * @param intro 视频素材的描述
     * @param result
     * @return
     * @throws Exception
     */
    public static MediaUploadResult uploadVideoMaterial(String accessToken, File file, String title, String intro, Result... result) throws Exception {
        String url = MessageFormat.format(ADD_MATERIAL_URL, accessToken);
        Map<String, String> d = new HashMap();
        d.put("title", title);
        d.put("introduction", intro);
        String jsonStr = UtilHttp.upload(url, file, new Param("type", Msg.MsgType.VIDEO.getVal()), new Param("description", JSON.toJSONString(d)));
        Result ret = JSON.parseObject(jsonStr, Result.class);
        if (!ret.isSuccess()) {
            if (result != null) {
                result[0].copy(ret);
            }
            return null;
        } else {
            return MediaUploadResult.fromJson(jsonStr);
        }

    }

    /**
     *
     * @param accessToken
     * @param mediaId
     * @param result
     * @return
     * @throws IOException
     */
    public static Attachment downloadMaterial(String accessToken, String mediaId, Result... result) throws IOException {
        String url = MessageFormat.format(DOWNLOAD_MATERIAL_URL, accessToken, mediaId);
        return UtilHttp.download(url);
    }

    /**
     * 下载图文素材
     *
     * @param accessToken
     * @param media_id
     * @param result
     * @return
     */
    public static NewsMaterialGet downloadNews(String accessToken, String media_id, Result... result) throws WxException {
        try {
            String url = MessageFormat.format(DOWNLOAD_MATERIAL_URL, accessToken);
            Map<String, String> d = new HashMap();
            d.put("media_id", media_id);
            String jsonStr = UtilHttp.post(url, true, JSON.toJSONString(d));
            Result ret = JSON.parseObject(jsonStr, Result.class);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {

                return NewsMaterialGet.fromJson(jsonStr);
            }

        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 下载视频素材
     *
     * @param accessToken
     * @param media_id
     * @param result
     * @return
     * @throws WxException
     */
    public static ViedoMaterialGet downloadVideo(String accessToken, String media_id, Result... result) throws WxException {
        try {
            String url = MessageFormat.format(DOWNLOAD_MATERIAL_URL, accessToken);
            Map<String, String> d = new HashMap();
            d.put("media_id", media_id);
            String jsonStr = UtilHttp.post(url, true, JSON.toJSONString(d));
            Result ret = JSON.parseObject(jsonStr, Result.class);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {

                return ViedoMaterialGet.fromJson(jsonStr);
            }

        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }
}
