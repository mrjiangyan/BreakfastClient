package com.breakfast.library.app;

import com.breakfast.library.data.entity.user.UserModel;

/**
 * Created by jliang on 7/30/2017.
 */

public class BreakfastApplication extends BaseApplication {

    private UserModel userModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
