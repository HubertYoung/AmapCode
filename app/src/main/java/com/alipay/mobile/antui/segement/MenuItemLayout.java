package com.alipay.mobile.antui.segement;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;

public class MenuItemLayout extends AULinearLayout {
    private AUTextView midText;

    public MenuItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.menu_item_layout, this, true);
    }

    public MenuItemLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.menu_item_layout, this, true);
        this.midText = (AUTextView) findViewById(R.id.tv_menu_name);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setText(String str) {
        this.midText.setText(str);
    }

    public int getTextWidth() {
        return getWidth();
    }

    public void setTextColor(ColorStateList color) {
        if (color != null) {
            this.midText.setTextColor(color);
        }
    }

    public void setInitTextColor(int color) {
        this.midText.setTextColor(color);
    }

    public void setTextSize(float size) {
        this.midText.setTextSize(0, size);
    }
}
