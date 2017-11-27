package com.ego.ext.weixin.common.msg.event;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.MsgHead;
import org.w3c.dom.Document;

/**
 * 弹出地理位置选择器的事件推送
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * @see
 * http://qydev.weixin.qq.com/wiki/index.php?title=接收事件#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6
 */
public class LocationSelectMenuEventMsg extends MenuEventMsg {

    // 地理位置纬度
    private String location_X;
    // 地理位置经度
    private String location_Y;
    // 地图缩放大小
    private String scale;
    // 地理位置信息
    private String label;
    //朋友圈POI的名字，可能为空
    private String poiname;

    public LocationSelectMenuEventMsg() {
        super();
    }

    public LocationSelectMenuEventMsg(MsgHead head) {
        super(head);
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

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }

    @Override
    public void read(Document document) {
        this.location_X = super.getElementContent(document, WeiXinXmlNodeName.LOCATION_X);
        this.location_Y = super.getElementContent(document, WeiXinXmlNodeName.LOCATION_Y);
        this.scale = super.getElementContent(document, WeiXinXmlNodeName.SCALE);
        this.label = super.getElementContent(document, WeiXinXmlNodeName.LABEL);
        this.poiname = super.getElementContent(document, WeiXinXmlNodeName.poiname);
    }
}
