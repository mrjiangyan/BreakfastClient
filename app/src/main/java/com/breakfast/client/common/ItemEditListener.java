package com.breakfast.client.common;

/**
 * Created by Steven on 2017/5/19.
 */

public interface ItemEditListener<T> {

    void onSuccess(T result);

    void onFail();
}
