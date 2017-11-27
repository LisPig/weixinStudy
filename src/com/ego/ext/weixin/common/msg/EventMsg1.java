package com.ego.ext.weixin.common.msg;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import java.text.MessageFormat;
import org.w3c.dom.Document;

/**
 * 事件消息 （只能接收）
 *
 * 用户在关注与取消关注公众号时，微信会把这个事件推送到开发者填写的URL。方便开发者给用户下发欢迎消息或者做帐号的解绑。
 *
 * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次
 *
 * 关于重试的消息排重，推荐使用FromUserName + CreateTime 排重。
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 */
public class EventMsg1 extends Msg {

    // 回复文本消息内容
    //  private String content;
    /**
     * 消息类型
     */
    public enum EventType {

        /**
         * 订阅事件
         */
        subscribe("subscribe"),
        /**
         * 取消订阅
         */
        unsubscribe("unsubscribe"),
        /**
         * 用户已关注时的事件推送
         */
        scan("scan"),
        /**
         * 上报地理位置事件
         */
        location("LOCATION"),
        /**
         * 自定义菜单点击事件，点击菜单拉取消息时的事件推送，用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
         * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
         */
        click("CLICK"),
        /**
         * 自定义菜单点击事件，点击菜单跳转链接时的事件推送，用户点击view类型按钮后，
         * 微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
         */
        view("VIEW"),
        /**
         * 目前只支持企业号。
         * 扫码推事件,用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
         */
        scancode_push("scancode_push"),
        /**
         * 目前只支持企业号。扫码推事件且弹出“消息接收中”提示框。
         * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
         */
        scancode_waitmsg("scancode_waitmsg"),
        /**
         * 目前只支持企业号。弹出系统拍照发图。用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
         */
        pic_sysphoto("pic_sysphoto"),
        /**
         * 目前只支持企业号。弹出拍照或者相册发图。用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
         */
        pic_photo_or_album("pic_photo_or_album"),
        /**
         * 目前只支持企业号。弹出微信相册发图器。用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
         */
        pic_weixin("pic_weixin"),
        /**
         * 目前只支持企业号。弹出地理位置选择器。用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
         */
        location_select("location_select");

        private String type;

        EventType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    private EventMsg1 eventMsg;
    // 事件类型,值为subscribe(订阅)、unsubscribe(取消订阅)、CLICK(自定义菜单点击事件)\location\scan
    private String event;
    //事件类型为subscribe：事件KEY值，qrscene_为前缀，后面为二维码的参数值；
    //事件类型为SCAN：事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
    //事件类型为CLICK：事件KEY值，与自定义菜单接口中KEY值对应
    //事件类型为VIEW：事件KEY值，设置的跳转URL
    private String eventKey;

    //事件类型为subscribe：二维码的ticket，可用来换取二维码图片
    //事件类型为SCAN：二维码的ticket，可用来换取二维码图片
    private String ticket;

    private String latitude;//地理位置纬度
    private String longitude;//地理位置经度
    private String precision;//地理位置精度

    private MassSendResultEvent massEvent = new MassSendResultEvent();

    /**
     * 程序内部调用
     *
     */
    public EventMsg1() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.EVENT.getType());//设置消息类型
    }

    public EventMsg1(MsgHead head) {
        this.head = head;
    }

    /**
     * 因为此消息都是由微信服务器发送给我们的，我们不用发给微信服务器，因此不用实现write.如果需要回复客户调用客户接口
     *
     */
    @Override
    public void write(Document document) {
        // Element root = document.createElement(WeiXinXmlNodeName.ROOT);
        //  head.write(root, document);
        // Element contentElement = document.createElement(WeiXinXmlNodeName.CONTENT);
        // contentElement.setTextContent(this.content);
        // root.appendChild(contentElement);
        // document.appendChild(root);
        throw new RuntimeException(MessageFormat.format("此消息类型{0}都是由微信服务器发送给我们的，我们不能发给微信服务器", super.getMsgType()));
    }

    @Override
    public void read(Document document) {
        this.event = getElementContent(document, WeiXinXmlNodeName.EVENT);
        //扫描带参数二维码事件
        //用户未关注时，进行关注后的事件推送
        //用户已关注时的事件推送
        if (EventType.subscribe.getType().equals(this.event) || EventType.scan.getType().equals(this.event)) {
            this.eventKey = getElementContent(document, WeiXinXmlNodeName.EVENT_KEY);
            this.ticket = getElementContent(document, WeiXinXmlNodeName.TICKET);
        } else if (EventType.location.getType().equals(this.event)) {// 上报地理位置事件
            this.latitude = getElementContent(document, WeiXinXmlNodeName.LATITUDE);
            this.longitude = getElementContent(document, WeiXinXmlNodeName.LONGITUDE);
            this.precision = getElementContent(document, WeiXinXmlNodeName.PRECISION);
        } else if (EventType.click.getType().equals(this.event) || EventType.view.getType().equals(this.event)) {// 自定义菜单事件
            this.eventKey = getElementContent(document, WeiXinXmlNodeName.EVENT_KEY);
        } else {
            this.massEvent.setMsgID(getElementContent(document, WeiXinXmlNodeName.MSG_ID));
            this.massEvent.setStatus(getElementContent(document, WeiXinXmlNodeName.STATUS));
            this.massEvent.setTotalCount(getElementContent(document, WeiXinXmlNodeName.TOTAL_COUNT));
            this.massEvent.setFilterCount(getElementContent(document, WeiXinXmlNodeName.FILTER_COUNT));
            this.massEvent.setSentCount(getElementContent(document, WeiXinXmlNodeName.SENT_COUNT));
            this.massEvent.setErrorCount(getElementContent(document, WeiXinXmlNodeName.ERROR_COUNT));
        }
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    /**
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
    /*
     public void setReplyContent(String content) {
     this.content = content;
     }
     */

    public MassSendResultEvent getMassSendResultEvent() {
        return this.massEvent;
    }

    protected class MassSendResultEvent {

        private String MsgID;
        private String Status;
        private String TotalCount;
        private String FilterCount;
        private String SentCount;
        private String ErrorCount;

        public String getMsgID() {
            return MsgID;
        }

        public void setMsgID(String MsgID) {
            this.MsgID = MsgID;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public void setTotalCount(String TotalCount) {
            this.TotalCount = TotalCount;
        }

        public String getTotalCount() {
            return TotalCount;
        }

        public void setFilterCount(String FilterCount) {
            this.FilterCount = FilterCount;
        }

        public String getFilterCount() {
            return FilterCount;
        }

        public void setSentCount(String SentCount) {
            this.SentCount = SentCount;
        }

        public String getSentCount() {
            return SentCount;
        }

        public void setErrorCount(String ErrorCount) {
            this.ErrorCount = ErrorCount;
        }

        public String getErrorCount() {
            return ErrorCount;
        }
    }

}
