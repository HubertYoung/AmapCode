package com.amap.bundle.appupgrade;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class NumberProgressBar extends ProgressBar {
    private static final String TAG = "NumberProgressBar";
    private a listener;

    public interface a {
        void a(int i, int i2);
    }

    public NumberProgressBar(Context context) {
        super(context);
    }

    public NumberProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NumberProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public synchronized void setTrackProgressListener(a aVar) {
        this.listener = aVar;
    }

    public synchronized void setProgress(int i) {
        super.setProgress(i);
        if (this.listener != null) {
            this.listener.a(i, getMax());
        }
    }
}
