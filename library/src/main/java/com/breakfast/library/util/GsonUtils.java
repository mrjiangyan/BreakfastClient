package com.breakfast.library.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Beyondhost on 2015/11/12.
 */
public class GsonUtils {

  private static Gson sGson = new Gson();

  public static String toJson(Object object) {
    return sGson.toJson(object);
  }

  public static <T> T fromJson(String json, Class<T> clazz) throws JsonParseException {
    return sGson.fromJson(json, clazz);
  }

  public static <T> T fromJson(String json, Type type) throws JsonParseException {
    return sGson.fromJson(json, type);
  }

  public static <T> List<T> toList(String s, Class<T[]> clazz) {
    T[] arr = sGson.fromJson(s, clazz);
    if (arr != null) {
      return new ArrayList<>(Arrays.asList(arr));
    } else {
      return new ArrayList<>(); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }
  }
}
