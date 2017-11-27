/*----------javabean---------
 * @功能说明：主要提供一些安全相关的工具方法，如加密、解密、消息验证码等
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.util;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 主要提供一些安全相关的工具方法，如加密、解密、消息验证码等
 *
 * @version 1.7
 * @author Administrator
 */
public class UtilSecurity {

    private static final String MAGIC = "$1$";
    private static byte[] finals = new byte[16];
    private static String table = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static char[] intToAscii64 = table.toCharArray();

   

    /**
     * MD5加密算法
     *
     * @param salt:一个包含$1$的种子串,加密算法的种子特征和长度详见Unix系统加密的内容
     * @param pw:一个待加密的原始串
     * @return:返回一个加密后的随机串
     */
    public static String encryptByMD5(String salt, String pw) {
        try {
            String fix = salt.substring(3);
            if (fix.length() > 8) {
                fix = fix.substring(0, 8);
            }
            int index = fix.indexOf("$");
            if (index != -1) {
                fix = fix.substring(0, index);
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            MessageDigest md1 = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(pw.getBytes());
            md.update(MAGIC.getBytes());
            md.update(fix.getBytes());

            md1.reset();
            md1.update(pw.getBytes());
            md1.update(fix.getBytes());
            md1.update(pw.getBytes());
            finals = md1.digest();
            for (int i = pw.length(); i > 0; i -= 16) {
                int len = (i > 16) ? 16 : i;
                md.update(finals, 0, len);
            }
            finals = new byte[16];
            for (int j = pw.length(); j > 0; j >>= 1) {
                if ((j & 1) > 0) {
                    md.update(finals, 0, 1);
                } else {
                    md.update(pw.getBytes(), 0, 1);
                }
            }
            StringBuffer password = new StringBuffer();
            password.append(MAGIC).append(fix).append("$");
            finals = md.digest();
            for (int k = 0; k < 1000; k++) {
                md1.reset();
                if ((k & 1) > 0) {
                    md1.update(pw.getBytes());
                } else {
                    md1.update(finals);
                }
                if (k % 3 > 0) {
                    md1.update(fix.getBytes());
                }
                if (k % 7 > 0) {
                    md1.update(pw.getBytes());
                }
                if ((k & 1) > 0) {
                    md1.update(finals);
                } else {
                    md1.update(pw.getBytes());
                }
                finals = md1.digest();
            }
            long pow24 = (long) Math.pow(2, 24);
            long pow16 = (long) Math.pow(2, 16);
            long pow8 = (long) Math.pow(2, 8);
            long l = ((finals[0] << 16) + pow24) % pow24
                    | ((finals[6] << 8) + pow16) % pow16 | (finals[12] + pow8) % pow8;
            crypt_i2a64(password, l, 4);
            l = ((finals[1] << 16) + pow24) % pow24
                    | ((finals[7] << 8) + pow16) % pow16 | (finals[13] + pow8) % pow8;
            crypt_i2a64(password, l, 4);
            l = ((finals[2] << 16) + pow24) % pow24
                    | ((finals[8] << 8) + pow16) % pow16 | (finals[14] + pow8) % pow8;
            crypt_i2a64(password, l, 4);
            l = ((finals[3] << 16) + pow24) % pow24
                    | ((finals[9] << 8) + pow16) % pow16 | (finals[15] + pow8) % pow8;
            crypt_i2a64(password, l, 4);
            l = ((finals[4] << 16) + pow24) % pow24
                    | ((finals[10] << 8) + pow16) % pow16 | (finals[5] + pow8) % pow8;
            crypt_i2a64(password, l, 4);
            l = (finals[11] + pow8) % pow8;
            crypt_i2a64(password, l, 2);
            finals = new byte[16];
            return new String(password);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 方法:crypt_i2a64(StringBuffer,long,int)
     *
     * @param sb :一个保存返回值的StringBuffer,
     * @param v :一个长整型数值
     * @param n :一个整型数值,控制循环次数
     */
    private static void crypt_i2a64(StringBuffer sb, long v, int n) {
        while (--n >= 0) {
            sb.append(intToAscii64[(int) (v & 0x3f)]);
            v >>= 6;
        }
    }

    /**
     * 加密
     *
     * @param strSrc 待加密的字符串
     * @param encodeType 加密类型，默认为"MD5",其他如"SHA-1"、"SHA-256"
     * @return
     */
    public static String encrypt(String strSrc, String encodeType) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            if (encodeType == null || "".equals(encodeType)) {
                encodeType = "MD5";//默认使用MD5
            }
            md = MessageDigest.getInstance(encodeType);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return strSrc;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    //
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static byte[] MD5(String text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "System doesn't support MD5 algorithm.");
        }
        msgDigest.reset();
        try {
            msgDigest.update(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(
                    "System doesn't support your  EncodingException.");
        }
        return msgDigest.digest();

    }

    public static String MD5S(String text) {
        byte[] bytes = MD5(text);
        String md5Str = new String(encodeHex(bytes));
        return md5Str;
    }

    public static char[] encodeHex(byte[] data) {
        int l = data.length;

        char[] out = new char[l << 1];

        int i = 0;
        for (int j = 0; i < l; i++) {
            out[(j++)] = DIGITS[((0xF0 & data[i]) >>> 4)];
            out[(j++)] = DIGITS[(0xF & data[i])];
        }
        return out;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            UtilSecurity se = new UtilSecurity();
            String strSrc = "可以加密汉字";
            System.out.println("Source String:" + strSrc);
            System.out.println("Encrypted String:");
            System.out.println("Use MD5:" + se.encrypt(strSrc, null));
            System.out.println("Use MD5:" + se.encrypt(strSrc, "MD5"));
            System.out.println("Use SHA:" + se.encrypt(strSrc, "SHA-1"));
            System.out.println("Use SHA-256:" + se.encrypt(strSrc, "SHA-256"));
        } catch (Exception ex) {
            Logger.getLogger(UtilSecurity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
