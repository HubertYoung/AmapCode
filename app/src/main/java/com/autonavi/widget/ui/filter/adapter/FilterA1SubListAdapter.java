package com.autonavi.widget.ui.filter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.filter.FilterLayout.c;

public class FilterA1SubListAdapter<T> extends FilterBaseAdapter<T> {
    public FilterA1SubListAdapter(Context context, ers<T> ers, String str, T t) {
        super(context, ers, str, t);
    }

    /* access modifiers changed from: protected */
    public int getResourceId(int i) {
        return R.layout.ui_filter_a1_sub_list_item;
    }

    /* access modifiers changed from: protected */
    public void setViewData(View view, int i) {
        ((TextView) c.a(view, R.id.filter_text)).setText(this.mFilterAdapter.a(getItem(i)));
        ImageView imageView = (ImageView) c.a(view, R.id.ivSelectedIcon);
        if (getSelectItem() == null || !this.mFilterAdapter.a(getSelectItem(), getItem(i))) {
            view.setSelected(false);
            imageView.setVisibility(4);
            return;
        }
        view.setSelected(true);
        imageView.setVisibility(0);
    }
}
