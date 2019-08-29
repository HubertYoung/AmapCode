package com.alipay.mobile.antui.segement;

import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AUSegment */
final class e implements Runnable {
    final /* synthetic */ AUSegment a;

    e(AUSegment this$0) {
        this.a = this$0;
    }

    public final void run() {
        try {
            this.a.moveScrollBarTo(this.a.currentClickedOnMenu.getX(), this.a.currentClickedOnMenu.getTextWidth());
            this.a.menuBarScroll(this.a.currentClickedOnMenu, this.a.currentClickedOnMenu.getX());
        } catch (Exception e) {
            AuiLogger.info(AUSegment.TAG, e.toString());
        }
    }
}
