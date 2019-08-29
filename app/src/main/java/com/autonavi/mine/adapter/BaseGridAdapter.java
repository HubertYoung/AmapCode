package com.autonavi.mine.adapter;

import android.graphics.drawable.Drawable;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public abstract class BaseGridAdapter extends BaseAdapter {
    protected ArrayList<a> localItems = null;
    protected ArrayList<ddr> toolsItmes = null;

    public static class a {
        public int a;
        public Drawable b;
        public String c;
        public boolean d = false;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public BaseGridAdapter(ArrayList<ddr> arrayList, ArrayList<a> arrayList2) {
        this.localItems = arrayList2;
        this.toolsItmes = arrayList;
    }

    public BaseGridAdapter(ArrayList<a> arrayList) {
        this.localItems = arrayList;
    }

    public int getCount() {
        if (this.toolsItmes == null) {
            return this.localItems.size();
        }
        return this.toolsItmes.size();
    }

    public Object getItem(int i) {
        if (this.toolsItmes == null) {
            return this.localItems.get(i);
        }
        return this.toolsItmes.get(i);
    }
}
