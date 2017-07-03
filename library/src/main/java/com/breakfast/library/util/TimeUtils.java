package com.breakfast.library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间转换工具
 */
public class TimeUtils {

  public static final String DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:ssz";
  public static final String MONTH_DAY_FORMATTER = "MM-dd";
  public static final String DATETIME_FORMATTER = "yyyy-MM-dd HH:mm";
  public static final String DATE_FORMATTER_NO_TIME = "yyyy-MM-dd";
  public static final String TIME_FORMATTER_NO_SECOND = "HH:mm";
  public static final String DATE_FORMATTER_NO_ZONE_T = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_FORMATTER_LONG = "yyyyMMddHHmm";
  public static final String MONTH_DAY_CHINEASE_FORMATTER = "MM月dd日";
  public static final String MONTH_DAY_CHINEASE_WITH_WEEKLY_FORMATTER = "MM月dd日(EEE)";
  public static final String DAY_WEEKLY_CHINEASE_FORMATTER = "yyy年MM月dd日(EEE)";
  public static final String DATETIME_CHINEASE_FORMATTER = "yyy年MM月dd日(EEE) HH:mm:ss";

  /**
   * 将格式为：yyyy-MM-ddTHH:mm:ss +0800的字符串转化为UnixTime
   *
   * @return -1表示失败，其他表示成功
   */
  public static long parse(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER_LONG, Locale.getDefault());
    return Long.parseLong(dateFormat.format(date));
  }


  public static boolean isToday(Calendar date)
  {
    Calendar today=Calendar.getInstance();
     return (today.get(Calendar.YEAR) == date.get(Calendar.YEAR)
             && today.get(Calendar.MONTH)  == date.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH) );
  }
  /**
   * 将格式为：yyyy-MM-ddTHH:mm:ss +0800的字符串转化为UnixTime
   *
   * @return -1表示失败，其他表示成功
   */
  public static long parse(Calendar date) {
    return parse(date.getTime());
  }

  /**
   * 将格式为：yyyy-MM-ddTHH:mm:ss +0800的字符串转化为UnixTime
   *
   * @return -1表示失败，其他表示成功
   */
  public static Date parse(long date) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER_LONG, Locale.getDefault());
    return dateFormat.parse(String.valueOf(date));
  }







  /**
   * 获取时区
   */
  public static String getTimeZone() {
    TimeZone tz = TimeZone.getDefault();
    return tz.getDisplayName(false, TimeZone.SHORT) + "  " + tz.getID();
  }

  public static String format(Calendar calendar, String pattern) {
    SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
    return format.format(calendar.getTime());
  }


  public static String format(Date date, String pattern) {
    SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
    return format.format(date.getTime());
  }







}
