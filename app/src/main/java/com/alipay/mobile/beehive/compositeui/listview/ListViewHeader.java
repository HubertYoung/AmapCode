package com.alipay.mobile.beehive.compositeui.listview;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ListViewHeader extends LinearLayout {
    public ListViewHeader(Context context) {
        super(context);
    }

    public ListViewHeader(Context context, View view) {
        super(context);
        addView(view, new LayoutParams(-1, -2));
    }
}
