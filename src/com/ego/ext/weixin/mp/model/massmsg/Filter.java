package com.ego.ext.weixin.mp.model.massmsg;

import com.alibaba.fastjson.JSON;

/**
 * 群发过滤器---是否群发全部还是根据分组群发。根据分组进行群发时用到
 *
 * @author Administrator
 */
public class Filter {

    private boolean is_to_all;

    private String group_id;

    /**
     * 群发全部
     */
    public Filter() {
        super();
        this.is_to_all = true;

    }

    /**
     * 根据分组群发
     *
     * @param group_id
     */
    public Filter(String group_id) {
        super();
        this.group_id = group_id;
    }

    /**
     * @deprecated 不建议用此构造方法
     * @param is_to_all
     * @param group_id
     */
    public Filter(boolean is_to_all, String group_id) {
        super();
        this.is_to_all = is_to_all;
        this.group_id = group_id;
    }

    public boolean isIs_to_all() {
        return is_to_all;
    }

    public void setIs_to_all(boolean is_to_all) {
        this.is_to_all = is_to_all;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static Filter fromJson(String json) {
        return JSON.parseObject(json, Filter.class);
    }

}
