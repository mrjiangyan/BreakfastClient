package com.breakfast.client.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.tendcloud.tenddata.TCAgent;
import com.breakfast.client.R;
import com.breakfast.client.base.contract.BaseView;
import com.breakfast.client.util.DialogUtils;
import com.breakfast.client.util.StatisticsUtils;
import com.breakfast.library.common.EventBusCenter;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {

  @Nullable
  protected BaseActivity mActivity;
  private BackHandleInterface mBackHandledInterface;
  private CharSequence title;


  @Override
  public void startActivity(Intent intent) {
    super.startActivity(intent);
    mActivity.statistics(StatisticsUtils.TYPE_TOUCH,intent.getComponent().getClassName());
  }



  @Override
  public void startActivity(Intent intent, @Nullable Bundle options) {
    super.startActivity(intent, options);
    mActivity.statistics(StatisticsUtils.TYPE_TOUCH,intent.getComponent().getClassName());
  }

  @Override
  public void startActivityForResult(Intent intent, int requestCode) {
    super.startActivityForResult(intent, requestCode);
    mActivity.statistics(StatisticsUtils.TYPE_TOUCH,intent.getComponent().getClassName());
  }

  @Override
  public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
    super.startActivityForResult(intent, requestCode, options);
    mActivity.statistics(StatisticsUtils.TYPE_TOUCH,intent.getComponent().getClassName());
  }

  private TextView tv_toolbar_title;

  /**
   * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
   * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
   * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
   */
  protected abstract boolean onBackPressed();


  public  static final String ENTITY="ENTITY";

  public  static final String ID="ID";

  //保证Fragment即使在onDetach后，仍持有Activity的引用（有引起内存泄露的风险，但是相比空指针闪退，这种做法“安全”些）
  protected Handler handler = new Handler(Looper.getMainLooper());
  protected LinearLayoutManager linearLayoutManager;
  Toolbar toolbar;

  protected Toolbar getToolbar()
  {
    return toolbar;
  }




  @Override public void onAttach(Context context) {
    super.onAttach(context);
    try {
      mActivity = (BaseActivity) context;
      setTitle(TextUtils.isEmpty(title) ? getTitle():title);

    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + " must extends BaseActivity");
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    menu.clear();
  }



  public void onResume() {
    super.onResume();
    LogUtils.w(getClass().getName()+ "-onResume");
  }

  public void onPause() {
    super.onPause();
    LogUtils.w(getClass().getName()+ "-onPause");
    TCAgent.onPageEnd(getContext(), getClass().getName());
  }

  public void onStart() {
    super.onStart();
    TCAgent.onPageStart(getContext(),getClass().getName());
    //告诉FragmentActivity，当前Fragment在栈顶
    mBackHandledInterface.setSelectedFragment(this);
  }

  protected boolean isShowNavigation()
  {
    return true;
  }


  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    setHasOptionsMenu(true);
    View v = view.findViewById(R.id.toolbar);
    if(v!= null && v instanceof  Toolbar)
    {
       toolbar= (Toolbar) v;
       mActivity.setSupportActionBar(toolbar);
    }
    else
    {
        toolbar =mActivity.getToolbar();
    }

    if(isShowNavigation())
    {
      toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
      toolbar.setNavigationContentDescription(null);
    }
    if(toolbar!= null)
    {
      toolbar.setTitle("");
      tv_toolbar_title= (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
      setTitle(TextUtils.isEmpty(title) ? getTitle():title);
      mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(isShowNavigation());

    }




  }

  protected void setTitle(CharSequence title)
  {
    this.title=title;
    if(tv_toolbar_title!= null)
      tv_toolbar_title.setText(title);
  }



  protected void setTitle(@StringRes int resId)
  {
    title=getString(resId);
    setTitle(title);
  }


  @Override public void onDetach() {
    super.onDetach();
    mActivity = null;
  }

  @Override public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    LogUtils.w(getClass().getName()+ "-onHiddenChanged:"+ hidden);
    if(!hidden && mActivity!= null)
    {
      setTitle(TextUtils.isEmpty(title) ? getTitle():title);
    }
  }

  protected abstract CharSequence getTitle();

  @Override
  public void onDestroy() {
    handler.removeCallbacksAndMessages(null);
    EventBusCenter.unregister(this);
    super.onDestroy();
  }

  public void close(boolean showToast, String message) {
    if(showToast && !TextUtils.isEmpty(message))
    {
      if(getContext()==null)
        return;
      showToast(message, DialogUtils.ToastType.success);
    }
    if(mActivity.getSupportFragmentManager().getBackStackEntryCount()==0)
      mActivity.finish();
    else
      mActivity.getSupportFragmentManager().popBackStack();

  }

  public void showToast(String message, DialogUtils.ToastType type) {
    DialogUtils.showToast(getContext(),message, type);

  }

  public void showToast(@StringRes int stringResId, DialogUtils.ToastType type) {
    DialogUtils.showToast(getContext(),getString(stringResId), type);

  }




  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(!(getActivity() instanceof BackHandleInterface)){
      throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
    }else{
      this.mBackHandledInterface = (BackHandleInterface)getActivity();
    }
    EventBusCenter.register(this);

  }


}
