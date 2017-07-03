package com.breakfast.library.data.source.impl;

import com.breakfast.library.data.entity.base.ApiResponse;
import com.breakfast.library.network.internal.ApiException;

import rx.functions.Func1;

public class ApiResponseFunc<T> implements Func1<ApiResponse<T>, T> {

  /**
   * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
   *
   * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
   */
  @Override public T call(ApiResponse<T> httpResult) {
    if (!httpResult.isOk()) {
      throw new ApiException(httpResult.getCode(), httpResult.getMessage());
    }

    return httpResult.getData();
  }
}
