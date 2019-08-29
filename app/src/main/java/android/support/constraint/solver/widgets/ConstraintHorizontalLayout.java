package android.support.constraint.solver.widgets;

public class ConstraintHorizontalLayout extends ConstraintWidgetContainer {
    private ContentAlignment aM = ContentAlignment.MIDDLE;

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

    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.support.constraint.solver.LinearSystem r8) {
        /*
            r7 = this;
            java.util.ArrayList r0 = r7.aL
            int r0 = r0.size()
            if (r0 == 0) goto L_0x0061
            r0 = 0
            java.util.ArrayList r1 = r7.aL
            int r1 = r1.size()
            r2 = r7
        L_0x0010:
            if (r0 >= r1) goto L_0x004e
            java.util.ArrayList r3 = r7.aL
            java.lang.Object r3 = r3.get(r0)
            android.support.constraint.solver.widgets.ConstraintWidget r3 = (android.support.constraint.solver.widgets.ConstraintWidget) r3
            if (r2 == r7) goto L_0x002b
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r4 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            r3.a(r4, r2, r5)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r4 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            r2.a(r4, r3, r5)
            goto L_0x003c
        L_0x002b:
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r4 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.STRONG
            android.support.constraint.solver.widgets.ConstraintHorizontalLayout$ContentAlignment r5 = r7.aM
            android.support.constraint.solver.widgets.ConstraintHorizontalLayout$ContentAlignment r6 = android.support.constraint.solver.widgets.ConstraintHorizontalLayout.ContentAlignment.END
            if (r5 != r6) goto L_0x0035
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r4 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.WEAK
        L_0x0035:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r6 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            r3.a(r5, r2, r6, r4)
        L_0x003c:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r4 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            r3.a(r2, r7, r4)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r4 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            r3.a(r2, r7, r4)
            int r0 = r0 + 1
            r2 = r3
            goto L_0x0010
        L_0x004e:
            if (r2 == r7) goto L_0x0061
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r0 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.STRONG
            android.support.constraint.solver.widgets.ConstraintHorizontalLayout$ContentAlignment r1 = r7.aM
            android.support.constraint.solver.widgets.ConstraintHorizontalLayout$ContentAlignment r3 = android.support.constraint.solver.widgets.ConstraintHorizontalLayout.ContentAlignment.BEGIN
            if (r1 != r3) goto L_0x005a
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r0 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.WEAK
        L_0x005a:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r1 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            r2.a(r1, r7, r3, r0)
        L_0x0061:
            super.a(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintHorizontalLayout.a(android.support.constraint.solver.LinearSystem):void");
    }
}
