package defpackage;

import android.os.Bundle;

/* renamed from: exn reason: default package */
/* compiled from: OnLogReceiveCommand */
public final class exn extends exs {
    public String a;
    public int b = 0;
    public boolean c = false;

    public final String toString() {
        return "OnLogCommand";
    }

    public exn() {
        super(7);
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "content", this.a);
        ewx.a((String) "log_level", this.b);
        boolean z = this.c;
        if (ewx.a == null) {
            ewx.a = new Bundle();
        }
        ewx.a.putBoolean("is_server_log", z);
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        this.a = ewx.a((String) "content");
        boolean z = false;
        this.b = ewx.b((String) "log_level", 0);
        if (ewx.a != null) {
            z = ewx.a.getBoolean("is_server_log", false);
        }
        this.c = z;
    }
}
