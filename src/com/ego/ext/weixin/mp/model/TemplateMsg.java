package com.ego.ext.weixin.mp.model;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息
 *
 * @author
 *
 */
public class TemplateMsg {

    private int index = 1;
    private String touser;
    private Map<String, TemplateDataInfo> data = new HashMap();
    private String template_id;
    private String url;
    private String topcolor;

    public TemplateMsg() {
        this.data.put("first", new TemplateDataInfo("提示", "#173177"));
        this.data.put("remark", new TemplateDataInfo("谢谢", "#173177"));
    }

    public String getTouser() {
        return touser;
    }

    /**
     *
     *
     * @param touser 必须。普通用户openid
     * @return
     */
    public TemplateMsg setTouser(String touser) {
        this.touser = touser;
        return this;
    }

    public Map<String, TemplateDataInfo> getData() {
        return this.data;
    }

    /**
     *
     * @param data 必须。消息内容
     * @return
     */
    public TemplateMsg setData(Map<String, TemplateDataInfo> data) {
        this.data = data;
        return this;
    }

    public TemplateMsg setFirst(TemplateDataInfo first) {
        this.data.put("first", first);
        return this;

    }

    public TemplateDataInfo getFirst() {

        return this.data.get("first");

    }

    public TemplateMsg setRemark(TemplateDataInfo remark) {
        this.data.put("remark", remark);
        return this;
    }

    public TemplateDataInfo getRemark() {

        return this.data.get("remark");
    }

    public TemplateMsg addKeynote(TemplateDataInfo keynote) {
        this.data.put("keynote" + this.index, keynote);
        this.index++;
        return this;
    }

    /**
     *
     *
     * @return
     */
    public String getTemplate_id() {
        return template_id;
    }

    public TemplateMsg setTemplate_id(String template_id) {
        this.template_id = template_id;
        return this;
    }

    /**
     *
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    public TemplateMsg setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public TemplateMsg setTopcolor(String topcolor) {
        this.topcolor = topcolor;
        return this;
    }

    public static TemplateMsg fromJson(String json) {
        return JSON.parseObject(json, TemplateMsg.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static class TemplateDataInfo {

        private String value;
        private String color;

        public TemplateDataInfo() {

        }

        public TemplateDataInfo(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public String getValue() {
            return value;
        }

        public TemplateDataInfo setValue(String value) {
            this.value = value;
            return this;
        }

        public String getColor() {
            return color;
        }

        public TemplateDataInfo setColor(String color) {
            this.color = color;
            return this;
        }
    }

    public static void main(String arg[]) {
        TemplateMsg t = new TemplateMsg();
        t.setTouser("1")
                .setUrl("rrrr")
                .setTopcolor("333")
                .setFirst(new TemplateDataInfo("s", "44"))
                .addKeynote(new TemplateDataInfo("k1", "k1"))
                .addKeynote(new TemplateDataInfo("k1", "k1"));

        System.out.print(t.toJson());
    }

}
