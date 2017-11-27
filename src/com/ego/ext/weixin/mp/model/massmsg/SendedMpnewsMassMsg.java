package com.ego.ext.weixin.mp.model.massmsg;

import com.ego.ext.weixin.common.msg.Msg;
import java.util.HashMap;
import java.util.Map;

public class SendedMpnewsMassMsg extends SendedMassMsg {

    private Map<String, String> mpnews;

    public SendedMpnewsMassMsg(String media_id) {
        super();
        mpnews = new HashMap();
        mpnews.put("media_id", media_id);
        super.setMsgtype(Msg.MsgType.MP_NEWS.getType());
    }

    public Map<String, String> getMpnews() {
        return mpnews;
    }

    public void setMpnews(Map<String, String> mpnews) {
        this.mpnews = mpnews;
    }

    public static void main(String arg[]) {
        SendedMpnewsMassMsg m = new SendedMpnewsMassMsg("444");
        m.setFilter(new Filter());
        System.out.print(m.toJson());
    }

}
