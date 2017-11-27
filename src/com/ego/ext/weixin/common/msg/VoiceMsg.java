/**
 *
 * 吴伟 版权所有
 */
package com.ego.ext.weixin.common.msg;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 语音消息（包括识别结果，如果开通语音识别功能，用户每次发送语音给公众号时，微信会在推送的语音消息XML数据包中，增加一个Recongnition字段。）
 *
 *
 */
public class VoiceMsg extends Msg {

    // 语音消息媒体id，可以调用多媒体文件下载接口拉取该媒体
    private String mediaId;
    // 语音格式：amr
    private String format;
    // 语音识别结果，UTF8编码
    private String recognition;
    // 消息id，64位整型
    private String msgId;

    /**
     * 默认构造
     */
    public VoiceMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.VOICE.getType());
    }

    public VoiceMsg(MsgHead head) {
        this.head = head;
    }

    @Override
    public void write(Document document) {
        Element root = document.createElement(WeiXinXmlNodeName.ROOT);
        head.write(root, document);
        Element voiceElement = document.createElement(WeiXinXmlNodeName.VOICE);
        Element mediaIdElement = document.createElement(WeiXinXmlNodeName.MEDIAID);
        mediaIdElement.setTextContent(this.mediaId);
        voiceElement.appendChild(mediaIdElement);
        root.appendChild(voiceElement);
        document.appendChild(root);
    }

    @Override
    public void read(Document document) {
        this.mediaId = getElementContent(document, WeiXinXmlNodeName.MEDIAID);
        this.format = getElementContent(document, WeiXinXmlNodeName.FORMAT);
        this.recognition = getElementContent(document, WeiXinXmlNodeName.RECOGNITION);
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
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the recognition
     */
    public String getRecognition() {
        return recognition;
    }

    /**
     * @param recognition the recognition to set
     */
    public void setRecognition(String recognition) {
        this.recognition = recognition;
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

}
