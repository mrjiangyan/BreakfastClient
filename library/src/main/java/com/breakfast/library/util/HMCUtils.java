package com.breakfast.library.util;

import android.text.TextUtils;


import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Decoder.BASE64Encoder;

/**
 * Created by Beyondhost on 2015/10/30.
 */
public class HMCUtils {



  public static String getMD5(byte[] data) throws NoSuchAlgorithmException {
    MessageDigest md5=MessageDigest.getInstance("MD5");
    BASE64Encoder base64en = new BASE64Encoder();
    //加密后的字符串
    return base64en.encode(md5.digest(data));
  }

  /**
   * 定义加密方式
   * MAC算法可选以下多种算法
   * <pre>
   * HmacMD5
   * HmacSHA1
   * HmacSHA256
   * HmacSHA384
   * HmacSHA512
   * </pre>
   */
  private final static String KEY_MAC = "HmacSHA256";

  /**
   * 全局数组
   */
  private final static String[] hexDigits = {
      "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
  };

  /**
   * HMAC加密
   *
   * @param data 需要加密的字节数组
   * @param key 密钥
   * @return 字节数组
   */
  public static byte[] encryptHMAC(byte[] data, String key) {
    SecretKey secretKey;
    byte[] bytes = null;
    try {
      secretKey = new SecretKeySpec(key.getBytes("UTF-8"), KEY_MAC);
      Mac mac = Mac.getInstance(secretKey.getAlgorithm());
      mac.init(secretKey);
      bytes = mac.doFinal(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bytes;
  }

  /**
   * HMAC加密
   *
   * @param data 需要加密的字节数组
   * @param key 密钥
   * @return 字节数组
   */
  public static byte[] encryptHMAC(byte[] data, byte[] key) {
    SecretKey secretKey;
    byte[] bytes = null;
    try {
      secretKey = new SecretKeySpec(key, KEY_MAC);
      Mac mac = Mac.getInstance(secretKey.getAlgorithm());
      mac.init(secretKey);
      bytes = mac.doFinal(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bytes;
  }

  /**
   * HMAC加密
   *
   * @param data 需要加密的字符串
   * @param key 密钥
   * @return 字符串
   */
  public static String encryptHMAC(String data, String key) {
    if (TextUtils.isEmpty(data)) {
      return null;
    }
    return byteArrayToHexString(encryptHMAC2Bytes(data, key));
  }

  /**
   * HMAC加密
   *
   * @param data 需要加密的字符串
   * @param key 密钥
   * @return 字符串
   */
  public static byte[] encryptHMAC2Bytes(String data, String key) {
    if (TextUtils.isEmpty(data)) {
      return null;
    }
    try {
      return encryptHMAC(data.getBytes("UTF-8"), key);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return new byte[0];
  }

  /**
   * 将一个字节转化成十六进制形式的字符串
   *
   * @param b 字节数组
   * @return 字符串
   */
  private static String byteToHexString(byte b) {
    int ret = b;
    //System.out.println("ret = " + ret);
    if (ret < 0) {
      ret += 256;
    }
    int m = ret / 16;
    int n = ret % 16;
    return hexDigits[m] + hexDigits[n];
  }

  /**
   * 转换字节数组为十六进制字符串
   *
   * @param bytes 字节数组
   * @return 十六进制字符串
   */
  private static String byteArrayToHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte aByte : bytes) {
      sb.append(byteToHexString(aByte));
    }
    return sb.toString();
  }
}




