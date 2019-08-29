package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class APLinearLayout extends LinearLayout {
    public View getView() {
        return this;
    }

    public APLinearLayout(Context context) {
        super(context);
    }

    public APLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
    }

    public void addView(View view, LayoutParams layoutParams) {
        super.addView(view, layoutParams);
    }

    public int getVisibility() {
        return super.getVisibility();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(APViewEventHelper.a(onClickListener));
    }
}
