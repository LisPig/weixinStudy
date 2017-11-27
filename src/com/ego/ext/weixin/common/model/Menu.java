package com.ego.ext.weixin.common.model;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 *
 *
 */
public class Menu {

    /**
     * @see
     * http://mp.weixin.qq.com/wiki/13/43de8269be54a0a6f64413e4dfa94f39.html
     */
    public enum MenuType{

        /**
         * 点击推事件 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
         * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
         */
        click("click", "点击事件"),
        /**
         * 跳转URL
         * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
         *
         */
        view("view", "跳转UR"),
        /**
         * 扫码推事件
         * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
         */
        scancode_push("scancode_push", "扫码推事件"),
        /**
         * 扫码推事件且弹出“消息接收中”提示框
         * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
         */
        scancode_waitmsg("scancode_waitmsg", "扫码弹出“消息接收中”提示框"),
        /**
         * 弹出系统拍照发图
         * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
         */
        pic_sysphoto("pic_sysphoto", "弹出系统拍照发图"),
        /**
         * 弹出拍照或者相册发图 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
         */
        pic_photo_or_album("pic_photo_or_album", "弹出拍照或者相册发图"),
        /**
         * 弹出微信相册发图器
         * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息
         */
        pic_weixin("pic_weixin", "弹出微信相册发图器"),
        /**
         * 弹出地理位置选择器
         * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。
         */
        location_select("location_select", "弹出地理位置选择器"),
        /**
         * 发消息（除文本消息）
         * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
         */
        media_id("media_id", "发消息（除文本消息）素材"),
        /**
         * 跳转图文消息URL
         * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
         */
        view_limited("media_id", "跳转图文消息URL素材");
        private String type;

        private final String value;
        private final String des;

        MenuType(String value, String des) {
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
            MenuType[] all = MenuType.values();
            for (int i = 0, len = all.length; i < len; i++) {
                if (all[i].toString().equals(enumName)) {
                    return true;
                }
            }
            return false;
        }
    }
    private List<MenuButton> button;

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        this.button = button;
    }

    public void addButton(MenuButton button) {
        if (this.button == null) {
            this.button = new ArrayList();
        }
        this.button.add(button);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static Menu fromJson(String json) {
        return JSON.parseObject(json, Menu.class);
    }

    public static class MenuButton {

        /**
         * 必须，菜单的响应动作类型
         */
        private String type;
        /**
         * 必须，菜单标题，不超过16个字节，子菜单不超过40个字节
         */
        private String name;
        /**
         * click等点击类型必须	菜单KEY值，用于消息接口推送，不超过128字节
         */
        private String key;
        /**
         * view类型必须	网页链接，用户点击菜单可打开链接，不超过256字节
         */
        private String url;
        /**
         * media_id类型和view_limited类型必须	调用新增永久素材接口返回的合法media_id
         */
        private String media_id;
        private List<MenuButton> sub_button;

        public List<MenuButton> getSub_button() {
            return sub_button;
        }

        public void setSub_button(List<MenuButton> sub_button) {
            this.sub_button = sub_button;
        }

        public void addSub_button(MenuButton sub_button) {
            if (this.sub_button == null) {
                this.sub_button = new ArrayList();
            };
            this.sub_button.add(sub_button);
        }

        /**
         * 必须，菜单的响应动作类型
         *
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * 必须，菜单的响应动作类型
         *
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * 必须，菜单标题，不超过16个字节，子菜单不超过40个字节
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * 必须，菜单标题，不超过16个字节，子菜单不超过40个字节
         *
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * click等点击类型必须	菜单KEY值，用于消息接口推送，不超过128字节
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * click等点击类型必须	菜单KEY值，用于消息接口推送，不超过128字节
         *
         * @param key the key to set
         */
        public void setKey(String key) {
            this.key = key;
        }

        /**
         * view类型必须	网页链接，用户点击菜单可打开链接，不超过256字节
         *
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * view类型必须	网页链接，用户点击菜单可打开链接，不超过256字节
         *
         * @param url the url to set
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * media_id类型和view_limited类型必须	调用新增永久素材接口返回的合法media_id
         *
         * @return the media_id
         */
        public String getMedia_id() {
            return media_id;
        }

        /**
         * media_id类型和view_limited类型必须	调用新增永久素材接口返回的合法media_id
         *
         * @param media_id the media_id to set
         */
        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }

    }

    public static void main(String arg[]) {
        Menu m = new Menu();

        System.out.print(m.toJson());
    }
}
