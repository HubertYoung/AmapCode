package com.alipay.zoloz.toyger.workspace;

/* compiled from: ToygerWorkspace */
final class p implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ m b;

    p(m mVar, int i) {
        this.b = mVar;
        this.a = i;
    }

    public final void run() {
        if (this.b.a.mToygerCirclePattern != null && this.b.a.mToygerCirclePattern.getTitleBar() != null && !"Cherry".equalsIgnoreCase("Cherry")) {
            this.b.a.mToygerCirclePattern.getTitleBar().setTimeOut((this.a / 1000) + "S");
        }
    }
}
