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

public class NewsMaterialGet {

    private List<News> news_item = new ArrayList<News>();

    /**
     * 设置 super.setMsgtype(Msg.MsgType.NEWS.getType());
     */
    public NewsMaterialGet() {

    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static NewsMaterialGet fromJson(String json) {
        return JSON.parseObject(json, NewsMaterialGet.class);
    }

    public static void main(String arg[]) {

    }

    /**
     * @return the news_item
     */
    public List<News> getNews_item() {
        return news_item;
    }

    /**
     * @param news_item the news_item to set
     */
    public void setNews_item(List<News> news_item) {
        this.news_item = news_item;
    }

}
