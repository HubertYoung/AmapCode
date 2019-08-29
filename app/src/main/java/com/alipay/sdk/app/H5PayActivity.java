package com.alipay.sdk.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.app.statistic.c;
import com.alipay.sdk.util.l;

public class H5PayActivity extends Activity {
    private WebView a;
    private WebViewClient b;

    private void b() {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable unused) {
        }
    }

    public void onBackPressed() {
        if (!this.a.canGoBack()) {
            i.a = i.a();
            finish();
        } else if (((b) this.b).c) {
            j a2 = j.a(j.NETWORK_ERROR.h);
            i.a = i.a(a2.h, a2.i, "");
            finish();
        }
    }

    public void finish() {
        a();
        super.finish();
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0009 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a() {
        /*
            r2 = this;
            java.lang.Object r0 = com.alipay.sdk.app.PayTask.a
            monitor-enter(r0)
            r0.notify()     // Catch:{ Exception -> 0x0009 }
            goto L_0x0009
        L_0x0007:
            r1 = move-exception
            goto L_0x000b
        L_0x0009:
            monitor-exit(r0)     // Catch:{ all -> 0x0007 }
            return
        L_0x000b:
            monitor-exit(r0)     // Catch:{ all -> 0x0007 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.H5PayActivity.a():void");
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.a != null) {
            this.a.removeAllViews();
            ((ViewGroup) this.a.getParent()).removeAllViews();
            try {
                this.a.destroy();
            } catch (Throwable unused) {
            }
            this.a = null;
        }
        if (this.b != null) {
            b bVar = (b) this.b;
            bVar.b = null;
            bVar.a = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        try {
            super.requestWindowFeature(1);
        } catch (Throwable unused) {
        }
        super.onCreate(bundle);
        try {
            Bundle extras = getIntent().getExtras();
            String string = extras.getString("url");
            if (!l.b(string)) {
                finish();
                return;
            }
            try {
                this.a = l.a((Activity) this, string, extras.getString("cookie"));
                this.b = new b(this);
                this.a.setWebViewClient(this.b);
            } catch (Throwable th) {
                a.a((String) c.b, (String) "GetInstalledAppEx", th);
                finish();
            }
        } catch (Exception unused2) {
            finish();
        }
    }
}
