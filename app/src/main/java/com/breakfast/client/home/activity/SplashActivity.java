package com.breakfast.client.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.breakfast.client.R;
import com.breakfast.client.base.BaseActivity;
import com.breakfast.client.home.contract.AuthContract;
import com.breakfast.client.home.presenter.AuthPresenter;
import com.breakfast.library.data.source.impl.AuthDataSourceImpl;
import com.breakfast.library.util.SecurityUtil;

public class SplashActivity extends BaseActivity implements AuthContract.View {


    private AuthContract.Presenter presenter;
    AlphaAnimation animation;

    private boolean isRunning=false;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        LogUtils.d("onCreate");
        //presenter = new AuthPresenter(new AuthDataSourceImpl(), this);
        ImageView imageView = (ImageView) findViewById(R.id.loadImage);
        // 设置加载动画透明度渐变从（0.1不显示-1.0完全显示）
        animation = new AlphaAnimation(0.1f, 1.0f);
        AnimationSet as=new AnimationSet(true);
        // 设置动画时间5s
        animation.setDuration(500);
        // 将组件与动画关联
        //imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            // 动画开始时执行
            public void onAnimationStart(Animation animation) {
                if(SecurityUtil.getCurrentUser(getApplication())!= null)
                {
                    isRunning=true;
                }
            }

            // 动画重复时执行
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            // 动画结束时执行
            public void onAnimationEnd(Animation animation) {
                if(!isRunning)
                    gotoLogin();
            }
        });
        as.addAnimation(animation);
        imageView.startAnimation(as);

    }




    private void gotoHome()
    {
        finish();
        setIntentClass(MainActivity.class);
    }

    private void gotoLogin()
    {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoginSuccess() {
        gotoHome();



    }

    @Override
    public void resetError() {

    }

    @Override
    public void showPasswordIsEmptyErrorMessage() {

    }

    @Override
    public void showAccountIsEmptyErrorMessage() {

    }

    @Override
    public void showUrlIsEmptyErrorMessage(){

    }

    @Override
    public void setPresenter(AuthContract.Presenter presenter) {
        this.presenter =presenter;
    }


    @Override
    public void showErrorView(String errMsg, @Nullable View.OnClickListener onClickListener) {
        isRunning=false;
        if(animation!= null && animation.hasEnded())
        {
            gotoLogin();
        }
    }

    @Override
    public void showProcessing() {

    }


}
