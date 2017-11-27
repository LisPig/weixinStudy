package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

public class SendedTextMassMsg extends SendedMassMsg {

    private Map<String, String> text;

    public SendedTextMassMsg(String content) {
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
        SendedTextMassMsg m = new SendedTextMassMsg("444");
        m.setFilter(new Filter());
        System.out.print(m.toJson());
    }

}
