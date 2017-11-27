package com.ego.ext.weixin.mp.model;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;

/**
 * 批量获取用户基本信息
 *
 *
 */
public class UserBatchGetInfo {

    private ArrayList<UserPostInfo> user_list = new ArrayList();

    /**
     * @return the user_list
     */
    public ArrayList<UserPostInfo> getUser_list() {
        return user_list;
    }

    /**
     * @param user_list the user_list to set
     */
    public void setUser_list(ArrayList<UserPostInfo> user_list) {
        this.user_list = user_list;
    }

    public void addUser_list(UserPostInfo user_list) {
        this.user_list.add(user_list);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static UserBatchGetInfo fromJson(String json) {
        return JSON.parseObject(json, UserBatchGetInfo.class);
    }

    public static class UserPostInfo {

        private String openid;
        /**
         * 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
         */
        private String lang = "zh_CN";

       public UserPostInfo() {
        }

        public   UserPostInfo(String openid, String lang) {
            this.openid = openid;
            this.lang = lang;
        }

        /**
         *
         *
         * @return
         */
        public String getOpenid() {
            return this.openid;

        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        /**
         * 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
         *
         * @return the lang
         */
        public String getLang() {
            return lang;
        }

        /**
         * 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
         *
         * @param lang the lang to set
         */
        public void setLang(String lang) {
            this.lang = lang;
        }
    }
}
