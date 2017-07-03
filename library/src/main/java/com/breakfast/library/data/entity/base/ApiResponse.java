package com.breakfast.library.data.entity.base;

/**
 * Created by Steven on 16/8/4.
 */


public class ApiResponse<T> {

  String message;
  T data;
  int code;


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public boolean isOk() {
    return code==0;
  }

}
