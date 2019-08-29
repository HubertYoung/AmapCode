package com.autonavi.minimap.route.bus.extbus.util;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public final class ViewPagerUtil {

    public static class SimplePagerAdapter extends PagerAdapter {
        public List<View> a;

        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public SimplePagerAdapter(List<View> list) {
            this.a = new ArrayList(list);
        }

        public final int getCount() {
            return this.a.size();
        }

        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            View view = this.a.get(i);
            viewGroup.addView(view);
            return view;
        }

        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView(this.a.get(i));
        }
    }
}
