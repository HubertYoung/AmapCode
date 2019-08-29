package com.autonavi.map.widget;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class RecyclablePagerAdapter<T> extends PagerAdapter {
    private List<T> mItemList = new ArrayList();
    private int mRealCount;

    public List<T> getRealItems() {
        return null;
    }

    public Object instantiateItemFromRecycler(ViewGroup viewGroup, View view, int i) {
        return null;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return false;
    }

    public void refreshView(View view, int i) {
    }

    public RecyclablePagerAdapter(List<T> list) {
        fakeItemList(list);
    }

    public void fakeItemList(List<T> list) {
        int i;
        this.mItemList.clear();
        if (list == null) {
            i = 0;
        } else {
            i = list.size();
        }
        this.mRealCount = i;
        if (list == null || list.size() <= 0 || list.size() >= 5) {
            this.mItemList.addAll(list);
            return;
        }
        this.mItemList.addAll(list);
        int size = 5 - list.size();
        int size2 = (size / list.size()) + (size % list.size() > 0 ? 1 : 0);
        for (int i2 = 0; i2 < size2; i2++) {
            this.mItemList.addAll(0, list);
        }
    }

    public final int getCount() {
        if (this.mItemList == null) {
            return 0;
        }
        return this.mItemList.size();
    }

    public int getRealCount() {
        return this.mRealCount;
    }
}
