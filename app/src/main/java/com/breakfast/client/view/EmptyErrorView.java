package com.breakfast.client.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.breakfast.client.R;
import com.breakfast.library.util.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by feng on 16/8/25.
 */
public class EmptyErrorView extends FrameLayout {
  @BindView(R.id.tv_empty) TextView mEmptyMsg;
  @BindView(R.id.layout_empty)
  RelativeLayout mEmptyLayout;
  @BindView(R.id.iv_error) ImageView mErrorViewImg;
  @BindView(R.id.tv_error) TextView mErrorMag;
  @BindView(R.id.btn_error) Button mErrorBtn;
  @BindView(R.id.layout_error) LinearLayout mErrorLayout;

  @OnClick({ R.id.btn_error }) public void onClick(View view) {
    if (null != errorViewReloadListener) {
      errorViewReloadListener.onClick(view);
    }
  }

  private OnClickListener errorViewReloadListener;

  public EmptyErrorView(Context context) {
    super(context);
    init(context);
  }

  public EmptyErrorView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    View.inflate(context, R.layout.empty_error_view, this);
    ButterKnife.bind(this);
  }

  private void showErrorView() {
    mErrorLayout.setVisibility(VISIBLE);
    mEmptyLayout.setVisibility(GONE);
  }

  private void showEmptyView() {
    mErrorLayout.setVisibility(GONE);
    mEmptyLayout.setVisibility(VISIBLE);
  }

  public void showErrorView(String errMsg) {
    mErrorMag.setText(errMsg);
    showErrorView();
  }

  private void showErrorView(String errMsg, @DrawableRes int errImageRes) {
    mErrorMag.setText(errMsg);
    mErrorViewImg.setImageResource(errImageRes);
    mErrorLayout.setVisibility(View.VISIBLE);
    showErrorView();
  }

  public void showErrorView(@StringRes int errMsgId) {
    showErrorView(ResourceUtils.getString(errMsgId));
  }

  public void showErrorView(@StringRes int errMsgId, @DrawableRes int errImageRes) {
    showErrorView(ResourceUtils.getString(errMsgId), errImageRes);
  }

  public void showEmptyView(String emptyMsg) {
    mEmptyMsg.setText(emptyMsg);
    showEmptyView();
  }

  public void showEmptyView(String emptyMsg, @DrawableRes int iconRes) {
    mEmptyMsg.setText(emptyMsg);
    showEmptyView();
  }

  public void showEmptyView(@StringRes int emptyMsgId) {
    showEmptyView(ResourceUtils.getString(emptyMsgId));
  }

  public void setOnErrorViewReloadListener(OnClickListener listener) {
    errorViewReloadListener = listener;
    if (listener == null) {
      mErrorBtn.setVisibility(GONE);
    } else {
      mErrorBtn.setVisibility(VISIBLE);
    }
  }
}
