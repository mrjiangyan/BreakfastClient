package com.breakfast.client.base;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breakfast.client.R;
import com.breakfast.client.base.contract.BasePresenter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by Steven on 2017/4/9.
 */

public class BaseRecycleViewAdapter<T> extends SwipeMenuAdapter<BaseHolder> {

    private LayoutInflater mInflater;
    private List<T> mList = new ArrayList<>(BasePresenter.PageSize);
    private Func1<View, BaseHolder<T>> func;
    private View.OnClickListener mListener;
    private int layoutId;
    private BaseHolder.OnItemClickListener mOnItemClickListener;


    public BaseRecycleViewAdapter(Context context, int layoutId, Func1<View, BaseHolder<T>> func, View.OnClickListener listener) {
        mInflater = LayoutInflater.from(context);
        TypedValue mTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mListener = listener;
        this.layoutId = layoutId;
        this.func = func;
    }

    public void setOnItemClickListener(BaseHolder.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return  mInflater.inflate(layoutId, parent, false);
    }

    @Override
    public BaseHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        BaseHolder viewHolder =func.call(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        T data = getItem(position);
        holder.bind(position, data, mListener);
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    private T getItem(int position) {
        if (mList == null)
            return null;
        return mList.get(position);
    }

    public void setList(List<T> list) {
        mList = list;
    }

    public List<T> getList()
    {
        return mList;
    }

}
