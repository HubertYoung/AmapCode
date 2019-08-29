package defpackage;

import com.uc.webview.export.WebView;

/* renamed from: wk reason: default package */
/* compiled from: AbstractBaseWebViewAdapter */
public final class wk implements wm {
    private a a;

    /* renamed from: wk$a */
    /* compiled from: AbstractBaseWebViewAdapter */
    public interface a {
        boolean canGoBack();

        String getUrl();

        WebView getWebView();

        void goBackOrForward(int i);

        void gobackByStep();

        void loadJs(String str);

        void stopLoading();
    }

    public wk(a aVar) {
        this.a = aVar;
    }

    public final void a(String str, String str2) {
        if (this.a != null) {
            a aVar = this.a;
            StringBuilder sb = new StringBuilder("javascript:");
            sb.append(str);
            sb.append("(");
            sb.append(str2);
            sb.append(")");
            aVar.loadJs(sb.toString());
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
        this.a.gobackByStep();
        return true;
    }

    public final boolean b() {
        if (this.a != null) {
            return this.a.canGoBack();
        }
        return false;
    }

    public final void a(int i) {
        if (this.a != null) {
            this.a.goBackOrForward(-i);
        }
    }

    public final void a(boolean z) {
        if (this.a != null) {
            this.a.getWebView().setLongClickable(z);
        }
    }

    public final String c() {
        return this.a != null ? this.a.getUrl() : "";
    }
}
