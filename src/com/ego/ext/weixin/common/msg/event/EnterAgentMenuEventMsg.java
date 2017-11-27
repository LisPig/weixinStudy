package com.ego.ext.weixin.common.msg.event;

import com.ego.ext.weixin.common.msg.MsgHead;
import org.w3c.dom.Document;

/**
 * 用户进入应用的事件推送.本事件只有在应用的回调模式中打开上报开关时上报
 *
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * @see
 * http://qydev.weixin.qq.com/wiki/index.php?title=接收事件#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6
 */
public class EnterAgentMenuEventMsg extends MenuEventMsg {

    public EnterAgentMenuEventMsg() {
        super();
    }

    public EnterAgentMenuEventMsg(MsgHead head) {
        super(head);
    }

    @Override
    public void read(Document document) {

    }
}
