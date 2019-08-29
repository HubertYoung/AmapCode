package com.alipay.mobile.antui.basic;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

/* compiled from: AUSearchView */
final class am implements OnClickListener {
    final /* synthetic */ AUSearchView a;

    am(AUSearchView this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.mSearchEditView.setText("");
        ((InputMethodManager) this.a.mSearchEditView.getContext().getSystemService("input_method")).showSoftInput(this.a.mSearchEditView, 1);
    }
}
