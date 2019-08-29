package com.uc.webview.export;

import android.view.View;
import android.view.View.OnLongClickListener;
import com.uc.webview.export.extension.UCCore;

/* compiled from: ProGuard */
final class a implements OnLongClickListener {
    final /* synthetic */ OnLongClickListener a;
    final /* synthetic */ WebView b;
    private OnLongClickListener c = this.a;

    a(WebView webView, OnLongClickListener onLongClickListener) {
        this.b = webView;
        this.a = onLongClickListener;
    }

    public final boolean onLongClick(View view) {
        if (this.c == null) {
            return false;
        }
        if (UCCore.getGlobalBooleanOption(UCCore.ENABLE_WEBVIEW_LISTENER_STANDARDIZATION_OPTION)) {
            return this.c.onLongClick(this.b);
        }
        return this.c.onLongClick(view);
    }
}
