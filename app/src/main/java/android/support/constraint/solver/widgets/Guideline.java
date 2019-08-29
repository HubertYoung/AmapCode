package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;

public class Guideline extends ConstraintWidget {
    protected float a = -1.0f;
    protected int as = -1;
    private ConstraintAnchor at = this.x;
    private int au;
    private boolean av;
    private int aw;
    private Rectangle ax;
    private int ay;
    protected int b = -1;

    public final boolean a() {
        return true;
    }

    public Guideline() {
        this.au = 0;
        this.av = false;
        this.aw = 0;
        this.ax = new Rectangle();
        this.ay = 8;
        this.F.clear();
        this.F.add(this.at);
        int length = this.E.length;
        for (int i = 0; i < length; i++) {
            this.E[i] = this.at;
        }
    }

    public final void j(int i) {
        if (this.au != i) {
            this.au = i;
            this.F.clear();
            if (this.au == 1) {
                this.at = this.w;
            } else {
                this.at = this.x;
            }
            this.F.add(this.at);
            int length = this.E.length;
            for (int i2 = 0; i2 < length; i2++) {
                this.E[i2] = this.at;
            }
        }
    }

    public final void a(boolean z) {
        if (this.av != z) {
            this.av = z;
        }
    }

    public final ConstraintAnchor a(Type type) {
        switch (type) {
            case LEFT:
            case RIGHT:
                if (this.au == 1) {
                    return this.at;
                }
                break;
            case TOP:
            case BOTTOM:
                if (this.au == 0) {
                    return this.at;
                }
                break;
            case BASELINE:
            case CENTER:
            case CENTER_X:
            case CENTER_Y:
            case NONE:
                return null;
        }
        throw new AssertionError(type.name());
    }

    public final ArrayList<ConstraintAnchor> w() {
        return this.F;
    }

    public final void k(int i) {
        a(((float) i) / 100.0f);
    }

    public final void a(float f) {
        if (f > -1.0f) {
            this.a = f;
            this.b = -1;
            this.as = -1;
        }
    }

    public final void l(int i) {
        if (i >= 0) {
            this.a = -1.0f;
            this.b = i;
            this.as = -1;
        }
    }

    public final void m(int i) {
        if (i >= 0) {
            this.a = -1.0f;
            this.b = -1;
            this.as = i;
        }
    }

    public final void a(int i) {
        ConstraintWidget constraintWidget = this.H;
        if (constraintWidget != null) {
            if (this.au == 1) {
                this.x.a.a(constraintWidget.x.a, 0);
                this.z.a.a(constraintWidget.x.a, 0);
                if (this.b != -1) {
                    this.w.a.a(constraintWidget.w.a, this.b);
                    this.y.a.a(constraintWidget.w.a, this.b);
                } else if (this.as != -1) {
                    this.w.a.a(constraintWidget.y.a, -this.as);
                    this.y.a.a(constraintWidget.y.a, -this.as);
                } else if (this.a != -1.0f && constraintWidget.z() == DimensionBehaviour.FIXED) {
                    int i2 = (int) (((float) constraintWidget.I) * this.a);
                    this.w.a.a(constraintWidget.w.a, i2);
                    this.y.a.a(constraintWidget.w.a, i2);
                }
            } else {
                this.w.a.a(constraintWidget.w.a, 0);
                this.y.a.a(constraintWidget.w.a, 0);
                if (this.b != -1) {
                    this.x.a.a(constraintWidget.x.a, this.b);
                    this.z.a.a(constraintWidget.x.a, this.b);
                } else if (this.as != -1) {
                    this.x.a.a(constraintWidget.z.a, -this.as);
                    this.z.a.a(constraintWidget.z.a, -this.as);
                } else if (this.a != -1.0f && constraintWidget.A() == DimensionBehaviour.FIXED) {
                    int i3 = (int) (((float) constraintWidget.J) * this.a);
                    this.x.a.a(constraintWidget.x.a, i3);
                    this.z.a.a(constraintWidget.x.a, i3);
                }
            }
        }
    }

    public final void a(LinearSystem linearSystem) {
        ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) this.H;
        if (constraintWidgetContainer != null) {
            ConstraintAnchor a2 = constraintWidgetContainer.a(Type.LEFT);
            ConstraintAnchor a3 = constraintWidgetContainer.a(Type.RIGHT);
            boolean z = this.H != null && this.H.G[0] == DimensionBehaviour.WRAP_CONTENT;
            if (this.au == 0) {
                a2 = constraintWidgetContainer.a(Type.TOP);
                a3 = constraintWidgetContainer.a(Type.BOTTOM);
                z = this.H != null && this.H.G[1] == DimensionBehaviour.WRAP_CONTENT;
            }
            if (this.b != -1) {
                SolverVariable a4 = linearSystem.a((Object) this.at);
                linearSystem.c(a4, linearSystem.a((Object) a2), this.b, 6);
                if (z) {
                    linearSystem.a(linearSystem.a((Object) a3), a4, 0, 5);
                }
            } else if (this.as != -1) {
                SolverVariable a5 = linearSystem.a((Object) this.at);
                SolverVariable a6 = linearSystem.a((Object) a3);
                linearSystem.c(a5, a6, -this.as, 6);
                if (z) {
                    linearSystem.a(a5, linearSystem.a((Object) a2), 0, 5);
                    linearSystem.a(a6, a5, 0, 5);
                }
            } else {
                if (this.a != -1.0f) {
                    linearSystem.a(LinearSystem.a(linearSystem, linearSystem.a((Object) this.at), linearSystem.a((Object) a2), linearSystem.a((Object) a3), this.a, this.av));
                }
            }
        }
    }

    public final void b(LinearSystem linearSystem) {
        if (this.H != null) {
            int b2 = LinearSystem.b((Object) this.at);
            if (this.au == 1) {
                c(b2);
                d(0);
                f(this.H.o());
                e(0);
                return;
            }
            c(0);
            d(b2);
            e(this.H.n());
            f(0);
        }
    }
}
