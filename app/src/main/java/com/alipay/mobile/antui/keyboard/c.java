package com.alipay.mobile.antui.keyboard;

import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AUNumberKeyBoardUtil */
final class c implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ AUNumberKeyBoardUtil b;

    c(AUNumberKeyBoardUtil this$0, int i) {
        this.b = this$0;
        this.a = i;
    }

    public final void run() {
        AuiLogger.debug(AUNumberKeyBoardUtil.TAG, "mScrollView to  = " + this.a);
        this.b.mScrollView.smoothScrollTo(0, this.a);
    }
}
