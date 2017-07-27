package com.breakfast.client.home.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;

import com.apkfuns.logutils.LogUtils;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.breakfast.client.base.BaseFragment;
import com.breakfast.client.util.ActivityUtils;
import com.breakfast.client.R;
import com.breakfast.client.base.BaseActivity;
import com.breakfast.client.home.fragment.SummaryFragment;
import com.breakfast.client.util.DialogUtils;
import com.breakfast.client.util.StatisticsUtils;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;


public class MainActivity111 extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{


    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1000;


    private long lastCheckTime=0;
    private BaseFragment lastFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main11;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        LogUtils.d("onCreate");
        bottomNavigationBar.setActiveColor(R.color.white).setInActiveColor(R.color.colorPrimaryDark).setBarBackgroundColor(android.R.color.holo_blue_dark);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.
                addItem(new BottomNavigationItem(R.drawable.ic_event_black_24dp, "签到"))
                .addItem(new BottomNavigationItem(R.drawable.ic_assessment_black_24dp, "统计"))
                .initialise();
        bottomNavigationBar.selectTab(0);

    }

    private HashMap<String,BaseFragment> map= new HashMap<>();


    @Override
    public void onTabSelected(int position) {
        String key=position+"";
        if(map.containsKey(key))
        {
            ActivityUtils.addFragment(
                    this,lastFragment, map.get(key));
            lastFragment=map.get(key);
            return;
        }
        statistics(StatisticsUtils.TYPE_TOUCH,"onTabSelected:"+position);
        BaseFragment fragment=null;
        if(position==0)
        {

            fragment= SummaryFragment.newInstance();

        }
        else if(position==1)
        {
            fragment= SummaryFragment.newInstance();

        }
        if(fragment==null)
            return;
        map.put(key,fragment);
        if(lastFragment==null)
        {

            ActivityUtils.addFragmentToActivity(
                    this, fragment);
        }
        else
        {
            ActivityUtils.addFragment(
                    this,lastFragment, map.get(key));
        }
        lastFragment=fragment;


    }
    @Override
    public void onTabUnselected(int position) {
    }
    @Override
    public void onTabReselected(int position) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[]grantResults){
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:{
                // If request is cancelled, the result arrays are empty.
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                }else{
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Here, thisActivity is the current activity
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            // Should we show an explanation?
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }else{
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else if((Calendar.getInstance().getTimeInMillis()-lastCheckTime)>1000*60*60*8)
        {
//            new AppServiceImpl().check(new Subscriber<AppCheck>()
//            {
//
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onNext(AppCheck appCheck) {
//                    lastCheckTime=Calendar.getInstance().getTimeInMillis();
//                    if(appCheck!= null && !DownloadTask.isRun())
//                    {
//                        new MaterialDialog.Builder(MainActivity111.this)
//                                .title("升级提示")
//                                .content(appCheck.content)
//                                //.positiveText(R.string.cancel)
//                                .negativeText(R.string.upgrade)
//                                .negativeColorRes(R.color.color_accent_red)
//                                // .negativeColorRes(R.color.colorPrimary)
//                                .onNegative((dd, who) -> DownloadTask.getInstance(MainActivity111.this).execute(appCheck))
//                                .show();
//                    }
//                }
//            });
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime=0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            long secondTime=System.currentTimeMillis();
            if(secondTime-firstTime>1000){
                DialogUtils.showToast(MainActivity111.this,"再按一次退出程序", DialogUtils.ToastType.normal);
                firstTime=secondTime;
                return true;
            }else{
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (map != null)
            map.clear();
    }
}
