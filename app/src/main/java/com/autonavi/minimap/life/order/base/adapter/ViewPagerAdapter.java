package com.autonavi.minimap.life.order.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<Fragment> a = null;

    public ViewPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> arrayList) {
        super(fragmentManager);
        this.a = arrayList;
    }

    public int getCount() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public Fragment getItem(int i) {
        if (this.a == null || i >= this.a.size()) {
            return null;
        }
        return this.a.get(i);
    }
}
