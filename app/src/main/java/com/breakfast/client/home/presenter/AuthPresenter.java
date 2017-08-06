package com.breakfast.client.home.presenter;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.breakfast.client.R;
import com.breakfast.client.home.contract.AuthContract;
import com.breakfast.library.app.BaseApplication;
import com.breakfast.library.data.entity.user.User;
import com.breakfast.library.data.entity.user.UserModel;
import com.breakfast.library.data.source.datasource.AuthDataSource;
import com.breakfast.library.data.source.datasource.BreakfastDataSource;
import com.breakfast.library.data.source.impl.BreakfastDataSourceImpl;
import com.breakfast.library.network.internal.ApiException;
import com.breakfast.library.network.internal.ErrorSubscriber;
import com.breakfast.library.util.SecurityUtil;
import com.breakfast.library.util.SharedPreferenceUtils;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Steven on 2017/2/26.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AuthPresenter implements AuthContract.Presenter {

    private final AuthDataSource mSource;
    private final BreakfastDataSource breakfastDataSource;

    private final AuthContract.View mView;

    @Override
    public void start() {

    }

    public AuthPresenter(@NonNull AuthDataSource userSource, @NonNull AuthContract.View userView) {
        mSource = checkNotNull(userSource, "userSource cannot be null");
        mView = checkNotNull(userView, "userView cannot be null!");
        breakfastDataSource = new BreakfastDataSourceImpl();
        mView.setPresenter(this);
    }

    @Override
    public void login(CharSequence url, CharSequence account, CharSequence password){
        mView.resetError();
        //mView.showLoginSuccess(new UserModel(new Parcel()));
        // Check for a valid email address.
        if (TextUtils.isEmpty(url)) {
            mView.showUrlIsEmptyErrorMessage();
            return;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(account)) {
            mView.showAccountIsEmptyErrorMessage();
            return;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mView.showPasswordIsEmptyErrorMessage();
            return;
        }
        User user=new User();
        user.setUrl(url.toString());
        user.setUserName(account.toString());
        user.setPassword(password.toString());
        user.setShift("0");

        mSource.login(user,new ErrorSubscriber<UserModel>() {
            @Override public void onNext(UserModel userModel) {

                if (userModel != null) {
                    SecurityUtil.save(BaseApplication.getInstance(),user);
                    SharedPreferenceUtils.saveConfig(R.string.STRING_LAST_LOGIN_SUCCESS_URL_ID, user.getUrl());
                    SharedPreferenceUtils.saveConfig(R.string.STRING_LAST_LOGIN_SUCCESS_NAME_ID, user.getUserName());
                    mView.showLoginSuccess(userModel);
                } else {
                    mView.showErrorView(null, null);
                }

            }

            @Override public void onResponseError(ApiException ex) {
                mView.showErrorView(ex.getMessage(),null);
            }
        });
    }
}
