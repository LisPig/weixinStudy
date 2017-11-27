package com.ego.ext.weixin.mp.model.massmsg;

import com.ego.ext.weixin.mp.model.custommsg.*;
import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.msg.Msg;

/**
 * 预览卡券
 *
 * @author Daniel Qian
 *
 */
public class PreviewCardMassMsg extends PreviewMassMsg {

    private PreviewCardMsgInfo wxcard;

    /**
     * 设置 super.setMsgtype(Msg.MsgType.wxcard.getType());
     */
    public PreviewCardMassMsg() {
        super.setMsgtype(Msg.MsgType.wxcard.getType());
    }

    public PreviewCardMsgInfo getWxcard() {
        return this.wxcard;
    }

    /**
     *
     * @param wxcard 必须。消息内容
     */
    public void setWxcard(PreviewCardMsgInfo wxcard) {
        this.wxcard = wxcard;
    }

    public static class PreviewCardMsgInfo {

        private String card_id;
        private String card_ext;

        public String getCard_id() {
            return card_id;
        }

        /**
         * @param card_id the card_id to set
         */
        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        /**
         * http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html
         *
         * @return
         */
        public String getCard_ext() {
            return this.card_ext;
        }

        /**
         * http://mp.weixin.qq.com/wiki/1/70a29afed17f56d537c833f89be979c9.html
         *
         * @param title 非必须。视频消息的标题
         * @return
         */
        public void setCard_ext(String card_ext) {
            this.card_ext = card_ext;

        }

    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static PreviewCardMassMsg fromJson(String json) {
        return JSON.parseObject(json, PreviewCardMassMsg.class);
    }

    public static void main(String arg[]) {
        PreviewCardMassMsg t = new PreviewCardMassMsg();

        System.out.print(JSON.toJSONString(t));
    }

}
