package com.alipay.mobile.antui.amount;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUAmountEditText */
final class b implements OnClickListener {
    final /* synthetic */ AUAmountEditText a;

    b(AUAmountEditText this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.mEditText.setText("");
        if (this.a.keyBoardUtil != null) {
            this.a.keyBoardUtil.showKeyboard();
        }
    }
}
