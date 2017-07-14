package com.breakfast.library.network.internal;

import android.content.Context;
import android.util.Base64;

import com.breakfast.library.app.BaseApplication;
import com.breakfast.library.data.entity.user.User;
import com.breakfast.library.util.AppUtils;
import com.breakfast.library.util.GsonUtils;
import com.breakfast.library.util.HMCUtils;
import com.breakfast.library.util.SecurityUtil;
import com.breakfast.library.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

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

    private String accessKeyId;
    private String secureKey;
    private final Context context;

    public PmsHeaderInterceptor(Context context) {
        this.context = context;
        accessKeyId = AppUtils.getAPPKey(context);
        secureKey = AppUtils.getSecureKey(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();


        StringBuilder sb = new StringBuilder();
        for (Object o : originalRequest.url().queryParameterNames()) {
            String key = o.toString();
            try {
                sb.append(URLEncoder.encode(key, "utf-8"));
                sb.append("=");
                sb.append(originalRequest.url().queryParameter(key));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //获取请求的内容
        String method = originalRequest.method().toUpperCase();

        RequestBody body = originalRequest.body();
        String bodyMd5 = "";
        if (body != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            BufferedSink bufferedSink = Okio.buffer(sink(stream));
            //写入
            body.writeTo(bufferedSink);
            bufferedSink.flush();
            if (stream.size() > 0)
                try {
                    bodyMd5 = HMCUtils.getMD5(stream.toByteArray());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
        }
        String CanonicalRequest = StringUtils.fillStringByArgs("{0}{1}{2}{3}",
                new String[]{method, originalRequest.url().encodedPath(), sb.toString(), bodyMd5});
        String requestId = UUID.randomUUID().toString();
        String token = getToken();
        String authStringPrefix = StringUtils.fillStringByArgs("{0}{1}{2}", new String[]{
                HeaderFlag.RequestVersion, accessKeyId, token});
        byte[] signingKey = HMCUtils.encryptHMAC2Bytes(authStringPrefix, secureKey);

        String signature = Base64.encodeToString(HMCUtils.encryptHMAC(CanonicalRequest.getBytes("UTF-8"), signingKey),
                Base64.NO_WRAP);


        String contentLength = body == null ? "0" : String.valueOf(body.contentLength());
        Request.Builder builder = originalRequest.newBuilder();
        try {
            builder = builder.header(HeaderFlag.UserToken, getToken())
                    .header(HeaderFlag.Language, getLanguage())
                    .header(HeaderFlag.AccessKeyId, accessKeyId)
                    .header(HeaderFlag.Version, HeaderFlag.RequestVersion)
                    .header(HeaderFlag.ApiHeader, GsonUtils.toJson(AppUtils.getHeader(context)))
                    .header(HeaderFlag.UserToken, token)
                    .header(HeaderFlag.RequestId, requestId)
                    .header(HeaderFlag.Authorization, signature)
                    .header(HeaderFlag.ContentLength, contentLength)
                    .header(HeaderFlag.Accept, "application/json");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chain.proceed(builder.build());
    }

    public String getToken() {
        User user = SecurityUtil.getCurrentUser(BaseApplication.getInstance());
        if (user != null)
            return user.getToken();
        return "";
    }

    public String getLanguage() {
        return "zh-cn";
    }
}
