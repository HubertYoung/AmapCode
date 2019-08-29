package com.alipay.mobile.antui.keyboard;

/* compiled from: AUNumberKeyBoardUtil */
final class b implements Runnable {
    final /* synthetic */ AnonymousClass2 a;

    b(AnonymousClass2 this$1) {
        this.a = this$1;
    }

    public final void run() {
        AUNumberKeyBoardUtil.this.mKeyboardView.show();
    }
}
