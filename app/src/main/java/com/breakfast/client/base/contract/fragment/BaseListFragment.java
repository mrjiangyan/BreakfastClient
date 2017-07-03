package com.breakfast.client.base.contract.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.breakfast.client.base.BaseFragment;
import com.breakfast.client.R;
import com.breakfast.client.base.BaseRecycleViewAdapter;
import com.breakfast.client.base.ItemClickListener;
import com.breakfast.client.base.contract.BaseListPresenter;
import com.breakfast.client.util.DialogUtils;
import com.breakfast.client.util.StatisticsUtils;
import com.breakfast.client.view.EndlessRecyclerViewScrollListener;
import com.breakfast.library.data.entity.BaseModel;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;

/**
 * Created by Steven on 2017/5/1.
 */

public class BaseListFragment<T extends BaseModel> extends BaseFragment {
    @Override
    protected CharSequence getTitle() {
        return null;
    }

    @BindView(R.id.list)
    protected SwipeMenuRecyclerView recycler;

    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    public boolean autoRefresh=true;

    //适配器
    protected BaseRecycleViewAdapter mAdapter;
    private EndlessRecyclerViewScrollListener listener;
    protected ItemClickListener<T> itemClickListener;
    protected boolean isSelectMode = false;
    protected BaseListPresenter<T> mPresenter;

    public String deleteString="请确认是否删除当前教务吗？删除以后将无法看到该数据";



    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    public SwipeMenuCreator swipeMenuCreator = (swipeLeftMenu, swipeRightMenu, viewType) -> {
        int width = getResources().getDimensionPixelSize(R.dimen.button_left_right_padding);

        // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        // 添加左侧的，如果不添加，则左侧不会出现菜单。
        {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getContext())
                    .setBackgroundColor(getResources().getColor(R.color.colorAccent))
                    .setImage(R.drawable.ic_border_color_black_24dp)
                    .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。


        }


    };

    /**
     * 菜单点击监听。
     */
    public OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            int size=mPresenter.getList().size();
            int index=adapterPosition;
            if(index>=size)
                return;

            mActivity.statistics(StatisticsUtils.TYPE_TOUCH,"DELETE");
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                new MaterialDialog.Builder(getContext())
                        .title("删除确认")
                        .content(deleteString)
                        .positiveText(R.string.cancel)
                        .negativeText("删除")
                        .positiveColorRes(R.color.color_accent_red)
                        .negativeColorRes(R.color.colorPrimary)
                        .onNegative((dd, who) -> {
                            // TODO 调用删除逻辑
                            mPresenter.delete(mPresenter.getList().get(index).getId());
                        })
                        .show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    //上拉加载回调
    public void onFinish(int page, long totalRecords) {
        handler.post(() -> swipeRefreshLayout.setRefreshing(false));
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (autoRefresh && !hidden) {
            handler.post(() -> swipeRefreshLayout.setRefreshing(true));
            mPresenter.addMore(1);
        }
        super.onHiddenChanged(hidden);
    }



    protected void addMore(final int page) {
        if (page == 1) {
            linearLayoutManager.scrollToPosition(0);
        }
        mPresenter.addMore(page);

    }

    protected void showData() {
        if (mPresenter != null && mPresenter.getList().size() > 0) {
            listener.setCurrentPage(1);
            mAdapter.setList(mPresenter.getList());
            mAdapter.notifyDataSetChanged();
        } else {
            // Load the first page
            addMore(1);
        }
    }


    @Override
    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(linearLayoutManager);

        //设置默认动画
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        recycler.setItemAnimator(new DefaultItemAnimator());
        this.listener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                addMore(page);
            }
        };
        recycler.addOnScrollListener(this.listener);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            addMore(1);//下拉刷新
        });
    }



    public void setPresenter(BaseListPresenter presenter) {

    }


    public void showErrorView(String errMsg, @Nullable View.OnClickListener onClickListener) {
        DialogUtils.showToast(getContext(),errMsg, DialogUtils.ToastType.error);
    }




    public void showProcessing() {

    }


    public void stopRefresh()
    {
        handler.post(() -> swipeRefreshLayout.setRefreshing(false));

    }



    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
