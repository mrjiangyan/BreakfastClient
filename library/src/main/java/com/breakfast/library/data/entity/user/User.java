package com.breakfast.library.data.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.breakfast.library.data.entity.BaseModel;
import com.breakfast.library.util.StringUtils;

/**
 * Created by Steven on 2017/2/26.
 */

public class User extends BaseModel implements Parcelable {

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String password;

    private String userName;

    private String url;

    private String shift;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public User() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.password);
        dest.writeString(this.userName);
        dest.writeString(this.url);
        dest.writeString(StringUtils.isBlank(this.shift) ? "0" : this.shift);
    }

    protected User(Parcel in) {
        super(in);
        this.password = in.readString();
        this.userName = in.readString();
        this.url = in.readString();
        this.shift = in.readString();
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
