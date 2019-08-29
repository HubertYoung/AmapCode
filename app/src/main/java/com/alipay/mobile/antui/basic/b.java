package com.alipay.mobile.antui.basic;

import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AUBladeView */
final class b implements Runnable {
    final /* synthetic */ AUBladeView a;

    b(AUBladeView this$0) {
        this.a = this$0;
    }

    public final void run() {
        if (this.a.mPopupWindow != null) {
            try {
                this.a.mPopupWindow.dismiss();
            } catch (Exception e) {
                AuiLogger.error("AUBladeView", e.toString());
            }
        }
    }
}
