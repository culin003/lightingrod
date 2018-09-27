/**
 * 
 */
package org.leoly.lightingrod.support;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author guguihe
 *
 */
public class LeolyDateUtils {

    /**
     * 默认日期格式
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 默认日期时间格式
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final int RETURN_DATES = 1;
    public static final int RETURN_HOURES = 2;
    public static final int RETURN_MINUTES = 3;
    public static final int RETURN_SECONDS = 4;
    public static final int RETURN_MILLI_SECONDS = 5;

    private static final String[] NUMBERS = new String[] { "O", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };

    /**
     * 格式化日期
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(date);
    }

    /**
     * 格式化日期
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * TODO: 获取日期格式化后的当前时间
     * 
     * @return
     */
    public static String formatNow() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * TODO: 获取日期格式化后的当前时间
     * 
     * @param pattern
     * @return
     */
    public static String formatNow(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取日期开始时间，从一天的零点开始
     * 
     * @param date
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date getBeginDate(String date) {
        try {
            Date temp = parseDate(date, DATE_TIME_PATTERN);
            return getBeginDate(temp);
        }
        catch (Exception e) {
            throw new RuntimeException("日期转换失败！！", e);
        }
    }

    /**
     * 获取日期开始时间，从一天的零点开始
     * 
     * @param date
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date getBeginDate(Date date) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }
        catch (Exception e) {
            throw new RuntimeException("日期转换失败！！", e);
        }
    }

    /**
     * 获取日期开始时间，从一天的零点开始
     * 
     * @param date
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date getBeginDate(String date, String pattern) {
        try {
            Date temp = parseDate(date, pattern);
            Calendar c = Calendar.getInstance();
            c.setTime(temp);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }
        catch (Exception e) {
            throw new RuntimeException("日期转换失败！！", e);
        }
    }

    /**
     * 获取日期结束时间，即一天的结束时间
     * 
     * @param date
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date getEndDate(String date) {
        try {
            Date temp = parseDate(date, DATE_TIME_PATTERN);
            return getEndDate(temp);
        }
        catch (Exception e) {
            throw new RuntimeException("日期转换失败！！", e);
        }
    }

    /**
     * 获取日期结束时间，即一天的结束时间
     * 
     * @param date
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date getEndDate(Date date) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            return c.getTime();
        }
        catch (Exception e) {
            throw new RuntimeException("日期转换失败！！", e);
        }
    }

    /**
     * 获取日期结束时间，即一天的结束时间
     * 
     * @param date
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date getEndDate(String date, String pattern) {
        try {
            Date temp = parseDate(date, pattern);
            Calendar c = Calendar.getInstance();
            c.setTime(temp);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
            return c.getTime();
        }
        catch (Exception e) {
            throw new RuntimeException("日期转换失败！！", e);
        }
    }

    /**
     * 将日期字符串转换成日期对象
     * 
     * @param dateStr
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(DATE_TIME_PATTERN);
            return sdf.parse(dateStr);
        }
        catch (Exception e) {
            throw new RuntimeException("日期转换失败！！", e);
        }
    }

    /**
     * 将日期字符串转换成日期对象
     * 
     * @param dateStr
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(pattern);
            return sdf.parse(dateStr);
        }
        catch (Exception e) {
            throw new RuntimeException("日期转换失败！！", e);
        }
    }

    /**
     * 将日期字符串转换成日期对象
     * 
     * @param dateStr
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     * @throws ParseException
     */
    public static String getDateString(Date date) {
        try {
            Date now = Calendar.getInstance().getTime();
            // 天
            int dates = subDate(now, date, RETURN_DATES);
            if (dates > 7) {
                return format(date, DATE_PATTERN);
            }

            // 小时
            int houres = subDate(now, date, RETURN_HOURES);
            if (houres >= 24) {
                return dates + "天前";
            }

            // 分钟
            int minutes = subDate(now, date, RETURN_MINUTES);
            if (minutes >= 60) {
                return houres + "小时前";
            }
            else if (minutes < 1) {
                return "刚刚";
            }
            else {
                return minutes + "分钟前";
            }
        }
        catch (Exception e) {
            return format(date);
        }
    }

    /**
     * TODO: 日期相减
     * 
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @param returnType
     *            返回类型
     * @return
     */
    public static int subDate(Date date1, Date date2, int returnType) {
        long now = date1.getTime();
        long dnow = date2.getTime();
        long dateTime = 0;
        switch (returnType) {
            case RETURN_DATES:
                dateTime = 1000 * 60 * 60 * 24;
                break;
            case RETURN_HOURES:
                dateTime = 1000 * 60 * 60;
                break;
            case RETURN_MILLI_SECONDS:
                dateTime = 1;
                break;
            case RETURN_MINUTES:
                dateTime = 1000 * 60;
                break;
            case RETURN_SECONDS:
                dateTime = 1000;
                break;
            default:
                break;
        }

        return Double.valueOf((now - dnow) / dateTime).intValue();
    }

    /**
     * TODO: 将标准日期格式转换为中文日期
     * 
     * @param standarDateStr
     *            标准日期字符串（2012-05-14）
     * @return 二O一二年五月十四日，未正确转换时返回原始字符串
     */
    public static String getChinessDate(String standarDateStr) {
        int size = standarDateStr.length();
        if (size != 10) {
            return standarDateStr;
        }

        String result = StringUtils.EMPTY;
        String[] array = standarDateStr.split("-");
        // 处理年
        result += processYear(array[0]) + "年";
        // 处理月
        result += processMonthOrDate(array[1]) + "月";
        // 处理日
        result += processMonthOrDate(array[2]) + "日";
        return result;
    }

    /**
     * TODO: 将标准日期格式转换为中文日期
     * 
     * @param standarDateStr
     *            标准日期字符串（2012-05-14）
     * @return 二O一二年五月十四日，未正确转换时返回原始字符串
     */
    public static String getChinessDate(Date date) {
        String standarDateStr = format(date);
        int size = standarDateStr.length();
        if (size != 10) {
            return standarDateStr;
        }

        String result = StringUtils.EMPTY;
        String[] array = standarDateStr.split("-");
        // 处理年
        result += processYear(array[0]) + "年";
        // 处理月
        result += processMonthOrDate(array[1]) + "月";
        // 处理日
        result += processMonthOrDate(array[2]) + "日";
        return result;
    }

    /**
     * 得到几天前的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBeforeDay(Date d, int day) {
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
    public static Date getDateAfterDay(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfterMonth(Date d, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.MONTH, month);
        return now.getTime();
    }

    /**
     * 得到本月的第一天
     * 
     * @return
     */
    public static Date getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 得到本月的最后一天
     * 
     * @return
     */
    public static Date getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 得到上个月的第一天
     * 
     * @return
     */
    public static Date getPreMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期即为本月第一天
        return calendar.getTime();
    }

    /**
     * 得到上个月的最后一天
     * 
     * @return
     */
    public static Date getPreMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * TODO: 处理年
     * 
     * @param year
     * @return
     */
    private static String processYear(String year) {
        int length = year.length();
        String result = StringUtils.EMPTY;
        for (int i = 0; i < length; i++) {
            result += NUMBERS[Integer.parseInt(String.valueOf(year.charAt(i)))];
        }

        return result;
    }

    /**
     * TODO: 处理月份或日期
     * 
     * @param monthOrDate
     * @return
     */
    private static String processMonthOrDate(String monthOrDate) {
        int length = monthOrDate.length();
        String result = StringUtils.EMPTY;
        if (length == 2) {
            if ('0' == monthOrDate.charAt(0)) {
                result += NUMBERS[Integer.parseInt(String.valueOf(monthOrDate.charAt(1)))];
            }
            else {
                int first = Integer.parseInt(String.valueOf(monthOrDate.charAt(0)));
                if (first == 1) {
                    if ('0' == monthOrDate.charAt(1)) {
                        result += NUMBERS[10];
                    }
                    else {
                        result += NUMBERS[10] + NUMBERS[Integer.parseInt(String.valueOf(monthOrDate.charAt(1)))];
                    }
                }
                else {
                    if ('0' == monthOrDate.charAt(1)) {
                        result += NUMBERS[first] + NUMBERS[10];
                    }
                    else {
                        result += NUMBERS[first] + NUMBERS[10]
                                + NUMBERS[Integer.parseInt(String.valueOf(monthOrDate.charAt(1)))];
                    }
                }
            }
        }
        else {
            result += NUMBERS[Integer.parseInt(monthOrDate)];
        }

        return result;
    }
    
    /**
     * 判断某一时间是否在一个区间内
     * 
     * @param sourceTime
     *            时间区间,半闭合,如[10:00-20:00)
     * @param curTime
     *            需要判断的时间 如10:00
     * @return 
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } 
            else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }
    
    /* 
     * 毫秒转化时分秒毫秒 
     */  
    public static String formatTime(Long ms) {  
        Integer ss = 1000;  
        Integer mi = ss * 60;  
        Integer hh = mi * 60;  
        Integer dd = hh * 24;  
      
        Long day = ms / dd;  
        Long hour = (ms - day * dd) / hh;  
        Long minute = (ms - day * dd - hour * hh) / mi;  
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  
          
        StringBuffer sb = new StringBuffer();  
        if(day > 0) {  
            sb.append(day+"天");  
        }  
        if(hour > 0) {  
            sb.append(hour+"时");  
        }  
        if(minute > 0) {  
            sb.append(minute+"分");  
        }  
        return sb.toString();  
    }
    
    /**
     * @Description TODO(当前时间时分秒转换为数字格式)   
     * @return
     * @author liushipeng
     */
    public static String formatNumberTime() {  
    	
    	Calendar date = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();  
        sb.append(date.get(Calendar.HOUR_OF_DAY));
        sb.append(date.get(Calendar.MINUTE));
        sb.append(date.get(Calendar.SECOND));
        
        return sb.toString();  
    }

    public static void main(String[] args) {
        String ds = "2015-06-03";
        Date d = parseDate(ds, "yyyy-MM-dd");
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Boolean isIn = isInTime("08:00-21:00", sdf.format(new Date()));
        System.out.println(isIn);
        System.out.println(formatNumberTime());
//        System.out.println(System.currentTimeMillis());
    }
}
