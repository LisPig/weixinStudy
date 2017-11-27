package com.ego.ext.weixin.common.msg.event;

import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.MsgHead;
import org.w3c.dom.Document;

/**
 * 扫码推事件的事件推送/扫码推事件且弹出“消息接收中”提示框的事件推送
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * @see
 * http://qydev.weixin.qq.com/wiki/index.php?title=接收事件#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6
 */
public class ScanCodeMenuEventMsg extends MenuEventMsg {

    /**
     * 扫描类型，一般是qrcode
     */
    private String scanType;
    /**
     * 扫描结果，即二维码对应的字符串信息
     */

    private String scanResult;

    public ScanCodeMenuEventMsg() {
        super();
    }

    public ScanCodeMenuEventMsg(MsgHead head) {
        super(head);
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanType = scanResult;
    }

    @Override
    public void read(Document document) {
        this.scanType = getElementContent(document, WeiXinXmlNodeName.scan_type);
        this.scanResult = getElementContent(document, WeiXinXmlNodeName.scan_result);

    }
}
