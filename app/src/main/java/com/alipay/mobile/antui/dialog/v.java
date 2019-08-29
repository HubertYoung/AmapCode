package com.alipay.mobile.antui.dialog;

import android.os.Handler;
import android.os.Message;
import com.alipay.mobile.antui.R;

/* compiled from: AUImageDialog */
final class v extends Handler {
    final /* synthetic */ AUImageDialog a;

    v(AUImageDialog this$0) {
        this.a = this$0;
    }

    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                this.a.confirmBtn.setEnabled(false);
                String time = String.format(this.a.mTimeColor, new Object[]{Integer.valueOf(AUImageDialog.times)});
                this.a.setConfirmBtnText(String.format(this.a.getContext().getString(R.string.retry_later), new Object[]{time}));
                break;
            case 2:
                this.a.confirmBtn.setEnabled(true);
                this.a.setConfirmBtnText(this.a.mConfirmStr);
                this.a.cancelTimer();
                break;
        }
        super.handleMessage(msg);
    }
}
