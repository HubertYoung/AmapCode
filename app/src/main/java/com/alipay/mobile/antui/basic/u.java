package com.alipay.mobile.antui.basic;

/* compiled from: AUHorizontalListView */
class u {
    private int a;
    final /* synthetic */ AUHorizontalListView c;

    private u(AUHorizontalListView aUHorizontalListView) {
        this.c = aUHorizontalListView;
    }

    /* synthetic */ u(AUHorizontalListView x0, byte b) {
        this(x0);
    }

    public final void a() {
        this.a = this.c.getWindowAttachCount();
    }

    public final boolean b() {
        return this.c.hasWindowFocus() && this.c.getWindowAttachCount() == this.a;
    }
}
