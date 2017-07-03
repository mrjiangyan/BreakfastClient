package com.breakfast.client.base.contract;

/**
 * Created by Steven on 2017/5/1.
 */

public interface BaseDetailPresenter<T> extends BasePresenter {
    void setEntity(long id);

    void setEntity(T entity);

    T getEntity();

    void clickModify();

}