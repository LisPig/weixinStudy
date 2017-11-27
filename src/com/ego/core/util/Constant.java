/*----------接口---------
 * @功能说明：一些常量
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.util;

/**
 *
 * @author Administrator
 */
public interface Constant {

    /**
     * 数字
     */
    String digits = "0123456789";
    /**
     * 小写字母
     */
    String lowercases = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 大写字母
     */
    String uppercases = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 所有字母，包括大小写
     */
    String letters = lowercases + uppercases;
    /**
     * 数字和字母（大小写）
     */
    String digitsAndLetters = lowercases + uppercases + digits;
}
