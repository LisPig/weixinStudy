package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

/**
 * 高级群发接口预览群发信息
 *
 * @author Administrator
 */
public class PreviewImageMassMsg  extends PreviewMassMsg{

    private Map<String, String> image;

    public PreviewImageMassMsg(String media_id) {
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
