package com.autonavi.map.template;

import com.autonavi.map.template.AbstractViewHolderAdapter.a;
import java.util.List;

public abstract class AbstractViewHolderBaseAdapter<T, VH extends a> extends AbstractViewHolderAdapter<VH> {
    private List<T> mList;

    public final long getItemId(int i) {
        return 0;
    }

    public abstract void onBindViewHolderWithData(VH vh, T t, int i, int i2);

    public final void setData(List<T> list) {
        if (list != null) {
            this.mList = list;
            return;
        }
        throw new IllegalArgumentException("AbstractBaseAdapter don't support null data.");
    }

    public final void setDataAndChangeDataSet(List<T> list) {
        this.mList = list;
        if (this.mList != null) {
            notifyDataSetChanged();
            return;
        }
        throw new IllegalArgumentException("AbstractBaseAdapter don't support null data.");
    }

    public final void clearDataAndChangeDataSet() {
        if (this.mList != null) {
            this.mList.clear();
            notifyDataSetChanged();
        }
    }

    public final List<T> getData() {
        return this.mList;
    }

    public T getDataByPos(int i) {
        if (this.mList == null) {
            return null;
        }
        int size = this.mList.size();
        if (size == 0 || i >= size || i < 0) {
            return null;
        }
        return this.mList.get(i);
    }

    public int getCount() {
        if (this.mList == null) {
            return 0;
        }
        return this.mList.size();
    }

    public final T getItem(int i) {
        if (this.mList == null || this.mList.size() <= i) {
            return null;
        }
        return this.mList.get(i);
    }

    public final void onBindViewHolder(VH vh, int i, int i2) {
        onBindViewHolderWithData(vh, getItem(i), i, i2);
    }

    public int getGroupCount() {
        if (this.mList == null) {
            return 0;
        }
        return this.mList.size();
    }
}
