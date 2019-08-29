package com.autonavi.map.search.imagepreview.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class ImageDetailAdapter extends PagerAdapter {
    public List<cal> a = new ArrayList();
    private byd b;
    private int c = 0;
    private int d = 0;

    public ImageDetailAdapter(byd byd) {
        this.b = byd;
    }

    public final void a(@NonNull List<cal> list) {
        if (list != null) {
            this.c = list.size();
            if (this.c == 1) {
                this.d = this.c;
            } else {
                this.d = this.c + 2;
            }
            if (list.size() > 0) {
                this.a.add(list.get(list.size() - 1));
                for (cal add : list) {
                    this.a.add(add);
                }
                this.a.add(list.get(0));
            }
        }
    }

    public int getCount() {
        return this.d;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        if (this.c == 0) {
            return null;
        }
        View a2 = this.b.a(i % this.c);
        if (a2 != null) {
            viewGroup.addView(a2);
        }
        return a2;
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}
