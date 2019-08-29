package com.alipay.mobile.antui.dialog;

import java.util.TimerTask;

/* compiled from: AUImageDialog */
final class w extends TimerTask {
    final /* synthetic */ AUImageDialog a;

    w(AUImageDialog this$0) {
        this.a = this$0;
    }

    public final void run() {
        if (AUImageDialog.times > 1) {
            this.a.mHandler.sendEmptyMessage(1);
        } else {
            this.a.callTimeFinish();
            this.a.mHandler.sendEmptyMessage(2);
        }
        AUImageDialog.access$310();
    }
}
