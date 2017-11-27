package com.ego.ext.weixin.mp.model.custommsg;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.msg.Msg;

/**
 * video消息
 *
 * @author Daniel Qian
 *
 */
public class SendedVideoCustomMsg extends SendedCustomMsg {

    private SendedVideoMsgInfo video;

    /**
     * 设置 super.setMsgtype(Msg.MsgType.VIDEO.getType());
     */
    public SendedVideoCustomMsg() {
        super.setMsgtype(Msg.MsgType.VIDEO.getType());
    }

    public SendedVideoMsgInfo getVideo() {
        return this.video;
    }

    /**
     *
     * @param video 必须。消息内容
     */
    public void setVideo(SendedVideoMsgInfo video) {
        this.video = video;
    }

    public static class SendedVideoMsgInfo {

        private String media_id;
        private String title;
        private String description;
        private String thumb_media_id;

        public String getMedia_id() {
            return media_id;
        }

        /**
         *
         * @param media_id 必须。语音文件id，可以调用上传媒体文件接口获取
         * @return
         */
        public SendedVideoMsgInfo setMedia_id(String media_id) {
            this.media_id = media_id;
            return this;
        }

        public String getTitle() {
            return this.title;
        }

        /**
         *
         * @param title 非必须。视频消息的标题
         * @return
         */
        public SendedVideoMsgInfo setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getDescription() {
            return this.description;
        }

        /**
         *
         * @param description 非必须。视频消息的描述
         * @return
         */
        public SendedVideoMsgInfo setDescription(String description) {
            this.description = description;
            return this;
        }

        public String getThumb_media_id() {
            return this.thumb_media_id;
        }

        /**
         *
         * @param thumb_media_id 缩略图的媒体ID
         *
         * @return
         */
        public SendedVideoMsgInfo setThumb_media_id(String thumb_media_id) {
            this.thumb_media_id = thumb_media_id;
            return this;
        }

    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static SendedVideoCustomMsg fromJson(String json) {
        return JSON.parseObject(json, SendedVideoCustomMsg.class);
    }

    public static void main(String arg[]) {
        SendedVideoCustomMsg t = new SendedVideoCustomMsg();
        t.setVideo(new SendedVideoMsgInfo().setMedia_id("22222255552").setDescription("bbbbb"));

        System.out.print(JSON.toJSONString(t));
    }

}
