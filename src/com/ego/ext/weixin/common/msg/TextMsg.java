package com.ego.ext.weixin.common.msg;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 文本消息(接收、发送）
 *
 */
public class TextMsg extends Msg {

    // 文本消息内容
    private String content;
    // 消息id，64位整型
    private String msgId;

    /**
     * 默认构造 初始化head对象，主要由开发者调用
     *
     */
    public TextMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.TEXT.getType());// 设置消息类型
    }

    /**
     * 此构造由程序内部接收微信服务器消息调用
     *
     * @param head
     */
    public TextMsg(MsgHead head) {
        this.head = head;
    }
    
    @Override
    public void write(Document document) {
        Element root = document.createElement(WeiXinXmlNodeName.ROOT);
        head.write(root, document);
        Element contentElement = document.createElement(WeiXinXmlNodeName.CONTENT);
        contentElement.setTextContent(this.content);
        root.appendChild(contentElement);
        document.appendChild(root);
    }
    
    @Override
    public void read(Document document) {
        this.content = document.getElementsByTagName(WeiXinXmlNodeName.CONTENT).item(0).getTextContent();
        this.msgId = document.getElementsByTagName(WeiXinXmlNodeName.MSG_ID).item(0).getTextContent();
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getMsgId() {
        return msgId;
    }
    
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    public static void main(String arg[]) {
        TextMsg t = new TextMsg();
        t.setMsgId("222");
        System.out.print(JSON.toJSONString(t));
    }
}
