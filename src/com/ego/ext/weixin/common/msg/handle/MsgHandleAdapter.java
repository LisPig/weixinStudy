package com.ego.ext.weixin.common.msg.handle;

import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.ImageMsg;
import com.ego.ext.weixin.common.msg.LinkMsg;
import com.ego.ext.weixin.common.msg.LocationMsg;
import com.ego.ext.weixin.common.msg.Msg;
import com.ego.ext.weixin.common.msg.TextMsg;
import com.ego.ext.weixin.common.msg.VideoMsg;
import com.ego.ext.weixin.common.msg.VoiceMsg;

/**
 * 处理消息适配器(适配器模式)
 *
 * @author marker
 *
 */
public class MsgHandleAdapter implements MsgHandleListener {

    @Override
    public boolean onTextMsg(TextMsg msg, Callback callback) {
        return false;

    }

    @Override
    public boolean onImageMsg(ImageMsg msg, Callback callback) {
        return false;

    }

    @Override
    public boolean onEventMsg(EventMsg msg, Callback callback) {
        return false;

    }

    @Override
    public boolean onLinkMsg(LinkMsg msg, Callback callback) {
        return false;

    }

    @Override
    public boolean onLocationMsg(LocationMsg msg, Callback callback) {
        return false;

    }

    @Override
    public boolean onVoiceMsg(VoiceMsg msg, Callback callback) {
        return false;

    }

    @Override
    public boolean onErrorMsg(int errorCode, Msg msg, Callback callback) {
        return callback.invoke(msg);

    }

    @Override
    public boolean onVideoMsg(VideoMsg msg, Callback callback) {
        return false;

    }

}
