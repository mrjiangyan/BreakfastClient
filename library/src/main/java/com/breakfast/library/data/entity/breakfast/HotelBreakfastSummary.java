package com.breakfast.library.data.entity.breakfast;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jliang on 7/24/2017.
 */

public class HotelBreakfastSummary implements Parcelable {

    public int getBreakfastTotalCount() {
        return BreakfastTotalCount;
    }

    public void setBreakfastTotalCount(int breakfastTotalCount) {
        BreakfastTotalCount = breakfastTotalCount;
    }

    public int getBreakfastUseCount() {
        return BreakfastUseCount;
    }

    public void setBreakfastUseCount(int breakfastUseCount) {
        BreakfastUseCount = breakfastUseCount;
    }

    public int getTotalAvailableBreakfastCount() {
        return TotalAvailableBreakfastCount;
    }

    public void setTotalAvailableBreakfastCount(int totalAvailableBreakfastCount) {
        TotalAvailableBreakfastCount = totalAvailableBreakfastCount;
    }

    public static Creator<HotelBreakfastSummary> getCREATOR() {
        return CREATOR;
    }

    /// <summary>
    /// 早餐总数量
    /// </summary>
    private int BreakfastTotalCount;

    /// <summary>
    /// 早餐使用数量
    /// </summary>
    private int BreakfastUseCount;

    /// <summary>
    /// 总的可用早餐分数
    /// </summary>
    private int TotalAvailableBreakfastCount;

    protected HotelBreakfastSummary(Parcel in) {
        BreakfastTotalCount = in.readInt();
        BreakfastTotalCount = in.readInt();
        TotalAvailableBreakfastCount = in.readInt();
    }

    public static final Creator<HotelBreakfastSummary> CREATOR = new Creator<HotelBreakfastSummary>() {
        @Override
        public HotelBreakfastSummary createFromParcel(Parcel in) {
            return new HotelBreakfastSummary(in);
        }

        @Override
        public HotelBreakfastSummary[] newArray(int size) {
            return new HotelBreakfastSummary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(BreakfastTotalCount);
        dest.writeInt(BreakfastUseCount);
        dest.writeInt(TotalAvailableBreakfastCount);
    }
}
