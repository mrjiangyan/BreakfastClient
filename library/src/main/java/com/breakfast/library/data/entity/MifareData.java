package com.breakfast.library.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steven on 2017/7/27.
 */

public class MifareData implements Parcelable {

    private String cardId;

    private String data;

    public MifareData()
    {

    }

    protected MifareData(Parcel in) {
        cardId = in.readString();
        data = in.readString();
    }

    public static final Creator<MifareData> CREATOR = new Creator<MifareData>() {
        @Override
        public MifareData createFromParcel(Parcel in) {
            return new MifareData(in);
        }

        @Override
        public MifareData[] newArray(int size) {
            return new MifareData[size];
        }
    };

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardId);
        dest.writeString(data);
    }
}
