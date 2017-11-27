package com.ego.ext.weixin.mp.model.custommsg;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.msg.Msg;

/**
 * 发送卡券
 *
 * @author Daniel Qian
 *
 */
public class SendedCardCustomMsg extends SendedCustomMsg {

    private SendedWxcardMsgInfo wxcard;

    /**
     * 设置 super.setMsgtype(Msg.MsgType.wxcard.getType());
     */
    public SendedCardCustomMsg() {
        super.setMsgtype(Msg.MsgType.wxcard.getType());
    }

    public SendedWxcardMsgInfo getWxcard() {
        return this.wxcard;
    }

    /**
     *
     * @param wxcard 必须。消息内容
     */
    public void setWxcard(SendedWxcardMsgInfo wxcard) {
        this.wxcard = wxcard;
    }

    public static class SendedWxcardMsgInfo {

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

    public static SendedCardCustomMsg fromJson(String json) {
        return JSON.parseObject(json, SendedCardCustomMsg.class);
    }

    public static void main(String arg[]) {
        SendedCardCustomMsg t = new SendedCardCustomMsg();

        System.out.print(JSON.toJSONString(t));
    }

}
