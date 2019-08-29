package com.taobao.applink.a;

import android.content.Context;
import com.taobao.applink.auth.TBAppLinkAuthListener;
import com.taobao.applink.auth.a;

public class b extends d {
    public TBAppLinkAuthListener a;
    public String b;

    public b(TBAppLinkAuthListener tBAppLinkAuthListener, String str) {
        this.a = tBAppLinkAuthListener;
        this.b = str;
    }

    public void a() {
        if (this.a != null) {
            new a(this.a).a(this.b);
        }
    }

    public void a(Context context) {
        if (this.a == null) {
        }
    }

    public void a(Context context, com.taobao.applink.f.a.b bVar) {
        if (context != null && bVar != null) {
            c cVar = new c(this, bVar);
            this.c = bVar;
            this.a = cVar;
        }
    }

    public void a(Exception exc) {
    }
}
