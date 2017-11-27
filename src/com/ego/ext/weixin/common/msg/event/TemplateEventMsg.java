package com.ego.ext.weixin.common.msg.event;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.MsgHead;
import org.w3c.dom.Document;

/**
 *
 * 模版消息发送任务完成通知事件.
 *
 * 在模版消息发送任务完成后，微信服务器会将是否送达成功作为通知，发送到开发者中心中填写的服务器配置地址中。
 *
 * @see http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
 *
 */
public class TemplateEventMsg extends EventMsg {

    private String MsgID;
    private String Status;

    public enum TemplateEventResultType {

        success("success", "送达成功"),
        failed_user_block("failed:user block", "送达由于用户拒收（用户设置拒绝接收公众号消息）而失败时"),
        failed_system_failed("failed:system failed", "其他原因发送失败");
        private String type;
        private String des;

        TemplateEventResultType(String type, String des) {
            this.type = type;
            this.des = des;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public TemplateEventMsg() {
        super();
    }

    public TemplateEventMsg(MsgHead head) {
        super(head);
    }

    /**
     * @return 模板信息消息id
     */
    public String getMsgID() {
        return MsgID;
    }

    /**
     * @param MsgID
     */
    public void setMsgID(String MsgID) {
        this.MsgID = MsgID;
    }

    /**
     * @return 模板信息发送状态
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getStatusDescription() {
        return TemplateEventResultType.valueOf(this.Status).getType();
    }

    @Override
    public void read(Document document) {
        this.MsgID = getElementContent(document, WeiXinXmlNodeName.MSG_ID);
        this.Status = getElementContent(document, WeiXinXmlNodeName.STATUS);

    }

}
