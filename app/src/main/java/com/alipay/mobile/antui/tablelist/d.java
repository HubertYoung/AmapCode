package com.alipay.mobile.antui.tablelist;

import android.text.Editable;
import android.text.TextWatcher;

/* compiled from: AULineBreakListItem */
final class d implements TextWatcher {
    final /* synthetic */ AULineBreakListItem a;
    private c b;

    public d(AULineBreakListItem aULineBreakListItem, c listener) {
        this.a = aULineBreakListItem;
        this.b = listener;
    }

    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public final void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public final void afterTextChanged(Editable s) {
        if (this.b != null) {
            this.b.a(s.toString());
        }
    }
}
