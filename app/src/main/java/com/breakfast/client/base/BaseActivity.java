package com.breakfast.client.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.breakfast.client.util.EspressoIdlingResource;
import com.breakfast.client.util.StatisticsUtils;
import com.google.common.eventbus.Subscribe;
import com.tendcloud.tenddata.TCAgent;
import com.breakfast.client.R;
import com.breakfast.library.common.Event;
import com.breakfast.library.common.EventBusCenter;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements BackHandleInterface{

  private BaseFragment mBackHandedFragment;




  private Toolbar toolbar;

  public Toolbar getToolbar()
  {
    return toolbar;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(setLayoutId());
    ButterKnife.bind(this);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    View v = findViewById(R.id.toolbar);
    if(v!= null && v instanceof  Toolbar)
    {
      toolbar= (Toolbar) v;
      setSupportActionBar(toolbar);
      setTitle("");
      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    try{
      initViews(savedInstanceState);
    }
    catch (Exception e){
      e.printStackTrace();
    }

    EventBusCenter.register(this);
  }

  @Subscribe
  public void receiveEvent(Event event) {
    System.out.println("String msg: " + event);
  }

  @Override
  public void setSelectedFragment(BaseFragment selectedFragment) {
    this.mBackHandedFragment = selectedFragment;
  }

  @Override
  public void onBackPressed() {
    if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
      if(getSupportFragmentManager().getBackStackEntryCount() == 0){
        super.onBackPressed();
      }else{
        getSupportFragmentManager().popBackStackImmediate();
      }
    }
  }



  @VisibleForTesting
  public IdlingResource getCountingIdlingResource() {
    return EspressoIdlingResource.getIdlingResource();
  }

  protected abstract int setLayoutId();
  protected abstract void initViews(Bundle savedInstanceState);



  protected void setIntentClass(Class<?> cla) {
    Intent intent = new Intent();
    intent.setClass(this, cla);
    startActivity(intent);
    //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
  }



  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_DOWN
            && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
        onBackPressed();
        return true;
    }
    return super.onKeyDown(keyCode, event);
  }






  @Override protected void onDestroy() {
    EventBusCenter.unregister(this);

    super.onDestroy();
  }

  @Override protected void onPause() {
    super.onPause();
    TCAgent.onPageEnd(this, getClass().getName());
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override protected void onResume() {
    super.onResume();
    TCAgent.onPageStart(this, getClass().getName());
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        StatisticsUtils.act(this, getTitle().toString(), "navigationUp");
        onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }


  public void statistics(int type, String action) {
    if (type == StatisticsUtils.TYPE_TOUCH) {
      StatisticsUtils.touch(this, getTitle().toString(), action);
    } else {
      StatisticsUtils.act(this, getTitle().toString(), action);
    }
  }
}
