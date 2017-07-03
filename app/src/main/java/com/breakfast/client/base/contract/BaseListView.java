package com.breakfast.client.base.contract;

import android.support.annotation.StringRes;

import com.breakfast.client.util.DialogUtils;

/**
 * Created by Steven on 2017/5/1.
 */

public interface BaseListView extends BaseView<BaseListPresenter> {


    void onFinish(int page, long totalRecords);

    void showToast(String message, DialogUtils.ToastType type);

    void showToast(@StringRes int stringResId, DialogUtils.ToastType type);

    void stopRefresh();


}