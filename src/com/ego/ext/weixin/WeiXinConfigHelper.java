package com.ego.ext.weixin;

import java.util.ArrayList;
import java.util.List;

import com.ego.ext.weixin.common.model.WxConfig;
import com.ego.ext.weixin.common.msg.handle.MsgHandleListener;

import friends.dataset.MemoryDataSet;
import friends.dataset.MemoryDataSetProvider;
import friends.util.StringUtils;

public class WeiXinConfigHelper
{
    /**
     * 根据appid返回微信配置
     *
     * @param appid
     * @throws java.lang.Throwable
     */
    public static WxConfig getConfig(String appid) throws Throwable
    {
        if(appid == null || "".equals(appid)) throw new Exception("参数appid不能为空");
        try
        {
            MemoryDataSet ds = new MemoryDataSet();
            MemoryDataSetProvider provider =  new MemoryDataSetProvider();
            ds.setProvider(provider);
            ds.setQueryString("SELECT * FROM jc_weixin_config c WHERE c.app_id = '" + StringUtils.replaceInvalid(appid) + "'");
            ds.openDataSet();
            if(ds.getRowCount() > 0)
            {
                ds.first();
                WxConfig cfg = new WxConfig();
                cfg.setAppId(ds.getString("app_id"));
                cfg.setSecret(ds.getString("app_secret"));
                cfg.setToken(ds.getString("token"));
                cfg.setEncodingAESKey(ds.getString("encoding_aes_key"));
                cfg.setEncrypt("Y".equalsIgnoreCase(ds.getString("is_encrypt")));
                ds.close();
                return cfg;
            }else
            {
                throw new Exception("表jc_weixin_config中没有appid\"" + appid + "\"对应的配置记录。");
            }
        }catch(Throwable e)
        {
            throw e;
        }
    }
    
    /**
     * 根据appid返回微信监听配置
     *
     * @param appid
     * @throws java.lang.Throwable
     */
    public static List<MsgHandleListener> getListeners(String appid) throws Throwable
    {
        if(appid == null || "".equals(appid)) throw new Exception("参数appid不能为空");
        try
        {
            MemoryDataSet ds = new MemoryDataSet();
            MemoryDataSetProvider provider =  new MemoryDataSetProvider();
            ds.setProvider(provider);
            ds.setQueryString("SELECT * FROM jc_weixin_listeners l WHERE l.enable_flag = 'Y' AND l.app_id = '" + StringUtils.replaceInvalid(appid) + "' ORDER BY l.call_seq");
            ds.openDataSet();
            if(ds.getRowCount() > 0)
            {
                ds.first();
                List<MsgHandleListener> list = new ArrayList<MsgHandleListener>();
                do
                {
                    String listenerClass = ds.getString("listener_class");
                    MsgHandleListener listener = (MsgHandleListener) Class.forName(listenerClass).newInstance();
                    list.add(listener);
                }while(ds.next());
                ds.close();
                return list;
            }else
            {
                throw new Exception("表jc_weixin_listeners中没有appid\"" + appid + "\"对应的有效配置记录。");
            }
        }catch(Throwable e)
        {
            throw e;
        }
    }
}
