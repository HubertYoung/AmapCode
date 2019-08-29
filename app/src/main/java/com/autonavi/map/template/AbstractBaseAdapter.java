package com.autonavi.map.template;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.HeterogeneousExpandableList;
import android.widget.ListAdapter;
import android.widget.SpinnerAdapter;

public abstract class AbstractBaseAdapter implements ExpandableListAdapter, HeterogeneousExpandableList, ListAdapter, SpinnerAdapter {
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public boolean areAllItemsEnabled() {
        return true;
    }

    public int getChildType(int i, int i2) {
        return 0;
    }

    public int getChildTypeCount() {
        return 1;
    }

    public long getCombinedChildId(long j, long j2) {
        return ((j & 2147483647L) << 32) | Long.MIN_VALUE | (j2 & -1);
    }

    public long getCombinedGroupId(long j) {
        return (j & 2147483647L) << 32;
    }

    public int getGroupType(int i) {
        return 0;
    }

    public int getGroupTypeCount() {
        return 1;
    }

    public int getItemViewType(int i) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isEnabled(int i) {
        return true;
    }

    public void onGroupCollapsed(int i) {
    }

    public void onGroupExpanded(int i) {
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        if (dataSetObserver != null) {
            this.mDataSetObservable.unregisterObserver(dataSetObserver);
        }
    }

    public void notifyDataSetChanged() {
        this.mDataSetObservable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        this.mDataSetObservable.notifyInvalidated();
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}
