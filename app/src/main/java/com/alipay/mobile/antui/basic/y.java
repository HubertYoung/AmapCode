package com.alipay.mobile.antui.basic;

import java.util.TimerTask;

/* compiled from: AUNetErrorView */
final class y extends TimerTask {
    final /* synthetic */ AUNetErrorView a;

    y(AUNetErrorView this$0) {
        this.a = this$0;
    }

    public final void run() {
        if (AUNetErrorView.times > 1) {
            this.a.mHandler.sendEmptyMessage(1);
        } else {
            this.a.callTimeFinish();
            this.a.mHandler.sendEmptyMessage(2);
        }
        AUNetErrorView.access$310();
    }
}
