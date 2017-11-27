package com.ego.ext.weixin.mp.model;

import com.alibaba.fastjson.JSON;

/**
 * 用户分组
 *
 *
 */
public class Group {

    //分组id，由微信分配
    private int id;
    //分组名字，UTF8编码
    private String name;
    //分组内用户数量
    private int count;

    /**
     * 分组id，由微信分配
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 分组名字，UTF8编码
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 分组内用户数量
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static Group fromJson(String json) {
        return JSON.parseObject(json, Group.class);
    }

}
