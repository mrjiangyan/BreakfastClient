package com.breakfast.client.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breakfast.client.base.BaseFragment;
import com.breakfast.client.R;


public class SummaryFragment extends BaseFragment {






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
