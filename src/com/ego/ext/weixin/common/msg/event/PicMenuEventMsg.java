package com.ego.ext.weixin.common.msg.event;

import com.ego.core.util.ToolKit;
import com.ego.ext.weixin.common.model.ct.WeiXinXmlNodeName;
import com.ego.ext.weixin.common.msg.MsgHead;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * 弹出系统拍照发图的事件推送/弹出拍照或者相册发图的事件推送/弹出微信相册发图器的事件推送
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * @see
 * http://qydev.weixin.qq.com/wiki/index.php?title=接收事件#.E4.B8.8A.E6.8A.A5.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E4.BA.8B.E4.BB.B6
 */
public class PicMenuEventMsg extends MenuEventMsg {

    /**
     * 发送的图片数量<br>
     *
     */
    private int count;
    private List<String> picList;

    public PicMenuEventMsg() {
        super();
    }

    public PicMenuEventMsg(MsgHead head) {
        super(head);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getPicList() {
        return this.picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    @Override
    public void read(Document document) {
        this.count = ToolKit.object2Int(super.getElementContent(document, WeiXinXmlNodeName.count));
        NodeList items = document.getElementsByTagName(WeiXinXmlNodeName.pic_md5_sum);
        for (int i = 0, len = items.getLength(); i < len; i++) {
            this.picList.add(items.item(i).getTextContent());
        }
    }

}
