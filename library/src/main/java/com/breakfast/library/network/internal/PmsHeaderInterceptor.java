package com.breakfast.library.network.internal;

import android.content.Context;
import android.util.Base64;

import com.breakfast.library.R;
import com.breakfast.library.app.BaseApplication;
import com.breakfast.library.data.entity.user.User;
import com.breakfast.library.util.AppUtils;
import com.breakfast.library.util.GsonUtils;
import com.breakfast.library.util.HMCUtils;
import com.breakfast.library.util.SecurityUtil;
import com.breakfast.library.util.SharedPreferenceUtils;
import com.breakfast.library.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

import static okio.Okio.sink;

/**
 * Created by Steven on 16/8/4.
 */
public class PmsHeaderInterceptor implements Interceptor {

    private String token;
    private final Context context;

    public PmsHeaderInterceptor(Context context) {
        this.context = context;
        token = SharedPreferenceUtils.getString(R.string.pms_token);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        RequestBody body = originalRequest.body();

        String contentLength = body == null ? "0" : String.valueOf(body.contentLength());
        Request.Builder builder = originalRequest.newBuilder();
        try {
            builder = builder.header(HeaderFlag.Language, getLanguage())
                    .header(HeaderFlag.Cookie, token)
                    .header(HeaderFlag.Version, HeaderFlag.RequestVersion)
                    .header(HeaderFlag.ContentLength, contentLength)
                    .header(HeaderFlag.Accept, "application/json");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chain.proceed(builder.build());
    }

    public String getLanguage() {
        return "zh-cn";
    }
}
