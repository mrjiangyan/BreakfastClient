package com.breakfast.client.home.presenter;

import android.support.annotation.NonNull;

import com.breakfast.client.R;
import com.breakfast.client.home.contract.BreakfastContract;
import com.breakfast.library.data.entity.breakfast.HotelBreakfastSummary;
import com.breakfast.library.data.source.datasource.BreakfastDataSource;
import com.breakfast.library.network.internal.ApiException;
import com.breakfast.library.network.internal.ErrorSubscriber;
import com.breakfast.library.util.SharedPreferenceUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jliang on 7/25/2017.
 */

public class BreakfastPresenter implements BreakfastContract.Presenter {

    private final BreakfastDataSource mSource;

    private final BreakfastContract.View mView;

    @Override
    public void start() {

    }

    public BreakfastPresenter(@NonNull BreakfastDataSource userSource, @NonNull BreakfastContract.View userView) {
        mSource = checkNotNull(userSource, "userSource cannot be null");
        mView = checkNotNull(userView, "userView cannot be null!");

        mView.setPresenter(this);
    }

    @Override
    public void getHotelBreakfastSummary() {
        mView.resetError();

        mSource.getHotelBreakfastSummary(new ErrorSubscriber<HotelBreakfastSummary>(){
            @Override
            public void onNext(HotelBreakfastSummary hotelBreakfastSummary) {
                if (hotelBreakfastSummary != null) {
                    SharedPreferenceUtils.saveConfig(R.string.STRING_HOTEL_BREAKFAST_TOTAL_COUNT_ID, hotelBreakfastSummary.getBreakfastTotalCount());
                    SharedPreferenceUtils.saveConfig(R.string.STRING_HOTEL_BREAKFAST_TOTAL_AVAILABLE_COUNT_ID, hotelBreakfastSummary.getTotalAvailableBreakfastCount());
                    SharedPreferenceUtils.saveConfig(R.string.STRING_HOTEL_BREAKFAST_USE_COUNT_ID, hotelBreakfastSummary.getBreakfastUseCount());
                    mView.showHotelBreakfastSummary();
                } else {
                    mView.showErrorView(null, null);
                }
            }

            @Override
            public void onResponseError(ApiException ex) {
                mView.showErrorView(ex.getMessage(), null);
            }
        });
    }
}
