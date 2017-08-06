package com.breakfast.client.home.contract;

import com.breakfast.client.base.contract.BasePresenter;
import com.breakfast.client.base.contract.BaseView;
import com.breakfast.library.data.entity.breakfast.HotelBreakfastSummary;

import java.util.List;

/**
 * Created by jliang on 7/25/2017.
 */

public interface BreakfastContract {

    interface View extends BaseView<BreakfastContract.Presenter> {

        void showHotelBreakfastSummary(HotelBreakfastSummary hotelBreakfastSummary);

        void resetError();

        void showWaitDialog(String str);

    }

    interface Presenter extends BasePresenter {
        void getHotelBreakfastSummary();

        void searchRooms(String txt);

        void consume(String roomId,int count);
    }
}
