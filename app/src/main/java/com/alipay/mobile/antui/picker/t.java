package com.alipay.mobile.antui.picker;

import android.util.Log;

/* compiled from: AUWheelView */
final class t implements Runnable {
    final /* synthetic */ AUWheelView a;

    private t(AUWheelView aUWheelView) {
        this.a = aUWheelView;
    }

    /* synthetic */ t(AUWheelView x0, byte b) {
        this(x0);
    }

    public final void run() {
        if (this.a.itemHeight == 0) {
            Log.d("compositeui", "itemHeight is zero");
            return;
        }
        if (this.a.initialY - this.a.getScrollY() == 0) {
            int remainder = this.a.initialY % this.a.itemHeight;
            int divided = this.a.initialY / this.a.itemHeight;
            Log.d("compositeui", "initialY: " + this.a.initialY + ", remainder: " + remainder + ", divided: " + divided);
            if (remainder == 0) {
                this.a.selectedIndex = this.a.offset + divided;
                this.a.onSelectedCallBack();
            } else if (remainder > this.a.itemHeight / 2) {
                this.a.post(new u(this, remainder, divided));
            } else {
                this.a.post(new v(this, remainder, divided));
            }
        } else {
            this.a.startScrollerTask();
        }
    }
}
