package com.ego.ext.weixin.common.util;

import com.ego.ext.weixin.common.context.WxConfigHolder;
import com.ego.core.util.UtilString;
import com.ego.ext.weixin.common.WxException;
import com.ego.ext.weixin.common.aes.WXBizMsgCrypt;
import com.ego.ext.weixin.common.model.WxConfig;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 对微信平台官方给出的加密解析代码进行再次封装
 *
 * 异常java.security.InvalidKeyException:illegal Key Size的解决方案：
 * 1：在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
 * 2：下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt
 * 3：如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件
 * 4：如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件
 *
 *
 * 设置为消息加密模式后 JFinal Action Report 中有如下参数： timestamp=1417610658 encrypt_type=aes
 * nonce=132155339 msg_signature=8ed2a14146c924153743162ab2c0d28eaf30a323
 * signature=1a3fad9a528869b1a73faf4c8054b7eeda2463d3
 */
public class UtilEncryp {

    /**
     * 将公众平台回复用户的消息加密打包.
     * <ol>
     * <li>对要发送的消息进行AES-CBC加密</li>
     * <li>生成安全签名</li>
     * <li>将消息密文和安全签名打包成xml格式</li>
     * </ol>
     *
     * @param replyMsg 公众平台待回复用户的消息，xml格式的字符串
     * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
     * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
     *
     * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce,
     * encrypt的xml格式的字符串
     * @throws WxException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public static String encryptMsg(String replyMsg, String timestamp, String nonce) throws WxException {
        WxConfig cfg = WxConfigHolder.getConfig();
        WXBizMsgCrypt pc = new WXBizMsgCrypt(cfg.getToken(), cfg.getEncodingAESKey(), cfg.getAppId());
        return pc.encryptMsg(replyMsg, timestamp, nonce);
    }

    /**
     * 将公众平台回复用户的消息加密打包.
     * <ol>
     * <li>对要发送的消息进行AES-CBC加密</li>
     * <li>生成安全签名</li>
     * <li>将消息密文和安全签名打包成xml格式</li>
     * </ol>
     *
     * @param replyMsg 公众平台待回复用户的消息，xml格式的字符串
     * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
     * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
     *
     * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce,
     * encrypt的xml格式的字符串
     * @throws WxException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public static ByteArrayOutputStream encryptMsgToOutStream(String replyMsg, String timestamp, String nonce) throws WxException {
        try {
            String re = encryptMsg(replyMsg, timestamp, nonce);
            return UtilString.string2OutStream(re, null);
        } catch (IOException ex) {
            throw new WxException("将微信加密结果写入流时错误", ex);
        }
    }
  /**
     * 将公众平台回复用户的消息加密打包并写入输出流.
     * <ol>
     * <li>对要发送的消息进行AES-CBC加密</li>
     * <li>生成安全签名</li>
     * <li>将消息密文和安全签名打包成xml格式</li>
     * </ol>
     *
     * @param replyMsg 公众平台待回复用户的消息，xml格式的字符串
     * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
     * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
     *
     * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce,
     * encrypt的xml格式的字符串
     * @throws WxException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public static void encryptMsgToOutStream(String replyMsg, String timestamp, String nonce, OutputStream out) throws WxException {
        try {
            String ret = encryptMsg(replyMsg, timestamp, nonce);
            byte[] b = ret.getBytes(WXBizMsgCrypt.CHARSET.name());
            out.write(b, 0, b.length);
        } catch (IOException ex) {
            throw new WxException("将微信加密结果写入流时错误", ex);
        }
    }

    /**
     * 检验消息的真实性，并且获取解密后的明文.
     * <ol>
     * <li>利用收到的密文生成安全签名，进行签名验证</li>
     * <li>若验证通过，则提取xml中的加密消息</li>
     * <li>对消息进行解密</li>
     * </ol>
     *
     * @param msgSignature 签名串，对应URL参数的msg_signature
     * @param timestamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param encryptedMsg 密文，对应POST请求的数据
     *
     * @return 解密后的原文
     */
    public static String decryptMsg(String encryptedMsg, String timestamp, String nonce, String msgSignature) {
        try {
            WxConfig cfg = WxConfigHolder.getConfig();
            //
            String encodingAesKey = cfg.getEncodingAESKey();
            if (encodingAesKey == null) {
                throw new IllegalStateException("encodingAesKey can not be null, config encodingAesKey first.");
            }

            WXBizMsgCrypt pc = new WXBizMsgCrypt(cfg.getToken(), encodingAesKey, cfg.getAppId());
            return pc.decryptMsg(msgSignature, timestamp, nonce, encryptedMsg);	// 此处 timestamp 如果与加密前的不同则报签名不正确的异常

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检验消息的真实性，并且获取解密后的明文.
     * <ol>
     * <li>利用收到的密文生成安全签名，进行签名验证</li>
     * <li>若验证通过，则提取xml中的加密消息</li>
     * <li>对消息进行解密</li>
     * </ol>
     *
     * @param msgSignature 签名串，对应URL参数的msg_signature
     * @param timestamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param encryptedMsg 密文，对应POST请求的数据
     *
     * @return 解密后的原文utf-8编码后的字节流
     */
    public static InputStream decryptMsgToInputStream(String encryptedMsg, String msgSignature, String timestamp, String nonce) {
        try {
            String ret = decryptMsg(encryptedMsg, timestamp, nonce, msgSignature);
            return UtilString.string2InputStream(ret, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
