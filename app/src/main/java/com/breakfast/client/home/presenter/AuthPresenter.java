package com.breakfast.client.home.presenter;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.breakfast.client.home.contract.AuthContract;
import com.breakfast.library.data.entity.user.User;
import com.breakfast.library.data.source.datasource.AuthDataSource;
import com.breakfast.library.network.internal.ApiException;
import com.breakfast.library.network.internal.ErrorSubscriber;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Steven on 2017/2/26.
 */

@SuppressWarnings("DefaultFileTemplate")
public class AuthPresenter implements AuthContract.Presenter {

    private Handler handler;

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
    public void login(CharSequence url, CharSequence account, CharSequence password){
        mView.resetError();
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

        handler=new android.os.Handler(){
            @Override
            public void handleMessage(Message message){
                if(message!=null){
                    System.out.println(message);
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                TestLogin(user);
                Message m=handler.obtainMessage();
                handler.sendMessage(m);
            }
        });



//        mSource.login(user,new ErrorSubscriber<String>() {
//            @Override public void onNext(String user) {
//
//                System.out.println(user);
////                //绑定数据
////                if (null != user ) {
////                    SecurityUtil.save(BaseApplication.getInstance(),user);
////                    mView.showLoginSuccess();
////                }
////                else
////                {
////                    mView.showErrorView(null,null);
////                }
//
//            }
//
//            @Override public void onResponseError(ApiException ex) {
//                mView.showErrorView(ex.getMessage(),null);
//            }
//        });
    }

    private String TestLogin(User user){
        String result="";
        String targetUrl="http://localhost:31119/auth/login";
        DefaultHttpClient httpClient=new DefaultHttpClient();
        List<BasicNameValuePair> paras=new ArrayList<>();
        paras.add(new BasicNameValuePair("userName",user.getUserName()));
        paras.add(new BasicNameValuePair("password",user.getPassword()));
        paras.add(new BasicNameValuePair("url",user.getUrl()));
        paras.add(new BasicNameValuePair("shift",user.getShift()));
        HttpPost httpRequest=new HttpPost(targetUrl);
        try{
            httpRequest.setEntity(new UrlEncodedFormEntity(paras,"utf-8"));
            HttpResponse httpResponse=httpClient.execute(httpRequest);
            if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                result+= EntityUtils.toString(httpResponse.getEntity());
            }else{
                result="请求失败";
            }
        }
        catch (UnsupportedEncodingException e1){
            e1.printStackTrace();
        }catch (ClientProtocolException e2){
            e2.printStackTrace();
        }catch (IOException e3){
            e3.printStackTrace();
        }

        return result;
    }
}
