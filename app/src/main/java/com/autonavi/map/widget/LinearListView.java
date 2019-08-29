package com.autonavi.map.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class LinearListView extends LinearLayout {
    private static final int INVALID = -1;
    /* access modifiers changed from: private */
    public BaseAdapter adapter;
    private DataSetObserver dataSetObserver;
    private int dividerViewResourceId;
    private View footerView;
    private View headerView;
    /* access modifiers changed from: private */
    public OnItemClickListener itemClickListener;
    private final LayoutInflater layoutInflater;

    class AdapterDataSetObserver extends DataSetObserver {
        AdapterDataSetObserver() {
        }

        public void onChanged() {
            super.onChanged();
            LinearListView.this.resetList();
            LinearListView.this.refreshList();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Object obj, View view, int i);
    }

    public LinearListView(Context context) {
        this(context, null);
    }

    public LinearListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dividerViewResourceId = -1;
        this.layoutInflater = LayoutInflater.from(getContext());
        setOrientation(1);
    }

    public void setDividerView(int i) {
        if (i < 0) {
            throw new IllegalStateException("Resource Id cannot be negative");
        }
        this.dividerViewResourceId = i;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public void setHeaderView(View view) {
        this.headerView = view;
    }

    public void setHeaderView(int i) {
        this.headerView = this.layoutInflater.inflate(i, this, false);
    }

    public void setFooterView(View view) {
        this.footerView = view;
    }

    public void setFooterView(int i) {
        this.footerView = this.layoutInflater.inflate(i, this, false);
    }

    public void setAdapter(BaseAdapter baseAdapter) {
        if (baseAdapter == null) {
            throw new NullPointerException("Adapter may not be null");
        }
        if (!(this.adapter == null || this.dataSetObserver == null)) {
            this.adapter.unregisterDataSetObserver(this.dataSetObserver);
        }
        this.adapter = baseAdapter;
        this.dataSetObserver = new AdapterDataSetObserver();
        this.adapter.registerDataSetObserver(this.dataSetObserver);
        resetList();
        refreshList();
    }

    /* access modifiers changed from: private */
    public void refreshList() {
        if (this.headerView != null) {
            addView(this.headerView);
        }
        int count = this.adapter.getCount();
        for (final int i = 0; i < count; i++) {
            final View view = this.adapter.getView(i, null, this);
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (LinearListView.this.itemClickListener != null) {
                        LinearListView.this.itemClickListener.onItemClick(LinearListView.this.adapter.getItem(i), view, i);
                    }
                }
            });
            addView(view);
            if (!(this.dividerViewResourceId == -1 || i == count - 1)) {
                addView(this.layoutInflater.inflate(this.dividerViewResourceId, this, false));
            }
        }
        if (this.footerView != null) {
            addView(this.footerView);
        }
    }

    /* access modifiers changed from: private */
    public void resetList() {
        removeAllViews();
        invalidate();
    }
}
