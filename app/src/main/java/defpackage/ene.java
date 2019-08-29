package defpackage;

import android.text.TextUtils;

/* renamed from: ene reason: default package */
/* compiled from: WebViewUrlRewriter */
public final class ene implements enc {
    public boolean a = true;
    private enb b = new enb();

    public ene() {
        enb enb = this.b;
        end end = new end();
        if (!TextUtils.isEmpty("local_html")) {
            enb.a.put("local_html", end);
        }
    }

    public final end a() {
        return (end) this.b.a("local_html");
    }

    public final String b(String str) {
        return !this.a ? str : this.b.b(str);
    }
}
