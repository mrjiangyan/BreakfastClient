package com.breakfast.library.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */
public class StringUtils {



  private StringUtils() {
    throw new AssertionError();
  }

  /**
   * is null or its length is 0 or it is made by space
   * <p/>
   * <pre>
   * isBlank(null) = true;
   * isBlank(&quot;&quot;) = true;
   * isBlank(&quot;  &quot;) = true;
   * isBlank(&quot;a&quot;) = false;
   * isBlank(&quot;a &quot;) = false;
   * isBlank(&quot; a&quot;) = false;
   * isBlank(&quot;a b&quot;) = false;
   * </pre>
   *
   * @return if string is null or its size is 0 or it is made by space, return true, else return
   * false.
   */
  public static boolean isBlank(String str) {
    return (str == null || str.trim().length() == 0);
  }

  /**
   * is null or its length is 0
   * <p/>
   * <pre>
   * isEmpty(null) = true;
   * isEmpty(&quot;&quot;) = true;
   * isEmpty(&quot;  &quot;) = false;
   * </pre>
   *
   * @return if string is null or its size is 0, return true, else return false.
   */
  public static boolean isEmpty(CharSequence str) {
    return (str == null || str.length() == 0);
  }

  /**
   * get length of CharSequence
   * <p/>
   * <pre>
   * length(null) = 0;
   * length(\"\") = 0;
   * length(\"abc\") = 3;
   * </pre>
   *
   * @return if str is null or empty, return 0, else return {@link CharSequence#length()}.
   */
  public static int length(CharSequence str) {
    return str == null ? 0 : str.length();
  }





   //占位符替换
  public static String fillStringByArgs(String str, String[] arr) {
    Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(str);
    while (m.find()) {
      String newValue = arr[Integer.parseInt(m.group(1))];
      if (newValue == null) newValue = "";
      str = str.replace(m.group(), newValue);
    }
    return str;
  }

  public static  String bytesToHexString(byte[] src) {
    return bytesToHexString(src, true);
  }

  public static String bytesToHexString(byte[] src, boolean isPrefix) {
    StringBuilder stringBuilder = new StringBuilder();
    if (isPrefix == true) {
      stringBuilder.append("0x");
    }
    if (src == null || src.length <= 0) {
      return null;
    }
    char[] buffer = new char[2];
    for (int i = 0; i < src.length; i++) {
      buffer[0] = Character.toUpperCase(Character.forDigit(
              (src[i] >>> 4) & 0x0F, 16));
      buffer[1] = Character.toUpperCase(Character.forDigit(src[i] & 0x0F,
              16));
      stringBuilder.append(buffer);
    }
    return stringBuilder.toString();
  }


}
