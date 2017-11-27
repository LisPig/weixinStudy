package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.result.Result;

/**
 * 微信错误码说明 http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 *
 * 发送模板消息 返回结果
 *
 * @see http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
 * @author
 *
 */
public class TemplateMsgSendedResult extends Result {

    private String msgid;

    /**
     * 消息ID
     *
     * @return
     */
    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public static TemplateMsgSendedResult fromJson(String json) {
        return JSON.parseObject(json, TemplateMsgSendedResult.class);
    }

    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
