package com.ego.ext.weixin.common.model;

import com.alibaba.fastjson.JSON;

/**
 * 新增永久图文素材、群发接口时用的图文素材
 *
 *
 * @see http://mp.weixin.qq.com/wiki/14/7e6c03263063f4813141c3e17dd4350a.html
 */
public class News {

    // 图文消息标题
    private String title;
    //图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
    private String thumb_media_id;
    //图文消息的作者
    private String author;
    //在图文消息页面点击“阅读原文”后的页面
    private String content_source_url;
    //图文消息页面的内容，支持HTML标签
    private String content;
    //图文消息的描述
    private String digest;
    //用于群发 是否显示封面，1为显示，0为不显示
    protected boolean show_cover_pic;

    /**
     * 默认构造方法
     *
     */
    public News() {
    }

    /**
     *
     *
     * @param title 标题
     * @param author
     * @param thumb_media_id
     * @param content_source_url
     * @param content
     */
    public News(String title, String author, String thumb_media_id, String content_source_url, String content) {
        this.title = title;
        this.author = author;
        this.thumb_media_id = thumb_media_id;
        this.content_source_url = content_source_url;
        this.content = content;
    }

    /**
     *
     *
     * @param title 标题
     * @param author
     * @param thumb_media_id
     * @param content_source_url
     * @param content
     * @param digest
     */
    public News(String title, String author, String thumb_media_id, String content_source_url, String content, String digest) {
        this.title = title;
        this.author = author;
        this.thumb_media_id = thumb_media_id;
        this.content_source_url = content_source_url;
        this.content = content;
        this.digest = digest;
    }

    /**
     *
     *
     * @param title 标题
     * @param author
     * @param thumb_media_id
     * @param content_source_url
     */
    public News(String title, String author, String thumb_media_id, String content_source_url) {
        this.title = title;
        this.author = author;
        this.thumb_media_id = thumb_media_id;
        this.content_source_url = content_source_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent_source_url() {
        return this.content_source_url;
    }

    public void setContent_source_url(String content_source_url) {
        this.content_source_url = content_source_url;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public boolean getShow_cover_pic() {
        return this.show_cover_pic;
    }

    public void setShow_cover_pic(boolean show_cover_pic) {
        this.show_cover_pic = show_cover_pic;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static News fromJson(String json) {
        return JSON.parseObject(json, News.class);
    }
}
