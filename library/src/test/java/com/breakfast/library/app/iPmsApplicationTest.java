package com.breakfast.library.app;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.breakfast.library.Common.BaseTestCase;
import com.breakfast.library.data.entity.constants.MetaDataKeys;
import com.breakfast.library.util.AppUtils;

import junit.framework.Assert;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import okhttp3.OkHttpClient;


public class iPmsApplicationTest extends BaseTestCase {


    @Test
    public void canCreateAClient() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Assert.assertNotNull(client);
    }


    @Test
    public void testExit() throws Exception {
        Application application = RuntimeEnvironment.application;
        ApplicationInfo ai = application.getPackageManager()
                .getApplicationInfo(application.getPackageName(),
                        PackageManager.GET_META_DATA);
        Bundle metaData = null;
        if (null != ai) {
            metaData = ai.metaData;
        }
        Assert.assertNotNull(metaData);
        String value = AppUtils.getMetaValue(application, MetaDataKeys.HOST);
        Assert.assertNotNull(value);
    }

    @Test
    public void testChangeRoomStatus() throws Exception {

       /* Application application = RuntimeEnvironment.application;
        final Call<ApiResponse<>> baseResponseCall = ServiceFactory.generateService(application, IRoomService.class).changeRoomStatus("2001", "VD", "2016-8-11");

        Response<ApiResponse<String>> response = baseResponseCall.execute();
        LogUtils.d("onResponse");
        if (!response.isSuccessful()) {
            try {
                LogUtils.d(response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogUtils.d(response.body());
        }

        LogUtils.d("End");*/
    }
}