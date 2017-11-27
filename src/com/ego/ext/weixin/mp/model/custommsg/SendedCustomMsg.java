package com.ego.ext.weixin.mp.model.custommsg;

import com.alibaba.fastjson.JSON;

/**
 * 发送的客服消息
 *
 * @author Daniel Qian
 *
 */
public abstract class SendedCustomMsg {

    private String touser;
    private String msgtype;
    /**
     * 
     * 以某个客服帐号来发消息
     * http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html
     */
    private SendedKFaccountInfo customservice;

    public String getTouser() {
        return touser;
    }

    /**
     *
     *
     * @param touser 必须。普通用户openid
     */
    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    /**
     *
     * @param msgtype
     * 必须。消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news
     */
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public SendedKFaccountInfo getCustomservice() {
        return this.customservice;
    }

    /**
     * 请注意，如果需要以某个客服帐号来发消息（在微信6.0.2及以上版本中显示自定义头像），则需在JSON数据包的后半部分加入customservice参数，例如发送文本消息则改为
     *
     * @param customservice 非必须
     * @see
     * http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html#.E4.BF.AE.E6.94.B9.E5.AE.A2.E6.9C.8D.E5.B8.90.E5.8F.B7
     */
    public void setCustomservice(SendedKFaccountInfo customservice) {
        this.customservice = customservice;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static class SendedKFaccountInfo {

        private String kfaccount;

        public String getKfaccount() {
            return kfaccount;
        }

        /**
         *
         * @param kfaccount
         * @return
         */
        public SendedKFaccountInfo setKfaccount(String kfaccount) {
            this.kfaccount = kfaccount;
            return this;
        }
    }

}
