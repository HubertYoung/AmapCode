package defpackage;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.layer.Layer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: id reason: default package */
/* compiled from: TextLayer */
public final class id extends hx {
    private final char[] g = new char[1];
    private final RectF h = new RectF();
    private final Matrix i = new Matrix();
    private final Paint j = new Paint() {
        {
            setStyle(Style.FILL);
        }
    };
    private final Paint k = new Paint() {
        {
            setStyle(Style.STROKE);
        }
    };
    private final Map<gs, List<ff>> l = new HashMap();
    private final gi m;
    private final ew n;
    private final ev o;
    @Nullable
    private fu<Integer, Integer> p;
    @Nullable
    private fu<Integer, Integer> q;
    @Nullable
    private fu<Float, Float> r;
    @Nullable
    private fu<Float, Float> s;

    id(ew ewVar, Layer layer) {
        super(ewVar, layer);
        this.n = ewVar;
        this.o = layer.b;
        this.m = layer.q.a();
        this.m.a((a) this);
        a((fu<?, ?>) this.m);
        hh hhVar = layer.r;
        if (!(hhVar == null || hhVar.a == null)) {
            this.p = hhVar.a.a();
            this.p.a((a) this);
            a(this.p);
        }
        if (!(hhVar == null || hhVar.b == null)) {
            this.q = hhVar.b.a();
            this.q.a((a) this);
            a(this.q);
        }
        if (!(hhVar == null || hhVar.c == null)) {
            this.r = hhVar.c.a();
            this.r.a((a) this);
            a(this.r);
        }
        if (hhVar != null && hhVar.d != null) {
            this.s = hhVar.d.a();
            this.s.a((a) this);
            a(this.s);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(Canvas canvas, Matrix matrix, int i2) {
        String str;
        List list;
        Canvas canvas2 = canvas;
        Matrix matrix2 = matrix;
        canvas.save();
        if (!this.n.e()) {
            canvas.setMatrix(matrix);
        }
        gp gpVar = (gp) this.m.a();
        gr grVar = this.o.c.get(gpVar.b);
        if (grVar == null) {
            canvas.restore();
            return;
        }
        if (this.p != null) {
            this.j.setColor(((Integer) this.p.a()).intValue());
        } else {
            this.j.setColor(gpVar.h);
        }
        if (this.q != null) {
            this.k.setColor(((Integer) this.q.a()).intValue());
        } else {
            this.k.setColor(gpVar.i);
        }
        int intValue = (((Integer) this.f.e.a()).intValue() * 255) / 100;
        this.j.setAlpha(intValue);
        this.k.setAlpha(intValue);
        if (this.r != null) {
            this.k.setStrokeWidth(((Float) this.r.a()).floatValue());
        } else {
            this.k.setStrokeWidth(((float) gpVar.j) * this.o.k * ij.a(matrix));
        }
        if (this.n.e()) {
            float f = ((float) gpVar.c) / 100.0f;
            float a = ij.a(matrix);
            String str2 = gpVar.a;
            int i3 = 0;
            while (i3 < str2.length()) {
                gs gsVar = (gs) this.o.d.get(gs.a(str2.charAt(i3), grVar.a, grVar.c));
                if (gsVar != null) {
                    if (this.l.containsKey(gsVar)) {
                        list = this.l.get(gsVar);
                        str = str2;
                    } else {
                        List<hv> list2 = gsVar.a;
                        int size = list2.size();
                        List arrayList = new ArrayList(size);
                        int i4 = 0;
                        while (i4 < size) {
                            String str3 = str2;
                            arrayList.add(new ff(this.n, this, list2.get(i4)));
                            i4++;
                            str2 = str3;
                        }
                        str = str2;
                        this.l.put(gsVar, arrayList);
                        list = arrayList;
                    }
                    for (int i5 = 0; i5 < list.size(); i5++) {
                        Path e = ((ff) list.get(i5)).e();
                        e.computeBounds(this.h, false);
                        this.i.set(matrix2);
                        this.i.preTranslate(0.0f, ((float) (-gpVar.g)) * this.o.k);
                        this.i.preScale(f, f);
                        e.transform(this.i);
                        if (gpVar.k) {
                            a(e, this.j, canvas2);
                            a(e, this.k, canvas2);
                        } else {
                            a(e, this.k, canvas2);
                            a(e, this.j, canvas2);
                        }
                    }
                    float f2 = ((float) gsVar.b) * f * this.o.k * a;
                    float f3 = ((float) gpVar.e) / 10.0f;
                    if (this.s != null) {
                        f3 += ((Float) this.s.a()).floatValue();
                    }
                    canvas2.translate(f2 + (f3 * a), 0.0f);
                } else {
                    str = str2;
                }
                i3++;
                str2 = str;
            }
        } else {
            a(gpVar, grVar, matrix2, canvas2);
        }
        canvas.restore();
    }

    private void a(gp gpVar, gr grVar, Matrix matrix, Canvas canvas) {
        gk gkVar;
        float a = ij.a(matrix);
        ew ewVar = this.n;
        T t = grVar.a;
        T t2 = grVar.c;
        Typeface typeface = null;
        if (ewVar.getCallback() == null) {
            gkVar = null;
        } else {
            if (ewVar.i == null) {
                ewVar.i = new gk(ewVar.getCallback(), ewVar.j);
            }
            gkVar = ewVar.i;
        }
        if (gkVar != null) {
            gu<String> guVar = gkVar.a;
            guVar.a = t;
            guVar.b = t2;
            typeface = gkVar.b.get(gkVar.a);
            if (typeface == null) {
                typeface = gkVar.c.get(t);
                if (typeface == null) {
                    StringBuilder sb = new StringBuilder("fonts/");
                    sb.append(t);
                    sb.append(gkVar.f);
                    typeface = Typeface.createFromAsset(gkVar.d, sb.toString());
                    gkVar.c.put(t, typeface);
                }
                boolean contains = t2.contains("Italic");
                boolean contains2 = t2.contains("Bold");
                int i2 = (!contains || !contains2) ? contains ? 2 : contains2 ? 1 : 0 : 3;
                if (typeface.getStyle() != i2) {
                    typeface = Typeface.create(typeface, i2);
                }
                gkVar.b.put(gkVar.a, typeface);
            }
        }
        if (typeface != null) {
            String str = gpVar.a;
            fb fbVar = this.n.k;
            if (fbVar != null) {
                if (fbVar.b && fbVar.a.containsKey(str)) {
                    str = fbVar.a.get(str);
                } else if (fbVar.b) {
                    fbVar.a.put(str, str);
                }
            }
            this.j.setTypeface(typeface);
            this.j.setTextSize(((float) gpVar.c) * this.o.k);
            this.k.setTypeface(this.j.getTypeface());
            this.k.setTextSize(this.j.getTextSize());
            for (int i3 = 0; i3 < str.length(); i3++) {
                char charAt = str.charAt(i3);
                this.g[0] = charAt;
                if (gpVar.k) {
                    a(this.g, this.j, canvas);
                    a(this.g, this.k, canvas);
                } else {
                    a(this.g, this.k, canvas);
                    a(this.g, this.j, canvas);
                }
                this.g[0] = charAt;
                float measureText = this.j.measureText(this.g, 0, 1);
                float f = ((float) gpVar.e) / 10.0f;
                if (this.s != null) {
                    f += ((Float) this.s.a()).floatValue();
                }
                canvas.translate(measureText + (f * a), 0.0f);
            }
        }
    }

    private static void a(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() != 0) {
            if (paint.getStyle() != Style.STROKE || paint.getStrokeWidth() != 0.0f) {
                canvas.drawPath(path, paint);
            }
        }
    }

    private static void a(char[] cArr, Paint paint, Canvas canvas) {
        if (paint.getColor() != 0) {
            if (paint.getStyle() != Style.STROKE || paint.getStrokeWidth() != 0.0f) {
                canvas.drawText(cArr, 0, 1, 0.0f, 0.0f, paint);
            }
        }
    }
}
