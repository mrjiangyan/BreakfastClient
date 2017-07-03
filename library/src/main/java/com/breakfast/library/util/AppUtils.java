package com.breakfast.library.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.breakfast.library.data.entity.base.ApiHeader;

import java.io.File;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * 当前程序是否后台运行
 * 当前手机是否处于睡眠
 * 当前网络是否已连接
 * 当前网络是否wifi状态
 * 安装apk
 * 初始化view的高度
 * 初始化view的高度后不可见
 * 判断是否为手机
 * 获取屏幕宽度
 * 获取屏幕高度
 * 获取设备的IMEI
 * 获取设备的mac地址
 * 获取当前应用的版本号
 * 收集设备信息并以Properties返回
 * 收集设备信息并以String返回
 * <p>
 * <p>
 */
public class AppUtils {
  private static final String TAG = "AppUtils";
  /**
   * 使用Properties来保存设备的信息和错误堆栈信息
   */
  private static final String VERSION_NAME = "versionName";
  private static final String VERSION_CODE = "versionCode";
  //    private static final CountDownLatch signal = new CountDownLatch(1);
  public static ApiHeader header = null;
  /**
   * 获取设备mac地址
   *
   * @param context
   * @return
   * @author wangjie
   */
  private static String macAddress = null;
  private static long lastClickTime;
  private static String app_key, secure_key, app_id;

  public static boolean isApkDebugable(Context context, String packageName) {
    try {
      PackageInfo pkginfo = context.getPackageManager().getPackageInfo(packageName, 1);
      if (pkginfo != null) {
        ApplicationInfo info = pkginfo.applicationInfo;
        return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
      }
    } catch (Exception ignored) {

    }
    return false;
  }

  /**
   * 判断当前应用程序是否后台运行
   * 在android5.0以上失效！请使用isApplicationBackground()方法代替！
   */
  @TargetApi(Build.VERSION_CODES.CUPCAKE) @Deprecated public static boolean isBackground(
      Context context) {
    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningAppProcessInfo> appProcesses =
        activityManager.getRunningAppProcesses();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.processName.equals(context.getPackageName())) {
        return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
      }
    }
    return false;
  }

  /**
   * 判断当前应用程序处于前台还是后台
   * 需要添加权限: <uses-permission android:name="android.permission.GET_TASKS" />
   */
  public static boolean isApplicationBackground(final Context context) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
    if (!tasks.isEmpty()) {
      ComponentName topActivity = tasks.get(0).topActivity;
      if (!topActivity.getPackageName().equals(context.getPackageName())) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断手机是否处理睡眠
   */
  public static boolean isSleeping(Context context) {
    KeyguardManager kgMgr = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
    return kgMgr.inKeyguardRestrictedInputMode();
  }

  /**
   * 检查网络是否已连接
   *
   * @author com.tiantian
   */
  public static boolean isOnline(Context context) {
    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
    NetworkInfo info = manager.getActiveNetworkInfo();
    return (info != null && info.isConnected());
  }

  /**
   * 判断当前是否是wifi状态
   */
  public static boolean isWifiConnected(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    return (wifiNetworkInfo.isConnected());
  }

  /**
   * 安装apk
   */
  public static void installApk(Context context, File file) {
    Intent intent = new Intent();
    intent.setAction("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.setType("application/vnd.android.package-archive");
    intent.setData(Uri.fromFile(file));
    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  /**
   * 初始化View的高宽
   */
  @Deprecated public static void initViewWH(final View view) {
    view.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            System.out.println(
                view + ", width: " + view.getWidth() + "; height: " + view.getHeight());
          }
        });
  }

  /**
   * 初始化View的高宽并显示不可见
   */
  @Deprecated public static void initViewWHAndGone(final View view) {
    view.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            view.setVisibility(View.GONE);
          }
        });
  }

  /**
   * 判断是否为手机
   *
   * @author wangjie
   */
  public static boolean isPhone(Context context) {
    TelephonyManager telephony =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    int type = telephony.getPhoneType();
    if (type == TelephonyManager.PHONE_TYPE_NONE) {
      Log.i(TAG, "Current device is Tablet!");
      return false;
    } else {
      Log.i(TAG, "Current device is phone!");
      return true;
    }
  }

  /**
   * 获得设备的屏幕宽度
   */
  public static int getDeviceWidth(Context context) {
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    return manager.getDefaultDisplay().getWidth();
  }

  /**
   * 获得设备的屏幕高度
   */
  public static int getDeviceHeight(Context context) {
    // WindowManager manager = (WindowManager) context
    //        .getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics dm = new DisplayMetrics();
    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    //width = dm.widthPixels;
    //宽度
    // height = dm.heightPixels ;//高度
    return dm.heightPixels;
  }

  /**
   * 获取设备id（IMEI）
   *
   * @author wangjie
   */
  @SuppressLint("HardwareIds")
  @TargetApi(Build.VERSION_CODES.CUPCAKE) public static String getDeviceIMEI(Context context) {
    String deviceId;
    TelephonyManager telephony =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    deviceId = telephony.getDeviceId();
    if (deviceId == null) {
      deviceId =
          Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    return deviceId != null ? deviceId : "Unknown";
  }

  public static String getMacAddress(Context context) throws Exception {
    //        WifiManager wifi = (WifiManager) context
    //                .getSystemService(Context.WIFI_SERVICE);
    //        WifiInfo info = wifi.getConnectionInfo();
    //        macAddress = info.getMacAddress();
    //        Log.d(TAG, "当前mac地址: " + (null == macAddress ? "null" : macAddress));
    //        if (null == macAddress) {
    //            return "";
    //        }
    //        macAddress = macAddress.replace(":", "");
    /***
     * 获取以太网MAC
     *
     * @return String
     * @throws SocketException
     */
    if (macAddress == null) {
      macAddress = bytesToMac(NetworkInterface.getByInetAddress(
          new InetSocketAddress(getLocalIpAddress(), 0).getAddress()).getHardwareAddress());
    }
    return macAddress;
  }

  public static String bytesToMac(byte[] arr) {
    StringBuilder sb = new StringBuilder();
    if (arr != null) {
      for (int i = 0; i < arr.length; i++) {
        if (i > 0 && i < arr.length) {
          sb.append(":");
        }
        sb.append(String.format("%02x", Byte.valueOf(arr[i])));
      }
    }
    return sb.toString();
  }

  public static String getLocalIpAddress() {
    try {
      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
          en.hasMoreElements(); ) {
        NetworkInterface intf = en.nextElement();
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
            enumIpAddr.hasMoreElements(); ) {
          InetAddress inetAddress = enumIpAddr.nextElement();
          if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
            return inetAddress.getHostAddress();
          }
        }
      }
    } catch (SocketException ignored) {
    }
    return null;
  }

  /**
   * 获取当前应用程序的版本号
   *
   * @author wangjie
   */
  public static String getAppVersion(Context context) {
    String version = "0";
    try {
      version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return version;
  }

  /**
   * 收集设备信息
   */
  public static Properties collectDeviceInfo(Context context) {
    Properties mDeviceCrashInfo = new Properties();
    try {
      // Class for retrieving various kinds of information related to the
      // application packages that are currently installed on the device.
      // You can find this class through getPackageManager().
      PackageManager pm = context.getPackageManager();
      // getPackageInfo(String packageName, int flags)
      // Retrieve overall information about an application package that is installed on the system.
      // public static final int GET_ACTIVITIES
      // Since: API Level 1 PackageInfo flag: return information about activities in the package in activities.
      PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
      if (pi != null) {
        // public String versionName The version name of this package,
        // as specified by the <manifest> tag's versionName attribute.
        mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
        // public int versionCode The version number of this package,
        // as specified by the <manifest> tag's versionCode attribute.
        mDeviceCrashInfo.put(VERSION_CODE, Integer.valueOf(pi.versionCode));
      }
    } catch (PackageManager.NameNotFoundException e) {
      Log.e(TAG, "Error while collect package info", e);
    }
    // 使用反射来收集设备信息.在Build类中包含各种设备信息,
    // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
    // 返回 Field 对象的一个数组，这些对象反映此 Class 对象所表示的类或接口所声明的所有字段
    Field[] fields = Build.class.getDeclaredFields();
    for (Field field : fields) {
      try {
        // setAccessible(boolean flag)
        // 将此对象的 accessible 标志设置为指示的布尔值。
        // 通过设置Accessible属性为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异常
        field.setAccessible(true);
        mDeviceCrashInfo.put(field.getName(), field.get(null));
      } catch (Exception e) {
        Log.e(TAG, "Error while collect crash info", e);
      }
    }

    return mDeviceCrashInfo;
  }

  /**
   * 是否有SDCard
   */
  public static boolean haveSDCard() {
    return android.os.Environment.getExternalStorageState()
        .equals(android.os.Environment.MEDIA_MOUNTED);
  }

  /**
   * 隐藏软键盘
   */
  @TargetApi(Build.VERSION_CODES.CUPCAKE) public static void hideSoftInput(Activity activity) {
    View view = activity.getWindow().peekDecorView();
    if (view != null) {
      InputMethodManager inputmanger =
          (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
      inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  /**
   * 隐藏软键盘
   */
  @TargetApi(Build.VERSION_CODES.CUPCAKE) public static void hideSoftInput(Context context,
      EditText edit) {
    edit.clearFocus();
    InputMethodManager inputmanger =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
  }

  /**
   * 显示软键盘
   */
  @TargetApi(Build.VERSION_CODES.CUPCAKE) public static void showSoftInput(Context context,
      EditText edit) {
    edit.setFocusable(true);
    edit.setFocusableInTouchMode(true);
    edit.requestFocus();
    InputMethodManager inputManager =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.showSoftInput(edit, 0);
  }

  @TargetApi(Build.VERSION_CODES.CUPCAKE)
  public static void toggleSoftInput(Context context, EditText edit) {
    edit.setFocusable(true);
    edit.setFocusableInTouchMode(true);
    edit.requestFocus();
    InputMethodManager inputManager =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
  }

  /**
   * 回到home，后台运行
   */
  public static void goHome(Context context) {
    Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);

    mHomeIntent.addCategory(Intent.CATEGORY_HOME);
    mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    context.startActivity(mHomeIntent);
  }

  /**
   * 获取状态栏高度
   */
  @TargetApi(Build.VERSION_CODES.CUPCAKE) public static int getStatusBarHeight(Activity activity) {
    Rect frame = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    return frame.top;
  }

  /**
   * 获取状态栏高度＋标题栏高度
   */
  public static int getTopBarHeight(Activity activity) {
    return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
  }

  /**
   * 获取网络类型
   */
  public static int getNetworkType(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getNetworkType();
  }

  /**
   * MCC+MNC代码 (SIM卡运营商国家代码和运营商网络代码)
   * 仅当用户已在网络注册时有效, CDMA 可能会无效
   *
   * @return （中国移动：46000 46002, 中国联通：46001,中国电信：46003）
   */
  public static String getNetworkOperator(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getNetworkOperator();
  }

  /**
   * 返回移动网络运营商的名字(例：中国联通、中国移动、中国电信)
   * 仅当用户已在网络注册时有效, CDMA 可能会无效
   */
  public static String getNetworkOperatorName(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getNetworkOperatorName();
  }

  /**
   * 返回移动终端类型
   * PHONE_TYPE_NONE :0手机制式未知
   * PHONE_TYPE_GSM :1手机制式为GSM，移动和联通
   * PHONE_TYPE_CDMA :2手机制式为CDMA，电信
   * PHONE_TYPE_SIP:3
   */
  public static int getPhoneType(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    return telephonyManager.getPhoneType();
  }

  /**
   * Check if this device has a camera
   */
  public static boolean checkCameraHardware(Context context) {
    return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
  }

  /**
   * 获取渠道名称
   */
  public static String getChannelName(Context context) {
    String channel = null;
    try {
      ApplicationInfo applicationInfo = context.getPackageManager()
          .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
      if (applicationInfo != null && applicationInfo.metaData != null) {
        Object obj = applicationInfo.metaData.get("UMENG_CHANNEL");
        if (obj != null) channel = String.valueOf(obj);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return channel;
  }

  public static String getMetaValue(Context context, String metaKey) {
    Bundle metaData = null;
    Object value = null;
    if (context == null || metaKey == null) {
      return null;
    }

    try {
      ApplicationInfo ai = context.getPackageManager()
          .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
      if (null != ai) {
        metaData = ai.metaData;
      }
      if (null != metaData) {
        value = metaData.get(metaKey);
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    if (value != null) return value.toString();

    return null;
  }

  /**
   * 控制按钮连续点击
   */
  public static boolean isFastDoubleClick() {
    long time = System.currentTimeMillis();
    long timeD = time - lastClickTime;
    if (0 < timeD && timeD < 1000) {
      return true;
    }
    lastClickTime = time;
    return false;
  }

  /**
   * 获取app版本号
   *
   * @throws Exception
   */
  public static int getVersionCode(Context context) {
    int versionCode = 0;

    try {
      versionCode =
          context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    return versionCode;
  }

  // 获取AppKey
  public static String getAPPKey(Context context) {
    if (app_key == null) {
      app_key = getMetaValue(context, "ACCESS_KEY_ID");
    }
    return app_key;
  }

  // 获取AppKey
  public static String getAPPId(Context context) {
    if (app_id == null) {
      app_id = getMetaValue(context, "APP_ID");
    }
    return app_id;
  }

  // 获取SecureKey
  public static String getSecureKey(Context context) {
    if (secure_key == null) {
      secure_key = getMetaValue(context, "SECURE_KEY");
    }
    return secure_key;
  }

  public static ApiHeader getHeader(Context context) throws Exception {
    ApiHeader header = new ApiHeader();
    header.TimeZone = URLEncoder.encode(TimeUtils.getTimeZone(), "utf-8");
    header.PUSH_TOKEN_ID = SharedPreferenceUtils.getString("mJPushID");
    header.Brand = Build.BRAND;
    header.Channel = AppUtils.getChannelName(context);
    header.PackageName = context.getPackageName();
    header.HardwareSeriesNo = Build.SERIAL;
    header.IP = AppUtils.getLocalIpAddress();
    header.Manufacturer = Build.MANUFACTURER;
    header.Model = Build.MODEL;
    header.OSVersion = Build.VERSION.RELEASE;
    header.AppVersionCode = AppUtils.getVersionCode(context) + "";
    header.AppVersionName = AppUtils.getAppVersion(context);
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    header.ScreenHeight = point.y;
    header.ScreenWidth = point.x;
    header.AppID = AppUtils.getAPPId(context);
    DisplayMetrics metric = new DisplayMetrics();
    display.getMetrics(metric);
    header.Dpi = metric.densityDpi;// 屏幕密度DPI（120 / 160 / 240）
    return header;
  }

  public static boolean isTopActivity(Context context, String className) {
    ActivityManager am = (ActivityManager) context.getSystemService(Application.ACTIVITY_SERVICE);
    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
    return cn.getClassName().contains(className);
  }

  /**
   * 检测 响应某个意图的Activity 是否存在
   */
  public static boolean isIntentAvailable(Context context, Intent intent) {
    final PackageManager packageManager = context.getPackageManager();
    List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);
    return list.size() > 0;
  }
}
