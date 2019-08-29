package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstraintWidgetContainer extends WidgetContainer {
    public boolean a = false;
    public List<ConstraintWidgetGroup> aA = new ArrayList();
    public boolean aB = false;
    public boolean aC = false;
    public boolean aD = false;
    public int aE = 0;
    public int aF = 0;
    public int aG = 7;
    public boolean aH = false;
    public boolean aI = false;
    public boolean aJ = false;
    int aK = 0;
    private Snapshot aM;
    int as;
    int at;
    int au;
    int av;
    int aw = 0;
    int ax = 0;
    ChainHead[] ay = new ChainHead[4];
    ChainHead[] az = new ChainHead[4];
    protected LinearSystem b = new LinearSystem();

    public boolean d() {
        return false;
    }

    public final boolean j(int i) {
        return (this.aG & i) == i;
    }

    public final void g() {
        this.b.b();
        this.as = 0;
        this.au = 0;
        this.at = 0;
        this.av = 0;
        this.aA.clear();
        this.aH = false;
        super.g();
    }

    private void a(LinearSystem linearSystem, boolean[] zArr) {
        zArr[2] = false;
        b(linearSystem);
        int size = this.aL.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) this.aL.get(i);
            constraintWidget.b(linearSystem);
            if (constraintWidget.G[0] == DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.n() < constraintWidget.V) {
                zArr[2] = true;
            }
            if (constraintWidget.G[1] == DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.o() < constraintWidget.W) {
                zArr[2] = true;
            }
        }
    }

    public final void a(int i) {
        super.a(i);
        int size = this.aL.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((ConstraintWidget) this.aL.get(i2)).a(i);
        }
    }

    /* JADX WARNING: type inference failed for: r12v11, types: [boolean] */
    /* JADX WARNING: type inference failed for: r12v13 */
    /* JADX WARNING: type inference failed for: r12v14 */
    /* JADX WARNING: type inference failed for: r12v42 */
    /* JADX WARNING: type inference failed for: r12v43 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r12v11, types: [boolean]
      assigns: []
      uses: [?[int, short, byte, char], boolean]
      mth insns count: 533
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
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x030f  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x0317  */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x0359  */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x03c7  */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x03dc  */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x03f8  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x0405  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0408  */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void B() {
        /*
            r26 = this;
            r1 = r26
            int r2 = r1.M
            int r3 = r1.N
            int r4 = r26.n()
            r5 = 0
            int r4 = java.lang.Math.max(r5, r4)
            int r6 = r26.o()
            int r6 = java.lang.Math.max(r5, r6)
            r1.aI = r5
            r1.aJ = r5
            android.support.constraint.solver.widgets.ConstraintWidget r7 = r1.H
            if (r7 == 0) goto L_0x00a1
            android.support.constraint.solver.widgets.Snapshot r7 = r1.aM
            if (r7 != 0) goto L_0x002a
            android.support.constraint.solver.widgets.Snapshot r7 = new android.support.constraint.solver.widgets.Snapshot
            r7.<init>(r1)
            r1.aM = r7
        L_0x002a:
            android.support.constraint.solver.widgets.Snapshot r7 = r1.aM
            int r8 = r26.l()
            r7.a = r8
            int r8 = r26.m()
            r7.b = r8
            int r8 = r26.n()
            r7.c = r8
            int r8 = r26.o()
            r7.d = r8
            java.util.ArrayList<android.support.constraint.solver.widgets.Snapshot$Connection> r8 = r7.e
            int r8 = r8.size()
            r9 = 0
        L_0x004b:
            if (r9 >= r8) goto L_0x008c
            java.util.ArrayList<android.support.constraint.solver.widgets.Snapshot$Connection> r10 = r7.e
            java.lang.Object r10 = r10.get(r9)
            android.support.constraint.solver.widgets.Snapshot$Connection r10 = (android.support.constraint.solver.widgets.Snapshot.Connection) r10
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r10.a
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r11 = r11.c
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r1.a(r11)
            r10.a = r11
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r10.a
            if (r11 == 0) goto L_0x007e
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r10.a
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r11.d
            r10.b = r11
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r10.a
            int r11 = r11.b()
            r10.c = r11
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r10.a
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r11 = r11.g
            r10.d = r11
            android.support.constraint.solver.widgets.ConstraintAnchor r11 = r10.a
            int r11 = r11.h
            r10.e = r11
            goto L_0x0089
        L_0x007e:
            r11 = 0
            r10.b = r11
            r10.c = r5
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r11 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.STRONG
            r10.d = r11
            r10.e = r5
        L_0x0089:
            int r9 = r9 + 1
            goto L_0x004b
        L_0x008c:
            int r7 = r1.as
            r1.c(r7)
            int r7 = r1.at
            r1.d(r7)
            r26.y()
            android.support.constraint.solver.LinearSystem r7 = r1.b
            android.support.constraint.solver.Cache r7 = r7.g
            r1.a(r7)
            goto L_0x00a5
        L_0x00a1:
            r1.M = r5
            r1.N = r5
        L_0x00a5:
            int r7 = r1.aG
            r8 = 32
            r9 = 8
            r10 = 1
            if (r7 == 0) goto L_0x00d0
            boolean r7 = r1.j(r9)
            if (r7 != 0) goto L_0x00b7
            r26.D()
        L_0x00b7:
            boolean r7 = r1.j(r8)
            if (r7 != 0) goto L_0x00cb
            boolean r7 = r1.j(r9)
            if (r7 != 0) goto L_0x00c8
            int r7 = r1.aG
            r1.a(r7)
        L_0x00c8:
            r26.C()
        L_0x00cb:
            android.support.constraint.solver.LinearSystem r7 = r1.b
            r7.d = r10
            goto L_0x00d4
        L_0x00d0:
            android.support.constraint.solver.LinearSystem r7 = r1.b
            r7.d = r5
        L_0x00d4:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r1.G
            r7 = r7[r10]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r11 = r1.G
            r11 = r11[r5]
            r26.G()
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r12 = r1.aA
            int r12 = r12.size()
            if (r12 != 0) goto L_0x00f8
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r12 = r1.aA
            r12.clear()
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r12 = r1.aA
            android.support.constraint.solver.widgets.ConstraintWidgetGroup r13 = new android.support.constraint.solver.widgets.ConstraintWidgetGroup
            java.util.ArrayList r14 = r1.aL
            r13.<init>(r14)
            r12.add(r5, r13)
        L_0x00f8:
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r12 = r1.aA
            int r12 = r12.size()
            java.util.ArrayList r13 = r1.aL
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = r26.z()
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r15 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r14 == r15) goto L_0x0113
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = r26.A()
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r15 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r14 != r15) goto L_0x0111
            goto L_0x0113
        L_0x0111:
            r14 = 0
            goto L_0x0114
        L_0x0113:
            r14 = 1
        L_0x0114:
            r15 = 0
            r16 = 0
        L_0x0117:
            if (r15 >= r12) goto L_0x0481
            boolean r9 = r1.aH
            if (r9 != 0) goto L_0x0481
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r9 = r1.aA
            java.lang.Object r9 = r9.get(r15)
            android.support.constraint.solver.widgets.ConstraintWidgetGroup r9 = (android.support.constraint.solver.widgets.ConstraintWidgetGroup) r9
            boolean r9 = r9.d
            if (r9 != 0) goto L_0x0467
            boolean r9 = r1.j(r8)
            if (r9 == 0) goto L_0x019d
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r26.z()
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r9 != r8) goto L_0x018f
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = r26.A()
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r8 != r9) goto L_0x018f
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r8 = r1.aA
            java.lang.Object r8 = r8.get(r15)
            android.support.constraint.solver.widgets.ConstraintWidgetGroup r8 = (android.support.constraint.solver.widgets.ConstraintWidgetGroup) r8
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r9 = r8.j
            boolean r9 = r9.isEmpty()
            if (r9 != 0) goto L_0x0152
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r8 = r8.j
            goto L_0x018a
        L_0x0152:
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r9 = r8.a
            int r9 = r9.size()
        L_0x0158:
            if (r5 >= r9) goto L_0x0175
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r10 = r8.a
            java.lang.Object r10 = r10.get(r5)
            android.support.constraint.solver.widgets.ConstraintWidget r10 = (android.support.constraint.solver.widgets.ConstraintWidget) r10
            r17 = r9
            boolean r9 = r10.ag
            if (r9 != 0) goto L_0x016f
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r9 = r8.j
            java.util.ArrayList r9 = (java.util.ArrayList) r9
            r8.a(r9, r10)
        L_0x016f:
            int r5 = r5 + 1
            r9 = r17
            r10 = 1
            goto L_0x0158
        L_0x0175:
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r5 = r8.k
            r5.clear()
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r5 = r8.k
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r9 = r8.a
            r5.addAll(r9)
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r5 = r8.k
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r9 = r8.j
            r5.removeAll(r9)
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r8 = r8.j
        L_0x018a:
            java.util.ArrayList r8 = (java.util.ArrayList) r8
            r1.aL = r8
            goto L_0x019d
        L_0x018f:
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r5 = r1.aA
            java.lang.Object r5 = r5.get(r15)
            android.support.constraint.solver.widgets.ConstraintWidgetGroup r5 = (android.support.constraint.solver.widgets.ConstraintWidgetGroup) r5
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidget> r5 = r5.a
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            r1.aL = r5
        L_0x019d:
            r26.G()
            java.util.ArrayList r5 = r1.aL
            int r5 = r5.size()
            r8 = 0
        L_0x01a7:
            if (r8 >= r5) goto L_0x01bd
            java.util.ArrayList r9 = r1.aL
            java.lang.Object r9 = r9.get(r8)
            android.support.constraint.solver.widgets.ConstraintWidget r9 = (android.support.constraint.solver.widgets.ConstraintWidget) r9
            boolean r10 = r9 instanceof android.support.constraint.solver.widgets.WidgetContainer
            if (r10 == 0) goto L_0x01ba
            android.support.constraint.solver.widgets.WidgetContainer r9 = (android.support.constraint.solver.widgets.WidgetContainer) r9
            r9.B()
        L_0x01ba:
            int r8 = r8 + 1
            goto L_0x01a7
        L_0x01bd:
            r10 = r16
            r8 = 1
            r9 = 0
        L_0x01c1:
            if (r8 == 0) goto L_0x044f
            r16 = 1
            int r9 = r9 + 1
            r18 = r8
            android.support.constraint.solver.LinearSystem r8 = r1.b     // Catch:{ Exception -> 0x02ef }
            r8.b()     // Catch:{ Exception -> 0x02ef }
            r26.G()     // Catch:{ Exception -> 0x02ef }
            android.support.constraint.solver.LinearSystem r8 = r1.b     // Catch:{ Exception -> 0x02ef }
            r1.c(r8)     // Catch:{ Exception -> 0x02ef }
            r8 = 0
        L_0x01d7:
            if (r8 >= r5) goto L_0x0201
            r19 = r10
            java.util.ArrayList r10 = r1.aL     // Catch:{ Exception -> 0x01f3 }
            java.lang.Object r10 = r10.get(r8)     // Catch:{ Exception -> 0x01f3 }
            android.support.constraint.solver.widgets.ConstraintWidget r10 = (android.support.constraint.solver.widgets.ConstraintWidget) r10     // Catch:{ Exception -> 0x01f3 }
            r20 = r12
            android.support.constraint.solver.LinearSystem r12 = r1.b     // Catch:{ Exception -> 0x01f1 }
            r10.c(r12)     // Catch:{ Exception -> 0x01f1 }
            int r8 = r8 + 1
            r10 = r19
            r12 = r20
            goto L_0x01d7
        L_0x01f1:
            r0 = move-exception
            goto L_0x01f6
        L_0x01f3:
            r0 = move-exception
            r20 = r12
        L_0x01f6:
            r23 = r2
            r22 = r3
            r24 = r13
            r10 = r18
            r2 = r0
            goto L_0x02fd
        L_0x0201:
            r19 = r10
            r20 = r12
            android.support.constraint.solver.LinearSystem r8 = r1.b     // Catch:{ Exception -> 0x02e9 }
            r1.a(r8)     // Catch:{ Exception -> 0x02e9 }
            java.util.ArrayList r10 = r1.aL     // Catch:{ Exception -> 0x02e9 }
            int r10 = r10.size()     // Catch:{ Exception -> 0x02e9 }
            r12 = 0
        L_0x0211:
            if (r12 >= r10) goto L_0x0274
            r21 = r10
            java.util.ArrayList r10 = r1.aL     // Catch:{ Exception -> 0x02e9 }
            java.lang.Object r10 = r10.get(r12)     // Catch:{ Exception -> 0x02e9 }
            android.support.constraint.solver.widgets.ConstraintWidget r10 = (android.support.constraint.solver.widgets.ConstraintWidget) r10     // Catch:{ Exception -> 0x02e9 }
            r22 = r3
            boolean r3 = r10 instanceof android.support.constraint.solver.widgets.ConstraintWidgetContainer     // Catch:{ Exception -> 0x026f }
            if (r3 == 0) goto L_0x025a
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r10.G     // Catch:{ Exception -> 0x026f }
            r16 = 0
            r3 = r3[r16]     // Catch:{ Exception -> 0x026f }
            r23 = r2
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r10.G     // Catch:{ Exception -> 0x0257 }
            r16 = 1
            r2 = r2[r16]     // Catch:{ Exception -> 0x0257 }
            r24 = r13
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT     // Catch:{ Exception -> 0x02e7 }
            if (r3 != r13) goto L_0x023c
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED     // Catch:{ Exception -> 0x02e7 }
            r10.a(r13)     // Catch:{ Exception -> 0x02e7 }
        L_0x023c:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT     // Catch:{ Exception -> 0x02e7 }
            if (r2 != r13) goto L_0x0245
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED     // Catch:{ Exception -> 0x02e7 }
            r10.b(r13)     // Catch:{ Exception -> 0x02e7 }
        L_0x0245:
            r10.a(r8)     // Catch:{ Exception -> 0x02e7 }
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT     // Catch:{ Exception -> 0x02e7 }
            if (r3 != r13) goto L_0x024f
            r10.a(r3)     // Catch:{ Exception -> 0x02e7 }
        L_0x024f:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT     // Catch:{ Exception -> 0x02e7 }
            if (r2 != r3) goto L_0x0264
            r10.b(r2)     // Catch:{ Exception -> 0x02e7 }
            goto L_0x0264
        L_0x0257:
            r0 = move-exception
            goto L_0x02f8
        L_0x025a:
            r23 = r2
            r24 = r13
            android.support.constraint.solver.widgets.Optimizer.a(r1, r8, r10)     // Catch:{ Exception -> 0x02e7 }
            r10.a(r8)     // Catch:{ Exception -> 0x02e7 }
        L_0x0264:
            int r12 = r12 + 1
            r10 = r21
            r3 = r22
            r2 = r23
            r13 = r24
            goto L_0x0211
        L_0x026f:
            r0 = move-exception
            r23 = r2
            goto L_0x02f8
        L_0x0274:
            r23 = r2
            r22 = r3
            r24 = r13
            int r2 = r1.aw     // Catch:{ Exception -> 0x02e7 }
            if (r2 <= 0) goto L_0x0282
            r2 = 0
            android.support.constraint.solver.widgets.Chain.a(r1, r8, r2)     // Catch:{ Exception -> 0x02e7 }
        L_0x0282:
            int r2 = r1.ax     // Catch:{ Exception -> 0x02e7 }
            if (r2 <= 0) goto L_0x028a
            r2 = 1
            android.support.constraint.solver.widgets.Chain.a(r1, r8, r2)     // Catch:{ Exception -> 0x02e7 }
        L_0x028a:
            android.support.constraint.solver.LinearSystem r2 = r1.b     // Catch:{ Exception -> 0x02e3 }
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h     // Catch:{ Exception -> 0x02e3 }
            if (r3 == 0) goto L_0x029b
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h     // Catch:{ Exception -> 0x02e3 }
            long r12 = r3.e     // Catch:{ Exception -> 0x02e3 }
            r8 = 0
            r17 = 1
            long r12 = r12 + r17
            r3.e = r12     // Catch:{ Exception -> 0x02e3 }
        L_0x029b:
            boolean r3 = r2.d     // Catch:{ Exception -> 0x02e3 }
            if (r3 == 0) goto L_0x02dc
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h     // Catch:{ Exception -> 0x02e3 }
            if (r3 == 0) goto L_0x02ae
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h     // Catch:{ Exception -> 0x02e3 }
            long r12 = r3.r     // Catch:{ Exception -> 0x02e3 }
            r8 = 0
            r17 = 1
            long r12 = r12 + r17
            r3.r = r12     // Catch:{ Exception -> 0x02e3 }
        L_0x02ae:
            r3 = 0
        L_0x02af:
            int r8 = r2.f     // Catch:{ Exception -> 0x02e3 }
            if (r3 >= r8) goto L_0x02c0
            android.support.constraint.solver.ArrayRow[] r8 = r2.c     // Catch:{ Exception -> 0x02e3 }
            r8 = r8[r3]     // Catch:{ Exception -> 0x02e3 }
            boolean r8 = r8.e     // Catch:{ Exception -> 0x02e3 }
            if (r8 != 0) goto L_0x02bd
            r3 = 0
            goto L_0x02c1
        L_0x02bd:
            int r3 = r3 + 1
            goto L_0x02af
        L_0x02c0:
            r3 = 1
        L_0x02c1:
            if (r3 != 0) goto L_0x02c9
            android.support.constraint.solver.LinearSystem$Row r3 = r2.b     // Catch:{ Exception -> 0x02e3 }
            r2.a(r3)     // Catch:{ Exception -> 0x02e3 }
            goto L_0x02e1
        L_0x02c9:
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h     // Catch:{ Exception -> 0x02e3 }
            if (r3 == 0) goto L_0x02d8
            android.support.constraint.solver.Metrics r3 = android.support.constraint.solver.LinearSystem.h     // Catch:{ Exception -> 0x02e3 }
            long r12 = r3.q     // Catch:{ Exception -> 0x02e3 }
            r8 = 0
            r17 = 1
            long r12 = r12 + r17
            r3.q = r12     // Catch:{ Exception -> 0x02e3 }
        L_0x02d8:
            r2.e()     // Catch:{ Exception -> 0x02e3 }
            goto L_0x02e1
        L_0x02dc:
            android.support.constraint.solver.LinearSystem$Row r3 = r2.b     // Catch:{ Exception -> 0x02e3 }
            r2.a(r3)     // Catch:{ Exception -> 0x02e3 }
        L_0x02e1:
            r10 = 1
            goto L_0x030c
        L_0x02e3:
            r0 = move-exception
            r2 = r0
            r10 = 1
            goto L_0x02fd
        L_0x02e7:
            r0 = move-exception
            goto L_0x02fa
        L_0x02e9:
            r0 = move-exception
            r23 = r2
            r22 = r3
            goto L_0x02f8
        L_0x02ef:
            r0 = move-exception
            r23 = r2
            r22 = r3
            r19 = r10
            r20 = r12
        L_0x02f8:
            r24 = r13
        L_0x02fa:
            r2 = r0
            r10 = r18
        L_0x02fd:
            r2.printStackTrace()
            java.io.PrintStream r3 = java.lang.System.out
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r8 = "EXCEPTION : "
            r3.<init>(r8)
            r3.append(r2)
        L_0x030c:
            r2 = 2
            if (r10 == 0) goto L_0x0317
            android.support.constraint.solver.LinearSystem r3 = r1.b
            boolean[] r8 = android.support.constraint.solver.widgets.Optimizer.a
            r1.a(r3, r8)
            goto L_0x0357
        L_0x0317:
            android.support.constraint.solver.LinearSystem r3 = r1.b
            r1.b(r3)
            r3 = 0
        L_0x031d:
            if (r3 >= r5) goto L_0x0357
            java.util.ArrayList r8 = r1.aL
            java.lang.Object r8 = r8.get(r3)
            android.support.constraint.solver.widgets.ConstraintWidget r8 = (android.support.constraint.solver.widgets.ConstraintWidget) r8
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r10 = r8.G
            r12 = 0
            r10 = r10[r12]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r10 != r12) goto L_0x033e
            int r10 = r8.n()
            int r12 = r8.V
            if (r10 >= r12) goto L_0x033e
            boolean[] r3 = android.support.constraint.solver.widgets.Optimizer.a
            r10 = 1
            r3[r2] = r10
            goto L_0x0357
        L_0x033e:
            r10 = 1
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r12 = r8.G
            r12 = r12[r10]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r12 != r13) goto L_0x0354
            int r12 = r8.o()
            int r8 = r8.W
            if (r12 >= r8) goto L_0x0354
            boolean[] r3 = android.support.constraint.solver.widgets.Optimizer.a
            r3[r2] = r10
            goto L_0x0357
        L_0x0354:
            int r3 = r3 + 1
            goto L_0x031d
        L_0x0357:
            if (r14 == 0) goto L_0x03c7
            r3 = 8
            if (r9 >= r3) goto L_0x03c9
            boolean[] r8 = android.support.constraint.solver.widgets.Optimizer.a
            boolean r2 = r8[r2]
            if (r2 == 0) goto L_0x03c9
            r2 = 0
            r8 = 0
            r10 = 0
        L_0x0366:
            if (r2 >= r5) goto L_0x038a
            java.util.ArrayList r12 = r1.aL
            java.lang.Object r12 = r12.get(r2)
            android.support.constraint.solver.widgets.ConstraintWidget r12 = (android.support.constraint.solver.widgets.ConstraintWidget) r12
            int r13 = r12.M
            int r16 = r12.n()
            int r13 = r13 + r16
            int r8 = java.lang.Math.max(r8, r13)
            int r13 = r12.N
            int r12 = r12.o()
            int r13 = r13 + r12
            int r10 = java.lang.Math.max(r10, r13)
            int r2 = r2 + 1
            goto L_0x0366
        L_0x038a:
            int r2 = r1.T
            int r2 = java.lang.Math.max(r2, r8)
            int r8 = r1.U
            int r8 = java.lang.Math.max(r8, r10)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r11 != r10) goto L_0x03ad
            int r10 = r26.n()
            if (r10 >= r2) goto L_0x03ad
            r1.e(r2)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.G
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r12 = 0
            r2[r12] = r10
            r2 = 1
            r10 = 1
            goto L_0x03b0
        L_0x03ad:
            r10 = r19
            r2 = 0
        L_0x03b0:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r7 != r12) goto L_0x03cc
            int r12 = r26.o()
            if (r12 >= r8) goto L_0x03cc
            r1.f(r8)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.G
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r10 = 1
            r2[r10] = r8
            r2 = 1
            r10 = 1
            goto L_0x03cc
        L_0x03c7:
            r3 = 8
        L_0x03c9:
            r10 = r19
            r2 = 0
        L_0x03cc:
            int r8 = r1.T
            int r12 = r26.n()
            int r8 = java.lang.Math.max(r8, r12)
            int r12 = r26.n()
            if (r8 <= r12) goto L_0x03e8
            r1.e(r8)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.G
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r10 = 0
            r2[r10] = r8
            r2 = 1
            r10 = 1
        L_0x03e8:
            int r8 = r1.U
            int r12 = r26.o()
            int r8 = java.lang.Math.max(r8, r12)
            int r12 = r26.o()
            if (r8 <= r12) goto L_0x0405
            r1.f(r8)
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.G
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r12 = 1
            r2[r12] = r8
            r2 = 1
            r10 = 1
            goto L_0x0406
        L_0x0405:
            r12 = 1
        L_0x0406:
            if (r10 != 0) goto L_0x0444
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r8 = r1.G
            r13 = 0
            r8 = r8[r13]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r8 != r3) goto L_0x0426
            if (r4 <= 0) goto L_0x0426
            int r3 = r26.n()
            if (r3 <= r4) goto L_0x0426
            r1.aI = r12
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.G
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r2[r13] = r3
            r1.e(r4)
            r2 = 1
            r10 = 1
        L_0x0426:
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r1.G
            r3 = r3[r12]
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r3 != r8) goto L_0x0444
            if (r6 <= 0) goto L_0x0444
            int r3 = r26.o()
            if (r3 <= r6) goto L_0x0444
            r1.aJ = r12
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.G
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r2[r12] = r3
            r1.f(r6)
            r8 = 1
            r10 = 1
            goto L_0x0445
        L_0x0444:
            r8 = r2
        L_0x0445:
            r12 = r20
            r3 = r22
            r2 = r23
            r13 = r24
            goto L_0x01c1
        L_0x044f:
            r23 = r2
            r22 = r3
            r19 = r10
            r20 = r12
            r24 = r13
            java.util.List<android.support.constraint.solver.widgets.ConstraintWidgetGroup> r2 = r1.aA
            java.lang.Object r2 = r2.get(r15)
            android.support.constraint.solver.widgets.ConstraintWidgetGroup r2 = (android.support.constraint.solver.widgets.ConstraintWidgetGroup) r2
            r2.a()
            r16 = r19
            goto L_0x046f
        L_0x0467:
            r23 = r2
            r22 = r3
            r20 = r12
            r24 = r13
        L_0x046f:
            int r15 = r15 + 1
            r12 = r20
            r3 = r22
            r2 = r23
            r13 = r24
            r5 = 0
            r8 = 32
            r9 = 8
            r10 = 1
            goto L_0x0117
        L_0x0481:
            r23 = r2
            r22 = r3
            r24 = r13
            r13 = r24
            java.util.ArrayList r13 = (java.util.ArrayList) r13
            r1.aL = r13
            android.support.constraint.solver.widgets.ConstraintWidget r2 = r1.H
            if (r2 == 0) goto L_0x04bd
            int r2 = r1.T
            int r3 = r26.n()
            int r2 = java.lang.Math.max(r2, r3)
            int r3 = r1.U
            int r4 = r26.o()
            int r3 = java.lang.Math.max(r3, r4)
            android.support.constraint.solver.widgets.Snapshot r4 = r1.aM
            r4.a(r1)
            int r4 = r1.as
            int r2 = r2 + r4
            int r4 = r1.au
            int r2 = r2 + r4
            r1.e(r2)
            int r2 = r1.at
            int r3 = r3 + r2
            int r2 = r1.av
            int r3 = r3 + r2
            r1.f(r3)
            goto L_0x04c5
        L_0x04bd:
            r2 = r23
            r1.M = r2
            r2 = r22
            r1.N = r2
        L_0x04c5:
            if (r16 == 0) goto L_0x04d1
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.G
            r3 = 0
            r2[r3] = r11
            android.support.constraint.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r1.G
            r3 = 1
            r2[r3] = r7
        L_0x04d1:
            android.support.constraint.solver.LinearSystem r2 = r1.b
            android.support.constraint.solver.Cache r2 = r2.g
            r1.a(r2)
            android.support.constraint.solver.widgets.ConstraintWidgetContainer r2 = r26.E()
            if (r1 != r2) goto L_0x04e1
            r26.x()
        L_0x04e1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.B():void");
    }

    public final void C() {
        ResolutionAnchor resolutionAnchor = a(Type.LEFT).a;
        ResolutionAnchor resolutionAnchor2 = a(Type.TOP).a;
        resolutionAnchor.a((ResolutionAnchor) null, 0.0f);
        resolutionAnchor2.a((ResolutionAnchor) null, 0.0f);
    }

    public final void f(int i, int i2) {
        if (!(this.G[0] == DimensionBehaviour.WRAP_CONTENT || this.e == null)) {
            this.e.a(i);
        }
        if (this.G[1] != DimensionBehaviour.WRAP_CONTENT && this.f != null) {
            this.f.a(i2);
        }
    }

    public final void D() {
        int size = this.aL.size();
        b();
        for (int i = 0; i < size; i++) {
            ((ConstraintWidget) this.aL.get(i)).b();
        }
    }

    private void G() {
        this.aw = 0;
        this.ax = 0;
    }

    /* access modifiers changed from: 0000 */
    public final void a(ConstraintWidget constraintWidget, int i) {
        if (i == 0) {
            c(constraintWidget);
            return;
        }
        if (i == 1) {
            d(constraintWidget);
        }
    }

    private void c(ConstraintWidget constraintWidget) {
        if (this.aw + 1 >= this.az.length) {
            this.az = (ChainHead[]) Arrays.copyOf(this.az, this.az.length * 2);
        }
        this.az[this.aw] = new ChainHead(constraintWidget, 0, this.a);
        this.aw++;
    }

    private void d(ConstraintWidget constraintWidget) {
        if (this.ax + 1 >= this.ay.length) {
            this.ay = (ChainHead[]) Arrays.copyOf(this.ay, this.ay.length * 2);
        }
        this.ay[this.ax] = new ChainHead(constraintWidget, 1, this.a);
        this.ax++;
    }
}
