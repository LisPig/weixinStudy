package com.ego.ext.weixin.common.model;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.mp.model.Qrcod;

/**
 * 发送客服图文消息时的图文消息
 *
 *
 * @see http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html
 */
public class CustomNews {

    // 图文消息标题
    private String title;
    // 图文消息描述
    private String description;
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80。
    private String picurl;
    // 点击图文消息跳转链接
    private String url;

    public CustomNews() {
    }

    /**
     * 所以属性的构造方法
     *
     * @param title 标题
     * @param description 描述
     * @param picUrl 图片地址
     * @param url url地址
     */
    public CustomNews(String title, String description, String picUrl, String url) {
        this.title = title;
        this.description = description;
        this.picurl = picUrl;
        this.url = url;
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

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static CustomNews fromJson(String json) {
        return JSON.parseObject(json, CustomNews.class);
    }
}
