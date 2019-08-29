package com.alipay.mobile.antui.segement;

import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AUSegment */
final class c implements Runnable {
    final /* synthetic */ AUSegment a;

    c(AUSegment this$0) {
        this.a = this$0;
    }

    public final void run() {
        try {
            this.a.moveScrollBarTo(this.a.currentClickedOnMenu.getX(), this.a.currentClickedOnMenu.getTextWidth());
        } catch (Exception e) {
            AuiLogger.info(AUSegment.TAG, e.toString());
        }
    }
}
