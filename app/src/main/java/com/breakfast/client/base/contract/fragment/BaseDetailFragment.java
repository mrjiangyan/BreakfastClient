package com.breakfast.client.base.contract.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.breakfast.client.R;
import com.breakfast.client.base.BaseFragment;
import com.breakfast.client.base.ItemClickListener;
import com.breakfast.client.base.contract.BaseDetailPresenter;
import com.breakfast.client.common.ItemEditListener;
import com.breakfast.client.util.DialogUtils;
import com.breakfast.client.util.StatisticsUtils;


public abstract class BaseDetailFragment<T> extends BaseFragment {
    @Override
    protected CharSequence getTitle() {
        return null;
    }

    protected ItemClickListener<T> itemClickListener;
    protected BaseDetailPresenter<T> mPresenter;
    protected ItemEditListener<T> itemEditListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    public void success()
    {

    }





    public void setPresenter(BaseDetailPresenter presenter) {

    }


    public void showErrorView(String errMsg, @Nullable View.OnClickListener onClickListener) {
        if(getContext()==null)
            return;
        DialogUtils.showToast(getContext(),errMsg, DialogUtils.ToastType.error);


    }


    public void setDisplayView() {
        bindView();
    }

    protected abstract void bindView();



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ENTITY)) {
            mPresenter.setEntity(getArguments().getParcelable(ENTITY));
        }
        else if (getArguments() != null && getArguments().containsKey(ID)) {
            mPresenter.setEntity(getArguments().getLong(ID));
        }
        else
            mPresenter.setEntity(null);

    }


    public void showProcessing() {

    }


    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }
}
