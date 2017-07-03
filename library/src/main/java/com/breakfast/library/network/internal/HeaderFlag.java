package com.breakfast.library.network.internal;

/**
 * Created by Steven on 16/8/4.
 */
public final class HeaderFlag {
  /// <summary>
  /// 语言
  /// </summary>
  public static final String Language = "language";
  /// <summary>
  /// 协议版本
  /// </summary>
  public static final String Version = "version";

  public static final String AccessKeyId = "accessKeyId";
  /// <summary>
  /// 认证签名
  /// </summary>
  public static final String Authorization = "authorization";
   /// <summary>
  /// 公共的请求头数据，标识了设备以及应用的一些数据
  /// </summary>
  public static final String ApiHeader = "apiheader";
  // 用户认证信息
  public static final String UserToken = "token";
  //请求编号
  public static final String RequestId = "requestId";

  public static final String RequestVersion = "V1";

  public static final String Accept = "accept";

  public static final String ContentLength = "Content-Length";
}
