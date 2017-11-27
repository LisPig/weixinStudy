/**
 * 微信公众平台开发模式(JAVA) SDK ( http://www.jeasyuicn.com/wechat
 */
package com.ego.ext.weixin.common.util;

import com.ego.core.util.UtilSecurity;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Tools {

    /**
     *
     * @param token Token由开发者任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）。
     * @param signature
     * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @see http://mp.weixin.qq.com/wiki/index.php?title=接入指南
     * @return
     */
    public static final boolean checkSignature(String signature, String token, String timestamp, String nonce) {
        List<String> params = new ArrayList<String>();
        params.add(token);
        params.add(timestamp);
        params.add(nonce);
        Collections.sort(params, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        String temp = params.get(0) + params.get(1) + params.get(2);
        //  return DigestUtils.shaHex(temp).equals(signature);
        return UtilSecurity.encrypt(temp, "SHA-1").equals(signature);
    }

    /**
     * <![CDATA[文本]]>
     *
     * @param content
     * @return
     */
    public static final String formatXML(String content) {
        return MessageFormat.format("<![CDATA[{0}]]>", content);
    }

    /**
     * 判断微信发来的text消息是否是qq标签。
     *
     * @param content 开发模式下微信服务器发来的文本信息
     * @return
     */
    public static boolean isQQFace(String content) {
        boolean result = false;

        // 判断QQ表情的正则表达式 
        String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }
}
