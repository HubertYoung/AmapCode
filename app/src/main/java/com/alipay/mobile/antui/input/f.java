package com.alipay.mobile.antui.input;

import android.os.Message;
import java.util.TimerTask;

/* compiled from: AUTextCodeInputBox */
final class f extends TimerTask {
    final /* synthetic */ AUTextCodeInputBox a;

    f(AUTextCodeInputBox this$0) {
        this.a = this$0;
    }

    public final void run() {
        this.a.currentSecond = this.a.currentSecond - 1;
        Message updateMessage = this.a.mTimerHanlder.obtainMessage();
        if (this.a.currentSecond > 0) {
            updateMessage.what = 1;
            updateMessage.obj = Integer.valueOf(this.a.currentSecond);
        } else {
            updateMessage.what = 2;
            cancel();
        }
        this.a.mTimerHanlder.sendMessage(updateMessage);
    }
}
