package com.ego.ext.weixin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ego.ext.weixin.common.model.AccessToken;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.mp.api.BaseApi;

import friends.dataset.MemoryDataSet;
import friends.dataset.MemoryDataSetProvider;
import friends.dataset.MemoryDataSetResolver;
import friends.util.StringUtils;
import friends.util.log.Log;

public class AccessTokenHelper
{
    private static HashMap<String, AccessToken> accessTokenMap = new HashMap<String, AccessToken>();
    public static final int cacheSeconds = 300; //应用程序缓存时间，超过这个时间则到数据库重新获取
    public static final int autoRefreshSeconds = 3600; //超过这个时间的AccessToken都刷新
    public static final int autoRefreshIntervalSeconds = 1800; //自动刷新程序运行间隔
    private static ScheduledExecutorService scheduledExecutorService = null; //定时自动运行服务
    
    private static Log log = new Log(AccessTokenHelper.class);
    
    static
    {
        //加载时自动运行定时刷新程序
        startAutoRefresh();
    }
    
    /**
     * 根据appid返回AccessToken，程序将依次从缓存、数据库、腾讯服务器获取有效的AccessToken
     *
     * @param appid
     * @throws java.lang.Throwable
     */
    public static AccessToken getAccessToken(String appid) throws Throwable
    {
        return getAccessToken(appid, false, null);
    }
    
    /**
     * 强制刷新AccessToken，程序根据oldAccessToken与缓存中现有的AccessToken进行比较，
     * 若值相同，表示AccessToken未被其它线程刷新过，程序将到腾讯服务器获取新的AccessToken;
     * 若值不同则标志已被其它线程刷新，直接返回当前缓存中的AccessToken
     *
     * @param appid
     * @param oldAccessToken    旧的AccessToken值
     * @throws java.lang.Throwable
     */
    public static AccessToken getAccessToken(String appid, String oldAccessToken) throws Throwable
    {
        return getAccessToken(appid, true, oldAccessToken);
    }
    
    /**
     * 若参数refresh为true时，程序不检查缓存和数据库，直接从腾讯服务器强制刷新AccessToken
     *
     * @param appid
     * @param refresh    强制刷新标志
     * @throws java.lang.Throwable
     */
    public static AccessToken getAccessToken(String appid, boolean refresh) throws Throwable
    {
        return getAccessToken(appid, refresh, null);
    }
    
    /**
     * 根据appid返回AccessToken
     *
     * @param appid
     * @param refresh         强制刷新标志
     * @param oldAccessToken  只有refresh为true时，参数oldAccessToken才起判断作用
     * @throws java.lang.Throwable
     */
    private static synchronized AccessToken getAccessToken(String appid, boolean refresh, String oldAccessToken) throws Throwable
    {
        if(appid == null || "".equals(appid)) throw new Exception("参数appid不能为空");
        AccessToken accessToken = null;
        WeiXinConfig cfg = null;
        if(!refresh)
        {
            //从缓存里获取accessToken
            if(accessTokenMap.containsKey(appid))
            {
                accessToken = accessTokenMap.get(appid);
            }
            //如果缓存里存在accessToken，并且未过期
            if(accessToken != null && accessToken.isAvailable())
            {
                Long cacheTime = accessToken.getCacheTime();
                if(cacheTime + cacheSeconds * 1000 >= System.currentTimeMillis()) //accessToken缓存时间未超时，则直接返回
                {
                    return accessToken;
                }
            }
            //能执行到这里，说明需要去数据库读取并检验access_token
            cfg = readFromDatabase(appid);
            //若数据获取的access_token有效，刷新缓存，并返回
            if(cfg.access_token != null && !"".equals(cfg.access_token.trim()) && 
               cfg.get_time > 0 && cfg.expires_in > 0)
            {
                accessToken = new AccessToken(cfg.access_token, cfg.expires_in);
                accessToken.setGetTime(cfg.get_time);
                if(accessToken.isAvailable())
                {
                    accessTokenMap.put(appid, accessToken);
                    return accessToken;
                }
            }
        }
        //若执行到这里，说明需要去腾讯服务刷新access_token
        if(cfg == null) cfg = readFromDatabase(appid);
        /*如果refresh为true,oldAccessToken不为空时，则表示需要配合oldAccessToken检查
         * access_token是否已被刷新过，已刷新过则不再刷新，否则从腾讯服务器刷新
         */
        if(refresh && oldAccessToken != null && !"".equals(oldAccessToken.trim()))
        {
            if(accessTokenMap.containsKey(appid))
            {
                accessToken = accessTokenMap.get(appid);
                if(accessToken.getAccess_token().equals(cfg.access_token) && 
                   !oldAccessToken.equals(accessToken.getAccess_token()) && accessToken.isAvailable())
                {
                    accessToken.setCacheTime(System.currentTimeMillis());
                    return accessToken;
                }
            }
        }
        accessToken = refreshAccessToken(cfg.app_id, cfg.app_secret);
        return accessToken;
    }
    
    /**
     * 从腾讯服务器获取新的AccessToken
     *
     * @param appid
     * @param appSecret         强制刷新标志
     * @throws java.lang.Throwable
     */
    private static AccessToken refreshAccessToken(String appid, String appSecret) throws Throwable
    {
        if(appid == null || "".equals(appid)) throw new Exception("参数appid不能为空");
        if(appSecret == null || "".equals(appSecret)) throw new Exception("参数appSecret不能为空");
        AccessToken accessToken = null;
        Result result = new Result();
        int retry = 3;
        for (int i = 0; i < retry; i++)
        {
            try
            {
                // 最多三次请求
                accessToken = BaseApi.getAccessToken(appid, appSecret, result);
            } catch (Exception e)
            {
                if(i < retry - 1)
                {
                    continue;
                }else
                {
                    throw e;
                }
            }
            if (accessToken != null && accessToken.isAvailable())
            {
                break;
            }else if(i == retry - 1)
            {
                throw new Exception(result.toString());
            }
        }
        
        accessTokenMap.put(appid, accessToken);
        saveToDatabase(appid, accessToken);
        return accessToken;
    }
    
    private static WeiXinConfig readFromDatabase(String appid) throws Throwable
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
                WeiXinConfig cfg = new WeiXinConfig();
                cfg.app_id = ds.getString("app_id");
                cfg.app_secret = ds.getString("app_secret");
                cfg.token = ds.getString("token");
                cfg.encoding_aes_key = ds.getString("encoding_aes_key");
                cfg.is_encrypt = "Y".equalsIgnoreCase(ds.getString("is_encrypt"));
                cfg.access_token = ds.getString("access_token");
                String getTimeString = ds.getValue("get_time");
                if(getTimeString != null && !"".equals(getTimeString.trim()))
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cfg.get_time = sdf.parse(getTimeString).getTime();
                }
                String expiresInString = ds.getValue("expires_in");
                if(expiresInString != null && !"".equals(expiresInString.trim()))
                {
                    cfg.expires_in = Integer.parseInt(expiresInString);
                }
                cfg.description = ds.getString("description");
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
    
    private static void saveToDatabase(String appid, AccessToken accessToken)
    {
        MemoryDataSet ds = new MemoryDataSet();
        MemoryDataSetResolver resolver = new MemoryDataSetResolver();
        ds.setResolver(resolver);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getTime = sdf.format(new Date(accessToken.getGetTime()));
        String sql = "UPDATE jc_weixin_config c SET c.access_token = '" + StringUtils.replaceInvalid(accessToken.getAccess_token()) +
                     "', c.get_time = TO_DATE('" + getTime + "', 'YYYY-MM-DD HH24:MI:SS'), c.expires_in = '" +
                     accessToken.getExpires_in() +"' WHERE c.app_id = '" + StringUtils.replaceInvalid(appid) + "'";
        ds.updateQuery(new String[] {sql});
        ds.close();
    }
    
    /**
     * 启动自动刷新程序，第一次运行时间在常量autoRefreshIntervalSeconds定义的秒数之后
     */
    public static void startAutoRefresh()
    {
        if(scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) return;
        Runnable runnable = new Runnable()
        {  
            public void run()
            {
                autoRefresh();
            }  
        };
        
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(runnable, autoRefreshIntervalSeconds, autoRefreshIntervalSeconds, TimeUnit.SECONDS);
    }
    
    /**
     * 停止自动刷新程序
     */
    public static void stopAutoRefresh()
    {
        if(scheduledExecutorService != null && !scheduledExecutorService.isShutdown())
        {
            scheduledExecutorService.shutdown();
        }
    }
    
    private static void autoRefresh()
    {
        try
        {
            Iterator<Entry<String, AccessToken>> iter = accessTokenMap.entrySet().iterator();
            while (iter.hasNext())
            {
                Map.Entry<String, AccessToken> entry = iter.next();
                String appid = entry.getKey();
                try
                {
                    AccessToken accessToken = entry.getValue();
                    String accessTokenString = accessToken.getAccess_token();
                    long getTime = accessToken.getGetTime();
                    Integer expires_in = accessToken.getExpires_in();
                  
                    //AccessToken中数据合法，AccessToken未超时，并且未超出最大刷新时间，则执行刷新操作
                    if(accessTokenString != null && !"".equals(accessTokenString) &&
                       getTime > 0 && expires_in != null && expires_in.intValue() > 0 &&
                       getTime + expires_in.intValue() * 1000 > System.currentTimeMillis() &&
                       getTime + autoRefreshSeconds * 1000 > System.currentTimeMillis()
                      )
                    {
                        //Do Nothing
                    }else
                    {
                        //重新获取AccessToken，此方法将自动刷新accessTokenMap和数据库
                        getAccessToken(appid, accessTokenString);
                    }
                }catch(Throwable e)
                {
                    log.error("AccessToken auto refresh failed, appid:" + appid, e);
                }
            }
        }catch(Throwable e)
        {
            log.error("AccessToken auto refresh failed!", e);
        }
    }
    
    /**
     * 清空AccessToken缓存，数据库端不会被清除
     */
    public static void clear()
    {
        accessTokenMap.clear();
    }
    
    /**
     * 移除单个AccessToken，数据库端不会被移除
     */
    public static void remove(String appid)
    {
        if(accessTokenMap.containsKey(appid))
        {
            accessTokenMap.remove(appid);
        }
    }
    
    @SuppressWarnings("unused")
    private static class WeiXinConfig
    {
        
        private String app_id;
        private String app_secret;
        private String token;
        private String encoding_aes_key;
        private boolean is_encrypt;
        private String access_token;
        private long get_time;
        private int expires_in;
        private String description;
    }
}
