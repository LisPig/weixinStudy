package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.result.Result;

/**
 * 微信错误码说明 http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 *
 * 获得模板ID 返回结果
 *
 * @see http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
 * @author
 *
 */
public class TemplateIdGetResult extends Result {

    private String template_id;

    /**
     * 模板ID
     *
     * @return
     */
    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public static TemplateIdGetResult fromJson(String json) {
        return JSON.parseObject(json, TemplateIdGetResult.class);
    }

    /**
     *
     * @return
     */
    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }
}
