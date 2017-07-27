package com.breakfast.library.data.source.impl;

import android.support.annotation.NonNull;

import com.breakfast.library.data.entity.breakfast.HotelBreakfastSummary;
import com.breakfast.library.data.source.datasource.BreakfastDataSource;
import com.breakfast.library.network.protocol.ServiceFactory;
import com.breakfast.library.network.protocol.security.IBreakfastService;

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
}
