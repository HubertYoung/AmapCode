package defpackage;

import android.graphics.Path;
import android.graphics.Path.FillType;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import java.util.List;

/* renamed from: fr reason: default package */
/* compiled from: ShapeContent */
public final class fr implements fn, a {
    private final Path a = new Path();
    private final String b;
    private final ew c;
    private final fu<?, Path> d;
    private boolean e;
    @Nullable
    private ft f;

    public fr(ew ewVar, hx hxVar, hw hwVar) {
        this.b = hwVar.a;
        this.c = ewVar;
        this.d = hwVar.b.a();
        hxVar.a(this.d);
        this.d.a((a) this);
    }

    public final void a(List<fe> list, List<fe> list2) {
        for (int i = 0; i < list.size(); i++) {
            fe feVar = list.get(i);
            if (feVar instanceof ft) {
                ft ftVar = (ft) feVar;
                if (ftVar.a == Type.Simultaneously) {
                    this.f = ftVar;
                    this.f.a(this);
                }
            }
        }
    }

    public final Path e() {
        if (this.e) {
            return this.a;
        }
        this.a.reset();
        this.a.set((Path) this.d.a());
        this.a.setFillType(FillType.EVEN_ODD);
        ij.a(this.a, this.f);
        this.e = true;
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final void a() {
        this.e = false;
        this.c.invalidateSelf();
    }
}
