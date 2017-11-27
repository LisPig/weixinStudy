package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

public class PreviewVoiceMassMsg  extends PreviewMassMsg {

    private Map<String, String> voice;

    public PreviewVoiceMassMsg(String media_id) {
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
        PreviewVoiceMassMsg m = new PreviewVoiceMassMsg("444");
       
        System.out.print(m.toJson());
    }
}
