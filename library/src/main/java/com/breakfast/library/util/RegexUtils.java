package com.breakfast.library.util;

import android.text.TextUtils;

import com.breakfast.library.R;

import java.util.regex.Pattern;

/**
 * 作者：ClareChen
 * 邮箱：wangyiclare@163.com
 * 时间：2015/12/9 9:49
 */
public class RegexUtils {

  /**
   * 手机号正则匹配
   */
  public static boolean isMobile(CharSequence mobile) {
    if (TextUtils.isEmpty(mobile)) {
      return false;
    }
    String value = "^1\\d{10}$";
    return Pattern.matches(value, mobile);
  }

  /**
   * 校验金额是否合法,逗号（,）也是不允许的
   *
   * @param decimal 是否允许包含小数，至多两位
   * @param signed 是否允许负数
   * @return 错误提示字符串的ResId, 为0则表示验证通过
   */
  public static int isMoney(String money, boolean decimal, boolean signed) {
    if (TextUtils.isEmpty(money)) {
      return -1;
    }

    // 校验是否包含非法字符
    try {
      Double.parseDouble(money);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return -2;
    }

    // 校验正负号
    if (!signed && money.contains("-")) {
      return -3;
    }

    // 校验小数，包括是否允许以及小数位数至多两位
    if (money.contains(".")) {
      if (!decimal) {
        return -4;
      } else {
        return Pattern.matches("^[-+]?[0-9]+([.]\\d{1,2})?$", money) ? 0 : -5;
      }
    }
    return 0;
  }

  /**
   * 判断金额是否在给定范围内
   *
   * @param min 最低限额（包含），不限制请传Integer.MIN_VALUE
   * @param max 最高限额（包含）,不限制请传Integer.MAX_VALUE
   * @return 错误提示字符串的ResId, 为0则表示验证通过
   */
  public static int isMoneyInRange(String money, boolean decimal, boolean signed, double min,
      double max) {

    // 先判断金额是否合法
    int isMoney = RegexUtils.isMoney(money, decimal, signed);
    if (isMoney != 0) {
      return isMoney;
    }

    // 判断金额范围
    double moneyD = Double.parseDouble(money);
    return moneyD >= min && moneyD <= max ? 0 : R.string.error_regex_money_out_of_range;
  }
}
