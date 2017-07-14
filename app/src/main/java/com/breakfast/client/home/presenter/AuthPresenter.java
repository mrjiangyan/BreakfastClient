package com.breakfast.client.home.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.breakfast.client.home.contract.AuthContract;
import com.breakfast.library.app.BaseApplication;
import com.breakfast.library.data.entity.user.User;
import com.breakfast.library.data.source.datasource.AuthDataSource;
import com.breakfast.library.network.internal.ApiException;
import com.breakfast.library.network.internal.ErrorSubscriber;
import com.breakfast.library.util.SecurityUtil;

import okhttp3.OkHttpClient;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Steven on 2017/2/26.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AuthPresenter implements AuthContract.Presenter {

    private final AuthDataSource mSource;

    private final AuthContract.View mView;



    @Override
    public void start() {

    }

    public AuthPresenter(@NonNull AuthDataSource userSource, @NonNull AuthContract.View userView) {
        mSource = checkNotNull(userSource, "userSource cannot be null");
        mView = checkNotNull(userView, "userView cannot be null!");

        mView.setPresenter(this);
    }

    @Override
    public void login(CharSequence url, CharSequence account, CharSequence password) {
        mView.resetError();
        // Check for a valid email address.
        if (TextUtils.isEmpty(url)) {
            mView.showAccountIsEmptyErrorMessage();
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
        user.setMobile(account.toString());
        user.setPassword(password.toString());



        mSource.login(user,new ErrorSubscriber<User>() {
            @Override public void onNext(User user) {

                //绑定数据
                if (null != user ) {
                    SecurityUtil.save(BaseApplication.getInstance(),user);
                    mView.showLoginSuccess();
                }
                else
                {
                    mView.showErrorView(null,null);
                }

            }

            @Override public void onResponseError(ApiException ex) {
                mView.showErrorView(ex.getMessage(),null);
            }
        });
    }

    @Override
    public void refreshToken() {
        mSource.refreshToken(new ErrorSubscriber<String>() {
            @Override public void onNext(String token) {

                //绑定数据
                if (null != token ) {
                    User user= SecurityUtil.getCurrentUser(BaseApplication.getInstance());
                    assert user != null;
                    user.setToken(token);
                    SecurityUtil.save(BaseApplication.getInstance(),user);
                    mView.showLoginSuccess();
                }

            }

            @Override public void onResponseError(ApiException ex) {
                mView.showErrorView(null,null);
            }
        });
    }
}
