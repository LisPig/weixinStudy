package com.ego.ext.weixin.mp.model.massmsg;

import com.alibaba.fastjson.JSON;

/**
 * 高级群发接口预览群发信息
 *
 * @see http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html
 *
 * @author
 *
 */
public abstract class PreviewMassMsg {

    protected String msgtype;
    /**
     * 接收消息用户对应该公众号的openid，该字段也可以改为towxname，以实现对微信号的预览
     */
    private String touser;//用于指定用户
    /**
     * 微信号
     */
    private String towxname;//

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getTouser() {
        return touser;
    }

    /**
     * 接收消息用户对应该公众号的openid
     *
     * @param touser
     */
    public void setTouser(String touser) {
        this.touser = touser;
    }

    /**
     * 微信号
     *
     * @return the towxname
     */
    public String getTowxname() {
        return towxname;
    }

    /**
     * 微信号
     *
     * @param towxname the towxname to set
     */
    public void setTowxname(String towxname) {
        this.towxname = towxname;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static PreviewMassMsg fromJson(String json) {
        return JSON.parseObject(json, PreviewMassMsg.class);
    }

}
