package defpackage;

import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.AmapWebView;
import com.amap.bundle.webview.widget.AmapWebView.a;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.widget.webview.MultiTabWebView;
import com.autonavi.widget.webview.inner.SafeWebView.b;
import com.uc.webview.export.WebView;

/* renamed from: ajd reason: default package */
/* compiled from: TransparentWebViewLayer */
public final class ajd implements IViewLayer, enf, vy {
    public MultiTabWebView a;
    public final bid b;
    public boolean c = false;
    public final String d;
    /* access modifiers changed from: private */
    public JsAdapter e;
    /* access modifiers changed from: private */
    public boolean f = false;
    private View g;
    /* access modifiers changed from: private */
    public volatile boolean h = false;

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void showBackground(boolean z) {
    }

    public ajd(bid bid, String str) {
        this.b = bid;
        this.d = str;
        this.g = LayoutInflater.from(DoNotUseTool.getContext()).inflate(R.layout.webview_transparent_page, null);
        this.a = (MultiTabWebView) this.g.findViewById(R.id.webview);
        this.e = new JsAdapter(bid, this.a, this);
        JsAdapter jsAdapter = this.e;
        MultiTabWebView multiTabWebView = this.a;
        multiTabWebView.setSupportMultiTab(false);
        multiTabWebView.setJavaScriptEnable(true);
        multiTabWebView.setUICreator(new erz() {
            @Nullable
            public final ProgressBar a() {
                ProgressBar progressBar = new ProgressBar(DoNotUseTool.getContext());
                progressBar.setVisibility(8);
                return progressBar;
            }
        });
        multiTabWebView.setViewBackgroundColor(0);
        multiTabWebView.addJavaScriptInterface(new a(jsAdapter), "jsInterface");
        multiTabWebView.addJavaScriptInterface(new bnr(), "kvInterface");
        multiTabWebView.addWebViewClient(new b() {
            public final void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (!ajd.this.f) {
                    webView.setOnTouchListener(null);
                    ajd.this.f = true;
                }
            }

            public final void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                if (!ajd.this.f) {
                    ajd.this.a();
                }
            }
        });
        if (multiTabWebView instanceof AmapWebView) {
            ((AmapWebView) multiTabWebView).setSslHandleListener(new a() {
                public final void a() {
                    if (!ajd.this.f) {
                        ajd.this.a();
                    }
                }
            });
        }
    }

    public final View getView() {
        return this.g;
    }

    public final boolean onBackPressed() {
        a();
        return true;
    }

    public final void a() {
        if (this.c) {
            if (this.b != null) {
                this.b.dismissViewLayer(this);
            }
            this.c = false;
            b();
        }
    }

    private void b() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public final void run() {
                if (ajd.this.e != null) {
                    ajd.this.e.onDestory();
                    ajd.this.e = null;
                }
                ajd.this.h = true;
                if (ajd.this.a != null) {
                    ajd.this.a.destroy();
                }
            }
        }, 100);
    }
}
