package com.ego.ext.weixin.mp.model.custommsg;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.msg.Msg;

/**
 * 消息
 *
 * @author Daniel Qian
 *
 */
public class SendedImageCustomMsg extends SendedCustomMsg {

    private SendedImagetMsgInfo image;

    /**
     * 设置 super.setMsgtype(Msg.MsgType.IMAGE.getType());
     */
    public SendedImageCustomMsg() {
        super.setMsgtype(Msg.MsgType.IMAGE.getType());
    }

    public SendedImagetMsgInfo getImage() {
        return this.image;
    }

    /**
     *
     * @param image 必须。消息内容
     */
    public void setImage(SendedImagetMsgInfo image) {
        this.image = image;
    }

    public static class SendedImagetMsgInfo {

        private String media_id;

        public String getMedia_id() {
            return media_id;
        }

        /**
         *
         * @param media_id 必须。图片媒体文件id，可以调用上传媒体文件接口获取
         * @return
         */
        public SendedImagetMsgInfo setMedia_id(String media_id) {
            this.media_id = media_id;
            return this;
        }

    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static SendedImageCustomMsg fromJson(String json) {
        return JSON.parseObject(json, SendedImageCustomMsg.class);
    }

    public static void main(String arg[]) {
        SendedImageCustomMsg t = new SendedImageCustomMsg();
        t.setImage(new SendedImagetMsgInfo().setMedia_id("2222222"));

        System.out.print(JSON.toJSONString(t));
    }

}
