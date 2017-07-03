package com.breakfast.client.base.contract;

import java.util.List;

/**
 * Created by Steven on 2017/5/1.
 */

public interface BaseListPresenter<T> extends BasePresenter {

    void addMore(int page);

    List<T> getList();

    void delete(long id);
}