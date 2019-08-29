package com.alipay.mobile.beehive.photo.util;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
    private View convertView;
    private SparseArray<View> views = new SparseArray<>();

    public static ViewHolder get(View view) {
        Object tag = view.getTag();
        if (tag instanceof ViewHolder) {
            return (ViewHolder) tag;
        }
        return new ViewHolder(view);
    }

    private ViewHolder(View view) {
        this.convertView = view;
        view.setTag(this);
    }

    public <T extends View> T findViewById(int id) {
        View view = this.views.get(id);
        if (view != null) {
            return view;
        }
        View view2 = this.convertView.findViewById(id);
        this.views.put(id, view2);
        return view2;
    }
}
