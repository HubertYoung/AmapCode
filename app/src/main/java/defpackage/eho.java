package defpackage;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: eho reason: default package */
/* compiled from: OfoCountdownTimer */
public final class eho extends ebq {
    CopyOnWriteArrayList<ehp> a = new CopyOnWriteArrayList<>();
    private long b;

    public eho(long j) {
        super("ofoCountDown", 10, j * 1000, 1000);
        this.b = j;
    }

    public final void b() {
        super.b();
        ehs.a((String) "share_bike_unlocking_status_id", (String) "true");
        StringBuilder sb = new StringBuilder("call start ");
        sb.append(this.b);
        sb.append("秒");
        eao.a((String) "ofoCountDown", sb.toString());
    }

    public final void c() {
        super.c();
        eao.a((String) "ofoCountDown", (String) "call stop");
    }

    public final void a(long j) {
        eao.a((String) "ofoCountDown", "onTick ".concat(String.valueOf(j)));
        ehs.a((int) (this.b - j));
        Iterator<ehp> it = this.a.iterator();
        while (it.hasNext()) {
            ehp next = it.next();
            if (next != null) {
                next.a(String.valueOf(this.b - j));
            }
        }
    }

    public final void a() {
        eao.a((String) "ofoCountDown", (String) "onStop");
        ehs.a((String) "share_bike_unlocking_status_id", (String) "false");
        eht.d();
        Iterator<ehp> it = this.a.iterator();
        while (it.hasNext()) {
            ehp next = it.next();
            if (next != null) {
                next.a();
            }
        }
    }
}
