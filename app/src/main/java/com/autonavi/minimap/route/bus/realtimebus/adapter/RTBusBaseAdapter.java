package com.autonavi.minimap.route.bus.realtimebus.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class RTBusBaseAdapter<DT> extends BaseAdapter implements dxz<DT> {
    protected ArrayList<DT> itemList;
    protected Context mContext;
    private Bundle mDataExt;
    protected int mDefaultItemViewId;
    protected dvq mInteraction;
    protected LayoutInflater mLayoutInflater;

    public long getItemId(int i) {
        return (long) i;
    }

    public RTBusBaseAdapter(Context context) {
        this(context, 0);
    }

    public RTBusBaseAdapter(Context context, int i) {
        this.mDefaultItemViewId = 0;
        this.mDataExt = new Bundle();
        this.mContext = context;
        this.itemList = new ArrayList<>(0);
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDefaultItemViewId = i;
    }

    public void clearData() {
        if (this.itemList != null) {
            this.itemList.clear();
        }
        notifyDataSetChanged();
    }

    public DT getData(int i) {
        return getItem(i);
    }

    public void setListData(List<DT> list) {
        if (this.itemList == null) {
            this.itemList = new ArrayList<>(0);
        }
        this.itemList.clear();
        if (list != null) {
            this.itemList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void appendListData(List<DT> list) {
        if (this.itemList == null) {
            this.itemList = new ArrayList<>(0);
        }
        if (list != null) {
            this.itemList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.itemList != null) {
            return this.itemList.size();
        }
        return 0;
    }

    public Object getItem(int i) {
        if (i < 0 || i >= getCount()) {
            return null;
        }
        return this.itemList.get(i);
    }

    /* access modifiers changed from: protected */
    public dxy getItemView(int i, ViewGroup viewGroup) {
        if (this.mDefaultItemViewId != 0) {
            return (dxy) this.mLayoutInflater.inflate(this.mDefaultItemViewId, viewGroup, false);
        }
        return null;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        dxy dxy;
        if (i < 0 || i > getCount()) {
            return null;
        }
        if (view instanceof dxy) {
            dxy = (dxy) view;
            Object data = getData(i);
            if (data != null && dxy.isDataChanged(data)) {
                dxy.bindData(i, data, this.mDataExt);
            }
        } else {
            dxy = getItemView(i, viewGroup);
            Object data2 = getData(i);
            if (!(data2 == null || dxy == null)) {
                dxy.bindData(i, data2, this.mDataExt);
            }
        }
        if (dxy != null) {
            dxy.setOnViewClickListener(this);
        }
        return (View) dxy;
    }

    public void setListItemInteraction(dvq dvq) {
        this.mInteraction = dvq;
    }

    public void clearDataExtra() {
        if (this.mDataExt == null) {
            this.mDataExt = new Bundle();
        } else {
            this.mDataExt.clear();
        }
    }

    public boolean hasExtra(String str) {
        return this.mDataExt != null && this.mDataExt.containsKey(str);
    }

    public void removeExtra(String str) {
        if (this.mDataExt != null) {
            this.mDataExt.remove(str);
        }
    }

    public void putExtra(String str, int i) {
        if (this.mDataExt == null) {
            this.mDataExt = new Bundle();
        }
        this.mDataExt.putInt(str, i);
    }

    public void putExtra(String str, boolean z) {
        if (this.mDataExt == null) {
            this.mDataExt = new Bundle();
        }
        this.mDataExt.putBoolean(str, z);
    }

    public void putExtra(String str, String str2) {
        if (this.mDataExt == null) {
            this.mDataExt = new Bundle();
        }
        this.mDataExt.putString(str, str2);
    }
}
