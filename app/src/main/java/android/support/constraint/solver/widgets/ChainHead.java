package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;

public class ChainHead {
    protected ConstraintWidget a;
    protected ConstraintWidget b;
    protected ConstraintWidget c;
    protected ConstraintWidget d;
    protected ConstraintWidget e;
    protected ConstraintWidget f;
    protected ConstraintWidget g;
    protected ArrayList<ConstraintWidget> h;
    protected int i;
    protected int j;
    protected float k = 0.0f;
    protected boolean l;
    protected boolean m;
    protected boolean n;
    boolean o;
    private int p;
    private boolean q = false;

    public ChainHead(ConstraintWidget constraintWidget, int i2, boolean z) {
        this.a = constraintWidget;
        this.p = i2;
        this.q = z;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        int i2 = this.p * 2;
        ConstraintWidget constraintWidget = this.a;
        ConstraintWidget constraintWidget2 = this.a;
        boolean z = false;
        ConstraintWidget constraintWidget3 = constraintWidget;
        boolean z2 = false;
        while (!z2) {
            this.i++;
            ConstraintWidget constraintWidget4 = null;
            constraintWidget2.ap[this.p] = null;
            constraintWidget2.ao[this.p] = null;
            if (constraintWidget2.ac != 8) {
                if (this.b == null) {
                    this.b = constraintWidget2;
                }
                this.d = constraintWidget2;
                if (constraintWidget2.G[this.p] == DimensionBehaviour.MATCH_CONSTRAINT && (constraintWidget2.i[this.p] == 0 || constraintWidget2.i[this.p] == 3 || constraintWidget2.i[this.p] == 2)) {
                    this.j++;
                    float f2 = constraintWidget2.an[this.p];
                    if (f2 > 0.0f) {
                        this.k += constraintWidget2.an[this.p];
                    }
                    int i3 = this.p;
                    if (constraintWidget2.ac != 8 && constraintWidget2.G[i3] == DimensionBehaviour.MATCH_CONSTRAINT && (constraintWidget2.i[i3] == 0 || constraintWidget2.i[i3] == 3)) {
                        if (f2 < 0.0f) {
                            this.l = true;
                        } else {
                            this.m = true;
                        }
                        if (this.h == null) {
                            this.h = new ArrayList<>();
                        }
                        this.h.add(constraintWidget2);
                    }
                    if (this.f == null) {
                        this.f = constraintWidget2;
                    }
                    if (this.g != null) {
                        this.g.ao[this.p] = constraintWidget2;
                    }
                    this.g = constraintWidget2;
                }
            }
            if (constraintWidget3 != constraintWidget2) {
                constraintWidget3.ap[this.p] = constraintWidget2;
            }
            ConstraintAnchor constraintAnchor = constraintWidget2.E[i2 + 1].d;
            if (constraintAnchor != null) {
                ConstraintWidget constraintWidget5 = constraintAnchor.b;
                if (constraintWidget5.E[i2].d != null && constraintWidget5.E[i2].d.b == constraintWidget2) {
                    constraintWidget4 = constraintWidget5;
                }
            }
            if (constraintWidget4 != null) {
                constraintWidget3 = constraintWidget2;
                constraintWidget2 = constraintWidget4;
            } else {
                constraintWidget3 = constraintWidget2;
                z2 = true;
            }
        }
        this.c = constraintWidget2;
        if (this.p != 0 || !this.q) {
            this.e = this.a;
        } else {
            this.e = this.c;
        }
        if (this.m && this.l) {
            z = true;
        }
        this.n = z;
    }
}
