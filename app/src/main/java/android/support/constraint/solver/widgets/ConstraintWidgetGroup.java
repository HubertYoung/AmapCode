package android.support.constraint.solver.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ConstraintWidgetGroup {
    public List<ConstraintWidget> a;
    int b = -1;
    int c = -1;
    public boolean d = false;
    public final int[] e = {this.b, this.c};
    List<ConstraintWidget> f = new ArrayList();
    List<ConstraintWidget> g = new ArrayList();
    HashSet<ConstraintWidget> h = new HashSet<>();
    HashSet<ConstraintWidget> i = new HashSet<>();
    List<ConstraintWidget> j = new ArrayList();
    List<ConstraintWidget> k = new ArrayList();

    ConstraintWidgetGroup(List<ConstraintWidget> list) {
        this.a = list;
    }

    ConstraintWidgetGroup(List<ConstraintWidget> list, byte b2) {
        this.a = list;
        this.d = true;
    }

    /* access modifiers changed from: 0000 */
    public final void a(ConstraintWidget constraintWidget, int i2) {
        if (i2 == 0) {
            this.h.add(constraintWidget);
            return;
        }
        if (i2 == 1) {
            this.i.add(constraintWidget);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(ArrayList<ConstraintWidget> arrayList, ConstraintWidget constraintWidget) {
        if (!constraintWidget.ai) {
            arrayList.add(constraintWidget);
            constraintWidget.ai = true;
            if (!constraintWidget.i()) {
                if (constraintWidget instanceof Helper) {
                    Helper helper = (Helper) constraintWidget;
                    int i2 = helper.at;
                    for (int i3 = 0; i3 < i2; i3++) {
                        a(arrayList, helper.as[i3]);
                    }
                }
                for (ConstraintAnchor constraintAnchor : constraintWidget.E) {
                    ConstraintAnchor constraintAnchor2 = constraintAnchor.d;
                    if (constraintAnchor2 != null) {
                        ConstraintWidget constraintWidget2 = constraintAnchor2.b;
                        if (!(constraintAnchor2 == null || constraintWidget2 == constraintWidget.H)) {
                            a(arrayList, constraintWidget2);
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        int size = this.k.size();
        for (int i2 = 0; i2 < size; i2++) {
            a(this.k.get(i2));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0095  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.support.constraint.solver.widgets.ConstraintWidget r7) {
        /*
            r6 = this;
            boolean r0 = r7.ag
            if (r0 == 0) goto L_0x00f1
            boolean r0 = r7.i()
            if (r0 == 0) goto L_0x000b
            return
        L_0x000b:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.y
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0015
            r0 = 1
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            if (r0 == 0) goto L_0x001d
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r7.y
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
            goto L_0x0021
        L_0x001d:
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r7.w
            android.support.constraint.solver.widgets.ConstraintAnchor r3 = r3.d
        L_0x0021:
            if (r3 == 0) goto L_0x004b
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r3.b
            boolean r4 = r4.ah
            if (r4 != 0) goto L_0x002e
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r3.b
            r6.a(r4)
        L_0x002e:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r4 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            if (r4 != r5) goto L_0x0040
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r3.b
            int r4 = r4.M
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r3.b
            int r3 = r3.n()
            int r3 = r3 + r4
            goto L_0x004c
        L_0x0040:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r4 = r3.c
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            if (r4 != r5) goto L_0x004b
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r3.b
            int r3 = r3.M
            goto L_0x004c
        L_0x004b:
            r3 = 0
        L_0x004c:
            if (r0 == 0) goto L_0x0056
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.y
            int r0 = r0.b()
            int r3 = r3 - r0
            goto L_0x0062
        L_0x0056:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.w
            int r0 = r0.b()
            int r4 = r7.n()
            int r0 = r0 + r4
            int r3 = r3 + r0
        L_0x0062:
            int r0 = r7.n()
            int r0 = r3 - r0
            r7.c(r0, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.A
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            if (r0 == 0) goto L_0x0095
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.A
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            android.support.constraint.solver.widgets.ConstraintWidget r1 = r0.b
            boolean r1 = r1.ah
            if (r1 != 0) goto L_0x0080
            android.support.constraint.solver.widgets.ConstraintWidget r1 = r0.b
            r6.a(r1)
        L_0x0080:
            android.support.constraint.solver.widgets.ConstraintWidget r1 = r0.b
            int r1 = r1.N
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.b
            int r0 = r0.S
            int r1 = r1 + r0
            int r0 = r7.S
            int r1 = r1 - r0
            int r0 = r7.J
            int r0 = r0 + r1
            r7.d(r1, r0)
            r7.ah = r2
            return
        L_0x0095:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.z
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            if (r0 == 0) goto L_0x009c
            r1 = 1
        L_0x009c:
            if (r1 == 0) goto L_0x00a3
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.z
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
            goto L_0x00a7
        L_0x00a3:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.x
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r0.d
        L_0x00a7:
            if (r0 == 0) goto L_0x00d0
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r0.b
            boolean r4 = r4.ah
            if (r4 != 0) goto L_0x00b4
            android.support.constraint.solver.widgets.ConstraintWidget r4 = r0.b
            r6.a(r4)
        L_0x00b4:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r4 = r0.c
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            if (r4 != r5) goto L_0x00c6
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r0.b
            int r3 = r3.N
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.b
            int r0 = r0.o()
            int r3 = r3 + r0
            goto L_0x00d0
        L_0x00c6:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r4 = r0.c
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            if (r4 != r5) goto L_0x00d0
            android.support.constraint.solver.widgets.ConstraintWidget r0 = r0.b
            int r3 = r0.N
        L_0x00d0:
            if (r1 == 0) goto L_0x00da
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.z
            int r0 = r0.b()
            int r3 = r3 - r0
            goto L_0x00e6
        L_0x00da:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r7.x
            int r0 = r0.b()
            int r1 = r7.o()
            int r0 = r0 + r1
            int r3 = r3 + r0
        L_0x00e6:
            int r0 = r7.o()
            int r0 = r3 - r0
            r7.d(r0, r3)
            r7.ah = r2
        L_0x00f1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetGroup.a(android.support.constraint.solver.widgets.ConstraintWidget):void");
    }
}
