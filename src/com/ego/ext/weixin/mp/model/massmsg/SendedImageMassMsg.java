package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

public class SendedImageMassMsg extends SendedMassMsg {

    private Map<String, String> image;

    public SendedImageMassMsg(String media_id) {
        super();
        image = new HashMap();
        image.put("media_id", media_id);
        super.msgtype = "image";
    }

    public Map<String, String> getImage() {
        return image;
    }

    public void setImage(Map<String, String> image) {
        this.image = image;
    }

}
