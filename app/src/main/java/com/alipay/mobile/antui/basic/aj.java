package com.alipay.mobile.antui.basic;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

/* compiled from: AUSearchBar */
final class aj implements OnClickListener {
    final /* synthetic */ AUSearchBar a;

    aj(AUSearchBar this$0) {
        this.a = this$0;
    }

    public final void onClick(View arg0) {
        try {
            Context context = this.a.getContext();
            ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(arg0.getWindowToken(), 0);
            if (context != null && (context instanceof Activity)) {
                ((Activity) context).onBackPressed();
            }
        } catch (Exception e) {
        }
    }
}
