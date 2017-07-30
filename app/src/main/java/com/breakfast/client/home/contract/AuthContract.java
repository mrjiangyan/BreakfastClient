package com.breakfast.client.home.contract;

import com.breakfast.client.base.contract.BasePresenter;
import com.breakfast.client.base.contract.BaseView;
import com.breakfast.library.data.entity.user.UserModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Steven on 2017/2/26.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface AuthContract {

    interface View extends BaseView<Presenter> {

        void showLoginSuccess(UserModel userModel);

        void resetError();

        void showUrlIsEmptyErrorMessage();

        void showPasswordIsEmptyErrorMessage();

        void showAccountIsEmptyErrorMessage();

    }

    interface Presenter extends BasePresenter {

       void login(CharSequence url, CharSequence account, CharSequence password);

    }
}
