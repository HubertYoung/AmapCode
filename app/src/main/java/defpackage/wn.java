package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.widget.webview.MultiTabWebView;

/* renamed from: wn reason: default package */
/* compiled from: MultiTabWebViewAdapter */
public final class wn implements wm {
    private MultiTabWebView a;
    private JsAdapter b;

    public wn(MultiTabWebView multiTabWebView, JsAdapter jsAdapter) {
        this.a = multiTabWebView;
        this.b = jsAdapter;
    }

    public final void a(String str, String str2) {
        if (this.a != null) {
            MultiTabWebView multiTabWebView = this.a;
            StringBuilder sb = new StringBuilder("javascript:");
            sb.append(str);
            sb.append("(");
            sb.append(str2);
            sb.append(")");
            multiTabWebView.loadJs(sb.toString());
        }
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.loadJs(str);
        }
    }

    public final boolean a() {
        if (this.a == null || !this.a.canGoBack()) {
            return false;
        }
        this.a.stopLoading();
        int i = this.b.getBundle().getInt("gobackStep");
        if (i > 0) {
            a(-i);
            this.b.getBundle().remove("gobackStep");
        } else {
            this.a.goBack();
        }
        return true;
    }

    public final boolean b() {
        return this.a != null && this.a.canGoBack();
    }

    public final void a(int i) {
        if (this.a != null) {
            this.a.goBackOrForward(-i);
        }
    }

    public final void a(boolean z) {
        if (this.a != null) {
            this.a.setViewLongClickable(z);
        }
    }

    public final String c() {
        return this.a != null ? this.a.getUrl() : "";
    }
}
