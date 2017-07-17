package com.breakfast.client.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;

import com.apkfuns.logutils.LogUtils;
import com.breakfast.client.util.DialogUtils;
import com.breakfast.library.data.entity.app.AppCheck;
import com.breakfast.library.util.AppUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Environment.MEDIA_MOUNTED;
import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStorageState;

/**
 * Created by Steven on 2017/6/1.
 */

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class DownloadTask extends AsyncTask<AppCheck, Integer, String> {


    private static PowerManager.WakeLock mWakeLock;

    public static boolean isRun()
    {
        if(mWakeLock== null)
            return false;
        return mWakeLock.isHeld();
    }


    public static DownloadTask getInstance(Context context)
    {
        return new DownloadTask(context);
    }

    private static final int REQUEST_CODE_PERMISSION_SD = 101;

    private static final int REQUEST_CODE_SETTING = 300;
    private static final String DOWNLOAD_NAME = "app.apk";

    private AppCheck entity;


    private Context context;
    private  File file = null;


    private DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(AppCheck... entity) {
        this.entity=entity[0];
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(this.entity.appUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            // expect HTTP 200 OK, so we don't mistakenly save error
            // report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP "
                        + connection.getResponseCode() + " "
                        + connection.getResponseMessage();
            }
            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();
            if (getExternalStorageState().equals(
                    MEDIA_MOUNTED)) {
                file = new File(getExternalStorageDirectory(),
                        DOWNLOAD_NAME);

                if (!file.exists()) {
                    // 判断父文件夹是否存在
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                }

            } else {
                DialogUtils.showToast(context, "sd卡未挂载", DialogUtils.ToastType.error);

            }
            input = connection.getInputStream();
            output = new FileOutputStream(file);
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);

            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return e.toString();

        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        LogUtils.d(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        if (result != null) {
            DialogUtils.showToast(context, "您未打开SD卡权限,无法进行应用升级", DialogUtils.ToastType.error);
        } else {
            AppUtils.installApk(context, file);


        }

    }


}