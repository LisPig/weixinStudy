package com.ego.ext.weixin.mp.model.custommsg;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.CustomNews;
import com.ego.ext.weixin.common.msg.Msg;

/**
 * text消息
 *
 * @author Daniel Qian
 *
 */
public class SendedTextCustomMsg extends SendedCustomMsg {

    private SendedTextMsgInfo text;

    /**
     * 设置 super.setMsgtype(Msg.MsgType.TEXT.getType());
     */
    public SendedTextCustomMsg() {
        super.setMsgtype(Msg.MsgType.TEXT.getType());
    }

    public SendedTextMsgInfo getText() {
        return this.text;
    }

    /**
     *
     * @param text 必须。消息内容
     */
    public void setText(SendedTextMsgInfo text) {
        this.text = text;
    }

    public static class SendedTextMsgInfo {

        private String content;

        public String getContent() {
            return content;
        }

        /**
         *
         * @param content 必须。消息内容
         */
        public SendedTextMsgInfo setContent(String content) {
            this.content = content;
            return this;
        }

    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static SendedTextCustomMsg fromJson(String json) {
        return JSON.parseObject(json, SendedTextCustomMsg.class);
    }

    public static void main(String arg[]) {
        SendedTextCustomMsg t = new SendedTextCustomMsg();
        t.setText(new SendedTextMsgInfo().setContent("3333"));

        System.out.print(JSON.toJSONString(t));
    }

}
