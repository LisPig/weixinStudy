package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.mp.model.User;
import java.util.ArrayList;

/**
 * 批量获取用户基本信息
 *
 *
 */
public class UserInfoList {

    private ArrayList<User> user_info_list = new ArrayList<User>();

    /**
     * @return the user_info_list
     */
    public ArrayList<User> getUser_info_list() {
        return user_info_list;
    }

    /**
     * @param user_info_list the user_info_list to set
     */
    public void setUser_info_list(ArrayList<User> user_info_list) {
        this.user_info_list = user_info_list;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static UserInfoList fromJson(String json) {
        return JSON.parseObject(json, UserInfoList.class);
    }

}
