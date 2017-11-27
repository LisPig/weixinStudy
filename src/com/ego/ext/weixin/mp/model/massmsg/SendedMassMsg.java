package com.ego.ext.weixin.mp.model.massmsg;

import com.alibaba.fastjson.JSON;
import java.util.Set;

/**
 * 发送的高级群发接口数据
 *
 * @author
 *
 */
public abstract class SendedMassMsg {

    protected String msgtype;

    private Filter filter;//用于特定组

    private Set<String> touser;//用于指定用户

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Filter getFilter() {
        return filter;
    }

    /**
     * 如果根据分组进行群发，则设置此参数，如果根据openids群发则不设置此参数
     *
     * @param filter
     */
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Set<String> getTouser() {
        return touser;
    }

    /**
     * 如果根据openids进行群发，则设置此参数，如果根据分组群发则不设置此参数
     *
     * @param touser
     */
    public void setTouser(Set<String> touser) {
        this.touser = touser;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static SendedMassMsg fromJson(String json) {
        return JSON.parseObject(json, SendedMassMsg.class);
    }

}
