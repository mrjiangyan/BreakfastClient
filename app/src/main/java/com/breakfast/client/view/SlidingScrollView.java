package com.breakfast.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.breakfast.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by feng on 16/8/25.
 */
public class SlidingScrollView extends LinearLayout {
  @BindView(R.id.layout_content)
  LinearLayout layout_content;

  @OnClick({ R.id.btn_close }) public void onClick(View view) {
    this.setVisibility(View.GONE);
  }


  public SlidingScrollView(Context context) {
    super(context);
    init(context);
  }

  public SlidingScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    View.inflate(context, R.layout.sliding_scroll_view, this);
    ButterKnife.bind(this);
  }

  public void setContentView(View view)
  {
    if(layout_content.getChildCount()>0)
    {
      layout_content.removeAllViews();
    }
    LinearLayout.LayoutParams prams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    layout_content.addView(view,prams);
  }

}
