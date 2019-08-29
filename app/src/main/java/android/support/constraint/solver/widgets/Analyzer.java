package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Analyzer {
    private Analyzer() {
    }

    private static boolean a(ConstraintWidget constraintWidget, ConstraintWidgetGroup constraintWidgetGroup, List<ConstraintWidgetGroup> list, boolean z) {
        if (constraintWidget == null) {
            return true;
        }
        constraintWidget.ah = false;
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget.H;
        if (constraintWidget.t == null) {
            constraintWidget.ag = true;
            constraintWidgetGroup.a.add(constraintWidget);
            constraintWidget.t = constraintWidgetGroup;
            if (constraintWidget.w.d == null && constraintWidget.y.d == null && constraintWidget.x.d == null && constraintWidget.z.d == null && constraintWidget.A.d == null && constraintWidget.D.d == null) {
                a(constraintWidgetContainer, constraintWidget, constraintWidgetGroup);
                if (z) {
                    return false;
                }
            }
            if (!(constraintWidget.x.d == null || constraintWidget.z.d == null)) {
                DimensionBehaviour dimensionBehaviour = DimensionBehaviour.WRAP_CONTENT;
                if (z) {
                    a(constraintWidgetContainer, constraintWidget, constraintWidgetGroup);
                    return false;
                } else if (!(constraintWidget.x.d.b == constraintWidget.H && constraintWidget.z.d.b == constraintWidget.H)) {
                    a(constraintWidgetContainer, constraintWidget, constraintWidgetGroup);
                }
            }
            if (!(constraintWidget.w.d == null || constraintWidget.y.d == null)) {
                DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.WRAP_CONTENT;
                if (z) {
                    a(constraintWidgetContainer, constraintWidget, constraintWidgetGroup);
                    return false;
                } else if (!(constraintWidget.w.d.b == constraintWidget.H && constraintWidget.y.d.b == constraintWidget.H)) {
                    a(constraintWidgetContainer, constraintWidget, constraintWidgetGroup);
                }
            }
            if (((constraintWidget.z() == DimensionBehaviour.MATCH_CONSTRAINT) ^ (constraintWidget.A() == DimensionBehaviour.MATCH_CONSTRAINT)) && constraintWidget.K != 0.0f) {
                a(constraintWidget);
            } else if (constraintWidget.z() == DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.A() == DimensionBehaviour.MATCH_CONSTRAINT) {
                a(constraintWidgetContainer, constraintWidget, constraintWidgetGroup);
                if (z) {
                    return false;
                }
            }
            if (((constraintWidget.w.d == null && constraintWidget.y.d == null) || ((constraintWidget.w.d != null && constraintWidget.w.d.b == constraintWidget.H && constraintWidget.y.d == null) || ((constraintWidget.y.d != null && constraintWidget.y.d.b == constraintWidget.H && constraintWidget.w.d == null) || (constraintWidget.w.d != null && constraintWidget.w.d.b == constraintWidget.H && constraintWidget.y.d != null && constraintWidget.y.d.b == constraintWidget.H)))) && constraintWidget.D.d == null && !(constraintWidget instanceof Guideline) && !(constraintWidget instanceof Helper)) {
                constraintWidgetGroup.f.add(constraintWidget);
            }
            if (((constraintWidget.x.d == null && constraintWidget.z.d == null) || ((constraintWidget.x.d != null && constraintWidget.x.d.b == constraintWidget.H && constraintWidget.z.d == null) || ((constraintWidget.z.d != null && constraintWidget.z.d.b == constraintWidget.H && constraintWidget.x.d == null) || (constraintWidget.x.d != null && constraintWidget.x.d.b == constraintWidget.H && constraintWidget.z.d != null && constraintWidget.z.d.b == constraintWidget.H)))) && constraintWidget.D.d == null && constraintWidget.A.d == null && !(constraintWidget instanceof Guideline) && !(constraintWidget instanceof Helper)) {
                constraintWidgetGroup.g.add(constraintWidget);
            }
            if (constraintWidget instanceof Helper) {
                a(constraintWidgetContainer, constraintWidget, constraintWidgetGroup);
                if (z) {
                    return false;
                }
                Helper helper = (Helper) constraintWidget;
                for (int i = 0; i < helper.at; i++) {
                    if (!a(helper.as[i], constraintWidgetGroup, list, z)) {
                        return false;
                    }
                }
            }
            for (ConstraintAnchor constraintAnchor : constraintWidget.E) {
                if (!(constraintAnchor.d == null || constraintAnchor.d.b == constraintWidget.H)) {
                    if (constraintAnchor.c == Type.CENTER) {
                        a(constraintWidgetContainer, constraintWidget, constraintWidgetGroup);
                        if (z) {
                            return false;
                        }
                    } else {
                        ResolutionAnchor resolutionAnchor = constraintAnchor.a;
                        if (!(constraintAnchor.d == null || constraintAnchor.d.d == constraintAnchor)) {
                            constraintAnchor.d.a.a(resolutionAnchor);
                        }
                    }
                    if (!a(constraintAnchor.d.b, constraintWidgetGroup, list, z)) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (constraintWidget.t != constraintWidgetGroup) {
            constraintWidgetGroup.a.addAll(constraintWidget.t.a);
            constraintWidgetGroup.f.addAll(constraintWidget.t.f);
            constraintWidgetGroup.g.addAll(constraintWidget.t.g);
            if (!constraintWidget.t.d) {
                constraintWidgetGroup.d = false;
            }
            list.remove(constraintWidget.t);
            for (ConstraintWidget constraintWidget2 : constraintWidget.t.a) {
                constraintWidget2.t = constraintWidgetGroup;
            }
        }
        return true;
    }

    private static void a(ConstraintWidgetContainer constraintWidgetContainer, ConstraintWidget constraintWidget, ConstraintWidgetGroup constraintWidgetGroup) {
        constraintWidgetGroup.d = false;
        constraintWidgetContainer.aH = false;
        constraintWidget.ag = false;
    }

    private static int a(ConstraintWidget constraintWidget, int i, boolean z, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int n;
        int i9;
        int i10;
        ConstraintWidget constraintWidget2 = constraintWidget;
        int i11 = i;
        boolean z2 = z;
        int i12 = 0;
        if (!constraintWidget2.ag) {
            return 0;
        }
        boolean z3 = constraintWidget2.A.d != null && i11 == 1;
        if (z2) {
            i6 = constraintWidget2.S;
            i5 = constraintWidget.o() - constraintWidget2.S;
            i4 = i11 * 2;
            i3 = i4 + 1;
        } else {
            i6 = constraintWidget.o() - constraintWidget2.S;
            i5 = constraintWidget2.S;
            i3 = i11 * 2;
            i4 = i3 + 1;
        }
        if (constraintWidget2.E[i3].d == null || constraintWidget2.E[i4].d != null) {
            i7 = i3;
            i8 = 1;
        } else {
            i7 = i4;
            i4 = i3;
            i8 = -1;
        }
        int b = (constraintWidget2.E[i4].b() * i8) + a(constraintWidget, i);
        int i13 = (z3 ? i2 - i6 : i2) + b;
        int n2 = (i11 == 0 ? constraintWidget.n() : constraintWidget.o()) * i8;
        Iterator it = constraintWidget2.E[i4].a.h.iterator();
        while (it.hasNext()) {
            i12 = Math.max(i12, a(((ResolutionAnchor) ((ResolutionNode) it.next())).a.b, i11, z2, i13));
        }
        int i14 = 0;
        for (Iterator it2 = constraintWidget2.E[i7].a.h.iterator(); it2.hasNext(); it2 = it2) {
            i14 = Math.max(i14, a(((ResolutionAnchor) ((ResolutionNode) it2.next())).a.b, i11, z2, n2 + i13));
        }
        if (z3) {
            i12 -= i6;
            n = i14 + i5;
        } else {
            n = i14 + ((i11 == 0 ? constraintWidget.n() : constraintWidget.o()) * i8);
        }
        int i15 = 1;
        if (i11 == 1) {
            Iterator it3 = constraintWidget2.A.a.h.iterator();
            int i16 = 0;
            while (it3.hasNext()) {
                Iterator it4 = it3;
                ResolutionAnchor resolutionAnchor = (ResolutionAnchor) ((ResolutionNode) it3.next());
                if (i8 == i15) {
                    i16 = Math.max(i16, a(resolutionAnchor.a.b, i11, z2, i6 + i13));
                    it3 = it4;
                } else {
                    i16 = Math.max(i16, a(resolutionAnchor.a.b, i11, z2, (i5 * i8) + i13));
                    it3 = it4;
                    i7 = i7;
                }
                i15 = 1;
            }
            i9 = i7;
            int i17 = i16;
            i10 = (constraintWidget2.A.a.h.size() <= 0 || z3) ? i17 : i8 == 1 ? i17 + i6 : i17 - i5;
        } else {
            i9 = i7;
            i10 = 0;
        }
        int max = b + Math.max(i12, Math.max(n, i10));
        int i18 = i13 + n2;
        if (i8 == -1) {
            int i19 = i13;
            i13 = i18;
            i18 = i19;
        }
        if (z2) {
            Optimizer.a(constraintWidget2, i11, i13);
            constraintWidget2.a(i13, i18, i11);
        } else {
            constraintWidget2.t.a(constraintWidget2, i11);
            constraintWidget2.e(i13, i11);
        }
        if (constraintWidget.i(i) == DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget2.K != 0.0f) {
            constraintWidget2.t.a(constraintWidget2, i11);
        }
        if (!(constraintWidget2.E[i4].d == null || constraintWidget2.E[i9].d == null)) {
            ConstraintWidget constraintWidget3 = constraintWidget2.H;
            if (constraintWidget2.E[i4].d.b == constraintWidget3 && constraintWidget2.E[i9].d.b == constraintWidget3) {
                constraintWidget2.t.a(constraintWidget2, i11);
            }
        }
        return max;
    }

    private static void b(ConstraintWidgetContainer constraintWidgetContainer) {
        constraintWidgetContainer.aA.clear();
        constraintWidgetContainer.aA.add(0, new ConstraintWidgetGroup(constraintWidgetContainer.aL));
    }

    public static void a(List<ConstraintWidgetGroup> list, int i, int i2) {
        HashSet<ConstraintWidget> hashSet;
        int i3;
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            ConstraintWidgetGroup constraintWidgetGroup = list.get(i4);
            if (i == 0) {
                hashSet = constraintWidgetGroup.h;
            } else if (i == 1) {
                hashSet = constraintWidgetGroup.i;
            } else {
                hashSet = null;
            }
            for (ConstraintWidget next : hashSet) {
                if (next.ag) {
                    int i5 = i * 2;
                    ConstraintAnchor constraintAnchor = next.E[i5];
                    ConstraintAnchor constraintAnchor2 = next.E[i5 + 1];
                    if ((constraintAnchor.d == null || constraintAnchor2.d == null) ? false : true) {
                        Optimizer.a(next, i, a(next, i) + constraintAnchor.b());
                    } else if (next.K == 0.0f || next.i(i) != DimensionBehaviour.MATCH_CONSTRAINT) {
                        if (i == 0) {
                            i3 = next.O;
                        } else if (i == 1) {
                            i3 = next.P;
                        } else {
                            i3 = 0;
                        }
                        int i6 = i2 - i3;
                        int b = i6 - next.b(i);
                        next.a(b, i6, i);
                        Optimizer.a(next, i, b);
                    } else {
                        int a = a(next);
                        int i7 = (int) next.E[i5].a.f;
                        constraintAnchor2.a.e = constraintAnchor.a;
                        constraintAnchor2.a.f = (float) a;
                        constraintAnchor2.a.i = 1;
                        next.a(i7, i7 + a, i);
                    }
                }
            }
        }
    }

    private static int a(ConstraintWidget constraintWidget, int i) {
        int i2 = i * 2;
        ConstraintAnchor constraintAnchor = constraintWidget.E[i2];
        ConstraintAnchor constraintAnchor2 = constraintWidget.E[i2 + 1];
        if (constraintAnchor.d == null || constraintAnchor.d.b != constraintWidget.H || constraintAnchor2.d == null || constraintAnchor2.d.b != constraintWidget.H) {
            return 0;
        }
        return (int) (((float) (((constraintWidget.H.b(i) - constraintAnchor.b()) - constraintAnchor2.b()) - constraintWidget.b(i))) * (i == 0 ? constraintWidget.Y : constraintWidget.Z));
    }

    private static int a(ConstraintWidget constraintWidget) {
        int i;
        int i2;
        if (constraintWidget.z() == DimensionBehaviour.MATCH_CONSTRAINT) {
            if (constraintWidget.L == 0) {
                i2 = (int) (((float) constraintWidget.o()) * constraintWidget.K);
            } else {
                i2 = (int) (((float) constraintWidget.o()) / constraintWidget.K);
            }
            constraintWidget.e(i2);
            return i2;
        } else if (constraintWidget.A() != DimensionBehaviour.MATCH_CONSTRAINT) {
            return -1;
        } else {
            if (constraintWidget.L == 1) {
                i = (int) (((float) constraintWidget.n()) * constraintWidget.K);
            } else {
                i = (int) (((float) constraintWidget.n()) / constraintWidget.K);
            }
            constraintWidget.f(i);
            return i;
        }
    }

    public static void a(ConstraintWidgetContainer constraintWidgetContainer) {
        if ((constraintWidgetContainer.aG & 32) != 32) {
            b(constraintWidgetContainer);
            return;
        }
        constraintWidgetContainer.aH = true;
        constraintWidgetContainer.aB = false;
        constraintWidgetContainer.aC = false;
        constraintWidgetContainer.aD = false;
        ArrayList<ConstraintWidget> arrayList = constraintWidgetContainer.aL;
        List<ConstraintWidgetGroup> list = constraintWidgetContainer.aA;
        boolean z = constraintWidgetContainer.z() == DimensionBehaviour.WRAP_CONTENT;
        boolean z2 = constraintWidgetContainer.A() == DimensionBehaviour.WRAP_CONTENT;
        boolean z3 = z || z2;
        list.clear();
        for (ConstraintWidget constraintWidget : arrayList) {
            constraintWidget.t = null;
            constraintWidget.ai = false;
            constraintWidget.b();
        }
        for (ConstraintWidget constraintWidget2 : arrayList) {
            if (constraintWidget2.t == null) {
                ConstraintWidgetGroup constraintWidgetGroup = new ConstraintWidgetGroup(new ArrayList(), 0);
                list.add(constraintWidgetGroup);
                if (!a(constraintWidget2, constraintWidgetGroup, list, z3)) {
                    b(constraintWidgetContainer);
                    constraintWidgetContainer.aH = false;
                    return;
                }
            }
        }
        int i = 0;
        int i2 = 0;
        for (ConstraintWidgetGroup next : list) {
            i = Math.max(i, a(next, 0));
            i2 = Math.max(i2, a(next, 1));
        }
        if (z) {
            constraintWidgetContainer.a(DimensionBehaviour.FIXED);
            constraintWidgetContainer.e(i);
            constraintWidgetContainer.aB = true;
            constraintWidgetContainer.aC = true;
            constraintWidgetContainer.aE = i;
        }
        if (z2) {
            constraintWidgetContainer.b(DimensionBehaviour.FIXED);
            constraintWidgetContainer.f(i2);
            constraintWidgetContainer.aB = true;
            constraintWidgetContainer.aD = true;
            constraintWidgetContainer.aF = i2;
        }
        a(list, 0, constraintWidgetContainer.n());
        a(list, 1, constraintWidgetContainer.o());
    }

    private static int a(ConstraintWidgetGroup constraintWidgetGroup, int i) {
        List<ConstraintWidget> list;
        int i2 = i * 2;
        if (i == 0) {
            list = constraintWidgetGroup.f;
        } else if (i == 1) {
            list = constraintWidgetGroup.g;
        } else {
            list = null;
        }
        int size = list.size();
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            ConstraintWidget constraintWidget = list.get(i4);
            int i5 = i2 + 1;
            i3 = Math.max(i3, a(constraintWidget, i, constraintWidget.E[i5].d == null || !(constraintWidget.E[i2].d == null || constraintWidget.E[i5].d == null), 0));
        }
        constraintWidgetGroup.e[i] = i3;
        return i3;
    }
}
