package com.alipay.mobile.antui.basic;

import android.widget.Scroller;

/* compiled from: AUPullRefreshView */
final class ai implements Runnable {
    final /* synthetic */ AUPullRefreshView a;
    private final Scroller b;
    private int c;
    private boolean d = true;

    public ai(AUPullRefreshView aUPullRefreshView) {
        this.a = aUPullRefreshView;
        this.b = new Scroller(aUPullRefreshView.getContext());
    }

    public final void run() {
        if (this.b.computeScrollOffset()) {
            this.a.moveDown(this.c - this.b.getCurrY(), false);
            this.c = this.b.getCurrY();
            this.a.post(this);
            return;
        }
        this.d = true;
        this.a.removeCallbacks(this);
    }

    public final void a(int dis) {
        if (dis > 0) {
            this.a.removeCallbacks(this);
            this.c = 0;
            this.d = false;
            this.b.startScroll(0, 0, 0, dis, 300);
            this.a.post(this);
        }
    }

    public final boolean a() {
        return this.d;
    }
}
