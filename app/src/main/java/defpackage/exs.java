package defpackage;

/* renamed from: exs reason: default package */
/* compiled from: OnReceiveCommand */
public class exs extends fbh {
    public String d = null;
    public int e = 0;

    public String toString() {
        return "OnReceiveCommand";
    }

    public exs(int i) {
        super(i);
    }

    public void a(ewx ewx) {
        ewx.a((String) "req_id", this.d);
        ewx.a((String) "status_msg_code", this.e);
    }

    public void b(ewx ewx) {
        this.d = ewx.a((String) "req_id");
        this.e = ewx.b((String) "status_msg_code", this.e);
    }
}
