package com.breakfast.library.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.List;

public class NetworkUtil {

  /**
   * Unknown network class
   */
  public static final int NETWORK_CLASS_UNKNOWN = 0;

  /**
   * wifi net work
   */
  public static final int NETWORK_WIFI = 1;

  /**
   * "2G" networks
   */
  public static final int NETWORK_CLASS_2_G = 2;

  /**
   * "3G" networks
   */
  public static final int NETWORK_CLASS_3_G = 3;

  /**
   * "4G" networks
   */
  public static final int NETWORK_CLASS_4_G = 4;

  //网络是否处于可用状态
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivity =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity != null) {
      NetworkInfo[] info = connectivity.getAllNetworkInfo();
      if (info != null) {
        for (NetworkInfo anInfo : info) {
          if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
            return true;
          }
        }
      }
    }
    return false;
  }

  //GPS是否启用
  public static boolean isGpsEnabled(Context context) {
    LocationManager locationManager =
        ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
    List<String> accessibleProviders = locationManager.getProviders(true);
    return accessibleProviders != null && accessibleProviders.size() > 0;
  }

  //Wifi是否可用
  public static boolean isWifiEnabled(Context context) {
    ConnectivityManager mgrConn =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    TelephonyManager mgrTel =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return ((mgrConn.getActiveNetworkInfo() != null
        && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
        || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
  }

  //当前是否为无线的链接方式
  public static boolean isWifi(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
  }

  //当前是否为3G的链接方式
  public static boolean is3G(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
  }

  /**
   * 在中国，联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
   *
   * @return 2G、3G、4G、未知四种状态
   */
  public static int getNetWorkClass(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

    switch (telephonyManager.getNetworkType()) {
      case TelephonyManager.NETWORK_TYPE_GPRS:
      case TelephonyManager.NETWORK_TYPE_EDGE:
      case TelephonyManager.NETWORK_TYPE_CDMA:
      case TelephonyManager.NETWORK_TYPE_1xRTT:
      case TelephonyManager.NETWORK_TYPE_IDEN:
        return NETWORK_CLASS_2_G;

      case TelephonyManager.NETWORK_TYPE_UMTS:
      case TelephonyManager.NETWORK_TYPE_EVDO_0:
      case TelephonyManager.NETWORK_TYPE_EVDO_A:
      case TelephonyManager.NETWORK_TYPE_HSDPA:
      case TelephonyManager.NETWORK_TYPE_HSUPA:
      case TelephonyManager.NETWORK_TYPE_HSPA:
      case TelephonyManager.NETWORK_TYPE_EVDO_B:
      case TelephonyManager.NETWORK_TYPE_EHRPD:
      case TelephonyManager.NETWORK_TYPE_HSPAP:
        return NETWORK_CLASS_3_G;

      case TelephonyManager.NETWORK_TYPE_LTE:
        return NETWORK_CLASS_4_G;

      default:
        return NETWORK_CLASS_UNKNOWN;
    }
  }

  /**
   * 返回状态：当前的网络链接状态
   * 0：其他
   * 1：WIFI
   * 2：2G
   * 3：3G
   * 4：4G
   *
   * @return 没有网络，2G，3G，4G，WIFI
   */
  public static int getNetWorkStatus(Context context) {
    int netWorkType = NETWORK_CLASS_UNKNOWN;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

    if (networkInfo != null && networkInfo.isConnected()) {
      int type = networkInfo.getType();

      if (type == ConnectivityManager.TYPE_WIFI) {
        netWorkType = NETWORK_WIFI;
      } else if (type == ConnectivityManager.TYPE_MOBILE) {
        netWorkType = getNetWorkClass(context);
      }
    }

    return netWorkType;
  }
}
