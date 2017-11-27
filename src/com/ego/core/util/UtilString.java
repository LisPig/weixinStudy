/*----------javabean工具类---------
 * @功能说明：操作字符串的便捷工具
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.util;

import com.ego.core.lang.Appender;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作字符串的便捷工具
 *
 * @author Administrator
 */
public class UtilString {

    /**
     * 后继标识
     */
    public static final int AFTER = 2;
    /**
     * 空白字符
     */
    public static final String whitespace = " \t\n\r";

    /**
     * 从字符串s中移除所有出现在字符串bag中的字符。删除字符串中指定的字符
     * 只要在delStrs参数中出现的字符，并且在inputString中也出现都会将其它删除
     *
     * <p>
     *
     * Removes all characters which appear in string bag from string s.
     *
     * @param s
     * @param bag
     * <p>
     * @return
     */
    public static String stripCharsInBag(String s, String bag) {
        int i;
        String returnString = "";

        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (bag.indexOf(c) == -1) {
                returnString += c;
            }
        }
        return returnString;
    }

    /**
     * 从字符串s中移除所有没有出现在字符串bag中的字符
     *
     * <p>
     * Removes all characters which do NOT appear in string bag from string s.
     *
     * @param s
     * @param bag
     * <p>
     * @return
     */
    public static String stripCharsNotInBag(String s, String bag) {
        int i;
        String returnString = "";

        // Search through string's characters one by one.
        // If character is in bag, append to returnString.
        for (i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (bag.indexOf(c) != -1) {
                returnString += c;
            }
        }
        return returnString;
    }

    /**
     * 从字符串s中移除所有空白字符 ，空白字符有：制表符、回车符、换行符
     *
     * {@link com.ego.util.UtilString#whitespace}
     *
     * <p>
     *
     * Removes all whitespace characters from s. Member whitespace(see above)
     * defines which characters are considered whitespace.
     *
     * @param s
     * <p>
     * @return
     */
    public static String stripWhitespace(String s) {
        return stripCharsInBag(s, whitespace);
    }

    /**
     *
     */
    /**
     * 从字符串s中移除首个空白字符 ，空白字符有：制表符、回车符、换行符
     * {@link com.ego.util.UtilString#whitespace}
     *
     * <p>
     *
     * Removes initial(leading) whitespace characters from s. Member
     * whitespace(see above) defines which characters are considered whitespace.
     *
     * @param s
     * <p>
     * @return
     */
    public static String stripInitialWhitespace(String s) {
        int i = 0;

        while ((i < s.length()) && UtilValidate.isIncluded(s.charAt(i), whitespace)) {
            i++;
        }
        return s.substring(i);
        // return s.substring(i, s.length);
    }

    /**
     *
     * @param sb
     * @param iterable
     * @param prefix
     * @param suffix
     * @param sep
     * <p>
     * @return
     */
    public static StringBuilder appendTo(StringBuilder sb,
            Iterable<? extends Appender<StringBuilder>> iterable,
            String prefix,
            String suffix,
            String sep) {
        return appendTo(sb, prefix, iterable, suffix, null, sep, null);
    }

    public static StringBuilder appendTo(StringBuilder sb,
            String prefix,
            Iterable<? extends Appender<StringBuilder>> iterable,
            String suffix,
            String prefixSep,
            String sep,
            String sepSuffix) {
        Iterator<? extends Appender<StringBuilder>> it = iterable.iterator();
        while (it.hasNext()) {
            if (prefix != null) {
                sb.append(prefix);
            }
            it.next().appendTo(sb);
            if (suffix != null) {
                sb.append(suffix);
            }
            if (it.hasNext() && sep != null) {
                if (prefixSep != null) {
                    sb.append(prefixSep);
                }
                sb.append(sep);
                if (sepSuffix != null) {
                    sb.append(sepSuffix);
                }
            }
        }
        return sb;
    }

    /**
     * 把迭代器中的内容（多行）追加到字符串中。
     *
     * @param sb 追加到的字符串序列
     * @param prefix 每一行的前缀
     * @param iterable 数据迭代器，每迭代一次就是一行
     * @param suffix 每一行的后缀
     * @param sep 行与行之间的分隔符
     * <p>
     * @return
     */
    public static StringBuilder append(StringBuilder sb,
            String prefix,
            Iterable<? extends Object> iterable,
            String suffix,
            String sep) {
        return append(sb, prefix, iterable, suffix, null, sep, null);
    }

    /**
     * 把迭代器中的内容（多行）追加到字符串中。
     *
     * @param sb 追加到的字符串序列
     * @param prefix 每一行的前缀
     * @param iterable 数据迭代器，每迭代一次就是一行
     * @param suffix 每一行的后缀
     * @param prefixSep
     * 行与行之间的分隔符的前缀分隔符，此前缀指：行与行之间分割符的前缀，依赖于行与行之间的分隔符是否存在，如前者存在，则此存在
     * @param sep 行与行之间的分隔符
     * @param suffixSep 与prefixSep类似，为分隔符的后缀分隔符
     * <p>
     * @return
     */
    public static StringBuilder append(StringBuilder sb,
            String prefix,
            Iterable<? extends Object> iterable,
            String suffix,
            String prefixSep,
            String sep,
            String suffixSep) {
        Iterator<? extends Object> it = iterable.iterator();
        while (it.hasNext()) {
            if (prefix != null) {
                sb.append(prefix);
            }
            sb.append(it.next());
            if (suffix != null) {
                sb.append(suffix);
            }
            if (it.hasNext() && sep != null) {
                if (prefixSep != null) {
                    sb.append(prefixSep);
                }
                sb.append(sep);
                if (suffixSep != null) {
                    sb.append(suffixSep);
                }
            }
        }
        return sb;
    }

    /**
     * 把迭数组中的内容（多行）追加到字符串中。
     *
     * @param sb 追加到的字符串序列
     * @param prefix 每一行的前缀
     * @param iterable 数据集，多行
     * @param suffix 每一行的后缀
     * @param sep 行与行之间的分隔符
     * <p>
     * @return
     */
    public static StringBuilder append(StringBuilder sb,
            String prefix,
            Object[] rows,
            String suffix,
            String sep) {
        return append(sb, prefix, rows, suffix, null, sep, null);
    }

    /**
     * 把迭数组中的内容（多行）追加到字符串中。
     *
     * @param sb 加到的字符串序列
     * @param prefix 每一行的前缀
     * @param rows 数据集，多行
     * @param suffix 每一行的后缀
     * @param prefixSep
     * 行与行之间的分隔符的前缀分隔符，此前缀指：行与行之间分割符的前缀，依赖于行与行之间的分隔符是否存在，如前者存在，则此存在
     * @param sep 行与行之间的分隔符
     * @param suffixSep
     * <p>
     * @return与prefixSep类似，为分隔符的后缀分隔符
     */
    public static StringBuilder append(StringBuilder sb,
            String prefix,
            Object[] rows,
            String suffix,
            String prefixSep,
            String sep,
            String suffixSep) {
        for (int i = 0, len = rows.length; i < len; i++) {
            if (prefix != null) {
                sb.append(prefix);
            }
            sb.append(rows[i]);
            if (suffix != null) {
                sb.append(suffix);
            }
            if (i != len - 1 && sep != null) {
                if (prefixSep != null) {
                    sb.append(prefixSep);
                }
                sb.append(sep);
                if (suffixSep != null) {
                    sb.append(suffixSep);
                }
            }
        }

        return sb;
    }

    /**
     * 追加字符序列，比如append(sb,"重量:","1234","千克!"）返回“重量:1234千克！”
     *
     * @param sb 追加到的字符序列
     * @param prefix 前缀
     *
     * @param str 待追加的字符
     *
     * @param suffix 后缀
     *
     * @return
     */
    public static StringBuilder append(StringBuilder sb,
            String prefix,
            CharSequence str,
            String suffix) {

        if (prefix != null) {
            sb.append(prefix);
        }
        sb.append(str);

        if (suffix != null) {
            sb.append(suffix);
        }

        return sb;
    }

    /**
     * 将gbk字符转换为utf-8字符
     *
     * @param str
     * <p>
     * @return 转换失败返回null
     */
    public static String gbK2Unicode(String str) {
        try {
            if (str == null) {
                return null;
            } else {
                str = new String(str.getBytes("GBK"), "utf-8");
                return str;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将gbk字符转换为utf8字符
     *
     * @param chenese
     * <p>
     * @return
     */
    public static byte[] gbk2Utf8(String chenese) {
        char c[] = chenese.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++) {
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);
            // System.out.println(word);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            // 补零
            for (int j = 0; j < len; j++) {
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");

            // System.out.println(sb.toString());
            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i * 3] = bf[0];
            bf[1] = b1;
            fullByte[i * 3 + 1] = bf[1];
            bf[2] = b2;
            fullByte[i * 3 + 2] = bf[2];

        }
        return fullByte;
    }

    /**
     * unicode转换为gbk
     *
     * @param strvalue
     * <p>
     * @return
     */
    public static String unicode2GBK(String strvalue) {
        try {
            if (strvalue == null) {
                return null;
            } else {
                strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
                return strvalue;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串过滤
     *
     * @param source 待过滤的字符串
     * @param regex 匹配的表达式
     * @param rpstr 将匹配的表达式替换为这个字符串
     * <p>
     * @return
     */
    public static String doFilter(String source, String regex, String rpstr) {
        Pattern p = Pattern.compile(regex, 2 | Pattern.DOTALL);
        Matcher m = p.matcher(source);
        return m.replaceAll(rpstr);
    }

    /**
     * 获得数字的随机字符串
     *
     * @param length 如果小于等于0则默认生成4位字符串。
     * <p>
     * @return
     */
    public static String getRandom(int length) {
        length = length <= 0 ? 4 : length;
        int[] array
                = {
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9
                };
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < length; i++) {
            result = result * 10 + array[i];
        }

        return "" + result;
    }

    /**
     * 获得数字和字母（大小写）随机字符串
     *
     * @param randStr
     * 如果传递null，则根据{@link com.ego.util.Constant#digitsAndLetters}生成随机字符串
     * @param randStrLength 如果小于等于0则默认生成4位字符串。
     * <p>
     * @return
     */
    public static String getRandom(String randStr, int randStrLength) {
        randStr = UtilValidate.isEmpty(randStr) ? Constant.digitsAndLetters : randStr; // 写入你所希望的所有的字母A-Z,a-z,0-9
        randStrLength = randStrLength <= 0 ? 4 : randStrLength;
        StringBuilder generateRandStr = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < randStrLength; i++) {
            int randNum = rand.nextInt(randStr.length());
            generateRandStr.append(randStr.substring(randNum, randNum + 1));
        }
        return generateRandStr.toString();
    }

    /**
     * 生成随机数字字符串--数字字符串没有重复值 注意：range不得小于length，否则会造成死循环
     *
     * @param range 生成的数字的范围
     * @param length 生成的数字字符串的长度
     * <p>
     * @deprecated 容易造成死循环
     * @return
     */
    public static int[] getRandom(int range, int length) {
        int[] randStr = new int[length];
        Random rand = new Random();
        for (int i = 0; i < randStr.length; i++) {
            int randNum = rand.nextInt(range);
            if (i == 0) {
                randStr[i] = randNum;
            } else {
                boolean b = true;
                for (int j = 0; j < randStr.length; j++) {
                    if (randStr[j] == randNum) {
                        i--;
                        b = false;
                        break;
                    }
                }
                if (b) {
                    randStr[i] = randNum;
                }
            }
        }
        return randStr;
    }

    /**
     * 去除字符串两边的空格，如果传入的为null，返回“”；
     *
     * @param value
     * <p>
     * @return
     */
    public static String trim(String value) {
        if (value == null) {
            return "";
        } else {
            return value.trim();
        }

    }

    /**
     * 去除左边多余的空格。
     *
     * @param value 待去左边空格的字符串
     * <p>
     * @return 去掉左边空格后的字符串
     */
    public static String trimLeft(String value) {
        String result = value;
        if (result == null) {
            return result;
        }
        char ch[] = result.toCharArray();
        int index = -1;
        for (int i = 0; i < ch.length; i++) {
            if (Character.isWhitespace(ch[i])) {
                index = i;
            } else {
                break;
            }
        }
        if (index != -1) {
            result = result.substring(index + 1);
        }
        return result;
    }

    /**
     * 去除右边多余的空格。
     *
     * @param value 待去右边空格的字符串
     * <p>
     * @return 去掉右边空格后的字符串
     */
    public static String trimRight(String value) {
        String result = value;
        if (result == null) {
            return result;
        }
        char ch[] = result.toCharArray();
        int endIndex = -1;
        for (int i = ch.length - 1; i > -1; i--) {
            if (Character.isWhitespace(ch[i])) {
                endIndex = i;
            } else {
                break;
            }
        }
        if (endIndex != -1) {
            result = result.substring(0, endIndex);
        }
        return result;
    }

    /**
     * 判断一个子字符串在父串中重复出现的次数
     *
     * @param parentStr 父串
     * @param subStr 子串
     * <p>
     * @return 子字符串在父字符串中重得出现的次数
     */
    public static int substrRepeatCounts(String parentStr, String subStr) {
        int count = 0;

        for (int i = 0; i < (parentStr.length() - subStr.length()); i++) {
            String tempString = parentStr.substring(i, i + subStr.length());
            if (subStr.equals(tempString)) {
                count++;
            }
        }

        return count;
    }

    /**
     * 得到在字符串中从开始字符串到结止字符串中间的子串
     *
     * @param parentStr 父串
     * @param startStr 开始串
     * @param endStr 结止串
     * <p>
     * @return 返回开始串与结止串之间的子串
     */
    public static String subString(String parentStr, String startStr, String endStr) {
        return parentStr.substring(parentStr.indexOf(startStr) + startStr.length(), parentStr.indexOf(endStr));
    }

    public static String subString(String parentStr, String lastIndexOfStartStr) {
        int i = parentStr.lastIndexOf(lastIndexOfStartStr);
        i = i == -1 ? 0 : i+1;
        return parentStr.substring(i);
    }

    /**
     * 首字母大写
     *
     * @param str
     * <p>
     * @return 如果str为空包括null和空字符串，则返回"";
     */
    public static String upperFrist(String str) {
        if (UtilValidate.isEmpty(str)) {
            return "";
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param string
     * <p>
     * @return
     */
    public static String lowerFrist(String string) {
        if (string == null) {
            return null;
        }
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    /**
     * 分隔字符串到一个集合
     *
     * @param str 待分割的字符串
     * @param start 起始字符串
     * @param end 结束字符串
     * <p>
     * @return
     */
    public static List<String> dividToList(String str, String start, String end) {
        if (str == null || str.length() == 0) {
            return null;
        }

        int s = 0;
        int e = 0;
        List<String> result = new ArrayList<String>();
        if (UtilValidate.isIncluded(str, start) && UtilValidate.isIncluded(str, end)) {
            while ((e = str.indexOf(start, s)) >= 0) {
                result.add(str.substring(s, e));
                s = str.indexOf(end, e) + end.length();
                result.add(str.substring(e, s));
            }
            if (s < str.length()) {
                result.add(str.substring(s));
            }

            if (s == 0) {
                result.add(str);
            }
        } else {
            result.add(str);
        }

        return result;
    }

    /**
     * 如果inStr字符串与没有给定length的长度，则用fill填充，在指定direction的方向
     * 如果inStr字符串长度大于length就直截返回inStr，不做处理
     *
     * @param inStr 待处理的字符串
     * @param fill 以该字符串作为填充字符串
     * @param length 填充后要求的长度
     * @param direction 填充方向，如果是AFTER填充在后面，否则填充在前面
     * <p>
     * @return 返回一个指定长度填充后的字符串
     */
    public static String fillStr(String inStr, String fill, int length,
            int direction) {
        if (inStr == null || inStr.length() > length || inStr.length() == 0) {
            return inStr;
        }

        StringBuilder tempStr = new StringBuilder("");
        for (int i = 0; i < length - inStr.length(); i++) {
            tempStr.append(fill);
        }

        if (direction == AFTER) {
            return tempStr.toString() + inStr;
        } else {
            return inStr + tempStr.toString();
        }
    }

    /**
     * 将指定的String转换为Unicode的等价值
     *
     * 基础知识： 所有的转义字符都是由 '\' 打头的第二个字符 0-9 :八进制 u :是Unicode转意，长度固定为6位
     * Other:则为以下字母中的一个 b,t,n,f,r,",\ 都不满足，则产生一个编译错误 提供八进制也是为了和C语言兼容. b,t,n,f,r
     * 则是为控制字符.书上的意思为:描述数据流的发送者希望那些信息如何被格式化或者被表示. 它可以写在代码的任意位置，只要转义后是合法的. 例如:
     * int c=0\u003b 上面的代码可以编译通过，等同于int c=0; \u003b也就是';'的Unicode代码
     *
     * @param str 待转换为Unicode的等价字符串
     * <p>
     * @return Unicode的字符串
     */
    public static String getUnicodeStr(String inStr) {
        char[] myBuffer = inStr.toCharArray();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inStr.length(); i++) {
            byte b = (byte) myBuffer[i];
            short s = (short) myBuffer[i];
            String hexB = Integer.toHexString(b);
            String hexS = Integer.toHexString(s);
            /*
             * //输出为大写 String hexB = Integer.toHexString(b).toUpperCase();
             * String hexS = Integer.toHexString(s).toUpperCase(); //print char
             * sb.append("char["); sb.append(i); sb.append("]='");
             * sb.append(myBuffer[i]); sb.append("'\t");
             * 
             * //byte value sb.append("byte="); sb.append(b); sb.append(" \\u");
             * sb.append(hexB); sb.append('\t');
             */

            // short value
            sb.append(" \\u");
            sb.append(fillStr(hexS, "0", 4, AFTER));
            // sb.append('\t');
            // Unicode Block
            // sb.append(Character.UnicodeBlock.of(myBuffer[i]));
        }
        return sb.toString();
    }

    public static String format(String str, Object... args) {

        return "";
    }

    /**
     * 字符-全角转半角
     *
     * @param QJstr
     * <p>
     * @return
     * <p>
     * @throws UnsupportedEncodingException
     */
    public static final String full2HalfChange(String QJstr)
            throws UnsupportedEncodingException {
        StringBuilder outStrBuf = new StringBuilder("");
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < QJstr.length(); i++) {
            Tstr = QJstr.substring(i, i + 1);
            // 全角空格转换成半角空格
            if (Tstr.equals("　")) {
                outStrBuf.append(" ");
                continue;
            }
            b = Tstr.getBytes("unicode");
            // 得到 unicode 字节数据
            if (b[2] == -1) {
                b[3] = (byte) (b[3] + 32);
                b[2] = 0;
                outStrBuf.append(new String(b, "unicode"));
            } else {
                outStrBuf.append(Tstr);
            }
        }
        return outStrBuf.toString();
    }

    /**
     * 将字符串转化为小驼峰模式，主要是将下划线删除并将下划线后面的第一个字符大写.如“lower_camel_case”，得到结果“lowerCamelCase”。注意返回的字符串第一个字符一定是小写的
     *
     * @param str
     * <p>
     * @return
     */
    public static String getLowerCamelCase(String str) {
        String[] ss = str.split("_");
        if (ss.length == 1) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0, len = ss.length; i < len; i++) {
                if (i != 0) {
                    sb.append(upperFrist(ss[i]));
                } else {
                    sb.append(lowerFrist(ss[i]));
                }
            }
            return sb.toString();
        }
    }

    /**
     * 将字符串转化为大驼峰模式，主要是将下划线删除并将下划线后面的第一个字符大写.如“lower_camel_case”，得到结果“LowerCamelCase”。注意返回的字符串第一个字符一定是大写的
     *
     * @param str
     * <p>
     * @return
     */
    public static String getUpperCamelCase(String str) {
        return upperFrist(getLowerCamelCase(str));
    }

    /**
     * 获取删除最后一个指定字符串后的值
     *
     * @param value
     * <p>
     * @return
     */
    public static String deleteLast(String s, String value) {
        if (null == s || "".equals(s)) {
            return null;
        }
        if (s.endsWith(value)) {
            s = s.substring(0, value.length() - 1);
        }
        return s;
    }

    /**
     * 将字符串按照指定的分隔符分割成字符串数组，如果字符串为空，“”、null，则返回空数组
     *
     * @param str
     * @param split
     * <p>
     * @return
     */
    public static String[] split(String str, String split) {
        if (UtilValidate.isNotEmpty(str)) {
            return str.split(split);
        } else {
            return new String[0];
        }

    }

    /**
     * 返回子字符串在原始字符串中的位置，第一个位置返回为0
     *
     * @param str
     * @param subStr
     * <p>
     * @return
     */
    public static int indexOf(String str, String subStr) {
        return UtilString.trim(str).indexOf(UtilString.trim(subStr));
    }

    /**
     * 获取指定字符串最大长度的子字符串
     *
     * @param s
     * @param maxLength
     * <p>
     * @return
     */
    public static String substring(String s, int maxLength) {
        if (UtilValidate.isEmpty(s)) {
            return "";
        }
        if (s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength);
    }

    /**
     * 将字符串转换为in流，默认utf-8编码
     *
     * @param str
     * @param charset 为空则为utf8
     * <p>
     * @return
     * <p>
     * @throws IOException
     */
    public static ByteArrayInputStream string2InputStream(String str, String charset) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes(UtilValidate.isEmpty(charset) ? "utf-8" : charset));
        return in;
    }

    /**
     * 将字符串转换为out流，默认utf-8编码
     *
     * @param str
     * @param charset 为空则为utf8
     * <p>
     * @return
     * <p>
     * @throws IOException
     */
    public static ByteArrayOutputStream string2OutStream(String str, String charset) throws IOException {
        charset = UtilValidate.isEmpty(charset) ? "utf-8" : charset;
        ByteArrayOutputStream ou = new ByteArrayOutputStream();
        byte[] b = str.getBytes(charset);
        ou.write(b, 0, b.length);
        return ou;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        //LoggerManager.info(getRandom(" ", 0));
        // LoggerManager.debug(getRandom(6));
        /*
         Map v = new HashMap<Object, Object>();
         v.put("3", "");

         v.put("2", "");
         v.put("1", "");
         Object[] values = new Object[]{"a=?", "b=?", "c=?"};

       
         StringBuilder s = new StringBuilder();
         Iterable<? extends Object> cols;
         */
        //java.util.logging.Logger log = java.util.logging.Logger.getLogger("e");
        // log.log(Level.FINE, MessageFormat.format("ddd", args));
        //LoggerManager.debug(UtilString.append(s, "开始：", values, "结束;", "-").toString().toString());
        //LoggerManager.debug(Wedlock.MARRIED.getValue());
        //sq = "UPDATE " + table + " SET " + StringUtil.implode(", ", cols) + " WHERE " + where;
        //  String sql = "UPDATE " + "tb" + " SET " + UtilString.append(new StringBuilder(), null, values, null, ",").toString() + " WHERE " + "a=1";
        // UtilString.append(s, null, "UPDATE", " ");
        //UtilString.append(s, null, "tb", " ");
        //  LoggerManager.debug(s.toString());
        //LoggerManager.debug(sql);
        //LoggerManager.debug(upperFrist("abcdefghijklmn"));
        //System.out.print(upperFrist("abcff"));

        //  System.out.print(string2InputStream("a55555555555555555555555555555555555555555555555", null).available());
        System.out.print(String.format("%05d", 100));
    }
}
