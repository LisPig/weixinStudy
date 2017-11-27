package com.ego.ext.weixin.common.msg;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 图片消息。
 *
 * 回复图片等多媒体消息时需要预先上传多媒体文件到微信服务器，只支持认证服务号。
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=发送被动响应消息
 *
 */
public class ImageMsg extends Msg {

    // 图片链接
    private String picUrl;
    // 消息id，64位整型
    private String msgId;
    // 图片消息媒体id，通过上传多媒体文件，得到的id。
    private String mediaId;

    /**
     * 开发者调用
     *
     */
    public ImageMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.IMAGE.getType());//设置消息类型
    }

    /**
     * 程序内部调用
     *
     * @param head
     */
    public ImageMsg(MsgHead head) {
        this.head = head;
    }
    
    @Override
    public void write(Document document) {
        Element root = document.createElement(WeiXinXmlNodeName.ROOT);
        head.write(root, document);
        Element imageElement = document.createElement(WeiXinXmlNodeName.IMAGE);
        Element mediaIdElement = document.createElement(WeiXinXmlNodeName.MEDIAID);
        mediaIdElement.setTextContent(this.mediaId);
        imageElement.appendChild(mediaIdElement);
        root.appendChild(imageElement);
        document.appendChild(root);
    }
    
    @Override
    public void read(Document document) {
        this.picUrl = super.getElementContent(document, WeiXinXmlNodeName.PIC_URL);
        //this.picUrl = document.getElementsByTagName(WeiXinXmlNodeName.PIC_URL).item(0).getTextContent();
        this.mediaId = super.getElementContent(document, WeiXinXmlNodeName.MEDIAID);
        this.msgId = super.getElementContent(document, WeiXinXmlNodeName.MSG_ID);
        //this.msgId = document.getElementsByTagName(WXXmlElementName.MSG_ID).item(0).getTextContent();
    }
    
    public String getPicUrl() {
        return picUrl;
    }
    
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    /**
     * 消息id，64位整型
     *
     * @return
     */
    public String getMsgId() {
        return msgId;
    }
    
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
     *
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
    
}
