package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.Metrics;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;

public class ResolutionAnchor extends ResolutionNode {
    ConstraintAnchor a;
    float b;
    ResolutionAnchor c;
    float d;
    ResolutionAnchor e;
    public float f;
    int g = 0;
    private ResolutionAnchor j;
    private float k;
    private ResolutionDimension l = null;
    private int m = 1;
    private ResolutionDimension n = null;
    private int o = 1;

    private static String a(int i) {
        return i == 1 ? "DIRECT" : i == 2 ? "CENTER" : i == 3 ? "MATCH" : i == 4 ? "CHAIN" : i == 5 ? "BARRIER" : "UNCONNECTED";
    }

    public ResolutionAnchor(ConstraintAnchor constraintAnchor) {
        this.a = constraintAnchor;
    }

    public String toString() {
        if (this.i != 1) {
            StringBuilder sb = new StringBuilder("{ ");
            sb.append(this.a);
            sb.append(" UNRESOLVED} type: ");
            sb.append(a(this.g));
            return sb.toString();
        } else if (this.e == this) {
            StringBuilder sb2 = new StringBuilder("[");
            sb2.append(this.a);
            sb2.append(", RESOLVED: ");
            sb2.append(this.f);
            sb2.append("]  type: ");
            sb2.append(a(this.g));
            return sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder("[");
            sb3.append(this.a);
            sb3.append(", RESOLVED: ");
            sb3.append(this.e);
            sb3.append(":");
            sb3.append(this.f);
            sb3.append("] type: ");
            sb3.append(a(this.g));
            return sb3.toString();
        }
    }

    public final void a(ResolutionAnchor resolutionAnchor, float f2) {
        if (this.i == 0 || !(this.e == resolutionAnchor || this.f == f2)) {
            this.e = resolutionAnchor;
            this.f = f2;
            if (this.i == 1) {
                c();
            }
            d();
        }
    }

    public final void a() {
        float f2;
        float f3;
        float f4;
        boolean z = true;
        if (this.i != 1 && this.g != 4) {
            if (this.l != null) {
                if (this.l.i == 1) {
                    this.d = ((float) this.m) * this.l.a;
                } else {
                    return;
                }
            }
            if (this.n != null) {
                if (this.n.i == 1) {
                    this.k = ((float) this.o) * this.n.a;
                } else {
                    return;
                }
            }
            if (this.g == 1 && (this.c == null || this.c.i == 1)) {
                if (this.c == null) {
                    this.e = this;
                    this.f = this.d;
                } else {
                    this.e = this.c.e;
                    this.f = this.c.f + this.d;
                }
                d();
            } else if (this.g == 2 && this.c != null && this.c.i == 1 && this.j != null && this.j.c != null && this.j.c.i == 1) {
                if (LinearSystem.a() != null) {
                    Metrics a2 = LinearSystem.a();
                    a2.w++;
                }
                this.e = this.c.e;
                this.j.e = this.j.c.e;
                int i = 0;
                if (!(this.a.c == Type.RIGHT || this.a.c == Type.BOTTOM)) {
                    z = false;
                }
                if (z) {
                    f2 = this.c.f - this.j.c.f;
                } else {
                    f2 = this.j.c.f - this.c.f;
                }
                if (this.a.c == Type.LEFT || this.a.c == Type.RIGHT) {
                    f4 = f2 - ((float) this.a.b.n());
                    f3 = this.a.b.Y;
                } else {
                    f4 = f2 - ((float) this.a.b.o());
                    f3 = this.a.b.Z;
                }
                int b2 = this.a.b();
                int b3 = this.j.a.b();
                if (this.a.d == this.j.a.d) {
                    f3 = 0.5f;
                    b3 = 0;
                } else {
                    i = b2;
                }
                float f5 = (float) i;
                float f6 = (float) b3;
                float f7 = (f4 - f5) - f6;
                if (z) {
                    this.j.f = this.j.c.f + f6 + (f7 * f3);
                    this.f = (this.c.f - f5) - (f7 * (1.0f - f3));
                } else {
                    this.f = this.c.f + f5 + (f7 * f3);
                    this.j.f = (this.j.c.f - f6) - (f7 * (1.0f - f3));
                }
                d();
                this.j.d();
            } else if (this.g != 3 || this.c == null || this.c.i != 1 || this.j == null || this.j.c == null || this.j.c.i != 1) {
                if (this.g == 5) {
                    this.a.b.c();
                }
            } else {
                if (LinearSystem.a() != null) {
                    Metrics a3 = LinearSystem.a();
                    a3.x++;
                }
                this.e = this.c.e;
                this.j.e = this.j.c.e;
                this.f = this.c.f + this.d;
                this.j.f = this.j.c.f + this.j.d;
                d();
                this.j.d();
            }
        }
    }

    public final void b() {
        super.b();
        this.c = null;
        this.d = 0.0f;
        this.l = null;
        this.m = 1;
        this.n = null;
        this.o = 1;
        this.e = null;
        this.f = 0.0f;
        this.b = 0.0f;
        this.j = null;
        this.k = 0.0f;
        this.g = 0;
    }

    public final void a(ResolutionAnchor resolutionAnchor, int i) {
        this.g = 1;
        this.c = resolutionAnchor;
        this.d = (float) i;
        this.c.a(this);
    }

    public final void b(ResolutionAnchor resolutionAnchor, int i) {
        this.c = resolutionAnchor;
        this.d = (float) i;
        this.c.a(this);
    }

    public final void a(ResolutionAnchor resolutionAnchor, int i, ResolutionDimension resolutionDimension) {
        this.c = resolutionAnchor;
        this.c.a(this);
        this.l = resolutionDimension;
        this.m = i;
        this.l.a(this);
    }

    public final void b(ResolutionAnchor resolutionAnchor, float f2) {
        this.j = resolutionAnchor;
        this.k = f2;
    }

    public final void b(ResolutionAnchor resolutionAnchor, int i, ResolutionDimension resolutionDimension) {
        this.j = resolutionAnchor;
        this.n = resolutionDimension;
        this.o = i;
    }

    /* access modifiers changed from: 0000 */
    public final void a(LinearSystem linearSystem) {
        SolverVariable solverVariable = this.a.i;
        if (this.e == null) {
            linearSystem.a(solverVariable, (int) (this.f + 0.5f));
        } else {
            linearSystem.c(solverVariable, linearSystem.a((Object) this.e.a), (int) (this.f + 0.5f), 6);
        }
    }
}
