package com.breakfast.client.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.breakfast.client.R;
import com.breakfast.client.base.BaseFragment;
import com.breakfast.client.home.contract.BreakfastContract;
import com.breakfast.client.home.presenter.BreakfastPresenter;
import com.breakfast.client.util.DialogUtils;
import com.breakfast.library.app.BreakfastApplication;
import com.breakfast.library.data.entity.breakfast.HotelBreakfastSummary;
import com.breakfast.library.data.entity.user.UserModel;
import com.breakfast.library.data.source.impl.BreakfastDataSourceImpl;

import butterknife.BindView;


public class ConsumeFragment extends BaseFragment implements BreakfastContract.View{

    @BindView(R.id.tv_count)
    TextView tv_count;

    @BindView(R.id.tv_hotelName)
    TextView tv_hotelName;

    @BindView(R.id.tv_username)
    TextView tv_username;


    private BreakfastContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        presenter = new BreakfastPresenter(BreakfastDataSourceImpl.getInstance(), this);
        presenter.getHotelBreakfastSummary();
        UserModel userModel = ((BreakfastApplication) getActivity().getApplication()).getUserModel();
        if(null!=userModel){
            tv_username.setText(userModel.getEmployeeName());
            tv_hotelName.setText(userModel.getOrgName());
        }

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
        return "消费";
    }


    @Override
    protected boolean onBackPressed() {
        return false;
    }


    @Override
    public void setPresenter(BreakfastContract.Presenter presenter) {
        this.presenter =presenter;
    }

    @Override
    public void showErrorView(String errMsg, @Nullable View.OnClickListener onClickListener) {
        DialogUtils.showToast(getContext(),errMsg, DialogUtils.ToastType.error);
    }

    @Override
    public void showProcessing() {

    }

    @Override
    public void showHotelBreakfastSummary(HotelBreakfastSummary hotelBreakfastSummary) {
        String availableCountAndTotalCount = hotelBreakfastSummary.getTotalAvailableBreakfastCount() + "/" + hotelBreakfastSummary.getBreakfastTotalCount();
        tv_count.setText(availableCountAndTotalCount);
    }

    @Override
    public void resetError() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        presenter.getHotelBreakfastSummary();
    }
}
