package com.alipay.mobile.antui.tablelist;

/* compiled from: AULineBreakListItem */
final class a implements c {
    final /* synthetic */ AULineBreakListItem a;

    a(AULineBreakListItem this$0) {
        this.a = this$0;
    }

    public final void a(String s) {
        this.a.mLeftLength = (int) this.a.mRightText.getPaint().measureText(s);
        this.a.adjustWeight();
    }
}
