package com.alipay.mobile.antui.input;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/* compiled from: AUInputBox */
final class b implements OnTouchListener {
    final /* synthetic */ AUInputBox a;

    b(AUInputBox this$0) {
        this.a = this$0;
    }

    public final boolean onTouch(View v, MotionEvent event) {
        this.a.mInputEditText.requestFocus();
        this.a.showSoftKeyboard();
        return false;
    }
}
