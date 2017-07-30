package com.breakfast.client.home.contract;

import com.breakfast.client.base.contract.BasePresenter;
import com.breakfast.client.base.contract.BaseView;
import com.breakfast.library.data.entity.breakfast.HotelBreakfastSummary;

/**
 * Created by jliang on 7/25/2017.
 */

public interface BreakfastContract {

    interface View extends BaseView<BreakfastContract.Presenter> {

        void showHotelBreakfastSummary(HotelBreakfastSummary hotelBreakfastSummary);

        void resetError();

    }

    interface Presenter extends BasePresenter {
        void getHotelBreakfastSummary();
    }
}
