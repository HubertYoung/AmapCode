package com.alipay.mobile.antui.input;

import android.text.Editable;
import android.text.TextWatcher;

/* compiled from: AUParagraphInputBox */
final class d implements TextWatcher {
    final /* synthetic */ AUParagraphInputBox a;

    d(AUParagraphInputBox this$0) {
        this.a = this$0;
    }

    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public final void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public final void afterTextChanged(Editable s) {
        this.a.paraTextView.setText(String.valueOf(s.length()) + "/" + String.valueOf(this.a.maxLength));
    }
}
