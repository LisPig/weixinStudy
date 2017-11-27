package com.ego.ext.weixin.common.model.ct;



/**
 * 素材的leixing
 *
 * @author Administrator
 */
public enum MediaType {

    image("image", "图片"),
    video("temp", "视频"),
    voice("forever", "语音"),
    news("temp", "图文");

    private final String value;
    private final String des;

    MediaType(String value, String des) {
        this.value = value;
        this.des = des;

    }

    public String getVal() {
        return this.value;
    }

    public String getDes() {
        return this.des;
    }

    public static boolean contain(String enumName) {
        MediaType[] all = MediaType.values();
        for (int i = 0, len = all.length; i < len; i++) {
            if (all[i].toString().equals(enumName)) {
                return true;
            }
        }
        return false;
    }
}
