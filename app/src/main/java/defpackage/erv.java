package defpackage;

import com.uc.webview.export.WebView;

/* renamed from: erv reason: default package */
/* compiled from: UCInitPreManager */
public final class erv {
    public static volatile a a;

    /* renamed from: erv$a */
    /* compiled from: UCInitPreManager */
    public interface a {
        void a();

        void a(WebView webView);
    }

    public static void a() {
        a aVar = a;
        if (aVar != null) {
            aVar.a();
        }
    }

    public static void a(WebView webView) {
        a aVar = a;
        if (aVar != null) {
            aVar.a(webView);
        }
    }
}
