package com.alipay.mobile.nebulacore.tabbar;

import com.alipay.mobile.nebulacore.ui.H5Fragment;
import java.util.HashMap;
import java.util.Map;

public class H5SessionTabManager {
    private int a = -1;
    private Map<Integer, H5Fragment> b = new HashMap();

    public int getCurrentIndex() {
        return this.a;
    }

    public void setCurrentIndex(int currentIndex) {
        this.a = currentIndex;
    }

    public void addTabFragment(int index, H5Fragment h5Fragment) {
        if (this.b != null) {
            this.b.put(Integer.valueOf(index), h5Fragment);
        }
    }

    public H5Fragment getTabFragmentByIndex(int index) {
        if (this.b != null) {
            return this.b.get(Integer.valueOf(index));
        }
        return null;
    }

    public Map<Integer, H5Fragment> getTabFragments() {
        return this.b;
    }

    public void clearTabFragments() {
        this.a = -1;
        if (this.b != null) {
            this.b.clear();
        }
    }

    public int countTabFragments() {
        if (this.b != null) {
            return this.b.size();
        }
        return 0;
    }

    public H5Fragment getCurrentFragment() {
        if (this.b != null) {
            return this.b.get(Integer.valueOf(this.a));
        }
        return null;
    }
}
