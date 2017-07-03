package com.breakfast.library.network.internal;

public class ApiException extends RuntimeException {

  /**
   * 未知错误
   */
  public static final int ERR_UNKNOWN = 1000;
  /**
   * 数据错误
   */
  public static final int ERR_PARSE = 1001;
  /**
   * 网络连接出错
   */
  public static final int ERR_NETWORK = 1002;

  private final int code;

  public ApiException(Throwable throwable, int code) {
    super(throwable);
    this.code = code;
  }

  public ApiException(int errCode, String errMsg) {
    super(errMsg);
    this.code = errCode;
  }

  public ApiException(Throwable throwable, int code, String errMsg) {
    super(errMsg, throwable);
    this.code = code;
  }

  public int getCode() {
    return code;
  }


}