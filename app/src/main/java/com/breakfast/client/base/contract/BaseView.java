package com.breakfast.client.base.contract;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * Author：ClareChen
 * E-mail：ggchaifeng@gmail.com
 * Date：  16/8/3 下午2:52
 */
public interface BaseView<T extends BasePresenter> {
  void setPresenter(T presenter);

  void showErrorView(String errMsg, @Nullable View.OnClickListener onClickListener);


  void showProcessing();


  void needToRefresh(RefreshType type);

  enum RefreshType
  {
      Self,
      Parent,
      Both
  }
}
