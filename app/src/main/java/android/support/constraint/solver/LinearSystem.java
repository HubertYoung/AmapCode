package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable.Type;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem {
    public static Metrics h = null;
    private static int i = 1000;
    int a;
    public Row b;
    public ArrayRow[] c;
    public boolean d;
    int e;
    public int f;
    public final Cache g;
    private HashMap<String, SolverVariable> j;
    private int k;
    private int l;
    private boolean[] m;
    private int n;
    private SolverVariable[] o;
    private int p;
    private ArrayRow[] q;
    private final Row r;

    interface Row {
        SolverVariable a(boolean[] zArr);

        void a();

        void a(Row row);

        SolverVariable b();

        void c(SolverVariable solverVariable);
    }

    public LinearSystem() {
        this.a = 0;
        this.j = null;
        this.k = 32;
        this.l = this.k;
        this.c = null;
        this.d = false;
        this.m = new boolean[this.k];
        this.e = 1;
        this.f = 0;
        this.n = this.k;
        this.o = new SolverVariable[i];
        this.p = 0;
        this.q = new ArrayRow[this.k];
        this.c = new ArrayRow[this.k];
        g();
        this.g = new Cache();
        this.b = new GoalRow(this.g);
        this.r = new ArrayRow(this.g);
    }

    public static void a(Metrics metrics) {
        h = metrics;
    }

    public static Metrics a() {
        return h;
    }

    private void f() {
        this.k *= 2;
        this.c = (ArrayRow[]) Arrays.copyOf(this.c, this.k);
        this.g.c = (SolverVariable[]) Arrays.copyOf(this.g.c, this.k);
        this.m = new boolean[this.k];
        this.l = this.k;
        this.n = this.k;
        if (h != null) {
            Metrics metrics = h;
            metrics.d++;
            Metrics metrics2 = h;
            metrics2.p = Math.max(metrics2.p, (long) this.k);
            Metrics metrics3 = h;
            metrics3.D = metrics3.p;
        }
    }

    private void g() {
        for (int i2 = 0; i2 < this.c.length; i2++) {
            ArrayRow arrayRow = this.c[i2];
            if (arrayRow != null) {
                this.g.a.a(arrayRow);
            }
            this.c[i2] = null;
        }
    }

    public final void b() {
        for (SolverVariable solverVariable : this.g.c) {
            if (solverVariable != null) {
                solverVariable.b();
            }
        }
        this.g.b.a(this.o, this.p);
        this.p = 0;
        Arrays.fill(this.g.c, null);
        if (this.j != null) {
            this.j.clear();
        }
        this.a = 0;
        this.b.a();
        this.e = 1;
        for (int i2 = 0; i2 < this.f; i2++) {
            this.c[i2].c = false;
        }
        g();
        this.f = 0;
    }

    public final SolverVariable a(Object obj) {
        SolverVariable solverVariable = null;
        if (obj == null) {
            return null;
        }
        if (this.e + 1 >= this.l) {
            f();
        }
        if (obj instanceof ConstraintAnchor) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) obj;
            solverVariable = constraintAnchor.i;
            if (solverVariable == null) {
                constraintAnchor.a();
                solverVariable = constraintAnchor.i;
            }
            if (solverVariable.b == -1 || solverVariable.b > this.a || this.g.c[solverVariable.b] == null) {
                if (solverVariable.b != -1) {
                    solverVariable.b();
                }
                this.a++;
                this.e++;
                solverVariable.b = this.a;
                solverVariable.g = Type.UNRESTRICTED;
                this.g.c[this.a] = solverVariable;
            }
        }
        return solverVariable;
    }

    public final ArrayRow c() {
        ArrayRow arrayRow = (ArrayRow) this.g.a.a();
        if (arrayRow == null) {
            arrayRow = new ArrayRow(this.g);
        } else {
            arrayRow.a = null;
            arrayRow.d.a();
            arrayRow.b = 0.0f;
            arrayRow.e = false;
        }
        SolverVariable.a();
        return arrayRow;
    }

    public final SolverVariable d() {
        if (h != null) {
            Metrics metrics = h;
            metrics.n++;
        }
        if (this.e + 1 >= this.l) {
            f();
        }
        SolverVariable a2 = a(Type.SLACK);
        this.a++;
        this.e++;
        a2.b = this.a;
        this.g.c[this.a] = a2;
        return a2;
    }

    public final void a(ArrayRow arrayRow, int i2, int i3) {
        arrayRow.d.a(a(i3), (float) i2);
    }

    public final SolverVariable a(int i2) {
        if (h != null) {
            Metrics metrics = h;
            metrics.m++;
        }
        if (this.e + 1 >= this.l) {
            f();
        }
        SolverVariable a2 = a(Type.ERROR);
        this.a++;
        this.e++;
        a2.b = this.a;
        a2.d = i2;
        this.g.c[this.a] = a2;
        this.b.c(a2);
        return a2;
    }

    private SolverVariable a(Type type) {
        SolverVariable solverVariable = (SolverVariable) this.g.b.a();
        if (solverVariable == null) {
            solverVariable = new SolverVariable(type);
            solverVariable.g = type;
        } else {
            solverVariable.b();
            solverVariable.g = type;
        }
        if (this.p >= i) {
            i *= 2;
            this.o = (SolverVariable[]) Arrays.copyOf(this.o, i);
        }
        SolverVariable[] solverVariableArr = this.o;
        int i2 = this.p;
        this.p = i2 + 1;
        solverVariableArr[i2] = solverVariable;
        return solverVariable;
    }

    public static int b(Object obj) {
        SolverVariable solverVariable = ((ConstraintAnchor) obj).i;
        if (solverVariable != null) {
            return (int) (solverVariable.e + 0.5f);
        }
        return 0;
    }

    public final void a(Row row) throws Exception {
        float f2;
        int i2;
        boolean z;
        long j2;
        long j3 = 1;
        if (h != null) {
            Metrics metrics = h;
            metrics.t++;
            Metrics metrics2 = h;
            metrics2.u = Math.max(metrics2.u, (long) this.e);
            Metrics metrics3 = h;
            metrics3.v = Math.max(metrics3.v, (long) this.f);
        }
        b((ArrayRow) row);
        int i3 = 0;
        while (true) {
            f2 = 0.0f;
            i2 = 1;
            if (i3 < this.f) {
                if (this.c[i3].a.g != Type.UNRESTRICTED && this.c[i3].b < 0.0f) {
                    z = true;
                    break;
                }
                i3++;
            } else {
                z = false;
                break;
            }
        }
        if (z) {
            boolean z2 = false;
            int i4 = 0;
            while (!z2) {
                if (h != null) {
                    Metrics metrics4 = h;
                    metrics4.k += j3;
                }
                i4 += i2;
                int i5 = 0;
                int i6 = -1;
                int i7 = -1;
                float f3 = Float.MAX_VALUE;
                int i8 = 0;
                while (i5 < this.f) {
                    ArrayRow arrayRow = this.c[i5];
                    if (arrayRow.a.g != Type.UNRESTRICTED && !arrayRow.e && arrayRow.b < f2) {
                        int i9 = 1;
                        while (i9 < this.e) {
                            SolverVariable solverVariable = this.g.c[i9];
                            float b2 = arrayRow.d.b(solverVariable);
                            if (b2 > f2) {
                                for (int i10 = 0; i10 < 7; i10++) {
                                    float f4 = solverVariable.f[i10] / b2;
                                    if ((f4 < f3 && i10 == i8) || i10 > i8) {
                                        i8 = i10;
                                        i7 = i9;
                                        f3 = f4;
                                        i6 = i5;
                                    }
                                }
                            }
                            i9++;
                            f2 = 0.0f;
                        }
                    }
                    i5++;
                    f2 = 0.0f;
                }
                if (i6 != -1) {
                    ArrayRow arrayRow2 = this.c[i6];
                    arrayRow2.a.c = -1;
                    if (h != null) {
                        Metrics metrics5 = h;
                        j2 = 1;
                        metrics5.j++;
                    } else {
                        j2 = 1;
                    }
                    arrayRow2.b(this.g.c[i7]);
                    arrayRow2.a.c = i6;
                    arrayRow2.a.c(arrayRow2);
                } else {
                    j2 = 1;
                    z2 = true;
                }
                if (i4 > this.e / 2) {
                    z2 = true;
                }
                j3 = j2;
                f2 = 0.0f;
                i2 = 1;
            }
        }
        b(row);
        e();
    }

    private final void b(ArrayRow arrayRow) {
        if (this.f > 0) {
            arrayRow.d.a(arrayRow, this.c);
            if (arrayRow.d.a == 0) {
                arrayRow.e = true;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:124:0x0111 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00c4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.support.constraint.solver.ArrayRow r21) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            android.support.constraint.solver.Metrics r2 = h
            r3 = 1
            if (r2 == 0) goto L_0x001f
            android.support.constraint.solver.Metrics r2 = h
            long r5 = r2.f
            long r5 = r5 + r3
            r2.f = r5
            boolean r2 = r1.e
            if (r2 == 0) goto L_0x001f
            android.support.constraint.solver.Metrics r2 = h
            long r5 = r2.g
            long r5 = r5 + r3
            r2.g = r5
        L_0x001f:
            int r2 = r0.f
            r5 = 1
            int r2 = r2 + r5
            int r6 = r0.n
            if (r2 >= r6) goto L_0x002e
            int r2 = r0.e
            int r2 = r2 + r5
            int r6 = r0.l
            if (r2 < r6) goto L_0x0031
        L_0x002e:
            r20.f()
        L_0x0031:
            boolean r2 = r1.e
            if (r2 != 0) goto L_0x01c4
            r20.b(r21)
            android.support.constraint.solver.SolverVariable r2 = r1.a
            r7 = 0
            if (r2 != 0) goto L_0x004b
            float r2 = r1.b
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 != 0) goto L_0x004b
            android.support.constraint.solver.ArrayLinkedVariables r2 = r1.d
            int r2 = r2.a
            if (r2 != 0) goto L_0x004b
            r2 = 1
            goto L_0x004c
        L_0x004b:
            r2 = 0
        L_0x004c:
            if (r2 == 0) goto L_0x004f
            return
        L_0x004f:
            float r2 = r1.b
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            r8 = -1
            if (r2 >= 0) goto L_0x0078
            float r2 = r1.b
            r9 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r2 = r2 * r9
            r1.b = r2
            android.support.constraint.solver.ArrayLinkedVariables r2 = r1.d
            int r10 = r2.g
            r11 = 0
        L_0x0063:
            if (r10 == r8) goto L_0x0078
            int r12 = r2.a
            if (r11 >= r12) goto L_0x0078
            float[] r12 = r2.f
            r13 = r12[r10]
            float r13 = r13 * r9
            r12[r10] = r13
            int[] r12 = r2.e
            r10 = r12[r10]
            int r11 = r11 + 1
            goto L_0x0063
        L_0x0078:
            android.support.constraint.solver.ArrayLinkedVariables r2 = r1.d
            int r9 = r2.g
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
        L_0x0085:
            if (r9 == r8) goto L_0x011c
            int r6 = r2.a
            if (r11 >= r6) goto L_0x011c
            float[] r6 = r2.f
            r6 = r6[r9]
            android.support.constraint.solver.Cache r10 = r2.c
            android.support.constraint.solver.SolverVariable[] r10 = r10.c
            int[] r8 = r2.d
            r8 = r8[r9]
            r8 = r10[r8]
            int r10 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r10 >= 0) goto L_0x00af
            r10 = -1165815185(0xffffffffba83126f, float:-0.001)
            int r10 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r10 <= 0) goto L_0x00c0
            float[] r6 = r2.f
            r6[r9] = r7
            android.support.constraint.solver.ArrayRow r6 = r2.b
            r8.b(r6)
        L_0x00ad:
            r6 = 0
            goto L_0x00c0
        L_0x00af:
            r10 = 981668463(0x3a83126f, float:0.001)
            int r10 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r10 >= 0) goto L_0x00c0
            float[] r6 = r2.f
            r6[r9] = r7
            android.support.constraint.solver.ArrayRow r6 = r2.b
            r8.b(r6)
            goto L_0x00ad
        L_0x00c0:
            int r10 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r10 == 0) goto L_0x0111
            android.support.constraint.solver.SolverVariable$Type r10 = r8.g
            android.support.constraint.solver.SolverVariable$Type r3 = android.support.constraint.solver.SolverVariable.Type.UNRESTRICTED
            if (r10 != r3) goto L_0x00e9
            if (r12 != 0) goto L_0x00d4
            boolean r3 = android.support.constraint.solver.ArrayLinkedVariables.a(r8)
        L_0x00d0:
            r15 = r3
            r14 = r6
            r12 = r8
            goto L_0x0111
        L_0x00d4:
            int r3 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x00dd
            boolean r3 = android.support.constraint.solver.ArrayLinkedVariables.a(r8)
            goto L_0x00d0
        L_0x00dd:
            if (r15 != 0) goto L_0x0111
            boolean r3 = android.support.constraint.solver.ArrayLinkedVariables.a(r8)
            if (r3 == 0) goto L_0x0111
            r14 = r6
            r12 = r8
            r15 = 1
            goto L_0x0111
        L_0x00e9:
            if (r12 != 0) goto L_0x0111
            int r3 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r3 >= 0) goto L_0x0111
            if (r13 != 0) goto L_0x00fb
            boolean r3 = android.support.constraint.solver.ArrayLinkedVariables.a(r8)
        L_0x00f5:
            r17 = r3
            r16 = r6
            r13 = r8
            goto L_0x0111
        L_0x00fb:
            int r3 = (r16 > r6 ? 1 : (r16 == r6 ? 0 : -1))
            if (r3 <= 0) goto L_0x0104
            boolean r3 = android.support.constraint.solver.ArrayLinkedVariables.a(r8)
            goto L_0x00f5
        L_0x0104:
            if (r17 != 0) goto L_0x0111
            boolean r3 = android.support.constraint.solver.ArrayLinkedVariables.a(r8)
            if (r3 == 0) goto L_0x0111
            r16 = r6
            r13 = r8
            r17 = 1
        L_0x0111:
            int[] r3 = r2.e
            r9 = r3[r9]
            int r11 = r11 + 1
            r3 = 1
            r8 = -1
            goto L_0x0085
        L_0x011c:
            if (r12 == 0) goto L_0x011f
            goto L_0x0120
        L_0x011f:
            r12 = r13
        L_0x0120:
            if (r12 != 0) goto L_0x0124
            r2 = 1
            goto L_0x0128
        L_0x0124:
            r1.b(r12)
            r2 = 0
        L_0x0128:
            android.support.constraint.solver.ArrayLinkedVariables r3 = r1.d
            int r3 = r3.a
            if (r3 != 0) goto L_0x0130
            r1.e = r5
        L_0x0130:
            if (r2 == 0) goto L_0x01a6
            android.support.constraint.solver.Metrics r2 = h
            if (r2 == 0) goto L_0x013f
            android.support.constraint.solver.Metrics r2 = h
            long r3 = r2.o
            r8 = 1
            long r3 = r3 + r8
            r2.o = r3
        L_0x013f:
            int r2 = r0.e
            int r2 = r2 + r5
            int r3 = r0.l
            if (r2 < r3) goto L_0x0149
            r20.f()
        L_0x0149:
            android.support.constraint.solver.SolverVariable$Type r2 = android.support.constraint.solver.SolverVariable.Type.SLACK
            android.support.constraint.solver.SolverVariable r2 = r0.a(r2)
            int r3 = r0.a
            int r3 = r3 + r5
            r0.a = r3
            int r3 = r0.e
            int r3 = r3 + r5
            r0.e = r3
            int r3 = r0.a
            r2.b = r3
            android.support.constraint.solver.Cache r3 = r0.g
            android.support.constraint.solver.SolverVariable[] r3 = r3.c
            int r4 = r0.a
            r3[r4] = r2
            r1.a = r2
            r20.c(r21)
            android.support.constraint.solver.LinearSystem$Row r3 = r0.r
            r3.a(r1)
            android.support.constraint.solver.LinearSystem$Row r3 = r0.r
            r0.b(r3)
            int r3 = r2.c
            r4 = -1
            if (r3 != r4) goto L_0x01a4
            android.support.constraint.solver.SolverVariable r3 = r1.a
            if (r3 != r2) goto L_0x0196
            android.support.constraint.solver.ArrayLinkedVariables r3 = r1.d
            r4 = 0
            android.support.constraint.solver.SolverVariable r2 = r3.a(r4, r2)
            if (r2 == 0) goto L_0x0196
            android.support.constraint.solver.Metrics r3 = h
            if (r3 == 0) goto L_0x0193
            android.support.constraint.solver.Metrics r3 = h
            long r8 = r3.j
            r10 = 1
            long r8 = r8 + r10
            r3.j = r8
        L_0x0193:
            r1.b(r2)
        L_0x0196:
            boolean r2 = r1.e
            if (r2 != 0) goto L_0x019f
            android.support.constraint.solver.SolverVariable r2 = r1.a
            r2.c(r1)
        L_0x019f:
            int r2 = r0.f
            int r2 = r2 - r5
            r0.f = r2
        L_0x01a4:
            r6 = 1
            goto L_0x01a7
        L_0x01a6:
            r6 = 0
        L_0x01a7:
            android.support.constraint.solver.SolverVariable r2 = r1.a
            if (r2 == 0) goto L_0x01bc
            android.support.constraint.solver.SolverVariable r2 = r1.a
            android.support.constraint.solver.SolverVariable$Type r2 = r2.g
            android.support.constraint.solver.SolverVariable$Type r3 = android.support.constraint.solver.SolverVariable.Type.UNRESTRICTED
            if (r2 == r3) goto L_0x01b9
            float r2 = r1.b
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 < 0) goto L_0x01bc
        L_0x01b9:
            r18 = 1
            goto L_0x01be
        L_0x01bc:
            r18 = 0
        L_0x01be:
            if (r18 != 0) goto L_0x01c1
            return
        L_0x01c1:
            r18 = r6
            goto L_0x01c6
        L_0x01c4:
            r18 = 0
        L_0x01c6:
            if (r18 != 0) goto L_0x01cb
            r20.c(r21)
        L_0x01cb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.LinearSystem.a(android.support.constraint.solver.ArrayRow):void");
    }

    private final void c(ArrayRow arrayRow) {
        if (this.c[this.f] != null) {
            this.g.a.a(this.c[this.f]);
        }
        this.c[this.f] = arrayRow;
        arrayRow.a.c = this.f;
        this.f++;
        arrayRow.a.c(arrayRow);
    }

    private final int b(Row row) {
        if (h != null) {
            Metrics metrics = h;
            metrics.h++;
        }
        for (int i2 = 0; i2 < this.e; i2++) {
            this.m[i2] = false;
        }
        boolean z = false;
        int i3 = 0;
        while (!z) {
            if (h != null) {
                Metrics metrics2 = h;
                metrics2.i++;
            }
            i3++;
            if (i3 >= this.e * 2) {
                return i3;
            }
            if (row.b() != null) {
                this.m[row.b().b] = true;
            }
            SolverVariable a2 = row.a(this.m);
            if (a2 != null) {
                if (this.m[a2.b]) {
                    return i3;
                }
                this.m[a2.b] = true;
            }
            if (a2 != null) {
                int i4 = -1;
                float f2 = Float.MAX_VALUE;
                for (int i5 = 0; i5 < this.f; i5++) {
                    ArrayRow arrayRow = this.c[i5];
                    if (arrayRow.a.g != Type.UNRESTRICTED && !arrayRow.e && arrayRow.a(a2)) {
                        float b2 = arrayRow.d.b(a2);
                        if (b2 < 0.0f) {
                            float f3 = (-arrayRow.b) / b2;
                            if (f3 < f2) {
                                i4 = i5;
                                f2 = f3;
                            }
                        }
                    }
                }
                if (i4 >= 0) {
                    ArrayRow arrayRow2 = this.c[i4];
                    arrayRow2.a.c = -1;
                    if (h != null) {
                        Metrics metrics3 = h;
                        metrics3.j++;
                    }
                    arrayRow2.b(a2);
                    arrayRow2.a.c = i4;
                    arrayRow2.a.c(arrayRow2);
                }
            }
            z = true;
        }
        return i3;
    }

    public final void e() {
        for (int i2 = 0; i2 < this.f; i2++) {
            ArrayRow arrayRow = this.c[i2];
            arrayRow.a.e = arrayRow.b;
        }
    }

    public final void a(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        ArrayRow c2 = c();
        SolverVariable d2 = d();
        d2.d = 0;
        c2.a(solverVariable, solverVariable2, d2, i2);
        if (i3 != 6) {
            a(c2, (int) (c2.d.b(d2) * -1.0f), i3);
        }
        a(c2);
    }

    public final void b(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        ArrayRow c2 = c();
        SolverVariable d2 = d();
        d2.d = 0;
        c2.b(solverVariable, solverVariable2, d2, i2);
        if (i3 != 6) {
            a(c2, (int) (c2.d.b(d2) * -1.0f), i3);
        }
        a(c2);
    }

    public final void a(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, float f2, SolverVariable solverVariable3, SolverVariable solverVariable4, int i3, int i4) {
        ArrayRow c2 = c();
        if (solverVariable2 == solverVariable3) {
            c2.d.a(solverVariable, 1.0f);
            c2.d.a(solverVariable4, 1.0f);
            c2.d.a(solverVariable2, -2.0f);
        } else if (f2 == 0.5f) {
            c2.d.a(solverVariable, 1.0f);
            c2.d.a(solverVariable2, -1.0f);
            c2.d.a(solverVariable3, -1.0f);
            c2.d.a(solverVariable4, 1.0f);
            if (i2 > 0 || i3 > 0) {
                c2.b = (float) ((-i2) + i3);
            }
        } else if (f2 <= 0.0f) {
            c2.d.a(solverVariable, -1.0f);
            c2.d.a(solverVariable2, 1.0f);
            c2.b = (float) i2;
        } else if (f2 >= 1.0f) {
            c2.d.a(solverVariable3, -1.0f);
            c2.d.a(solverVariable4, 1.0f);
            c2.b = (float) i3;
        } else {
            float f3 = 1.0f - f2;
            c2.d.a(solverVariable, f3 * 1.0f);
            c2.d.a(solverVariable2, f3 * -1.0f);
            c2.d.a(solverVariable3, -1.0f * f2);
            c2.d.a(solverVariable4, 1.0f * f2);
            if (i2 > 0 || i3 > 0) {
                c2.b = (((float) (-i2)) * f3) + (((float) i3) * f2);
            }
        }
        if (i4 != 6) {
            c2.a(this, i4);
        }
        a(c2);
    }

    public final void a(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2) {
        ArrayRow c2 = c();
        c2.a(solverVariable, solverVariable2, solverVariable3, solverVariable4, f2);
        a(c2);
    }

    public final ArrayRow c(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        ArrayRow c2 = c();
        boolean z = false;
        if (i2 != 0) {
            if (i2 < 0) {
                i2 *= -1;
                z = true;
            }
            c2.b = (float) i2;
        }
        if (!z) {
            c2.d.a(solverVariable, -1.0f);
            c2.d.a(solverVariable2, 1.0f);
        } else {
            c2.d.a(solverVariable, 1.0f);
            c2.d.a(solverVariable2, -1.0f);
        }
        if (i3 != 6) {
            c2.a(this, i3);
        }
        a(c2);
        return c2;
    }

    public final void a(SolverVariable solverVariable, int i2) {
        int i3 = solverVariable.c;
        if (solverVariable.c != -1) {
            ArrayRow arrayRow = this.c[i3];
            if (arrayRow.e) {
                arrayRow.b = (float) i2;
            } else if (arrayRow.d.a == 0) {
                arrayRow.e = true;
                arrayRow.b = (float) i2;
            } else {
                ArrayRow c2 = c();
                if (i2 < 0) {
                    c2.b = (float) (i2 * -1);
                    c2.d.a(solverVariable, 1.0f);
                } else {
                    c2.b = (float) i2;
                    c2.d.a(solverVariable, -1.0f);
                }
                a(c2);
            }
        } else {
            ArrayRow c3 = c();
            c3.a = solverVariable;
            float f2 = (float) i2;
            solverVariable.e = f2;
            c3.b = f2;
            c3.e = true;
            a(c3);
        }
    }

    public static ArrayRow a(LinearSystem linearSystem, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, float f2, boolean z) {
        ArrayRow c2 = linearSystem.c();
        if (z) {
            c2.a(linearSystem, 0);
        }
        c2.d.a(solverVariable, -1.0f);
        c2.d.a(solverVariable2, 1.0f - f2);
        c2.d.a(solverVariable3, f2);
        return c2;
    }
}
