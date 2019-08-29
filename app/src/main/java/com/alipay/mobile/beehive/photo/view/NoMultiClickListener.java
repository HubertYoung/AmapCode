package com.alipay.mobile.beehive.photo.view;

import android.view.View;
import android.view.View.OnClickListener;

public abstract class NoMultiClickListener implements OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public abstract void onNoMultiClick(View view);

    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.lastClickTime > 1000) {
            onNoMultiClick(v);
        }
        this.lastClickTime = currentTime;
    }
}
