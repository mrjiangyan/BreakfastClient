package com.breakfast.library.data.entity.base;

import java.io.Serializable;

/**
 * Created by Steven on 16/8/4.
 */
public class ApiHeader implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 800846228120215096L;

  /// <summary>
  /// 屏幕高度
  /// </summary>
  public int ScreenHeight;
  /// <summary>
  /// 屏幕宽度
  /// </summary>
  public int ScreenWidth;
  /// <summary>
  /// 屏幕点距
  /// </summary>
  public int Dpi;
  /// <summary>
  /// 应用版本名称
  /// </summary>
  public String AppVersionName;
  /// <summary>
  /// 应用版本代码
  /// </summary>
  public String AppVersionCode;
  /// <summary>
  /// 系统版本号
  /// </summary>
  public String OSVersion;

  /// <summary>
  /// 系统IP
  /// </summary>
  public String IP;
  /// <summary>
  /// 硬件序列号
  /// </summary>
  public String HardwareSeriesNo;

  /// <summary>
  /// 用户会话凭据，通过该凭据来验证当前用户的会话是否有效
  /// </summary>
  //    public String UserToken;

  /// <summary>
  /// 应用ID，每一个应用ID都会和一个平台关联，并且可以去控制该应用是否可用
  /// </summary>
  public String AppID;

  // / <summary>
  // / 设备品牌
  // / </summary>
  public String Brand;

  // / <summary>
  // / 设备的制造商
  // / </summary>
  public String Manufacturer;

  // / <summary>
  // / 设备型号
  // / </summary>
  public String Model;

  // / <summary>
  // / 应用的发放渠道
  // / </summary>
  public String Channel;

  // / <summary>
  // / 系统包名称
  // / </summary>
  public String PackageName;

  //客户端的推送ID
  public String PUSH_TOKEN_ID;

  //时区  格林尼治标准时间+0800 Asia/Shanghai
  public String TimeZone;

  @Override public String toString() {
    return "{" +
        "\"ScreenHeight\":" + ScreenHeight +
        ", \"ScreenWidth\":" + ScreenWidth +
        ", \"Dpi\":" + Dpi +
        ", \"AppVersionName\":\"" + AppVersionName + '\"' +
        ", \"AppVersionCode\":\"" + AppVersionCode + '\"' +
        ", \"OSVersion\":\"" + OSVersion + '\"' +
        ", \"IP\":\"" + IP + '\"' +
        ", \"HardwareSeriesNo\":\"" + HardwareSeriesNo + '\"' +
        ", \"AppID\":\"" + AppID + '\"' +
        ", \"Brand\":\"" + Brand + '\"' +
        ", \"Manufacturer\":\"" + Manufacturer + '\"' +
        ", \"Model\":\"" + Model + '\"' +
        ", \"Channel\":\"" + Channel + '\"' +
        ", \"PackageName\":\"" + PackageName + '\"' +
        ", \"PUSH_TOKEN_ID\":\"" + PUSH_TOKEN_ID + '\"' +
        ", \"TimeZone\":\"" + TimeZone + '\"' +
        '}';
  }
}