package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.result.Result;

/**
 * 微信错误码说明 http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 *
 * 高级群发接口 返回结果
 *
 * @see http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html
 * @author
 *
 */
public class MassSendedResult extends Result {

    private String msg_id;
    private String type;
    /**
     * 消息的数据ID，，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。
     */
    private String msg_data_id;

    /**
     * 消息ID
     *
     * @return
     */
    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    /**
     * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     *
     * @return
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static MassSendedResult fromJson(String json) {
        return JSON.parseObject(json, MassSendedResult.class);
    }

    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * 消息的数据ID，，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。
     *
     * @return the msg_data_id
     */
    public String getMsg_data_id() {
        return msg_data_id;
    }

    /**
     * 消息的数据ID，，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。
     *
     * @param msg_data_id the msg_data_id to set
     */
    public void setMsg_data_id(String msg_data_id) {
        this.msg_data_id = msg_data_id;
    }
}
