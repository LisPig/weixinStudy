package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import java.util.List;

/**
 * 获取关注列表
 *
 *
 */
public class UserFollwerList {

    private int total;
    private int count;
    private String next_openid;
    private UserFollwerListInfo data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext_openid() {
        return next_openid;
    }

    public void setNext_openid(String next_openid) {
        this.next_openid = next_openid;
    }

    public UserFollwerListInfo getData() {
        return this.data;

    }

    public void setData(UserFollwerListInfo data) {
        this.data = data;

    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static UserFollwerList fromJson(String json) {
        return JSON.parseObject(json, UserFollwerList.class);
    }

    public static class UserFollwerListInfo {

        private List<String> openid;

        /**
         * 列表数据，OPENID的列表
         *
         * @return
         */
        public List<String> getOpenid() {
            return this.openid;

        }

        public void setOpenid(List<String> openid) {
            this.openid = openid;
        }
    }
}
