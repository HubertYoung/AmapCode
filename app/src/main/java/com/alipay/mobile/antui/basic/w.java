package com.alipay.mobile.antui.basic;

import com.alipay.mobile.antui.api.VisibilityChangeObserver;

/* compiled from: AUNetErrorView */
final class w implements VisibilityChangeObserver {
    final /* synthetic */ AUNetErrorView a;

    w(AUNetErrorView this$0) {
        this.a = this$0;
    }

    public final void visibilityChanged(int visibility) {
        this.a.mActionContainer.setVisibility(visibility);
    }
}
