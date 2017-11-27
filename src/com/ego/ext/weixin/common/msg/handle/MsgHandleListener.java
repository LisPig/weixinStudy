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
 * 消息处理监听器。主要用于接收微信服务器消息的接口
 *
 * @author marker
 *
 */
public interface MsgHandleListener {

    /**
     * 收到文本消息
     *
     * @param msg
     * @param callback
     * @return true继续执行下一个监听器，FALSE不执行后续的监听器
     */
    public boolean onTextMsg(TextMsg msg, Callback callback);

    /**
     * 收到图片消息
     *
     * @param msg
     * @param callback
     * @return true继续执行下一个监听器，FALSE不执行后续的监听器
     */
    public boolean onImageMsg(ImageMsg msg, Callback callback);

    /**
     * 收到事件推送消息
     *
     * @param msg
     * @param callback
     * @return true继续执行下一个监听器，FALSE不执行后续的监听器
     */
    public boolean onEventMsg(EventMsg msg, Callback callback);

    /**
     * 收到链接消息
     *
     * @param msg
     * @param callback
     * @return true继续执行下一个监听器，FALSE不执行后续的监听器
     */
    public boolean onLinkMsg(LinkMsg msg, Callback callback);

    /**
     * 收到地理位置消息
     *
     * @param msg
     * @param callback
     * @return true继续执行下一个监听器，FALSE不执行后续的监听器
     */
    public boolean onLocationMsg(LocationMsg msg, Callback callback);

    /**
     * 语音识别消息
     *
     * @param msg
     * @param callback
     * @return true继续执行下一个监听器，FALSE不执行后续的监听器
     */
    public boolean onVoiceMsg(VoiceMsg msg, Callback callback);

    /**
     * 错误消息
     *
     * @param errorCode
     * @param msg
     * @param callback
     * @return true继续执行下一个监听器，FALSE不执行后续的监听器
     */
    public boolean onErrorMsg(int errorCode, Msg msg, Callback callback);

    /**
     * 视频消息
     *
     * @param msg
     * @param callback
     * @return true继续执行下一个监听器，FALSE不执行后续的监听器
     */
    public boolean onVideoMsg(VideoMsg msg, Callback callback);
}
