package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.ArrayList;

public class Barrier extends Helper {
    public int a = 0;
    private ArrayList<ResolutionAnchor> au = new ArrayList<>(4);
    public boolean b = true;

    public final boolean a() {
        return true;
    }

    public final void b() {
        super.b();
        this.au.clear();
    }

    public final void a(int i) {
        ResolutionAnchor resolutionAnchor;
        ResolutionAnchor resolutionAnchor2;
        if (this.H != null && ((ConstraintWidgetContainer) this.H).j(2)) {
            switch (this.a) {
                case 0:
                    resolutionAnchor = this.w.a;
                    break;
                case 1:
                    resolutionAnchor = this.y.a;
                    break;
                case 2:
                    resolutionAnchor = this.x.a;
                    break;
                case 3:
                    resolutionAnchor = this.z.a;
                    break;
                default:
                    return;
            }
            resolutionAnchor.g = 5;
            if (this.a == 0 || this.a == 1) {
                this.x.a.a((ResolutionAnchor) null, 0.0f);
                this.z.a.a((ResolutionAnchor) null, 0.0f);
            } else {
                this.w.a.a((ResolutionAnchor) null, 0.0f);
                this.y.a.a((ResolutionAnchor) null, 0.0f);
            }
            this.au.clear();
            for (int i2 = 0; i2 < this.at; i2++) {
                ConstraintWidget constraintWidget = this.as[i2];
                if (this.b || constraintWidget.a()) {
                    switch (this.a) {
                        case 0:
                            resolutionAnchor2 = constraintWidget.w.a;
                            break;
                        case 1:
                            resolutionAnchor2 = constraintWidget.y.a;
                            break;
                        case 2:
                            resolutionAnchor2 = constraintWidget.x.a;
                            break;
                        case 3:
                            resolutionAnchor2 = constraintWidget.z.a;
                            break;
                        default:
                            resolutionAnchor2 = null;
                            break;
                    }
                    if (resolutionAnchor2 != null) {
                        this.au.add(resolutionAnchor2);
                        resolutionAnchor2.a(resolutionAnchor);
                    }
                }
            }
        }
    }

    public final void c() {
        ResolutionAnchor resolutionAnchor;
        float f = Float.MAX_VALUE;
        switch (this.a) {
            case 0:
                resolutionAnchor = this.w.a;
                break;
            case 1:
                resolutionAnchor = this.y.a;
                break;
            case 2:
                resolutionAnchor = this.x.a;
                break;
            case 3:
                resolutionAnchor = this.z.a;
                break;
            default:
                return;
        }
        f = 0.0f;
        int size = this.au.size();
        ResolutionAnchor resolutionAnchor2 = null;
        int i = 0;
        while (i < size) {
            ResolutionAnchor resolutionAnchor3 = this.au.get(i);
            if (resolutionAnchor3.i == 1) {
                if (this.a == 0 || this.a == 2) {
                    if (resolutionAnchor3.f < f) {
                        f = resolutionAnchor3.f;
                        resolutionAnchor2 = resolutionAnchor3.e;
                    }
                } else if (resolutionAnchor3.f > f) {
                    f = resolutionAnchor3.f;
                    resolutionAnchor2 = resolutionAnchor3.e;
                }
                i++;
            } else {
                return;
            }
        }
        if (LinearSystem.a() != null) {
            Metrics a2 = LinearSystem.a();
            a2.z++;
        }
        resolutionAnchor.e = resolutionAnchor2;
        resolutionAnchor.f = f;
        resolutionAnchor.d();
        switch (this.a) {
            case 0:
                this.y.a.a(resolutionAnchor2, f);
                return;
            case 1:
                this.w.a.a(resolutionAnchor2, f);
                return;
            case 2:
                this.z.a.a(resolutionAnchor2, f);
                return;
            case 3:
                this.x.a.a(resolutionAnchor2, f);
                return;
            default:
                return;
        }
    }

    public final void a(LinearSystem linearSystem) {
        boolean z;
        this.E[0] = this.w;
        this.E[2] = this.x;
        this.E[1] = this.y;
        this.E[3] = this.z;
        for (int i = 0; i < this.E.length; i++) {
            this.E[i].i = linearSystem.a((Object) this.E[i]);
        }
        if (this.a >= 0 && this.a < 4) {
            ConstraintAnchor constraintAnchor = this.E[this.a];
            int i2 = 0;
            while (true) {
                if (i2 >= this.at) {
                    z = false;
                    break;
                }
                ConstraintWidget constraintWidget = this.as[i2];
                if ((this.b || constraintWidget.a()) && (((this.a == 0 || this.a == 1) && constraintWidget.z() == DimensionBehaviour.MATCH_CONSTRAINT) || ((this.a == 2 || this.a == 3) && constraintWidget.A() == DimensionBehaviour.MATCH_CONSTRAINT))) {
                    z = true;
                } else {
                    i2++;
                }
            }
            z = true;
            if (this.a == 0 || this.a == 1 ? this.H.z() == DimensionBehaviour.WRAP_CONTENT : this.H.A() == DimensionBehaviour.WRAP_CONTENT) {
                z = false;
            }
            for (int i3 = 0; i3 < this.at; i3++) {
                ConstraintWidget constraintWidget2 = this.as[i3];
                if (this.b || constraintWidget2.a()) {
                    SolverVariable a2 = linearSystem.a((Object) constraintWidget2.E[this.a]);
                    constraintWidget2.E[this.a].i = a2;
                    if (this.a == 0 || this.a == 2) {
                        SolverVariable solverVariable = constraintAnchor.i;
                        ArrayRow c = linearSystem.c();
                        SolverVariable d = linearSystem.d();
                        d.d = 0;
                        c.b(solverVariable, a2, d, 0);
                        if (z) {
                            linearSystem.a(c, (int) (c.d.b(d) * -1.0f), 1);
                        }
                        linearSystem.a(c);
                    } else {
                        SolverVariable solverVariable2 = constraintAnchor.i;
                        ArrayRow c2 = linearSystem.c();
                        SolverVariable d2 = linearSystem.d();
                        d2.d = 0;
                        c2.a(solverVariable2, a2, d2, 0);
                        if (z) {
                            linearSystem.a(c2, (int) (c2.d.b(d2) * -1.0f), 1);
                        }
                        linearSystem.a(c2);
                    }
                }
            }
            if (this.a == 0) {
                linearSystem.c(this.y.i, this.w.i, 0, 6);
                if (!z) {
                    linearSystem.c(this.w.i, this.H.y.i, 0, 5);
                }
            } else if (this.a == 1) {
                linearSystem.c(this.w.i, this.y.i, 0, 6);
                if (!z) {
                    linearSystem.c(this.w.i, this.H.w.i, 0, 5);
                }
            } else if (this.a == 2) {
                linearSystem.c(this.z.i, this.x.i, 0, 6);
                if (!z) {
                    linearSystem.c(this.x.i, this.H.z.i, 0, 5);
                }
            } else if (this.a == 3) {
                linearSystem.c(this.x.i, this.z.i, 0, 6);
                if (!z) {
                    linearSystem.c(this.x.i, this.H.x.i, 0, 5);
                }
            }
        }
    }
}
