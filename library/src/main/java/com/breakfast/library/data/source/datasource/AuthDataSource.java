package com.breakfast.library.data.source.datasource;

import android.support.annotation.NonNull;

import com.breakfast.library.data.entity.user.User;
import com.breakfast.library.data.entity.user.UserModel;

import rx.Subscriber;

/**
 * Created by Steven on 2017/2/26.
 */

public interface AuthDataSource {

    void login(@NonNull User user,
               @NonNull Subscriber<UserModel> subscriber);
}
