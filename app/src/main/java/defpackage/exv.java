package defpackage;

/* renamed from: exv reason: default package */
/* compiled from: OnVerifyReceiveCommand */
public abstract class exv extends exs {
    public String b;
    public long c;

    public exv(int i) {
        super(i);
    }

    public void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "OnVerifyCallBackCommand.EXTRA_SECURITY_CONTENT", this.b);
        ewx.a((String) "notify_id", this.c);
    }

    public void b(ewx ewx) {
        super.b(ewx);
        this.b = ewx.a((String) "OnVerifyCallBackCommand.EXTRA_SECURITY_CONTENT");
        this.c = ewx.b((String) "notify_id", -1);
    }
}
