package com.alipay.mobile.nebulacore.dev.bugme;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class H5BugMeViewPageAdapter extends PagerAdapter {
    private H5BugmeConsole a;

    public H5BugMeViewPageAdapter(H5BugmeConsole console) {
        this.a = console;
    }

    public int getCount() {
        return this.a.getTabSize();
    }

    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.a.getSubContentView(position);
        container.addView(view);
        return view;
    }

    public CharSequence getPageTitle(int position) {
        return H5BugmeConsole.a[position];
    }

    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}
