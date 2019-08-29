package com.uc.webview.export;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.uc.webview.export.extension.UCCore;

/* compiled from: ProGuard */
final class b implements OnTouchListener {
    final /* synthetic */ OnTouchListener a;
    final /* synthetic */ WebView b;
    private OnTouchListener c = this.a;

    b(WebView webView, OnTouchListener onTouchListener) {
        this.b = webView;
        this.a = onTouchListener;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.c == null) {
            return false;
        }
        if (UCCore.getGlobalBooleanOption(UCCore.ENABLE_WEBVIEW_LISTENER_STANDARDIZATION_OPTION)) {
            return this.c.onTouch(this.b, motionEvent);
        }
        return this.c.onTouch(view, motionEvent);
    }
}
