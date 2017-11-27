package com.ego.ext.weixin.common.msg.event;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.MsgHead;
import org.w3c.dom.Document;

/**
 *
 * 公众号。推送群发结果事件。
 * 由于群发任务提交后，群发任务可能在一定时间后才完成，因此，群发接口调用时，仅会给出群发任务是否提交成功的提示，若群发任务提交成功，则在群发任务结束时，会向开发者在公众平台填写的开发者URL（callback
 * URL）推送事件。
 *
 * @see
 * http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E4.BA.8B.E4.BB.B6.E6.8E.A8.E9.80.81.E7.BE.A4.E5.8F.91.E7.BB.93.E6.9E.9C
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * @see
 * http://qydev.weixin.qq.com/wiki/index.php?title=接收事件#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6
 */
public class MassSendJobEventMsg extends EventMsg {

    //群发的消息ID
    private String MsgID;
    //群发的结构，为“send success”或“send fail”或“err(num)”。但send success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，可能的情况如下：
    //err(10001), //涉嫌广告 err(20001), //涉嫌政治 err(20004), //涉嫌社会 err(20002), //涉嫌色情 err(20006), //涉嫌违法犯罪 err(20008), //涉嫌欺诈 err(20013), //涉嫌版权 err(22000), //涉嫌互推(互相宣传) err(21000), //涉嫌其他
    private String Status;
    //group_id下粉丝数；或者openid_list中的粉丝数
    private String TotalCount;
    //过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount = SentCount + ErrorCount
    private String FilterCount;
    //发送成功的粉丝数
    private String SentCount;
    //发送失败的粉丝数
    private String ErrorCount;

    public MassSendJobEventMsg() {
        super();
    }

    public MassSendJobEventMsg(MsgHead head) {
        super(head);
    }

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

    @Override
    public void read(Document document) {
        this.setMsgID(getElementContent(document, WeiXinXmlNodeName.MSG_ID));
        this.setStatus(getElementContent(document, WeiXinXmlNodeName.STATUS));
        this.setTotalCount(getElementContent(document, WeiXinXmlNodeName.TOTAL_COUNT));
        this.setFilterCount(getElementContent(document, WeiXinXmlNodeName.FILTER_COUNT));
        this.setSentCount(getElementContent(document, WeiXinXmlNodeName.SENT_COUNT));
        this.setErrorCount(getElementContent(document, WeiXinXmlNodeName.ERROR_COUNT));
    }
}
