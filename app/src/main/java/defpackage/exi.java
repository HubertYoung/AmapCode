package defpackage;

/* renamed from: exi reason: default package */
/* compiled from: MsgArriveCommand */
public final class exi extends fbh {
    private String a;

    public exi() {
        super(2013);
    }

    public exi(String str) {
        this();
        this.a = str;
    }

    public final void a(ewx ewx) {
        ewx.a((String) "MsgArriveCommand.MSG_TAG", this.a);
    }

    public final void b(ewx ewx) {
        this.a = ewx.a((String) "MsgArriveCommand.MSG_TAG");
    }
}
