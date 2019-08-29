package com.alipay.mobile.antui.amount;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/* compiled from: AUAmountEditText */
final class a implements OnTouchListener {
    final /* synthetic */ AUAmountEditText a;

    a(AUAmountEditText this$0) {
        this.a = this$0;
    }

    public final boolean onTouch(View v, MotionEvent event) {
        if (!this.a.mEditText.hasFocus()) {
            this.a.requestFocus();
            if (this.a.mEditText.getText() != null) {
                this.a.mEditText.setSelection(this.a.mEditText.getText().length());
            } else {
                this.a.mEditText.setSelection(0);
            }
        }
        return false;
    }
}
