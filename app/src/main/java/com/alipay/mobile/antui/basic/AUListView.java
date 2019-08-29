package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AUListView extends ListView implements AUViewInterface {
    private Boolean isAP;

    public AUListView(Context context) {
        super(context);
    }

    public AUListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(AUViewEventHelper.wrapItemClickListener(listener));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        postInvalidate();
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
