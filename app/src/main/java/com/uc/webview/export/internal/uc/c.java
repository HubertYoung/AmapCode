package com.uc.webview.export.internal.uc;

import android.content.Context;
import android.content.IntentFilter;
import android.os.PowerManager;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.interfaces.IWebView;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.e;
import com.uc.webview.export.utility.Utils;

/* compiled from: ProGuard */
public final class c extends com.uc.webview.export.internal.a {
    static Runnable f = new d();
    /* access modifiers changed from: private */
    public static e g;

    /* compiled from: ProGuard */
    public class a implements com.uc.webview.export.internal.utility.e.a {
        private Context b;

        public a(Context context) {
            this.b = context.getApplicationContext();
        }

        public final void a() {
            Log.d("WebViewDetector", "onScreenOn: onScreenOn");
            c.g;
            if (!e.a(this.b) && SDKFactory.d != null) {
                SDKFactory.d.onScreenUnLock();
                SDKFactory.d.onResume();
                Log.d("WebViewDetector", "onScreenOn: onScreenUnLock");
            }
        }

        public final void b() {
            Log.d("WebViewDetector", "onScreenOff: onScreenOff");
            if (SDKFactory.d != null) {
                SDKFactory.d.onScreenLock();
                SDKFactory.d.onPause();
                Log.d("WebViewDetector", "onScreenOff: onScreenLock");
            }
        }

        public final void c() {
            Log.d("WebViewDetector", "onUserPresent: onUserPresent");
            if (SDKFactory.d != null) {
                SDKFactory.d.onScreenUnLock();
                SDKFactory.d.onResume();
                Log.d("WebViewDetector", "onUserPresent: onScreenUnLock");
            }
        }
    }

    public c(Context context) {
        if (!SDKFactory.g && g == null) {
            e eVar = new e(context);
            g = eVar;
            eVar.b = new a(context);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            eVar.a.registerReceiver(eVar, intentFilter);
            if (e.a((PowerManager) eVar.a.getSystemService("power"))) {
                if (eVar.b != null) {
                    eVar.b.a();
                }
            } else if (eVar.b != null) {
                eVar.b.b();
            }
        }
    }

    public final void a(int i, int i2) {
        if (b != i || c != i2) {
            if (!SDKFactory.g && SDKFactory.d != null) {
                SDKFactory.d.onWindowSizeChanged();
            }
            b = i;
            c = i2;
        }
    }

    public final void a(IWebView iWebView, int i) {
        Log.d("WebViewDetector", "onWindowVisibilityChanged: ".concat(String.valueOf(i)));
        iWebView.notifyForegroundChanged(i == 0);
        if (i == 0) {
            if (d != 1) {
                if (!SDKFactory.g && SDKFactory.d != null) {
                    SDKFactory.d.onResume();
                }
                Log.d("WebViewDetector", "WebViewDetector:onResume");
                d = 1;
            }
        } else if (d == 1) {
            e.removeCallbacks(f);
            e.post(f);
        }
    }

    public final void b(IWebView iWebView) {
        a.remove(iWebView);
        if (a.isEmpty()) {
            if (Utils.sWAPrintLog) {
                Log.d("SDKWaStat", "WebViewDetector:destroy");
            }
            WaStat.saveData(true);
        }
    }
}
