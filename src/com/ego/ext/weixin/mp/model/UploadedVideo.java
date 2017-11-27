package com.ego.ext.weixin.mp.model;

import com.alibaba.fastjson.JSON;

/**
 * 被用于高级群发接口上传视频
 *
 * @see http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html
 * @author Administrator
 */
public class UploadedVideo {
/**
 * 此处media_id需通过基础支持中的上传下载多媒体文件来得到
 */
    private String media_id;

    private String title;

    private String description;

    public String getMedia_id() {
        return media_id;
    }

    /**
     * media_id需通过基础支持中的上传下载多媒体文件来得到
     *
     * @param media_id
     */
    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static UploadedVideo fromJson(String json) {
        return JSON.parseObject(json, UploadedVideo.class);
    }

}
