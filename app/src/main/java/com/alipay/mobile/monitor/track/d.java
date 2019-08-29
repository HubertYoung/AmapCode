package com.alipay.mobile.monitor.track;

/* compiled from: TrackAutoHelper */
class d implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ TrackAutoHelper b;

    d(TrackAutoHelper this$0, String str) {
        this.b = this$0;
        this.a = str;
    }

    public void run() {
        this.b.b.remove(this.a);
    }
}
