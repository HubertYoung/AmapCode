package defpackage;

import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ft reason: default package */
/* compiled from: TrimPathContent */
public final class ft implements fe, a {
    final Type a;
    public final fu<?, Float> b;
    public final fu<?, Float> c;
    public final fu<?, Float> d;
    private String e;
    private final List<a> f = new ArrayList();

    public final void a(List<fe> list, List<fe> list2) {
    }

    public ft(hx hxVar, ShapeTrimPath shapeTrimPath) {
        this.e = shapeTrimPath.a;
        this.a = shapeTrimPath.b;
        this.b = shapeTrimPath.c.a();
        this.c = shapeTrimPath.d.a();
        this.d = shapeTrimPath.e.a();
        hxVar.a(this.b);
        hxVar.a(this.c);
        hxVar.a(this.d);
        this.b.a((a) this);
        this.c.a((a) this);
        this.d.a((a) this);
    }

    public final void a() {
        for (int i = 0; i < this.f.size(); i++) {
            this.f.get(i).a();
        }
    }

    public final String b() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public final void a(a aVar) {
        this.f.add(aVar);
    }
}
