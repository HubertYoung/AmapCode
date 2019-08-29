package com.alipay.mobile.antui.segement;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUSegment */
final class a implements OnClickListener {
    int a = 0;
    final /* synthetic */ AUSegment b;

    a(AUSegment this$0) {
        this.b = this$0;
    }

    public final OnClickListener a(int position) {
        this.a = position;
        return this;
    }

    public final void onClick(View v) {
        this.b.selectTabAndAdjustLine(this.a);
        this.b.tabSwitchListener.onTabClick(this.a, v);
    }
}
