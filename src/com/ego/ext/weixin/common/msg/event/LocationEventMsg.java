package com.ego.ext.weixin.common.msg.event;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.MsgHead;
import org.w3c.dom.Document;

/**
 *
 * 上报地理位置事件.
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * @see
 * http://qydev.weixin.qq.com/wiki/index.php?title=接收事件#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6
 */
public class LocationEventMsg extends EventMsg {

    private String latitude;//地理位置纬度
    private String longitude;//地理位置经度
    private String precision;//地理位置精度

    public LocationEventMsg() {
        super();
    }

    public LocationEventMsg(MsgHead head) {
        super(head);
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the precision
     */
    public String getPrecision() {
        return precision;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(String precision) {
        this.precision = precision;
    }

    @Override
    public void read(Document document) {
        this.latitude = getElementContent(document, WeiXinXmlNodeName.LATITUDE);
        this.longitude = getElementContent(document, WeiXinXmlNodeName.LONGITUDE);
        this.precision = getElementContent(document, WeiXinXmlNodeName.PRECISION);

    }

}
