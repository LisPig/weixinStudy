package com.ego.ext.weixin.mp.model.massmsg;

import com.ego.ext.weixin.common.msg.Msg;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡券消息
 *
 * @author Administrator
 */
public class SendedCardMassMsg extends SendedMassMsg {

    private Map<String, String> wxcard;

    /**
     *
     * @param card_id
     */
    public SendedCardMassMsg(String card_id) {
        super();
        wxcard = new HashMap<String, String>();
        wxcard.put("card_id", card_id);
        super.msgtype = Msg.MsgType.wxcard.getType();
    }

    public Map<String, String> getWxcard() {
        return wxcard;
    }

    /**
     * @
     * @param wxcard
     */
    public void setWxcard(Map<String, String> wxcard) {
        this.wxcard = wxcard;
    }

    public void setWxcardId(String card_id) {
        wxcard.put("card_id", card_id);
    }

    public void getWxcardId() {
        wxcard.get("card_id");
    }

}
