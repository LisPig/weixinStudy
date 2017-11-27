package com.ego.ext.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.ext.weixin.WXMP;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.context.WxConfigHolder;
import com.ego.ext.weixin.common.model.WxConfig;
import com.ego.ext.weixin.common.msg.handle.DefaultMsgHandler;
import com.ego.ext.weixin.common.msg.handle.MsgHandleListener;

import friends.util.log.Log;

public class WeiXinServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private Log log = new Log(getClass());
    private HashMap<String, WxConfig> cfgMap = new HashMap<String, WxConfig>();
    private HashMap<String, List<MsgHandleListener>> listenersMap = new HashMap<String, List<MsgHandleListener>>();
    private WxConfig currCfg = null;
    private List<MsgHandleListener> currListeners = null;
     
    //TOKEN 是你在微信平台开发模式中设置的哦
    // public static final String TOKEN = "";
    /**
     * 处理微信服务器验证
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        try
        {
            config(request);
        }catch(Throwable e)
        {
            throw new ServletException(e);
        }

        String token = currCfg.getToken();
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 

        Writer out = response.getWriter();
        // 验证
        if (WXMP.checkSignature(signature, token, timestamp, nonce)) {
            out.write(echostr);// 请求验证成功，返回随机码
        } else {
            out.write("");
        }
        out.flush();
        out.close();
    }

    /**
     * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
     *
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.info("doPost before config");
        try
        {
            config(request);
        }catch(Throwable e)
        {
            throw new ServletException(e);
        }
        log.info("doPost after config");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        log.info("doPost before try");
        DefaultMsgHandler handler = null;
        try {
            handler = DefaultMsgHandler.newInstance();            
            handler.setOnHandleMsgListeners(currListeners);
            handler.process(request, response);//处理微信消息 
            handler.close();//关闭处理器，包括输入输出流
        } catch (WxException ex) {
            log.info("java.version:" + System.getProperty("java.version"));
            log.error("微信推送处理失败", ex);
        }catch(Throwable e)
        {
            log.info("java.version:" + System.getProperty("java.version"));
            log.error("doPost when try", e);
        }
    }
    
    private void config(HttpServletRequest request) throws Throwable
    {
        //清除缓存的配置
        String resetConfig = request.getParameter("resetConfig");
        if("Y".equalsIgnoreCase(resetConfig))
        {
            listenersMap = new HashMap<String, List<MsgHandleListener>>();
            cfgMap = new HashMap<String, WxConfig>();
        }
        //获取配置信息
        String appid = request.getParameter("appid");
        if(cfgMap.containsKey(appid))
        {
            currCfg = cfgMap.get(appid);
        }else
        {
            try
            {
                currCfg = WeiXinConfigHelper.getConfig(appid);
                cfgMap.put(appid, currCfg);
            }catch(Throwable e)
            {
                log.error("获取微信配置信息失败", e);
                throw e;
            }
        }
        WxConfigHolder.setConfig(currCfg);
        //设置监听
        if(listenersMap.containsKey(appid))
        {
            currListeners = listenersMap.get(appid);
        }else
        {
            try
            {
                currListeners = WeiXinConfigHelper.getListeners(appid);
                listenersMap.put(appid, currListeners);
            }catch(Throwable e)
            {
                log.error("获取微信监听处理程序失败", e);
                throw e;
            }
        }
    }
}
