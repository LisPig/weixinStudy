package com.ego.ext.weixin.common.msg.event;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.MsgHead;
import org.w3c.dom.Document;

/**
 *
 * 关注事件、扫描带参数二维码事件.
 *
 * 用户扫描带场景值二维码时，可能推送以下两种事件：
 *
 * 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
 * 如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * @see
 * http://qydev.weixin.qq.com/wiki/index.php?title=接收事件#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6
 */
public class SubscribeOrScanEventMsg extends UnsubscribeEventMsg {

    private String eventKey;

    private String ticket;

    public SubscribeOrScanEventMsg() {
        super();
    }

    public SubscribeOrScanEventMsg(MsgHead head) {
        super(head);
    }

    /**
     * 1. 用户未关注时，进行关注后的事件推送:事件KEY值，qrscene_为前缀，后面为二维码的参数值<BR>
     *
     * 2. 用户已关注时的事件推送:事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
     *
     * @return
     */
    public String getEventKey() {
        return eventKey;
    }

    public String getScene_id() {
        if (this.eventKey != null && this.eventKey.startsWith("qrscene_")) {
            return this.eventKey.replaceFirst("qrscene_", "");
        } else if (this.eventKey != null) {
            return this.eventKey;
        }
        return null;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    /**
     * 1. 用户未关注时，进行关注后的事件推送:二维码的ticket，可用来换取二维码图片<BR>
     *
     * 2. 用户已关注时的事件推送:二维码的ticket，可用来换取二维码图片
     *
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * 判断是否是用户已经关注 扫描带参数二维码事件，返回true则是
     *
     * @return
     */
    public boolean isScan() {
        return this.eventKey != null && !this.eventKey.startsWith("qrscene_");
    }

    @Override
    public void read(Document document) {
        this.eventKey = getElementContent(document, WeiXinXmlNodeName.EVENT_KEY);
        this.ticket = getElementContent(document, WeiXinXmlNodeName.TICKET);
    }

    public static void main(String arg[]) {
        System.out.print("qrscene_123456".replaceFirst("qrscene_", ""));
    }
}
