package com.ego.ext.weixin.common.msg.handle;

import com.ego.ext.weixin.common.msg.Msg;

public interface Callback {

    /**
     *
     * @param msg 回应微信服务器的消息
     * @return 调用成功返回true
     */
    public boolean invoke(Msg msg);
}
