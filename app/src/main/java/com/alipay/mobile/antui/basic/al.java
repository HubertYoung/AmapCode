package com.alipay.mobile.antui.basic;

import android.text.Editable;
import android.text.TextWatcher;

/* compiled from: AUSearchBar */
final class al implements TextWatcher {
    final /* synthetic */ AUSearchBar a;
    private TextWatcher b;

    private al(AUSearchBar aUSearchBar) {
        this.a = aUSearchBar;
    }

    /* synthetic */ al(AUSearchBar x0, byte b2) {
        this(x0);
    }

    public final void a(TextWatcher watcher) {
        this.b = watcher;
    }

    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (this.b != null) {
            this.b.beforeTextChanged(s, start, count, after);
        }
    }

    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s == null || s.length() == 0) {
            this.a.setButtonUnActivity();
        } else {
            this.a.setButtonActivity();
        }
        if (this.b != null) {
            this.b.onTextChanged(s, start, before, count);
        }
    }

    public final void afterTextChanged(Editable s) {
        if (this.b != null) {
            this.b.afterTextChanged(s);
        }
    }
}
