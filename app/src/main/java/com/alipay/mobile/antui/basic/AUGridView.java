package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class AUGridView extends GridView implements AUViewInterface {
    private Boolean isAP;

    public AUGridView(Context context) {
        super(context);
    }

    public AUGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(AUViewEventHelper.wrapItemClickListener(listener));
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
