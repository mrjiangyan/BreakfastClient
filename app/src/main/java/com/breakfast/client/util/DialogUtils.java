package com.breakfast.client.util;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cocosw.bottomsheet.BottomSheet;
import com.breakfast.client.R;
import com.breakfast.library.util.BitmapUtils;

import es.dmoral.toasty.Toasty;


/**
 * Created by Steven on 2017/4/23.
 */

public class DialogUtils {

    public static void showToast(Context context, String message, ToastType type) {
        if(type==ToastType.error)
        {
            Toasty.error(context,message, Toast.LENGTH_LONG,true).show();
        }
        else if(type==ToastType.info)
        {
            Toasty.error(context,message, Toast.LENGTH_SHORT,true).show();
        }
        else if(type==ToastType.success)
        {
            Toasty.success(context,message, Toast.LENGTH_SHORT,true).show();
        }
        else if(type==ToastType.normal)
        {
            Toasty.normal(context,message, Toast.LENGTH_SHORT).show();
        }
        else if(type==ToastType.warning)
        {
            Toasty.warning(context,message, Toast.LENGTH_SHORT,true).show();
        }
    }




    public static BottomSheet BuildBottomSheet(Context context, @MenuRes int menuResId, MenuItem.OnMenuItemClickListener listener)
    {
        return new BottomSheet.Builder(context, R.style.BottomSheet_StyleDialog).title("操作").sheet(menuResId)
                .icon(BitmapUtils.getRoundedBitmap(context,R.drawable.logo)).listener(listener).build();
    }

    public static BottomSheet BuildBottomSheet(Context context,@StringRes int resId, @MenuRes int menuResId, MenuItem.OnMenuItemClickListener listener)
    {
        return new BottomSheet.Builder(context, R.style.BottomSheet_StyleDialog).title(resId).sheet(menuResId)
                .icon(BitmapUtils.getRoundedBitmap(context,R.drawable.logo)).listener(listener).build();
    }

    public static BottomSheet BuildBottomSheet(Context context,String title, @MenuRes int menuResId, MenuItem.OnMenuItemClickListener listener)
    {
        return new BottomSheet.Builder(context, R.style.BottomSheet_StyleDialog).title(title).sheet(menuResId)
                .icon(BitmapUtils.getRoundedBitmap(context,R.drawable.logo)).listener(listener).build();
    }

    //双按钮的操作
    public static  MaterialDialog.Builder BuildDialog(Context context, @StringRes int titleResId, @StringRes int contentResId,
                                              @StringRes int positiveResId, @StringRes int negativeResId,
                                              MaterialDialog.SingleButtonCallback negativeListener,MaterialDialog.SingleButtonCallback positiveListener)
    {
        MaterialDialog.Builder builder= new MaterialDialog.Builder(context)
                .title(titleResId)
                .content(contentResId)
                .positiveText(positiveResId)
                .positiveColorRes(R.color.color_accent_red)
                .negativeColorRes(R.color.colorPrimary)
                .onPositive(positiveListener);
        if(negativeListener!= null)
        {
            builder.negativeText(negativeResId) .onNegative(negativeListener);
        }
        return builder;
    }

    public static  MaterialDialog.Builder BuildDialog(Context context, @StringRes int titleResId, String content,
                                                      @StringRes int positiveResId, @StringRes int negativeResId,
                                                      MaterialDialog.SingleButtonCallback negativeListener,MaterialDialog.SingleButtonCallback positiveListener)
    {
        return new MaterialDialog.Builder(context)
                .title(titleResId)
                .content(content)
                .positiveText(positiveResId)
                .negativeText(negativeResId)
                .positiveColorRes(R.color.color_accent_red)
                .negativeColorRes(R.color.colorPrimary)
                .onNegative(negativeListener)
                .onPositive(positiveListener);
    }

    //单个按钮的操作
    public static  MaterialDialog.Builder BuildDialog(Context context, @StringRes int titleResId, @StringRes int contentResId,
                                                      @StringRes int positiveResId, MaterialDialog.SingleButtonCallback positiveListener)
    {
        return BuildDialog(context,titleResId,contentResId,positiveResId,-1,null,positiveListener);
    }

    public enum ToastType
    {
        error,
        success,
        info,
        warning,
        normal,
        custom
    }

}
