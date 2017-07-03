package com.breakfast.client.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Steven on 2017/4/9.
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {
    private View mView;
    OnItemClickListener mOnItemClickListener;

    protected BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mView=itemView;
    }

    public void bind(int position,T data, View.OnClickListener listener)
    {
        if(mView!= null)
        {
            mView.setOnClickListener(listener);
            mView.setTag(position);

        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface  OnItemClickListener {

        void onItemClick(int position);

    }

}
