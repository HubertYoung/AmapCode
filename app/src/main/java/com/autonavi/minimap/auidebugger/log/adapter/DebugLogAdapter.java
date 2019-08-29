package com.autonavi.minimap.auidebugger.log.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class DebugLogAdapter extends PagerAdapter {
    private ArrayList<View> a;

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public int getCount() {
        return this.a.size();
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View view = this.a.get(i);
        viewGroup.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (i < this.a.size()) {
            viewGroup.removeView(this.a.get(i));
        }
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
