package com.alipay.mobile.antui.input;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUInputBox */
final class c implements OnClickListener {
    final /* synthetic */ AUInputBox a;

    c(AUInputBox this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.mInputEditText.setText("");
        this.a.mClearButton.setVisibility(8);
        if (this.a.mCleanButtonListener != null) {
            this.a.mCleanButtonListener.onClick(this.a.mClearButton);
        }
    }
}
