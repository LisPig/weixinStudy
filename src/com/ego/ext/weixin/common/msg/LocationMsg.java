package com.ego.ext.weixin.common.msg;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import java.text.MessageFormat;
import org.w3c.dom.Document;

/**
 * 地理位置消息（只能接收）
 *
 */
public class LocationMsg extends Msg {

    // 地理位置纬度
    private String location_X;
    // 地理位置经度
    private String location_Y;
    // 地图缩放大小
    private String scale;
    // 地理位置信息
    private String label;
    //消息id，64位整型
    private String msgId;

    /**
     * 开发者调用
     *
     */
    public LocationMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.LOCATION.getType());
    }

    /**
     * 程序内部
     *
     * @param head调用
     *
     */
    public LocationMsg(MsgHead head) {
        this.head = head;
    }

    @Override
    public void write(Document document) {
        throw new RuntimeException(MessageFormat.format("此消息类型{0}都是由微信服务器发送给我们的，我们不能发给微信服务器", super.getMsgType()));
    }

    @Override
    public void read(Document document) {
        this.location_X = document.getElementsByTagName(WeiXinXmlNodeName.LOCATION_X).item(0).getTextContent();
        this.location_Y = document.getElementsByTagName(WeiXinXmlNodeName.LOCATION_Y).item(0).getTextContent();
        this.scale = document.getElementsByTagName(WeiXinXmlNodeName.SCALE).item(0).getTextContent();
        this.label = document.getElementsByTagName(WeiXinXmlNodeName.LABEL).item(0).getTextContent();
        this.msgId = document.getElementsByTagName(WeiXinXmlNodeName.MSG_ID).item(0).getTextContent();
    }

    public String getLocation_X() {
        return location_X;
    }

    public void setLocation_X(String location_X) {
        this.location_X = location_X;
    }

    public String getLocation_Y() {
        return location_Y;
    }

    public void setLocation_Y(String location_Y) {
        this.location_Y = location_Y;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
