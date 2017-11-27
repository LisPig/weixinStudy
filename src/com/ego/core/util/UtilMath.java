/*----------javabean---------
 * @功能说明：主要设置数学常用方法
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算， 这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。
 *
 * @author Administrator
 */
public class UtilMath {

    // private static char[] operator = { '+', '-', '*', '/', '%', '^' };
    // 内部计算操作符,即计算后缀表达式时支持的操作符，分别对应加、减、乘、除、余、次方（根号可写为除法形式，eg:4的2次根=4^(1/2)）、小括号、
    // sin、cos、tan、正、负、log、阶乘
    // 注意，log包括lg10、ln5等形式，将log当做双目运算符,lg20写为10log20
    // 全部运算符
    private static String operator = "+,-,*,×,/,÷,%,^,(,),s,c,t,p,n,l,!";
    // 参与运算的符号，除）外,这个是会入栈的运算符，也即内部运算符
    private static String cOperator = "+,-,*,/,%,^,(,s,c,t,p,n,l,!";
    // 算数运算符
    private static String mOperator = "+,-,*,×,/,÷,%,^,s,c,t,p,n,l,!";
    // 支持的单目运算符,分别为运算符的英文单词首字母，即“sin、cos、tan、正号、负号、阶乘”
    private static String unaryOperator = "s,c,t,p,n,!";
    // 双目运算符
    private static String binaryOperator = "+,-,*,×,/,÷,%,^,l";
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static Double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v 需要四舍五入的数字
     * @param precision 小数点后保留几位，精度
     * @return 四舍五入后的结果
     */
    public static double round(double v, int precision) {
        if (precision < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, precision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 返回以a为底b的对数
     *
     * @param a 底
     * @param b
     * @return
     */
    public static double log(double a, double b) {
        if (a == 1) {
            throw new ArithmeticException("底数不能为1");
        }
        return java.lang.Math.log(a) / java.lang.Math.log(b);
    }

    /**
     * 计算n!阶乘
     *
     * @param a
     * @return
     */
    public static long factorial(int a) {
        if (a == 0) {
            throw new ArithmeticException("can't be 0(n不能为0)");
        }
        long result = 1;
        for (int i = 1; i <= a; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * 效率较高的求幂运算
     */
    public long pow(long x, int n) {

        if (n == 0) {
            return 1;
        } else {

            //判断n的奇偶   
            if (n % 2 == 0) {
                return pow(x * x, n / 2);
            } else {
                return pow(x * x, (n - 1) / 2) * x;
            }

        }
    }

    /**
     * 效率较高的求幂运算
     */
    public BigInteger pow(BigInteger x, int n) {
        if (n == 0) {
            return BigInteger.valueOf(1);
        } else {
            if (n % 2 == 0) {
                return pow(x.multiply(x), n / 2);
            } else {
                return pow(x.multiply(x), (n - 1) / 2).multiply(x);
            }

        }

    }

    /**
     * 求最大公约数
     */
    public static int gcd(int m, int n) {
        while (true) {
            if ((m = m % n) == 0) {
                return n;
            }
            if ((n = n % m) == 0) {
                return m;
            }
        }
    }

    /**
     * 求最小公倍数
     */
    public static int gys(int z, int y) {
        int t = 0;
        int c = 0;
        c = gcd(z, y);
        t = z * y / c;

        return t;
    }

    /**
     * 求几个数的最小公倍数
     */
    public static int getN(ArrayList list) {

        int t = 1;

        for (int i = 0; i < list.size(); i++) {
            Integer temp = (Integer) list.get(i);
            t = gys(t, temp.intValue());
        }
        return t;
    }

    /**
     * 此检查仅仅初步检查表达式是否合格，更底层的检验应捕获中缀表达式转后缀表达式，以及计算后缀表达式时抛出的错误。计算后缀表达式
     * 时抛出了“运算符与操作数个数不匹配”的异常，中缀转换为后缀时抛出了“不支持运算符”、“表达式不能为空”、“操作数不能以0开头”异常。
     * ”数学符号转为自定义符号“方法中抛出”sincos、tansin等格式“异常
     * 检验的操作符：“+、-、*（×）、/（÷）、%、（[{、)]}、.、π、e、s、c、t、p、n、l“以及普通操作数
     *
     * 检验规则<br> ： 检查中缀表达式是否正确.检查：
     *
     * (1)（、[、{括号是否成对出现。
     *
     * (2)+、-、*、×、/÷、%、^、l、!符号前面不能有（、[、{括号。但正负号、sin、cos可以，即双目运算符(但包括阶乘单目运算符）
     * 前面不能有（、[、{括号
     *
     * (3)运算符不能连续出现。但sin、cos、tan可以，如3*sin12，即除了sin等系列外运算符不能连续出现,但又例外不能sinsin
     * 、sincos 这个例外会在”数学符号转为自定义符号“方法中抛出。确切的说是算数运算符不能连续出现，比如-（（可以连续 .
     *
     * 但阶乘后可以跟 +、-、*、/、% 。但正负号前可以有算数运算符
     *
     * （4）操作数不能单独 出现,具体表现为：a操作数前不能是“）]}”，b操作数后不能是“([{”。
     *
     * （5）+、-、*、/、%、^……不能出现在表达式开头。但除正负号、sin、cos外：p、n。即，双目运算符（包括!）b不能在开头
     *
     * （6）操作符+、-、*、/、%、^p、n、s、c、t、l 后只可以跟操作数和（[{符号^,即 操作符后不能有非操作数和）]}符号
     * ,但阶乘例外。阶乘后面可以跟）]}但不可以直接跟操作数
     *
     * （7）小数点只能处于数字之间
     *
     * (8)π当做普通操作数，因而有普通操作数的检验规则，但除此外π后面还不能跟操作数（包括“.”），另外π不能连续出现，即ππ
     *
     * (9)一个特殊情况，因为本计算基于十进制，所以操作数不能以0开头（除0后紧跟.操作符外）这个会在中缀转换后缀时检验
     *
     * （10）sin、cos、tan检验：不能出现sincos、tancos、sin（）cos（）、cos（
     * ）、sin(55)23等格式，在”数学符号转为自定义符号“方法中检验。。。换句话说sin、cos等后只能有数字或（[{
     *
     * (11)因为log在中缀表达式写为双目运算符，所以具有双目运算规则
     *
     * (12)操作符不能出现在表达式最后，严格说是参与运算符(但阶乘!可以出现在最后），即入了操作符栈的符号，如）]}这样的括号在最后
     *
     * 按操作符归类：
     *
     *
     * 阶乘后被不能跟数字。阶乘后可以跟的操作符“+、-、*（×）、/（÷）、%“
     *
     * @param infixExp
     * @return 返回一个长度为2的数组，第一位为“true”或“false”，第二位是状态描述
     */
    public static String[] checkInfixExp(String infixExp) throws Exception {
        // 将一些数学符号替换为自定义符号
        infixExp = mathOperator2CustomOperator(infixExp);
        Deque<Character> stack = new ArrayDeque<Character>();
        Character cChar = null, openChar, preChar, nChar;// 定义当前字符、开括号字符、上一个字符、下一个字符变量
        boolean flag = true;
        String result[] = new String[2];
        for (int i = 0, len = infixExp.length(); i < len; i++) {
            preChar = cChar;
            cChar = infixExp.charAt(i);
            nChar = (i + 1) <= (len - 1) ? infixExp.charAt(i + 1) : null;
            switch (cChar) {
                case '+':
                case '-':
                case '*':
                case '×':
                case '/':
                case '÷':
                case '%':
                case '^':
                case 's':
                case 'c':
                case 't':
                case 'p':
                case 'n':
                case 'l':
                case '!':

                    // 表达式开头不可以有+-运算符，但可以有正号、负号、sin、cos，分别为“p、n、s、c、t”
                    if (preChar == null
                            && (cChar == '!' || binaryOperator.contains(cChar
                                    .toString()))) {// +-*/×÷%^l运算符(即双目，阶乘）不能出现在表达式开头.规则5
                        result[0] = "false";
                        result[1] = cChar + "不能出现在表达式开头（除正/负/sin/cos...号).位置:"
                                + (i + 1);
                        flag = false;
                    } else if (preChar != null
                            && (mOperator.replace("!", "").contains(
                                    preChar.toString())
                            && "sctpn".indexOf(cChar) == -1 || preChar == '!'
                            && "+-*×/÷%".indexOf(cChar) == -1)) {// +-*/×÷%pn^算数运算符不能连续出现，规则3
                        result[0] = "false";
                        result[1] = cChar + "与" + preChar + "不能连续出现.位置:" + (i + 1);
                        flag = false;

                    } else if (preChar != null
                            && (cChar == '!' || binaryOperator.contains(cChar
                                    .toString()))
                            && "([{^".contains(preChar.toString())) {// +-*/%^不能出现在（[{的后面
                        // ,规则2
                        result[0] = "false";
                        result[1] = cChar + "不能出现在\"(,[,{后\".位置:" + (i + 1);
                        flag = false;
                    } else if (i == len - 1 // 操作符不能出现在表达式最后，严格说是参与运算符（不包括!），即入了操作符栈的符号，规则12
                            && cOperator.contains(cChar.toString()) && cChar != '!') {
                        result[0] = "false";
                        result[1] = "操作符" + cChar + "不能出现在表达式最后.位置:" + (i + 1);
                        flag = false;
                    } else if (nChar != null && ")]}".contains(nChar.toString())
                            && cChar != '!') {// 此处可以和“运算符不能连续出现”、“小数点只能在操作数之间”检验法则
                        // 合并条件为:操作符后不能紧跟非操作数，即只能跟操作数和（[{运算符.规则6
                        result[0] = "false";
                        result[1] = "操作符" + cChar + "后不能紧跟)]}符号.位置:" + (i + 1);
                        flag = false;
                    } else if (nChar != null && cChar == '!'
                            && Character.isDigit(nChar)) {// 阶乘后不可以直接跟数字
                        result[0] = "false";
                        result[1] = "操作符" + cChar + "后不能跟数字.位置:" + (i + 1);
                        flag = false;
                    } else if (nChar != null && cChar == '!'
                            && "([{".contains(nChar.toString())) {// 阶乘后不可以直接跟([{
                        result[0] = "false";
                        result[1] = "操作符" + cChar + "后不能跟([{.位置:" + (i + 1);
                        flag = false;
                    }
                    break;
                case '.': // 小数点只能处于数字之间
                    if (preChar == null || !Character.isDigit(preChar)
                            || nChar == null || !Character.isDigit(nChar)) {
                        result[0] = "false";
                        result[1] = "小数点只能处于数字之间.位置:" + (i + 1);
                        flag = false;
                    }
                    break;
                case '(':
                case '[':
                case '{':
                    stack.addLast(cChar);
                    break;
                case ')':
                    openChar = stack.pollLast();
                    if (openChar == null) {
                        result[0] = "false";
                        result[1] = "意外(或多于）的\")\"标记。第" + i + "个字符.位置:" + (i + 1);
                        flag = false;
                    } else if (openChar != '(') {
                        result[0] = "false";
                        result[1] = "意外(不匹配）的\")\"标记。期待\"" + openChar
                                + "\"，写入\")\".位置:" + (i + 1);
                        ;
                        flag = false;
                    }
                    break;
                case ']':
                    openChar = stack.pollLast();
                    if (openChar == null) {
                        result[0] = "false";
                        result[1] = "意外(或多于）的\"]\"标记。第" + i + "个字符.位置:" + (i + 1);
                        flag = false;
                    } else if (openChar != '[') {
                        result[0] = "false";
                        result[1] = "意外(不匹配）的\"]\"标记。期待\"" + openChar
                                + "\"，写入\"]\".位置:" + (i + 1);
                        flag = false;
                    }
                    break;
                case '}':
                    openChar = stack.pollLast();
                    if (openChar == null) {
                        result[0] = "false";
                        result[1] = "意外(或多于）的\"]\"标记。第" + i + "个字符.位置:" + (i + 1);
                        flag = false;
                    } else if (openChar != '{') {
                        result[0] = "false";
                        result[1] = "意外(不匹配）的\"}\"标记。期待\"" + openChar
                                + "\"，写入\"}\".位置:" + (i + 1);
                        flag = false;
                    }
                    break;
                default:// 默认处理操作数的情况
                    if (preChar != null && ")]}".contains(preChar.toString())) {// 操作数前不能是）]}括号，规则4
                        result[0] = "false";
                        result[1] = "操作数" + cChar + "不能紧跟在)]}符号后.位置:" + (i + 1);
                        flag = false;
                    } else if (nChar != null && "([{".contains(nChar.toString())) {// 操作数后不能是([{括号，规则4
                        result[0] = "false";
                        result[1] = "操作数" + cChar + "后不能跟([{括号.位置:" + (i + 1);
                        flag = false;
                    } else if (cChar == 'π'
                            && (nChar != null && (Character.isDigit(nChar) || nChar == '.'))) {// π当做普通操作数，因而有普通操作数的检验规则，但除此外π后面还不能跟操作数（包括“.”）规则8
                        result[0] = "false";
                        result[1] = "操作数" + cChar + "后不能跟操作数和“.”.位置:" + (i + 1);
                        flag = false;
                    } else if ((cChar == 'π' || cChar == 'e')
                            && (nChar != null && (nChar == 'π' || nChar == 'e'))) {// π当做普通操作数，因而有普通操作数的检验规则，但除此外π后面还不能跟操作数（包括“.”）规则8
                        result[0] = "false";
                        result[1] = "操作数" + cChar + "不能连续出现，但可以2" + cChar + "3"
                                + cChar + "...格式.位置:" + (i + 1);
                        flag = false;
                    }
            }
            if (!flag) {
                return result;
            }
        }
        if ((openChar = stack.pollLast()) != null) {
            result[0] = "false";
            result[1] = "意外(或多于）的\"" + openChar + "\"标记";
            flag = false;
        }
        if (flag) {
            result[0] = "true";
            result[1] = "正确";
        }
        return result;
    }

    /**
     * 计算后缀表达式，也即逆波兰表达式 。此计算假设所有输入合法。 支持的操作符:"+,-,*,/,%,^,(,),s,c,t,p,n,l,!";
     * <br> 其中(和)已经被中缀转换后缀抵消，所有支持的操作符只有"+,-,*,/,%,^,s,c,t,p,n,l,!"; <br>
     * 操作只有数字（包括带小数点），常数比如π等应该在中缀转换后缀时替换，否则会出现未知异常.
     *
     * <br> 注意：规定所有内部的操作符都为一个字符
     * 上述支持的操作符分别对应加、减、乘、除、余、次方（根号可写为除法形式，eg:4的2次根=4^(1/2)）、小括号、
     * sin、cos、tan、正、负、log、阶乘
     *
     * <br> 注意：log是双目运算符,但优先级视为单目运算符
     *
     * @param postfixExpression 后缀表达式
     * @param precision 变长参数，可选设置，若设置返回指定精度的值
     * @return double 类型计算结果值
     * @exception Exception ("操作符与操作数个数不匹配")，此情况发生在：循环期间出现数栈为空，或后缀表达式已经输入完毕，但数栈的
     * 长度不等于1
     */
    public static double calculatePostfixExp(String[] postfixExp,
            int... precision) throws Exception {
        // String operator = "+,-,*,/,%,^,(,)";
        BigDecimal result, head = null, tail = null;
        // Iterator iterator = postfixExpression.iterator();
        Deque<BigDecimal> numStack = new ArrayDeque<BigDecimal>();// 存放操作数的栈
        // while (iterator.hasNext()) {
        for (int i = 0, length = postfixExp.length; i < length; i++) {
            // String elem = (String) iterator.next();
            String elem = (String) postfixExp[i];
            if (operator.indexOf(elem) == -1) {// 如果取出的字符串不是操作符，当做操作数入栈
                numStack.add(new BigDecimal(elem));
            } else {// 在循环期间如果出现数栈为空或size为1的情况下说明操作数与操作符个数不匹配，应抛出错误。但单目运算符只有一个操作数
                tail = numStack.pollLast();
                // tail = tail == null ? BigDecimal.ZERO : tail;
                // 如果单目运算符只取一个操作数,反之取两个
                if (!isUnaryOperator(elem.charAt(0))) {// 因为操作符是一个字符，所以取第一个
                    if ((head = numStack.pollLast()) == null) {
                        throw new Exception("操作符与操作数个数不匹配");
                    }
                }
                // 兼顾单目运算与双目运算都没有操作数
                if (tail == null) {
                    throw new Exception("操作符与操作数个数不匹配");
                }
                // head = head == null ? BigDecimal.ZERO : head;

                if (elem.equals("+")) {
                    result = tail.add(head, MathContext.DECIMAL128);
                    numStack.add(result);
                } else if (elem.equals("-")) {
                    // tail = numStack.pollLast();
                    // head = numStack.pollLast();
                    result = head.subtract(tail, MathContext.DECIMAL128);
                    numStack.add(result);
                } else if (elem.equals("*")) {
                    // result = numStack.pollLast() * numStack.pollLast();
                    result = tail.multiply(head, MathContext.DECIMAL128);
                    numStack.add(result);
                } else if (elem.equals("/")) {
                    // tail = numStack.pollLast();
                    // head = numStack.pollLast();
                    result = head.divide(tail, MathContext.DECIMAL128);
                    numStack.add(result);
                } else if (elem.equals("%")) {
                    // tail = numStack.pollLast();
                    // head = numStack.pollLast();
                    result = head.remainder(tail, MathContext.DECIMAL128);
                    numStack.add(result);
                } else if (elem.equals("^")) {
                    // tail = numStack.pollLast();
                    // head = numStack.pollLast();
                    // result = java.lang.UtilMath.pow(head, tail);
                    result = head.pow(tail.intValue(), MathContext.DECIMAL128);
                    numStack.add(result);
                } else if (elem.equals("l")) {
                    // tail = numStack.pollLast();
                    // head = numStack.pollLast();
                    // result = java.lang.UtilMath.pow(head, tail);
                    result = BigDecimal.valueOf(UtilMath.log(head.doubleValue(),
                            tail.doubleValue()));
                    numStack.add(result);
                } // 处理单目运算符
                else if (elem.equals("s")) {

                    result = BigDecimal.valueOf(java.lang.Math.sin(tail
                            .doubleValue()));
                    numStack.add(result);
                } else if (elem.equals("c")) {

                    result = BigDecimal.valueOf(java.lang.Math.cos(tail
                            .doubleValue()));
                    numStack.add(result);
                } else if (elem.equals("t")) {
                    result = BigDecimal.valueOf(java.lang.Math.tan(tail
                            .doubleValue()));
                    numStack.add(result);
                } else if (elem.equals("p")) {
                    result = tail.plus(MathContext.DECIMAL128);
                    numStack.add(result);
                } else if (elem.equals("n")) {
                    result = tail.negate(MathContext.DECIMAL128);
                    numStack.add(result);

                } else if (elem.equals("!")) {
                    result = BigDecimal.valueOf(factorial(tail.intValue()));
                    numStack.add(result);

                }
            }
        }
        if (numStack.size() != 1) {
            // throw new Exception("操作符与操作数个数不匹配");
        }
        if (precision.length != 0) {
            return numStack.pollLast().round(new MathContext(precision[0]))
                    .doubleValue();
        }
        return numStack.pollLast().doubleValue();
    }

    /**
     * 支持的运算符：:+、-、*（×）、/（÷）、%（mod取余）、^（n次方）,(、）、[、]、{、}、sin、cos、tan、log、!。
     *
     * 将中缀表达式转换为后缀表达式 表达式支持的操作符:+、-、*（×）、/（÷）、%（mod取余）、^（n次方）,(、）、[、]、{、}.
     * 支持的操作符另增加了：单目运算符：“sin、cos、tan”.单目运算符在中缀转后缀时当做普通二元运算符入栈，在计算后缀表达式时特殊处理。
     *
     * 增加双目运算符log，在中缀表达式中log的写法：4log5，即以4为底5的对数。lg与ln可以写为：10logN、elogN；
     * log必须在中缀表达式中 写正确，程序中不做严格检查，只是当做普通的双目操作符检验。 增加单目目运算符!；
     *
     *
     * l(log)运算符与单目运算符优先级相同，是因为虽然log作为双目运算符，但是其作为整体计算的 以上的操作符都会转为自定义的单个字符
     *
     * 其中[和{是转为（参与运算的。 .<br> 支持的常数(变量）：
     *
     * 1、π（即3.14),可以将数字和π连写，即"3π"、”2π”。视为3*π、2*π;
     *
     * 2、e(即2.71828....），可以将数字和e连写，即"3e"、”2e”。视为3*e、2*e;
     *
     * 注意，中缀转换后缀时可以先将“[]{}”替换为"()"，再进行比较，但没这么做，而是在比较操作符时再转换。。。
     *
     * sin、cos、tan运算符替换为各自的首字母，方便将操作数和运算符分割开
     *
     * 支持的写法：手写常规写法，即数学表示法。
     *
     * 具体表现为：
     *
     * 1、大中小括号运算
     *
     * 2、π视作变量，可以写为2π、3.5π等形式,不能ππ
     *
     * 3、2sin45cos30是合法的，视为2*sin45*cos30，常规写法是可行的，如sin（cos0）、sin（0）cos（0）
     *
     * @param infixExp
     * @return
     * @throws Exception
     * 不支持操作符、Exception("操作数不能以0开头（除0.1形式）")、Exception("表达式不能为空" );
     */
    public static String[] infixExp2PostfixExp(String infixExp)
            throws Exception {
        if (infixExp == null || "".equals(infixExp)) {
            throw new Exception("表达式不能为空");
        }

        infixExp = mathOperator2CustomOperator(infixExp);

        // 将表现形式的乘、除、余替换为计算机表示
        infixExp = infixExp.replaceAll("×", "*").replaceAll("÷", "/")
                .replaceAll("mod", "%");

        StringBuilder numBuffer = new StringBuilder();// 用来保存一个整体的数，包含小数点
        List<String> postfixExpBuffer = new ArrayList<String>();// 存放转化的后缀表达式的链表
        // StringBuilder postfixExpBuffer = new StringBuilder();//用来保存后缀表达式
        Deque<Character> operatorStack = new ArrayDeque<Character>();// 存放操作符堆
        Character cChar = null, preOperator, preChar;
        out:
        for (int i = 0, len = infixExp.length(); i < len; i++) {
            preChar = cChar;
            cChar = infixExp.charAt(i);
            switch (cChar) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '%':
                case '^':
                case 's':
                case 'c':
                case 't':
                case 'p':
                case 'n':
                case 'l':
                case '!':
                    // 处理第一次为空栈的情况 不能为null，并且先不忙着移除，等判断操作符
                    // 的优先级足够再移除.注意”（“在栈中的优先级最低
                    // 左括号是一个输入符号时看铖是一个高优先级的操作符，而在栈中时看成是低优先级的操作符，"("在栈中时只有后续出现一个对应“)”才能弹出
                    preOperator = operatorStack.peekLast();
                    while (preOperator != null && preOperator != '('
                            && compareOperatorPriority(preOperator, cChar) != -1) {// 上一个操作符的优先级大于等于当前操作符，则将上一个操作符弹出加入后缀
                        // 表达式，再继续弹出操作符直至操作符的优先级小于当前操作符，再将当前操作符加入操作符堆栈。
                        // postfixExpBuffer.append(operatorStack.pollLast());
                        postfixExpBuffer.add(operatorStack.pollLast().toString());
                        preOperator = operatorStack.peekLast();
                    }
                    operatorStack.addLast(cChar);
                    break;
                // 如果栈里面的操作符优先级比当前的大，则把栈中优先级大的都添加到后缀表达式列表中
                case '(':
                case '[':
                case '{':
                    // 左括号([{直接压栈 左括号(
                    operatorStack.addLast('(');
                    break;
                case ')':
                case ']':
                case '}':
                    // 右括号则直接把栈中左括号上面的弹出，并加入后缀表达式中 .并且不将（加入后缀表达式c != null &&
                    Character c = operatorStack.pollLast();
                    while (c != null && c != '(') {
                        // postfixExpBuffer.append(c);
                        postfixExpBuffer.add(c.toString());
                        c = operatorStack.pollLast(); // c是'('时不要添加到输出字符串中
                    }
                    break;
                // 过滤空白符
                case ' ':
                case '\t':
                case '\n':
                case '\b':
                case '\r':
                    break;
                // 数字或小数点则凑成一个整数，加入后缀表达式postfixExpBuffer
                default:
                    if (Character.isDigit(cChar) || cChar == '.') {
                        while (Character.isDigit(cChar) || cChar == '.') {
                            numBuffer.append(cChar);
                            i++;
                            if (i < len) {
                                cChar = infixExp.charAt(i);
                            } else {
                                break;
                            }
                        }
                        String numS = numBuffer.toString();
                        if (Pattern.matches("^0\\d+", numS)) {
                            throw new Exception("操作数不能以0开头（除0.1形式）");
                        }
                        postfixExpBuffer.add(numBuffer.toString());
                        // postfixExpBuffer.append(numBuffer.toString());
                        numBuffer.setLength(0);
                        i -= 1;
                        // 将π视为一个整体的操作数
                    } else if (cChar == 'π') {// 因为前面已经将数字连接π的形式转换为数字*π，且再检查中缀表达式时避免了操作数的违规表现额
                        // π后不能紧跟操作数，所以此处可以直接加入。
                        postfixExpBuffer.add(Double.toString(java.lang.Math.PI));
                    } else if (cChar == 'e') {// 因为前面已经将数字连接e的形式转换为数字*e，且再检查中缀表达式时避免了操作数的违规表现额
                        // π后不能紧跟操作数，所以此处可以直接加入。
                        postfixExpBuffer.add(Double.toString(java.lang.Math.E));

                    } else {
                        throw new Exception("未知的操作符号");
                    }
            }
        }
        // 把剩余的操作符取出加入后缀表达式中
        while ((preOperator = operatorStack.pollLast()) != null) {
            postfixExpBuffer.add(preOperator.toString());
            // postfixExpBuffer.append(preOperator);
        }
        return postfixExpBuffer.toArray(new String[0]);
    }

    /**
     * 将中缀表达式一些数学符号替换为自定义规定符号号 并且也执行一些检验操作
     *
     * sin-s;cos-c;tan-t;
     *
     * @param infixExp中缀表达式
     * @return 返回替换操作符后的中缀表达式
     */
    private static String mathOperator2CustomOperator(String infixExp)
            throws Exception {
        StringBuffer sb = new StringBuffer();// 用来替换字符串
        Pattern p;
        Matcher m;
        boolean flag = true;
        int start = 0;
        // 不能有sincos、sincos( )、sin( )cos( )等格式。可以πsin（）
        // 组合：sinsin(保含：sinsin(数字)、sinsin数字)、sinsin(空白)、sin(空白)sin（包含sin(空白)sin(数字)、sin(空白)sin数字）、sin(空白)sin(空白)、
        p = Pattern
                .compile("((sin)|(sin\\(\\s*\\))|(cos)|(cos\\(\\s*\\))|(tan)|(tan\\(\\s*\\)))((sin)|(sin\\(\\s*\\))|(cos)|(cos\\(\\s*\\))|(tan)|(tan\\(\\s*\\)))");
        m = p.matcher(infixExp);
        if (m.find()) {
            throw new Exception("错误的表达式" + m.group(1) + "与" + m.group(8)
                    + "不能相连");
        }
        // sin(空白)、sin(数字)后不能直接跟数字,sin不能单独出现就不检验了，单独出现会抛出操作数与操作符不相等情况，但又有特殊情况。。。看情况检验吧
        // 对应sin单独出现的情况：不能与其他的操作符连续出现
        p = Pattern
                .compile("(((sin\\(.*\\))|(cos\\(.*\\))|(tan\\(.*\\)))([0-9]))|((sin\\(\\s*\\))|(cos\\(\\s*\\))|(tan\\(\\s*\\)))");
        m = p.matcher(infixExp);
        if (m.find()) {
            if (m.group(2) != null) {
                throw new Exception("错误的表达式" + m.group(2) + "与" + m.group(6)
                        + "不能相连");
            } else {
                throw new Exception("错误的表达式" + m.group(7) + "括号内不能为空");
            }

        }

        // 1.首先替换3π、ππ这样的字符串为3*π、π*π，并替换"×÷"符号为“*/”
        // ,因为检验是ππ格式是违法的，所以下面可以去除前一个π
        // 将替换3π、ππ与替换4sin等合并一块。请看下面
        // Pattern p = Pattern.compile("([0-9π])\\s*π");
        // Matcher m = p.matcher(infixExp);
        // while (m.find()) {
        // m.appendReplacement(sb, "$1*π");
        // }
        // infixExp = m.appendTail(sb).toString();
        // sb.setLength(0);//记得置为0
        // 替换2sin3、4sin45等格式为2*sin3、4*sin45，也即如果操作数与sin、cos、tan等相连则视为乘法运算
        // 所以如果这样写2sin45cos30是合法的，视为2*sin45*cos30
        // p = Pattern.compile("([0-9πe])\\s*(π|e|sin|cos|tan)")
        p = Pattern.compile("([0-9])\\s*(π|e|sin|cos|tan)");// 郁闷本想匹配3ππ形式的，但第一次匹配后，从第二个π开始，所以ππ不能连续匹配
        // 本以为可以用find（n)方法从第二个π前开始匹配，但不知怎么调用find（n）方法内存不足。。。。???只得用笨方法从新编写模式，此处不做处理
        while (flag) {
            m = p.matcher(infixExp);
            while (m.find()) {
                m.appendReplacement(sb, "$1*$2");
                start = m.start();
            }
            infixExp = m.appendTail(sb).toString();
            sb.setLength(0);// 记得置为0
            if (!m.find(start)) {
                flag = false;
            }
        }
        // 2.替换sin(3)sin(5)cos(6)格式为sin(3)*sin(5)*cos(6)

        p = Pattern
                .compile("((sin)|(sin\\(.+\\))|(cos)|(cos\\(.+\\))|(tan)|(tan\\(.+\\)))((sin)|(sin\\(.+\\))|(cos)|(cos\\(.+\\))|(tan)|(tan\\(.+\\)))");
        // p =
        // Pattern.compile("((sin)|(cos)|(tan))(\\{|\\[|\\().+(\\}|\\]|\\)))((sin|cos|tan)(\\{|\\[|\\().+(\\}|\\]|\\)))");
        flag = true;
        start = 0;
        while (flag) {
            m = p.matcher(infixExp);
            while (m.find()) {
                m.appendReplacement(sb, "$1*$8");
                start = m.start();
            }
            infixExp = m.appendTail(sb).toString();
            sb.setLength(0);// 记得置为0
            if (!m.find(start)) {
                flag = false;
            }
        }
        // 3.替换表现形式的sin、cos、tan、log替换为定义的对应字符，该字符为首字母
        infixExp = infixExp.replaceAll("sin", "s").replaceAll("cos", "c")
                .replaceAll("tan", "t").replaceAll("log", "l");
        return infixExp;
    }

    /**
     * 判断操作符是否是单目运算符
     *
     * @param operator
     * @return 是单目运算符返回true
     */
    private static boolean isUnaryOperator(char operator) {
        if (unaryOperator.indexOf(operator) != -1) {
            return true;
        }
        return false;
    }

    /**
     * 比较运算符的优先级,前者大于后者返回1，等于返回0，小于返回-1； 不支持操作符会捕获异常 . .
     * 支持的操作符“+，-，*，/，%,^，（，[，{”以及单目运算符。其中（、[、{的优先级相等
     *
     * @param preOperator
     * @param nextOperator
     * @return 1,0,-1
     * @throws Exception 不支持操作符
     */
    public static int compareOperatorPriority(char preOperator,
            char nextOperator) throws Exception {
        if (getOperatorPriority(preOperator) > getOperatorPriority(nextOperator)) {
            return 1;
        } else if (getOperatorPriority(preOperator) == getOperatorPriority(nextOperator)) {
            return 0;
        } else {
            return -1;
        }
    }

    // 获取运算符的优先级,优先级越高返回数字越大，+，-运算符优先级为1,*/%优先级为2，^为3，,单目运算符优先级4、（[{为5
    // l(log)运算符与单目运算符优先级相同，是因为虽然log作为双目运算符，但是其作为整体计算的
    // 抛出不支持运算符异常
    private static int getOperatorPriority(char operator) throws Exception {// 定义优先级
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
            case 'l':
                return 4;
            case '(':
            case '[':
            case '{':
                return 5;
            default:
                if (isUnaryOperator(operator)) {
                    return 4;
                } else {
                    throw new Exception("不支持此运算符");
                }

        }
    }

    public static void main(String[] args) {
        try {

            ArrayDeque<String> f = new ArrayDeque<String>();
            f.add("6");
            f.add("5");
            f.add("2");
            f.add("3");
            f.add("+");
            f.add("8");
            f.add("*");
            f.add("+");
            f.add("3");
            f.add("-");
            f.add("*");
            String[] d = {"6", "995", "2", "3", "+", "98", "%", "+", "3", "-",
                "*"};
            int n = 6;
            if (n != 0) {
                // System.out.print(1);
            } else if (n == 6) {
                // System.out.print(2);
            }

            // System.out.print(ToolKit.UtilMath.calculatePostfixExp(d));
            // System.out.print(Character.isDigit('0'));
            if (UtilMath.checkInfixExp("n4")[0] == "true") {
                System.out.print(UtilMath.calculatePostfixExp(UtilMath
                        .infixExp2PostfixExp("n4")));
            }
            System.out.print(UtilMath.checkInfixExp("ee")[1]);
            System.out.print(UtilMath.mathOperator2CustomOperator("0ππ"));
            System.out.print(UtilMath.infixExp2PostfixExp("377ππ4π"));
            // System.out.print(java.lang.UtilMath.sin(1));
            // System.out.print(java.lang.UtilMath.log10(1008));
            // System.out.print(ToolKit.UtilMath.calculatePostfixExp(ToolKit.UtilMath.infixExp2PostfixExp("3-(29-28)^2")));
            // System.out.print(ToolKit.UtilMath.checkInfixExp("3π-1-(3+8)")[1]);
            // System.out.print(011 + 1);
            // System.out.print(BigDecimal.valueOf(10.1).subtract(
            // BigDecimal.valueOf(1.8)));
            // System.out.print(10.1 - 1.8);
            // StringBuilder s = new StringBuilder("2333");
            // s.setLength(0);
            // System.out.print(s.toString().equals(""));
            // System.out.print(fk);
            // System.out.print("-------");
            // ToolKit.UtilMath.infixExp2PostfixExp("4 990π000");
            // System.out.print(Array2String(ToolKit.UtilMath.infixExp2PostfixExp("2π5ππ1sin2cos3tan"),
            // ""));
            System.out.print(UtilMath.mathOperator2CustomOperator("0ππ"));

        } catch (Exception ex) {
            Logger.getLogger(ToolKit.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

    }
}
