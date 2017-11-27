package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.Menu;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import java.text.MessageFormat;
import java.util.Map;

/**
 * 菜单,可以将accessToken 存储在session或者memcache中
 *
 * @see http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
 */
@SuppressWarnings("unchecked")
public class MenuApi {

    public static final String MENU_CREATE_URI = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";
    public static final String MENU_GET_URI = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token={0}";
    public static final String MENU_DELETE_URI = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token={0}";

    /**
     * 创建菜单
     *
     *
     * @param accessToken
     * @param params
     * @param retResult 创建菜单请求的详细结果
     * 参考{@link  http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口}
     * @return 正确时的返回JSON数据包如下：
     *
     * {"errcode":0,"errmsg":"ok"} 错误时的返回JSON数据包如下（示例为无效菜单名长度）：
     *
     * {"errcode":40018,"errmsg":"invalid button name size"}
     * @deprecated
     * @throws java.lang.WxException
     */
    public static boolean create(String accessToken, String params, Map<String, Object> retResult) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(MenuApi.MENU_CREATE_URI, accessToken), true, params);
            Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
            if (retResult != null && map != null) {
                retResult.putAll(map);
            }
            return "0".equals(map.get("errcode").toString());
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    public static Result create(String accessToken, Menu menu) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(MenuApi.MENU_CREATE_URI, accessToken), true, menu.toJson());
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 查询菜单
     *
     * @param accessToken
     * @return 对应创建接口，正确的Json返回结果:
     * {"menu":{"button":[{"type":"click","name":"今日歌曲","key":"V1001_TODAY_MUSIC","sub_button":[]},{"type":"click","name":"歌手简介","key":"V1001_TODAY_SINGER","sub_button":[]},{"name":"菜单","sub_button":[{"type":"view","name":"搜索","url":"http://www.soso.com/","sub_button":[]},{"type":"view","name":"视频","url":"http://v.qq.com/","sub_button":[]},{"type":"click","name":"赞一下我们","key":"V1001_GOOD","sub_button":[]}]}]}}
     * @deprecated
     * @throws WxException
     *
     */
    public static String get(String accessToken) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(MENU_GET_URI, accessToken), true);
            //Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
            return jsonStr;
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    public static Menu get(String accessToken, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(MENU_GET_URI, accessToken), true);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
                String menuStr = map.get("menu");
                return Menu.fromJson(menuStr);
            }
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 删除自定义菜单. 使用接口创建自定义菜单后，开发者还可使用接口删除当前使用的自定义菜单。
     *
     * @param accessToken
     * @param retResult 删除菜单请求的详细结果
     * @return
     * @deprecated
     * @throws WxException
     * @see http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
     */
    public static boolean delete(String accessToken, Map<String, Object> retResult) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(MENU_DELETE_URI, accessToken), true);
            Map<String, Object> map = JSON.parseObject(jsonStr, Map.class);
            if (retResult != null && map != null) {
                retResult.putAll(map);
            }
            return "0".equals(map.get("errcode").toString());
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    public static Result delete(String accessToken) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(MENU_DELETE_URI, accessToken), true);
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }
}
