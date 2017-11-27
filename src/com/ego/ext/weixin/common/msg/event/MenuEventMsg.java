package com.ego.ext.weixin.common.msg.event;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.MsgHead;
import org.w3c.dom.Document;

/**
 * 自定义菜单事件--------点击菜单拉取消息时的事件推送、点击菜单跳转链接时的事件推送
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * @see
 * http://qydev.weixin.qq.com/wiki/index.php?title=接收事件#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6
 */
public class MenuEventMsg extends EventMsg {

    /**
     * Event:事件类型，CLICK ----------事件KEY值，与自定义菜单接口中KEY值对应<br>
     *
     * Event:事件类型，VIEW -----------事件KEY值，设置的跳转URL <br>
     *
     * Event:事件类型，scancode_push ----------事件KEY值，由开发者在创建菜单时设定<br>
     *
     * Event:事件类型，scancode_waitmsg-----------事件KEY值，由开发者在创建菜单时设定 <br>
     *
     * Event:事件类型，pic_sysphoto ----------事件KEY值，由开发者在创建菜单时设定<br>
     *
     * Event:事件类型，pic_photo_or_album-----------事件KEY值，由开发者在创建菜单时设定 <br>
     *
     * Event:事件类型，pic_weixin-----------事件KEY值，由开发者在创建菜单时设定 <br>
     *
     * Event:事件类型，location_select-----------事件KEY值，由开发者在创建菜单时设定 <br>
     *
     * Event:事件类型，enter_agent-----------事件KEY值，此事件该值为空 <br>
     *
     *
     */
    public MenuEventMsg() {
        super();
    }

    public MenuEventMsg(MsgHead head) {
        super(head);
    }

    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Override
    public void read(Document document) {
        this.eventKey = super.getElementContent(document, WeiXinXmlNodeName.EVENT_KEY);
    }

}
