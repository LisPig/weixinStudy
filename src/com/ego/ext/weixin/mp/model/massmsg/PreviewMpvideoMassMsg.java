package com.ego.ext.weixin.mp.model.massmsg;

import java.util.HashMap;
import java.util.Map;

/**
 * 仅适用于根据分组groupid群发接口时
 *
 * @author Administrator
 */
public class PreviewMpvideoMassMsg  extends PreviewMassMsg {

    private Map<String, String> mpvideo;

    /**
     *
     * @param media_id
     */
    public PreviewMpvideoMassMsg(String media_id) {
        super();
        mpvideo = new HashMap<String, String>();
        mpvideo.put("media_id", media_id);
        super.msgtype = "mpvideo";
    }

    public Map<String, String> getMpvideo() {
        return mpvideo;
    }

    public void setMpvideo(Map<String, String> mpvideo) {
        this.mpvideo = mpvideo;
    }

    public void setTitle(String title) {
        this.mpvideo.put("title", title);
    }

    public void setDescription(String description) {
        this.mpvideo.put("description", description);
    }
}
