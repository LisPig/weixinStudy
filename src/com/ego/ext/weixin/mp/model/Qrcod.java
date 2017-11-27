package com.ego.ext.weixin.mp.model;

import com.alibaba.fastjson.JSON;


public class Qrcod {

    public enum QrcodType  {

        /**
         * 临时
         */
        QR_SCENE("QR_SCENE", "临时二维码"),
        /**
         * 永久
         */
        QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久二维码参数ID为数字"),
        /**
         * 永久的字符串参数值
         */
        QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久二维码参数ID为字符串");
        private final String value;
        private final String des;

        QrcodType(String value, String des) {
            this.value = value;
            this.des = des;

        }

        public String getType() {
            return this.value;
        }

        public String getVal() {
            return this.value;
        }

        public String getDes() {
            return this.des;
        }

        public static boolean contain(String enumName) {
            QrcodType[] all = QrcodType.values();
            for (int i = 0, len = all.length; i < len; i++) {
                if (all[i].toString().equals(enumName)) {
                    return true;
                }
            }
            return false;
        }
    }
    private int expire_seconds;
    private String action_name;
    private ActionInfo action_info;

    public Qrcod() {

    }

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过1800。临时二维码请求带此参数。
     *
     * @return
     */
    public int getExpire_seconds() {
        return expire_seconds;
    }

    public Qrcod setExpire_seconds(int expire_seconds) {
        this.expire_seconds = expire_seconds;
        return this;
    }

    /**
     * 二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值,
     *
     *
     * @return
     */
    public String getAction_name() {
        return action_name;
    }

    public Qrcod setAction_name(String action_name) {
        this.action_name = action_name;
        return this;
    }

    /**
     * 二维码详细信息
     *
     * @return
     */
    public ActionInfo getAction_info() {
        return action_info;
    }

    public Qrcod setAction_info(ActionInfo action_info) {
        this.action_info = action_info;
        return this;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static Qrcod fromJson(String json) {
        return JSON.parseObject(json, Qrcod.class);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static class ActionInfo {

        private Scene scene;

        public Scene getScene() {
            return scene;
        }

        public ActionInfo setScene(Scene scene) {
            this.scene = scene;
            return this;
        }

        public String toJson() {
            return JSON.toJSONString(this);
        }
    }

    public static class Scene {

        private Integer scene_id;
        private String scene_str;

        /**
         * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
         *
         * @return
         */
        public Integer getScene_id() {
            return scene_id;
        }

        public Scene setScene_id(Integer scene_id) {
            this.scene_id = scene_id;
            return this;
        }

        /**
         * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
         *
         * @return
         */
        public String getScene_str() {
            return scene_str;
        }

        public Scene setScene_str(String scene_str) {
            this.scene_str = scene_str;
            return this;
        }

        public String toJson() {
            return JSON.toJSONString(this);
        }
    }

    public static void main(String arg[]) {
        Qrcod q = new Qrcod()
                .setAction_name(Qrcod.QrcodType.QR_SCENE.getVal())
                .setExpire_seconds(77)
                .setAction_info(new Qrcod.ActionInfo()
                        .setScene(new Qrcod.Scene()
                                .setScene_id(89)));

        System.out.println(q.toJson());
        // Qrcod q1 = Qrcod.fromJson("{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}");
        // System.out.println(q1.toJson());
    }
}
