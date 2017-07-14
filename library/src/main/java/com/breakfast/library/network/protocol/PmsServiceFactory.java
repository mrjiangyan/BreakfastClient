package com.breakfast.library.network.protocol;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.breakfast.library.app.BaseApplication;
import com.breakfast.library.network.internal.PmsHeaderInterceptor;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Steven on 16/8/4.
 */
public final class PmsServiceFactory {

    private static final String CLIENT_PASSWORD = "steven";
    private static final String CERTIFICATE = "";
    private static Retrofit sRetrofit;

    private PmsServiceFactory() {
    }

    public static <T> T generateService(Class<T> clazz) {
        synchronized (PmsServiceFactory.class) {
            return generateService(null,BaseApplication.getInstance(), clazz);
        }
    }

    public static <T> T generateService(String url,Context applicationContext, Class<T> clazz) {
        synchronized (PmsServiceFactory.class) {
            if (sRetrofit == null) {
                sRetrofit = getRetrofit(url,applicationContext);
            }
            return sRetrofit.create(clazz);
        }
    }

    public static <T> T generateService(String url, Class<T> clazz) {
        synchronized (PmsServiceFactory.class) {
            return getRetrofit(url).create(clazz);
        }
    }

    private static Retrofit getRetrofit(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().retryOnConnectionFailure(true);
        builder = builder.readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS);

        // 日志
        if (LogUtils.configAllowLog) {
            // Log信息
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder = builder.addInterceptor(loggingInterceptor);
        }
        final OkHttpClient client = builder.build();

        sRetrofit = new Retrofit.Builder().baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return sRetrofit;
    }

    private static Retrofit getRetrofit(final String host, Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                .addInterceptor(new PmsHeaderInterceptor(context));
        builder = builder.readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS);

        String baseUrl = host;

        if (baseUrl.toLowerCase().startsWith("https")) {
            TrustManager[] trustManagers;
            SSLSocketFactory sslSocketFactory;
            try {
                trustManagers =
                        trustManagerForCertificates(new Buffer().writeUtf8(CERTIFICATE).inputStream());
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustManagers, null);
                sslSocketFactory = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagers[0]);
        }
        // 日志
        if (LogUtils.configAllowLog) {
            // Log信息
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder = builder.addInterceptor(loggingInterceptor);
        }
        final OkHttpClient client = builder.build();

        sRetrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return sRetrofit;
    }


    private static Gson gson;

    public static synchronized Gson getGson() {
        if (gson == null) {
            JsonDeserializer<Date> dateJsonDeserializer =
                    (json, typeOfT, context) -> json == null ? null : new Date(json.getAsLong());
            JsonSerializer<Date> dateJsonSeserializer = (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.getTime());

            gson = new GsonBuilder().registerTypeAdapter(Date.class, dateJsonDeserializer)
                    .registerTypeAdapter(Date.class, dateJsonSeserializer)
                    .setLenient()
                    // 反序列化时不忽略任何字段
                    .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return false;
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    }).create();
        }

        return gson;
    }

    private static TrustManager[] trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = CLIENT_PASSWORD.toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore();
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException(
                    "Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        return trustManagers;
    }

    private static KeyStore newEmptyKeyStore() throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
