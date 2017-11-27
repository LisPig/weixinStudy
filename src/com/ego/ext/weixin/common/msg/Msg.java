package com.ego.ext.weixin.common.msg;


import org.w3c.dom.Document;

/**
 * 抽象消息类 提供各种消息类型字段、头部消息对象以及写入和读取抽象方法
 *
 * @see www.yl-blog.com
 * @see http://t.qq.com/wuweiit
 *
 */
public abstract class Msg {

    /**
     * 消息类型
     */
    public enum MsgType  {

        TEXT("text", "文本消息"),
        LOCATION("location", "地理位置消息"),
        IMAGE("image", "图片消息"),
        LINK("link", "链接消息"),
        /**
         * 语音识别消息
         */
        VOICE("voice", "语音消息"),
        EVENT("event", "事件消息"),
        /**
         * 多客服转发消息
         */
        TRANSFER_CUSTOMER_SERVICE("transfer_customer_service", "多客服转发消息"),
        /**
         * 视频消息
         */
        VIDEO("video", "视频消息"),
        /**
         * 单图文消息
         */
        NEWS("news", "单图文消息"),
        /**
         * 多图文
         */
        MP_NEWS("mpnews", "多图文消息"),
        MUSIC("music", "音乐消息"),
        FILE("file", "文件消息"),
        wxcard("wxcard", "卡券");
        private String type;
        private String des;

        MsgType(String type, String des) {
            this.type = type;
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

       
        public String getVal() {
            return this.type;
        }

     
        public String getDes() {
            return this.des;
        }
    }

    /**
     * 消息头
     */
    protected MsgHead head;

    /**
     * 写入实体中的消息内容至document中,用于向微信服务器推送消息
     *
     * @param document 写入信息到document
     */
    public abstract void write(Document document);

    /**
     * 从document读取消息内容 到消息实体，用于接收微信服务器推送的消息
     *
     * @param document
     */
    public abstract void read(Document document);

    /**
     * 获取节点文本内容
     *
     * @param document 文档
     * @param element 节点名称
     * @return 内容
     */
    protected String getElementContent(Document document, String element) {
        if (document.getElementsByTagName(element).getLength() > 0) {// 判断是否有节点 
            return document.getElementsByTagName(element).item(0).getTextContent();
        } else {
            return null;
        }
    }

    public MsgHead getHead() {
        return head;
    }

    public void setHead(MsgHead head) {
        this.head = head;
    }

    public String getToUserName() {
        return head.getToUserName();
    }

    public void setToUserName(String toUserName) {
        head.setToUserName(toUserName);
    }

    public String getFromUserName() {
        return head.getFromUserName();
    }

    public void setFromUserName(String fromUserName) {
        head.setFromUserName(fromUserName);
    }

    public String getCreateTime() {
        return head.getCreateTime();
    }

    public void setCreateTime(String createTime) {
        head.setCreateTime(createTime);
    }

    public void setMsgType(String MsgType) {
        head.setMsgType(MsgType);
    }

    public String getMsgType() {
        return head.getMsgType();
    }

    public Integer getAgentID() {
        return head.getAgentID();
    }
}
