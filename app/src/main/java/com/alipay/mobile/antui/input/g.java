package com.alipay.mobile.antui.input;

/* compiled from: AUTextCodeInputBox */
final class g implements SendResultCallback {
    final /* synthetic */ AUTextCodeInputBox a;

    private g(AUTextCodeInputBox aUTextCodeInputBox) {
        this.a = aUTextCodeInputBox;
    }

    /* synthetic */ g(AUTextCodeInputBox x0, byte b) {
        this(x0);
    }

    public final void onSuccess() {
        this.a.scheduleTimer();
    }

    public final void onFail() {
        this.a.sendButtonInnerCheckEnable = true;
        this.a.updateSendButtonEnableStatus();
    }
}
