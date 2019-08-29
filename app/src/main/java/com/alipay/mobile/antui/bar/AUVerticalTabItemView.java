package com.alipay.mobile.antui.bar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.basic.AUTextView;

public class AUVerticalTabItemView extends AUFrameLayout {
    private View mLineView;
    private AUTextView mNameView;

    public AUVerticalTabItemView(Context context) {
        super(context);
        init(context);
    }

    public AUVerticalTabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUVerticalTabItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_vertical_tab_item_view, this, true);
        setClickable(true);
        this.mLineView = findViewById(R.id.item_line);
        this.mNameView = (AUTextView) findViewById(R.id.item_name);
    }

    public void setSelected(boolean isSelected) {
        if (isSelected) {
            setBackgroundColor(getResources().getColor(R.color.AU_COLOR15));
            this.mLineView.setVisibility(0);
            this.mNameView.setTextColor(getResources().getColor(R.color.AU_COLOR_LINK));
            return;
        }
        setBackgroundColor(getResources().getColor(17170445));
        this.mLineView.setVisibility(8);
        this.mNameView.setTextColor(getResources().getColor(R.color.AU_COLOR_MAIN_CONTENT));
    }

    public AUTextView getNameView() {
        return this.mNameView;
    }
}
