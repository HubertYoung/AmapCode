package defpackage;

/* renamed from: exu reason: default package */
/* compiled from: OnUndoMsgReceiveCommand */
public final class exu extends exv {
    public long a = -1;
    private int h;

    public final String toString() {
        return "OnUndoMsgCommand";
    }

    public exu() {
        super(20);
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "undo_msg_v1", this.a);
        ewx.a((String) "undo_msg_type_v1", this.h);
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        this.a = ewx.b((String) "undo_msg_v1", this.a);
        this.h = ewx.b((String) "undo_msg_type_v1", 0);
    }
}
