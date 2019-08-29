package com.alipay.mobile.antui.amount;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnFocusChangeListener;

/* compiled from: AUAmountFootView */
final class d implements OnFocusChangeListener {
    final /* synthetic */ AUAmountFootView a;

    d(AUAmountFootView this$0) {
        this.a = this$0;
    }

    public final void onFocusChange(View v, boolean hasFocus) {
        this.a.onInputTextStatusChanged(TextUtils.isEmpty(this.a.getEditTextEditable()), hasFocus);
    }
}
