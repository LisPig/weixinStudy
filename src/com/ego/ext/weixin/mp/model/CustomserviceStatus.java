package com.ego.ext.weixin.mp.model;

import com.alibaba.fastjson.JSON;

/**
 * 客服状态
 *
 *
 */
public class CustomserviceStatus {

    private String kf_account;
    private int kf_id;
    private int status;
    private int auto_accept;
    private int accepted_case;

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
     * 客服在线状态 1：pc在线，2：手机在线。若pc和手机同时在线则为 1+2=3
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
/**
 * 
 * @return  1：pc在线，2：手机在线。若pc和手机同时在线则为 1+2=3 \ 未知状态
 */
    public String getStatusDescription() {
        if (this.status == 1) {
            return "pc在线";
        } else if (this.status == 2) {
            return "手机在线";
        } else if (this.status == 3) {
            return "pc和手机同时在线";
        } else {
            return "未知状态";
        }

    }

    /**
     * 客服设置的最大自动接入数
     *
     * @return
     */
    public int getAuto_accept() {
        return auto_accept;
    }

    public void setAuto_accept(int auto_accept) {
        this.auto_accept = auto_accept;
    }

    /**
     * 客服当前正在接待的会话数
     *
     * @return
     */
    public int getAccepted_case() {
        return accepted_case;
    }

    public void setAccepted_case(int accepted_case) {
        this.accepted_case = accepted_case;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static CustomserviceStatus fromJson(String json) {
        return JSON.parseObject(json, CustomserviceStatus.class
        );
    }

}
