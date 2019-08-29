package defpackage;

/* renamed from: exc reason: default package */
/* compiled from: AppCommand */
public final class exc extends exd {
    public String a;
    public String b;
    private String h;
    private String i;

    public exc(String str) {
        super(str);
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "sdk_clients", this.h);
        ewx.a((String) "sdk_version", 280);
        ewx.a((String) "BaseAppCommand.EXTRA_APPID", this.b);
        ewx.a((String) "BaseAppCommand.EXTRA_APPKEY", this.a);
        ewx.a((String) "PUSH_REGID", this.i);
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        this.h = ewx.a((String) "sdk_clients");
        this.b = ewx.a((String) "BaseAppCommand.EXTRA_APPID");
        this.a = ewx.a((String) "BaseAppCommand.EXTRA_APPKEY");
        this.i = ewx.a((String) "PUSH_REGID");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AppCommand:");
        sb.append(this.f);
        return sb.toString();
    }
}
