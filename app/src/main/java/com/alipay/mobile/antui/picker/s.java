package com.alipay.mobile.antui.picker;

/* compiled from: AUWheelView */
final class s implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ AUWheelView b;

    s(AUWheelView this$0, int i) {
        this.b = this$0;
        this.a = i;
    }

    public final void run() {
        this.b.scrollTo(0, this.a * this.b.itemHeight);
        this.b.selectedIndex = this.a + this.b.offset;
        this.b.onSelectedCallBack();
    }
}
