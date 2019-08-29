package com.autonavi.widget.ui.filter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class FilterBaseAdapter<T> extends ArrayAdapter<T> {
    ers<T> mFilterAdapter;
    String mTag;
    T selectItem = null;

    /* access modifiers changed from: protected */
    public abstract int getResourceId(int i);

    /* access modifiers changed from: protected */
    public abstract void setViewData(View view, int i);

    public FilterBaseAdapter(Context context, ers<T> ers, String str) {
        super(context, 0, ers.a(str));
        this.mFilterAdapter = ers;
        this.mTag = str;
    }

    public FilterBaseAdapter(Context context, ers<T> ers, String str, T t) {
        super(context, 0, ers.a(t, str));
        this.mFilterAdapter = ers;
        this.mTag = str;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view);
    }

    /* access modifiers changed from: protected */
    public View getView(int i, View view) {
        if (view == null) {
            view = View.inflate(getContext(), getResourceId(i), null);
        }
        try {
            setViewData(view, i);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("setViewData error ");
            sb.append(getClass().getSimpleName());
            sb.append(":::");
            sb.append(e.toString());
        }
        return view;
    }

    public T getSelectItem() {
        return this.selectItem;
    }

    public void setSelectItem(T t) {
        this.selectItem = t;
    }
}
