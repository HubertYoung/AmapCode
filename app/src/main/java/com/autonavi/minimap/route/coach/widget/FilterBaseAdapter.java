package com.autonavi.minimap.route.coach.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class FilterBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mListData;
    protected T mSelectedItem;

    /* access modifiers changed from: protected */
    public abstract void bindViewData(int i, View view);

    public long getItemId(int i) {
        return (long) i;
    }

    /* access modifiers changed from: protected */
    public abstract int getResourceId(int i);

    public FilterBaseAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        if (this.mListData != null) {
            return this.mListData.size();
        }
        return 0;
    }

    public Object getItem(int i) {
        if (this.mListData == null || this.mListData.size() <= i) {
            return null;
        }
        return this.mListData.get(i);
    }

    @NonNull
    public View getView(int i, View view, ViewGroup viewGroup) {
        return initView(i, view);
    }

    /* access modifiers changed from: protected */
    public View initView(int i, View view) {
        if (view == null) {
            view = View.inflate(this.mContext, getResourceId(i), null);
        }
        try {
            bindViewData(i, view);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder("initView position:");
            sb.append(i);
            sb.append(", Exception:");
        }
        return view;
    }

    public void setSelectedItem(T t) {
        this.mSelectedItem = t;
    }

    public T getSelectedItem() {
        return this.mSelectedItem;
    }

    public void setListData(List<T> list) {
        this.mListData = new ArrayList();
        this.mListData.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getListData() {
        return this.mListData;
    }
}
