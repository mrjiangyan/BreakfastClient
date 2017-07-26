package com.breakfast.client.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.breakfast.client.R;
import com.breakfast.client.base.BaseFragment;

import butterknife.BindView;


public class ConsumeFragment extends BaseFragment {



    @BindView(R.id.tv_count)
    TextView tv_count;

    @BindView(R.id.tv_hotelName)
    TextView tv_hotelName;

    @BindView(R.id.tv_username)
    TextView tv_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_consume, container, false);
    }

    public static ConsumeFragment newInstance() {
        ConsumeFragment fragment = new ConsumeFragment();
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
