package com.autonavi.minimap.drive.navi.navitts.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> a = null;
    public FragmentManager b = null;

    public CustomFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.b = fragmentManager;
    }

    public int getCount() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public Fragment getItem(int i) {
        if (this.a == null || i < 0 || i >= this.a.size()) {
            return null;
        }
        return this.a.get(i);
    }
}
