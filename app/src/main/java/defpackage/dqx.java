package defpackage;

import android.support.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

/* renamed from: dqx reason: default package */
/* compiled from: SketchScenicEngineHelper */
public final class dqx {
    public amv a;
    private final dqy b;
    private Map<Integer, String> c = new HashMap();

    public dqx(@NonNull bty bty) {
        this.b = new dqy(bty);
    }

    public final boolean a() {
        return this.a != null && this.a.d;
    }

    public final void b() {
        this.b.a();
        dqy.a(this.c);
        dqy.c(this.c);
    }

    public final void c() {
        dqy.b(this.c);
        this.b.b();
    }
}
