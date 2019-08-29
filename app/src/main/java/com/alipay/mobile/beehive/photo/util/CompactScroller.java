package com.alipay.mobile.beehive.photo.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.OverScroller;

@TargetApi(9)
public class CompactScroller {
    private static final String TAG = "DecorScroller";
    private static boolean hasCompactProblam = isVivoY11();
    private OverScrollerCopyed cScroller;
    private OverScroller scroller;

    public CompactScroller(Context context) {
        if (hasCompactProblam) {
            this.cScroller = new OverScrollerCopyed(context);
        } else {
            this.scroller = new OverScroller(context);
        }
    }

    public static boolean isVivoY11() {
        String model = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;
        PhotoLogger.info(TAG, "model: " + model);
        PhotoLogger.info(TAG, "manufacturer: " + manufacturer);
        return "BBK".equalsIgnoreCase(manufacturer) && "vivo Y11t".equalsIgnoreCase(model);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        if (this.scroller != null) {
            this.scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
        } else {
            this.cScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
        }
    }

    public final void forceFinished(boolean finished) {
        if (this.scroller != null) {
            this.scroller.forceFinished(finished);
        } else {
            this.cScroller.forceFinished(finished);
        }
    }

    public final boolean isFinished() {
        if (this.scroller != null) {
            return this.scroller.isFinished();
        }
        return this.cScroller.isFinished();
    }

    public boolean computeScrollOffset() {
        if (this.scroller != null) {
            return this.scroller.computeScrollOffset();
        }
        return this.cScroller.computeScrollOffset();
    }

    public final int getCurrX() {
        if (this.scroller != null) {
            return this.scroller.getCurrX();
        }
        return this.cScroller.getCurrX();
    }

    public final int getCurrY() {
        if (this.scroller != null) {
            return this.scroller.getCurrY();
        }
        return this.cScroller.getCurrY();
    }
}
