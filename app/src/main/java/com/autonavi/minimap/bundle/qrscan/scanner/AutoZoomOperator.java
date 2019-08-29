package com.autonavi.minimap.bundle.qrscan.scanner;

import android.os.Handler;
import android.os.Looper;

public class AutoZoomOperator {
    private static final String TAG = "AutoZoomOperator";
    private static Handler handler = new Handler(Looper.getMainLooper());
    private final int MaxIndex = 10;
    /* access modifiers changed from: private */
    public volatile boolean disableContinueZoom;
    private ZoomOperator mZoomOperator;

    public interface ZoomOperator {
        void setZoom(int i);
    }

    public AutoZoomOperator(ZoomOperator zoomOperator) {
        this.mZoomOperator = zoomOperator;
    }

    public void clearActivity() {
        this.mZoomOperator = null;
    }

    public void startAutoZoom(float f, int i) {
        StringBuilder sb = new StringBuilder("startAutoZoom : rate is ");
        sb.append(f);
        sb.append(", curIndex is ");
        sb.append(i);
        if (f < 0.0f || this.disableContinueZoom || i >= 10) {
            this.disableContinueZoom = false;
            return;
        }
        this.disableContinueZoom = true;
        invalidate(0, (int) f);
    }

    /* access modifiers changed from: private */
    public void setZoom(int i, int i2, int i3) {
        if (this.mZoomOperator != null) {
            this.mZoomOperator.setZoom(i);
            invalidate(i2 + 1, i3);
        }
    }

    private void invalidate(final int i, final int i2) {
        handler.postDelayed(new Runnable() {
            public void run() {
                if (i < 10) {
                    AutoZoomOperator.this.setZoom(((int) ((((float) i2) * 1.0f) / 10.0f)) * (i + 1), i, i2);
                } else {
                    AutoZoomOperator.this.disableContinueZoom = false;
                }
            }
        }, 20);
    }
}
