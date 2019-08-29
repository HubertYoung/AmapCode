package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import com.airbnb.lottie.model.layer.Layer;
import java.util.ArrayList;
import java.util.List;

/* renamed from: hy reason: default package */
/* compiled from: CompositionLayer */
public final class hy extends hx {
    public final List<hx> g = new ArrayList();
    @Nullable
    public Boolean h;
    @Nullable
    private final fu<Float, Float> i;
    private final RectF j = new RectF();
    private final RectF k = new RectF();
    @Nullable
    private Boolean l;

    public hy(ew ewVar, Layer layer, List<Layer> list, ev evVar) {
        hx hxVar;
        super(ewVar, layer);
        gy gyVar = layer.s;
        if (gyVar != null) {
            this.i = gyVar.a();
            a(this.i);
            this.i.a((a) this);
        } else {
            this.i = null;
        }
        LongSparseArray longSparseArray = new LongSparseArray(evVar.f.size());
        int size = list.size() - 1;
        hx hxVar2 = null;
        while (true) {
            if (size >= 0) {
                Layer layer2 = list.get(size);
                switch (layer2.e) {
                    case Shape:
                        hxVar = new ib(ewVar, layer2);
                        break;
                    case PreComp:
                        hxVar = new hy(ewVar, layer2, evVar.a.get(layer2.g), evVar);
                        break;
                    case Solid:
                        hxVar = new ic(ewVar, layer2);
                        break;
                    case Image:
                        hxVar = new hz(ewVar, layer2, evVar.k);
                        break;
                    case Null:
                        hxVar = new ia(ewVar, layer2);
                        break;
                    case Text:
                        hxVar = new id(ewVar, layer2);
                        break;
                    default:
                        new StringBuilder("Unknown layer type ").append(layer2.e);
                        hxVar = null;
                        break;
                }
                if (hxVar != null) {
                    longSparseArray.put(hxVar.c.d, hxVar);
                    if (hxVar2 == null) {
                        this.g.add(0, hxVar);
                        switch (layer2.u) {
                            case Add:
                            case Invert:
                                hxVar2 = hxVar;
                                break;
                        }
                    } else {
                        hxVar2.d = hxVar;
                        hxVar2 = null;
                    }
                }
                size--;
            } else {
                for (int i2 = 0; i2 < longSparseArray.size(); i2++) {
                    hx hxVar3 = (hx) longSparseArray.get(longSparseArray.keyAt(i2));
                    hx hxVar4 = (hx) longSparseArray.get(hxVar3.c.f);
                    if (hxVar4 != null) {
                        hxVar3.e = hxVar4;
                    }
                }
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(Canvas canvas, Matrix matrix, int i2) {
        eu.a("CompositionLayer#draw");
        canvas.save();
        this.k.set(0.0f, 0.0f, (float) this.c.o, (float) this.c.p);
        matrix.mapRect(this.k);
        for (int size = this.g.size() - 1; size >= 0; size--) {
            if (!this.k.isEmpty() ? canvas.clipRect(this.k) : true) {
                this.g.get(size).a(canvas, matrix, i2);
            }
        }
        canvas.restore();
        eu.b("CompositionLayer#draw");
    }

    public final void a(RectF rectF, Matrix matrix) {
        super.a(rectF, matrix);
        this.j.set(0.0f, 0.0f, 0.0f, 0.0f);
        for (int size = this.g.size() - 1; size >= 0; size--) {
            this.g.get(size).a(this.j, this.a);
            if (rectF.isEmpty()) {
                rectF.set(this.j);
            } else {
                rectF.set(Math.min(rectF.left, this.j.left), Math.min(rectF.top, this.j.top), Math.max(rectF.right, this.j.right), Math.max(rectF.bottom, this.j.bottom));
            }
        }
    }

    public final void a(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        super.a(f);
        if (this.i != null) {
            f = ((float) ((long) (((Float) this.i.a()).floatValue() * 1000.0f))) / ((float) this.b.a.a());
        }
        if (this.c.m != 0.0f) {
            f /= this.c.m;
        }
        float f2 = f - this.c.n;
        for (int size = this.g.size() - 1; size >= 0; size--) {
            this.g.get(size).a(f2);
        }
    }

    public final boolean e() {
        if (this.l == null) {
            for (int size = this.g.size() - 1; size >= 0; size--) {
                hx hxVar = this.g.get(size);
                if (hxVar instanceof ib) {
                    if (hxVar.d()) {
                        this.l = Boolean.TRUE;
                        return true;
                    }
                } else if ((hxVar instanceof hy) && ((hy) hxVar).e()) {
                    this.l = Boolean.TRUE;
                    return true;
                }
            }
            this.l = Boolean.FALSE;
        }
        return this.l.booleanValue();
    }

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            hx hxVar = this.g.get(i2);
            String str3 = hxVar.c.c;
            if (str == null) {
                hxVar.a((String) null, (String) null, colorFilter);
            } else if (str3.equals(str)) {
                hxVar.a(str, str2, colorFilter);
            }
        }
    }
}
