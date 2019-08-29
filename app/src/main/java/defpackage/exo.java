package defpackage;

import android.text.TextUtils;

/* renamed from: exo reason: default package */
/* compiled from: OnMessageReceiveCommand */
public final class exo extends exv {
    protected ezr a;

    public final String toString() {
        return "OnMessageCommand";
    }

    public exo() {
        super(3);
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "msg_v1", this.a.a());
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        String a2 = ewx.a((String) "msg_v1");
        if (!TextUtils.isEmpty(a2)) {
            this.a = new ezr(a2);
            this.a.c = this.c;
        }
    }

    public final String m_() {
        if (this.a == null) {
            return null;
        }
        return this.a.a();
    }

    public final ezr b() {
        return this.a;
    }
}
