package com.amap.bundle.utils.view;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class OneClickListener implements OnClickListener {
    private long dbClickTime;
    private long internalMillisecond;

    /* access modifiers changed from: protected */
    public abstract void doClick(View view);

    public OneClickListener() {
        this.internalMillisecond = 500;
        this.dbClickTime = 0;
        this.dbClickTime = System.currentTimeMillis();
    }

    public OneClickListener(long j) {
        this.internalMillisecond = 500;
        this.dbClickTime = 0;
        this.internalMillisecond = j;
    }

    public void onClick(View view) {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - this.dbClickTime) >= this.internalMillisecond) {
            this.dbClickTime = currentTimeMillis;
            doClick(view);
        }
    }
}
