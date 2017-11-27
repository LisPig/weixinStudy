/*----------javabean---------
 * @功能说明：主要设置一些用于用于检验对象、数字等的便捷方法。
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主要设置一些用于用于检验对象、数字等的便捷方法。
 */
public class UtilValidate {

    public static final String module = UtilValidate.class.getName();
    /**
     * boolean specifying by default whether or not it is okay for a String to
     * be empty
     */
    public static final boolean defaultEmptyOK = true;
    /**
     * 空白字符，有制表符、回车符、换行符
     */
    public static final String whitespace = " \t\n\r";
    /**
     * 数字字符
     */
    public static final String digits = "0123456789";
    /**
     * 十六进制字符
     */
    public static final String hexDigits = digits + "abcdefABCDEF";
    /**
     * non-digit characters which are allowed in phone numbers
     */
    public static final String phoneNumberDelimiters = "()- ";

    /**
     * 整数
     */
    private static final String V_INTEGER = "^-?[1-9]\\d*$";

    /**
     * 正整数
     */
    private static final String V_Z_INDEX = "^[1-9]\\d*$";

    /**
     * 负整数
     */
    private static final String V_NEGATIVE_INTEGER = "^-[1-9]\\d*$";

    /**
     * 数字
     */
    private static final String V_NUMBER = "^([+-]?)\\d*\\.?\\d+$";

    /**
     * 正数
     */
    private static final String V_POSITIVE_NUMBER = "^[1-9]\\d*|0$";

    /**
     * 负数
     */
    private static final String V_NEGATINE_NUMBER = "^-[1-9]\\d*|0$";

    /**
     * 浮点数
     */
    private static final String V_FLOAT = "^([+-]?)\\d*\\.\\d+$";

    /**
     * 正浮点数
     */
    private static final String V_POSTTIVE_FLOAT = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";

    /**
     * 负浮点数
     */
    private static final String V_NEGATIVE_FLOAT = "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$";

    /**
     * 非负浮点数（正浮点数 + 0）
     */
    private static final String V_UNPOSITIVE_FLOAT = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";

    /**
     * 非正浮点数（负浮点数 + 0）
     */
    private static final String V_UN_NEGATIVE_FLOAT = "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$";

    /**
     * 邮件
     */
    private static final String V_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    /**
     * 颜色
     */
    private static final String V_COLOR = "^[a-fA-F0-9]{6}$";

    /**
     * url
     */
    private static final String V_URL = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&*=]*)?$";

    /**
     * 仅中文
     */
    private static final String V_CHINESE = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";

    /**
     * 仅ACSII字符
     */
    private static final String V_ASCII = "^[\\x00-\\xFF]+$";

    /**
     * 邮编
     */
    private static final String V_ZIPCODE = "^\\d{6}$";

    /**
     * 手机
     */
    private static final String V_MOBILE = "^(13|15)[0-9]{9}$";

    /**
     * ip地址
     */
    private static final String V_IP4 = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";

    /**
     * 非空
     */
    private static final String V_NOTEMPTY = "^\\S+$";

    /**
     * 图片
     */
    private static final String V_PICTURE = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";

    /**
     * 压缩文件
     */
    private static final String V_RAR = "(.*)\\.(rar|zip|7zip|tgz)$";

    /**
     * 日期
     */
    private static final String V_DATE = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";

    /**
     * QQ号码
     */
    private static final String V_QQ_NUMBER = "^[1-9]*[1-9][0-9]*$";

    /**
     * 电话号码的函数(包括验证国内区号,国际区号,分机号)
     */
    private static final String V_TEL = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";

    /**
     * 用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
     */
    private static final String V_USERNAME = "^\\w+$";

    /**
     * 用来用户注册。匹配由中文、数字、26个英文字母或者下划线组成的字符串
     */
    private static final String V_ACCOUNT = "[\u4e00-\u9fa5\\w]+";

    /**
     * 字母
     */
    private static final String V_LETTER = "^[A-Za-z]+$";

    /**
     * 大写字母
     */
    private static final String V_LETTER_U = "^[A-Z]+$";

    /**
     * 小写字母
     */
    private static final String V_LETTER_I = "^[a-z]+$";

    /**
     * 身份证
     */
    private static final String V_IDCARD = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";

    /**
     * 验证密码(数字和英文同时存在)
     */
    private static final String V_PASSWORD_REG = "[A-Za-z]+[0-9]";

    /**
     * 验证密码长度(6-18位)
     */
    private static final String V_PASSWORD_LENGTH = "^\\d{6,18}$";

    /**
     * 验证两位数
     */
    private static final String V_TWO＿POINT = "^[0-9]+(.[0-9]{2})?$";

    /**
     * 验证一个月的31天
     */
    private static final String V_31DAYS = "^((0?[1-9])|((1|2)[0-9])|30|31)$";

    /**
     * 验证CSS的class tag id
     */
    private static final String V_CSS = "^[class|id|tag]+\\[+([\\w-])+\\]+(\\[([0-9]+)\\])?$";

    /**
     * 私有构造方法，不允许创建实例，只能使用本类声明的静态方法
     */
    private UtilValidate() {
    }

    /**
     * 数组中的每个值是否都不为空
     *
     * @param values
     * <p>
     * @return
     */
    public static boolean isObjectsNotEmpty(Object... values) {
        for (Object o : values) {
            if (isEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组中的每个值是否都为空
     *
     * @param values
     * <p>
     * @return
     */
    public static boolean isObjectsEmpty(Object... values) {
        for (Object o : values) {
            if (isNotEmpty(o)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    /**
     * 检查一个对象是否为空，检查的对象可以是String, Map,Collection,CharSequence 等等
     *
     * <p>
     *
     * Check whether an object is empty, will see if it is a String, Map,
     * Collection, etc.
     *
     *
     * @param value
     * <p>
     * @return
     */
    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return UtilValidate.isEmpty((String) value);
        }
        if (value instanceof Collection) {
            return UtilValidate.isEmpty((Collection<? extends Object>) value);
        }
        if (value instanceof Map) {
            return UtilValidate.isEmpty((Map<? extends Object, ? extends Object>) value);
        }
        if (value instanceof CharSequence) {
            return UtilValidate.isEmpty((CharSequence) value);
        }

        // These types would flood the log
        // Number covers: BigDecimal, BigInteger, Byte, Double, Float, Integer, Long, Short
        if (value instanceof Boolean) {
            return false;
        }
        if (value instanceof Number) {
            return false;
        }
        if (value instanceof Character) {
            return false;
        }
        if (value instanceof java.sql.Timestamp) {
            return false;
        }

        return false;
    }

    /**
     *
     * 检测给定字符串参数是否为空。
     *
     * <p>
     *
     * <b>依据:</b>如果这个参数不存在(为null）或是空字符串（“”）则这个参数为空
     *
     * @return 布尔值。如果传入的参数为空或是空字符串返回true，反之为false
     */
    public static boolean isEmpty(String parameter) {
        return parameter == null || parameter.trim().equals("");
    }

    /**
     * 检测给定字符串参数数组是否为空。
     *
     * <p>
     *
     * <b>依据:</b>如果这个参数数组中的任意字符串不存在(为null）或是空字符串（“”）则这个参数为空
     *
     * @param parameter
     * @return 布尔值。如果传入的参数数组中任意一个为空或是空字符串返回true。
     */
    public static boolean isEmpty(String[] parameter) {
        for (int i = 0, length = parameter.length; i < length; i++) {
            if (isEmpty(parameter[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查一个集合是否为空，集合为null或集合的元素个数为0为空
     *
     * @param <E>
     * @param c
     * <p>
     * @return
     */
    public static <E> boolean isEmpty(Collection<E> c) {
        return ((c == null) || (c.size() == 0));
    }

    /**
     * 检查一个映射是否为空。
     *
     * @param <K>
     * @param <E>
     * @param m
     * <p>
     * @return
     */
    public static <K, E> boolean isEmpty(Map<K, E> m) {
        return ((m == null) || (m.size() == 0));
    }

    /**
     * 检查字符序列是否为空。为null或字符长度为0为空
     *
     * @param <E>
     * @param c
     * <p>
     * @return
     */
    public static <E> boolean isEmpty(CharSequence c) {
        return ((c == null) || (c.length() == 0));
    }

    /**
     * Check whether string s is NOT empty.
     */
    /**
     * 检查字符串不为空，非null或字符串长度大于0不为空
     *
     * @param s
     * <p>
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return ((s != null) && (s.length() > 0));
    }

    /**
     * 检查集合不为空，非null或元素个数大于0，非空
     *
     * @param <E>
     * @param c
     * <p>
     * @return
     */
    public static <E> boolean isNotEmpty(Collection<E> c) {
        return ((c != null) && (c.size() > 0));
    }

    /**
     * 检查映射不为空，非null或元素个数大于0，非空
     *
     * @param <E>
     * @param c
     * <p>
     * @return
     */
    public static <E> boolean isNotEmpty(CharSequence c) {
        return ((c != null) && (c.length() > 0));
    }

    /**
     * 检验数组是否为空，如果为null或数组的长度为0 表示为空
     *
     * @param array
     * <p>
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return null == array || array.length == 0;
    }

    /**
     * 检验数组不为空
     *
     * @param array
     * <p>
     * @return
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 检查对象是字符串
     *
     * @param obj
     * <p>
     * @return
     */
    public static boolean isString(Object obj) {
        return ((obj != null) && (obj instanceof java.lang.String));
    }

    /**
     * 检查字符串是否是空白字符串，如果传入字符串为空返回true。
     *
     * <p>
     *
     * Search through string's characters one by one until we find a
     * non-whitespace character. When we do, return false; if we don't, return
     * true.
     *
     * @param s
     * <p>
     * @return
     */
    public static boolean isWhitespace(String s) {
        // Is s empty?
        if (isEmpty(s)) {
            return true;
        }

        // Search through string's characters one by one
        // until we find a non-whitespace character.
        // When we do, return false; if we don't, return true.
        for (int i = 0; i < s.length(); i++) {
            // Check that current character isn't whitespace.
            char c = s.charAt(i);

            if (whitespace.indexOf(c) == -1) {
                return false;
            }
        }
        // All characters are whitespace.
        return true;
    }

    /**
     * 检测给定字符串参数中间是否存在空格；
     *
     * <p>
     *
     * <b>依据:</b>这个字符串参数中间是否有空格存在
     *
     * @param str,要检测的字符串,不能为null，否则抛出空指针错误
     * <p>
     * @return 布尔值。如果字符串中间有空格返回true
     */
    public static boolean hasMidBlank(String str) {
        return str.trim().indexOf(" ") > -1;
    }

    /**
     *
     * 检测指定字符串参数中间是否存在空格；
     *
     * 重载方法；批量检测。
     *
     * <p>
     *
     * <b>依据:</b>这个字符串参数数组中任意一个字符串间是否有空格存在
     *
     * @para str[],数组类型，要检测的字符串数组
     * <p>
     * @return 布尔值。如果字符串数组中任意一个字符串含有空格则返回true。
     */
    public static boolean hasMidBlank(String[] str) {
        for (int i = 0; i < str.length; i++) {
            if (hasMidBlank(str[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * 检测指定字符串参数是否为数字，包括正负正数、正负小数
     *
     *
     *
     * <p>
     * @return
     */
    public static boolean isNumber(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[0-9]+(.[0-9]+)?$");
        return pattern.matcher(str).matches();
    }

    /**
     *
     * 检测指定字符串参数是否全部为数字.
     *
     * <p>
     *
     *
     *
     * <p>
     * @return 如果字符串数组中任意一个字符串不为数字返回false
     */
    public static boolean isNumber(String[] str) {
        for (int i = 0; i < str.length; i++) {
            if (!isNumber(str[i])) //只要有一个不是数字就返回false
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是数字
     *
     * @param strarray 字符串，多个值之间用”,"分割
     * @return
     */
    public static boolean isNumber2(String strarray) {
        return isNumber2(strarray, ",");
    }

    /**
     * 判断是否是数字
     *
     * @param strarray 分隔符
     * @param seprator
     * @return
     */
    public static boolean isNumber2(String strarray, String seprator) {
        if (strarray == null) {
            return false;
        }
        return isNumber(strarray.split(seprator));
    }

    /**
     * 判断是否是正数，包括正负正数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[0-9]+$");
        return pattern.matcher(str).matches();
    }

    public static boolean isInteger(String[] str) {
        for (int i = 0; i < str.length; i++) {
            if (!isInteger(str[i])) //只要有一个不是数字就返回false
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是小数
     *
     * @param str
     * @return
     */
    public static boolean isDecimal(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[0-9]+.{1}[0-9]+$");
        return pattern.matcher(str).matches();
    }

    public static boolean isDecimal(String[] str) {
        for (int i = 0; i < str.length; i++) {
            if (!isDecimal(str[i])) //只要有一个不是数字就返回false
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为数字，包括小数或整数,正数、负数
     *
     * @param str
     * <p>
     * @return
     */
    public static boolean isDigit(String str) {
        return str == null ? false : str.trim().matches("^-?[0-9]+.?[0-9]+$");
    }

    public static boolean isDigit(String[] str) {
        for (int i = 0; i < str.length; i++) {
            if (!isDigit(str[i])) //只要有一个不是数字就返回false
            {
                return false;
            }
        }
        return true;
    }
    /*
     *
     * 检测指定字符串数字参数是否为手机号码.
     *
     * <p>
     *
     * <b>依据:</b>这个数字字符串为null、为空字符串、或不全是数字，或数字的个数不是11位，则不是手机号
     *
     * @param mobile,字符串形式的电话数字
     * @return 布尔值。是手机号返回true，否则返回false
     */
    /*
     public static boolean isMobile(String mobile) {
     if (!isEmpty(mobile) && isNumber(mobile) && mobile.trim().length() == 11) {
     return true;
     }
     return false;
     }
     */

    /**
     * 判断传入的字符串是否是一个合法的移动手机号码(中国)
     *
     * @param srcNo
     * <p>
     * @return true or false
     */
    public static boolean isMobile(String srcNo) {
        if (srcNo != null) {
            // return srcNo.matches("(13|15)[0-9]{9}");
            return UtilString.trim(srcNo).matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        } else {
            return false;
        }
    }

    /**
     * 检查邮箱有效性
     *
     * @param emailName
     * <p>
     * @return
     */
    public static boolean isEmail(String emailName) {
        if (emailName.matches("[\\w\\W]{3,30}@(?:\\w+\\.)+\\w+")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证Email地址是否有效
     *
     * @param sEmail
     * <p>
     * @return
     */
    public static boolean isEmail2(String sEmail) {
        String pattern = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return sEmail.matches(pattern);
    }

    /**
     * 判断字符串是否为中文
     *
     * @param str
     * <p>
     * @return
     */
    public static boolean isChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > 128) {
                return true;
            }

        }
        return false;

    }

    /**
     * 判断字符串转型
     *
     * @param str
     * <p>
     * @return
     */
    public static boolean isLong(String str) {
        boolean b = false;
        try {
            Long.parseLong(str);
            b = true;
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    /**
     *
     * 检测指定字符串参数是否超过最大长度字数。字符串两边的空格将不计入字符串数目。
     *
     * <p>
     *
     * <b>依据:</b>去掉给定字符串参数两侧空格后比较字符串的长度与给定的最大长度值maxLength比较
     *
     * @param str 要检测的字符串。
     * @param maxLength 指定字符串不能超过的最长子数
     * <p>
     * @return 如果超过最大长度返回true。
     */
    public static boolean isOutOfMaxLength(String str, int maxLength) {
        if (str.trim().length() > maxLength) {
            return true;
        }
        return false;
    }

    /**
     *
     *
     * 检测指定字符串参数是否超过最小长度字数。字符串两边的空格将不计入字符串数目。
     *
     * <p>
     *
     * <b>依据:</b>去掉给定字符串参数两侧空格后比较字符串的长度与给定的最小大长度值minLength比较
     *
     * @param st要检测的字符串。
     * @param mi指定字符串不能超过的最短子数
     * <p>
     * @return 如果超过最小长度返回true。
     */
    public static boolean isOutMinLength(String str, int minLength) {
        if (str.trim().length() < minLength) {
            return true;
        }
        return false;
    }

    /**
     * 检验一个字符是否出现在指定的字符串s中
     *
     * <p>
     *
     * Returns true if single character c(actually a string) is contained within
     * string s.
     *
     * @param c
     * @param s
     * <p>
     * @return
     */
    public static boolean isIncluded(char c, String s) {
        return (s.indexOf(c) != -1);
        // for(int i = 0; i < s.length; i++) {
        // if (s.charAt(i) == c) return true;
        // }
        // return false;
    }

    /**
     * 判断一个字符串中是否包含另一个字符串
     *
     * @param parentStr 父串
     * @param subStr 子串
     * <p>
     * @return 如果父串包含子串的内容返回真，否则返回假
     */
    public static boolean isIncluded(String parentStr, String subStr) {
        boolean hasSub = false;
        for (int i = 0; i < (parentStr.length() - subStr.length() + 1); i++) {
            String tempString = parentStr.substring(i, i + subStr.length());
            if (subStr.equals(tempString)) {
                hasSub = true;
                break;
            }
        }

        return hasSub;
    }

    /**
     * 检验字符是否是是字母
     *
     * <p>
     *
     * Returns true if character c is an English letter (A .. Z, a..z).
     *
     * NOTE: Need i18n version to support European characters. This could be
     * tricky due to different character sets and orderings for various
     * languages and platforms.
     *
     * @param c
     * <p>
     * @return
     */
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    /**
     * 检验字符是否是数字
     *
     * @param c
     * <p>
     * @return
     */
    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    /**
     * 字符是字母或数字（十六进制）
     *
     * @param c
     * <p>
     * @return
     */
    public static boolean isLetterOrDigit(char c) {
        return Character.isLetterOrDigit(c);
    }

    /**
     * Returns true if character c is a letter or digit.
     */
    public static boolean isHexDigit(char c) {
        return hexDigits.indexOf(c) >= 0;
    }

    /**
     * 是否是正整数
     *
     *
     * Returns true if string s is an integer > 0. NOTE: using the Java Long
     * object for greatest precision
     */
    public static boolean isPositiveInteger(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        try {
            long temp = Long.parseLong(s);

            if (temp > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

        // return(isSignedInteger(s, secondArg)
        // &&((isEmpty(s) && secondArg)  ||(parseInt(s) > 0)));
    }

    /**
     * isInternationalPhoneNumber returns true if string s is a valid
     * international phone number. Must be digits only; any length OK. May be
     * prefixed by + character.
     */
    public static boolean isInternationalPhoneNumber(String s) {
        if (isEmpty(s)) {
            return defaultEmptyOK;
        }

        String normalizedPhone = UtilString.stripCharsInBag(s, phoneNumberDelimiters);

        return isPositiveInteger(normalizedPhone);
    }

    /**
     * 初步检验是否是url，检验方式是看字符串是否包含://
     *
     *
     * @param s 待验证的url字符串
     * <p>
     * @return 返回true，如果包含 ://
     */
    public static boolean isUrl(String s) {
        if (isEmpty(s)) {
            return false;
        }
        if (s.indexOf("://") != -1) {
            return true;
        }
        return false;
    }

    /**
     * 检验url字符串是否是有效的
     *
     * @param s
     * <p>
     * @return
     */
    public static boolean isValidUrl(String url) {
        if (url == null) {
            return false;
        }

        if (url.startsWith("https://")) {
            // URL doesn't understand the https protocol, hack it
            url = "http://" + url.substring(8);
        }

        try {
            new URL(url);

            return true;
        } catch (MalformedURLException e) {

            return false;
        }
    }

    /**
     * 判断一个字符串是否是合法的Java标识符
     *
     * @param s 待判断的字符串
     * <p>
     * @return 如果参数s是一个合法的Java标识符返回真，否则返回假
     */
    public static boolean isJavaIdentifier(String s) {
        if (s.length() == 0 || Character.isJavaIdentifierStart(s.charAt(0))) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isJavaIdentifierPart(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否包含小写字符
     *
     * @param str 待检测的字符串
     * <p>
     * @return
     */
    public static boolean isLowerCaseContained(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (Character.isLowerCase(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String trimSpaces(String IP) {
        while (IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }

    public static boolean isIp(String IP) {
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = IP.split("\\.");
            if ((Integer.parseInt(s[0]) < 255)
                    && (Integer.parseInt(s[1]) < 255)
                    && (Integer.parseInt(s[2]) < 255)
                    && (Integer.parseInt(s[3]) < 255)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断数组中是否包含s
     *
     * @param arr
     * @param s
     * @return
     */
    public static boolean contains(String[] arr, String s) {
        if (UtilValidate.isNotEmpty(arr)) {
            for (String a : arr) {
                if (UtilValidate.isNotEmpty(a) && a.equals(s)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * @param regex 正则表达式字符串
     *
     * @param str 要匹配的字符串
     *
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     *
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     *
     * 验证是不是整数
     *
     * @param value 要验证的字符串 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Integer(String value) {
        return match(V_INTEGER, value);
    }

    /**
     *
     * 验证是不是正整数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Z_index(String value) {
        return match(V_Z_INDEX, value);
    }

    /**
     *
     * 验证是不是负整数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Negative_integer(String value) {
        return match(V_NEGATIVE_INTEGER, value);
    }

    /**
     *
     * 验证是不是数字
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Number(String value) {
        return match(V_NUMBER, value);
    }

    /**
     *
     * 验证是不是正数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean PositiveNumber(String value) {
        return match(V_POSITIVE_NUMBER, value);
    }

    /**
     *
     * 验证是不是负数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean NegatineNumber(String value) {
        return match(V_NEGATINE_NUMBER, value);
    }

    /**
     *
     * 验证一个月的31天
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Is31Days(String value) {
        return match(V_31DAYS, value);
    }

    /**
     *
     * 验证是不是ASCII
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean ASCII(String value) {
        return match(V_ASCII, value);
    }

    /**
     *
     * 验证是不是中文
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Chinese(String value) {
        return match(V_CHINESE, value);
    }

    /**
     *
     * 验证是不是颜色
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Color(String value) {
        return match(V_COLOR, value);
    }

    /**
     *
     * 验证是不是日期
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Date(String value) {
        return match(V_DATE, value);
    }

    /**
     *
     * 验证是不是邮箱地址
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Email(String value) {
        return match(V_EMAIL, value);
    }

    /**
     *
     * 验证是不是浮点数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Float(String value) {
        return match(V_FLOAT, value);
    }

    /**
     *
     * 验证是不是正确的身份证号码
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean IDcard(String value) {
        return match(V_IDCARD, value);
    }

    /**
     *
     * 验证是不是正确的IP地址
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean IP4(String value) {
        return match(V_IP4, value);
    }

    /**
     *
     * 验证是不是字母
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Letter(String value) {
        return match(V_LETTER, value);
    }

    /**
     *
     * 验证是不是小写字母
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Letter_i(String value) {
        return match(V_LETTER_I, value);
    }

    /**
     *
     * 验证是不是大写字母
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Letter_u(String value) {
        return match(V_LETTER_U, value);
    }

    /**
     *
     * 验证是不是手机号码
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Mobile(String value) {
        return match(V_MOBILE, value);
    }

    /**
     *
     * 验证是不是负浮点数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Negative_float(String value) {
        return match(V_NEGATIVE_FLOAT, value);
    }

    /**
     *
     * 验证非空
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Notempty(String value) {
        return match(V_NOTEMPTY, value);
    }

    /**
     *
     * 验证密码的长度(6~18位)
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Number_length(String value) {
        return match(V_PASSWORD_LENGTH, value);
    }

    /**
     *
     * 验证密码(数字和英文同时存在)
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Password_reg(String value) {
        return match(V_PASSWORD_REG, value);
    }

    /**
     *
     * 验证图片
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Picture(String value) {
        return match(V_PICTURE, value);
    }

    /**
     *
     * 验证正浮点数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Posttive_float(String value) {
        return match(V_POSTTIVE_FLOAT, value);
    }

    /**
     *
     * 验证QQ号码
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean QQnumber(String value) {
        return match(V_QQ_NUMBER, value);
    }

    /**
     *
     * 验证压缩文件
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Rar(String value) {
        return match(V_RAR, value);
    }

    /**
     *
     * 验证电话
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Tel(String value) {
        return match(V_TEL, value);
    }

    /**
     *
     * 验证两位小数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Two_point(String value) {
        return match(V_TWO＿POINT, value);
    }

    /**
     *
     * 验证非正浮点数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Un_negative_float(String value) {
        return match(V_UN_NEGATIVE_FLOAT, value);
    }

    /**
     *
     * 验证非负浮点数
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Unpositive_float(String value) {
        return match(V_UNPOSITIVE_FLOAT, value);
    }

    /**
     *
     * 验证URL
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Url(String value) {
        return match(V_URL, value);
    }

    /**
     *
     * 验证用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean UserName(String value) {
        return match(V_USERNAME, value);
    }

    /**
     *
     * 验证用户注册。匹配由中文、数字、26个英文字母或者下划线组成的字符串
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Account(String value) {
        return match(V_ACCOUNT, value);
    }

    /**
     *
     * 验证邮编
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Zipcode(String value) {
        return match(V_ZIPCODE, value);
    }

    /**
     *
     * 验证CSS tag 与 class id 的定义
     *
     * @param value 要验证的字符串
     *
     * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
     *
     */
    public static boolean Css(String value) {
        return match(V_CSS, value);
    }

    

    public static void main(String ars[]) {
        //   System.out.print(String.valueOf(3434).toString());
        System.out.println(UtilValidate.isDigit("-10.01.01"));
        System.out.println(UtilValidate.isDigit("-23.01"));
        System.out.println(UtilValidate.isDigit("23.01"));
        System.out.println(UtilValidate.isDigit("23.01"));
        System.out.println("    123 ".toString());
        System.out.println(UtilValidate.isNumber("-2.5"));
    }
}
