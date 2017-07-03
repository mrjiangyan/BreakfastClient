package com.breakfast.library.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.StringRes;

import com.breakfast.library.app.BaseApplication;

import java.util.Collections;
import java.util.Set;

/**
 * Created by Beyondhost on 2015/11/12.
 * 存取软件配置和当前用户账户配置
 */
public class SharedPreferenceUtils {

  private static SharedPreferences sp;

  private static SharedPreferences getPreferences() {
    if (SharedPreferenceUtils.sp == null) {
      SharedPreferenceUtils.sp =
          PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
    }
    return SharedPreferenceUtils.sp;
  }

  /**
   * 存储软件配置
   */
  public static void saveConfig(@StringRes int keyRes, String value) {

    String key = ResourceUtils.getString(keyRes);
    getPreferences().edit().putString(key, value).commit();
  }

  /**
   * 存储软件配置
   */
  public static void saveConfig(@StringRes int keyRes, Set<String> value) {

    String key = ResourceUtils.getString(keyRes);
    getPreferences().edit().putStringSet(key, value).commit();
  }

  /**
   * 存储软件配置
   */
  public static void saveConfig(@StringRes int keyRes, boolean value) {

    String key = ResourceUtils.getString(keyRes);
    getPreferences().edit().putBoolean(key, value).commit();
  }

  /**
   * 存储软件配置
   */
  public static void saveConfig(@StringRes int keyRes, int value) {

    String key = ResourceUtils.getString(keyRes);
    getPreferences().edit().putInt(key, value).commit();
  }

  /**
   * 存储软件配置
   */
  public static void saveConfig(@StringRes int keyRes, float value) {

    String key = ResourceUtils.getString(keyRes);
    getPreferences().edit().putFloat(key, value).commit();
  }

  /**
   * 存储软件配置
   */
  public static void saveConfig(@StringRes int keyRes, long value) {

    String key = ResourceUtils.getString(keyRes);
    SharedPreferenceUtils.getPreferences().edit().putLong(key, value).commit();
  }

  /**
   * 获取软件的配置设置
   */
  public static String getString(@StringRes int keyRes) {
    return getString(ResourceUtils.getString(keyRes));
  }

  /**
   * 获取软件的配置设置
   */
  public static String getString(String key) {
    return getPreferences().getString(key, "");
  }

  /**
   * 获取软件的配置设置
   */
  public static int getInt(@StringRes int keyRes) {
    String key = ResourceUtils.getString(keyRes);
    return SharedPreferenceUtils.getPreferences().getInt(key, Integer.MIN_VALUE);
  }

  /**
   * 获取软件的配置设置
   */
  public static int getInt(@StringRes int keyRes, int def) {
    String key = ResourceUtils.getString(keyRes);
    return SharedPreferenceUtils.getPreferences().getInt(key, def);
  }

  /**
   * 获取软件的配置设置
   */
  public static long getLong(@StringRes int keyRes) {
    String key = ResourceUtils.getString(keyRes);
    return SharedPreferenceUtils.getPreferences().getLong(key, Long.MIN_VALUE);
  }

  /**
   * 获取软件的配置设置
   */
  public static boolean getBoolean(@StringRes int keyRes, boolean defaultValue) {
    String key = ResourceUtils.getString(keyRes);
    return SharedPreferenceUtils.getPreferences().getBoolean(key, defaultValue);
  }

  /**
   * 获取软件的配置设置
   */
  public static Set<String> getStringSet(@StringRes int keyRes) {
    String key = ResourceUtils.getString(keyRes);
    return SharedPreferenceUtils.getPreferences().getStringSet(key, Collections.emptySet());
  }

  /**
   * 获取软件的配置设置
   */
  public static float getFloat(@StringRes int keyRes) {
    String key = ResourceUtils.getString(keyRes);
    return SharedPreferenceUtils.getPreferences().getFloat(key, Float.MIN_VALUE);
  }
}
