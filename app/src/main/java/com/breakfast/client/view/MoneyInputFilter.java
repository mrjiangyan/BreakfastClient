package com.breakfast.client.view;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.SoundEffectConstants;
import android.view.View;

/**
 * Author：ClareChen
 * E-mail：ggchaifeng@gmail.com
 * Date：  16/8/12 下午12:11
 */
class MoneyInputFilter implements InputFilter {

  private final View mView;

  public MoneyInputFilter(View view) {
    if (view == null) {
      throw new IllegalArgumentException("view can't be null!!!");
    }
    this.mView = view;
    mView.setSoundEffectsEnabled(true);
  }

  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    String temp = dest + source.toString();
    if (temp.equals(".")) {
      return "0.";
    } else if (!temp.contains(".")) {
      int beforeDecimal = 5;
      int maxLength = temp.contains("-") ? beforeDecimal + 1 : beforeDecimal;
      if (temp.length() > maxLength) {
        mView.playSoundEffect(SoundEffectConstants.CLICK);
        return "";
      }
    } else {
      temp = temp.substring(temp.indexOf(".") + 1);
      int afterDecimal = 2;
      if (temp.length() > afterDecimal) {
        mView.playSoundEffect(SoundEffectConstants.CLICK);
        return "";
      }
    }
    return null;
  }
}
