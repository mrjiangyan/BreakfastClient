package com.breakfast.client.home.contract;

import com.breakfast.client.base.contract.BasePresenter;
import com.breakfast.client.base.contract.BaseView;

/**
 * Created by jliang on 7/25/2017.
 */

public interface BreakfastContract {

    interface View extends BaseView<BreakfastContract.Presenter> {

        void showHotelBreakfastSummary();

        void resetError();

    }

    interface Presenter extends BasePresenter {
        void getHotelBreakfastSummary();
    }
}
