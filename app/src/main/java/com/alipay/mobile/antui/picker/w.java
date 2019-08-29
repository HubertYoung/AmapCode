package com.alipay.mobile.antui.picker;

/* compiled from: ItemDragCallback */
final class w implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ int b;
    final /* synthetic */ ItemDragCallback c;

    w(ItemDragCallback this$0, int i, int i2) {
        this.c = this$0;
        this.a = i;
        this.b = i2;
    }

    public final void run() {
        if (this.c.onMoveListener != null) {
            this.c.onMoveListener.onItemMove(this.a, this.b);
        }
        this.c.animating = false;
    }
}
