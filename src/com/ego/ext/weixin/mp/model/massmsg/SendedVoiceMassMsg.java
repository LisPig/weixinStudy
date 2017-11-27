package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

public class SendedVoiceMassMsg extends SendedMassMsg {

    private Map<String, String> voice;

    public SendedVoiceMassMsg(String media_id) {
        super();
        voice = new HashMap<String, String>();
        voice.put("media_id", media_id);
        super.msgtype = "voice";
    }

    public Map<String, String> getVoice() {
        return voice;
    }

    public void setVoice(Map<String, String> voice) {
        this.voice = voice;
    }

    public static void main(String arg[]) {
        SendedVoiceMassMsg m = new SendedVoiceMassMsg("444");
        m.setFilter(new Filter());
        System.out.print(m.toJson());
    }
}
