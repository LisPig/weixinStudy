/**
 * 微信公众平台开发模式(JAVA) SDK (c) http://www.jeasyuicn.com/wechat
 */
package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.model.TemplateMsg;
import com.ego.ext.weixin.mp.model.result.TemplateIdGetResult;
import com.ego.ext.weixin.mp.model.result.TemplateMsgSendedResult;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息接口。 模板消息仅用于公众号向用户发送重要的服务通知，只能用于符合其要求的服务场景中，如信用卡刷卡通知，
 * 商品购买成功通知等。不支持广告等营销类消息以及其它所有可能对用户造成骚扰的消息。
 *
 * @see http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
 */
public class TemplateApi {

    public static final String SET_INDUSTRY_URI = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token={0}";
    public static final String ADD_TEMPLATE_URI = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token={0}";
    public static final String MESSAGE_TEMPLATE_SEND_URI = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";

    /**
     * 设置所属行业
     *
     * @param accessToken
     * @param industry_id1 必须。公众号模板消息所属行业编号
     * @param industry_id2 必须。公众号模板消息所属行业编号
     * @see
     * http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
     * @return
     * @throws WxException
     */
    public static Result setIndustry(String accessToken, String industry_id1, String industry_id2) throws WxException {
        try {
            Map<String, Object> json = new HashMap();
            json.put("industry_id1", industry_id1);
            json.put("industry_id2", industry_id2);
            String jsonStr = UtilHttp.post(MessageFormat.format(TemplateApi.SET_INDUSTRY_URI, accessToken), true, JSON.toJSONString(json));
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 获得模板ID
     *
     * 从行业模板库选择模板到账号后台，获得模板ID的过程可在MP中完成。为方便第三方开发者，提供通过接口调用的方式来修改账号所属行业，具体如下：
     *
     * @see
     * http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
     * @param accessToken
     * @param template_id_short 必须。模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * @return
     * @throws WxException
     */
    public static TemplateIdGetResult getTemplateId(String accessToken, String template_id_short) throws WxException {
        try {
            Map<String, Object> json = new HashMap();
            json.put("template_id_short", template_id_short);
            String jsonStr = UtilHttp.post(MessageFormat.format(TemplateApi.ADD_TEMPLATE_URI, accessToken), true, JSON.toJSONString(json));
            return TemplateIdGetResult.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 发送模板消息
     *
     * @param accessToken
     * @param templateMsg
     * @return
     * @throws WxException
     */
    public static TemplateMsgSendedResult sendTemplateMessage(String accessToken, TemplateMsg templateMsg) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(TemplateApi.MESSAGE_TEMPLATE_SEND_URI, accessToken), true, templateMsg.toJson());
            return TemplateMsgSendedResult.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }
}
