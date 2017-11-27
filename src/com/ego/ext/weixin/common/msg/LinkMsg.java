package com.ego.ext.weixin.common.msg;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import java.text.MessageFormat;
import org.w3c.dom.Document;

public class LinkMsg extends Msg {

    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息描述
     */
    private String description;
    //消息链接
    private String url;
    //消息id，64位整型
    private String msgId;

    /**
     * 开发者调用创建实例
     *
     */
    public LinkMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.LINK.getType());
    }

    /**
     * 推送来的消息采用此构造
     *
     * @param head
     */
    public LinkMsg(MsgHead head) {
        this.head = head;
    }

    @Override
    public void write(Document document) {
        throw new RuntimeException(MessageFormat.format("此消息类型{0}都是由微信服务器发送给我们的，我们不能发给微信服务器", super.getMsgType()));
    }

    @Override
    public void read(Document document) {
        this.setTitle(document.getElementsByTagName(WeiXinXmlNodeName.TITLE).item(0).getTextContent());
        this.setDescription(document.getElementsByTagName(WeiXinXmlNodeName.DESCRITION).item(0).getTextContent());
        this.setUrl(document.getElementsByTagName(WeiXinXmlNodeName.URL).item(0).getTextContent());
        this.setMsgId(document.getElementsByTagName(WeiXinXmlNodeName.MSG_ID).item(0).getTextContent());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
