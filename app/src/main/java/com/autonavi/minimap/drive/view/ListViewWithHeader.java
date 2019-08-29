package com.autonavi.minimap.drive.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.amap.bundle.drivecommon.view.ListViewWithHeaderAdapter;
import com.amap.bundle.drivecommon.view.ListViewWithHeaderAdapter.a;

public abstract class ListViewWithHeader extends ListView implements OnItemClickListener, a {
    private int mFooterHeight;
    private View mFooterView;
    private View mHeaderView;
    private OnItemClickListener mOnItemClickListener;

    /* access modifiers changed from: protected */
    public abstract int getFooterHeight();

    /* access modifiers changed from: protected */
    public abstract View initFooterView();

    /* access modifiers changed from: protected */
    public abstract View initHeaderView();

    public ListViewWithHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public void initView() {
        this.mHeaderView = initHeaderView();
        this.mFooterView = initFooterView();
        this.mFooterHeight = getFooterHeight();
        setupHeaderView();
        setupFooterView();
        setOnItemClickListener(this);
    }

    private void setupHeaderView() {
        if (this.mHeaderView != null) {
            addHeaderView(this.mHeaderView);
            setHeaderDividersEnabled(true);
        }
    }

    private void setupFooterView() {
        if (this.mFooterView != null) {
            addFooterView(this.mFooterView);
            setFooterDividersEnabled(false);
        }
    }

    /* access modifiers changed from: protected */
    public void setOnChildItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (i > 0 && this.mOnItemClickListener != null) {
            OnItemClickListener onItemClickListener = this.mOnItemClickListener;
            if (this.mHeaderView != null) {
                i--;
            }
            onItemClickListener.onItemClick(adapterView, view, i, j);
        }
    }

    private void goneFooterView() {
        if (this.mFooterView.getPaddingTop() != (-this.mFooterHeight)) {
            this.mFooterView.setPadding(0, -this.mFooterHeight, 0, 0);
        }
        this.mFooterView.setVisibility(8);
    }

    private void visiableFooterView() {
        if (this.mFooterView.getPaddingTop() != 0) {
            resetFooterPadding();
        }
        this.mFooterView.setVisibility(0);
    }

    private void resetFooterPadding() {
        this.mFooterView.setPadding(0, 0, 0, 0);
    }

    public void onViewChange() {
        BaseAdapter baseAdapter = (BaseAdapter) ((HeaderViewListAdapter) getAdapter()).getWrappedAdapter();
        if (baseAdapter != null) {
            if (baseAdapter.getCount() <= 0) {
                goneFooterView();
                return;
            }
            visiableFooterView();
        }
    }

    @Deprecated
    public void setAdapter(ListAdapter listAdapter) {
        throw new RuntimeException("please use setAdapter with ListViewWithHeaderAdapter !!!");
    }

    public void setAdapter(ListViewWithHeaderAdapter listViewWithHeaderAdapter) {
        if (listViewWithHeaderAdapter != null) {
            listViewWithHeaderAdapter.setOnNotifyViewChangeListener(this);
            super.setAdapter(listViewWithHeaderAdapter);
            listViewWithHeaderAdapter.notifyDataSetChanged();
        }
    }
}
