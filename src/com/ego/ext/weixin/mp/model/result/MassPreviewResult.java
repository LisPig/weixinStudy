package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.result.Result;

/**
 * 微信错误码说明 http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明
 *
 * 预览接口 返回结果
 *
 * @see
 * http://mp.weixin.qq.com/wiki/15/5380a4e6f02f2ffdc7981a8ed7a40753.html#.E6.A0.B9.E6.8D.AEOpenID.E5.88.97.E8.A1.A8.E7.BE.A4.E5.8F.91.E3.80.90.E8.AE.A2.E9.98.85.E5.8F.B7.E4.B8.8D.E5.8F.AF.E7.94.A8.EF.BC.8C.E6.9C.8D.E5.8A.A1.E5.8F.B7.E8.AE.A4.E8.AF.81.E5.90.8E.E5.8F.AF.E7.94.A8.E3.80.91
 * @author
 *
 */
public class MassPreviewResult extends Result {

    private String msg_id;

    /**
     * 消息ID
     *
     * @return
     */
    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public static MassPreviewResult fromJson(String json) {
        return JSON.parseObject(json, MassPreviewResult.class);
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
