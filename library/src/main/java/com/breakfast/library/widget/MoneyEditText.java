package com.breakfast.library.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.breakfast.library.util.DecimalDigitsInputFilter;
import com.breakfast.library.util.RegexUtils;

import java.text.DecimalFormat;

/**
 * Created by feng on 16/1/8.
 */
public class MoneyEditText extends AppCompatEditText {

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MoneyEditText(Context context) {
    super(context);
    this.init(context,null);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MoneyEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.init(context,attrs);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.init(context,attrs);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void init(Context context, AttributeSet attrs) {
    int maxLength=4;
    if(getFilters()!= null)
    {
        for(InputFilter filter :getFilters())
        {
            if(filter instanceof InputFilter.LengthFilter)
            {
              maxLength=((InputFilter.LengthFilter)filter).getMax();
            }
        }
    }
    setFilters(new InputFilter[] { new DecimalDigitsInputFilter(maxLength, 2) });
    this.setSelectAllOnFocus(true);

  }

  /**
   * 获取金额
   */
  public double getMoney() {
    String money = getText().toString().trim().replace("￥", "");
    double moneyD = 0;
    try {
      moneyD = Double.parseDouble(money);
    } catch (Exception e) {

    }
    return moneyD;
  }

  public void setAmount(double money) {
    DecimalFormat df;

    //四舍五入
    if ((int) money == money) {
      df = new DecimalFormat("######0");
    } else {
      df = new DecimalFormat("######0.00");
    }
    String text = df.format(money);
    setText(text);
    this.setEnabled(false);
  }

  public void setText(String money) {
    money = setScale(money);
    if (RegexUtils.isMoney(money, true, true) != 0) {
      super.setText(null);
      return;
    }
    setMoney(Double.parseDouble(money));
  }

  @NonNull private String setScale(String money) {
    if (money.contains(".") && money.indexOf(".") + 2 < money.length() - 1) {
      money = money.substring(0, money.indexOf(".") + 3);
    }
    return money;
  }

  public void setMoney(double money) {
    DecimalFormat df;

    if ((int) money == money) {
      df = new DecimalFormat("######0");
    } else {
      df = new DecimalFormat("######0.00");
    }
    String text = df.format(money);
    super.setText(text);
  }

  /**
   *
   */
  public void setSignedNum(int type) {
    if(type==0)//禁止输入负数
      setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
    else if(type==1)
    {
      setKeyListener(DigitsKeyListener.getInstance("0123456789"));

    }
    else
    /**
     * 允许输入负数
     */
      setInputType(InputType.TYPE_CLASS_NUMBER
              | InputType.TYPE_NUMBER_FLAG_DECIMAL
              | InputType.TYPE_NUMBER_FLAG_SIGNED);
  }

  /**
   * 设置晃动动画
   */
  public void setShakeAnimation() {
    setAnimation(MoneyEditText.shakeAnimation(5));
  }

  /**
   * 晃动动画
   *
   * @param counts 1秒钟晃动多少下
   */
  private static Animation shakeAnimation(int counts) {
    Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
    translateAnimation.setInterpolator(new CycleInterpolator(counts));
    translateAnimation.setDuration(1000);
    return translateAnimation;
  }


}

