package com.taobao.applink.f;

import android.text.TextUtils;
import android.webkit.WebView;
import com.taobao.applink.f.a.b;
import java.util.List;

class c implements b {
    final /* synthetic */ WebView a;
    final /* synthetic */ b b;

    c(b bVar, WebView webView) {
        this.b = bVar;
        this.a = webView;
    }

    public void a(String str) {
        try {
            List f = f.f(str);
            if (f != null && f.size() != 0) {
                for (int i = 0; i < f.size(); i++) {
                    f fVar = (f) f.get(i);
                    String a2 = fVar.a();
                    if (!TextUtils.isEmpty(a2)) {
                        ((b) this.b.b.get(a2)).a(fVar.b());
                        this.b.b.remove(a2);
                    } else {
                        String c = fVar.c();
                        (!TextUtils.isEmpty(fVar.e()) ? (com.taobao.applink.f.a.c) this.b.c.get(fVar.e()) : this.b.d).a(fVar.d(), !TextUtils.isEmpty(c) ? new d(this, c) : new e(this));
                    }
                }
            }
        } catch (Exception unused) {
        }
    }
}
