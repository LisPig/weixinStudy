package com.ego.ext.weixin.common.test;

import com.ego.ext.weixin.common.model.CustomNews;
import com.ego.ext.weixin.common.msg.EventMsg;
import com.ego.ext.weixin.common.msg.ImageMsg;
import com.ego.ext.weixin.common.msg.LocationMsg;
import com.ego.ext.weixin.common.msg.NewsMsg;
import com.ego.ext.weixin.common.msg.TextMsg;
import com.ego.ext.weixin.common.msg.VoiceMsg;
import com.ego.ext.weixin.common.msg.handle.Callback;
import com.ego.ext.weixin.common.msg.handle.MsgHandleAdapter;

/**
 *
 * @author Administrator
 */
public class TestMsgHandleListener extends MsgHandleAdapter {

    @Override
    public boolean onTextMsg(TextMsg msg, Callback callback) {
        if ("1".equals(msg.getContent())) {// 菜单选项1
            TextMsg reMsg = new TextMsg();
            reMsg.setFromUserName(msg.getToUserName());
            reMsg.setToUserName(msg.getFromUserName());
            reMsg.setCreateTime(msg.getCreateTime());
            reMsg.setContent("【菜单】\n"
                    + "1. 功能菜单\n"
                    + "2. 图文消息测试\n"
                    + "3. 图片消息测试\n");
            return callback.invoke(reMsg);
        } else if ("2".equals(msg.getContent())) {
            //回复一条消息
            CustomNews d1 = new CustomNews("蘑菇建站系统", "CMS不解释", "http://cms.yl-blog.com/themes/blue/images/logo.png", "cms.yl-blog.com");
            CustomNews d2 = new CustomNews("雨林博客", "发布各种技术文章", "http://www.yl-blog.com/template/ylblog/images/logo.png", "www.yl-blog.com");

            NewsMsg mit = new NewsMsg();
            mit.setFromUserName(msg.getToUserName());
            mit.setToUserName(msg.getFromUserName());
            mit.setCreateTime(msg.getCreateTime());
            mit.addItem(d1);
            mit.addItem(d2);
            return callback.invoke(mit);
        } else if ("3".equals(msg.getContent())) {
            ImageMsg rmsg = new ImageMsg();
            rmsg.setFromUserName(msg.getToUserName());
            rmsg.setToUserName(msg.getFromUserName());
            rmsg.setPicUrl("http://www.yl-blog.com/template/ylblog/images/logo.png");
            return callback.invoke(rmsg);
        } else {
            TextMsg reMsg = new TextMsg();
            reMsg.setFromUserName(msg.getToUserName());
            reMsg.setToUserName(msg.getFromUserName());
            reMsg.setCreateTime(msg.getCreateTime());

            reMsg.setContent("消息命令错误，谢谢您的支持！@wuweiit");

            return callback.invoke(reMsg);
        }
    }

    @Override
    public boolean onVoiceMsg(VoiceMsg msg, Callback callback) {
        TextMsg reMsg = new TextMsg();
        reMsg.setFromUserName(msg.getToUserName());
        reMsg.setToUserName(msg.getFromUserName());
        reMsg.setCreateTime(msg.getCreateTime());
        reMsg.setContent("识别结果: " + msg.getRecognition());
        return callback.invoke(reMsg);// 回传消息 
    }

    @Override
    public boolean onEventMsg(EventMsg msg, Callback callback) {
        String eventType = msg.getEvent();
        if (EventMsg.EventType.subscribe.getType().equals(eventType)) {// 订阅
            System.out.println("关注人：" + msg.getFromUserName());
            System.out.println("参数值：" + msg.getSubscribeOrScanEventMsg().getEventKey());

            TextMsg reMsg = new TextMsg();
            reMsg.setFromUserName(msg.getToUserName());
            reMsg.setToUserName(msg.getFromUserName());
            reMsg.setCreateTime(msg.getCreateTime());

            reMsg.setContent("【菜单】\n"
                    + "1. 功能菜单\n"
                    + "2. 图文消息测试\n"
                    + "3. 图片消息测试\n");

            return callback.invoke(reMsg);//回传消息

        } else if (EventMsg.EventType.unsubscribe.getType().equals(eventType)) {// 取消订阅
            System.out.println("取消关注：" + msg.getFromUserName());

        } else if (EventMsg.EventType.click.getType().equals(eventType)) {// 点击事件
            System.out.println("用户：" + msg.getFromUserName());
            System.out.println("点击Key：" + msg.getSubscribeOrScanEventMsg());
        }
        return false;
    }

    @Override
    public boolean onLocationMsg(LocationMsg msg, Callback callback) {
        System.out.println("收到地理位置消息：");
        System.out.println("X:" + msg.getLocation_X());
        System.out.println("Y:" + msg.getLocation_Y());
        System.out.println("Scale:" + msg.getScale());
        return true;
    }
}
