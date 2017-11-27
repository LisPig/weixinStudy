package com.ego.ext.weixin.mp.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ego.core.util.ToolKit;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.model.result.Result;
import com.ego.ext.weixin.common.util.UtilHttp;
import com.ego.ext.weixin.mp.model.Group;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户操作接口
 *
 * 开发者可以使用接口，对公众平台的分组进行查询、创建、修改操作，也可以使用接口在需要时移动用户到某个分组。
 *
 *
 * @see
 * http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口#.E5.88.9B.E5.BB.BA.E5.88.86.E7.BB.84
 */
public class GroupApi {

    public static final String GROUP_CREATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token={0}";
    public static final String GROUP_GET_URI = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token={0}";
    public static final String GROUP_GETID_URI = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token={0}";
    public static final String GROUP_UPDATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token={0}";
    public static final String GROUP_MEMBERS_UPDATE_URI = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token={0}";
    public static final String GROUP_DELETE_URI = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token={0}";

    /**
     * 创建分组
     *
     * 一个公众账号，最多支持创建500个分组。
     *
     * @param accessToken
     * @param name 分组名字（30个字符以内）
     * @param result
     * @return 正常时的返回JSON数据包示例： {"group": { "id": 107, "name": "test" } }
     * @throws WxException
     */
    public static Group create(String accessToken, String name, Result... result) throws WxException {
        try {
            Map<String, Object> group = new HashMap<String, Object>();
            Map<String, Object> nameObj = new HashMap<String, Object>();
            nameObj.put("name", name);
            group.put("group", nameObj);
            String post = JSONObject.toJSONString(group);
            String jsonStr = UtilHttp.post(MessageFormat.format(GROUP_CREATE_URI, accessToken), true, post);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return null;
            } else {
                return Group.fromJson(jsonStr);
            }
            /*
             if (UtilValidate.isNotEmpty(jsonStr)) {
             return JSONObject.parseObject(jsonStr);
             }
             */
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 查询所有分组
     *
     * @param accessToken
     * @param result
     * @return 正常时的返回JSON数据包示例：
     *
     * {
     * "groups": [ { "id": 0, "name": "未分组", "count": 72596 }, { "id": 1,
     * "name": "黑名单", "count": 36 }, { "id": 2, "name": "星标组", "count": 8 }, {
     * "id": 104, "name": "华东媒", "count": 4 }, { "id": 106, "name": "★不测试组★",
     * "count": 1 } ] }
     * @throws WxException
     */
    public static List<Group> list(String accessToken, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.get(MessageFormat.format(GROUP_GET_URI, accessToken), true);
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return new ArrayList<Group>();
            } else {
                Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
                String groupsStr = map.get("groups");
                return JSON.parseArray(jsonStr, Group.class);
                //  return Group.fromJson(jsonStr);
            }
            /*
             if (UtilValidate.isNotEmpty(jsonStr)) {
             return JSONObject.parseObject(jsonStr);
             }
             return null;
             */
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 查询用户所在分组
     *
     * 通过用户的OpenID查询其所在的GroupID。
     *
     * @param accessToken
     * @param openid 用户的OpenID
     * @param result
     * @return 用户所属的groupid,错误返回-1.正常时的返回JSON数据包示例：
     *
     * {"groupid": 102 }
     * @throws WxException
     */
    public static int memberIn(String accessToken, String openid, Result... result) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(GROUP_GETID_URI, accessToken), true, "{\"openid\":\"" + openid + "\"}");
            Result ret = Result.fromJson(jsonStr);
            if (!ret.isSuccess()) {
                if (result != null) {
                    result[0].copy(ret);
                }
                return -1;
            } else {
                Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
                String groupid = map.get("groupid");
                return ToolKit.object2Int(groupid, -1);
            }

        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 修改分组名
     *
     * @param accessToken
     * @param id 分组id，由微信分配
     * @param name 分组名字（30个字符以内）
     * @return 正常时的返回JSON数据包示例： {"errcode": 0, "errmsg": "ok"}
     * @throws WxException
     */
    public static Result updateGroupName(String accessToken, String id, String name) throws WxException {
        try {
            Map<String, Object> group = new HashMap<String, Object>();
            Map<String, Object> nameObj = new HashMap<String, Object>();
            nameObj.put("name", name);
            nameObj.put("id", id);
            group.put("group", nameObj);
            String post = JSONObject.toJSONString(group);
            String jsonStr = UtilHttp.post(MessageFormat.format(GROUP_UPDATE_URI, accessToken), true, post);
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 移动用户分组
     *
     * @param accessToken
     * @param openid 用户唯一标识符
     * @param to_groupid 分组id
     * @return 正常时的返回JSON数据包示例：* {"errcode": 0, "errmsg": "ok"}
     * @throws WxException
     */
    public static Result membersMove(String accessToken, String openid, String to_groupid) throws WxException {
        try {
            String jsonStr = UtilHttp.post(MessageFormat.format(GROUP_MEMBERS_UPDATE_URI, accessToken), true, "{\"openid\":\"" + openid + "\",\"to_groupid\":" + to_groupid + "}");
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    /**
     * 删除分组
     *
     * 注意本接口是删除一个用户分组，删除分组后，所有该分组内的用户自动进入默认分组
     *
     * @see http://mp.weixin.qq.com/wiki/0/56d992c605a97245eb7e617854b169fc.html
     * @param accessToken
     * @param id
     * @return
     * @throws WxException
     */
    public static Result delete(String accessToken, String id) throws WxException {
        try {
            Map<String, Object> group = new HashMap<String, Object>();
            Map<String, Object> nameObj = new HashMap<String, Object>();
            nameObj.put("id", id);
            group.put("group", nameObj);
            String post = JSONObject.toJSONString(group);
            String jsonStr = UtilHttp.post(MessageFormat.format(GROUP_DELETE_URI, accessToken), true, post);
            return Result.fromJson(jsonStr);
        } catch (Exception ex) {
            throw new WxException(ex.getMessage(), ex);
        }
    }

    public static void main(String arg[]) throws Exception {

        /*
         String s = "{\"groups\": [{ \"id\": 0, \"name\": \"未分组\", \"count\": 72596 }]}";
         JSONObject obj = JSONObject.parseObject(s);
         JSONArray groups = obj.getJSONArray("groups");
         Iterator<Object> it = groups.iterator();
         while (it.hasNext()) {
         Map g = (Map) it.next();
         System.out.print(g);
         }
         */
        //String a = WeiXin.getAccessToken("wx413e9374865ee58a", "87f24c1785233247b2e38355b865c6a9", null);
        GroupApi g = new GroupApi();
        Result r = Result.getInstance();
        g.list("ff", r);
        System.out.print(r.getErrcode());
        //System.out.println(g.create(a, "fff"));
        /*
         System.out.println(Result.fromJson("{\n"
         + "    \"group\": {\n"
         + "        \"id\": 107, \n"
         + "        \"name\": \"test\"\n"
         + "    }\n"
         + "}").isSuccess());
         */

    }
}
