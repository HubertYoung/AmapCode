package defpackage;

import android.graphics.Path;
import java.util.List;
import org.json.JSONObject;

/* renamed from: he reason: default package */
/* compiled from: AnimatableShapeValue */
public final class he extends hl<ht, Path> {
    private final Path c;

    /* renamed from: he$a */
    /* compiled from: AnimatableShapeValue */
    public static final class a {
        public static he a(JSONObject jSONObject, ev evVar) {
            a a = hk.a(jSONObject, evVar.k, evVar, defpackage.ht.a.a).a();
            return new he(a.a, (ht) a.b, 0);
        }
    }

    /* synthetic */ he(List list, ht htVar, byte b) {
        this(list, htVar);
    }

    private he(List<fc<ht>> list, ht htVar) {
        super(list, htVar);
        this.c = new Path();
    }

    public final fu<ht, Path> a() {
        if (!d()) {
            return new gh(a((ht) this.b));
        }
        return new gf(this.a);
    }

    /* access modifiers changed from: private */
    public Path a(ht htVar) {
        this.c.reset();
        ii.a(htVar, this.c);
        return this.c;
    }
}
