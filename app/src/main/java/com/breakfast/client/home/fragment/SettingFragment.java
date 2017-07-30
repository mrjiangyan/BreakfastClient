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


public class SettingFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
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
        return "设置";
    }


    @Override
    protected boolean onBackPressed() {
        return false;
    }




}
