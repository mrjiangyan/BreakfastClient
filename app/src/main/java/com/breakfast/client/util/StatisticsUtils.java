package com.breakfast.client.util;

import android.content.Context;

import com.tendcloud.tenddata.TCAgent;

/**
 * Author：ClareChen
 * E-mail：ggchaifeng@gmail.com
 * Date：  16/9/1 下午2:08
 */
public class StatisticsUtils {

  public static final int TYPE_ACT = 0;
  public static final int TYPE_TOUCH = TYPE_ACT + 1;

  public static void act(Context context, String page, String action) {
    TCAgent.onEvent(context, String.format("act_%1$s_%2$s", page, action));
  }

  public static void touch(Context context, CharSequence page, String direction) {
    TCAgent.onEvent(context, String.format("touch_%1$s_%2$s", page, direction));
  }
}
