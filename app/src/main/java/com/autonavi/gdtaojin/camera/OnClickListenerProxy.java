package com.autonavi.gdtaojin.camera;

import android.view.View;
import android.view.View.OnClickListener;

public class OnClickListenerProxy implements OnClickListener {
    public static long MIN_INTERVAL = 800;
    private OnClickListener mHolder;
    private long mLastEffectiveClickTime = 0;

    public OnClickListenerProxy(OnClickListener onClickListener) {
        this.mHolder = onClickListener;
    }

    public void onClick(View view) {
        if (System.currentTimeMillis() - this.mLastEffectiveClickTime >= MIN_INTERVAL) {
            this.mLastEffectiveClickTime = System.currentTimeMillis();
            this.mHolder.onClick(view);
        }
    }
}
