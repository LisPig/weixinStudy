package com.ego.ext.weixin.common.msg.handle;

import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.ImageMsg;
import com.ego.ext.weixin.common.msg.LinkMsg;
import com.ego.ext.weixin.common.msg.LocationMsg;
import com.ego.ext.weixin.common.msg.Msg;
import com.ego.ext.weixin.common.msg.TextMsg;
import com.ego.ext.weixin.common.msg.VideoMsg;
import com.ego.ext.weixin.common.msg.VoiceMsg;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于监听器的微信消息处理器的实现。通过添加监听器来对微信消息进行监听
 *
 * 可以添加多个监听器来处理不同的消息内容。
 *
 * 注意：此类并非线程安全，建议每次使用newInstance调用
 *
 * @author marker
 *
 */
public class DefaultMsgHandler extends MsgHandler {

    /**
     * 监听器集合
     */
    private List<MsgHandleListener> listeners = new ArrayList();

    /**
     * 私有构造方法
     */
    private DefaultMsgHandler() throws WxException {
        super();
    }

    /**
     * 创建一个监听器实例
     *
     * @return
     * @throws com.ego.weixin.WxException
     */
    public static DefaultMsgHandler newInstance() throws WxException {
        return new DefaultMsgHandler();
    }

    /**
     * 添加监听器
     *
     * @param handleMassge
     */
    public void addOnHandleMsgListener(MsgHandleListener handleMassge) {
        listeners.add(handleMassge);
    }

    public void setOnHandleMsgListeners(List<MsgHandleListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * 移除监听器
     *
     * @param handleMassge
     */
    public void removeOnHandleMsgListener(MsgHandleListener handleMassge) {
        listeners.remove(handleMassge);
    }

    @Override
    public void onTextMsg(TextMsg msg) {
        for (MsgHandleListener currentListener : listeners) {
            boolean ret = currentListener.onTextMsg(msg, super.getCallback());
            if (!ret) {
                break;
            }
        }
    }

    @Override
    public void onImageMsg(ImageMsg msg) {
        for (MsgHandleListener currentListener : listeners) {
            boolean ret = currentListener.onImageMsg(msg, super.getCallback());
            if (!ret) {
                break;
            }
        }
    }

    @Override
    public void onEventMsg(EventMsg msg) {
        for (MsgHandleListener currentListener : listeners) {
            boolean ret = currentListener.onEventMsg(msg, super.getCallback());
            if (!ret) {
                break;
            }
        }
    }

    @Override
    public void onLinkMsg(LinkMsg msg) {
        for (MsgHandleListener currentListener : listeners) {
            boolean ret = currentListener.onLinkMsg(msg, super.getCallback());
            if (!ret) {
                break;
            }
        }
    }

    @Override
    public void onLocationMsg(LocationMsg msg) {
        for (MsgHandleListener currentListener : listeners) {
            boolean ret = currentListener.onLocationMsg(msg, super.getCallback());
            if (!ret) {
                break;
            }
        }
    }

    @Override
    public void onErrorMsg(int errorCode, Msg msg) {
        for (MsgHandleListener currentListener : listeners) {
            boolean ret = currentListener.onErrorMsg(errorCode, msg, super.getCallback());
            if (!ret) {
                break;
            }
        }
    }

    @Override
    public void onVoiceMsg(VoiceMsg msg) {
        for (MsgHandleListener currentListener : listeners) {
            boolean ret = currentListener.onVoiceMsg(msg, super.getCallback());
            if (!ret) {
                break;
            }
        }
    }

    @Override
    public void onVideoMsg(VideoMsg msg) {
        for (MsgHandleListener currentListener : listeners) {
            boolean ret = currentListener.onVideoMsg(msg, super.getCallback());
            if (!ret) {
                break;
            }
        }
    }
}
