package com.ego.ext.weixin.mp.model.custommsg;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.msg.Msg;

/**
 * music消息
 *
 * @author Daniel Qian
 *
 */
public class SendedMusicCustomMsg extends SendedCustomMsg {

    private SendedMusicMsgInfo music;

    /**
     * 设置 super.setMsgtype(Msg.MsgType.music.getType());
     */
    public SendedMusicCustomMsg() {
        super.setMsgtype(Msg.MsgType.VIDEO.getType());
    }

    public SendedMusicMsgInfo getMusic() {
        return this.music;
    }

    /**
     *
     * @param music 必须。消息内容
     */
    public void setMusic(SendedMusicMsgInfo music) {
        this.music = music;
    }

    public static class SendedMusicMsgInfo {

        private String musicurl;
        private String title;
        private String description;
        private String thumb_media_id;
        private String hqmusicurl;

        public String getMusicurl() {
            return musicurl;
        }

        /**
         *
         * @param musicurl 必须。音乐链接
         *
         * @return
         */
        public SendedMusicMsgInfo setMusicurl(String musicurl) {
            this.musicurl = musicurl;
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
        public SendedMusicMsgInfo setTitle(String title) {
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
        public SendedMusicMsgInfo setDescription(String description) {
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
        public SendedMusicMsgInfo setThumb_media_id(String thumb_media_id) {
            this.thumb_media_id = thumb_media_id;
            return this;
        }

        public String getHqmusicurl() {
            return this.hqmusicurl;
        }

        /**
         *
         *
         * @param hqmusicurl 必须。高品质音乐链接，wifi环境优先使用该链接播放音乐
         * @return
         */
        public SendedMusicMsgInfo setHqmusicurl(String hqmusicurl) {
            this.hqmusicurl = hqmusicurl;
            return this;
        }
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static SendedMusicCustomMsg fromJson(String json) {
        return JSON.parseObject(json, SendedMusicCustomMsg.class);
    }

    public static void main(String arg[]) {
        SendedMusicCustomMsg t = new SendedMusicCustomMsg();
        t.setMusic(new SendedMusicMsgInfo().setDescription("bbbbb"));

        System.out.print(JSON.toJSONString(t));
    }

}
