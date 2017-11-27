package com.ego.ext.weixin.common.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.core.util.UtilObject;
import com.ego.ext.weixin.common.WxException;
import java.lang.reflect.InvocationTargetException;

/**
 * 微信请求状态数据. 微信错误码说明 http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 *
 * @author
 *
 */
public class Result {

    private int errcode = 0;
    private String errmsg = "正确";
    //  private String json;

    public static Result getInstance() {
        return new Result();
    }

    public boolean isSuccess() {
        return this.errcode == 0;
    }

    public int getErrcode() {
        return errcode;
    }

    public Result setErrcode(int errcode) {
        this.errcode = errcode;
        return this;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public Result setErrmsg(String errmsg) {
        this.errmsg = errmsg;
        return this;
    }
    /*
     public String getJson() {
     return json;
     }

     public Result setJson(String json) {
     this.json = json;
     return this;
     }
     */

    public static Result fromJson(String json) {
        return JSON.parseObject(json, Result.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public void copy(Result other) throws WxException {
        try {
            UtilObject.copy(other, this);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    @Override
    public String toString() {
        return "微信结果 errcode=" + errcode + ", errmsg=" + errmsg + "\n";
    }
}
