package defpackage;

/* renamed from: exj reason: default package */
/* compiled from: OnAppReceiveCommand */
public final class exj extends exs {
    public String a;
    private String b;
    private String c;

    public final String toString() {
        return "OnBindCommand";
    }

    public exj(int i) {
        super(i);
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "app_id", this.b);
        ewx.a((String) "client_id", this.c);
        ewx.a((String) "client_token", this.a);
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        this.b = ewx.a((String) "app_id");
        this.c = ewx.a((String) "client_id");
        this.a = ewx.a((String) "client_token");
    }
}
