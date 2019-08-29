package com.alipay.mobile.antui.card;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/* compiled from: AUCard */
final class a implements TextWatcher {
    final /* synthetic */ AUCard a;

    a(AUCard this$0) {
        this.a = this$0;
    }

    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            this.a.mDottdLine.setVisibility(8);
        } else {
            this.a.mDottdLine.setVisibility(0);
        }
    }

    public final void afterTextChanged(Editable s) {
    }
}
