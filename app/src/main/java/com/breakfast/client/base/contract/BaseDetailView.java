package com.breakfast.client.base.contract;

/**
 * Created by Steven on 2017/5/1.
 */

public interface BaseDetailView extends BaseView<BaseDetailPresenter> {

    void setDisplayView();


    boolean check();

    void save();

    void close(boolean showToast, String message);

    void success();

}