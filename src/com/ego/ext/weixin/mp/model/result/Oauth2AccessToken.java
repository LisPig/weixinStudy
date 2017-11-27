/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ego.ext.weixin.mp.model.result;

import com.alibaba.fastjson.JSON;
import com.ego.ext.weixin.common.model.AccessToken;

/**
 * 封装 网页授权access_token
 *
 * @author Administrator
 */
public class Oauth2AccessToken extends AccessToken {

    private String refresh_token;
    private String openid;
    private String scope;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
     */
    private String unionid;

    public Oauth2AccessToken() {
        super();

    }

    public Oauth2AccessToken(String access_token) {
        super(access_token);

    }

    /**
     * 用户刷新access_token
     *
     * @return
     */
    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     *
     * @return
     */
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * 用户授权的作用域，使用逗号（,）分隔
     *
     * @return
     */
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public static Oauth2AccessToken fromJson(String json) {
        return JSON.parseObject(json, Oauth2AccessToken.class);
    }

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
     *
     * @return the unionid
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
     *
     * @param unionid the unionid to set
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

}
