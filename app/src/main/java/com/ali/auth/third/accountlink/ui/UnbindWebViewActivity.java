package com.ali.auth.third.accountlink.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebViewClient;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.ui.context.CallbackContext;
import com.ali.auth.third.ui.webview.BaseWebViewActivity;

public class UnbindWebViewActivity extends BaseWebViewActivity {
    public static LoginCallback b = null;
    private static String c = "login.WebViewActivity";
    public boolean a = false;

    public void a() {
        setResult(0);
        finish();
    }

    public WebViewClient createWebViewClient() {
        return new a(this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = false;
    }

    public void onDestroy() {
        super.onDestroy();
        b = null;
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        a();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        if (intent != null) {
            String stringExtra = intent.getStringExtra("url");
            if (stringExtra != null) {
                this.authWebView.loadUrl(stringExtra);
            }
        }
    }

    public void onResume() {
        super.onResume();
        CallbackContext.loginCallback = b;
        if (this.a) {
            a();
        }
    }
}
