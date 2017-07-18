package com.breakfast.library.data.entity;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by Steven on 2017/4/24.
 */


public class BaseModel implements Parcelable {

    //数据Id
    private long id;
    //是否有效
    private boolean active;

    //是否被置为无用状态，true表示可用，false表示不可用
    private boolean valid;

    //备注
    private String remark;

    public BaseModel()
    {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeByte(this.valid ? (byte) 1 : (byte) 0);
        dest.writeString(this.remark);
    }

    protected BaseModel(Parcel in) {
        this.id = in.readLong();
        this.active = in.readByte() != 0;
        this.valid = in.readByte() != 0;
        this.remark = in.readString();
    }


    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

}
