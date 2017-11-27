package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

public class PreviewTextMassMsg  extends PreviewMassMsg {

    private Map<String, String> text;

    public PreviewTextMassMsg(String content) {
        super();
        text = new HashMap<String, String>();
        text.put("content", content);
        super.msgtype = "text";
    }

    public Map<String, String> getText() {
        return text;
    }

    public void setText(Map<String, String> text) {
        this.text = text;
    }

    public static void main(String arg[]) {
        PreviewTextMassMsg m = new PreviewTextMassMsg("444");
       
        System.out.print(m.toJson());
    }

}
