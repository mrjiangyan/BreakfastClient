package com.breakfast.library.network.internal;

import com.breakfast.library.data.entity.base.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Steven on 16/8/6.
 */
public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final Type type;

  JsonResponseBodyConverter(Gson gson, Type type) {
    this.gson = gson;
    this.type = type;
  }

  @Override public T convert(ResponseBody value) throws IOException {
    String response = value.string();

    try {
      ApiResponse resultResponse = gson.fromJson(response, ApiResponse.class);
      if (resultResponse.isOk()) {
        return gson.fromJson(response, type);
      }
      throw new ApiException(resultResponse.getCode(), resultResponse.getMessage());
    } catch (JsonParseException err) {
      throw new ApiException(0, response);
    }
  }
}
