package com.breakfast.client.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.breakfast.client.base.BaseFragment;
import com.breakfast.client.R;
import com.breakfast.library.util.SharedPreferenceUtils;


public class SummaryFragment extends BaseFragment {

    private TextView txtHotelTotalBreakfastCount = null;
    private TextView txtHotelAvailableBreakfastCount = null;
    private TextView txtHotelUseBreakfastCount = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    public static SummaryFragment newInstance() {
        SummaryFragment fragment = new SummaryFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       // mPresenter = new MyProfilePresenter(new OrgDataSourceImpl(), this);
        super.onViewCreated(view, savedInstanceState);
        txtHotelTotalBreakfastCount=(TextView)view.findViewById(R.id.txtHotelTotalBreakfastCount);
        txtHotelAvailableBreakfastCount=(TextView)view.findViewById(R.id.txtHotelTotalAvailableBreakfastCount);
        txtHotelUseBreakfastCount=(TextView)view.findViewById(R.id.txtHotelTotalUseBreakfastCount);

        txtHotelTotalBreakfastCount.setText(Integer.toString(SharedPreferenceUtils.getInt(R.string.STRING_HOTEL_BREAKFAST_TOTAL_COUNT_ID)));
        txtHotelAvailableBreakfastCount.setText(Integer.toString(SharedPreferenceUtils.getInt(R.string.STRING_HOTEL_BREAKFAST_TOTAL_AVAILABLE_COUNT_ID)));
        txtHotelUseBreakfastCount.setText(Integer.toString(SharedPreferenceUtils.getInt(R.string.STRING_HOTEL_BREAKFAST_USE_COUNT_ID)));

    }

    @Override
    public boolean isShowNavigation() {
        return false;
    }



    @Override
    protected CharSequence getTitle() {
        return "统计";
    }


    @Override
    protected boolean onBackPressed() {
        return false;
    }




}
