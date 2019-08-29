package com.autonavi.widget.ui.filter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.filter.FilterLayout.c;
import java.util.HashSet;

public class FilterA3ListAdapter<T> extends FilterBaseAdapter<T> {
    private HashSet<Integer> checkedItemPositions = new HashSet<>();
    private HashSet<Integer> tempCheckedItemPositions = new HashSet<>();

    public FilterA3ListAdapter(Context context, ers<T> ers, String str) {
        super(context, ers, str);
    }

    /* access modifiers changed from: protected */
    public int getResourceId(int i) {
        return R.layout.ui_filter_a3_list_item;
    }

    /* access modifiers changed from: protected */
    public void setViewData(View view, int i) {
        TextView textView = (TextView) c.a(view, R.id.filter_text);
        textView.setText(this.mFilterAdapter.a(getItem(i)));
        ImageView imageView = (ImageView) c.a(view, R.id.ivSelectedIcon);
        if (this.tempCheckedItemPositions.size() == 0 || !this.tempCheckedItemPositions.contains(Integer.valueOf(i))) {
            textView.setSelected(false);
            view.setBackgroundResource(R.drawable.ui_filter_a3_list_item_bg_normal);
            imageView.setVisibility(8);
            return;
        }
        textView.setSelected(true);
        view.setBackgroundResource(R.drawable.ui_filter_a3_list_item_bg_checked);
        imageView.setVisibility(0);
    }

    public HashSet<Integer> getCheckedItemPositions() {
        return this.checkedItemPositions;
    }

    public HashSet<Integer> getTempCheckedItemPositions() {
        return this.tempCheckedItemPositions;
    }
}
