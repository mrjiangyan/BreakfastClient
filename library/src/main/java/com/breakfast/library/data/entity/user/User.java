package com.breakfast.library.data.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.breakfast.library.data.entity.BaseModel;

/**
 * Created by Steven on 2017/2/26.
 */

public class User extends BaseModel implements Parcelable{



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    private Boolean activation;

    private String password;

    private String mobile;

    private String name;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public User()
    {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.token);
        dest.writeValue(this.activation);
        dest.writeString(this.password);
        dest.writeString(this.mobile);
        dest.writeString(this.name);
    }

    protected User(Parcel in) {
        super(in);
        this.token = in.readString();
        this.activation = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.password = in.readString();
        this.mobile = in.readString();
        this.name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
