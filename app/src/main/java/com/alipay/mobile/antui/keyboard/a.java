package com.alipay.mobile.antui.keyboard;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/* compiled from: AUNumberKeyBoardUtil */
final class a implements OnTouchListener {
    final /* synthetic */ AUNumberKeyBoardUtil a;

    a(AUNumberKeyBoardUtil this$0) {
        this.a = this$0;
    }

    public final boolean onTouch(View v, MotionEvent event) {
        if (this.a.mEditText.isFocused()) {
            this.a.showKeyboard();
        }
        return false;
    }
}
