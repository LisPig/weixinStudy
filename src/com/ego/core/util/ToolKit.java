/*----------javabean---------
 * @功能说明：主要设置一些常用的便捷方法
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.util;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 主要设置一些常用的便捷方法
 */
public class ToolKit {

    /**
     * 输入流转换为字符串，编码默认为utf-8
     *
     * @param in
     * @param charset 编码，为null则为utf-8
     * <p>
     * @return
     * <p>
     * @throws java.io.IOException
     */
    public static String inputStream2String(InputStream in, String charset) throws IOException {
        if (in == null) {
            return "";
        }
        charset = UtilValidate.isNotEmpty(charset) ? charset : "utf-8";
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n, "UTF-8"));
        }
        return out.toString();
    }

    /**
     * 将一个Double转为int的String，将省略小数点后面的值
     *
     * @param
     * @return
     */
    public static String double2IntString(Double d) {
        int value = ((Double) d).intValue();
        return String.valueOf(value);
    }

    /**
     *
     * @param i
     * <p>
     * @return
     */
    public static String toString(Integer i) {
        if (i != null) {
            return String.valueOf(i);
        }
        return "";
    }

    public static String toString(Double d) {
        if (null != d) {
            return String.valueOf(d);
        }
        return "";
    }

    /**
     * obj为null则返回指定字符串值，如果指定字符串为null则返回"";
     *
     * @param obj
     * @param str
     * <p>
     * @return
     */
    public static String null2string(Object obj, String str) {
        if (obj == null) {
            return str != null ? str : "";
        }
        return obj.toString();
    }

    public static String null2string(Object obj) {
        return null2string(obj, null);
    }

    /**
     * 将对象转换为long型数值。如果对象不是数值对象或者对象为null返回0
     *
     * @param longObj
     * <p>
     * @return 如果对象不是数值对象或者对象为null返回0
     */
    public static long object2long(Object longObj) {

        return object2long(longObj, 0);
    }

    /**
     * 将对象转换为long型数值。如果对象不是数值对象或者对象为null返回0
     *
     * @param longObj
     * @param defaultvalue
     * <p>
     * @return
     */
    public static long object2long(Object longObj, long defaultvalue) {
        if (UtilValidate.isEmpty(longObj)) {
            return defaultvalue;
        } else if (longObj instanceof Number || UtilValidate.isNumber(longObj.toString())) {
            return Long.valueOf(longObj.toString());
        }
        return defaultvalue;
    }

    /**
     * 如对象为null 转换为0
     *
     * @param s
     * <p>
     * @return
     */
    public static int object2Int(Object intobj) {
        return object2Int(intobj, 0);
    }

    /**
     * 将对象转换为int类型，如果intobj为null，则返回默认值
     *
     * @param intobj
     * @param defaultvalue
     * <p>
     * @return
     */
    public static int object2Int(Object intobj, int defaultvalue) {
        if (UtilValidate.isEmpty(intobj)) {
            return defaultvalue;
        } else if (intobj instanceof Number || UtilValidate.isNumber(intobj.toString())) {
            return Integer.parseInt(intobj.toString());
        }
        return defaultvalue;

    }

    public static float object2Float(Object floatobj, float defaultvalue) {
        if (UtilValidate.isEmpty(floatobj)) {
            return defaultvalue;
        } else if (floatobj instanceof Number || UtilValidate.isDigit(floatobj.toString())) {
            return Float.parseFloat(floatobj.toString());
        }
        return defaultvalue;

    }

    public static float object2Float(Object floatobj) {

        return object2Float(floatobj, 0.0f);

    }

    public static boolean object2Boolean(Object booleanobj, boolean defaultvalue) {

        if (booleanobj != null && (booleanobj instanceof Boolean || "true".equalsIgnoreCase(booleanobj.toString()) || "false".equalsIgnoreCase(booleanobj.toString()))) {
            return Boolean.parseBoolean(booleanobj.toString());
        }
        return defaultvalue;
    }

    public static boolean object2Boolean(Object booleanobj) {

        return object2Boolean(booleanobj, false);
    }

    public static double object2Double(Object doubleobj, double defaultvalue) {
        if (UtilValidate.isEmpty(doubleobj)) {
            return defaultvalue;
        } else if (doubleobj instanceof Number || UtilValidate.isDigit(doubleobj.toString())) {
            return Double.parseDouble(doubleobj.toString());
        }
        return defaultvalue;

    }

    /**
     * 将对象转换为DOUBLE型数值。如果对象不是数值对象或者对象为null返回0
     *
     * @return
     */
    public static double object2Double(Object doubleobj) {
        return object2Double(doubleobj, -1D);

    }

    public static BigDecimal object2BigDecimal(Object numberobj, double defaultvalue) {
        return BigDecimal.valueOf(ToolKit.object2Double(numberobj, defaultvalue));

    }

    public static BigDecimal object2BigDecimal(Object numberobj) {
        return object2BigDecimal(numberobj, -1);

    }

    /**
     * <b>功 能:</b>将字符串数组转换为有指定分隔符的字符串。如果没有知道分
     *
     * 隔符或分隔符为空，则指定默认“，”分隔符 <br><b>注释:</b>如果字符串数
     *
     * 组长度为0，返回null。
     *
     * @param str字符串数组
     * @param separator ，子字符串连接时的分隔符
     * <p>
     * @return 字符串，将字符串数组各子字符串连接成字符串。
     */
    public static String Array2String(String[] str, String separator) {
        if (str.length == 0) {
            return null;
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0, length = str.length; i < length; i++) {
            if (i != length - 1) {
                s.append(str[i]);
                if (separator != null || !separator.equals("")) {
                    s.append(separator);
                } else {
                    s.append(",");
                }
            } else {
                s.append(str[i]);
            }
        }
        return s.toString();
    }

    /**
     * 将一个list转为以split分隔的string
     *
     * @param list
     * @param split
     * <p>
     * @return
     */
    public static String list2String(List list, String split) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : list) {
            if (sb.length() != 0) {
                sb.append(split);
            }
            sb.append(obj.toString());
        }
        return sb.toString();
    }

    /**
     * 将map转换为字符串
     *
     * @param map
     * @param keyValueSplit 键值中间的分隔符
     * @param rowSplit 各行分隔符
     * <p>
     * @return
     */
    public static String map2String(Map map, String keyValueSplit, String rowSplit) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry> set = map.entrySet();

        for (Map.Entry entry : set) {
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (sb.length() != 0) {
                if (rowSplit != null) {
                    sb.append(rowSplit);
                }

            }
            sb.append(k.toString());
            if (keyValueSplit != null) {
                sb.append(keyValueSplit);
            }
            sb.append(v.toString());
        }
        return sb.toString();
    }

    /**
     * 将列表转换为数组
     *
     * @param <F>
     * @param <T>
     * @param list
     * <p>
     * @return
     */
    public static <F, T> T[] list2StringArray(List<F> list) {
        //  String[] values = new T[list.size()];
        return (T[]) list.toArray();
    }

    /**
     * 将字符数组转为整型数组
     *
     * @param c
     * <p>
     * @return
     * <p>
     * @throws NumberFormatException
     */
    public static int[] charArray2IntArray(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }

    /**
     * 将字符串对象按给定的分隔符separator转象为ArrayList对象
     *
     * @param str 待转换的字符串对象
     * @param separator 字符型分隔符
     * <p>
     * @return ArrayList对象
     */
    public static ArrayList<String> str2ArrayList(String str, String separator) {
        StringTokenizer strTokens = new StringTokenizer(str, separator);
        ArrayList<String> list = new ArrayList<String>();

        while (strTokens.hasMoreTokens()) {
            list.add(strTokens.nextToken().trim());
        }
        return list;
    }

    /**
     * 将字符串对象按给定的分隔符separator转象为字符型数组对象
     *
     * @param str 待转换的符串对象
     * @param separator 字符型分隔符
     * <p>
     * @return 字符型数组
     */
    public static String[] str2StrArray(String str, String separator) {
        StringTokenizer strTokens = new StringTokenizer(str, separator);
        String[] strArray = new String[strTokens.countTokens()];
        int i = 0;

        while (strTokens.hasMoreTokens()) {
            strArray[i] = strTokens.nextToken().trim();
            i++;
        }

        return strArray;
    }

    /**
     * 将字符串对象按给定的分隔符separator转象为Long 类型的ArrayList对象。如果给定的不是数字字符串则不转换
     *
     * @param str 待转换的字符串对象
     * @param separator 字符型分隔符
     * <p>
     * @return
     */
    public static List<Long> str2LongArrayList(String str, String separator) {
        if (UtilValidate.isEmpty(str)) {
            return new ArrayList<Long>(0);
        }
        StringTokenizer strTokens = new StringTokenizer(str, separator);
        ArrayList<Long> list = new ArrayList<Long>();
        while (strTokens.hasMoreTokens()) {
            String n = strTokens.nextToken().trim();
            if (UtilValidate.isNumber(n)) {
                long num = Long.parseLong(n);
                list.add(num);
            }
        }
        return list;
    }

    /**
     *
     * 用分隔符将字符串进行分割，并放入集合
     *
     * @param str
     * @param separator
     * <p>
     * @return
     */
    public static Set<String> str2Set(String str, String separator) {
        String[] values = str2StrArray(str, separator);
        Set<String> result = new LinkedHashSet<String>();
        result.addAll(Arrays.asList(values));
        return result;
    }

    /**
     * 不支持泛型的Array.
     *
     * @param oList
     * @return
     */
    public static Object[] list2Array(List<Object> oList) {
        Object[] oArray = oList.toArray(new Object[]{});
        // TODO 需要在用到的时候另外写方法，不支持泛型的Array.  
        return oArray;
    }

    public static Object[] set2Array(Set<Object> oSet) {
        Object[] oArray = oSet.toArray(new Object[]{});
        // TODO 需要在用到的时候另外写方法，不支持泛型的Array.  
        return oArray;
    }

    public static <T> List<T> set2List(Set<T> oSet) {
        List<T> tList = new ArrayList<T>(oSet);
        // TODO 需要在用到的时候另外写构造，根据需要生成List的对应子类。  
        return tList;
    }

    public static <T> List<T> array2List(T[] tArray) {
        List<T> tList = Arrays.asList(tArray);
        // TODO 单纯的asList()返回的tList无法add(),remove(),clear()等一些影响集合个数的操作，  
        // 因为Arrays$ArrayList和java.util.ArrayList一样，都是继承AbstractList，  
        // 但是Arrays$ArrayList没有override这些方法，而java.util.ArrayList实现了。  
        // TODO 建议使用List的子类做返回，而不是Arrays$ArrayList。根据需要吧。如下行注释:  
        // List<T> tList = new ArrayList<T>(Arrays.asList(tArray));  
        return tList;
    }

    public static <T> Set<T> list2Set(List<T> tList) {
        Set<T> tSet = new HashSet<T>(tList);
        //TODO 具体实现看需求转换成不同的Set的子类。  
        return tSet;
    }

    public static <T> Set<T> array2Set(T[] tArray) {
        Set<T> tSet = new HashSet<T>(Arrays.asList(tArray));
        // TODO 没有一步到位的方法，根据具体的作用，选择合适的Set的子类来转换。  
        return tSet;
    }

    /**
     * 字节基本类型数组转换为字节对象数组
     *
     * @param bytes
     * <p>
     * @return
     */
    public static Byte[] bytePrimitiveArray2ByteObjectArray(byte[] bytes) {
        final Byte[] objects = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            objects[i] = b;
        }
        return objects;
    }

    /**
     * 字节对象数组转换为字节基本类型数组
     *
     * @param objects
     * <p>
     * @return
     */
    public static byte[] byteObjectArray2BytePrimitiveArray(Byte[] objects) {
        final byte[] bytes = new byte[objects.length];
        for (int i = 0; i < objects.length; i++) {
            Byte b = objects[i];
            bytes[i] = b;
        }
        return bytes;
    }

    /**
     * 转换给定字符串的编码格式
     *
     * @param inputString 待转换字符串对象
     * @param inencoding 待转换字符串的编码格式
     * @param outencoding 准备转换为的编码格式
     * <p>
     * @return 指定编码与字符串的字符串对象
     */
    public static String convertEncoding(String inputString, String inencoding, String outencoding) {
        if (inputString == null || inputString.length() == 0) {
            return inputString;
        }
        try {
            return new String(inputString.getBytes(outencoding), inencoding);
        } catch (Exception e) {
            return inputString;
        }
    }

    /**
     * <b>功 能:</b>获取日期。按指定的分隔符显示日期.只获取年月日 <br> <b>注释:</b>
     *
     * @param separator ，日期格式的分隔符;
     * <p>
     * @return Date类型的当前日期
     */
    public static Date getDate(String separator) {
        return java.sql.Date.valueOf(getDate2Str(separator));
    }

    /**
     * <b>功 能:</b>获取日期。按默认的“-”的分隔符显示日期.只获取年月日 <br> <b>注释:</b>
     *
     * @return Date类型的当前日期
     */
    public static Date getDate() {
        return getDate("-");
    }

    /**
     * <b>功 能:</b>获取日期。按指定的分隔符显示日期.只获取年月日。 此月份从1开始。
     *
     * @param separator
     * <p>
     * @return 返回字符串形式
     */
    public static String getDate2Str(String separator) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //String m = month < 10 ? "0" + month : "" + month;
        //String d = day < 10 ? "0" + day : "" + day;

        return year + separator + (month < 10 ? "0" + month : "" + month) + separator + (day < 10 ? "0" + day : "" + day);
    }

    /**
     * <b>功 能:</b>获取日期。按默认的“-”的分隔符显示日期.只获取年月日
     *
     * @return 当前日期字符串形式
     */
    public static String getDate2Str() {
        return getDate2Str("-");
    }

    /**
     * 获取当前系统的时间戳，格式“2013:02:12 12:11:56 123”
     *
     * @return
     */
    public static Timestamp getTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 将日期对象转换为日历对象
     *
     * @param date ����
     * <p>
     * @return ����
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 获得日历对象
     *
     * @param year ���
     * @param month �·�
     * @param day ���µڼ���
     * <p>
     * @return ����
     */
    public static Calendar getCalendar(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal;
    }

    /**
     * 根据表达式格式化日期。exp表达式如“y-m-d”
     *
     * @param date
     * @param format
     * <p>
     * @return
     */
    public static String getDate2Str(Date date, String format) {
        DateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    /**
     * 获取系统当前的时间,格式 "yyyy-MM-dd HH:mm:ss"并转化为大写形式
     *
     * @return
     */
    public static String getTime2Str() {
        return getDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss").toUpperCase();
    }

    /**
     * 将字符串转换为日期对象
     *
     * @param source
     * @param format
     * <p>
     * @return
     * <p>
     * @throws ParseException
     */
    public static Date dateParse(String source, String format) throws ParseException {
        DateFormat fmt = new SimpleDateFormat(format);
        return fmt.parse(source);
    }

    public static String numberFormat(double number, String ex) {
        NumberFormat fmt = new DecimalFormat(ex);
        return fmt.format(number);
    }

    public static Number numberParse(String source, String ex) throws ParseException {
        NumberFormat fmt = new DecimalFormat(ex);
        return fmt.parse(source);
    }

    public static List<String> getLocalhostIp() throws SocketException {
        List<String> ips = new ArrayList();
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            // System.out.println(netInterface.getName());
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address) {
                    ips.add(ip.getHostAddress());
                    //System.out.println("本机的IP = " + ip.getHostAddress());
                }
            }
        }
        return ips;
    }

    /**
     * 类所在的绝对路径.得到的是当前类所在的目录，包括报名，但不包括类名
     *
     * @param clazz
     * <p>
     * @return
     */
    public static String getPath(Class clazz) {
        String path = clazz.getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    /**
     * 对象所在的绝对路径.得到的是当前类所在的目录，包括报名，但不包括类名
     *
     * @param object
     * <p>
     * @return
     */
    public static String getPath(Object object) {
        String path = getPath(object.getClass());
        return path;
    }

    /**
     * 得到工程名字
     *
     * @return
     */
    public static String getProjectDir() {
        return System.getProperty("user.dir");
    }

    /**
     * 包的路径,物理路径,没准确实现
     *
     * @param object
     * @deprecated
     * @return
     */
    public static String getPackagePath(Object object) {
        Package p = object.getClass().getPackage();
      //  LoggerManager.debug(p.getName());
        return p != null ? p.getName().replace(".", File.separator) : "";
    }

    /**
     * 获得应用程序类加载的根目录（物理路径）如“D:/360云盘/临时/项目/java-web/egoshop-1.0.7/build/web/WEB-INF/classes/”。
     *
     * <p>
     *
     * 路径后有“/”。
     *
     * <p>
     *
     * 对于非web应用来说，类加载的根目录就是应用程序的根目录，而web应用程序的根目录是"web/",通过{@link com.ego.jee.web.util.WebKit#getWebAppRootPath(java.lang.Class)
     * }
     *
     * <p>
     *
     * 获取web应用的根目录.
     *
     * <p>
     *
     * 会对url进行解码，解码失败返回原url。
     *
     * @param cls 相对某个类的路径，如果cls为null，默认为本类。
     * <p>
     * @return 应用程序的根目录
     */
    public static String getAppClassesLoadedRootPath(Class cls) {
        cls = cls != null ? cls : ToolKit.class;
        //获得类的全限定名，没有后缀“.class”。包括包名和类名的路径信息，即类的权限定名。但没有后缀.class。”test.Test“
        String clsFullName = cls.getName();
        //com.ego.test.PropHelper.class。
        String clsFullFileName = clsFullName + ".class";
        //获得包，如果类在根目录包为null
        Package pk = cls.getPackage();
        //包名
        String pkName = "";
        if (pk != null) {
            pkName = pk.getName();
        }
        //得到类的文件名.类文件，不包括包名，但包括后缀.class。”Test.class“
        String clsFileName = clsFullFileName.substring(pkName.length() + 1);

        //资源的绝对物理路径。”file:/C:/Documents%20and%20Settings/Administrator/%e6%a1%8c%e9%9d%a2/test/build/classes/test/Test.class“
        String clsFilePath = cls.getResource(clsFileName).toString();
        //根目录”file:/D:/360%e4%ba%91%e7%9b%98/%e4%b8%b4%e6%97%b6/%e9%a1%b9%e7%9b%ae/java-web/egoshop-1.0.7/build/web/WEB-INF/classes/“
        String appRoot = clsFilePath.substring(0, clsFilePath.length() - clsFullFileName.length());

        int pos = appRoot.indexOf("file:");
        if (pos > -1) {
            appRoot = appRoot.substring(pos + 6);
        }

        if (appRoot.endsWith("!")) {
            appRoot = appRoot.substring(0, appRoot.lastIndexOf("/"));
        }
        try {
            //解码，还原空格
            return URLDecoder.decode(appRoot, "utf-8").replace("%20", " ");
        } catch (UnsupportedEncodingException ex) {
            return appRoot.replace("%20", " ");
        }

    }

    /**
     * 获取应用的根目录物理路径不以"/“结尾
     *
     * @return
     */
    public static String getRootPath() {
        try {
            //   String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString();
            String filePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            if (filePath.toLowerCase().indexOf("file:") > -1) {
                filePath = filePath.substring(6, filePath.length());
            }
            if (filePath.toLowerCase().indexOf("classes") > -1) {
                filePath = filePath.replaceAll("/classes", "");
            }
            if (filePath.toLowerCase().indexOf("web-inf") > -1) {
                filePath = filePath.substring(0, filePath.length() - 9);
            }
            if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
                filePath = "/" + filePath;
            } else if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            if (filePath.endsWith("/")) {
                filePath = filePath.substring(0, filePath.length() - 1);
            }

            return filePath;

        } catch (URISyntaxException ex) {
            Logger.getLogger(ToolKit.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * 获取指定资源的根目录路径，以"/”结尾
     *
     * @param resource
     * <p>
     * @return
     */
    public static String getRootPath(String resource) {
        try {
            //String filePath = Thread.currentThread().getContextClassLoader().getResource(resource).toString();
            URL file = Thread.currentThread().getContextClassLoader().getResource(resource);
            if (file != null) {
                String filePath = file.toURI().getPath();
                if (filePath.toLowerCase().indexOf("file:") > -1) {
                    filePath = filePath.substring(6, filePath.length());
                }
                if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
                    filePath = "/" + filePath;
                } else if (filePath.startsWith("/")) {
                    filePath = filePath.substring(1);
                }
                if (!filePath.endsWith("/")) {
                    filePath = filePath + "/";
                }
                return filePath;
            }
            return null;
        } catch (URISyntaxException ex) {
            Logger.getLogger(ToolKit.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * 以进程方式调用javac工具编译java源码文件
     *
     * @param classSrc 。class源文件地址
     * @param classOutputPath ，产生class文件后的地址
     * <p>
     * @return 编译失败返回false
     */
    public static boolean compileByJavacTool(File javaSrc, File classOutputPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("javac.exe",
                    javaSrc.toString(), "-d", classOutputPath.toString());
            Process process;
            process = pb.start();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * 计算校验和
     *
     * @param value
     * @param length
     * <p>
     * @return
     */
    public static char calculateChecksum(String value, int length) {
        if (value != null && value.length() == length + 1) {
            value = value.substring(0, length);
        }
        if (value == null || value.length() != length) {
            throw new IllegalArgumentException("Illegal size of value; must be either" + length + " or " + (length + 1) + " characters");
        }
        int oddsum = 0;
        int evensum = 0;
        for (int i = value.length() - 1; i >= 0; i--) {
            if ((value.length() - i) % 2 == 0) {
                evensum += Character.digit(value.charAt(i), 10);
            } else {
                oddsum += Character.digit(value.charAt(i), 10);
            }
        }
        int check = 10 - ((evensum + 3 * oddsum) % 10);
        if (check >= 10) {
            check = 0;
        }
        return Character.forDigit(check, 10);
    }

    /**
     * 获取简单类名，即不包括包名。
     *
     * @param clz
     * <p>
     * @return
     */
    public static String getClassName(Class clz) {
        String name = clz.getSimpleName();
        int pos = name.indexOf("$$EnhancerByCGLIB$$");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
    }

    /**
     * md5计算采用md5散列的方式，计算hash值，这样基本上能保证key散列出啦的hash不会重复。
     *
     * @param key
     * @return
     */
    public static long MD5Hash(String key) {
        byte[] bKey = UtilSecurity.MD5(key);
        long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
        /*
         for (int h = 0; h < 4; h++) {
         Long k = ((long) (bKey[3 + h * 4] & 0xFF) << 24)
         | ((long) (bKey[2 + h * 4] & 0xFF) << 16)
         | ((long) (bKey[1 + h * 4] & 0xFF) << 8)
         | (bKey[h * 4] & 0xFF);

         }
         */
        return res;
    }

    public static void main(String[] args) {
        try {
            /*
             System.out.print(getAppClassesLoadedRootPath(ToolKit.class).substring(0, getAppClassesLoadedRootPath(ToolKit.class).indexOf("WEB-INF/")));

             System.out.print(numberFormat(4, "-"));
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
             */
            // System.out.print(ToolKit.Math.calculatePostfixExp(d));
            // System.out.print(Character.isDigit('0'));
            //if (ToolKit.Math.checkInfixExp("n4")[0] == "true") {
            // System.out.print(ToolKit.Math.calculatePostfixExp(ToolKit.Math
            //     .infixExp2PostfixExp("n4")));
            //  }
            //System.out.print(ToolKit.Math.checkInfixExp("ee")[1]);
            //System.out.print(Math.mathOperator2CustomOperator("0ππ"));
            // System.out.print(ToolKit.Math.infixExp2PostfixExp("377ππ4π"));
            // System.out.print(java.lang.Math.sin(1));
            // System.out.print(java.lang.Math.log10(1008));
            // System.out.print(ToolKit.Math.calculatePostfixExp(ToolKit.Math.infixExp2PostfixExp("3-(29-28)^2")));
            // System.out.print(ToolKit.Math.checkInfixExp("3π-1-(3+8)")[1]);
            // System.out.print(011 + 1);
            // System.out.print(BigDecimal.valueOf(10.1).subtract(
            // BigDecimal.valueOf(1.8)));
            // System.out.print(10.1 - 1.8);
            // StringBuilder s = new StringBuilder("2333");
            // s.setLength(0);
            // System.out.print(s.toString().equals(""));
            // System.out.print(fk);
            // System.out.print("-------");
            // ToolKit.Math.infixExp2PostfixExp("4 990π000");
            // System.out.print(Array2String(ToolKit.Math.infixExp2PostfixExp("2π5ππ1sin2cos3tan"),
            // ""));
            // System.out.print(Math.mathOperator2CustomOperator("0ππ"));
        } catch (Exception ex) {
            // Logger.getLogger(ToolKit.class.getName()).log(Level.SEVERE, null,
            //   ex);
        }

    }
}
