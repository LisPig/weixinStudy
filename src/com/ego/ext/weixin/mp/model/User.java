package com.ego.ext.weixin.mp.model;

import com.alibaba.fastjson.JSON;

/**
 * 用戶实体
 *
 *
 */
public class User {

    //用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
    private int subscribe;
    //用户的标识，对当前公众号唯一
    private String openid;
    private String nickname;
    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private int sex;
    private String language;
    private String city;
    private String province;
    //用户所在国家
    private String country;
    //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
    private String headimgurl;
    private Long subscribe_time;
    /*用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
     * @deprecated 
     */
    private Object privilege[];
    //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
    private String unionid;

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     *
     * @return
     */
    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    /**
     * 用户的标识，对当前公众号唯一
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
     * 用户的昵称
     *
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     *
     * @return
     */
    public int getSex() {
        return sex;
    }

    public String getSexCN() {
        if (this.sex == 1) {
            return "男";
        } else if (this.sex == 2) {
            return "女";
        } else {
            return "未知";
        }
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
     * 用户的语言，简体中文为zh_CN
     *
     * @return
     */
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 用户所在城市
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 用户所在省份
     *
     * @return
     */
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 用户所在国家
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
     *
     * @return
     */
    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     *
     * @return
     */
    public Long getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(Long subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    /**
     * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
     *
     * @deprecated
     * @return
     */
    public Object[] getPrivilege() {
        return this.privilege;

    }

    public void setPrivilege(Object[] privileges) {
        this.privilege = privileges;

    }

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
     *
     * @return
     */
    public String getUnionid() {
        return this.unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static User fromJson(String json) {
        return JSON.parseObject(json, User.class
        );
    }

}
