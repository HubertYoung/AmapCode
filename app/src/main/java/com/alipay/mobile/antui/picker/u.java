package com.alipay.mobile.antui.picker;

/* compiled from: AUWheelView */
final class u implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ int b;
    final /* synthetic */ t c;

    u(t this$1, int i, int i2) {
        this.c = this$1;
        this.a = i;
        this.b = i2;
    }

    public final void run() {
        this.c.a.smoothScrollTo(0, (this.c.a.initialY - this.a) + this.c.a.itemHeight);
        this.c.a.selectedIndex = this.b + this.c.a.offset + 1;
        this.c.a.onSelectedCallBack();
    }
}
