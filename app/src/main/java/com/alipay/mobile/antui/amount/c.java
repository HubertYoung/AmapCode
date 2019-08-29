package com.alipay.mobile.antui.amount;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/* compiled from: AUAmountFootView */
final class c implements TextWatcher {
    final /* synthetic */ AUAmountFootView a;

    c(AUAmountFootView this$0) {
        this.a = this$0;
    }

    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        this.a.onInputTextStatusChanged(TextUtils.isEmpty(s), this.a.mEditText.isFocused());
    }

    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public final void afterTextChanged(Editable s) {
    }
}
