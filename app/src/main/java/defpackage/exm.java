package defpackage;

import java.util.ArrayList;

/* renamed from: exm reason: default package */
/* compiled from: OnListTagReceiveCommand */
public final class exm extends exs {
    private ArrayList<String> a;

    public final String toString() {
        return "OnListTagCommand";
    }

    public exm() {
        super(8);
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "tags_list", this.a);
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        this.a = ewx.b("tags_list");
    }
}
