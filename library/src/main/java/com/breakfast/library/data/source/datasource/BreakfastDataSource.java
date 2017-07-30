package com.breakfast.library.data.source.datasource;

import android.support.annotation.NonNull;

import com.breakfast.library.data.entity.breakfast.ConsumeBreakfast;
import com.breakfast.library.data.entity.breakfast.HotelBreakfastSummary;

import rx.Subscriber;

/**
 * Created by jliang on 7/24/2017.
 */

public interface BreakfastDataSource {
    void getHotelBreakfastSummary(@NonNull Subscriber<HotelBreakfastSummary> subscriber);

    //void getRoomNumbers(@NonNull Subscriber<HotelBreakfastSummary> subscriber,String roomNumber);

    void  consumeBreakfast(@NonNull ConsumeBreakfast model);
}
