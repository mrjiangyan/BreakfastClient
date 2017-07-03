package com.breakfast.library.util;

import java.math.BigDecimal;

/**
 * 作者：ClareChen
 * 邮箱：wangyiclare@163.com
 * 时间：2015/11/16 17:19
 */
public class NumberUtils {

  /**
   * 去除double尾数后面无用的0
   */
  public static String getIntString(double number) {
    String tmp = String.valueOf(number);
    while (tmp.endsWith("0")) {
      tmp = tmp.substring(0, tmp.length() - 1);
    }
    if (tmp.endsWith(".")) {
      tmp = tmp.substring(0, tmp.length() - 1);
    }
    return tmp;
  }

  // 默认除法运算精度
  private static final int DEF_DIV_SCALE = 10;

  // 这个类不能实例化
  private NumberUtils() {
  }

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
  public static double mul(double v1, double v2) {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.multiply(b2).doubleValue();
  }

  /**
   * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
   * 小数点以后 10 位，以后的数字四舍五入。
   *
   * @param v1 被除数
   * @param v2 除数
   * @return 两个参数的商
   */
  public static double div(double v1, double v2) {
    return div(v1, v2, DEF_DIV_SCALE);
  }

  /**
   * 提供（相对）精确的除法运算。当发生除不尽的情况时，由 scale 参数指
   * 定精度，以后的数字四舍五入。
   *
   * @param v1 被除数
   * @param v2 除数
   * @param scale 表示表示需要精确到小数点以后几位。
   * @return 两个参数的商
   */
  public static double div(double v1, double v2, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
  }

  /**
   * 提供精确的小数位四舍五入处理。
   *
   * @param v 需要四舍五入的数字
   * @param scale 小数点后保留几位
   * @return 四舍五入后的结果
   */
  public static double round(double v, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
  }

  /**
   * 除法并取余数
   *
   * @param v1 被除数
   * @param v2 除数
   * @return double[0] 商，double[1] 余数
   */
  public static double[] div2(double v1, double v2) {
    double[] doubles = new double[2];
    if (v2 == 0) {
      doubles[0] = v1;
      doubles[1] = 0;
      return doubles;
    }
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    BigDecimal[] bigDecimals = b1.divideAndRemainder(b2);
    doubles[0] = bigDecimals[0].doubleValue();
    doubles[1] = bigDecimals[1].doubleValue();
    //        LogUtils.i("被除数：" + v1 + ";除数：" + v2 + ";商：" + doubles[0] + ";余数：" + doubles[1]);
    return doubles;
  }

  /**
   * 判断两个double是否相等
   */
  public static int compare(double v1, double v2) {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.compareTo(b2);
  }


  public static boolean isNumeric(CharSequence str){
    for (int i = str.length(); i >=0;){
      if (!Character.isDigit(str.charAt(i))){
        return false;
      }
      --i;
    }
    return true;
  }
}
