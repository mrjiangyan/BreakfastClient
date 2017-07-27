package com.breakfast.client.home.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.breakfast.client.R;
import com.breakfast.client.base.BaseActivity;
import com.breakfast.client.base.BaseFragment;
import com.breakfast.client.home.fragment.ConsumeFragment;
import com.breakfast.client.home.fragment.SummaryFragment;
import com.breakfast.client.util.ActivityUtils;
import com.breakfast.client.util.DialogUtils;
import com.breakfast.client.util.NfcUtils;
import com.breakfast.client.util.StatisticsUtils;
import com.breakfast.library.data.entity.MifareData;
import com.breakfast.library.util.StringUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{


    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    // NFC适配器
    private NfcAdapter nfcAdapter = null;
    // 传达意图
    private PendingIntent pi = null;
    // 滤掉组件无法响应和处理的Intent
    private IntentFilter tagDetected = null;


    // 是否支持NFC功能的标签
    private boolean isNFC_support = false;

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1000;


    private long lastCheckTime=0;
    private BaseFragment lastFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNFCData();
    }



    @Override
    protected void initViews(Bundle savedInstanceState) {
        bottomNavigationBar.setActiveColor(R.color.white).setInActiveColor(R.color.colorPrimaryDark).setBarBackgroundColor(android.R.color.holo_blue_dark);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.
                addItem(new BottomNavigationItem(R.drawable.ic_event_black_24dp, "消费"))
                .addItem(new BottomNavigationItem(R.drawable.ic_assessment_black_24dp, "设置"))
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

            fragment= ConsumeFragment.newInstance();

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
//                        new MaterialDialog.Builder(MainActivity.this)
//                                .title("升级提示")
//                                .content(appCheck.content)
//                                //.positiveText(R.string.cancel)
//                                .negativeText(R.string.upgrade)
//                                .negativeColorRes(R.color.color_accent_red)
//                                // .negativeColorRes(R.color.colorPrimary)
//                                .onNegative((dd, who) -> DownloadTask.getInstance(MainActivity.this).execute(appCheck))
//                                .show();
//                    }
//                }
//            });
        }

        if (isNFC_support) {
            // 开始监听NFC设备是否连接
            startNFC_Listener();
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(this.getIntent()
                    .getAction())) {
                // 注意这个if中的代码几乎不会进来，因为刚刚在上一行代码开启了监听NFC连接，下一行代码马上就收到了NFC连接的intent，这种几率很小
                // 处理该intent
                processIntent(this.getIntent());
            }
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
                DialogUtils.showToast(MainActivity.this,"再按一次退出程序", DialogUtils.ToastType.normal);
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 当前app正在前端界面运行，这个时候有intent发送过来，那么系统就会调用onNewIntent回调方法，将intent传送过来
        // 我们只需要在这里检验这个intent是否是NFC相关的intent，如果是，就调用处理方法
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            processIntent(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (map != null)
            map.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isNFC_support) {
            // 当前Activity如果不在手机的最前端，就停止NFC设备连接的监听
            stopNFC_Listener();
        }
    }

    private void initNFCData() {
        // 初始化设备支持NFC功能
        isNFC_support = true;
        // 得到默认nfc适配器
        nfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        // 提示信息定义
        String metaInfo;
        // 判定设备是否支持NFC或启动NFC
        if (nfcAdapter == null) {
            metaInfo = "设备不支持NFC！";
            Toast.makeText(this, metaInfo, Toast.LENGTH_SHORT).show();
            isNFC_support = false;
        } else if (!nfcAdapter.isEnabled()) {
            metaInfo = "请在系统设置中先启用NFC功能！";
            Toast.makeText(this, metaInfo, Toast.LENGTH_SHORT).show();
            isNFC_support = false;
        }
        if (isNFC_support) {
            // 初始化PendingIntent，当有NFC设备连接上的时候，就交给当前Activity处理
            pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            // 新建IntentFilter，使用的是第二种的过滤机制
            tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        }


    }


    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    public void processIntent(Intent intent) {
        if (!isNFC_support)
            return;

        // 取出封装在intent中的TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String metaInfo = "";
        metaInfo += "卡片ID：" + StringUtils.bytesToHexString(tagFromIntent.getId()) + "\n";
        Toast.makeText(this, metaInfo, Toast.LENGTH_SHORT).show();
        String[] techList = tagFromIntent.getTechList();
        //分析NFC卡的类型： Mifare Classic/UltraLight Info
        String CardType = "";
        for (int i = 0; i < techList.length; i++) {
            if (techList[i].equals(MifareClassic.class.getName())) {
                try {
                    MifareData data= new MifareData();
                    data.setCardId(StringUtils.bytesToHexString(tagFromIntent.getId(),false) );
                    data.setData(StringUtils.bytesToHexString(NfcUtils.readMifareTag(tagFromIntent)));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (techList[i].equals(MifareUltralight.class.getName())) {
                MifareUltralight mifareUlTag = MifareUltralight
                        .get(tagFromIntent);
                String lightType = "";
                // Type Info
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        lightType = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        lightType = "Ultralight C";
                        break;
                }
                CardType = lightType + "卡片类型\n";

                Ndef ndef = Ndef.get(tagFromIntent);
                CardType += "最大数据尺寸:" + ndef.getMaxSize() + "\n";

            }
            else if (techList[i].equals(NfcB.class.getName())) {
                NfcB tag = NfcB
                        .get(tagFromIntent);
                String lightType = "";
                // Type Info
                metaInfo += "卡片数据：" + StringUtils.bytesToHexString(tag.getApplicationData()) + "\n";
                CardType = lightType + "卡片类型\n";

            }
        }
        metaInfo += CardType;
        LogUtils.d(metaInfo);
    }




    private MediaPlayer ring() throws Exception, IOException {
        // TODO Auto-generated method stub
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer player = new MediaPlayer();
        player.setDataSource(this, alert);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            player.setLooping(false);
            player.prepare();
            player.start();
        }
        return player;
    }

    private void startNFC_Listener() {
        // 开始监听NFC设备是否连接，如果连接就发pi意图
        nfcAdapter.enableForegroundDispatch(this, pi,
                new IntentFilter[]{tagDetected}, null);
    }

    private void stopNFC_Listener() {
        // 停止监听NFC设备是否连接
        nfcAdapter.disableForegroundDispatch(this);
    }


}
