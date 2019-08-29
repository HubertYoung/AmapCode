package com.uc.webview.export;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import com.uc.webview.export.extension.UCCore;

/* compiled from: ProGuard */
final class c implements OnKeyListener {
    final /* synthetic */ OnKeyListener a;
    final /* synthetic */ WebView b;
    private OnKeyListener c = this.a;

    c(WebView webView, OnKeyListener onKeyListener) {
        this.b = webView;
        this.a = onKeyListener;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (this.c == null) {
            return false;
        }
        if (UCCore.getGlobalBooleanOption(UCCore.ENABLE_WEBVIEW_LISTENER_STANDARDIZATION_OPTION)) {
            return this.c.onKey(this.b, i, keyEvent);
        }
        return this.c.onKey(view, i, keyEvent);
    }
}
