package defpackage;

import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.PolystarShape.Type;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import java.util.List;

/* renamed from: fo reason: default package */
/* compiled from: PolystarContent */
public final class fo implements fn, a {
    private final Path a = new Path();
    private final String b;
    private final ew c;
    private final Type d;
    private final fu<?, Float> e;
    private final fu<?, PointF> f;
    private final fu<?, Float> g;
    @Nullable
    private final fu<?, Float> h;
    private final fu<?, Float> i;
    @Nullable
    private final fu<?, Float> j;
    private final fu<?, Float> k;
    @Nullable
    private ft l;
    private boolean m;

    public fo(ew ewVar, hx hxVar, PolystarShape polystarShape) {
        this.c = ewVar;
        this.b = polystarShape.a;
        this.d = polystarShape.b;
        this.e = polystarShape.c.a();
        this.f = polystarShape.d.a();
        this.g = polystarShape.e.a();
        this.i = polystarShape.g.a();
        this.k = polystarShape.i.a();
        if (this.d == Type.Star) {
            this.h = polystarShape.f.a();
            this.j = polystarShape.h.a();
        } else {
            this.h = null;
            this.j = null;
        }
        hxVar.a(this.e);
        hxVar.a(this.f);
        hxVar.a(this.g);
        hxVar.a(this.i);
        hxVar.a(this.k);
        if (this.d == Type.Star) {
            hxVar.a(this.h);
            hxVar.a(this.j);
        }
        this.e.a((a) this);
        this.f.a((a) this);
        this.g.a((a) this);
        this.i.a((a) this);
        this.k.a((a) this);
        if (this.d == Type.Star) {
            this.i.a((a) this);
            this.k.a((a) this);
        }
    }

    public final void a(List<fe> list, List<fe> list2) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            fe feVar = list.get(i2);
            if (feVar instanceof ft) {
                ft ftVar = (ft) feVar;
                if (ftVar.a == ShapeTrimPath.Type.Simultaneously) {
                    this.l = ftVar;
                    this.l.a(this);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x029a  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x029d  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x02a1  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x02a4  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x02a8  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x02ab  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x02af  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x02b2  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x02c9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.Path e() {
        /*
            r51 = this;
            r0 = r51
            boolean r1 = r0.m
            if (r1 == 0) goto L_0x0009
            android.graphics.Path r1 = r0.a
            return r1
        L_0x0009:
            android.graphics.Path r1 = r0.a
            r1.reset()
            int[] r1 = defpackage.fo.AnonymousClass1.a
            com.airbnb.lottie.model.content.PolystarShape$Type r2 = r0.d
            int r2 = r2.ordinal()
            r1 = r1[r2]
            r2 = 4618760256179416344(0x401921fb54442d18, double:6.283185307179586)
            r4 = 4636033603912859648(0x4056800000000000, double:90.0)
            r6 = 0
            r9 = 1120403456(0x42c80000, float:100.0)
            switch(r1) {
                case 1: goto L_0x012e;
                case 2: goto L_0x002b;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x0319
        L_0x002b:
            fu<?, java.lang.Float> r1 = r0.e
            java.lang.Object r1 = r1.a()
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            double r13 = (double) r1
            double r13 = java.lang.Math.floor(r13)
            int r1 = (int) r13
            fu<?, java.lang.Float> r13 = r0.g
            if (r13 != 0) goto L_0x0042
            goto L_0x004f
        L_0x0042:
            fu<?, java.lang.Float> r6 = r0.g
            java.lang.Object r6 = r6.a()
            java.lang.Float r6 = (java.lang.Float) r6
            float r6 = r6.floatValue()
            double r6 = (double) r6
        L_0x004f:
            r13 = 0
            double r6 = r6 - r4
            double r4 = java.lang.Math.toRadians(r6)
            double r6 = (double) r1
            double r2 = r2 / r6
            float r1 = (float) r2
            fu<?, java.lang.Float> r2 = r0.k
            java.lang.Object r2 = r2.a()
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            float r2 = r2 / r9
            fu<?, java.lang.Float> r3 = r0.i
            java.lang.Object r3 = r3.a()
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            double r13 = (double) r3
            double r15 = java.lang.Math.cos(r4)
            double r8 = r13 * r15
            float r8 = (float) r8
            double r15 = java.lang.Math.sin(r4)
            double r10 = r13 * r15
            float r9 = (float) r10
            android.graphics.Path r10 = r0.a
            r10.moveTo(r8, r9)
            double r10 = (double) r1
            double r4 = r4 + r10
            double r6 = java.lang.Math.ceil(r6)
            r18 = r13
            r1 = 0
        L_0x008e:
            double r12 = (double) r1
            int r12 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r12 >= 0) goto L_0x0116
            double r12 = java.lang.Math.cos(r4)
            double r13 = r18 * r12
            float r12 = (float) r13
            double r13 = java.lang.Math.sin(r4)
            double r13 = r13 * r18
            float r13 = (float) r13
            r14 = 0
            int r15 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r15 == 0) goto L_0x00fe
            double r14 = (double) r9
            r27 = r6
            double r6 = (double) r8
            double r6 = java.lang.Math.atan2(r14, r6)
            r14 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            double r6 = r6 - r14
            float r6 = (float) r6
            double r6 = (double) r6
            double r14 = java.lang.Math.cos(r6)
            float r14 = (float) r14
            double r6 = java.lang.Math.sin(r6)
            float r6 = (float) r6
            r29 = r4
            double r4 = (double) r13
            r31 = r10
            double r10 = (double) r12
            double r4 = java.lang.Math.atan2(r4, r10)
            r10 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            double r4 = r4 - r10
            float r4 = (float) r4
            double r4 = (double) r4
            double r10 = java.lang.Math.cos(r4)
            float r7 = (float) r10
            double r4 = java.lang.Math.sin(r4)
            float r4 = (float) r4
            float r5 = r3 * r2
            r10 = 1048576000(0x3e800000, float:0.25)
            float r5 = r5 * r10
            float r14 = r14 * r5
            float r6 = r6 * r5
            float r7 = r7 * r5
            float r5 = r5 * r4
            android.graphics.Path r4 = r0.a
            float r21 = r8 - r14
            float r22 = r9 - r6
            float r23 = r12 + r7
            float r24 = r13 + r5
            r20 = r4
            r25 = r12
            r26 = r13
            r20.cubicTo(r21, r22, r23, r24, r25, r26)
            goto L_0x0109
        L_0x00fe:
            r29 = r4
            r27 = r6
            r31 = r10
            android.graphics.Path r4 = r0.a
            r4.lineTo(r12, r13)
        L_0x0109:
            r4 = 0
            double r4 = r29 + r31
            int r1 = r1 + 1
            r8 = r12
            r9 = r13
            r6 = r27
            r10 = r31
            goto L_0x008e
        L_0x0116:
            fu<?, android.graphics.PointF> r1 = r0.f
            java.lang.Object r1 = r1.a()
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            android.graphics.Path r2 = r0.a
            float r3 = r1.x
            float r1 = r1.y
            r2.offset(r3, r1)
            android.graphics.Path r1 = r0.a
            r1.close()
            goto L_0x0319
        L_0x012e:
            fu<?, java.lang.Float> r1 = r0.e
            java.lang.Object r1 = r1.a()
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            fu<?, java.lang.Float> r8 = r0.g
            if (r8 != 0) goto L_0x013f
            goto L_0x014c
        L_0x013f:
            fu<?, java.lang.Float> r6 = r0.g
            java.lang.Object r6 = r6.a()
            java.lang.Float r6 = (java.lang.Float) r6
            float r6 = r6.floatValue()
            double r6 = (double) r6
        L_0x014c:
            r8 = 0
            double r6 = r6 - r4
            double r4 = java.lang.Math.toRadians(r6)
            double r6 = (double) r1
            double r2 = r2 / r6
            float r2 = (float) r2
            r3 = 1073741824(0x40000000, float:2.0)
            float r8 = r2 / r3
            int r10 = (int) r1
            float r10 = (float) r10
            float r1 = r1 - r10
            r10 = 0
            int r11 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r11 == 0) goto L_0x0168
            r10 = 1065353216(0x3f800000, float:1.0)
            float r10 = r10 - r1
            float r10 = r10 * r8
            double r12 = (double) r10
            double r4 = r4 + r12
        L_0x0168:
            fu<?, java.lang.Float> r10 = r0.i
            java.lang.Object r10 = r10.a()
            java.lang.Float r10 = (java.lang.Float) r10
            float r10 = r10.floatValue()
            fu<?, java.lang.Float> r12 = r0.h
            java.lang.Object r12 = r12.a()
            java.lang.Float r12 = (java.lang.Float) r12
            float r12 = r12.floatValue()
            fu<?, java.lang.Float> r13 = r0.j
            if (r13 == 0) goto L_0x0192
            fu<?, java.lang.Float> r13 = r0.j
            java.lang.Object r13 = r13.a()
            java.lang.Float r13 = (java.lang.Float) r13
            float r13 = r13.floatValue()
            float r13 = r13 / r9
            goto L_0x0193
        L_0x0192:
            r13 = 0
        L_0x0193:
            fu<?, java.lang.Float> r14 = r0.k
            if (r14 == 0) goto L_0x01a6
            fu<?, java.lang.Float> r14 = r0.k
            java.lang.Object r14 = r14.a()
            java.lang.Float r14 = (java.lang.Float) r14
            float r14 = r14.floatValue()
            float r9 = r14 / r9
            goto L_0x01a7
        L_0x01a6:
            r9 = 0
        L_0x01a7:
            if (r11 == 0) goto L_0x01d2
            float r14 = r10 - r12
            float r14 = r14 * r1
            float r14 = r14 + r12
            r33 = r11
            r34 = r12
            double r11 = (double) r14
            double r15 = java.lang.Math.cos(r4)
            r35 = r14
            double r14 = r11 * r15
            float r14 = (float) r14
            double r15 = java.lang.Math.sin(r4)
            double r11 = r11 * r15
            float r11 = (float) r11
            android.graphics.Path r12 = r0.a
            r12.moveTo(r14, r11)
            float r12 = r2 * r1
            float r12 = r12 / r3
            r36 = r11
            double r11 = (double) r12
            double r4 = r4 + r11
            r37 = r10
            goto L_0x01f4
        L_0x01d2:
            r33 = r11
            r34 = r12
            double r11 = (double) r10
            double r14 = java.lang.Math.cos(r4)
            double r14 = r14 * r11
            float r14 = (float) r14
            double r15 = java.lang.Math.sin(r4)
            double r11 = r11 * r15
            float r11 = (float) r11
            android.graphics.Path r12 = r0.a
            r12.moveTo(r14, r11)
            r37 = r10
            r38 = r11
            double r10 = (double) r8
            double r4 = r4 + r10
            r36 = r38
            r35 = 0
        L_0x01f4:
            double r6 = java.lang.Math.ceil(r6)
            r10 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r6 = r6 * r10
            r39 = r4
            r5 = r36
            r4 = 0
            r17 = 0
        L_0x0203:
            double r10 = (double) r4
            int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r12 >= 0) goto L_0x0303
            if (r17 == 0) goto L_0x020e
            r12 = r37
        L_0x020c:
            r15 = 0
            goto L_0x0211
        L_0x020e:
            r12 = r34
            goto L_0x020c
        L_0x0211:
            int r16 = (r35 > r15 ? 1 : (r35 == r15 ? 0 : -1))
            if (r16 == 0) goto L_0x0221
            r18 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r20 = r6 - r18
            int r15 = (r10 > r20 ? 1 : (r10 == r20 ? 0 : -1))
            if (r15 != 0) goto L_0x0223
            float r15 = r2 * r1
            float r15 = r15 / r3
            goto L_0x0224
        L_0x0221:
            r18 = 4611686018427387904(0x4000000000000000, double:2.0)
        L_0x0223:
            r15 = r8
        L_0x0224:
            r20 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            if (r16 == 0) goto L_0x0233
            double r22 = r6 - r20
            int r16 = (r10 > r22 ? 1 : (r10 == r22 ? 0 : -1))
            if (r16 != 0) goto L_0x0233
            r41 = r4
            r12 = r35
            goto L_0x0235
        L_0x0233:
            r41 = r4
        L_0x0235:
            double r3 = (double) r12
            r42 = r10
            r10 = r39
            double r22 = java.lang.Math.cos(r10)
            r44 = r6
            double r6 = r3 * r22
            float r6 = (float) r6
            double r22 = java.lang.Math.sin(r10)
            double r3 = r3 * r22
            float r3 = (float) r3
            r4 = 0
            int r7 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r7 != 0) goto L_0x0260
            int r7 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r7 != 0) goto L_0x0260
            android.graphics.Path r5 = r0.a
            r5.lineTo(r6, r3)
            r46 = r8
            r48 = r9
            r49 = r13
            goto L_0x02ee
        L_0x0260:
            r46 = r8
            double r7 = (double) r5
            r47 = r5
            double r4 = (double) r14
            double r4 = java.lang.Math.atan2(r7, r4)
            r7 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            double r4 = r4 - r7
            float r4 = (float) r4
            double r4 = (double) r4
            double r7 = java.lang.Math.cos(r4)
            float r7 = (float) r7
            double r4 = java.lang.Math.sin(r4)
            float r4 = (float) r4
            r48 = r9
            double r8 = (double) r3
            r49 = r13
            double r12 = (double) r6
            double r8 = java.lang.Math.atan2(r8, r12)
            r12 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            double r8 = r8 - r12
            float r5 = (float) r8
            double r8 = (double) r5
            double r12 = java.lang.Math.cos(r8)
            float r5 = (float) r12
            double r8 = java.lang.Math.sin(r8)
            float r8 = (float) r8
            if (r17 == 0) goto L_0x029d
            r9 = r49
            goto L_0x029f
        L_0x029d:
            r9 = r48
        L_0x029f:
            if (r17 == 0) goto L_0x02a4
            r12 = r48
            goto L_0x02a6
        L_0x02a4:
            r12 = r49
        L_0x02a6:
            if (r17 == 0) goto L_0x02ab
            r13 = r34
            goto L_0x02ad
        L_0x02ab:
            r13 = r37
        L_0x02ad:
            if (r17 == 0) goto L_0x02b2
            r16 = r37
            goto L_0x02b4
        L_0x02b2:
            r16 = r34
        L_0x02b4:
            float r13 = r13 * r9
            r9 = 1056236141(0x3ef4e26d, float:0.47829)
            float r13 = r13 * r9
            float r7 = r7 * r13
            float r13 = r13 * r4
            float r16 = r16 * r12
            float r16 = r16 * r9
            float r5 = r5 * r16
            float r16 = r16 * r8
            if (r33 == 0) goto L_0x02db
            if (r41 != 0) goto L_0x02d0
            float r7 = r7 * r1
            float r13 = r13 * r1
            goto L_0x02db
        L_0x02d0:
            r4 = 0
            double r8 = r44 - r20
            int r4 = (r42 > r8 ? 1 : (r42 == r8 ? 0 : -1))
            if (r4 != 0) goto L_0x02db
            float r5 = r5 * r1
            float r16 = r16 * r1
        L_0x02db:
            android.graphics.Path r4 = r0.a
            float r23 = r14 - r7
            float r24 = r47 - r13
            float r25 = r6 + r5
            float r26 = r3 + r16
            r22 = r4
            r27 = r6
            r28 = r3
            r22.cubicTo(r23, r24, r25, r26, r27, r28)
        L_0x02ee:
            double r4 = (double) r15
            double r39 = r10 + r4
            r17 = r17 ^ 1
            int r4 = r41 + 1
            r5 = r3
            r14 = r6
            r6 = r44
            r8 = r46
            r9 = r48
            r13 = r49
            r3 = 1073741824(0x40000000, float:2.0)
            goto L_0x0203
        L_0x0303:
            fu<?, android.graphics.PointF> r1 = r0.f
            java.lang.Object r1 = r1.a()
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            android.graphics.Path r2 = r0.a
            float r3 = r1.x
            float r1 = r1.y
            r2.offset(r3, r1)
            android.graphics.Path r1 = r0.a
            r1.close()
        L_0x0319:
            android.graphics.Path r1 = r0.a
            r1.close()
            android.graphics.Path r1 = r0.a
            ft r2 = r0.l
            defpackage.ij.a(r1, r2)
            r1 = 1
            r0.m = r1
            android.graphics.Path r1 = r0.a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fo.e():android.graphics.Path");
    }

    public final String b() {
        return this.b;
    }

    public final void a() {
        this.m = false;
        this.c.invalidateSelf();
    }
}
