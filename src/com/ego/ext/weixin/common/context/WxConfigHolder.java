package com.ego.ext.weixin.common.context;

import com.ego.ext.weixin.common.model.WxConfig;

/**
 * 将 ApiConfig 绑定到 ThreadLocal 的工具类，以方便在当前线程的各个地方获取 ApiConfig 对象：
 *
 * 1：如果控制器继承自
 *
 * MsgController 该过程是自动的，详细可查看 MsgInterceptor 与之的配合
 *
 * 2：如果控制器继承自 ApiController
 *
 * 该过程是自动的，详细可查看 ApiInterceptor 与之的配合
 *
 * 3：如果控制器没有继承自
 *
 * MsgController、ApiController，则需要先手动调用
 *
 * ApiConfigKit.setThreadLocalApiConfig(ApiConfig) 来绑定 apiConfig 到线程之上
 */
public class WxConfigHolder {

    private static final ThreadLocal<WxConfig> wxConfigThreadLocalHolder = new ThreadLocal();

    public static void setConfig(WxConfig config) {
        wxConfigThreadLocalHolder.set(config);
    }

    public static WxConfig getConfig() {
        WxConfig result = wxConfigThreadLocalHolder.get();
        /*
        if (result == null) {
            throw new IllegalStateException("需要事先使用 WxConfigHolder.setConfig(WxConfig config) 将 WxConfig对象存入，才可以调用  WxConfig getConfig()  方法");
        }
        */
        return result;
    }

    /**
     * 移除
     */
    public static void remove() {
        wxConfigThreadLocalHolder.remove();
    }

}
