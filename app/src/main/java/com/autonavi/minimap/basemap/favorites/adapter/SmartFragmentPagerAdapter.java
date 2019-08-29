package com.autonavi.minimap.basemap.favorites.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class SmartFragmentPagerAdapter extends PagerAdapter {
    private static int a = 1;
    private final int b;
    private final FragmentManager c;
    private FragmentTransaction d = null;
    private Fragment e = null;

    public abstract Fragment a(int i);

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable saveState() {
        return null;
    }

    public void startUpdate(ViewGroup viewGroup) {
    }

    public SmartFragmentPagerAdapter(FragmentManager fragmentManager) {
        this.c = fragmentManager;
        this.b = a;
        a++;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        if (this.d == null) {
            this.d = this.c.beginTransaction();
        }
        long j = (long) i;
        Fragment findFragmentByTag = this.c.findFragmentByTag(a(viewGroup.getId(), j, this.b));
        if (findFragmentByTag != null) {
            this.d.attach(findFragmentByTag);
        } else {
            findFragmentByTag = a(i);
            this.d.add(viewGroup.getId(), findFragmentByTag, a(viewGroup.getId(), j, this.b));
        }
        if (findFragmentByTag != this.e) {
            findFragmentByTag.setMenuVisibility(false);
            findFragmentByTag.setUserVisibleHint(false);
        }
        return findFragmentByTag;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (this.d == null) {
            this.d = this.c.beginTransaction();
        }
        this.d.detach((Fragment) obj);
    }

    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        if (fragment != this.e) {
            if (this.e != null) {
                this.e.setMenuVisibility(false);
                this.e.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            this.e = fragment;
        }
    }

    public void finishUpdate(ViewGroup viewGroup) {
        if (this.d != null) {
            this.d.commitAllowingStateLoss();
            this.d = null;
            this.c.executePendingTransactions();
        }
    }

    public boolean isViewFromObject(View view, Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    private static String a(int i, long j, int i2) {
        StringBuilder sb = new StringBuilder("android:switcher:");
        sb.append(i);
        sb.append(":");
        sb.append(j);
        sb.append(":");
        sb.append(i2);
        return sb.toString();
    }
}
