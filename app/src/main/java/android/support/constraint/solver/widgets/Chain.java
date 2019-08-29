package android.support.constraint.solver.widgets;

class Chain {
    Chain() {
    }

    /* JADX WARNING: type inference failed for: r4v2 */
    /* JADX WARNING: type inference failed for: r25v1 */
    /* JADX WARNING: type inference failed for: r4v7, types: [android.support.constraint.solver.SolverVariable] */
    /* JADX WARNING: type inference failed for: r25v2 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v10, types: [android.support.constraint.solver.SolverVariable] */
    /* JADX WARNING: type inference failed for: r4v24 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x042a  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x0431  */
    /* JADX WARNING: Removed duplicated region for block: B:223:0x048c  */
    /* JADX WARNING: Removed duplicated region for block: B:224:0x048e  */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(android.support.constraint.solver.widgets.ConstraintWidgetContainer r48, android.support.constraint.solver.LinearSystem r49, int r50) {
        /*
            r0 = r48
            r10 = r49
            r11 = r50
            if (r11 != 0) goto L_0x0010
            int r1 = r0.aw
            android.support.constraint.solver.widgets.ChainHead[] r2 = r0.az
            r9 = r1
            r14 = r2
            r15 = 0
            goto L_0x0017
        L_0x0010:
            int r1 = r0.ax
            android.support.constraint.solver.widgets.ChainHead[] r2 = r0.ay
            r9 = r1
            r14 = r2
            r15 = 2
        L_0x0017:
            r8 = 0
        L_0x0018:
            if (r8 >= r9) goto L_0x063a
            r1 = r14[r8]
            boolean r2 = r1.o
            if (r2 != 0) goto L_0x0023
            r1.a()
        L_0x0023:
            r2 = 1
            r1.o = r2
            r7 = 4
            boolean r3 = r0.j(r7)
            if (r3 == 0) goto L_0x003e
            boolean r3 = android.support.constraint.solver.widgets.Optimizer.a(r10, r11, r15, r1)
            if (r3 != 0) goto L_0x0034
            goto L_0x003e
        L_0x0034:
            r40 = r8
            r17 = r9
            r31 = r14
            r16 = 0
            goto L_0x0630
        L_0x003e:
            android.support.constraint.solver.widgets.ConstraintWidget r6 = r1.a
            android.support.constraint.solver.widgets.ConstraintWidget r5 = r1.c
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r1.b
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r1.d
            android.support.constraint.solver.widgets.ConstraintWidget r7 = r1.e
            float r13 = r1.k
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r12 = r0.G
            r12 = r12[r11]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r12 != r2) goto L_0x0054
            r2 = 1
            goto L_0x0055
        L_0x0054:
            r2 = 0
        L_0x0055:
            if (r11 != 0) goto L_0x007d
            int r12 = r7.aj
            if (r12 != 0) goto L_0x005f
            r20 = r8
            r12 = 1
            goto L_0x0062
        L_0x005f:
            r20 = r8
            r12 = 0
        L_0x0062:
            int r8 = r7.aj
            r21 = r9
            r9 = 1
            if (r8 != r9) goto L_0x006b
            r8 = 1
            goto L_0x006c
        L_0x006b:
            r8 = 0
        L_0x006c:
            int r9 = r7.aj
            r22 = r8
            r8 = 2
            if (r9 != r8) goto L_0x0075
            r8 = 1
            goto L_0x0076
        L_0x0075:
            r8 = 0
        L_0x0076:
            r9 = r6
            r18 = r8
            r23 = r12
            r8 = 0
            goto L_0x00a0
        L_0x007d:
            r20 = r8
            r21 = r9
            int r8 = r7.ak
            if (r8 != 0) goto L_0x0087
            r12 = 1
            goto L_0x0088
        L_0x0087:
            r12 = 0
        L_0x0088:
            int r8 = r7.ak
            r9 = 1
            if (r8 != r9) goto L_0x008f
            r8 = 1
            goto L_0x0090
        L_0x008f:
            r8 = 0
        L_0x0090:
            int r9 = r7.ak
            r23 = r12
            r12 = 2
            if (r9 != r12) goto L_0x0099
            r9 = 1
            goto L_0x009a
        L_0x0099:
            r9 = 0
        L_0x009a:
            r22 = r8
            r18 = r9
            r8 = 0
            r9 = r6
        L_0x00a0:
            r25 = 0
            if (r8 != 0) goto L_0x0186
            android.support.constraint.solver.widgets.ConstraintAnchor[] r12 = r9.E
            r12 = r12[r15]
            if (r2 != 0) goto L_0x00b0
            if (r18 == 0) goto L_0x00ad
            goto L_0x00b0
        L_0x00ad:
            r27 = 4
            goto L_0x00b2
        L_0x00b0:
            r27 = 1
        L_0x00b2:
            int r28 = r12.b()
            r29 = r8
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r12.d
            if (r8 == 0) goto L_0x00c6
            if (r9 == r6) goto L_0x00c6
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r12.d
            int r8 = r8.b()
            int r28 = r28 + r8
        L_0x00c6:
            r8 = r28
            if (r18 == 0) goto L_0x00d4
            if (r9 == r6) goto L_0x00d4
            if (r9 == r4) goto L_0x00d4
            r30 = r13
            r31 = r14
            r13 = 6
            goto L_0x00e4
        L_0x00d4:
            if (r23 == 0) goto L_0x00de
            if (r2 == 0) goto L_0x00de
            r30 = r13
            r31 = r14
            r13 = 4
            goto L_0x00e4
        L_0x00de:
            r30 = r13
            r31 = r14
            r13 = r27
        L_0x00e4:
            android.support.constraint.solver.widgets.ConstraintAnchor r14 = r12.d
            if (r14 == 0) goto L_0x0111
            if (r9 != r4) goto L_0x00f9
            android.support.constraint.solver.SolverVariable r14 = r12.i
            r32 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r12.d
            android.support.constraint.solver.SolverVariable r7 = r7.i
            r33 = r6
            r6 = 5
            r10.a(r14, r7, r8, r6)
            goto L_0x0107
        L_0x00f9:
            r33 = r6
            r32 = r7
            android.support.constraint.solver.SolverVariable r6 = r12.i
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r12.d
            android.support.constraint.solver.SolverVariable r7 = r7.i
            r14 = 6
            r10.a(r6, r7, r8, r14)
        L_0x0107:
            android.support.constraint.solver.SolverVariable r6 = r12.i
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r12.d
            android.support.constraint.solver.SolverVariable r7 = r7.i
            r10.c(r6, r7, r8, r13)
            goto L_0x0115
        L_0x0111:
            r33 = r6
            r32 = r7
        L_0x0115:
            if (r2 == 0) goto L_0x014a
            int r6 = r9.ac
            r7 = 8
            if (r6 == r7) goto L_0x0139
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r6 = r9.G
            r6 = r6[r11]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r6 != r7) goto L_0x0139
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r9.E
            int r7 = r15 + 1
            r6 = r6[r7]
            android.support.constraint.solver.SolverVariable r6 = r6.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r9.E
            r7 = r7[r15]
            android.support.constraint.solver.SolverVariable r7 = r7.i
            r8 = 0
            r12 = 5
            r10.a(r6, r7, r8, r12)
            goto L_0x013a
        L_0x0139:
            r8 = 0
        L_0x013a:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r9.E
            r6 = r6[r15]
            android.support.constraint.solver.SolverVariable r6 = r6.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r0.E
            r7 = r7[r15]
            android.support.constraint.solver.SolverVariable r7 = r7.i
            r12 = 6
            r10.a(r6, r7, r8, r12)
        L_0x014a:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r9.E
            int r7 = r15 + 1
            r6 = r6[r7]
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.d
            if (r6 == 0) goto L_0x016b
            android.support.constraint.solver.widgets.ConstraintWidget r6 = r6.b
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r6.E
            r7 = r7[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.d
            if (r7 == 0) goto L_0x016b
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r6.E
            r7 = r7[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.d
            android.support.constraint.solver.widgets.ConstraintWidget r7 = r7.b
            if (r7 == r9) goto L_0x0169
            goto L_0x016b
        L_0x0169:
            r25 = r6
        L_0x016b:
            if (r25 == 0) goto L_0x017b
            r9 = r25
            r8 = r29
            r13 = r30
            r14 = r31
            r7 = r32
            r6 = r33
            goto L_0x00a0
        L_0x017b:
            r13 = r30
            r14 = r31
            r7 = r32
            r6 = r33
            r8 = 1
            goto L_0x00a0
        L_0x0186:
            r33 = r6
            r32 = r7
            r30 = r13
            r31 = r14
            if (r3 == 0) goto L_0x01b1
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r5.E
            int r7 = r15 + 1
            r6 = r6[r7]
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.d
            if (r6 == 0) goto L_0x01b1
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r3.E
            r6 = r6[r7]
            android.support.constraint.solver.SolverVariable r8 = r6.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r9 = r5.E
            r7 = r9[r7]
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.d
            android.support.constraint.solver.SolverVariable r7 = r7.i
            int r6 = r6.b()
            int r6 = -r6
            r9 = 5
            r10.b(r8, r7, r6, r9)
        L_0x01b1:
            if (r2 == 0) goto L_0x01cd
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r0.E
            int r6 = r15 + 1
            r2 = r2[r6]
            android.support.constraint.solver.SolverVariable r2 = r2.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r5.E
            r7 = r7[r6]
            android.support.constraint.solver.SolverVariable r7 = r7.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r8 = r5.E
            r6 = r8[r6]
            int r6 = r6.b()
            r8 = 6
            r10.a(r2, r7, r6, r8)
        L_0x01cd:
            java.util.ArrayList<android.support.constraint.solver.widgets.ConstraintWidget> r2 = r1.h
            if (r2 == 0) goto L_0x02fc
            int r6 = r2.size()
            r9 = 1
            if (r6 <= r9) goto L_0x02fc
            boolean r7 = r1.l
            if (r7 == 0) goto L_0x01e4
            boolean r7 = r1.n
            if (r7 != 0) goto L_0x01e4
            int r7 = r1.j
            float r13 = (float) r7
            goto L_0x01e6
        L_0x01e4:
            r13 = r30
        L_0x01e6:
            r7 = 0
            r12 = r25
            r8 = 0
            r14 = 0
        L_0x01eb:
            if (r8 >= r6) goto L_0x02fc
            java.lang.Object r19 = r2.get(r8)
            r9 = r19
            android.support.constraint.solver.widgets.ConstraintWidget r9 = (android.support.constraint.solver.widgets.ConstraintWidget) r9
            float[] r0 = r9.an
            r0 = r0[r11]
            int r19 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r19 >= 0) goto L_0x021e
            boolean r0 = r1.n
            if (r0 == 0) goto L_0x0218
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r9.E
            int r7 = r15 + 1
            r0 = r0[r7]
            android.support.constraint.solver.SolverVariable r0 = r0.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r9.E
            r7 = r7[r15]
            android.support.constraint.solver.SolverVariable r7 = r7.i
            r35 = r6
            r6 = 4
            r9 = 0
            r10.c(r0, r7, r9, r6)
            r6 = 6
            goto L_0x023a
        L_0x0218:
            r35 = r6
            r6 = 4
            r0 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0221
        L_0x021e:
            r35 = r6
            r6 = 4
        L_0x0221:
            r16 = 0
            int r19 = (r0 > r16 ? 1 : (r0 == r16 ? 0 : -1))
            if (r19 != 0) goto L_0x0244
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r9.E
            int r7 = r15 + 1
            r0 = r0[r7]
            android.support.constraint.solver.SolverVariable r0 = r0.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r9.E
            r7 = r7[r15]
            android.support.constraint.solver.SolverVariable r7 = r7.i
            r6 = 6
            r9 = 0
            r10.c(r0, r7, r9, r6)
        L_0x023a:
            r38 = r1
            r36 = r2
            r16 = 0
            r17 = 0
            goto L_0x02ee
        L_0x0244:
            r6 = 6
            r16 = 0
            if (r12 == 0) goto L_0x02e0
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r12.E
            r6 = r6[r15]
            android.support.constraint.solver.SolverVariable r6 = r6.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r12 = r12.E
            int r17 = r15 + 1
            r12 = r12[r17]
            android.support.constraint.solver.SolverVariable r12 = r12.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r9.E
            r7 = r7[r15]
            android.support.constraint.solver.SolverVariable r7 = r7.i
            r36 = r2
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r9.E
            r2 = r2[r17]
            android.support.constraint.solver.SolverVariable r2 = r2.i
            r37 = r9
            android.support.constraint.solver.ArrayRow r9 = r49.c()
            r38 = r1
            r1 = 0
            r9.b = r1
            int r17 = (r13 > r1 ? 1 : (r13 == r1 ? 0 : -1))
            r1 = -1082130432(0xffffffffbf800000, float:-1.0)
            if (r17 == 0) goto L_0x02c0
            int r17 = (r14 > r0 ? 1 : (r14 == r0 ? 0 : -1))
            if (r17 != 0) goto L_0x027b
            goto L_0x02c0
        L_0x027b:
            r17 = 0
            int r26 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            if (r26 != 0) goto L_0x0290
            android.support.constraint.solver.ArrayLinkedVariables r2 = r9.d
            r7 = 1065353216(0x3f800000, float:1.0)
            r2.a(r6, r7)
            android.support.constraint.solver.ArrayLinkedVariables r2 = r9.d
            r2.a(r12, r1)
        L_0x028d:
            r39 = r0
            goto L_0x02dc
        L_0x0290:
            r1 = 1065353216(0x3f800000, float:1.0)
            if (r19 != 0) goto L_0x02a1
            android.support.constraint.solver.ArrayLinkedVariables r6 = r9.d
            r6.a(r7, r1)
            android.support.constraint.solver.ArrayLinkedVariables r1 = r9.d
            r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            r1.a(r2, r6)
            goto L_0x028d
        L_0x02a1:
            float r14 = r14 / r13
            float r19 = r0 / r13
            float r14 = r14 / r19
            r39 = r0
            android.support.constraint.solver.ArrayLinkedVariables r0 = r9.d
            r0.a(r6, r1)
            android.support.constraint.solver.ArrayLinkedVariables r0 = r9.d
            r1 = -1082130432(0xffffffffbf800000, float:-1.0)
            r0.a(r12, r1)
            android.support.constraint.solver.ArrayLinkedVariables r0 = r9.d
            r0.a(r2, r14)
            android.support.constraint.solver.ArrayLinkedVariables r0 = r9.d
            float r1 = -r14
            r0.a(r7, r1)
            goto L_0x02dc
        L_0x02c0:
            r39 = r0
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            r1 = 1065353216(0x3f800000, float:1.0)
            r17 = 0
            android.support.constraint.solver.ArrayLinkedVariables r14 = r9.d
            r14.a(r6, r1)
            android.support.constraint.solver.ArrayLinkedVariables r6 = r9.d
            r6.a(r12, r0)
            android.support.constraint.solver.ArrayLinkedVariables r6 = r9.d
            r6.a(r2, r1)
            android.support.constraint.solver.ArrayLinkedVariables r1 = r9.d
            r1.a(r7, r0)
        L_0x02dc:
            r10.a(r9)
            goto L_0x02ea
        L_0x02e0:
            r39 = r0
            r38 = r1
            r36 = r2
            r37 = r9
            r17 = 0
        L_0x02ea:
            r12 = r37
            r14 = r39
        L_0x02ee:
            int r8 = r8 + 1
            r6 = r35
            r2 = r36
            r1 = r38
            r0 = r48
            r7 = 0
            r9 = 1
            goto L_0x01eb
        L_0x02fc:
            r38 = r1
            r16 = 0
            if (r4 == 0) goto L_0x037d
            if (r4 == r3) goto L_0x0306
            if (r18 == 0) goto L_0x037d
        L_0x0306:
            r0 = r33
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r0.E
            r1 = r1[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r5.E
            int r6 = r15 + 1
            r2 = r2[r6]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r0.E
            r7 = r7[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.d
            if (r7 == 0) goto L_0x0323
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r0.E
            r0 = r0[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            android.support.constraint.solver.SolverVariable r0 = r0.i
            goto L_0x0325
        L_0x0323:
            r0 = r25
        L_0x0325:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r5.E
            r7 = r7[r6]
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.d
            if (r7 == 0) goto L_0x0336
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r5.E
            r7 = r7[r6]
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r7.d
            android.support.constraint.solver.SolverVariable r7 = r7.i
            goto L_0x0338
        L_0x0336:
            r7 = r25
        L_0x0338:
            if (r4 != r3) goto L_0x0342
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r4.E
            r1 = r1[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r4.E
            r2 = r2[r6]
        L_0x0342:
            if (r0 == 0) goto L_0x0373
            if (r7 == 0) goto L_0x0373
            if (r11 != 0) goto L_0x034d
            r6 = r32
            float r6 = r6.Y
            goto L_0x0351
        L_0x034d:
            r6 = r32
            float r6 = r6.Z
        L_0x0351:
            int r8 = r1.b()
            int r9 = r2.b()
            android.support.constraint.solver.SolverVariable r12 = r1.i
            android.support.constraint.solver.SolverVariable r13 = r2.i
            r14 = 5
            r1 = r10
            r2 = r12
            r12 = r3
            r3 = r0
            r0 = r4
            r4 = r8
            r8 = r5
            r5 = r6
            r6 = r7
            r7 = r13
            r13 = r8
            r40 = r20
            r8 = r9
            r17 = r21
            r9 = r14
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x037a
        L_0x0373:
            r12 = r3
            r0 = r4
            r13 = r5
            r40 = r20
            r17 = r21
        L_0x037a:
            r14 = r0
            goto L_0x05ce
        L_0x037d:
            r12 = r3
            r14 = r4
            r13 = r5
            r40 = r20
            r17 = r21
            r0 = r33
            if (r23 == 0) goto L_0x0496
            if (r14 == 0) goto L_0x0496
            r1 = r38
            int r2 = r1.j
            if (r2 <= 0) goto L_0x0399
            int r2 = r1.i
            int r1 = r1.j
            if (r2 != r1) goto L_0x0399
            r34 = 1
            goto L_0x039b
        L_0x0399:
            r34 = 0
        L_0x039b:
            r8 = r14
            r9 = r8
        L_0x039d:
            if (r9 == 0) goto L_0x05ce
            android.support.constraint.solver.widgets.ConstraintWidget[] r1 = r9.ap
            r1 = r1[r11]
            r7 = r1
        L_0x03a4:
            if (r7 == 0) goto L_0x03b1
            int r1 = r7.ac
            r2 = 8
            if (r1 != r2) goto L_0x03b1
            android.support.constraint.solver.widgets.ConstraintWidget[] r1 = r7.ap
            r7 = r1[r11]
            goto L_0x03a4
        L_0x03b1:
            if (r7 != 0) goto L_0x03c3
            if (r9 != r12) goto L_0x03b6
            goto L_0x03c3
        L_0x03b6:
            r43 = r0
            r44 = r7
            r24 = r8
            r0 = r9
        L_0x03bd:
            r20 = 6
            r21 = 4
            goto L_0x0486
        L_0x03c3:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r9.E
            r1 = r1[r15]
            android.support.constraint.solver.SolverVariable r2 = r1.i
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r1.d
            if (r3 == 0) goto L_0x03d2
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r1.d
            android.support.constraint.solver.SolverVariable r3 = r3.i
            goto L_0x03d4
        L_0x03d2:
            r3 = r25
        L_0x03d4:
            if (r8 == r9) goto L_0x03df
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r8.E
            int r4 = r15 + 1
            r3 = r3[r4]
            android.support.constraint.solver.SolverVariable r3 = r3.i
            goto L_0x03f6
        L_0x03df:
            if (r9 != r14) goto L_0x03f6
            if (r8 != r9) goto L_0x03f6
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r0.E
            r3 = r3[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            if (r3 == 0) goto L_0x03f4
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r0.E
            r3 = r3[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            android.support.constraint.solver.SolverVariable r3 = r3.i
            goto L_0x03f6
        L_0x03f4:
            r3 = r25
        L_0x03f6:
            int r1 = r1.b()
            android.support.constraint.solver.widgets.ConstraintAnchor[] r4 = r9.E
            int r5 = r15 + 1
            r4 = r4[r5]
            int r4 = r4.b()
            if (r7 == 0) goto L_0x0411
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r7.E
            r6 = r6[r15]
            r41 = r0
            android.support.constraint.solver.SolverVariable r0 = r6.i
        L_0x040e:
            r42 = r7
            goto L_0x0422
        L_0x0411:
            r41 = r0
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r13.E
            r0 = r0[r5]
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r0.d
            if (r6 == 0) goto L_0x041e
            android.support.constraint.solver.SolverVariable r0 = r6.i
            goto L_0x040e
        L_0x041e:
            r42 = r7
            r0 = r25
        L_0x0422:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r7 = r9.E
            r7 = r7[r5]
            android.support.constraint.solver.SolverVariable r7 = r7.i
            if (r6 == 0) goto L_0x042f
            int r6 = r6.b()
            int r4 = r4 + r6
        L_0x042f:
            if (r8 == 0) goto L_0x043a
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r8.E
            r6 = r6[r5]
            int r6 = r6.b()
            int r1 = r1 + r6
        L_0x043a:
            if (r2 == 0) goto L_0x047d
            if (r3 == 0) goto L_0x047d
            if (r0 == 0) goto L_0x047d
            if (r7 == 0) goto L_0x047d
            if (r9 != r14) goto L_0x044c
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r14.E
            r1 = r1[r15]
            int r1 = r1.b()
        L_0x044c:
            r6 = r1
            if (r9 != r12) goto L_0x045a
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r12.E
            r1 = r1[r5]
            int r1 = r1.b()
            r18 = r1
            goto L_0x045c
        L_0x045a:
            r18 = r4
        L_0x045c:
            if (r34 == 0) goto L_0x0461
            r19 = 6
            goto L_0x0463
        L_0x0461:
            r19 = 4
        L_0x0463:
            r5 = 1056964608(0x3f000000, float:0.5)
            r1 = r10
            r4 = r6
            r43 = r41
            r20 = 6
            r21 = 4
            r6 = r0
            r0 = r42
            r24 = r8
            r8 = r18
            r44 = r0
            r0 = r9
            r9 = r19
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0486
        L_0x047d:
            r24 = r8
            r0 = r9
            r43 = r41
            r44 = r42
            goto L_0x03bd
        L_0x0486:
            int r1 = r0.ac
            r2 = 8
            if (r1 == r2) goto L_0x048e
            r8 = r0
            goto L_0x0490
        L_0x048e:
            r8 = r24
        L_0x0490:
            r0 = r43
            r9 = r44
            goto L_0x039d
        L_0x0496:
            r43 = r0
            r1 = r38
            r20 = 6
            r21 = 4
            if (r22 == 0) goto L_0x05ce
            if (r14 == 0) goto L_0x05ce
            int r0 = r1.j
            if (r0 <= 0) goto L_0x04af
            int r0 = r1.i
            int r1 = r1.j
            if (r0 != r1) goto L_0x04af
            r34 = 1
            goto L_0x04b1
        L_0x04af:
            r34 = 0
        L_0x04b1:
            r0 = r14
            r9 = r0
        L_0x04b3:
            if (r0 == 0) goto L_0x056d
            android.support.constraint.solver.widgets.ConstraintWidget[] r1 = r0.ap
            r1 = r1[r11]
        L_0x04b9:
            if (r1 == 0) goto L_0x04c6
            int r2 = r1.ac
            r3 = 8
            if (r2 != r3) goto L_0x04c6
            android.support.constraint.solver.widgets.ConstraintWidget[] r1 = r1.ap
            r1 = r1[r11]
            goto L_0x04b9
        L_0x04c6:
            if (r0 == r14) goto L_0x055e
            if (r0 == r12) goto L_0x055e
            if (r1 == 0) goto L_0x055e
            if (r1 != r12) goto L_0x04d1
            r8 = r25
            goto L_0x04d2
        L_0x04d1:
            r8 = r1
        L_0x04d2:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r0.E
            r1 = r1[r15]
            android.support.constraint.solver.SolverVariable r2 = r1.i
            android.support.constraint.solver.widgets.ConstraintAnchor[] r3 = r9.E
            int r4 = r15 + 1
            r3 = r3[r4]
            android.support.constraint.solver.SolverVariable r3 = r3.i
            int r1 = r1.b()
            android.support.constraint.solver.widgets.ConstraintAnchor[] r5 = r0.E
            r5 = r5[r4]
            int r5 = r5.b()
            if (r8 == 0) goto L_0x0506
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r8.E
            r6 = r6[r15]
            android.support.constraint.solver.SolverVariable r7 = r6.i
            r45 = r7
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r6.d
            if (r7 == 0) goto L_0x04ff
            android.support.constraint.solver.widgets.ConstraintAnchor r7 = r6.d
            android.support.constraint.solver.SolverVariable r7 = r7.i
            goto L_0x0501
        L_0x04ff:
            r7 = r25
        L_0x0501:
            r18 = r7
            r7 = r45
            goto L_0x0521
        L_0x0506:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r0.E
            r6 = r6[r4]
            android.support.constraint.solver.widgets.ConstraintAnchor r6 = r6.d
            if (r6 == 0) goto L_0x0513
            android.support.constraint.solver.SolverVariable r7 = r6.i
            r46 = r6
            goto L_0x0517
        L_0x0513:
            r46 = r6
            r7 = r25
        L_0x0517:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r6 = r0.E
            r6 = r6[r4]
            android.support.constraint.solver.SolverVariable r6 = r6.i
            r18 = r6
            r6 = r46
        L_0x0521:
            if (r6 == 0) goto L_0x0528
            int r6 = r6.b()
            int r5 = r5 + r6
        L_0x0528:
            r19 = r5
            if (r9 == 0) goto L_0x0535
            android.support.constraint.solver.widgets.ConstraintAnchor[] r5 = r9.E
            r4 = r5[r4]
            int r4 = r4.b()
            int r1 = r1 + r4
        L_0x0535:
            r4 = r1
            if (r34 == 0) goto L_0x053b
            r26 = 6
            goto L_0x053d
        L_0x053b:
            r26 = 4
        L_0x053d:
            if (r2 == 0) goto L_0x0557
            if (r3 == 0) goto L_0x0557
            if (r7 == 0) goto L_0x0557
            if (r18 == 0) goto L_0x0557
            r5 = 1056964608(0x3f000000, float:0.5)
            r1 = r10
            r6 = r7
            r7 = r18
            r18 = r8
            r8 = r19
            r19 = r9
            r9 = r26
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x055b
        L_0x0557:
            r18 = r8
            r19 = r9
        L_0x055b:
            r1 = r18
            goto L_0x0560
        L_0x055e:
            r19 = r9
        L_0x0560:
            int r2 = r0.ac
            r3 = 8
            if (r2 == r3) goto L_0x0568
            r9 = r0
            goto L_0x056a
        L_0x0568:
            r9 = r19
        L_0x056a:
            r0 = r1
            goto L_0x04b3
        L_0x056d:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r14.E
            r0 = r0[r15]
            r1 = r43
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r1.E
            r1 = r1[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor r1 = r1.d
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r12.E
            int r3 = r15 + 1
            r9 = r2[r3]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r2 = r13.E
            r2 = r2[r3]
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r2.d
            if (r1 == 0) goto L_0x05bb
            if (r14 == r12) goto L_0x0598
            android.support.constraint.solver.SolverVariable r2 = r0.i
            android.support.constraint.solver.SolverVariable r1 = r1.i
            int r0 = r0.b()
            r3 = 5
            r10.c(r2, r1, r0, r3)
            r1 = r8
            r0 = r9
            goto L_0x05bd
        L_0x0598:
            if (r8 == 0) goto L_0x05bb
            android.support.constraint.solver.SolverVariable r2 = r0.i
            android.support.constraint.solver.SolverVariable r3 = r1.i
            int r4 = r0.b()
            r5 = 1056964608(0x3f000000, float:0.5)
            android.support.constraint.solver.SolverVariable r6 = r9.i
            android.support.constraint.solver.SolverVariable r7 = r8.i
            int r0 = r9.b()
            r18 = 5
            r1 = r10
            r47 = r8
            r8 = r0
            r0 = r9
            r9 = r18
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9)
            r1 = r47
            goto L_0x05bd
        L_0x05bb:
            r0 = r9
            r1 = r8
        L_0x05bd:
            if (r1 == 0) goto L_0x05ce
            if (r14 == r12) goto L_0x05ce
            android.support.constraint.solver.SolverVariable r2 = r0.i
            android.support.constraint.solver.SolverVariable r1 = r1.i
            int r0 = r0.b()
            int r0 = -r0
            r3 = 5
            r10.c(r2, r1, r0, r3)
        L_0x05ce:
            if (r23 != 0) goto L_0x05d2
            if (r22 == 0) goto L_0x0630
        L_0x05d2:
            if (r14 == 0) goto L_0x0630
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r14.E
            r0 = r0[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r12.E
            int r2 = r15 + 1
            r1 = r1[r2]
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r0.d
            if (r3 == 0) goto L_0x05e7
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r0.d
            android.support.constraint.solver.SolverVariable r3 = r3.i
            goto L_0x05e9
        L_0x05e7:
            r3 = r25
        L_0x05e9:
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.d
            if (r4 == 0) goto L_0x05f2
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r1.d
            android.support.constraint.solver.SolverVariable r4 = r4.i
            goto L_0x05f4
        L_0x05f2:
            r4 = r25
        L_0x05f4:
            if (r13 == r12) goto L_0x0607
            android.support.constraint.solver.widgets.ConstraintAnchor[] r4 = r13.E
            r4 = r4[r2]
            android.support.constraint.solver.widgets.ConstraintAnchor r5 = r4.d
            if (r5 == 0) goto L_0x0604
            android.support.constraint.solver.widgets.ConstraintAnchor r4 = r4.d
            android.support.constraint.solver.SolverVariable r4 = r4.i
            r25 = r4
        L_0x0604:
            r6 = r25
            goto L_0x0608
        L_0x0607:
            r6 = r4
        L_0x0608:
            if (r14 != r12) goto L_0x0612
            android.support.constraint.solver.widgets.ConstraintAnchor[] r0 = r14.E
            r0 = r0[r15]
            android.support.constraint.solver.widgets.ConstraintAnchor[] r1 = r14.E
            r1 = r1[r2]
        L_0x0612:
            if (r3 == 0) goto L_0x0630
            if (r6 == 0) goto L_0x0630
            int r4 = r0.b()
            if (r12 != 0) goto L_0x061d
            r12 = r13
        L_0x061d:
            android.support.constraint.solver.widgets.ConstraintAnchor[] r5 = r12.E
            r2 = r5[r2]
            int r8 = r2.b()
            android.support.constraint.solver.SolverVariable r2 = r0.i
            r5 = 1056964608(0x3f000000, float:0.5)
            android.support.constraint.solver.SolverVariable r7 = r1.i
            r9 = 5
            r1 = r10
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9)
        L_0x0630:
            int r8 = r40 + 1
            r9 = r17
            r14 = r31
            r0 = r48
            goto L_0x0018
        L_0x063a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.Chain.a(android.support.constraint.solver.widgets.ConstraintWidgetContainer, android.support.constraint.solver.LinearSystem, int):void");
    }
}
