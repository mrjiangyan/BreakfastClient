package com.breakfast.client.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.breakfast.client.base.contract.BaseView;
import com.breakfast.client.home.contract.AuthContract;
import com.breakfast.client.util.DialogUtils;
import com.breakfast.client.R;
import com.breakfast.client.base.BaseActivity;
import com.breakfast.client.home.presenter.AuthPresenter;
import com.breakfast.client.view.DeletableEditText;
import com.breakfast.library.data.source.impl.AuthDataSourceImpl;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements AuthContract.View {

    @BindView(R.id.tv_mobile)
    DeletableEditText tv_mobile;

    @BindView(R.id.tv_password)
    DeletableEditText tv_password;

    @OnClick(R.id.sign_in_button)
    void showLogin() {
        presenter.login(tv_mobile.getText().toString(),tv_password.getText().toString());
    }



    private AuthContract.Presenter presenter;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        presenter = new AuthPresenter(new AuthDataSourceImpl(), this);

    }








    @Override
    public void showLoginSuccess() {
        finish();
        setIntentClass(MainActivity.class);
    }

    @Override
    public void resetError() {

    }


    @Override
    public void showPasswordIsEmptyErrorMessage() {
        tv_password.setError("请输入密码");
        tv_password.setShakeAnimation();
    }

    @Override
    public void showAccountIsEmptyErrorMessage() {
        tv_mobile.setError("请输入账号");
        tv_mobile.setShakeAnimation();
    }

    @Override
    public void setPresenter(AuthContract.Presenter presenter) {
        this.presenter =presenter;
    }

    @Override
    public void showErrorView(String errMsg, @Nullable View.OnClickListener onClickListener) {
        DialogUtils.showToast(this,errMsg, DialogUtils.ToastType.error);

    }


    @Override
    public void showProcessing() {

    }

    @Override
    public void needToRefresh(BaseView.RefreshType type) {

    }


}

