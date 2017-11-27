package com.ego.ext.weixin.common.model.result;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 微信接口全局返回码说明
 *
 * @author Administrator
 */
public class Status {

    protected static final Map<Integer, String> status = new HashMap();

    static {
        status.put(-1, "微信系统繁忙");
        status.put(0, "请求成功");
        status.put(40001, "获取access_token时AppSecret错误，或者access_token无效");
        status.put(40002, "不合法的凭证类型");
        status.put(40003, "不合法的OpenID");
        status.put(40004, "不合法的媒体文件类型");
        status.put(40005, "不合法的文件类型");
        status.put(40006, "不合法的文件大小");
        status.put(40007, "不合法的媒体文件id");
        status.put(40008, "不合法的消息类型");
        status.put(40009, "不合法的图片文件大小");
        status.put(40010, "不合法的语音文件大小");
        status.put(40011, "不合法的视频文件大小");
        status.put(40012, "不合法的缩略图文件大小");
        status.put(40013, "不合法的APPID");
        status.put(40014, "不合法的access_token");
        status.put(40015, "不合法的菜单类型");
        status.put(40016, "不合法的按钮个数");
        status.put(40017, "不合法的按钮个数");
        status.put(40018, "不合法的按钮名字长度");
        status.put(40019, "不合法的按钮KEY长度");
        status.put(40020, "不合法的按钮URL长度");
        status.put(40021, "不合法的菜单版本号");
        status.put(40022, "不合法的子菜单级数");
        status.put(40023, "不合法的子菜单按钮个数");
        status.put(40024, "不合法的子菜单按钮类型");
        status.put(40025, "不合法的子菜单按钮名字长度");
        status.put(40026, "不合法的子菜单按钮KEY长度");
        status.put(40027, "不合法的子菜单按钮URL长度");
        status.put(40028, "不合法的自定义菜单使用用户");
        status.put(40029, "不合法的oauth_code");
        status.put(40030, "不合法的refresh_token");
        status.put(40031, "不合法的openid列表");
        status.put(40032, "不合法的openid列表长度");
        status.put(40033, "不合法的请求字符，不能包含\\uxxxx格式的字符");
        status.put(40035, "不合法的参数");
        status.put(40038, "不合法的请求格式");
        status.put(40039, "不合法的URL长度");
        status.put(40050, "不合法的分组id");
        status.put(40051, "分组名字不合法");
        status.put(41001, "缺少access_token参数");
        status.put(41002, "缺少appid参数");
        status.put(41003, "缺少refresh_token参数");
        status.put(41004, "缺少secret参数");
        status.put(41005, "缺少多媒体文件数据");
        status.put(41006, "缺少media_id参数");
        status.put(41007, "缺少子菜单数据");
        status.put(41008, "缺少oauth code");
        status.put(41009, "缺少openid");
        status.put(42001, "access_token超时");
        status.put(42002, "refresh_token超时");
        status.put(42003, "oauth_code超时");
        status.put(43001, "需要GET请求");
        status.put(43002, "需要POST请求");
        status.put(43003, "需要HTTPS请求");
        status.put(43004, "需要接收者关注");
        status.put(43005, "需要好友关系");
        status.put(44001, "多媒体文件为空");
        status.put(44002, "POST的数据包为空");
        status.put(44003, "图文消息内容为空");
        status.put(44004, "文本消息内容为空");
        status.put(45001, "多媒体文件大小超过限制");
        status.put(45002, "消息内容超过限制");
        status.put(45003, "标题字段超过限制");
        status.put(45004, "描述字段超过限制");
        status.put(45005, "链接字段超过限制");
        status.put(45006, "图片链接字段超过限制");
        status.put(45007, "语音播放时间超过限制");
        status.put(45008, "图文消息超过限制");
        status.put(45009, "接口调用超过限制");
        status.put(45010, "创建菜单个数超过限制");
        status.put(45015, "回复时间超过限制");
        status.put(45016, "系统分组，不允许修改");
        status.put(45017, "分组名字过长");
        status.put(45018, "分组数量超过上限");
        status.put(46001, "不存在媒体数据");
        status.put(46002, "不存在的菜单版本");
        status.put(46003, "不存在的菜单数据");
        status.put(46004, "不存在的用户");
        status.put(47001, "解析JSON/XML内容错误");
        status.put(48001, "api功能未授权");
        status.put(50001, "用户未授权该api");
        status.put(61450, "系统错误(system error)");
        // //客服管理接口返回码说明
        status.put(61451, "参数错误(invalid parameter)");
        status.put(61452, "无效客服账号(invalid kf_account)");
        status.put(61453, "客服帐号已存在(kf_account exsited)");
        status.put(61454, "客服帐号名长度超过限制(仅允许10个英文字符，不包括@及@后的公众号的微信号)(invalid kf_acount length)");
        status.put(61455, "客服帐号名包含非法字符(仅允许英文+数字)(illegal character in kf_account)");
        status.put(61456, "客服帐号个数超过限制(10个客服账号)(kf_account count exceeded)");
        status.put(61457, "无效头像文件类型(invalid file type)");
        //Aes
        status.put(-40001, "签名验证错误");
        status.put(-40002, "xml解析失败");
        status.put(-40003, "sha加密生成签名失败");
        status.put(-40004, "SymmetricKey非法");
        status.put(-40005, "appid校验失败");
        status.put(-40006, "aes加密失败");
        status.put(-40007, "aes解密失败");
        status.put(-40008, "解密后得到的buffer非法");

    }

    /**
     * 错误信息
     *
     * @param code
     * @return
     */
    public static String get(int code) {
        if (status.containsKey(code)) {
            return status.get(code);
        }
        return "未知错误";
    }
}
