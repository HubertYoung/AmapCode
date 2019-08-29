package defpackage;

import android.text.TextUtils;

/* renamed from: exp reason: default package */
/* compiled from: OnNotificationClickReceiveCommand */
public final class exp extends fbh {
    public String a;
    public long b;
    public ezp c;

    public final String toString() {
        return "OnNotificationClickCommand";
    }

    public exp(String str, long j, ezp ezp) {
        super(5);
        this.a = str;
        this.b = j;
        this.c = ezp;
    }

    public exp() {
        super(5);
    }

    public final void a(ewx ewx) {
        ewx.a((String) "package_name", this.a);
        ewx.a((String) "notify_id", this.b);
        ewx.a((String) "notification_v1", fau.b(this.c));
    }

    public final void b(ewx ewx) {
        this.a = ewx.a((String) "package_name");
        this.b = ewx.b((String) "notify_id", -1);
        String a2 = ewx.a((String) "notification_v1");
        if (!TextUtils.isEmpty(a2)) {
            this.c = fau.a(a2);
        }
        if (this.c != null) {
            this.c.q = this.b;
        }
    }
}
