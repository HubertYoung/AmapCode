package defpackage;

import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import com.airbnb.lottie.model.content.GradientType;
import java.util.ArrayList;
import java.util.List;

/* renamed from: fj reason: default package */
/* compiled from: GradientFillContent */
public final class fj implements fg, a {
    private final String a;
    private final LongSparseArray<LinearGradient> b = new LongSparseArray<>();
    private final LongSparseArray<RadialGradient> c = new LongSparseArray<>();
    private final Matrix d = new Matrix();
    private final Path e = new Path();
    private final Paint f = new Paint(1);
    private final RectF g = new RectF();
    private final List<fn> h = new ArrayList();
    private final GradientType i;
    private final fu<ho, ho> j;
    private final fu<Integer, Integer> k;
    private final fu<PointF, PointF> l;
    private final fu<PointF, PointF> m;
    private final ew n;
    private final int o;

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
    }

    public fj(ew ewVar, hx hxVar, hp hpVar) {
        this.a = hpVar.g;
        this.n = ewVar;
        this.i = hpVar.a;
        this.e.setFillType(hpVar.b);
        this.o = (int) (ewVar.a.a() / 32);
        this.j = hpVar.c.a();
        this.j.a((a) this);
        hxVar.a(this.j);
        this.k = hpVar.d.a();
        this.k.a((a) this);
        hxVar.a(this.k);
        this.l = hpVar.e.a();
        this.l.a((a) this);
        hxVar.a(this.l);
        this.m = hpVar.f.a();
        this.m.a((a) this);
        hxVar.a(this.m);
    }

    public final void a() {
        this.n.invalidateSelf();
    }

    public final void a(List<fe> list, List<fe> list2) {
        for (int i2 = 0; i2 < list2.size(); i2++) {
            fe feVar = list2.get(i2);
            if (feVar instanceof fn) {
                this.h.add((fn) feVar);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r2v4, types: [android.graphics.Shader] */
    /* JADX WARNING: type inference failed for: r2v10, types: [android.graphics.RadialGradient] */
    /* JADX WARNING: type inference failed for: r7v0, types: [android.graphics.RadialGradient] */
    /* JADX WARNING: type inference failed for: r2v21, types: [android.graphics.LinearGradient] */
    /* JADX WARNING: type inference failed for: r7v1, types: [android.graphics.LinearGradient] */
    /* JADX WARNING: type inference failed for: r2v26 */
    /* JADX WARNING: type inference failed for: r7v2, types: [android.graphics.RadialGradient] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v10, types: [android.graphics.RadialGradient]
      assigns: [android.graphics.RadialGradient, android.graphics.LinearGradient, ?[OBJECT, ARRAY]]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], android.graphics.Shader, android.graphics.RadialGradient]
      mth insns count: 106
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.graphics.Canvas r16, android.graphics.Matrix r17, int r18) {
        /*
            r15 = this;
            r0 = r15
            r1 = r17
            java.lang.String r2 = "GradientFillContent#draw"
            defpackage.eu.a(r2)
            android.graphics.Path r2 = r0.e
            r2.reset()
            r2 = 0
            r3 = 0
        L_0x000f:
            java.util.List<fn> r4 = r0.h
            int r4 = r4.size()
            if (r3 >= r4) goto L_0x002b
            android.graphics.Path r4 = r0.e
            java.util.List<fn> r5 = r0.h
            java.lang.Object r5 = r5.get(r3)
            fn r5 = (defpackage.fn) r5
            android.graphics.Path r5 = r5.e()
            r4.addPath(r5, r1)
            int r3 = r3 + 1
            goto L_0x000f
        L_0x002b:
            android.graphics.Path r3 = r0.e
            android.graphics.RectF r4 = r0.g
            r3.computeBounds(r4, r2)
            com.airbnb.lottie.model.content.GradientType r2 = r0.i
            com.airbnb.lottie.model.content.GradientType r3 = com.airbnb.lottie.model.content.GradientType.Linear
            if (r2 != r3) goto L_0x007c
            int r2 = r0.c()
            android.support.v4.util.LongSparseArray<android.graphics.LinearGradient> r3 = r0.b
            long r4 = (long) r2
            java.lang.Object r2 = r3.get(r4)
            android.graphics.LinearGradient r2 = (android.graphics.LinearGradient) r2
            if (r2 == 0) goto L_0x0049
            goto L_0x00c6
        L_0x0049:
            fu<android.graphics.PointF, android.graphics.PointF> r2 = r0.l
            java.lang.Object r2 = r2.a()
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            fu<android.graphics.PointF, android.graphics.PointF> r3 = r0.m
            java.lang.Object r3 = r3.a()
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            fu<ho, ho> r6 = r0.j
            java.lang.Object r6 = r6.a()
            ho r6 = (defpackage.ho) r6
            int[] r12 = r6.b
            float[] r13 = r6.a
            android.graphics.LinearGradient r6 = new android.graphics.LinearGradient
            float r8 = r2.x
            float r9 = r2.y
            float r10 = r3.x
            float r11 = r3.y
            android.graphics.Shader$TileMode r14 = android.graphics.Shader.TileMode.CLAMP
            r7 = r6
            r7.<init>(r8, r9, r10, r11, r12, r13, r14)
            android.support.v4.util.LongSparseArray<android.graphics.LinearGradient> r2 = r0.b
            r2.put(r4, r6)
            r2 = r6
            goto L_0x00c6
        L_0x007c:
            int r2 = r0.c()
            android.support.v4.util.LongSparseArray<android.graphics.RadialGradient> r3 = r0.c
            long r4 = (long) r2
            java.lang.Object r2 = r3.get(r4)
            android.graphics.RadialGradient r2 = (android.graphics.RadialGradient) r2
            if (r2 == 0) goto L_0x008c
            goto L_0x00c6
        L_0x008c:
            fu<android.graphics.PointF, android.graphics.PointF> r2 = r0.l
            java.lang.Object r2 = r2.a()
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            fu<android.graphics.PointF, android.graphics.PointF> r3 = r0.m
            java.lang.Object r3 = r3.a()
            android.graphics.PointF r3 = (android.graphics.PointF) r3
            fu<ho, ho> r6 = r0.j
            java.lang.Object r6 = r6.a()
            ho r6 = (defpackage.ho) r6
            int[] r11 = r6.b
            float[] r12 = r6.a
            float r8 = r2.x
            float r9 = r2.y
            float r2 = r3.x
            float r3 = r3.y
            float r2 = r2 - r8
            double r6 = (double) r2
            float r3 = r3 - r9
            double r2 = (double) r3
            double r2 = java.lang.Math.hypot(r6, r2)
            float r10 = (float) r2
            android.graphics.RadialGradient r2 = new android.graphics.RadialGradient
            android.graphics.Shader$TileMode r13 = android.graphics.Shader.TileMode.CLAMP
            r7 = r2
            r7.<init>(r8, r9, r10, r11, r12, r13)
            android.support.v4.util.LongSparseArray<android.graphics.RadialGradient> r3 = r0.c
            r3.put(r4, r2)
        L_0x00c6:
            android.graphics.Matrix r3 = r0.d
            r3.set(r1)
            android.graphics.Matrix r1 = r0.d
            r2.setLocalMatrix(r1)
            android.graphics.Paint r1 = r0.f
            r1.setShader(r2)
            r1 = r18
            float r1 = (float) r1
            r2 = 1132396544(0x437f0000, float:255.0)
            float r1 = r1 / r2
            fu<java.lang.Integer, java.lang.Integer> r3 = r0.k
            java.lang.Object r3 = r3.a()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            float r3 = (float) r3
            float r1 = r1 * r3
            r3 = 1120403456(0x42c80000, float:100.0)
            float r1 = r1 / r3
            float r1 = r1 * r2
            int r1 = (int) r1
            android.graphics.Paint r2 = r0.f
            r2.setAlpha(r1)
            android.graphics.Path r1 = r0.e
            android.graphics.Paint r2 = r0.f
            r3 = r16
            r3.drawPath(r1, r2)
            java.lang.String r1 = "GradientFillContent#draw"
            defpackage.eu.b(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fj.a(android.graphics.Canvas, android.graphics.Matrix, int):void");
    }

    public final void a(RectF rectF, Matrix matrix) {
        this.e.reset();
        for (int i2 = 0; i2 < this.h.size(); i2++) {
            this.e.addPath(this.h.get(i2).e(), matrix);
        }
        this.e.computeBounds(rectF, false);
        rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
    }

    public final String b() {
        return this.a;
    }

    private int c() {
        int round = Math.round(this.l.c * ((float) this.o));
        int round2 = Math.round(this.m.c * ((float) this.o));
        int round3 = Math.round(this.j.c * ((float) this.o));
        int i2 = round != 0 ? round * 527 : 17;
        if (round2 != 0) {
            i2 = i2 * 31 * round2;
        }
        return round3 != 0 ? i2 * 31 * round3 : i2;
    }
}
