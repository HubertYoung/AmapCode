package com.autonavi.widget.ui.filter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.filter.FilterLayout.c;

public class FilterA2ListAdapter<T> extends FilterBaseAdapter<T> {
    public FilterA2ListAdapter(Context context, ers<T> ers, String str) {
        super(context, ers, str);
    }

    /* access modifiers changed from: protected */
    public int getResourceId(int i) {
        return R.layout.ui_filter_a2_list_item;
    }

    /* access modifiers changed from: protected */
    public void setViewData(View view, int i) {
        ((TextView) c.a(view, R.id.filter_text)).setText(this.mFilterAdapter.a(getItem(i)));
        ImageView imageView = (ImageView) c.a(view, R.id.ivSelectedIcon);
        if (getSelectItem() == null || !this.mFilterAdapter.a(getSelectItem(), getItem(i))) {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.c_1));
            imageView.setVisibility(4);
            return;
        }
        view.setBackgroundColor(getContext().getResources().getColor(R.color.c_3));
        imageView.setVisibility(0);
    }
}
