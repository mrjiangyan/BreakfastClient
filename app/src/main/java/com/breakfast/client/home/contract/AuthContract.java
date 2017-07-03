package com.breakfast.client.home.contract;

import com.breakfast.client.base.contract.BasePresenter;
import com.breakfast.client.base.contract.BaseView;

/**
 * Created by Steven on 2017/2/26.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface AuthContract {

    interface View extends BaseView<Presenter> {

        void showLoginSuccess();

        void resetError();

        void showPasswordIsEmptyErrorMessage();

        void showAccountIsEmptyErrorMessage();

    }

    interface Presenter extends BasePresenter {

        void login(String account,String password);

        void refreshToken();


    }
}
