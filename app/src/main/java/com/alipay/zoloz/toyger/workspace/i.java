package com.alipay.zoloz.toyger.workspace;

/* compiled from: ToygerWorkspace */
final class i implements Runnable {
    final /* synthetic */ double a;
    final /* synthetic */ double b;
    final /* synthetic */ ToygerWorkspace c;

    i(ToygerWorkspace toygerWorkspace, double d, double d2) {
        this.c = toygerWorkspace;
        this.a = d;
        this.b = d2;
    }

    public final void run() {
        this.c.mToygerCirclePattern.onPreviewChanged(this.a, this.b);
    }
}
