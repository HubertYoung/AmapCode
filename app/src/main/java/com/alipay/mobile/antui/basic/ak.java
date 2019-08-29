package com.alipay.mobile.antui.basic;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

/* compiled from: AUSearchBar */
final class ak implements OnClickListener {
    final /* synthetic */ AUSearchBar a;

    ak(AUSearchBar this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.mSearchEditView.setText("");
        ((InputMethodManager) this.a.mSearchEditView.getContext().getSystemService("input_method")).showSoftInput(this.a.mSearchEditView, 1);
    }
}
