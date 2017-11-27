package com.ego.ext.weixin.common.model.result;

import com.alibaba.fastjson.JSON;

public class MediaUploadResult {

    private String type;
    private String media_id;
    //private String thumbMediaId;
    private long created_at;
    /**
     * 新增的图片素材的图片URL（仅新增图片素材时会返回该字段）
     */
    private String url;

    /**
     * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），次数为news，即图文消息
     *
     * @return
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 媒体文件/图文消息上传后获取的唯一标识
     *
     * @return
     */
    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    /**
     * 媒体文件上传时间
     *
     * @return
     */
    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
    /*
     public String getThumbMediaId() {
     return thumbMediaId;
     }

     public void setThumbMediaId(String thumbMediaId) {
     this.thumbMediaId = thumbMediaId;
     }
     */

    public static MediaUploadResult fromJson(String json) {
        return JSON.parseObject(json, MediaUploadResult.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    @Override
    public String toString() {
        return this.toJson();
    }

    /**
     * 新增的图片素材的图片URL（仅新增图片素材时会返回该字段）
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 新增的图片素材的图片URL（仅新增图片素材时会返回该字段）
     *
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
