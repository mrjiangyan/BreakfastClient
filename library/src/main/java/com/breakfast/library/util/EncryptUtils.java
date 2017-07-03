package com.breakfast.library.util;

import android.util.Base64;

import com.breakfast.library.common.RC4;

/**
 * Created by Steven on 2017/5/24.
 */

public class EncryptUtils {

    public static final String TAG = "EncryptUtils";
    public static String RC4_KEY = "jiangtao2016";

    public static String encoderJson(String json) {
        byte[] buffer = RC4.encry_RC4_byte(json, RC4_KEY);
        String baseResult = Base64.encodeToString(buffer, Base64.DEFAULT);
        //String baseResult = Base64.getEncoder().encodeToString(buffer);
        return baseResult;
    }

    public static String encoderJson(String json, String key) {
        byte[] buffer = RC4.encry_RC4_byte(json, key);
        String baseResult = Base64.encodeToString(buffer, Base64.DEFAULT);
        return baseResult;
    }

    public static String decoderJson(String base64) {
        byte[] decoder = Base64.decode(base64, Base64.DEFAULT);
        String decoderRaw = RC4.decry_RC4(decoder, RC4_KEY);
        return decoderRaw;
    }


    public static String decoderJson(String base64, String key) {
        byte[] decoder = Base64.decode(base64, Base64.DEFAULT);
        String decoderRaw = RC4.decry_RC4(decoder, key);
        return decoderRaw;
    }

}