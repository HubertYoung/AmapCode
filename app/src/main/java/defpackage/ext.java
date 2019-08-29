package defpackage;

import java.util.ArrayList;

/* renamed from: ext reason: default package */
/* compiled from: OnTagsReceiveCommand */
public final class ext extends exs {
    public ArrayList<String> a = null;
    public ArrayList<String> b = null;

    public final String toString() {
        return "OnSetTagsCommand";
    }

    public ext(int i) {
        super(i);
    }

    public final void a(ewx ewx) {
        super.a(ewx);
        ewx.a((String) "content", this.a);
        ewx.a((String) "error_msg", this.b);
    }

    public final void b(ewx ewx) {
        super.b(ewx);
        this.a = ewx.b("content");
        this.b = ewx.b("error_msg");
    }
}
