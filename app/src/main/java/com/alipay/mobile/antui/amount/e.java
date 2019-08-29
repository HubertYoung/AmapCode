package com.alipay.mobile.antui.amount;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

/* compiled from: AUAmountFootView */
final class e implements OnClickListener {
    final /* synthetic */ AUAmountFootView a;

    e(AUAmountFootView this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.mEditText.setText("");
        ((InputMethodManager) this.a.mEditText.getContext().getSystemService("input_method")).showSoftInput(this.a.mEditText, 1);
    }
}
