package com.alipay.mobile.android.verify.bridge.b;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.WebView;
import com.alipay.mobile.android.verify.bridge.h;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEventTypes;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.squareup.otto.Subscribe;

/* compiled from: ScriptLoaderPlugin */
public class d implements IPlugin {
    /* access modifiers changed from: private */
    public final WebView a;
    /* access modifiers changed from: private */
    public boolean b = false;

    public d(WebView webView) {
        this.a = webView;
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action)) {
            Logger.t("ScriptLoaderPlugin").i("null or empty action", new Object[0]);
        } else if (BridgeEventTypes.PAGE_START.equalsIgnoreCase(bridgeEvent.action)) {
            c();
        } else if (BridgeEventTypes.RECEIVED_TITLE.equalsIgnoreCase(bridgeEvent.action)) {
            a();
        } else {
            if (BridgeEventTypes.PAGE_LOADED.equalsIgnoreCase(bridgeEvent.action)) {
                a();
            }
        }
    }

    private void a() {
        h.a((Runnable) new e(this));
    }

    /* access modifiers changed from: private */
    public synchronized void b() {
        if (this.b) {
            Logger.t("ScriptLoaderPlugin").d("script already loaded");
            return;
        }
        String a2 = h.a(this.a.getContext().getApplicationContext());
        this.b = !TextUtils.isEmpty(a2);
        Logger.t("ScriptLoaderPlugin").i("script load result %s", Boolean.valueOf(this.b));
        new Handler(Looper.getMainLooper()).post(new f(this, a2));
    }

    private void c() {
        this.b = false;
    }
}
