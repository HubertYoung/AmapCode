package com.alipay.mobile.antui.basic;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

/* compiled from: AUBasePwdInputBox */
final class a implements Runnable {
    final /* synthetic */ View a;
    final /* synthetic */ AUBasePwdInputBox b;

    a(AUBasePwdInputBox this$0, View view) {
        this.b = this$0;
        this.a = view;
    }

    public final void run() {
        ((InputMethodManager) this.a.getContext().getSystemService("input_method")).showSoftInput(this.a, 0);
    }
}
