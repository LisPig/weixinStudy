package com.ego.ext.weixin.common.msg;

import com.ego.core.util.ToolKit;
import com.ego.core.util.UtilTime;
import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 微信消息头
 *
 */
public class MsgHead {

    // 开发者微信号
    private String toUserName;
    // 发送方帐号（一个OpenID）
    private String fromUserName;
    // 消息创建时间 （整型）
    private String createTime;
    // 消息类型：text\image\
    private String msgType;
    //区别企业号与公众号。企业应用的id，整型。可在应用的设置页面查看,被动响应消息不带此参数，主动发送信息要带
    private Integer agentID;

    /**
     * 一般由程序内部调用，开发者不用调用
     *
     */
    public MsgHead() {
        this.createTime = UtilTime.getDate();//初始化创建时间
    }

    /**
     * 写入消息头中的消息内容至document中
     *
     * @param root 消息的根节点
     * @param document 写入信息到document
     */
    public void write(Element root, Document document) {
        Element toUserNameElement = document
                .createElement(WeiXinXmlNodeName.TO_USER_NAME);
        toUserNameElement.setTextContent(this.toUserName);
        Element fromUserNameElement = document
                .createElement(WeiXinXmlNodeName.FROM_USER_NAME);
        fromUserNameElement.setTextContent(this.fromUserName);
        Element createTimeElement = document
                .createElement(WeiXinXmlNodeName.CREATE_TIME);
        createTimeElement.setTextContent(this.createTime);
        Element msgTypeElement = document
                .createElement(WeiXinXmlNodeName.MSG_TYPE);
        msgTypeElement.setTextContent(this.msgType);

        root.appendChild(toUserNameElement);
        root.appendChild(fromUserNameElement);
        root.appendChild(createTimeElement);
        root.appendChild(msgTypeElement);
    }

    public void read(Document document) {
        this.toUserName = document
                .getElementsByTagName(WeiXinXmlNodeName.TO_USER_NAME).item(0)
                .getTextContent();
        this.fromUserName = document
                .getElementsByTagName(WeiXinXmlNodeName.FROM_USER_NAME).item(0)
                .getTextContent();
        this.createTime = document
                .getElementsByTagName(WeiXinXmlNodeName.CREATE_TIME).item(0)
                .getTextContent();
        this.msgType = document.getElementsByTagName(WeiXinXmlNodeName.MSG_TYPE)
                .item(0).getTextContent();
        NodeList agent = document.getElementsByTagName(WeiXinXmlNodeName.AGENT_ID);
        if (agent != null && agent.getLength() > 0) {
            this.agentID = ToolKit.object2Int(agent.item(0).getTextContent());
        }

    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * 区别企业号与公众号。企业应用的id，整型。可在应用的设置页面查看,被动响应消息不带此参数，主动发送信息要带
     *
     * @return
     */
    public Integer getAgentID() {
        return this.agentID;
    }
    /*
     public void setAgentID(int agentID) {
     this.agentID = agentID;
     }
     */

}
