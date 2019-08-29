package com.alipay.mobile.antui.segement;

import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AUSegment */
final class b implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ AUSegment b;

    b(AUSegment this$0, int i) {
        this.b = this$0;
        this.a = i;
    }

    public final void run() {
        try {
            int[] location = new int[2];
            this.b.viewContainer.getChildAt(this.a).getLocationInWindow(location);
            this.b.horizontalScrollView.smoothScrollBy(location[0] - (this.b.screenWidth / 3), 0);
        } catch (Exception e) {
            AuiLogger.info(AUSegment.TAG, e.toString());
        }
    }
}
