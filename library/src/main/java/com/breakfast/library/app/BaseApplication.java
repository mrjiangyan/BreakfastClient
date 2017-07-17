package com.breakfast.library.app;

import android.app.Application;
import android.os.Build;

import com.apkfuns.logutils.LogUtils;
import com.breakfast.library.data.entity.constants.MetaDataKeys;
import com.tendcloud.tenddata.TCAgent;
import com.breakfast.library.BuildConfig;
import com.breakfast.library.util.AppUtils;

import static rx.internal.util.RxJavaPluginUtils.handleException;


/**
 * Created by Steven on 16/8/3.
 */
public class BaseApplication extends Application implements
        Thread.UncaughtExceptionHandler {

  private static BaseApplication sInstance;


  @Override public void onCreate() {
    super.onCreate();
    sInstance = this;
    //设置Thread Exception Handler
    Thread.setDefaultUncaughtExceptionHandler(this);



    TCAgent.init(this, AppUtils.getMetaValue(this, MetaDataKeys.TD_APP_ID),
        AppUtils.getMetaValue(this, MetaDataKeys.TD_CHANNEL_ID));
    TCAgent.setReportUncaughtExceptions(true);
    TCAgent.LOG_ON = LogUtils.configAllowLog = BuildConfig.DEBUG;
    TCAgent.setPushDisabled();
    TCAgent.setGlobalKV("HardwareSeriesNo", Build.SERIAL);
  }

  public static BaseApplication getInstance() {
    return sInstance;
  }


  /**
   * 当UncaughtException发生时会转入该重写的方法来处理
   */
  public void uncaughtException(Thread thread, Throwable ex) {
    ex.printStackTrace();
    android.os.Process.killProcess(android.os.Process.myPid());
    System.exit(0);

  }
}
