package com.breakfast.client.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.breakfast.client.R;
import com.breakfast.library.util.ResourceUtils;

import java.lang.reflect.Method;

/**
 * 带删除按钮的EditText
 */
public class DeletableEditText extends AppCompatEditText
    implements OnFocusChangeListener, TextWatcher {
  public DeletableEditText(Context context) {
    super(context);
    this.init();
  }

  public DeletableEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.init();
  }

  public DeletableEditText(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.init();
  }

  /**
   * 删除按钮的引用
   */
  private Drawable mClearDrawable;
  /**
   * 控件是否有焦点
   */
  private boolean hasFocus;


  private void init() {
    //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
    this.mClearDrawable = this.getCompoundDrawables()[2];
    if (!this.isInEditMode()) {
      if (this.mClearDrawable == null) {
        this.mClearDrawable = ResourceUtils.getDrawable(R.drawable.ic_cancel_black_24dp);
      }

      assert this.mClearDrawable != null;
      this.mClearDrawable.setBounds(0, 0, this.mClearDrawable != null ? this.mClearDrawable.getIntrinsicWidth() : 0,
              this.mClearDrawable != null ? this.mClearDrawable.getIntrinsicHeight() : 0);
    }
    //默认设置隐藏图标
    this.setClearIconVisible(false);
    //设置焦点改变的监听
    this.setOnFocusChangeListener(this);
    //设置输入框里面内容发生改变的监听
    this.addTextChangedListener(this);
  }

  /**
   * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
   * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
   * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
   */
  @Override public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (this.getCompoundDrawables()[2] != null) {

        boolean touchable = event.getX() > this.getWidth() - this.getTotalPaddingRight()
            && event.getX() < this.getWidth() - this.getPaddingRight();

        if (touchable) {
          setText("");
          this.setShowSoftInputOnFocusInt(false);
        } else {
          this.setShowSoftInputOnFocusInt(true);
        }
      } else {
        this.setShowSoftInputOnFocusInt(true);
      }
    }

    return super.onTouchEvent(event);
  }

  private void setShowSoftInputOnFocusInt(boolean show) {
    if (VERSION.SDK_INT < 21) {
      try {
        Method setShowSoftInputOnFocus =
            this.getClass().getMethod("setShowSoftInputOnFocus", boolean.class);
        setShowSoftInputOnFocus.setAccessible(true);
        setShowSoftInputOnFocus.invoke(this, show);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } else {
      this.setShowSoftInputOnFocus(show);
    }
  }

  /**
   * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
   */
  @Override public void onFocusChange(View v, boolean hasFocus) {
    this.hasFocus = hasFocus;
    if (hasFocus) {
      this.setClearIconVisible(this.getText().length() > 0);
    } else {
      this.setClearIconVisible(false);
    }
  }

  /**
   * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
   */
  private void setClearIconVisible(boolean visible) {
    Drawable right = visible ? this.mClearDrawable : null;
    this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], right,
        this.getCompoundDrawables()[3]);
  }

  /**
   * 当输入框里面内容发生变化的时候回调的方法
   */
  @Override public void onTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override public void afterTextChanged(Editable s) {
    if (this.hasFocus) {
      this.setClearIconVisible(s.length() > 0);
    }
  }

  /**
   * 设置晃动动画
   */
  public void setShakeAnimation() {
    setAnimation(DeletableEditText.shakeAnimation(5));
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
