package defpackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/* renamed from: fbh reason: default package */
/* compiled from: PushCommand */
public abstract class fbh {
    public int f = -1;
    public String g;

    /* access modifiers changed from: protected */
    public abstract void a(ewx ewx);

    public boolean a() {
        return false;
    }

    /* access modifiers changed from: protected */
    public abstract void b(ewx ewx);

    public fbh(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("PushCommand: the value of command must > 0.");
        }
        this.f = i;
    }

    public final void c(ewx ewx) {
        String a = fbi.a(this.f);
        if (a == null) {
            a = "";
        }
        ewx.a((String) "method", a);
        e(ewx);
    }

    public final void a(Intent intent) {
        ewx a = ewx.a(intent);
        if (a == null) {
            fat.b((String) "PushCommand", (String) "bundleWapper is null");
            return;
        }
        a.a((String) "method", this.f);
        e(a);
        Bundle bundle = a.a;
        if (bundle != null) {
            intent.putExtras(bundle);
        }
    }

    private final void e(ewx ewx) {
        ewx.a((String) "command", this.f);
        ewx.a((String) "client_pkgname", this.g);
        a(ewx);
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public final void d(ewx ewx) {
        String str = ewx.b;
        if (!TextUtils.isEmpty(str)) {
            this.g = str;
        } else {
            this.g = ewx.a((String) "client_pkgname");
        }
        b(ewx);
    }
}
