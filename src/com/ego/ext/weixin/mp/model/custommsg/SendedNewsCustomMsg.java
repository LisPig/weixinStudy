package com.ego.ext.weixin.mp.model.custommsg;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.CustomNews;
import com.ego.ext.weixin.common.msg.Msg;
import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息
 *
 * @author Daniel Qian
 *
 */
public class SendedNewsCustomMsg extends SendedCustomMsg {

    private SendedNewsMsgInfo news;

    /**
     * 设置 super.setMsgtype(Msg.MsgType.NEWS.getType());
     */
    public SendedNewsCustomMsg() {
        super.setMsgtype(Msg.MsgType.NEWS.getType());
    }

    public SendedNewsMsgInfo getNews() {
        return this.news;
    }

    /**
     *
     * @param news 必须。消息内容
     */
    public void setNews(SendedNewsMsgInfo news) {
        this.news = news;
    }

    public static class SendedNewsMsgInfo {

        private List<CustomNews> articles = new ArrayList();

        public List<CustomNews> getArticles() {
            return articles;
        }

        public SendedNewsMsgInfo setArticles(List<CustomNews> articles) {
            this.articles = articles;
            return this;
        }

        public SendedNewsMsgInfo addArticle(CustomNews article) {
            this.articles.add(article);
            return this;
        }
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static SendedNewsCustomMsg fromJson(String json) {
        return JSON.parseObject(json, SendedNewsCustomMsg.class);
    }

    public static void main(String arg[]) {
        SendedNewsCustomMsg t = new SendedNewsCustomMsg();
        CustomNews a = new CustomNews();
        a = new CustomNews();
        a.setTitle("1");
        a.setUrl("2");
        a.setPicurl("rrrrrrrrr");
        t.setNews(new SendedNewsMsgInfo().addArticle(a));

        System.out.print(JSON.toJSONString(t));
    }

}
