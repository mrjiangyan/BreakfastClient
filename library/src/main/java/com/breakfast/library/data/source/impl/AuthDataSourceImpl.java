package com.breakfast.library.data.source.impl;

import android.support.annotation.NonNull;

import com.breakfast.library.data.entity.user.User;
import com.breakfast.library.data.entity.user.UserModel;
import com.breakfast.library.data.source.datasource.AuthDataSource;
import com.breakfast.library.network.protocol.security.IAuthService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Steven on 2017/2/26.
 */

public class AuthDataSourceImpl implements AuthDataSource {

    private static AuthDataSourceImpl INSTANCE;

    public static AuthDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthDataSourceImpl();
        }
        return INSTANCE;
    }

    private IAuthService mService;

    private IAuthService pmsService;

    public AuthDataSourceImpl() {
        pmsService = ServiceFactory.generateService(IAuthService.class);
    }



    @Override
    public void login(@NonNull User user, @NonNull Subscriber<UserModel> subscriber) {
        pmsService.login(user)
                .map(new ApiResponseFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
