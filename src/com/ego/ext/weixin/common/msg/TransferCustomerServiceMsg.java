package com.ego.ext.weixin.common.msg;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import java.text.MessageFormat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 多客服触发信息,目前只支持公众号.消息转发到多客服
 *
 * @see http://mp.weixin.qq.com/wiki/5/ae230189c9bd07a6b221f48619aeef35.html
 *
 */
public class TransferCustomerServiceMsg extends Msg {

    private String KfAccount;

    /**
     * 默认构造 初始化head对象，主要由开发者调用
     *
     */
    public TransferCustomerServiceMsg() {
        this.head = new MsgHead();
        this.head.setMsgType(Msg.MsgType.TRANSFER_CUSTOMER_SERVICE.getType());// 设置消息类型
    }

    /**
     * 此构造由程序内部接收微信服务器消息调用
     *
     * @param head
     */
    public TransferCustomerServiceMsg(MsgHead head) {
        this.head = head;
    }

    public String getKfAccount() {
        return KfAccount;
    }

    /**
     * 消息转发到指定客服
     *
     * 如果您有多个客服人员同时登陆了多客服并且开启了自动接入在进行接待，每一个客户的消息转发给多客服时，多客服系统会将客户分配给其中一个客服人员。如果您希望将某个客户的消息转给指定的客服来接待，可以在返回transfer_customer_service消息时附上TransInfo信息指定一个客服帐号。
     * 需要注意，如果指定的客服没有接入能力(不在线、没有开启自动接入或者自动接入已满)，该用户会一直等待指定客服有接入能力后才会被接入，而不会被其他客服接待。建议在指定客服时，先查询客服的接入能力（获取在线客服接待信息接口），指定到有能力接入的客服，保证客户能够及时得到服务。
     *
     * @see http://mp.weixin.qq.com/wiki/5/ae230189c9bd07a6b221f48619aeef35.html
     * @param KfAccount 指定会话接入的客服账号
     */
    public void setKfAccount(String KfAccount) {
        this.KfAccount = KfAccount;
    }

    @Override
    public void write(Document document) {
        Element root = document.createElement(WeiXinXmlNodeName.ROOT);
        head.write(root, document);
        if (this.KfAccount != null) {
            Element transInfoElement = document.createElement(WeiXinXmlNodeName.TRANSINFO);
            Element kfAccountElement = document.createElement(WeiXinXmlNodeName.KF_ACCOUNT);
            kfAccountElement.setTextContent(this.KfAccount);
            transInfoElement.appendChild(kfAccountElement);
            root.appendChild(transInfoElement);
        }
        document.appendChild(root);

    }

    @Override
    public void read(Document document) {
        throw new RuntimeException(MessageFormat.format("此消息类型{0}都是由我们发送给微信服务器的", super.getMsgType()));
    }

}
