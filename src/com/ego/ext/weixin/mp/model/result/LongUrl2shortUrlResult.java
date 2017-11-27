package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.result.Result;

/**
 * 微信错误码说明 http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 *
 * @author
 *
 */
public class LongUrl2shortUrlResult extends Result {

    private String short_url;

    /**
     * 短链接。
     *
     * @return
     */
    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public static LongUrl2shortUrlResult fromJson(String json) {
        return JSON.parseObject(json, LongUrl2shortUrlResult.class);
    }

    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
