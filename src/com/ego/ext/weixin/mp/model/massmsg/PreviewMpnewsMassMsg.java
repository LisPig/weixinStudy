package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

public class PreviewMpnewsMassMsg  extends PreviewMassMsg {

    private Map<String, String> mpnews;

    public PreviewMpnewsMassMsg(String media_id) {
        super();
        mpnews = new HashMap();
        mpnews.put("media_id", media_id);
        super.setMsgtype("mpnews");
    }

    public Map<String, String> getMpnews() {
        return mpnews;
    }

    public void setMpnews(Map<String, String> mpnews) {
        this.mpnews = mpnews;
    }

    public static void main(String arg[]) {
        PreviewMpnewsMassMsg m = new PreviewMpnewsMassMsg("444");
      //  m.setFilter(new Filter());
        System.out.print(m.toJson());
    }

}
