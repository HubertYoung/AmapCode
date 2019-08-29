package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;

public class ConstraintWidget {
    public static float X = 0.5f;
    ConstraintAnchor A = new ConstraintAnchor(this, Type.BASELINE);
    ConstraintAnchor B = new ConstraintAnchor(this, Type.CENTER_X);
    ConstraintAnchor C = new ConstraintAnchor(this, Type.CENTER_Y);
    ConstraintAnchor D = new ConstraintAnchor(this, Type.CENTER);
    protected ConstraintAnchor[] E = {this.w, this.y, this.x, this.z, this.A, this.D};
    protected ArrayList<ConstraintAnchor> F = new ArrayList<>();
    protected DimensionBehaviour[] G = {DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
    ConstraintWidget H = null;
    int I = 0;
    int J = 0;
    protected float K = 0.0f;
    protected int L = -1;
    protected int M = 0;
    protected int N = 0;
    int O = 0;
    int P = 0;
    protected int Q = 0;
    protected int R = 0;
    public int S = 0;
    protected int T;
    protected int U;
    public int V;
    public int W;
    public float Y = X;
    public float Z = X;
    private int a = 0;
    public Object aa;
    int ab = 0;
    public int ac = 0;
    public String ad = null;
    boolean ae;
    boolean af;
    boolean ag = false;
    boolean ah = false;
    boolean ai = false;
    public int aj = 0;
    public int ak = 0;
    boolean al;
    boolean am;
    public float[] an = {-1.0f, -1.0f};
    protected ConstraintWidget[] ao = {null, null};
    protected ConstraintWidget[] ap = {null, null};
    ConstraintWidget aq = null;
    ConstraintWidget ar = null;
    private int as = 0;
    private int at = 0;
    private String au = null;
    private int b = 0;
    public int c = -1;
    public int d = -1;
    ResolutionDimension e;
    ResolutionDimension f;
    public int g = 0;
    public int h = 0;
    int[] i = new int[2];
    public int j = 0;
    public int k = 0;
    public float l = 1.0f;
    public int m = 0;
    public int n = 0;
    public float o = 1.0f;
    public boolean p;
    public boolean q;
    int r = -1;
    float s = 1.0f;
    ConstraintWidgetGroup t = null;
    public int[] u = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    public float v = 0.0f;
    ConstraintAnchor w = new ConstraintAnchor(this, Type.LEFT);
    ConstraintAnchor x = new ConstraintAnchor(this, Type.TOP);
    ConstraintAnchor y = new ConstraintAnchor(this, Type.RIGHT);
    ConstraintAnchor z = new ConstraintAnchor(this, Type.BOTTOM);

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public void c() {
    }

    public final boolean e() {
        return this.g == 0 && this.K == 0.0f && this.j == 0 && this.k == 0 && this.G[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public final boolean f() {
        return this.h == 0 && this.K == 0.0f && this.m == 0 && this.n == 0 && this.G[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public void g() {
        this.w.c();
        this.x.c();
        this.y.c();
        this.z.c();
        this.A.c();
        this.B.c();
        this.C.c();
        this.D.c();
        this.H = null;
        this.v = 0.0f;
        this.I = 0;
        this.J = 0;
        this.K = 0.0f;
        this.L = -1;
        this.M = 0;
        this.N = 0;
        this.a = 0;
        this.b = 0;
        this.as = 0;
        this.at = 0;
        this.Q = 0;
        this.R = 0;
        this.S = 0;
        this.T = 0;
        this.U = 0;
        this.V = 0;
        this.W = 0;
        this.Y = X;
        this.Z = X;
        this.G[0] = DimensionBehaviour.FIXED;
        this.G[1] = DimensionBehaviour.FIXED;
        this.aa = null;
        this.ab = 0;
        this.ac = 0;
        this.au = null;
        this.ae = false;
        this.af = false;
        this.aj = 0;
        this.ak = 0;
        this.al = false;
        this.am = false;
        this.an[0] = -1.0f;
        this.an[1] = -1.0f;
        this.c = -1;
        this.d = -1;
        this.u[0] = Integer.MAX_VALUE;
        this.u[1] = Integer.MAX_VALUE;
        this.g = 0;
        this.h = 0;
        this.l = 1.0f;
        this.o = 1.0f;
        this.k = Integer.MAX_VALUE;
        this.n = Integer.MAX_VALUE;
        this.j = 0;
        this.m = 0;
        this.r = -1;
        this.s = 1.0f;
        if (this.e != null) {
            this.e.b();
        }
        if (this.f != null) {
            this.f.b();
        }
        this.t = null;
        this.ag = false;
        this.ah = false;
        this.ai = false;
    }

    public void b() {
        for (int i2 = 0; i2 < 6; i2++) {
            this.E[i2].a.b();
        }
    }

    public final void h() {
        for (int i2 = 0; i2 < 6; i2++) {
            ResolutionAnchor resolutionAnchor = this.E[i2].a;
            ConstraintAnchor constraintAnchor = resolutionAnchor.a.d;
            if (constraintAnchor != null) {
                if (constraintAnchor.d == resolutionAnchor.a) {
                    resolutionAnchor.g = 4;
                    constraintAnchor.a.g = 4;
                }
                int b2 = resolutionAnchor.a.b();
                if (resolutionAnchor.a.c == Type.RIGHT || resolutionAnchor.a.c == Type.BOTTOM) {
                    b2 = -b2;
                }
                resolutionAnchor.b(constraintAnchor.a, b2);
            }
        }
    }

    public void a(int i2) {
        Optimizer.a(i2, this);
    }

    public final boolean i() {
        if (this.w.a.i == 1 && this.y.a.i == 1 && this.x.a.i == 1 && this.z.a.i == 1) {
            return true;
        }
        return false;
    }

    public final ResolutionDimension j() {
        if (this.e == null) {
            this.e = new ResolutionDimension();
        }
        return this.e;
    }

    public final ResolutionDimension k() {
        if (this.f == null) {
            this.f = new ResolutionDimension();
        }
        return this.f;
    }

    public ConstraintWidget() {
        this.F.add(this.w);
        this.F.add(this.x);
        this.F.add(this.y);
        this.F.add(this.z);
        this.F.add(this.B);
        this.F.add(this.C);
        this.F.add(this.D);
        this.F.add(this.A);
    }

    public void a(Cache cache) {
        this.w.a();
        this.x.a();
        this.y.a();
        this.z.a();
        this.A.a();
        this.D.a();
        this.B.a();
        this.C.a();
    }

    public void a(LinearSystem linearSystem, String str) {
        this.ad = str;
        SolverVariable a2 = linearSystem.a((Object) this.w);
        SolverVariable a3 = linearSystem.a((Object) this.x);
        SolverVariable a4 = linearSystem.a((Object) this.y);
        SolverVariable a5 = linearSystem.a((Object) this.z);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".left");
        a2.a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(".top");
        a3.a = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(".right");
        a4.a = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append(".bottom");
        a5.a = sb4.toString();
        if (this.S > 0) {
            SolverVariable a6 = linearSystem.a((Object) this.A);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append(".baseline");
            a6.a = sb5.toString();
        }
    }

    public final void c(LinearSystem linearSystem) {
        linearSystem.a((Object) this.w);
        linearSystem.a((Object) this.x);
        linearSystem.a((Object) this.y);
        linearSystem.a((Object) this.z);
        if (this.S > 0) {
            linearSystem.a((Object) this.A);
        }
    }

    public String toString() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        if (this.au != null) {
            StringBuilder sb2 = new StringBuilder("type: ");
            sb2.append(this.au);
            sb2.append(Token.SEPARATOR);
            str = sb2.toString();
        } else {
            str = "";
        }
        sb.append(str);
        if (this.ad != null) {
            StringBuilder sb3 = new StringBuilder("id: ");
            sb3.append(this.ad);
            sb3.append(Token.SEPARATOR);
            str2 = sb3.toString();
        } else {
            str2 = "";
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.M);
        sb.append(", ");
        sb.append(this.N);
        sb.append(") - (");
        sb.append(this.I);
        sb.append(" x ");
        sb.append(this.J);
        sb.append(") wrap: (");
        sb.append(this.V);
        sb.append(" x ");
        sb.append(this.W);
        sb.append(")");
        return sb.toString();
    }

    public final int l() {
        return this.M;
    }

    public final int m() {
        return this.N;
    }

    public final int n() {
        if (this.ac == 8) {
            return 0;
        }
        return this.I;
    }

    public final int o() {
        if (this.ac == 8) {
            return 0;
        }
        return this.J;
    }

    public final int b(int i2) {
        if (i2 == 0) {
            return n();
        }
        if (i2 == 1) {
            return o();
        }
        return 0;
    }

    public final int p() {
        return this.a + this.Q;
    }

    public final int q() {
        return this.b + this.R;
    }

    /* access modifiers changed from: protected */
    public final int r() {
        return this.M + this.Q;
    }

    /* access modifiers changed from: protected */
    public final int s() {
        return this.N + this.R;
    }

    public final boolean v() {
        return this.S > 0;
    }

    public ArrayList<ConstraintAnchor> w() {
        return this.F;
    }

    public final void c(int i2) {
        this.M = i2;
    }

    public final void d(int i2) {
        this.N = i2;
    }

    public final void a(int i2, int i3) {
        this.M = i2;
        this.N = i3;
    }

    public void b(int i2, int i3) {
        this.Q = i2;
        this.R = i3;
    }

    public void x() {
        int i2 = this.M;
        int i3 = this.N;
        this.a = i2;
        this.b = i3;
        this.as = (this.M + this.I) - i2;
        this.at = (this.N + this.J) - i3;
    }

    public final void e(int i2) {
        this.I = i2;
        if (this.I < this.T) {
            this.I = this.T;
        }
    }

    public final void f(int i2) {
        this.J = i2;
        if (this.J < this.U) {
            this.J = this.U;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0089  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto L_0x008e
            int r1 = r9.length()
            if (r1 != 0) goto L_0x000b
            goto L_0x008e
        L_0x000b:
            r1 = -1
            int r2 = r9.length()
            r3 = 44
            int r3 = r9.indexOf(r3)
            r4 = 0
            r5 = 1
            if (r3 <= 0) goto L_0x0037
            int r6 = r2 + -1
            if (r3 >= r6) goto L_0x0037
            java.lang.String r6 = r9.substring(r4, r3)
            java.lang.String r7 = "W"
            boolean r7 = r6.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x002c
            r1 = 0
            goto L_0x0035
        L_0x002c:
            java.lang.String r4 = "H"
            boolean r4 = r6.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0035
            r1 = 1
        L_0x0035:
            int r4 = r3 + 1
        L_0x0037:
            r3 = 58
            int r3 = r9.indexOf(r3)
            if (r3 < 0) goto L_0x0075
            int r2 = r2 - r5
            if (r3 >= r2) goto L_0x0075
            java.lang.String r2 = r9.substring(r4, r3)
            int r3 = r3 + r5
            java.lang.String r9 = r9.substring(r3)
            int r3 = r2.length()
            if (r3 <= 0) goto L_0x0084
            int r3 = r9.length()
            if (r3 <= 0) goto L_0x0084
            float r2 = java.lang.Float.parseFloat(r2)     // Catch:{ NumberFormatException -> 0x0084 }
            float r9 = java.lang.Float.parseFloat(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            int r3 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r3 <= 0) goto L_0x0084
            int r3 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r3 <= 0) goto L_0x0084
            if (r1 != r5) goto L_0x006f
            float r9 = r9 / r2
            float r9 = java.lang.Math.abs(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x006f:
            float r2 = r2 / r9
            float r9 = java.lang.Math.abs(r2)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x0075:
            java.lang.String r9 = r9.substring(r4)
            int r2 = r9.length()
            if (r2 <= 0) goto L_0x0084
            float r9 = java.lang.Float.parseFloat(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x0084:
            r9 = 0
        L_0x0085:
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x008d
            r8.K = r9
            r8.L = r1
        L_0x008d:
            return
        L_0x008e:
            r8.K = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidget.a(java.lang.String):void");
    }

    public final void g(int i2) {
        if (i2 < 0) {
            this.T = 0;
        } else {
            this.T = i2;
        }
    }

    public final void h(int i2) {
        if (i2 < 0) {
            this.U = 0;
        } else {
            this.U = i2;
        }
    }

    public final void a(int i2, int i3, int i4) {
        if (i4 == 0) {
            c(i2, i3);
        } else if (i4 == 1) {
            d(i2, i3);
        }
        this.ah = true;
    }

    public final void c(int i2, int i3) {
        this.M = i2;
        this.I = i3 - i2;
        if (this.I < this.T) {
            this.I = this.T;
        }
    }

    public final void d(int i2, int i3) {
        this.N = i2;
        this.J = i3 - i2;
        if (this.J < this.U) {
            this.J = this.U;
        }
    }

    /* access modifiers changed from: 0000 */
    public final void e(int i2, int i3) {
        if (i3 == 0) {
            this.O = i2;
            return;
        }
        if (i3 == 1) {
            this.P = i2;
        }
    }

    public boolean a() {
        return this.ac != 8;
    }

    public final void a(Type type, ConstraintWidget constraintWidget, Type type2, int i2, int i3) {
        a(type).a(constraintWidget.a(type2), i2, i3, Strength.STRONG, 0, true);
    }

    public final void a(Type type, ConstraintWidget constraintWidget, Type type2) {
        a(type, constraintWidget, type2, Strength.STRONG);
    }

    public final void a(Type type, ConstraintWidget constraintWidget, Type type2, Strength strength) {
        a(type, constraintWidget, type2, strength, 0);
    }

    private void a(Type type, ConstraintWidget constraintWidget, Type type2, Strength strength, int i2) {
        boolean z2;
        Type type3 = type;
        ConstraintWidget constraintWidget2 = constraintWidget;
        Type type4 = type2;
        int i3 = i2;
        boolean z3 = false;
        if (type3 == Type.CENTER) {
            if (type4 == Type.CENTER) {
                ConstraintAnchor a2 = a(Type.LEFT);
                ConstraintAnchor a3 = a(Type.RIGHT);
                ConstraintAnchor a4 = a(Type.TOP);
                ConstraintAnchor a5 = a(Type.BOTTOM);
                if ((a2 == null || !a2.d()) && (a3 == null || !a3.d())) {
                    ConstraintWidget constraintWidget3 = constraintWidget2;
                    Strength strength2 = strength;
                    int i4 = i3;
                    a(Type.LEFT, constraintWidget3, Type.LEFT, strength2, i4);
                    a(Type.RIGHT, constraintWidget3, Type.RIGHT, strength2, i4);
                    z2 = true;
                } else {
                    z2 = false;
                }
                if ((a4 == null || !a4.d()) && (a5 == null || !a5.d())) {
                    ConstraintWidget constraintWidget4 = constraintWidget2;
                    Strength strength3 = strength;
                    int i5 = i3;
                    a(Type.TOP, constraintWidget4, Type.TOP, strength3, i5);
                    a(Type.BOTTOM, constraintWidget4, Type.BOTTOM, strength3, i5);
                    z3 = true;
                }
                if (z2 && z3) {
                    a(Type.CENTER).a(constraintWidget2.a(Type.CENTER), i3);
                } else if (z2) {
                    a(Type.CENTER_X).a(constraintWidget2.a(Type.CENTER_X), i3);
                } else {
                    if (z3) {
                        a(Type.CENTER_Y).a(constraintWidget2.a(Type.CENTER_Y), i3);
                    }
                }
            } else if (type4 == Type.LEFT || type4 == Type.RIGHT) {
                ConstraintWidget constraintWidget5 = constraintWidget2;
                Type type5 = type4;
                Strength strength4 = strength;
                int i6 = i3;
                a(Type.LEFT, constraintWidget5, type5, strength4, i6);
                a(Type.RIGHT, constraintWidget5, type5, strength4, i6);
                a(Type.CENTER).a(constraintWidget.a(type2), i3);
            } else if (type4 == Type.TOP || type4 == Type.BOTTOM) {
                ConstraintWidget constraintWidget6 = constraintWidget2;
                Type type6 = type4;
                Strength strength5 = strength;
                int i7 = i3;
                a(Type.TOP, constraintWidget6, type6, strength5, i7);
                a(Type.BOTTOM, constraintWidget6, type6, strength5, i7);
                a(Type.CENTER).a(constraintWidget.a(type2), i3);
            }
        } else if (type3 == Type.CENTER_X && (type4 == Type.LEFT || type4 == Type.RIGHT)) {
            ConstraintAnchor a6 = a(Type.LEFT);
            ConstraintAnchor a7 = constraintWidget.a(type2);
            ConstraintAnchor a8 = a(Type.RIGHT);
            a6.a(a7, i3);
            a8.a(a7, i3);
            a(Type.CENTER_X).a(a7, i3);
        } else if (type3 == Type.CENTER_Y && (type4 == Type.TOP || type4 == Type.BOTTOM)) {
            ConstraintAnchor a9 = constraintWidget.a(type2);
            a(Type.TOP).a(a9, i3);
            a(Type.BOTTOM).a(a9, i3);
            a(Type.CENTER_Y).a(a9, i3);
        } else if (type3 == Type.CENTER_X && type4 == Type.CENTER_X) {
            a(Type.LEFT).a(constraintWidget2.a(Type.LEFT), i3);
            a(Type.RIGHT).a(constraintWidget2.a(Type.RIGHT), i3);
            a(Type.CENTER_X).a(constraintWidget.a(type2), i3);
        } else if (type3 == Type.CENTER_Y && type4 == Type.CENTER_Y) {
            a(Type.TOP).a(constraintWidget2.a(Type.TOP), i3);
            a(Type.BOTTOM).a(constraintWidget2.a(Type.BOTTOM), i3);
            a(Type.CENTER_Y).a(constraintWidget.a(type2), i3);
        } else {
            ConstraintAnchor a10 = a(type3);
            ConstraintAnchor a11 = constraintWidget.a(type2);
            if (a10.a(a11)) {
                if (type3 == Type.BASELINE) {
                    ConstraintAnchor a12 = a(Type.TOP);
                    ConstraintAnchor a13 = a(Type.BOTTOM);
                    if (a12 != null) {
                        a12.c();
                    }
                    if (a13 != null) {
                        a13.c();
                    }
                } else if (type3 == Type.TOP || type3 == Type.BOTTOM) {
                    ConstraintAnchor a14 = a(Type.BASELINE);
                    if (a14 != null) {
                        a14.c();
                    }
                    ConstraintAnchor a15 = a(Type.CENTER);
                    if (a15.d != a11) {
                        a15.c();
                    }
                    ConstraintAnchor e2 = a(type3).e();
                    ConstraintAnchor a16 = a(Type.CENTER_Y);
                    if (a16.d()) {
                        e2.c();
                        a16.c();
                    }
                } else if (type3 == Type.LEFT || type3 == Type.RIGHT) {
                    ConstraintAnchor a17 = a(Type.CENTER);
                    if (a17.d != a11) {
                        a17.c();
                    }
                    ConstraintAnchor e3 = a(type3).e();
                    ConstraintAnchor a18 = a(Type.CENTER_X);
                    if (a18.d()) {
                        e3.c();
                        a18.c();
                    }
                }
                a10.a(a11, 0, strength, i3);
            }
        }
    }

    public ConstraintAnchor a(Type type) {
        switch (type) {
            case LEFT:
                return this.w;
            case TOP:
                return this.x;
            case RIGHT:
                return this.y;
            case BOTTOM:
                return this.z;
            case BASELINE:
                return this.A;
            case CENTER:
                return this.D;
            case CENTER_X:
                return this.B;
            case CENTER_Y:
                return this.C;
            case NONE:
                return null;
            default:
                throw new AssertionError(type.name());
        }
    }

    public final DimensionBehaviour z() {
        return this.G[0];
    }

    public final DimensionBehaviour A() {
        return this.G[1];
    }

    public final void a(DimensionBehaviour dimensionBehaviour) {
        this.G[0] = dimensionBehaviour;
        if (dimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            e(this.V);
        }
    }

    public final void b(DimensionBehaviour dimensionBehaviour) {
        this.G[1] = dimensionBehaviour;
        if (dimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            f(this.W);
        }
    }

    private boolean j(int i2) {
        int i3 = i2 * 2;
        if (!(this.E[i3].d == null || this.E[i3].d.d == this.E[i3])) {
            int i4 = i3 + 1;
            if (this.E[i4].d != null && this.E[i4].d.d == this.E[i4]) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:187:0x02dd, code lost:
        if (r15.r == -1) goto L_0x02e1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x02d6  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x02e4  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x02f0  */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x0307  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x037d  */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x038a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:213:0x038b  */
    /* JADX WARNING: Removed duplicated region for block: B:238:0x03ee  */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x03f8  */
    /* JADX WARNING: Removed duplicated region for block: B:242:0x03fe  */
    /* JADX WARNING: Removed duplicated region for block: B:243:0x0408  */
    /* JADX WARNING: Removed duplicated region for block: B:246:0x0443  */
    /* JADX WARNING: Removed duplicated region for block: B:250:0x046a  */
    /* JADX WARNING: Removed duplicated region for block: B:253:0x0474  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(android.support.constraint.solver.LinearSystem r42) {
        /*
            r41 = this;
            r15 = r41
            r14 = r42
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.w
            android.support.constraint.solver.SolverVariable r21 = r14.a(r0)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.y
            android.support.constraint.solver.SolverVariable r13 = r14.a(r0)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.x
            android.support.constraint.solver.SolverVariable r12 = r14.a(r0)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.z
            android.support.constraint.solver.SolverVariable r11 = r14.a(r0)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.A
            android.support.constraint.solver.SolverVariable r10 = r14.a(r0)
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            r1 = 8
            r9 = 1
            r8 = 0
            if (r0 == 0) goto L_0x00f4
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            if (r0 == 0) goto L_0x003a
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r0.G
            r0 = r0[r8]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r2) goto L_0x003a
            r0 = 1
            goto L_0x003b
        L_0x003a:
            r0 = 0
        L_0x003b:
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r15.H
            if (r2 == 0) goto L_0x004b
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r15.H
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r2.G
            r2 = r2[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r2 != r3) goto L_0x004b
            r2 = 1
            goto L_0x004c
        L_0x004b:
            r2 = 0
        L_0x004c:
            boolean r3 = r15.j(r8)
            if (r3 == 0) goto L_0x005b
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r15.H
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r3 = (android.support.constraint.solver.widgets.ConstraintWidgetContainer) r3
            r3.a(r15, r8)
        L_0x0059:
            r3 = 1
            goto L_0x007d
        L_0x005b:
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.w
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            if (r3 == 0) goto L_0x006b
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.w
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.w
            if (r3 == r4) goto L_0x0059
        L_0x006b:
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.y
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            if (r3 == 0) goto L_0x007c
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r15.y
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.y
            if (r3 != r4) goto L_0x007c
            goto L_0x0059
        L_0x007c:
            r3 = 0
        L_0x007d:
            boolean r4 = r15.j(r9)
            if (r4 == 0) goto L_0x008c
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r15.H
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r4 = (android.support.constraint.solver.widgets.ConstraintWidgetContainer) r4
            r4.a(r15, r9)
        L_0x008a:
            r4 = 1
            goto L_0x00ae
        L_0x008c:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.d
            if (r4 == 0) goto L_0x009c
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.d
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.d
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.x
            if (r4 == r5) goto L_0x008a
        L_0x009c:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.z
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.d
            if (r4 == 0) goto L_0x00ad
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r15.z
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.d
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.d
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.z
            if (r4 != r5) goto L_0x00ad
            goto L_0x008a
        L_0x00ad:
            r4 = 0
        L_0x00ae:
            if (r0 == 0) goto L_0x00cb
            int r5 = r15.ac
            if (r5 == r1) goto L_0x00cb
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.w
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.d
            if (r5 != 0) goto L_0x00cb
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.y
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.d
            if (r5 != 0) goto L_0x00cb
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.H
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.y
            android.support.constraint.solver.SolverVariable r5 = r14.a(r5)
            r14.a(r5, r13, r8, r9)
        L_0x00cb:
            if (r2 == 0) goto L_0x00ec
            int r5 = r15.ac
            if (r5 == r1) goto L_0x00ec
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.d
            if (r5 != 0) goto L_0x00ec
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.z
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.d
            if (r5 != 0) goto L_0x00ec
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r15.A
            if (r5 != 0) goto L_0x00ec
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r15.H
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r5.z
            android.support.constraint.solver.SolverVariable r5 = r14.a(r5)
            r14.a(r5, r11, r8, r9)
        L_0x00ec:
            r22 = r2
            r16 = r3
            r23 = r4
            r2 = r0
            goto L_0x00fb
        L_0x00f4:
            r2 = 0
            r16 = 0
            r22 = 0
            r23 = 0
        L_0x00fb:
            int r0 = r15.I
            int r3 = r15.T
            if (r0 >= r3) goto L_0x0103
            int r0 = r15.T
        L_0x0103:
            int r3 = r15.J
            int r4 = r15.U
            if (r3 >= r4) goto L_0x010b
            int r3 = r15.U
        L_0x010b:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r15.G
            r4 = r4[r8]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 == r5) goto L_0x0115
            r4 = 1
            goto L_0x0116
        L_0x0115:
            r4 = 0
        L_0x0116:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r15.G
            r5 = r5[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 == r6) goto L_0x0120
            r5 = 1
            goto L_0x0121
        L_0x0120:
            r5 = 0
        L_0x0121:
            int r6 = r15.L
            r15.r = r6
            float r6 = r15.K
            r15.s = r6
            int r6 = r15.g
            int r7 = r15.h
            float r9 = r15.K
            r17 = 0
            int r9 = (r9 > r17 ? 1 : (r9 == r17 ? 0 : -1))
            r17 = 4
            if (r9 <= 0) goto L_0x02c0
            int r9 = r15.ac
            if (r9 == r1) goto L_0x02c0
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.G
            r9 = 0
            r1 = r1[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r8 = 3
            if (r1 != r9) goto L_0x0148
            if (r6 != 0) goto L_0x0148
            r6 = 3
        L_0x0148:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.G
            r9 = 1
            r1 = r1[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r9) goto L_0x0154
            if (r7 != 0) goto L_0x0154
            r7 = 3
        L_0x0154:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.G
            r9 = 0
            r1 = r1[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r18 = 1065353216(0x3f800000, float:1.0)
            if (r1 != r9) goto L_0x025b
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.G
            r9 = 1
            r1 = r1[r9]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r9) goto L_0x025b
            if (r6 != r8) goto L_0x025b
            if (r7 != r8) goto L_0x025b
            int r1 = r15.r
            r8 = -1
            if (r1 != r8) goto L_0x018a
            if (r4 == 0) goto L_0x0179
            if (r5 != 0) goto L_0x0179
            r1 = 0
            r15.r = r1
            goto L_0x018a
        L_0x0179:
            if (r4 != 0) goto L_0x018a
            if (r5 == 0) goto L_0x018a
            r1 = 1
            r15.r = r1
            int r1 = r15.L
            if (r1 != r8) goto L_0x018a
            float r1 = r15.s
            float r1 = r18 / r1
            r15.s = r1
        L_0x018a:
            int r1 = r15.r
            if (r1 != 0) goto L_0x01a2
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.x
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x019e
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.z
            boolean r1 = r1.d()
            if (r1 != 0) goto L_0x01a2
        L_0x019e:
            r1 = 1
            r15.r = r1
            goto L_0x01ba
        L_0x01a2:
            r1 = 1
            int r4 = r15.r
            if (r4 != r1) goto L_0x01ba
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.w
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x01b7
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.y
            boolean r1 = r1.d()
            if (r1 != 0) goto L_0x01ba
        L_0x01b7:
            r1 = 0
            r15.r = r1
        L_0x01ba:
            int r1 = r15.r
            r4 = -1
            if (r1 != r4) goto L_0x020c
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.x
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x01df
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.z
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x01df
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.w
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x01df
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.y
            boolean r1 = r1.d()
            if (r1 != 0) goto L_0x020c
        L_0x01df:
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.x
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x01f3
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.z
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x01f3
            r1 = 0
            r15.r = r1
            goto L_0x020c
        L_0x01f3:
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.w
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x020c
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.y
            boolean r1 = r1.d()
            if (r1 == 0) goto L_0x020c
            float r1 = r15.s
            float r1 = r18 / r1
            r15.s = r1
            r1 = 1
            r15.r = r1
        L_0x020c:
            int r1 = r15.r
            r4 = -1
            if (r1 != r4) goto L_0x0226
            if (r2 == 0) goto L_0x0219
            if (r22 != 0) goto L_0x0219
            r1 = 0
            r15.r = r1
            goto L_0x0226
        L_0x0219:
            if (r2 != 0) goto L_0x0226
            if (r22 == 0) goto L_0x0226
            float r1 = r15.s
            float r1 = r18 / r1
            r15.s = r1
            r1 = 1
            r15.r = r1
        L_0x0226:
            int r1 = r15.r
            r4 = -1
            if (r1 != r4) goto L_0x0248
            int r1 = r15.j
            if (r1 <= 0) goto L_0x0237
            int r1 = r15.m
            if (r1 != 0) goto L_0x0237
            r1 = 0
            r15.r = r1
            goto L_0x0248
        L_0x0237:
            int r1 = r15.j
            if (r1 != 0) goto L_0x0248
            int r1 = r15.m
            if (r1 <= 0) goto L_0x0248
            float r1 = r15.s
            float r1 = r18 / r1
            r15.s = r1
            r1 = 1
            r15.r = r1
        L_0x0248:
            int r1 = r15.r
            r4 = -1
            if (r1 != r4) goto L_0x02b5
            if (r2 == 0) goto L_0x02b5
            if (r22 == 0) goto L_0x02b5
            float r1 = r15.s
            float r1 = r18 / r1
            r15.s = r1
            r1 = 1
            r15.r = r1
            goto L_0x02b5
        L_0x025b:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.G
            r4 = 0
            r1 = r1[r4]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r5) goto L_0x027e
            if (r6 != r8) goto L_0x027e
            r15.r = r4
            float r0 = r15.s
            int r1 = r15.J
            float r1 = (float) r1
            float r0 = r0 * r1
            int r0 = (int) r0
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.G
            r4 = 1
            r1 = r1[r4]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 == r5) goto L_0x02b5
            r18 = r0
            r29 = r3
            goto L_0x02c6
        L_0x027e:
            r4 = 1
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.G
            r1 = r1[r4]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r5) goto L_0x02b5
            if (r7 != r8) goto L_0x02b5
            r15.r = r4
            int r1 = r15.L
            r3 = -1
            if (r1 != r3) goto L_0x0296
            float r1 = r15.s
            float r1 = r18 / r1
            r15.s = r1
        L_0x0296:
            float r1 = r15.s
            int r3 = r15.I
            float r3 = (float) r3
            float r1 = r1 * r3
            int r1 = (int) r1
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r15.G
            r4 = 0
            r3 = r3[r4]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 == r4) goto L_0x02b0
            r18 = r0
            r29 = r1
            r17 = r6
            r27 = 4
            goto L_0x02c8
        L_0x02b0:
            r18 = r0
            r29 = r1
            goto L_0x02b9
        L_0x02b5:
            r18 = r0
            r29 = r3
        L_0x02b9:
            r17 = r6
            r27 = r7
            r28 = 1
            goto L_0x02ca
        L_0x02c0:
            r18 = r0
            r29 = r3
            r17 = r6
        L_0x02c6:
            r27 = r7
        L_0x02c8:
            r28 = 0
        L_0x02ca:
            int[] r0 = r15.i
            r1 = 0
            r0[r1] = r17
            int[] r0 = r15.i
            r1 = 1
            r0[r1] = r27
            if (r28 == 0) goto L_0x02e4
            int r0 = r15.r
            if (r0 == 0) goto L_0x02e0
            int r0 = r15.r
            r8 = -1
            if (r0 != r8) goto L_0x02e5
            goto L_0x02e1
        L_0x02e0:
            r8 = -1
        L_0x02e1:
            r19 = 1
            goto L_0x02e7
        L_0x02e4:
            r8 = -1
        L_0x02e5:
            r19 = 0
        L_0x02e7:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.G
            r1 = 0
            r0 = r0[r1]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r1) goto L_0x02f6
            boolean r0 = r15 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x02f6
            r6 = 1
            goto L_0x02f7
        L_0x02f6:
            r6 = 0
        L_0x02f7:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.D
            boolean r0 = r0.d()
            r9 = 1
            r24 = r0 ^ 1
            int r0 = r15.c
            r7 = 2
            r26 = 0
            if (r0 == r7) goto L_0x037d
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            if (r0 == 0) goto L_0x0315
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.y
            android.support.constraint.solver.SolverVariable r0 = r14.a(r0)
            r4 = r0
            goto L_0x0317
        L_0x0315:
            r4 = r26
        L_0x0317:
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            if (r0 == 0) goto L_0x0325
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.w
            android.support.constraint.solver.SolverVariable r0 = r14.a(r0)
            r3 = r0
            goto L_0x0327
        L_0x0325:
            r3 = r26
        L_0x0327:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.G
            r20 = 0
            r5 = r0[r20]
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r15.w
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.y
            int r9 = r15.M
            r31 = r11
            int r11 = r15.T
            int[] r7 = r15.u
            r25 = r7[r20]
            float r7 = r15.Y
            r33 = r13
            int r13 = r15.j
            r34 = r13
            int r13 = r15.k
            r35 = r13
            float r13 = r15.l
            r36 = r0
            r0 = r15
            r37 = r1
            r1 = r14
            r32 = r7
            r7 = r37
            r8 = r36
            r38 = r10
            r10 = r18
            r30 = r31
            r39 = r12
            r12 = r25
            r31 = r13
            r25 = r33
            r18 = r34
            r20 = r35
            r13 = r32
            r14 = r19
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r20
            r19 = r31
            r20 = r24
            r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r15 = r41
            goto L_0x0385
        L_0x037d:
            r38 = r10
            r30 = r11
            r39 = r12
            r25 = r13
        L_0x0385:
            int r0 = r15.d
            r1 = 2
            if (r0 != r1) goto L_0x038b
            return
        L_0x038b:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.G
            r14 = 1
            r0 = r0[r14]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r1) goto L_0x039a
            boolean r0 = r15 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x039a
            r6 = 1
            goto L_0x039b
        L_0x039a:
            r6 = 0
        L_0x039b:
            if (r28 == 0) goto L_0x03a9
            int r0 = r15.r
            if (r0 == r14) goto L_0x03a6
            int r0 = r15.r
            r1 = -1
            if (r0 != r1) goto L_0x03a9
        L_0x03a6:
            r16 = 1
            goto L_0x03ab
        L_0x03a9:
            r16 = 0
        L_0x03ab:
            int r0 = r15.S
            if (r0 <= 0) goto L_0x03e4
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.A
            android.support.constraint.solver.widgets.ResolutionAnchor r0 = r0.a
            int r0 = r0.i
            if (r0 != r14) goto L_0x03c3
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.A
            android.support.constraint.solver.widgets.ResolutionAnchor r0 = r0.a
            r13 = r42
            r0.a(r13)
            r12 = r39
            goto L_0x03e8
        L_0x03c3:
            r13 = r42
            int r0 = r15.S
            r1 = 6
            r2 = r38
            r12 = r39
            r13.c(r2, r12, r0, r1)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.A
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            if (r0 == 0) goto L_0x03e8
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r15.A
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            android.support.constraint.solver.SolverVariable r0 = r13.a(r0)
            r3 = 0
            r13.c(r2, r0, r3, r1)
            r20 = 0
            goto L_0x03ea
        L_0x03e4:
            r12 = r39
            r13 = r42
        L_0x03e8:
            r20 = r24
        L_0x03ea:
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            if (r0 == 0) goto L_0x03f8
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.z
            android.support.constraint.solver.SolverVariable r0 = r13.a(r0)
            r4 = r0
            goto L_0x03fa
        L_0x03f8:
            r4 = r26
        L_0x03fa:
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            if (r0 == 0) goto L_0x0408
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r15.H
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.x
            android.support.constraint.solver.SolverVariable r0 = r13.a(r0)
            r3 = r0
            goto L_0x040a
        L_0x0408:
            r3 = r26
        L_0x040a:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.G
            r5 = r0[r14]
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r15.x
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r15.z
            int r9 = r15.N
            int r11 = r15.U
            int[] r0 = r15.u
            r17 = r0[r14]
            float r10 = r15.Z
            int r2 = r15.m
            int r1 = r15.n
            float r0 = r15.o
            r19 = r0
            r0 = r15
            r18 = r1
            r1 = r13
            r24 = r2
            r2 = r22
            r22 = r10
            r10 = r29
            r26 = r12
            r12 = r17
            r13 = r22
            r14 = r16
            r15 = r23
            r16 = r27
            r17 = r24
            r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            if (r28 == 0) goto L_0x046a
            r6 = r41
            int r0 = r6.r
            r1 = 1
            if (r0 != r1) goto L_0x045a
            float r5 = r6.s
            r0 = r42
            r1 = r30
            r2 = r26
            r3 = r25
            r4 = r21
            r0.a(r1, r2, r3, r4, r5)
            goto L_0x046c
        L_0x045a:
            float r5 = r6.s
            r0 = r42
            r1 = r25
            r2 = r21
            r3 = r30
            r4 = r26
            r0.a(r1, r2, r3, r4, r5)
            goto L_0x046c
        L_0x046a:
            r6 = r41
        L_0x046c:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r6.D
            boolean r0 = r0.d()
            if (r0 == 0) goto L_0x0509
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r6.D
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.b
            float r1 = r6.v
            r2 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 + r2
            double r1 = (double) r1
            double r1 = java.lang.Math.toRadians(r1)
            float r1 = (float) r1
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r6.D
            int r2 = r2.b()
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r6.a(r3)
            r4 = r42
            android.support.constraint.solver.SolverVariable r8 = r4.a(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r6.a(r3)
            android.support.constraint.solver.SolverVariable r10 = r4.a(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r6.a(r3)
            android.support.constraint.solver.SolverVariable r3 = r4.a(r3)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r6.a(r5)
            android.support.constraint.solver.SolverVariable r11 = r4.a(r5)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r0.a(r5)
            android.support.constraint.solver.SolverVariable r5 = r4.a(r5)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r7 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r0.a(r7)
            android.support.constraint.solver.SolverVariable r12 = r4.a(r7)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r7 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r0.a(r7)
            android.support.constraint.solver.SolverVariable r15 = r4.a(r7)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r7 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.a(r7)
            android.support.constraint.solver.SolverVariable r13 = r4.a(r0)
            android.support.constraint.solver.ArrayRow r0 = r42.c()
            double r6 = (double) r1
            double r16 = java.lang.Math.sin(r6)
            double r1 = (double) r2
            r40 = r15
            double r14 = r16 * r1
            float r14 = (float) r14
            r9 = r0
            r9.b(r10, r11, r12, r13, r14)
            r4.a(r0)
            android.support.constraint.solver.ArrayRow r0 = r42.c()
            double r6 = java.lang.Math.cos(r6)
            double r6 = r6 * r1
            float r12 = (float) r6
            r7 = r0
            r9 = r3
            r10 = r5
            r11 = r40
            r7.b(r8, r9, r10, r11, r12)
            r4.a(r0)
        L_0x0509:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidget.a(android.support.constraint.solver.LinearSystem):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x01d1 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x028f  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x02d4  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x02e3  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x02e6  */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x02f7 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x030c  */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x0315  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01a8  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x01cb  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.support.constraint.solver.LinearSystem r35, boolean r36, android.support.constraint.solver.SolverVariable r37, android.support.constraint.solver.SolverVariable r38, android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour r39, boolean r40, android.support.constraint.solver.widgets.ConstraintAnchor r41, android.support.constraint.solver.widgets.ConstraintAnchor r42, int r43, int r44, int r45, int r46, float r47, boolean r48, boolean r49, int r50, int r51, int r52, float r53, boolean r54) {
        /*
            r34 = this;
            r0 = r34
            r10 = r35
            r12 = r37
            r13 = r38
            r14 = r41
            r15 = r42
            r1 = r45
            r2 = r46
            android.support.constraint.solver.SolverVariable r9 = r10.a(r14)
            android.support.constraint.solver.SolverVariable r8 = r10.a(r15)
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r14.d
            android.support.constraint.solver.SolverVariable r7 = r10.a(r6)
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r15.d
            android.support.constraint.solver.SolverVariable r6 = r10.a(r6)
            boolean r12 = r10.d
            r16 = 1
            r22 = r6
            if (r12 == 0) goto L_0x005d
            android.support.constraint.solver.widgets.ResolutionAnchor r12 = r14.a
            int r12 = r12.i
            r6 = 1
            if (r12 != r6) goto L_0x005d
            android.support.constraint.solver.widgets.ResolutionAnchor r12 = r15.a
            int r12 = r12.i
            if (r12 != r6) goto L_0x005d
            android.support.constraint.solver.Metrics r1 = android.support.constraint.solver.LinearSystem.a()
            if (r1 == 0) goto L_0x0049
            android.support.constraint.solver.Metrics r1 = android.support.constraint.solver.LinearSystem.a()
            long r2 = r1.s
            long r2 = r2 + r16
            r1.s = r2
        L_0x0049:
            android.support.constraint.solver.widgets.ResolutionAnchor r1 = r14.a
            r1.a(r10)
            android.support.constraint.solver.widgets.ResolutionAnchor r1 = r15.a
            r1.a(r10)
            if (r49 != 0) goto L_0x005c
            if (r36 == 0) goto L_0x005c
            r1 = 0
            r2 = 6
            r10.a(r13, r8, r1, r2)
        L_0x005c:
            return
        L_0x005d:
            android.support.constraint.solver.Metrics r6 = android.support.constraint.solver.LinearSystem.a()
            if (r6 == 0) goto L_0x006d
            android.support.constraint.solver.Metrics r6 = android.support.constraint.solver.LinearSystem.a()
            long r12 = r6.B
            long r12 = r12 + r16
            r6.B = r12
        L_0x006d:
            boolean r6 = r41.d()
            boolean r12 = r42.d()
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r0.D
            boolean r13 = r13.d()
            if (r6 == 0) goto L_0x0080
            r16 = 1
            goto L_0x0082
        L_0x0080:
            r16 = 0
        L_0x0082:
            if (r12 == 0) goto L_0x0086
            int r16 = r16 + 1
        L_0x0086:
            if (r13 == 0) goto L_0x008a
            int r16 = r16 + 1
        L_0x008a:
            r23 = r16
            if (r48 == 0) goto L_0x0090
            r15 = 3
            goto L_0x0092
        L_0x0090:
            r15 = r50
        L_0x0092:
            int[] r16 = android.support.constraint.solver.widgets.ConstraintWidget.AnonymousClass1.b
            int r17 = r39.ordinal()
            r16 = r16[r17]
            r3 = 4
            switch(r16) {
                case 1: goto L_0x009e;
                case 2: goto L_0x009e;
                case 3: goto L_0x009e;
                case 4: goto L_0x00a1;
                default: goto L_0x009e;
            }
        L_0x009e:
            r16 = 0
            goto L_0x00a6
        L_0x00a1:
            if (r15 != r3) goto L_0x00a4
            goto L_0x009e
        L_0x00a4:
            r16 = 1
        L_0x00a6:
            int r3 = r0.ac
            r0 = 8
            if (r3 != r0) goto L_0x00b0
            r0 = 0
            r16 = 0
            goto L_0x00b2
        L_0x00b0:
            r0 = r44
        L_0x00b2:
            if (r54 == 0) goto L_0x00cf
            if (r6 != 0) goto L_0x00c0
            if (r12 != 0) goto L_0x00c0
            if (r13 != 0) goto L_0x00c0
            r3 = r43
            r10.a(r9, r3)
            goto L_0x00cf
        L_0x00c0:
            if (r6 == 0) goto L_0x00cf
            if (r12 != 0) goto L_0x00cf
            int r3 = r41.b()
            r24 = r13
            r13 = 6
            r10.c(r9, r7, r3, r13)
            goto L_0x00d2
        L_0x00cf:
            r24 = r13
            r13 = 6
        L_0x00d2:
            if (r16 != 0) goto L_0x00fe
            if (r40 == 0) goto L_0x00ec
            r3 = 0
            r13 = 3
            r10.c(r8, r9, r3, r13)
            if (r1 <= 0) goto L_0x00e2
            r3 = 6
            r10.a(r8, r9, r1, r3)
            goto L_0x00e3
        L_0x00e2:
            r3 = 6
        L_0x00e3:
            r0 = 2147483647(0x7fffffff, float:NaN)
            if (r2 >= r0) goto L_0x00f0
            r10.b(r8, r9, r2, r3)
            goto L_0x00f0
        L_0x00ec:
            r3 = 6
            r10.c(r8, r9, r0, r3)
        L_0x00f0:
            r3 = r51
            r13 = r52
            r25 = r7
            r28 = r23
            r7 = r34
        L_0x00fa:
            r17 = 4
            goto L_0x01cf
        L_0x00fe:
            r3 = 6
            r2 = -2
            r13 = r51
            if (r13 != r2) goto L_0x0108
            r13 = r52
            r3 = r0
            goto L_0x010b
        L_0x0108:
            r3 = r13
            r13 = r52
        L_0x010b:
            if (r13 != r2) goto L_0x010e
            r13 = r0
        L_0x010e:
            if (r3 <= 0) goto L_0x0119
            r2 = 6
            r10.a(r8, r9, r3, r2)
            int r0 = java.lang.Math.max(r0, r3)
            goto L_0x011a
        L_0x0119:
            r2 = 6
        L_0x011a:
            if (r13 <= 0) goto L_0x0123
            r10.b(r8, r9, r13, r2)
            int r0 = java.lang.Math.min(r0, r13)
        L_0x0123:
            r2 = 1
            if (r15 != r2) goto L_0x013b
            if (r36 == 0) goto L_0x012e
            r2 = 6
            r10.c(r8, r9, r0, r2)
            goto L_0x01a2
        L_0x012e:
            if (r49 == 0) goto L_0x0136
            r2 = 4
            r10.c(r8, r9, r0, r2)
            goto L_0x01a2
        L_0x0136:
            r2 = 1
            r10.c(r8, r9, r0, r2)
            goto L_0x01a2
        L_0x013b:
            r2 = 2
            if (r15 != r2) goto L_0x01a2
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = r14.c
            r25 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r7 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            if (r2 == r7) goto L_0x016e
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = r14.c
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r7 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            if (r2 != r7) goto L_0x014d
            goto L_0x016e
        L_0x014d:
            r7 = r34
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r7.H
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r2.a(r14)
            android.support.constraint.solver.SolverVariable r2 = r10.a(r2)
            android.support.constraint.solver.widgets.ConstraintWidget r14 = r7.H
            r26 = r2
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r14.a(r2)
            android.support.constraint.solver.SolverVariable r2 = r10.a(r2)
            r19 = r2
            r20 = r26
            goto L_0x018e
        L_0x016e:
            r7 = r34
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r7.H
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r14 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r2.a(r14)
            android.support.constraint.solver.SolverVariable r2 = r10.a(r2)
            android.support.constraint.solver.widgets.ConstraintWidget r14 = r7.H
            r27 = r2
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r14.a(r2)
            android.support.constraint.solver.SolverVariable r2 = r10.a(r2)
            r19 = r2
            r20 = r27
        L_0x018e:
            android.support.constraint.solver.ArrayRow r16 = r35.c()
            r17 = r8
            r18 = r9
            r21 = r53
            android.support.constraint.solver.ArrayRow r2 = r16.a(r17, r18, r19, r20, r21)
            r10.a(r2)
            r16 = 0
            goto L_0x01a6
        L_0x01a2:
            r25 = r7
            r7 = r34
        L_0x01a6:
            if (r16 == 0) goto L_0x01cb
            r2 = r23
            r14 = 2
            if (r2 == r14) goto L_0x01c7
            r17 = 4
            if (r48 != 0) goto L_0x01c4
            int r0 = java.lang.Math.max(r3, r0)
            if (r13 <= 0) goto L_0x01bb
            int r0 = java.lang.Math.min(r13, r0)
        L_0x01bb:
            r28 = r2
            r2 = 6
            r10.c(r8, r9, r0, r2)
            r16 = 0
            goto L_0x01cf
        L_0x01c4:
            r28 = r2
            goto L_0x01cf
        L_0x01c7:
            r28 = r2
            goto L_0x00fa
        L_0x01cb:
            r28 = r23
            goto L_0x00fa
        L_0x01cf:
            if (r54 == 0) goto L_0x031c
            if (r49 == 0) goto L_0x01d5
            goto L_0x031c
        L_0x01d5:
            r0 = 5
            if (r6 != 0) goto L_0x01e9
            if (r12 != 0) goto L_0x01e9
            if (r24 != 0) goto L_0x01e9
            if (r36 == 0) goto L_0x01e4
            r4 = 0
            r5 = r38
            r10.a(r5, r8, r4, r0)
        L_0x01e4:
            r1 = r8
        L_0x01e5:
            r3 = 0
            r4 = 6
            goto L_0x0313
        L_0x01e9:
            r4 = 0
            r5 = r38
            if (r6 == 0) goto L_0x01f6
            if (r12 != 0) goto L_0x01f6
            if (r36 == 0) goto L_0x01e4
            r10.a(r5, r8, r4, r0)
            goto L_0x01e4
        L_0x01f6:
            if (r6 != 0) goto L_0x020f
            if (r12 == 0) goto L_0x020f
            r2 = r42
            int r1 = r42.b()
            int r1 = -r1
            r2 = r22
            r3 = 6
            r10.c(r8, r2, r1, r3)
            if (r36 == 0) goto L_0x01e4
            r1 = r37
            r10.a(r9, r1, r4, r0)
            goto L_0x01e4
        L_0x020f:
            r30 = r22
            r0 = r37
            r2 = r42
            if (r6 == 0) goto L_0x01e4
            if (r12 == 0) goto L_0x01e4
            if (r16 == 0) goto L_0x0283
            if (r36 == 0) goto L_0x0224
            if (r1 != 0) goto L_0x0224
            r6 = 6
            r10.a(r8, r9, r4, r6)
            goto L_0x0225
        L_0x0224:
            r6 = 6
        L_0x0225:
            if (r15 != 0) goto L_0x0253
            if (r13 > 0) goto L_0x0231
            if (r3 <= 0) goto L_0x022c
            goto L_0x0231
        L_0x022c:
            r1 = 6
            r12 = r41
            r14 = 0
            goto L_0x0235
        L_0x0231:
            r1 = 4
            r12 = r41
            r14 = 1
        L_0x0235:
            int r15 = r41.b()
            r0 = r25
            r10.c(r9, r0, r15, r1)
            int r15 = r42.b()
            int r15 = -r15
            r6 = r30
            r10.c(r8, r6, r15, r1)
            if (r13 > 0) goto L_0x024f
            if (r3 <= 0) goto L_0x024d
            goto L_0x024f
        L_0x024d:
            r1 = 0
            goto L_0x0250
        L_0x024f:
            r1 = 1
        L_0x0250:
            r3 = 1
            r13 = 5
            goto L_0x028d
        L_0x0253:
            r0 = r25
            r6 = r30
            r3 = 1
            r12 = r41
            if (r15 != r3) goto L_0x0260
            r1 = 1
            r13 = 6
        L_0x025e:
            r14 = 1
            goto L_0x028d
        L_0x0260:
            r1 = 3
            if (r15 != r1) goto L_0x0281
            if (r48 != 0) goto L_0x026e
            int r1 = r7.r
            r14 = -1
            if (r1 == r14) goto L_0x026e
            if (r13 > 0) goto L_0x026e
            r1 = 6
            goto L_0x026f
        L_0x026e:
            r1 = 4
        L_0x026f:
            int r13 = r41.b()
            r10.c(r9, r0, r13, r1)
            int r13 = r42.b()
            int r13 = -r13
            r10.c(r8, r6, r13, r1)
            r1 = 1
            r13 = 5
            goto L_0x025e
        L_0x0281:
            r1 = 0
            goto L_0x028b
        L_0x0283:
            r0 = r25
            r6 = r30
            r3 = 1
            r12 = r41
            r1 = 1
        L_0x028b:
            r13 = 5
            r14 = 0
        L_0x028d:
            if (r1 == 0) goto L_0x02d4
            int r15 = r41.b()
            int r17 = r42.b()
            r1 = r10
            r11 = r2
            r2 = r9
            r18 = 1
            r3 = r0
            r19 = 0
            r4 = r15
            r15 = r5
            r5 = r47
            r32 = r0
            r31 = r6
            r0 = 6
            r0 = r32
            r7 = r8
            r15 = r8
            r8 = r17
            r33 = r15
            r15 = r9
            r9 = r13
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9)
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r12.d
            android.support.constraint.solver.widgets.ConstraintWidget r1 = r1.b
            boolean r1 = r1 instanceof android.support.constraint.solver.widgets.Barrier
            android.support.constraint.solver.widgets.ConstraintAnchor r2 = r11.d
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r2.b
            boolean r2 = r2 instanceof android.support.constraint.solver.widgets.Barrier
            if (r1 == 0) goto L_0x02cc
            if (r2 != 0) goto L_0x02cc
            r18 = r36
            r1 = 1
            r6 = 5
            r29 = 6
            goto L_0x02e1
        L_0x02cc:
            if (r1 != 0) goto L_0x02da
            if (r2 == 0) goto L_0x02da
            r1 = r36
            r6 = 6
            goto L_0x02df
        L_0x02d4:
            r11 = r2
            r31 = r6
            r33 = r8
            r15 = r9
        L_0x02da:
            r1 = r36
            r18 = r1
            r6 = 5
        L_0x02df:
            r29 = 5
        L_0x02e1:
            if (r14 == 0) goto L_0x02e6
            r2 = 6
            r6 = 6
            goto L_0x02e8
        L_0x02e6:
            r2 = r29
        L_0x02e8:
            if (r16 != 0) goto L_0x02ec
            if (r18 != 0) goto L_0x02ee
        L_0x02ec:
            if (r14 == 0) goto L_0x02f5
        L_0x02ee:
            int r3 = r41.b()
            r10.a(r15, r0, r3, r6)
        L_0x02f5:
            if (r16 != 0) goto L_0x02f9
            if (r1 != 0) goto L_0x02fb
        L_0x02f9:
            if (r14 == 0) goto L_0x0308
        L_0x02fb:
            int r0 = r42.b()
            int r0 = -r0
            r3 = r31
            r1 = r33
            r10.b(r1, r3, r0, r2)
            goto L_0x030a
        L_0x0308:
            r1 = r33
        L_0x030a:
            if (r36 == 0) goto L_0x01e5
            r2 = r37
            r3 = 0
            r4 = 6
            r10.a(r15, r2, r3, r4)
        L_0x0313:
            if (r36 == 0) goto L_0x031b
            r5 = r1
            r1 = r38
            r10.a(r1, r5, r3, r4)
        L_0x031b:
            return
        L_0x031c:
            r5 = r8
            r15 = r9
            r6 = r28
            r1 = r38
            r2 = r37
            r3 = 0
            r4 = 6
            r7 = 2
            if (r6 >= r7) goto L_0x0331
            if (r36 == 0) goto L_0x0331
            r10.a(r15, r2, r3, r4)
            r10.a(r1, r5, r3, r4)
        L_0x0331:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidget.a(android.support.constraint.solver.LinearSystem, boolean, android.support.constraint.solver.SolverVariable, android.support.constraint.solver.SolverVariable, android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour, boolean, android.support.constraint.solver.widgets.ConstraintAnchor, android.support.constraint.solver.widgets.ConstraintAnchor, int, int, int, int, float, boolean, boolean, int, int, int, float, boolean):void");
    }

    public void b(LinearSystem linearSystem) {
        int b2 = LinearSystem.b((Object) this.w);
        int b3 = LinearSystem.b((Object) this.x);
        int b4 = LinearSystem.b((Object) this.y);
        int b5 = LinearSystem.b((Object) this.z);
        int i2 = b5 - b3;
        if (b4 - b2 < 0 || i2 < 0 || b2 == Integer.MIN_VALUE || b2 == Integer.MAX_VALUE || b3 == Integer.MIN_VALUE || b3 == Integer.MAX_VALUE || b4 == Integer.MIN_VALUE || b4 == Integer.MAX_VALUE || b5 == Integer.MIN_VALUE || b5 == Integer.MAX_VALUE) {
            b2 = 0;
            b3 = 0;
            b4 = 0;
            b5 = 0;
        }
        int i3 = b4 - b2;
        int i4 = b5 - b3;
        this.M = b2;
        this.N = b3;
        if (this.ac == 8) {
            this.I = 0;
            this.J = 0;
            return;
        }
        if (this.G[0] == DimensionBehaviour.FIXED && i3 < this.I) {
            i3 = this.I;
        }
        if (this.G[1] == DimensionBehaviour.FIXED && i4 < this.J) {
            i4 = this.J;
        }
        this.I = i3;
        this.J = i4;
        if (this.J < this.U) {
            this.J = this.U;
        }
        if (this.I < this.T) {
            this.I = this.T;
        }
        this.ah = true;
    }

    public final int t() {
        return this.M + this.I;
    }

    public final int u() {
        return this.N + this.J;
    }

    public final void y() {
        ConstraintWidget constraintWidget = this.H;
        if (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) this.H).d()) {
            int size = this.F.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.F.get(i2).c();
            }
        }
    }

    public final DimensionBehaviour i(int i2) {
        if (i2 == 0) {
            return this.G[0];
        }
        if (i2 == 1) {
            return this.G[1];
        }
        return null;
    }
}
