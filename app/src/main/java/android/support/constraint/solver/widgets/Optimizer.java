package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;

public class Optimizer {
    static boolean[] a = new boolean[3];

    static void a(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ConstraintWidget constraintWidget) {
        if (constraintWidgetContainer.G[0] != DimensionBehaviour.WRAP_CONTENT && constraintWidget.G[0] == DimensionBehaviour.MATCH_PARENT) {
            int i = constraintWidget.w.e;
            int n = constraintWidgetContainer.n() - constraintWidget.y.e;
            constraintWidget.w.i = linearSystem.a((Object) constraintWidget.w);
            constraintWidget.y.i = linearSystem.a((Object) constraintWidget.y);
            linearSystem.a(constraintWidget.w.i, i);
            linearSystem.a(constraintWidget.y.i, n);
            constraintWidget.c = 2;
            constraintWidget.c(i, n);
        }
        if (constraintWidgetContainer.G[1] != DimensionBehaviour.WRAP_CONTENT && constraintWidget.G[1] == DimensionBehaviour.MATCH_PARENT) {
            int i2 = constraintWidget.x.e;
            int o = constraintWidgetContainer.o() - constraintWidget.z.e;
            constraintWidget.x.i = linearSystem.a((Object) constraintWidget.x);
            constraintWidget.z.i = linearSystem.a((Object) constraintWidget.z);
            linearSystem.a(constraintWidget.x.i, i2);
            linearSystem.a(constraintWidget.z.i, o);
            if (constraintWidget.S > 0 || constraintWidget.ac == 8) {
                constraintWidget.A.i = linearSystem.a((Object) constraintWidget.A);
                linearSystem.a(constraintWidget.A.i, constraintWidget.S + i2);
            }
            constraintWidget.d = 2;
            constraintWidget.d(i2, o);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x003e A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(android.support.constraint.solver.widgets.ConstraintWidget r3, int r4) {
        /*
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r3.G
            r0 = r0[r4]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r2 = 0
            if (r0 == r1) goto L_0x000a
            return r2
        L_0x000a:
            float r0 = r3.K
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r1 = 1
            if (r0 == 0) goto L_0x0020
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r3.G
            if (r4 != 0) goto L_0x0017
            goto L_0x0018
        L_0x0017:
            r1 = 0
        L_0x0018:
            r3 = r3[r1]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 != r4) goto L_0x001f
            return r2
        L_0x001f:
            return r2
        L_0x0020:
            if (r4 != 0) goto L_0x0030
            int r4 = r3.g
            if (r4 == 0) goto L_0x0027
            return r2
        L_0x0027:
            int r4 = r3.j
            if (r4 != 0) goto L_0x002f
            int r3 = r3.k
            if (r3 == 0) goto L_0x003e
        L_0x002f:
            return r2
        L_0x0030:
            int r4 = r3.h
            if (r4 == 0) goto L_0x0035
            return r2
        L_0x0035:
            int r4 = r3.m
            if (r4 != 0) goto L_0x003f
            int r3 = r3.n
            if (r3 == 0) goto L_0x003e
            goto L_0x003f
        L_0x003e:
            return r1
        L_0x003f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.Optimizer.a(android.support.constraint.solver.widgets.ConstraintWidget, int):boolean");
    }

    static void a(int i, ConstraintWidget constraintWidget) {
        ConstraintWidget constraintWidget2 = constraintWidget;
        constraintWidget.h();
        ResolutionAnchor resolutionAnchor = constraintWidget2.w.a;
        ResolutionAnchor resolutionAnchor2 = constraintWidget2.x.a;
        ResolutionAnchor resolutionAnchor3 = constraintWidget2.y.a;
        ResolutionAnchor resolutionAnchor4 = constraintWidget2.z.a;
        boolean z = (i & 8) == 8;
        boolean z2 = constraintWidget2.G[0] == DimensionBehaviour.MATCH_CONSTRAINT && a(constraintWidget2, 0);
        if (!(resolutionAnchor.g == 4 || resolutionAnchor3.g == 4)) {
            if (constraintWidget2.G[0] == DimensionBehaviour.FIXED || (z2 && constraintWidget2.ac == 8)) {
                if (constraintWidget2.w.d == null && constraintWidget2.y.d == null) {
                    resolutionAnchor.g = 1;
                    resolutionAnchor3.g = 1;
                    if (z) {
                        resolutionAnchor3.a(resolutionAnchor, 1, constraintWidget.j());
                    } else {
                        resolutionAnchor3.b(resolutionAnchor, constraintWidget.n());
                    }
                } else if (constraintWidget2.w.d != null && constraintWidget2.y.d == null) {
                    resolutionAnchor.g = 1;
                    resolutionAnchor3.g = 1;
                    if (z) {
                        resolutionAnchor3.a(resolutionAnchor, 1, constraintWidget.j());
                    } else {
                        resolutionAnchor3.b(resolutionAnchor, constraintWidget.n());
                    }
                } else if (constraintWidget2.w.d == null && constraintWidget2.y.d != null) {
                    resolutionAnchor.g = 1;
                    resolutionAnchor3.g = 1;
                    resolutionAnchor.b(resolutionAnchor3, -constraintWidget.n());
                    if (z) {
                        resolutionAnchor.a(resolutionAnchor3, -1, constraintWidget.j());
                    } else {
                        resolutionAnchor.b(resolutionAnchor3, -constraintWidget.n());
                    }
                } else if (!(constraintWidget2.w.d == null || constraintWidget2.y.d == null)) {
                    resolutionAnchor.g = 2;
                    resolutionAnchor3.g = 2;
                    if (z) {
                        constraintWidget.j().a(resolutionAnchor);
                        constraintWidget.j().a(resolutionAnchor3);
                        resolutionAnchor.b(resolutionAnchor3, -1, constraintWidget.j());
                        resolutionAnchor3.b(resolutionAnchor, 1, constraintWidget.j());
                    } else {
                        resolutionAnchor.b(resolutionAnchor3, (float) (-constraintWidget.n()));
                        resolutionAnchor3.b(resolutionAnchor, (float) constraintWidget.n());
                    }
                }
            } else if (z2) {
                int n = constraintWidget.n();
                resolutionAnchor.g = 1;
                resolutionAnchor3.g = 1;
                if (constraintWidget2.w.d == null && constraintWidget2.y.d == null) {
                    if (z) {
                        resolutionAnchor3.a(resolutionAnchor, 1, constraintWidget.j());
                    } else {
                        resolutionAnchor3.b(resolutionAnchor, n);
                    }
                } else if (constraintWidget2.w.d == null || constraintWidget2.y.d != null) {
                    if (constraintWidget2.w.d != null || constraintWidget2.y.d == null) {
                        if (!(constraintWidget2.w.d == null || constraintWidget2.y.d == null)) {
                            if (z) {
                                constraintWidget.j().a(resolutionAnchor);
                                constraintWidget.j().a(resolutionAnchor3);
                            }
                            if (constraintWidget2.K == 0.0f) {
                                resolutionAnchor.g = 3;
                                resolutionAnchor3.g = 3;
                                resolutionAnchor.b(resolutionAnchor3, 0.0f);
                                resolutionAnchor3.b(resolutionAnchor, 0.0f);
                            } else {
                                resolutionAnchor.g = 2;
                                resolutionAnchor3.g = 2;
                                resolutionAnchor.b(resolutionAnchor3, (float) (-n));
                                resolutionAnchor3.b(resolutionAnchor, (float) n);
                                constraintWidget2.e(n);
                            }
                        }
                    } else if (z) {
                        resolutionAnchor.a(resolutionAnchor3, -1, constraintWidget.j());
                    } else {
                        resolutionAnchor.b(resolutionAnchor3, -n);
                    }
                } else if (z) {
                    resolutionAnchor3.a(resolutionAnchor, 1, constraintWidget.j());
                } else {
                    resolutionAnchor3.b(resolutionAnchor, n);
                }
            }
        }
        boolean z3 = constraintWidget2.G[1] == DimensionBehaviour.MATCH_CONSTRAINT && a(constraintWidget2, 1);
        if (!(resolutionAnchor2.g == 4 || resolutionAnchor4.g == 4)) {
            if (constraintWidget2.G[1] == DimensionBehaviour.FIXED || (z3 && constraintWidget2.ac == 8)) {
                if (constraintWidget2.x.d == null && constraintWidget2.z.d == null) {
                    resolutionAnchor2.g = 1;
                    resolutionAnchor4.g = 1;
                    if (z) {
                        resolutionAnchor4.a(resolutionAnchor2, 1, constraintWidget.k());
                    } else {
                        resolutionAnchor4.b(resolutionAnchor2, constraintWidget.o());
                    }
                    if (constraintWidget2.A.d != null) {
                        constraintWidget2.A.a.g = 1;
                        resolutionAnchor2.a(constraintWidget2.A.a, -constraintWidget2.S);
                    }
                } else if (constraintWidget2.x.d != null && constraintWidget2.z.d == null) {
                    resolutionAnchor2.g = 1;
                    resolutionAnchor4.g = 1;
                    if (z) {
                        resolutionAnchor4.a(resolutionAnchor2, 1, constraintWidget.k());
                    } else {
                        resolutionAnchor4.b(resolutionAnchor2, constraintWidget.o());
                    }
                    if (constraintWidget2.S > 0) {
                        constraintWidget2.A.a.a(resolutionAnchor2, constraintWidget2.S);
                    }
                } else if (constraintWidget2.x.d == null && constraintWidget2.z.d != null) {
                    resolutionAnchor2.g = 1;
                    resolutionAnchor4.g = 1;
                    if (z) {
                        resolutionAnchor2.a(resolutionAnchor4, -1, constraintWidget.k());
                    } else {
                        resolutionAnchor2.b(resolutionAnchor4, -constraintWidget.o());
                    }
                    if (constraintWidget2.S > 0) {
                        constraintWidget2.A.a.a(resolutionAnchor2, constraintWidget2.S);
                    }
                } else if (!(constraintWidget2.x.d == null || constraintWidget2.z.d == null)) {
                    resolutionAnchor2.g = 2;
                    resolutionAnchor4.g = 2;
                    if (z) {
                        resolutionAnchor2.b(resolutionAnchor4, -1, constraintWidget.k());
                        resolutionAnchor4.b(resolutionAnchor2, 1, constraintWidget.k());
                        constraintWidget.k().a(resolutionAnchor2);
                        constraintWidget.j().a(resolutionAnchor4);
                    } else {
                        resolutionAnchor2.b(resolutionAnchor4, (float) (-constraintWidget.o()));
                        resolutionAnchor4.b(resolutionAnchor2, (float) constraintWidget.o());
                    }
                    if (constraintWidget2.S > 0) {
                        constraintWidget2.A.a.a(resolutionAnchor2, constraintWidget2.S);
                    }
                }
            } else if (z3) {
                int o = constraintWidget.o();
                resolutionAnchor2.g = 1;
                resolutionAnchor4.g = 1;
                if (constraintWidget2.x.d == null && constraintWidget2.z.d == null) {
                    if (z) {
                        resolutionAnchor4.a(resolutionAnchor2, 1, constraintWidget.k());
                    } else {
                        resolutionAnchor4.b(resolutionAnchor2, o);
                    }
                } else if (constraintWidget2.x.d == null || constraintWidget2.z.d != null) {
                    if (constraintWidget2.x.d != null || constraintWidget2.z.d == null) {
                        if (!(constraintWidget2.x.d == null || constraintWidget2.z.d == null)) {
                            if (z) {
                                constraintWidget.k().a(resolutionAnchor2);
                                constraintWidget.j().a(resolutionAnchor4);
                            }
                            if (constraintWidget2.K == 0.0f) {
                                resolutionAnchor2.g = 3;
                                resolutionAnchor4.g = 3;
                                resolutionAnchor2.b(resolutionAnchor4, 0.0f);
                                resolutionAnchor4.b(resolutionAnchor2, 0.0f);
                                return;
                            }
                            resolutionAnchor2.g = 2;
                            resolutionAnchor4.g = 2;
                            resolutionAnchor2.b(resolutionAnchor4, (float) (-o));
                            resolutionAnchor4.b(resolutionAnchor2, (float) o);
                            constraintWidget2.f(o);
                            if (constraintWidget2.S > 0) {
                                constraintWidget2.A.a.a(resolutionAnchor2, constraintWidget2.S);
                            }
                        }
                    } else if (z) {
                        resolutionAnchor2.a(resolutionAnchor4, -1, constraintWidget.k());
                    } else {
                        resolutionAnchor2.b(resolutionAnchor4, -o);
                    }
                } else if (z) {
                    resolutionAnchor4.a(resolutionAnchor2, 1, constraintWidget.k());
                } else {
                    resolutionAnchor4.b(resolutionAnchor2, o);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        if (r7.aj == 2) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003e, code lost:
        if (r7.ak == 2) goto L_0x002a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00fa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a(android.support.constraint.solver.LinearSystem r23, int r24, int r25, android.support.constraint.solver.widgets.ChainHead r26) {
        /*
            r0 = r23
            r1 = r24
            r2 = r26
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r2.a
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r2.c
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r2.b
            android.support.constraint.solver.widgets.ConstraintWidget r6 = r2.d
            android.support.constraint.solver.widgets.ConstraintWidget r7 = r2.e
            float r2 = r2.k
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r8 = 2
            r10 = 1
            if (r1 != 0) goto L_0x002e
            int r11 = r7.aj
            if (r11 != 0) goto L_0x001e
            r11 = 1
            goto L_0x001f
        L_0x001e:
            r11 = 0
        L_0x001f:
            int r12 = r7.aj
            if (r12 != r10) goto L_0x0025
            r12 = 1
            goto L_0x0026
        L_0x0025:
            r12 = 0
        L_0x0026:
            int r7 = r7.aj
            if (r7 != r8) goto L_0x002c
        L_0x002a:
            r7 = 1
            goto L_0x0041
        L_0x002c:
            r7 = 0
            goto L_0x0041
        L_0x002e:
            int r11 = r7.ak
            if (r11 != 0) goto L_0x0034
            r11 = 1
            goto L_0x0035
        L_0x0034:
            r11 = 0
        L_0x0035:
            int r12 = r7.ak
            if (r12 != r10) goto L_0x003b
            r12 = 1
            goto L_0x003c
        L_0x003b:
            r12 = 0
        L_0x003c:
            int r7 = r7.ak
            if (r7 != r8) goto L_0x002c
            goto L_0x002a
        L_0x0041:
            r14 = r3
            r8 = 0
            r10 = 0
            r13 = 0
            r15 = 0
            r17 = 0
        L_0x0048:
            r9 = 8
            if (r13 != 0) goto L_0x00fd
            r18 = r13
            int r13 = r14.ac
            if (r13 == r9) goto L_0x0095
            int r10 = r10 + 1
            if (r1 != 0) goto L_0x005d
            int r13 = r14.n()
            float r13 = (float) r13
            float r15 = r15 + r13
            goto L_0x0063
        L_0x005d:
            int r13 = r14.o()
            float r13 = (float) r13
            float r15 = r15 + r13
        L_0x0063:
            if (r14 == r5) goto L_0x006f
            android.support.constraint.solver.widgets.ConstraintAnchor[] r13 = r14.E
            r13 = r13[r25]
            int r13 = r13.b()
            float r13 = (float) r13
            float r15 = r15 + r13
        L_0x006f:
            if (r14 == r6) goto L_0x007d
            android.support.constraint.solver.widgets.ConstraintAnchor[] r13 = r14.E
            int r19 = r25 + 1
            r13 = r13[r19]
            int r13 = r13.b()
            float r13 = (float) r13
            float r15 = r15 + r13
        L_0x007d:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r13 = r14.E
            r13 = r13[r25]
            int r13 = r13.b()
            float r13 = (float) r13
            float r17 = r17 + r13
            android.support.constraint.solver.widgets.ConstraintAnchor[] r13 = r14.E
            int r19 = r25 + 1
            r13 = r13[r19]
            int r13 = r13.b()
            float r13 = (float) r13
            float r17 = r17 + r13
        L_0x0095:
            int r13 = r14.ac
            if (r13 == r9) goto L_0x00ce
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r9 = r14.G
            r9 = r9[r1]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 != r13) goto L_0x00ce
            int r8 = r8 + 1
            if (r1 != 0) goto L_0x00b5
            int r9 = r14.g
            if (r9 == 0) goto L_0x00ab
            r9 = 0
            return r9
        L_0x00ab:
            r9 = 0
            int r13 = r14.j
            if (r13 != 0) goto L_0x00b4
            int r13 = r14.k
            if (r13 == 0) goto L_0x00c4
        L_0x00b4:
            return r9
        L_0x00b5:
            r9 = 0
            int r13 = r14.h
            if (r13 == 0) goto L_0x00bb
            return r9
        L_0x00bb:
            int r13 = r14.m
            if (r13 != 0) goto L_0x00cd
            int r13 = r14.n
            if (r13 == 0) goto L_0x00c4
            goto L_0x00cd
        L_0x00c4:
            float r13 = r14.K
            r16 = 0
            int r13 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r13 == 0) goto L_0x00ce
            return r9
        L_0x00cd:
            return r9
        L_0x00ce:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r9 = r14.E
            int r13 = r25 + 1
            r9 = r9[r13]
            android.support.constraint.solver.widgets.ConstraintAnchor r9 = r9.d
            if (r9 == 0) goto L_0x00f0
            android.support.constraint.solver.widgets.ConstraintWidget r9 = r9.b
            android.support.constraint.solver.widgets.ConstraintAnchor[] r13 = r9.E
            r13 = r13[r25]
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.d
            if (r13 == 0) goto L_0x00f0
            android.support.constraint.solver.widgets.ConstraintAnchor[] r13 = r9.E
            r13 = r13[r25]
            android.support.constraint.solver.widgets.ConstraintAnchor r13 = r13.d
            android.support.constraint.solver.widgets.ConstraintWidget r13 = r13.b
            if (r13 == r14) goto L_0x00ed
            goto L_0x00f0
        L_0x00ed:
            r20 = r9
            goto L_0x00f2
        L_0x00f0:
            r20 = 0
        L_0x00f2:
            if (r20 == 0) goto L_0x00fa
            r13 = r18
            r14 = r20
            goto L_0x0048
        L_0x00fa:
            r13 = 1
            goto L_0x0048
        L_0x00fd:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r13 = r3.E
            r13 = r13[r25]
            android.support.constraint.solver.widgets.ResolutionAnchor r13 = r13.a
            android.support.constraint.solver.widgets.ConstraintAnchor[] r9 = r4.E
            int r18 = r25 + 1
            r9 = r9[r18]
            android.support.constraint.solver.widgets.ResolutionAnchor r9 = r9.a
            r21 = r3
            android.support.constraint.solver.widgets.ResolutionAnchor r3 = r13.c
            if (r3 == 0) goto L_0x036e
            android.support.constraint.solver.widgets.ResolutionAnchor r3 = r9.c
            if (r3 != 0) goto L_0x0117
            goto L_0x036e
        L_0x0117:
            android.support.constraint.solver.widgets.ResolutionAnchor r3 = r13.c
            int r3 = r3.i
            r0 = 1
            if (r3 != r0) goto L_0x036c
            android.support.constraint.solver.widgets.ResolutionAnchor r3 = r9.c
            int r3 = r3.i
            if (r3 == r0) goto L_0x0126
            goto L_0x036c
        L_0x0126:
            if (r8 <= 0) goto L_0x012c
            if (r8 == r10) goto L_0x012c
            r0 = 0
            return r0
        L_0x012c:
            if (r7 != 0) goto L_0x0135
            if (r11 != 0) goto L_0x0135
            if (r12 == 0) goto L_0x0133
            goto L_0x0135
        L_0x0133:
            r0 = 0
            goto L_0x014e
        L_0x0135:
            if (r5 == 0) goto L_0x0141
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r5.E
            r0 = r0[r25]
            int r0 = r0.b()
            float r0 = (float) r0
            goto L_0x0142
        L_0x0141:
            r0 = 0
        L_0x0142:
            if (r6 == 0) goto L_0x014e
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r6.E
            r3 = r3[r18]
            int r3 = r3.b()
            float r3 = (float) r3
            float r0 = r0 + r3
        L_0x014e:
            android.support.constraint.solver.widgets.ResolutionAnchor r3 = r13.c
            float r3 = r3.f
            android.support.constraint.solver.widgets.ResolutionAnchor r6 = r9.c
            float r6 = r6.f
            int r9 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r9 >= 0) goto L_0x015d
            float r6 = r6 - r3
            float r6 = r6 - r15
            goto L_0x0160
        L_0x015d:
            float r6 = r3 - r6
            float r6 = r6 - r15
        L_0x0160:
            r9 = -1082130432(0xffffffffbf800000, float:-1.0)
            r19 = 1
            if (r8 <= 0) goto L_0x020b
            if (r8 != r10) goto L_0x020b
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r14.H
            if (r0 == 0) goto L_0x0178
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r14.H
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r0.G
            r0 = r0[r1]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r5) goto L_0x0178
            r0 = 0
            return r0
        L_0x0178:
            float r6 = r6 + r15
            float r6 = r6 - r17
            r0 = r21
        L_0x017d:
            if (r0 == 0) goto L_0x0209
            android.support.constraint.solver.Metrics r5 = android.support.constraint.solver.LinearSystem.h
            if (r5 == 0) goto L_0x019b
            android.support.constraint.solver.Metrics r5 = android.support.constraint.solver.LinearSystem.h
            long r10 = r5.B
            long r10 = r10 - r19
            r5.B = r10
            android.support.constraint.solver.Metrics r5 = android.support.constraint.solver.LinearSystem.h
            long r10 = r5.s
            long r10 = r10 + r19
            r5.s = r10
            android.support.constraint.solver.Metrics r5 = android.support.constraint.solver.LinearSystem.h
            long r10 = r5.y
            long r10 = r10 + r19
            r5.y = r10
        L_0x019b:
            android.support.constraint.solver.widgets.ConstraintWidget[] r5 = r0.ap
            r5 = r5[r1]
            if (r5 != 0) goto L_0x01a7
            if (r0 != r4) goto L_0x01a4
            goto L_0x01a7
        L_0x01a4:
            r14 = r23
            goto L_0x0206
        L_0x01a7:
            float r7 = (float) r8
            float r7 = r6 / r7
            r10 = 0
            int r11 = (r2 > r10 ? 1 : (r2 == r10 ? 0 : -1))
            if (r11 <= 0) goto L_0x01c0
            float[] r7 = r0.an
            r7 = r7[r1]
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 != 0) goto L_0x01b9
            r7 = 0
            goto L_0x01c0
        L_0x01b9:
            float[] r7 = r0.an
            r7 = r7[r1]
            float r7 = r7 * r6
            float r7 = r7 / r2
        L_0x01c0:
            int r10 = r0.ac
            r11 = 8
            if (r10 != r11) goto L_0x01c7
            r7 = 0
        L_0x01c7:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r10 = r0.E
            r10 = r10[r25]
            int r10 = r10.b()
            float r10 = (float) r10
            float r3 = r3 + r10
            android.support.constraint.solver.widgets.ConstraintAnchor[] r10 = r0.E
            r10 = r10[r25]
            android.support.constraint.solver.widgets.ResolutionAnchor r10 = r10.a
            android.support.constraint.solver.widgets.ResolutionAnchor r11 = r13.e
            r10.a(r11, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r10 = r0.E
            r10 = r10[r18]
            android.support.constraint.solver.widgets.ResolutionAnchor r10 = r10.a
            android.support.constraint.solver.widgets.ResolutionAnchor r11 = r13.e
            float r3 = r3 + r7
            r10.a(r11, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r0.E
            r7 = r7[r25]
            android.support.constraint.solver.widgets.ResolutionAnchor r7 = r7.a
            r14 = r23
            r7.a(r14)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r0.E
            r7 = r7[r18]
            android.support.constraint.solver.widgets.ResolutionAnchor r7 = r7.a
            r7.a(r14)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r0.E
            r0 = r0[r18]
            int r0 = r0.b()
            float r0 = (float) r0
            float r3 = r3 + r0
        L_0x0206:
            r0 = r5
            goto L_0x017d
        L_0x0209:
            r0 = 1
            return r0
        L_0x020b:
            r14 = r23
            r2 = 0
            int r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0215
            r7 = 1
            r11 = 0
            r12 = 0
        L_0x0215:
            if (r7 == 0) goto L_0x029b
            float r6 = r6 - r0
            if (r1 != 0) goto L_0x021f
            r2 = r21
            float r9 = r2.Y
            goto L_0x0226
        L_0x021f:
            r2 = r21
            r0 = 1
            if (r1 != r0) goto L_0x0226
            float r9 = r2.Z
        L_0x0226:
            float r6 = r6 * r9
            float r3 = r3 + r6
        L_0x0229:
            if (r2 == 0) goto L_0x02a2
            android.support.constraint.solver.Metrics r0 = android.support.constraint.solver.LinearSystem.h
            if (r0 == 0) goto L_0x0247
            android.support.constraint.solver.Metrics r0 = android.support.constraint.solver.LinearSystem.h
            long r5 = r0.B
            long r5 = r5 - r19
            r0.B = r5
            android.support.constraint.solver.Metrics r0 = android.support.constraint.solver.LinearSystem.h
            long r5 = r0.s
            long r5 = r5 + r19
            r0.s = r5
            android.support.constraint.solver.Metrics r0 = android.support.constraint.solver.LinearSystem.h
            long r5 = r0.y
            long r5 = r5 + r19
            r0.y = r5
        L_0x0247:
            android.support.constraint.solver.widgets.ConstraintWidget[] r0 = r2.ap
            r0 = r0[r1]
            if (r0 != 0) goto L_0x024f
            if (r2 != r4) goto L_0x0299
        L_0x024f:
            if (r1 != 0) goto L_0x0257
            int r5 = r2.n()
            float r5 = (float) r5
            goto L_0x025c
        L_0x0257:
            int r5 = r2.o()
            float r5 = (float) r5
        L_0x025c:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r2.E
            r6 = r6[r25]
            int r6 = r6.b()
            float r6 = (float) r6
            float r3 = r3 + r6
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r2.E
            r6 = r6[r25]
            android.support.constraint.solver.widgets.ResolutionAnchor r6 = r6.a
            android.support.constraint.solver.widgets.ResolutionAnchor r7 = r13.e
            r6.a(r7, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r2.E
            r6 = r6[r18]
            android.support.constraint.solver.widgets.ResolutionAnchor r6 = r6.a
            android.support.constraint.solver.widgets.ResolutionAnchor r7 = r13.e
            float r3 = r3 + r5
            r6.a(r7, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r5 = r2.E
            r5 = r5[r25]
            android.support.constraint.solver.widgets.ResolutionAnchor r5 = r5.a
            r5.a(r14)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r5 = r2.E
            r5 = r5[r18]
            android.support.constraint.solver.widgets.ResolutionAnchor r5 = r5.a
            r5.a(r14)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r2.E
            r2 = r2[r18]
            int r2 = r2.b()
            float r2 = (float) r2
            float r3 = r3 + r2
        L_0x0299:
            r2 = r0
            goto L_0x0229
        L_0x029b:
            r2 = r21
            if (r11 != 0) goto L_0x02a5
            if (r12 == 0) goto L_0x02a2
            goto L_0x02a5
        L_0x02a2:
            r0 = 1
            goto L_0x036b
        L_0x02a5:
            if (r11 == 0) goto L_0x02a9
            float r6 = r6 - r0
            goto L_0x02ac
        L_0x02a9:
            if (r12 == 0) goto L_0x02ac
            float r6 = r6 - r0
        L_0x02ac:
            int r0 = r10 + 1
            float r0 = (float) r0
            float r0 = r6 / r0
            if (r12 == 0) goto L_0x02c0
            r7 = 1
            if (r10 <= r7) goto L_0x02bc
            int r0 = r10 + -1
            float r0 = (float) r0
            float r0 = r6 / r0
            goto L_0x02c0
        L_0x02bc:
            r0 = 1073741824(0x40000000, float:2.0)
            float r0 = r6 / r0
        L_0x02c0:
            int r6 = r2.ac
            r7 = 8
            if (r6 == r7) goto L_0x02c9
            float r6 = r3 + r0
            goto L_0x02ca
        L_0x02c9:
            r6 = r3
        L_0x02ca:
            if (r12 == 0) goto L_0x02d9
            r7 = 1
            if (r10 <= r7) goto L_0x02d9
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r5.E
            r6 = r6[r25]
            int r6 = r6.b()
            float r6 = (float) r6
            float r6 = r6 + r3
        L_0x02d9:
            if (r11 == 0) goto L_0x02e7
            if (r5 == 0) goto L_0x02e7
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r5.E
            r3 = r3[r25]
            int r3 = r3.b()
            float r3 = (float) r3
            float r6 = r6 + r3
        L_0x02e7:
            if (r2 == 0) goto L_0x02a2
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h
            if (r3 == 0) goto L_0x0305
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h
            long r7 = r3.B
            long r7 = r7 - r19
            r3.B = r7
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h
            long r7 = r3.s
            long r7 = r7 + r19
            r3.s = r7
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h
            long r7 = r3.y
            long r7 = r7 + r19
            r3.y = r7
        L_0x0305:
            android.support.constraint.solver.widgets.ConstraintWidget[] r3 = r2.ap
            r3 = r3[r1]
            if (r3 != 0) goto L_0x0311
            if (r2 != r4) goto L_0x030e
            goto L_0x0311
        L_0x030e:
            r7 = 8
            goto L_0x0368
        L_0x0311:
            if (r1 != 0) goto L_0x0319
            int r7 = r2.n()
            float r7 = (float) r7
            goto L_0x031e
        L_0x0319:
            int r7 = r2.o()
            float r7 = (float) r7
        L_0x031e:
            if (r2 == r5) goto L_0x032a
            android.support.constraint.solver.widgets.ConstraintAnchor[] r8 = r2.E
            r8 = r8[r25]
            int r8 = r8.b()
            float r8 = (float) r8
            float r6 = r6 + r8
        L_0x032a:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r8 = r2.E
            r8 = r8[r25]
            android.support.constraint.solver.widgets.ResolutionAnchor r8 = r8.a
            android.support.constraint.solver.widgets.ResolutionAnchor r9 = r13.e
            r8.a(r9, r6)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r8 = r2.E
            r8 = r8[r18]
            android.support.constraint.solver.widgets.ResolutionAnchor r8 = r8.a
            android.support.constraint.solver.widgets.ResolutionAnchor r9 = r13.e
            float r10 = r6 + r7
            r8.a(r9, r10)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r8 = r2.E
            r8 = r8[r25]
            android.support.constraint.solver.widgets.ResolutionAnchor r8 = r8.a
            r8.a(r14)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r8 = r2.E
            r8 = r8[r18]
            android.support.constraint.solver.widgets.ResolutionAnchor r8 = r8.a
            r8.a(r14)
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r2.E
            r2 = r2[r18]
            int r2 = r2.b()
            float r2 = (float) r2
            float r7 = r7 + r2
            float r6 = r6 + r7
            if (r3 == 0) goto L_0x030e
            int r2 = r3.ac
            r7 = 8
            if (r2 == r7) goto L_0x0368
            float r6 = r6 + r0
        L_0x0368:
            r2 = r3
            goto L_0x02e7
        L_0x036b:
            return r0
        L_0x036c:
            r0 = 0
            return r0
        L_0x036e:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.Optimizer.a(android.support.constraint.solver.LinearSystem, int, int, android.support.constraint.solver.widgets.ChainHead):boolean");
    }

    static void a(ConstraintWidget constraintWidget, int i, int i2) {
        int i3 = i * 2;
        int i4 = i3 + 1;
        constraintWidget.E[i3].a.e = constraintWidget.H.w.a;
        constraintWidget.E[i3].a.f = (float) i2;
        constraintWidget.E[i3].a.i = 1;
        constraintWidget.E[i4].a.e = constraintWidget.E[i3].a;
        constraintWidget.E[i4].a.f = (float) constraintWidget.b(i);
        constraintWidget.E[i4].a.i = 1;
    }
}
