package com.ego.ext.weixin.mp.model;

import com.alibaba.fastjson.JSON;

/**
 * 客服账号
 *
 *
 */
public class Customservice {

    private String kf_account;
    private String kf_nick;
    private int kf_id;
    private String kf_headimg;

    private String password;

    private String media;

    /**
     * 完整客服账号，格式为：账号前缀@公众号微信号
     *
     * @return
     */
    public String getKf_account() {
        return kf_account;
    }

    public void setKf_account(String kf_account) {
        this.kf_account = kf_account;
    }

    /**
     * 客服昵称
     *
     * @return
     */
    public String getKf_nick() {
        return kf_nick;
    }

    public void setKf_nick(String kf_nick) {
        this.kf_nick = kf_nick;
    }

    /**
     * 客服工号
     *
     * @return
     */
    public int getKf_id() {
        return kf_id;
    }

    public void setKf_id(int kf_id) {
        this.kf_id = kf_id;
    }

    /**
     * 客服头像
     *
     * @return
     */
    public String getKf_headimg() {
        return kf_headimg;
    }

    public void setKf_headimg(String kf_headimg) {
        this.kf_headimg = kf_headimg;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static Customservice fromJson(String json) {
        return JSON.parseObject(json, Customservice.class
        );
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the media
     */
    public String getMedia() {
        return media;
    }

    /**
     * @param media the media to set
     */
    public void setMedia(String media) {
        this.media = media;
    }

}
