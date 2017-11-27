package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;

/**
 * 创建二维码ticket返回结果
 *
 *
 */
public class TicketCreateResult {

    private String ticket;
    private int expire_seconds;
    private String url;

    /**
     * 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
     *
     * @return
     */
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    /**
     * 二维码的有效时间，以秒为单位。最大不超过1800。
     *
     * @return
     */
    public int getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(int expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static TicketCreateResult fromJson(String json) {
        return JSON.parseObject(json, TicketCreateResult.class);
    }

}
