package com.autonavi.widget.ui.filter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.filter.FilterLayout.c;

public class FilterA1MainListAdapter<T> extends FilterBaseAdapter<T> {
    T tempSelectItem = null;

    public FilterA1MainListAdapter(Context context, ers<T> ers, String str) {
        super(context, ers, str);
    }

    /* access modifiers changed from: protected */
    public int getResourceId(int i) {
        return R.layout.ui_filter_a1_main_list_item;
    }

    /* access modifiers changed from: protected */
    public void setViewData(View view, int i) {
        ((TextView) c.a(view, R.id.filter_text)).setText(this.mFilterAdapter.a(getItem(i)));
        if (this.tempSelectItem != null) {
            if (this.mFilterAdapter.a(this.tempSelectItem, getItem(i))) {
                view.setBackgroundColor(getContext().getResources().getColor(R.color.c_3));
            } else {
                view.setBackgroundColor(getContext().getResources().getColor(R.color.c_1));
            }
        } else if (getSelectItem() == null || !this.mFilterAdapter.a(getSelectItem(), getItem(i))) {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.c_1));
        } else {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.c_3));
        }
    }

    public T getTempSelectItem() {
        return this.tempSelectItem;
    }

    public void setTempSelectItem(T t) {
        this.tempSelectItem = t;
    }
}
