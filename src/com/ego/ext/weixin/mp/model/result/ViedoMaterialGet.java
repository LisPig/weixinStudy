package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.News;
import com.ego.ext.weixin.mp.model.massmsg.*;
import com.ego.ext.weixin.common.msg.Msg;
import com.ego.ext.weixin.mp.model.custommsg.SendedNewsCustomMsg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViedoMaterialGet {

    /**
     *
     */
    private String title;
    private String description;
    private String down_url;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static ViedoMaterialGet fromJson(String json) {
        return JSON.parseObject(json, ViedoMaterialGet.class);
    }

    public static void main(String arg[]) {

    }

    /**
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the down_url
     */
    public String getDown_url() {
        return down_url;
    }

    /**
     * @param down_url the down_url to set
     */
    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

}
