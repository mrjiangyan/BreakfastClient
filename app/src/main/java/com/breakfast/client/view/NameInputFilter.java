package com.breakfast.client.view;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

/**
 * Author：ClareChen
 * E-mail：ggchaifeng@gmail.com
 * Date：  16/8/12 下午12:11
 */
public class NameInputFilter implements InputFilter {

  private final String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    if (Pattern.matches(regEx, source)) {
      return "";
    }
    return null;
  }
}
