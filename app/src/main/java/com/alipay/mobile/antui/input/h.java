package com.alipay.mobile.antui.input;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.mobile.antui.R;

/* compiled from: AUTextCodeInputBox */
final class h extends Handler {
    final /* synthetic */ AUTextCodeInputBox a;

    private h(AUTextCodeInputBox aUTextCodeInputBox) {
        this.a = aUTextCodeInputBox;
    }

    /* synthetic */ h(AUTextCodeInputBox x0, byte b) {
        this(x0);
    }

    public final void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1:
                int time = ((Integer) msg.obj).intValue();
                this.a.mSendButton.setText(this.a.timeStr.replace("$s$", time < 10 ? "  " + time : String.valueOf(time)));
                return;
            case 2:
                this.a.sendButtonInnerCheckEnable = true;
                this.a.updateSendButtonEnableStatus();
                if (!TextUtils.isEmpty(this.a.mSendButtonTextRetry)) {
                    this.a.mSendButton.setText(this.a.mSendButtonTextRetry);
                } else {
                    this.a.mSendButton.setText(this.a.getContext().getText(R.string.resendCheckCode));
                }
                this.a.resetTime();
                return;
            default:
                return;
        }
    }
}
