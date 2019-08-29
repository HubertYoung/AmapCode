package defpackage;

import com.autonavi.widget.webview.MultiTabWebView;

/* renamed from: erw reason: default package */
/* compiled from: WebViewSettings */
public final class erw {
    public boolean a;
    public boolean b;
    public boolean c;
    public String d;
    public MultiTabWebView e;

    public final void a() {
        boolean z = !this.a;
        this.a = true;
        c(z);
    }

    public final void a(boolean z) {
        boolean z2 = this.b != z;
        this.b = z;
        c(z2);
    }

    public final void b(boolean z) {
        boolean z2 = this.c != z;
        this.c = z;
        c(z2);
    }

    public final void a(String str) {
        boolean z = str != null && !str.equals(this.d);
        this.d = str;
        c(z);
    }

    private void c(boolean z) {
        if (z && this.e != null) {
            this.e.setWebSettings(this);
        }
    }
}
