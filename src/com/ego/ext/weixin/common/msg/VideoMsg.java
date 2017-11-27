package com.ego.ext.weixin.common.msg;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 视频消息
 *
 */
public class VideoMsg extends Msg {

    // 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String mediaId;
    // 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
    private String thumbMediaId;
    // 消息id，64位整型
    private String msgId;

    private String title;
    private String description;

    /**
     * 开发者调用
     *
     */
    public VideoMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.VIDEO.getType());
    }

    /**
     * @param head
     */
    public VideoMsg(MsgHead head) {
        this.head = head;
    }

    @Override
    public void write(Document document) {
        Element root = document.createElement(WeiXinXmlNodeName.ROOT);
        head.write(root, document);
        Element videoElement = document.createElement(WeiXinXmlNodeName.VIDEO);

        Element mediaIdElement = document.createElement(WeiXinXmlNodeName.MEDIAID);
        mediaIdElement.setTextContent(this.mediaId);

        Element thumbMediaIdElement = document.createElement(WeiXinXmlNodeName.THUMBMEDIAID);
        thumbMediaIdElement.setTextContent(this.thumbMediaId);

        Element titleElement = document.createElement(WeiXinXmlNodeName.TITLE);
        titleElement.setTextContent(this.title);

        Element descriptionElement = document.createElement(WeiXinXmlNodeName.DESCRITION);
        titleElement.setTextContent(this.description);

        videoElement.appendChild(mediaIdElement);
        videoElement.appendChild(thumbMediaIdElement);
        videoElement.appendChild(titleElement);
        videoElement.appendChild(descriptionElement);

        root.appendChild(videoElement);
        document.appendChild(root);
    }

    // 因为用户不能发送音乐消息给我们，因此没有实现
    @Override
    public void read(Document document) {
        this.mediaId = getElementContent(document, WeiXinXmlNodeName.MEDIAID);
        this.thumbMediaId = getElementContent(document, WeiXinXmlNodeName.THUMBMEDIAID);
        this.msgId = getElementContent(document, WeiXinXmlNodeName.MSG_ID);
    }

    /**
     * @return the mediaId
     */
    public String getMediaId() {
        return mediaId;
    }

    /**
     * @param mediaId the mediaId to set
     */
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    /**
     * @return the thumbMediaId
     */
    public String getThumbMediaId() {
        return thumbMediaId;
    }

    /**
     * @param thumbMediaId the thumbMediaId to set
     */
    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    /**
     * @return the msgId
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId the msgId to set
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
