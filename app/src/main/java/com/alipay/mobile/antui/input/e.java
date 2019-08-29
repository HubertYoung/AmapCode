package com.alipay.mobile.antui.input;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUTextCodeInputBox */
final class e implements OnClickListener {
    final /* synthetic */ AUTextCodeInputBox a;

    e(AUTextCodeInputBox this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        if (this.a.mSendCallback != null) {
            this.a.sendButtonInnerCheckEnable = false;
            this.a.updateSendButtonEnableStatus();
            this.a.mSendCallback.onSend(this.a.mResultCallback);
        }
    }
}
