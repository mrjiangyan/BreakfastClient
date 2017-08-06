package com.breakfast.library.data.source.impl;

import android.support.annotation.NonNull;

import com.breakfast.library.data.entity.base.ApiResponse;
import com.breakfast.library.data.entity.breakfast.ConsumeBreakfast;
import com.breakfast.library.data.entity.breakfast.HotelBreakfastSummary;
import com.breakfast.library.data.source.datasource.BreakfastDataSource;
import com.breakfast.library.network.protocol.ServiceFactory;
import com.breakfast.library.network.protocol.security.IBreakfastService;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jliang on 7/25/2017.
 */

public class BreakfastDataSourceImpl implements BreakfastDataSource {
    private static BreakfastDataSourceImpl INSTANCE;

    public static BreakfastDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BreakfastDataSourceImpl();
        }
        return INSTANCE;
    }

    private IBreakfastService breakfastService;

    public BreakfastDataSourceImpl() {
        breakfastService = ServiceFactory.generateService(IBreakfastService.class);
    }

    @Override
    public void getHotelBreakfastSummary(@NonNull Subscriber<HotelBreakfastSummary> subscriber) {
        breakfastService.getHotelBreakfastSummary()
                .map(new ApiResponseFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getBreakfastLists() {
        List<String> rooms = new ArrayList<>();
        rooms.add("1001");
        rooms.add("1002");
        rooms.add("1003");
        rooms.add("1004");
        //do subscriber
        breakfastService.getRoomNumbers(null)
                .map(new ApiResponseFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void consumeBreakfast(@NonNull ConsumeBreakfast model) {
        //do subscriber
         breakfastService.postConsumeBreakfast(model)
                 .map(new ApiResponseFunc<>())
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe();
    }
}
