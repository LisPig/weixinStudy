package com.ego.ext.weixin.mp.model.custommsg;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.msg.Msg;

/**
 * voice消息
 *
 * @author Daniel Qian
 *
 */
public class SendedVoiceCustomMsg extends SendedCustomMsg {

    private SendedVoiceMsgInfo voice;

    /**
     * 设置 super.setMsgtype(Msg.MsgType.VOICE.getType());
     */
    public SendedVoiceCustomMsg() {
        super.setMsgtype(Msg.MsgType.VOICE.getType());
    }

    public SendedVoiceMsgInfo getVoice() {
        return this.voice;
    }

    /**
     *
     * @param voice 必须。消息内容
     */
    public void setVoice(SendedVoiceMsgInfo voice) {
        this.voice = voice;
    }

    public static class SendedVoiceMsgInfo {

        private String media_id;

        public String getMedia_id() {
            return media_id;
        }

        /**
         *
         * @param media_id 必须。语音文件id，可以调用上传媒体文件接口获取
         * @return
         */
        public SendedVoiceMsgInfo setMedia_id(String media_id) {
            this.media_id = media_id;
            return this;
        }

    }

    public static void main(String arg[]) {
        SendedVoiceCustomMsg t = new SendedVoiceCustomMsg();
        t.setVoice(new SendedVoiceMsgInfo().setMedia_id("22222255552"));

        System.out.print(JSON.toJSONString(t));
    }

}
