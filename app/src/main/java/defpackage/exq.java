package defpackage;

import android.text.TextUtils;

/* renamed from: exq reason: default package */
/* compiled from: OnNotifyArrivedReceiveCommand */
public final class exq extends exv {
    protected ezp a;
    private String h;

    public final String toString() {
        return "OnNotifyArrivedCommand";
    }

    public exq() {
        super(4);
    }

    public final ezp n_() {
        return this.a;
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        this.h = fau.b(this.a);
        ewx.a((String) "notification_v1", this.h);
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        this.h = ewx.a((String) "notification_v1");
        if (!TextUtils.isEmpty(this.h)) {
            this.a = fau.a(this.h);
            if (this.a != null) {
                this.a.q = this.c;
            }
        }
    }

    public final String b() {
        if (!TextUtils.isEmpty(this.h)) {
            return this.h;
        }
        if (this.a == null) {
            return null;
        }
        return fau.b(this.a);
    }
}
