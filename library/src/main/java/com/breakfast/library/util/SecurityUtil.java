package com.breakfast.library.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;

import com.breakfast.library.app.BaseApplication;
import com.breakfast.library.data.entity.user.User;

/**
 * Created by Steven on 2017/5/24.
 */

public class SecurityUtil {


    public static User getCurrentUser(Context context)
    {
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        String data=sp.getString("DATA",null);
        if(TextUtils.isEmpty(data))
            return null;
        String base64=EncryptUtils.decoderJson(data,context.getPackageName());
        String json=new String(Base64.decode(base64,Base64.DEFAULT));
        return GsonUtils.fromJson(json,User.class);

    }

    public static void save(@NonNull Context context, @NonNull User user)
    {
        SharedPreferences sp =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= sp.edit();
        String  base64=Base64.encodeToString(GsonUtils.toJson(user).getBytes(),Base64.DEFAULT);
        //对数据进行简单的加密
        editor.putString("DATA", EncryptUtils.encoderJson(base64,context.getPackageName()));
        editor.commit();
    }
}
