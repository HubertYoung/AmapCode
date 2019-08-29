package defpackage;

/* renamed from: exk reason: default package */
/* compiled from: OnChangePushStatusReceiveCommand */
public final class exk extends exs {
    public int a = -1;
    public int b = -1;

    public final String toString() {
        return "OnChangePushStatusCommand";
    }

    public exk() {
        super(12);
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "OnChangePushStatus.EXTRA_REQ_SERVICE_STATUS", this.a);
        ewx.a((String) "OnChangePushStatus.EXTRA_REQ_RECEIVER_STATUS", this.b);
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        this.a = ewx.b((String) "OnChangePushStatus.EXTRA_REQ_SERVICE_STATUS", this.a);
        this.b = ewx.b((String) "OnChangePushStatus.EXTRA_REQ_RECEIVER_STATUS", this.b);
    }
}
