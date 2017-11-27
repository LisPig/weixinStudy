package com.ego.ext.weixin.common.msg;


import com.ego.core.util.UtilString;
import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.event.EnterAgentMenuEventMsg;
import com.ego.ext.weixin.common.msg.event.LocationEventMsg;
import com.ego.ext.weixin.common.msg.event.LocationSelectMenuEventMsg;
import com.ego.ext.weixin.common.msg.event.MassSendJobEventMsg;
import com.ego.ext.weixin.common.msg.event.MenuEventMsg;
import com.ego.ext.weixin.common.msg.event.PicMenuEventMsg;
import com.ego.ext.weixin.common.msg.event.ScanCodeMenuEventMsg;
import com.ego.ext.weixin.common.msg.event.SubscribeOrScanEventMsg;
import com.ego.ext.weixin.common.msg.event.TemplateEventMsg;
import com.ego.ext.weixin.common.msg.event.UnsubscribeEventMsg;
import java.text.MessageFormat;
import org.w3c.dom.Document;

/**
 * 事件消息 （只能接收）
 *
 * 用户在关注与取消关注公众号时，微信会把这个
 * 微信服务器在五秒内收不到响应会断掉连接，并且重新发起请求，总共重试三次
 *
 * 关于重试的消息排重，推荐使用FromUserName + CreateTime 排重。
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 */
public class EventMsg extends Msg {

    // 回复文本消息内容
    //  private String content;
    /**
     * 消息类型
     */
    public enum EventType {

        /**
         * 订阅事件
         */
        subscribe("subscribe", "订阅事件"),
        /**
         * 取消订阅
         */
        unsubscribe("unsubscribe", "取消订阅"),
        /**
         * 用户已关注时的事件推送
         */
        scan("SCAN", "用户已关注时的事件推送"),
        /**
         * 上报地理位置事件
         */
        location("LOCATION", "上报地理位置事件"),
        /**
         * 自定义菜单点击事件，点击菜单拉取消息时的事件推送，用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
         * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
         */
        click("CLICK", "自定义菜单点击事件"),
        /**
         * 自定义菜单点击事件，点击菜单跳转链接时的事件推送，用户点击view类型按钮后，
         * 微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
         */
        view("VIEW", "自定义菜单点击事件"),
        /**
         * 公众号。事件推送群发结果。
         *
         * @see
         * http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81.E7.BE.A4.E5.8F.91.E7.BB.93.E6.9E.9C
         */
        mass_send_job_finish("MASSSENDJOBFINISH", "公众号,事件推送群发结果"),
        /**
         *
         * 扫码推事件,用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
         */
        scancode_push("scancode_push", "扫码推事件"),
        /**
         * 扫码推事件且弹出“消息接收中”提示框。
         * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
         */
        scancode_waitmsg("scancode_waitmsg", "扫码推事件且弹出“消息接收中”提示框。"),
        /**
         * 弹出系统拍照发图。用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
         */
        pic_sysphoto("pic_sysphoto", "弹出系统拍照发图"),
        /**
         * 弹出拍照或者相册发图。用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
         */
        pic_photo_or_album("pic_photo_or_album", "弹出拍照或者相册发图"),
        /**
         * 弹出微信相册发图器。用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
         */
        pic_weixin("pic_weixin", "弹出微信相册发图器"),
        /**
         * 弹出地理位置选择器。用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
         */
        location_select("location_select", "弹出地理位置选择器"),
        /**
         * 用户进入应用的事件推送
         */
        enter_agent("enter_agent", "用户进入应用的事件推送"),
        /**
         * 事件为模板消息发送结束
         */
        TEMPLATE_SEND_JOB_FINISH("TEMPLATESENDJOBFINISH", "事件为模板消息发送结束"),
        batch_job_result("batch_job_result", "异步任务完成事件推送");

        private String type;
        private String des;

        EventType(String type, String des) {
            this.type = type;
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

       
        public String getVal() {
            return this.type;
        }

  
        public String getDes() {
            return this.des;
        }
    }

    private EventMsg eventMsg;
    // 事件类型,值为subscribe(订阅)、unsubscribe(取消订阅)、CLICK(自定义菜单点击事件)\location\scan
    private String event;

    /**
     * 程序内部调用
     *
     */
    public EventMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.EVENT.getType());//设置消息类型
    }

    public EventMsg(MsgHead head) {
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
        this.event = UtilString.trim(getElementContent(document, WeiXinXmlNodeName.EVENT));
        //取消关注事件
        if (EventType.unsubscribe.getType().equals(this.event)) {
            UnsubscribeEventMsg msg = new UnsubscribeEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
            //关注事件、扫描带参数二维码事件 
        } else if (EventType.subscribe.getType().equals(this.event) || EventType.scan.getType().equals(this.event)) {
            SubscribeOrScanEventMsg msg = new SubscribeOrScanEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
        } else if (EventType.location.getType().equals(this.event)) {
            LocationEventMsg msg = new LocationEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
            // 自定义菜单事件——点击菜单跳转链接时的事件推送 /  点击菜单拉取消息时的事件推送
        } else if (EventType.click.getType().equals(this.event) || EventType.view.getType().equals(this.event)) {
            MenuEventMsg msg = new MenuEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
            // 自定义菜单事件——扫码推事件的事件推送 /  扫码推事件且弹出“消息接收中”提示框的事件推送
        } else if (EventType.pic_sysphoto.getType().equals(this.event) || EventType.pic_photo_or_album.getType().equals(this.event) || EventType.pic_weixin.getType().equals(this.event)) {
            ScanCodeMenuEventMsg msg = new ScanCodeMenuEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
            // 自定义菜单事件——弹出系统拍照发图的事件推送 /  弹出拍照或者相册发图的事件推送 / 弹出微信相册发图器的事件推送
        } else if (EventType.scancode_push.getType().equals(this.event) || EventType.scancode_waitmsg.getType().equals(this.event)) {
            PicMenuEventMsg msg = new PicMenuEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
            // 自定义菜单事件——弹出地理位置选择器的事件推送
        } else if (EventType.location_select.getType().equals(this.event)) {
            LocationSelectMenuEventMsg msg = new LocationSelectMenuEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
            // 自定义菜单事件——用户进入应用的事件推送
        } else if (EventType.enter_agent.getType().equals(this.event)) {
            EnterAgentMenuEventMsg msg = new EnterAgentMenuEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
            //群发推送结果事件
        } else if (EventType.mass_send_job_finish.getType().equals(this.event)) {
            MassSendJobEventMsg msg = new MassSendJobEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
        } else if (EventType.TEMPLATE_SEND_JOB_FINISH.getType().equals(this.event)) {
            TemplateEventMsg msg = new TemplateEventMsg(head);
            msg.read(document);
            this.eventMsg = msg;
        }
    }

    /**
     *
     * @return 返回字符串形式的事件类型
     */
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public EventType getEventType() {
        return EventType.valueOf(this.event);
    }

    /**
     * 获取事件消息，如需要获取具体的事件消息类型，可以进行类型转换
     *
     * @return
     */
    public EventMsg getEventMsg() {
        return this.eventMsg;
    }

    public SubscribeOrScanEventMsg getSubscribeOrScanEventMsg() {

        if (this.eventMsg instanceof SubscribeOrScanEventMsg) {
            return (SubscribeOrScanEventMsg) this.eventMsg;
        }
        return null;
    }

    public UnsubscribeEventMsg getUnsubscribeEventMsg() {
        //取消关注事件
        if (this.eventMsg instanceof UnsubscribeEventMsg) {
            return (UnsubscribeEventMsg) this.eventMsg;
        }
        return null;
    }

    public EnterAgentMenuEventMsg getEnterAgentMenuEventMsg() {

        if (this.eventMsg instanceof EnterAgentMenuEventMsg) {
            return (EnterAgentMenuEventMsg) this.eventMsg;
        }
        return null;
    }

    public LocationEventMsg getLocationEventMsg() {

        if (this.eventMsg instanceof LocationEventMsg) {
            return (LocationEventMsg) this.eventMsg;
        }
        return null;
    }

    public LocationSelectMenuEventMsg getLocationSelectMenuEventMsg() {

        if (this.eventMsg instanceof LocationSelectMenuEventMsg) {
            return (LocationSelectMenuEventMsg) this.eventMsg;
        }
        return null;
    }

    public MassSendJobEventMsg getMassSendJobEventMsg() {

        if (this.eventMsg instanceof MassSendJobEventMsg) {
            return (MassSendJobEventMsg) this.eventMsg;
        }
        return null;
    }

    public MenuEventMsg getMenuEventMsg() {

        if (this.eventMsg instanceof MenuEventMsg) {
            return (MenuEventMsg) this.eventMsg;
        }
        return null;
    }

    public PicMenuEventMsg getPicMenuEventMsg() {

        if (this.eventMsg instanceof PicMenuEventMsg) {
            return (PicMenuEventMsg) this.eventMsg;
        }
        return null;
    }

    public ScanCodeMenuEventMsg getScanCodeMenuEventMsg() {

        if (this.eventMsg instanceof ScanCodeMenuEventMsg) {
            return (ScanCodeMenuEventMsg) this.eventMsg;
        }
        return null;
    }

    public TemplateEventMsg getTemplateEventMsg() {

        if (this.eventMsg instanceof TemplateEventMsg) {
            return (TemplateEventMsg) this.eventMsg;
        }
        return null;
    }

}
