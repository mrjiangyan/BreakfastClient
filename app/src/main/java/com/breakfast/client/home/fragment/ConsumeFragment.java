package com.breakfast.client.home.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
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

import java.util.List;

import butterknife.BindView;


public class ConsumeFragment extends BaseFragment implements BreakfastContract.View,View.OnClickListener {

    @BindView(R.id.tv_count)
    TextView tv_count;

    @BindView(R.id.tv_hotelName)
    TextView tv_hotelName;

    @BindView(R.id.tv_username)
    TextView tv_username;

    @BindView(R.id.txtInput)
    TextView txtInput;

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
        presenter = new BreakfastPresenter(BreakfastDataSourceImpl.getInstance(), null);
        init(view);
        super.onViewCreated(view, savedInstanceState);


    }

    private void init(View v) {
        txtInput.setOnClickListener(this);
        int[] resIds=new int[]{R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,
                R.id.six,R.id.seven,R.id.eight,R.id.nine,R.id.zero,R.id.de};
        for(int id: resIds)
        {
            v.findViewById(id).setOnClickListener(this);
        }
        txtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 2) {
                    //do get
                }
                if (editable.length() > 20) {
                    txtInput.setError("房间号不能超过20位！");
                }
                if (editable.length() <= 20) {
                    txtInput.setError(null);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.de:
                if (txtInput.getText().length() > 0) {
                    txtInput.setText(txtInput.getText().subSequence(0,
                            txtInput.getText().length() - 1));
                }
                break;
            default:
                if (txtInput.getText().length() == 10) {
                    return;
                }
                txtInput.setText(txtInput.getText() + v.getTag().toString());
                break;

        }
        if (  txtInput.length()>2&&txtInput.length()<10  )
        {
            String aaa=txtInput.getText().toString();
            presenter.searchRooms(aaa);
        }
    }

    private  void  error(TextView tv)
    {
        tv.setError("aaa");

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

    @Override
    public void showWaitDialog(String str){
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).items(str).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onAny(MaterialDialog dialog) {
                super.onAny(dialog);
            }

            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
            }

            @Override
            public void onNeutral(MaterialDialog dialog) {
                super.onNeutral(dialog);
            }
        }).build();
        dialog.show();

    }


}
