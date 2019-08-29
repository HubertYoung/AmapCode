package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: ff reason: default package */
/* compiled from: ContentGroup */
public final class ff implements fg, fn, a {
    private final Matrix a;
    private final Path b;
    private final RectF c;
    private final String d;
    private final List<fe> e;
    private final ew f;
    @Nullable
    private List<fn> g;
    @Nullable
    private gj h;

    private static List<fe> a(ew ewVar, hx hxVar, List<hn> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            fe a2 = list.get(i).a(ewVar, hxVar);
            if (a2 != null) {
                arrayList.add(a2);
            }
        }
        return arrayList;
    }

    @Nullable
    private static hi a(List<hn> list) {
        for (int i = 0; i < list.size(); i++) {
            hn hnVar = list.get(i);
            if (hnVar instanceof hi) {
                return (hi) hnVar;
            }
        }
        return null;
    }

    ff(ew ewVar, hx hxVar, String str, List<fe> list, @Nullable hi hiVar) {
        this.a = new Matrix();
        this.b = new Path();
        this.c = new RectF();
        this.d = str;
        this.f = ewVar;
        this.e = list;
        if (hiVar != null) {
            this.h = hiVar.a();
            this.h.a(hxVar);
            this.h.a((a) this);
        }
        ArrayList arrayList = new ArrayList();
        for (int size = list.size() - 1; size >= 0; size--) {
            fe feVar = list.get(size);
            if (feVar instanceof fl) {
                arrayList.add((fl) feVar);
            }
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            ((fl) arrayList.get(size2)).a(list.listIterator(list.size()));
        }
    }

    public final void a() {
        this.f.invalidateSelf();
    }

    public final String b() {
        return this.d;
    }

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        for (int i = 0; i < this.e.size(); i++) {
            fe feVar = this.e.get(i);
            if (feVar instanceof fg) {
                fg fgVar = (fg) feVar;
                if (str2 == null || str2.equals(feVar.b())) {
                    fgVar.a(str, (String) null, colorFilter);
                } else {
                    fgVar.a(str, str2, colorFilter);
                }
            }
        }
    }

    public final void a(List<fe> list, List<fe> list2) {
        ArrayList arrayList = new ArrayList(list.size() + this.e.size());
        arrayList.addAll(list);
        for (int size = this.e.size() - 1; size >= 0; size--) {
            fe feVar = this.e.get(size);
            feVar.a(arrayList, this.e.subList(0, size));
            arrayList.add(feVar);
        }
    }

    /* access modifiers changed from: 0000 */
    public final List<fn> c() {
        if (this.g == null) {
            this.g = new ArrayList();
            for (int i = 0; i < this.e.size(); i++) {
                fe feVar = this.e.get(i);
                if (feVar instanceof fn) {
                    this.g.add((fn) feVar);
                }
            }
        }
        return this.g;
    }

    /* access modifiers changed from: 0000 */
    public final Matrix d() {
        if (this.h != null) {
            return this.h.a();
        }
        this.a.reset();
        return this.a;
    }

    public final Path e() {
        this.a.reset();
        if (this.h != null) {
            this.a.set(this.h.a());
        }
        this.b.reset();
        for (int size = this.e.size() - 1; size >= 0; size--) {
            fe feVar = this.e.get(size);
            if (feVar instanceof fn) {
                this.b.addPath(((fn) feVar).e(), this.a);
            }
        }
        return this.b;
    }

    public final void a(Canvas canvas, Matrix matrix, int i) {
        this.a.set(matrix);
        if (this.h != null) {
            this.a.preConcat(this.h.a());
            i = (int) ((((((float) ((Integer) this.h.e.a()).intValue()) / 100.0f) * ((float) i)) / 255.0f) * 255.0f);
        }
        for (int size = this.e.size() - 1; size >= 0; size--) {
            fe feVar = this.e.get(size);
            if (feVar instanceof fg) {
                ((fg) feVar).a(canvas, this.a, i);
            }
        }
    }

    public final void a(RectF rectF, Matrix matrix) {
        this.a.set(matrix);
        if (this.h != null) {
            this.a.preConcat(this.h.a());
        }
        this.c.set(0.0f, 0.0f, 0.0f, 0.0f);
        for (int size = this.e.size() - 1; size >= 0; size--) {
            fe feVar = this.e.get(size);
            if (feVar instanceof fg) {
                ((fg) feVar).a(this.c, this.a);
                if (rectF.isEmpty()) {
                    rectF.set(this.c);
                } else {
                    rectF.set(Math.min(rectF.left, this.c.left), Math.min(rectF.top, this.c.top), Math.max(rectF.right, this.c.right), Math.max(rectF.bottom, this.c.bottom));
                }
            }
        }
    }

    public ff(ew ewVar, hx hxVar, hv hvVar) {
        this(ewVar, hxVar, hvVar.a, a(ewVar, hxVar, hvVar.b), a(hvVar.b));
    }
}
