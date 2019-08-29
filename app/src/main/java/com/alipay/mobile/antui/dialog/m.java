package com.alipay.mobile.antui.dialog;

import android.util.Log;

/* compiled from: AUCardMenu */
final class m implements j {
    final /* synthetic */ int a;
    final /* synthetic */ l b;

    m(l this$1, int i) {
        this.b = this$1;
        this.a = i;
    }

    public final void a(int btnCount) {
        Log.d(AUCardMenu.TAG, String.format("OptionBtnClick: position = %d, btnCount = %d ", new Object[]{Integer.valueOf(this.a), Integer.valueOf(btnCount)}));
        if (this.b.a.mListener != null) {
            this.b.a.mListener.onItemOptionsClick(this.a, btnCount);
        } else {
            Log.d(AUCardMenu.TAG, "OptionBtnClick: mListener is null");
        }
    }
}
