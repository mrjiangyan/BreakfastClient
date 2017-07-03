package com.breakfast.library.util;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by feng on 16/1/15.
 */
public class DecimalDigitsInputFilter implements InputFilter {
  private final int digitsBeforeZero;
  private final int digitsAfterZero;
  private boolean hasSymbol;

  public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
    this.digitsBeforeZero = digitsBeforeZero;
    this.digitsAfterZero = digitsAfterZero;
  }

  @Override
  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    int dotPos = -1;
    int len = dest.length();
    for (int i = 0; i < len; i++) {
      char c = dest.charAt(i);
      if (c == '-') {
        this.hasSymbol = true;
      }
      if (c == '.' || c == ',') {
        dotPos = i;
        break;
      }
    }

    /**
     * 禁止输入正号 "+"
     */
    if (source.equals("+")) {
      return "";
    }

    if (dotPos == -1) {
      if (!this.hasSymbol) {
        if (dest.length() < this.digitsBeforeZero) {
          return null;
        } else {
          if (source.equals(".")) {
            return null;
          } else {
            return "";
          }
        }
      } else {
        if (dest.length() < this.digitsBeforeZero + 1) {
          return null;
        } else {
          if (source.equals(".")) {
            return null;
          } else {
            return "";
          }
        }
      }
    } else if (dotPos >= 0) {

      // protects against many dots
      if (source.equals(".") || source.equals(",")) {
        return "";
      }
            /*// if the text is entered before the dot
            if (dend <= dotPos) {
                return null;
            }*/
      if (dest.length() == dend && dstart == 0) {
        return null;
      } else if (len - dotPos > this.digitsAfterZero) {
        return "";
      }
    }

    return null;
  }
}
