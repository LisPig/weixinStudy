package com.ego.ext.weixin.common.model;

/**
 * 存放 微信公众号与企业号需要用到的公共才参数
 *
 * @see http://mp.weixin.qq.com/wiki/13/80a1a25adbc46faf2716774c423b3151.html
 * @see http://qydev.weixin.qq.com/wiki/index.php?title=回调模式
 * @author
 *
 */
public class WxConfig {

    protected String appId;
    protected String secret;
    protected String token;
    protected String encodingAESKey;
    private boolean encrypt = false;	// 消息加密与否

    /**
     * 对应公众号的appId 或者企业号的corpid
     *
     * @return
     */
    public String getAppId() {
        return this.appId;
    }

    /**
     * 对应公众号的appId 或者企业号的corpid
     *
     * @param id
     */
    public WxConfig setAppId(String id) {
        this.appId = id;
        return this;
    }

    /**
     * 对应公众号的appSecret 或者企业号的corpSecret
     *
     * @return
     */
    public String getSecret() {
        return this.secret;
    }

    /**
     * 对应公众号的appSecret 或者企业号的corpSecret
     *
     * @param secret
     */
    public WxConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    /**
     * Token可由开发者可以任意填写，用作生成签名
     *
     * @return
     */
    public String getToken() {
        return this.token;
    }

    public WxConfig setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * 微信公众平台采用AES对称加密算法对推送给公众帐号的消息体对行加密，EncodingAESKey则是加密所用的秘钥。公众帐号用此秘钥对收到的密文消息体进行解密，回复消息体也用此秘钥加密。
     *
     * @see
     * http://mp.weixin.qq.com/wiki/13/80a1a25adbc46faf2716774c423b3151.html
     * @see http://qydev.weixin.qq.com/wiki/index.php?title=回调模式
     * @return
     */
    public String getEncodingAESKey() {
        return encodingAESKey;
    }

    /**
     * 微信公众平台采用AES对称加密算法对推送给公众帐号的消息体对行加密，EncodingAESKey则是加密所用的秘钥。公众帐号用此秘钥对收到的密文消息体进行解密，回复消息体也用此秘钥加密。
     *
     * @param encodingAESKey
     */
    public WxConfig setEncodingAESKey(String encodingAESKey) {
        this.encodingAESKey = encodingAESKey;
        return this;
    }

    /**
     * 是否对消息进行加密，对应于微信平台的消息加解密方式：
     *
     * 1：true进行加密且必须配置 encodingAESKey
     *
     *
     * 2：false采用明文模式，同时也支持混合模式
     *
     * @return
     */
    public boolean isEncrypt() {
        return encrypt;
    }

    /**
     *
     *
     * @param encrypt
     */
    public WxConfig setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
        return this;
    }
}
