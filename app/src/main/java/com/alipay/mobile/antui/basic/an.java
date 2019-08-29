package com.alipay.mobile.antui.basic;

import android.text.Editable;
import android.text.TextWatcher;

/* compiled from: AUSearchView */
final class an implements TextWatcher {
    final /* synthetic */ AUSearchView a;

    an(AUSearchView this$0) {
        this.a = this$0;
    }

    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s == null || s.length() == 0) {
            this.a.setButtonUnActivity();
        } else {
            this.a.setButtonActivity();
        }
    }

    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public final void afterTextChanged(Editable s) {
    }
}
