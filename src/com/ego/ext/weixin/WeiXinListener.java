package com.ego.ext.weixin;

import java.util.Date;

import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.ImageMsg;
import com.ego.ext.weixin.common.msg.LinkMsg;
import com.ego.ext.weixin.common.msg.LocationMsg;
import com.ego.ext.weixin.common.msg.Msg;
import com.ego.ext.weixin.common.msg.TextMsg;
import com.ego.ext.weixin.common.msg.VideoMsg;
import com.ego.ext.weixin.common.msg.VoiceMsg;
import com.ego.ext.weixin.common.msg.handle.Callback;
import com.ego.ext.weixin.common.msg.handle.MsgHandleListener;

import friends.util.log.Log;

public class WeiXinListener implements MsgHandleListener
{
    private Log log = new Log(getClass());

    @Override
    public boolean onErrorMsg(int errorCode, Msg msg, Callback callback)
    {
        log.error("error code:" + errorCode);
        return true;
    }

    @Override
    public boolean onEventMsg(EventMsg msg, Callback callback)
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean onImageMsg(ImageMsg msg, Callback callback)
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean onLinkMsg(LinkMsg msg, Callback callback)
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean onLocationMsg(LocationMsg msg, Callback callback)
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean onTextMsg(TextMsg msg, Callback callback)
    {
        String fromUserName = msg.getFromUserName();
        String toUserName = msg.getToUserName();
        msg.setFromUserName(toUserName);
        msg.setToUserName(fromUserName);
        msg.setCreateTime(String.valueOf(new Date().getTime()));
        msg.setContent("回复消息测试:" +  msg.getContent());
        
        try
        {
            boolean invoke = callback.invoke(msg);
            log.info(invoke + "   " + msg.getContent());
        }catch(Exception e)
        {
            log.info("callback.invoke error:", e);
        }
        
        return true;
    }

    @Override
    public boolean onVideoMsg(VideoMsg msg, Callback callback)
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean onVoiceMsg(VoiceMsg msg, Callback callback)
    {
        // TODO Auto-generated method stub
        return true;
    }

}
