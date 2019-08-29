package com.uc.webview.export.internal.android;

import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import com.uc.webview.export.WebChromeClient.FileChooserParams;
import com.uc.webview.export.WebView;
import com.uc.webview.export.annotations.Jni;

/* compiled from: ProGuard */
public class WebChromeClientCompatibility extends WebChromeClient {
    protected WebView a;
    protected com.uc.webview.export.WebChromeClient b;

    /* compiled from: ProGuard */
    class a extends FileChooserParams {
        private WebChromeClient.FileChooserParams b;

        a(WebChromeClient.FileChooserParams fileChooserParams) {
            this.b = fileChooserParams;
        }

        public final int getMode() {
            return this.b.getMode();
        }

        public final String[] getAcceptTypes() {
            return this.b.getAcceptTypes();
        }

        public final boolean isCaptureEnabled() {
            return this.b.isCaptureEnabled();
        }

        public final CharSequence getTitle() {
            return this.b.getTitle();
        }

        public final String getFilenameHint() {
            return this.b.getFilenameHint();
        }

        public final Intent createIntent() {
            return this.b.createIntent();
        }
    }

    @Jni
    public boolean onShowFileChooser(android.webkit.WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return this.b.onShowFileChooser(this.a, valueCallback, fileChooserParams == null ? null : new a(fileChooserParams));
    }
}
