/*----------javabean---------
 * @功能说明：主要日期时间常用方法
 * @创建日期：2012-6-31:09:23
 * @最后修改日期：2013-3-31:09:23
 */
package com.ego.core.util;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 主要日期时间常用方法
 *
 * @author ego4qin@163.com
 */

public class UtilTime {

    // 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    // 格式：年－月－日 小时：分钟
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";
    // 格式：年月日-小时分钟秒
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";
    // 格式：年－月－日
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    // 格式：月－日
    public static final String SHORT_DATE_FORMAT = "MM-dd";
    // 格式：小时：分钟：秒
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";
    //格式：年-月
    public static final String MONTG_DATE_FORMAT = "yyyy-MM";
    // 年的加减
    public static final int SUB_YEAR = Calendar.YEAR;
    // 月加减
    public static final int SUB_MONTH = Calendar.MONTH;
    // 天的加减
    public static final int SUB_DAY = Calendar.DATE;
    // 小时的加减
    public static final int SUB_HOUR = Calendar.HOUR;
    // 分钟的加减
    public static final int SUB_MINUTE = Calendar.MINUTE;
    // 秒的加减
    public static final int SUB_SECOND = Calendar.SECOND;
    static final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四",
        "星期五", "星期六"};
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 获取系统日期，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getDate() {
        Date date = new Date();

        return timeFormat.format(date);
    }

    /**
     * 获取系统日期，返回以指定分隔符显示的日期.默认“yyyyMMdd”
     *
     * @param separator
     * @return
     */
    public static String getDate(String separator) {
        separator = separator == null ? "" : separator;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(MessageFormat.format("yyyy{0}MM{0}dd", separator));
        return df.format(date);
    }

    /**
     * 符合日期格式的字符串转换为日期类型 根据给定的字符串日期活动日期对象.
     *
     * @param date 字符串日期
     * @param pattern 返回日期的模式如“yyyy-mm-dd”，如果为null返回 "yyyy-MM-dd"格式的日期对象
     * @return
     */
    public static Date getDate(String date, String pattern) {
        if (("" + date).equals("")) {
            return null;
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date newDate = new Date();
        try {
            newDate = sdf.parse(date);
        } catch (Exception ex) {
           // LoggerManager.debug("字符串日期:{0格式化:{1}错误", date, pattern);
        }
        return newDate;
    }

    /**
     * 根据给定的日期对象获得字符串格式的日期.
     *
     * @param date
     * @param pattern 返回日期的模式如“yyyy-mm-dd”，如果为null返回 "yyyy-MM-dd"格式的日期对象
     * @return
     */
    public static String getDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            dateString = sdf.format(date);
        } catch (Exception ex) {
            // LoggerManager.debug("字符串日期:{0格式化:{1}错误", date, pattern);
        }
        return dateString;
    }

    /**
     * 判断日期two是否在日期one前面
     *
     * @param one 日期一
     * @param two 日期二
     * @return
     */
    public static boolean before(Date one, Date two) {
        return two.getTime() - one.getTime() < 0;
    }

    /**
     * 判断日期date是否在当前日期前面
     *
     * @param date
     * @return
     */
    public static boolean beforeNow(Date date) {
        return date.getTime() - new Date().getTime() < 0;
    }

    /**
     * 判断日期two是否在日期one后
     *
     * @param one
     * @param two
     * @return
     */
    public static boolean after(Date one, Date two) {
        return two.getTime() - one.getTime() > 0;
    }

    /**
     * 判断日期date是否在当前日期后面
     *
     * @param date
     * @return
     */
    public static boolean afterNow(Date date) {
        return date.getTime() - new Date().getTime() > 0;
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 判断是不是今天
     *
     * @param time
     * @return
     */
    public static boolean isToday(Date time) {
        try {
            Date nowTime = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String todayStr = format.format(nowTime);
            Date today = format.parse(todayStr);
            long starttime = today.getTime();
            long endtime = today.getTime() + 86400000;
            if (starttime <= time.getTime() && time.getTime() <= endtime) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是不是昨天.同一天,前天
     *
     * @param oldTime 较小的时间
     * @param newTime 较大的时间 (如果为空 默认当前时间 ,表示和当前时间相比)
     * @return -1 ：同一天. 0：昨天 . 1 ：至少是前天.
     * @throws ParseException 转换异常
     */
    public static int isYeaterday(Date oldTime, Date newTime) throws ParseException {
        if (newTime == null) {
            newTime = new Date();
        }
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点    
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today = format.parse(todayStr);
        //昨天 86400000=24*60*60*1000 一天    
        if ((today.getTime() - oldTime.getTime()) > 0 && (today.getTime() - oldTime.getTime()) <= 86400000) {
            return 0;
        } else if ((today.getTime() - oldTime.getTime()) <= 0) { //至少是今天    
            return -1;
        } else { //至少是前天    
            return 1;
        }

    }

    /**
     * 获得某月的天数
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if (month.equals("1") || month.equals("3") || month.equals("5")
                || month.equals("7") || month.equals("8") || month.equals("10")
                || month.equals("12")) {
            days = 31;
        } else if (month.equals("4") || month.equals("6") || month.equals("9")
                || month.equals("11")) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * 获取某年某月的天数
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得当前月份
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前年份
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的天
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的月份，1-12
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
     */
    public static long dayDiff(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 86400000;
    }

    /**
     * 获取每月的第一周
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的最后一周
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     *
     * @param date YYYY-mm-dd
     * @return
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    public static Date getTodayStartTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return (Date) currentDate.getTime().clone();
    }

    public static Date getTodayEndTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return (Date) currentDate.getTime().clone();
    }

    public static Date getWeekStartTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);

        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (Date) currentDate.getTime().clone();
    }

    public static Date getWeekEndTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return (Date) currentDate.getTime().clone();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UtilTime date = new UtilTime();
        System.out.println(date.getDate("/"));

    }
}
