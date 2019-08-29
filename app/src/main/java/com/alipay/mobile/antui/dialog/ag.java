package com.alipay.mobile.antui.dialog;

import com.alipay.mobile.antui.basic.AUTextView.OnVisibilityChangeListener;

/* compiled from: AUNoticeDialog */
final class ag implements OnVisibilityChangeListener {
    final /* synthetic */ AUNoticeDialog a;

    ag(AUNoticeDialog this$0) {
        this.a = this$0;
    }

    public final void onChange(int visibility) {
        if (visibility == 8) {
            this.a.mTitle.setGravity(19);
        } else {
            this.a.mTitle.setGravity(51);
        }
    }
}
