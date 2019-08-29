package com.alipay.mobile.antui.tablelist;

/* compiled from: AULineBreakListItem */
final class b implements c {
    final /* synthetic */ AULineBreakListItem a;

    b(AULineBreakListItem this$0) {
        this.a = this$0;
    }

    public final void a(String s) {
        this.a.mRightLength = (int) this.a.mRightText.getPaint().measureText(s);
        this.a.adjustWeight();
    }
}
