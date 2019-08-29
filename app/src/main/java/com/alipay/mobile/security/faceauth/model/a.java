package com.alipay.mobile.security.faceauth.model;

import java.util.TimerTask;

/* compiled from: DetectTimerTask */
final class a extends TimerTask {
    final /* synthetic */ DetectTimerTask a;

    a(DetectTimerTask detectTimerTask) {
        this.a = detectTimerTask;
    }

    public final void run() {
        this.a.b -= this.a.d;
        if (this.a.b <= 0) {
            this.a.b = 0;
            this.a.a.cancel();
        }
        if (this.a.e != null) {
            this.a.e.countdown(this.a.b);
        }
    }
}
