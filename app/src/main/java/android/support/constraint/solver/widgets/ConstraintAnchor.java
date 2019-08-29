package android.support.constraint.solver.widgets;

import android.support.constraint.solver.SolverVariable;

public class ConstraintAnchor {
    public ResolutionAnchor a = new ResolutionAnchor(this);
    final ConstraintWidget b;
    final Type c;
    public ConstraintAnchor d;
    public int e = 0;
    int f = -1;
    Strength g = Strength.NONE;
    int h = 0;
    public SolverVariable i;
    private ConnectionType j = ConnectionType.RELAXED;

    public enum ConnectionType {
        RELAXED,
        STRICT
    }

    public enum Strength {
        NONE,
        STRONG,
        WEAK
    }

    public enum Type {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y
    }

    public ConstraintAnchor(ConstraintWidget constraintWidget, Type type) {
        this.b = constraintWidget;
        this.c = type;
    }

    public final void a() {
        if (this.i == null) {
            this.i = new SolverVariable(android.support.constraint.solver.SolverVariable.Type.UNRESTRICTED);
        } else {
            this.i.b();
        }
    }

    public final int b() {
        if (this.b.ac == 8) {
            return 0;
        }
        if (this.f < 0 || this.d == null || this.d.b.ac != 8) {
            return this.e;
        }
        return this.f;
    }

    public final void c() {
        this.d = null;
        this.e = 0;
        this.f = -1;
        this.g = Strength.STRONG;
        this.h = 0;
        this.j = ConnectionType.RELAXED;
        this.a.b();
    }

    public final boolean a(ConstraintAnchor constraintAnchor, int i2, Strength strength, int i3) {
        return a(constraintAnchor, i2, -1, strength, i3, false);
    }

    public final boolean a(ConstraintAnchor constraintAnchor, int i2, int i3, Strength strength, int i4, boolean z) {
        if (constraintAnchor == null) {
            this.d = null;
            this.e = 0;
            this.f = -1;
            this.g = Strength.NONE;
            this.h = 2;
            return true;
        } else if (!z && !a(constraintAnchor)) {
            return false;
        } else {
            this.d = constraintAnchor;
            if (i2 > 0) {
                this.e = i2;
            } else {
                this.e = 0;
            }
            this.f = i3;
            this.g = strength;
            this.h = i4;
            return true;
        }
    }

    public final boolean a(ConstraintAnchor constraintAnchor, int i2) {
        return a(constraintAnchor, 0, -1, Strength.STRONG, i2, false);
    }

    public final boolean b(ConstraintAnchor constraintAnchor, int i2) {
        return a(constraintAnchor, i2, -1, Strength.STRONG, 0, false);
    }

    public final boolean d() {
        return this.d != null;
    }

    public final void a(Strength strength) {
        if (d()) {
            this.g = strength;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.ad);
        sb.append(":");
        sb.append(this.c.toString());
        return sb.toString();
    }

    public final ConstraintAnchor e() {
        switch (this.c) {
            case CENTER:
            case BASELINE:
            case CENTER_X:
            case CENTER_Y:
            case NONE:
                return null;
            case LEFT:
                return this.b.y;
            case RIGHT:
                return this.b.w;
            case TOP:
                return this.b.z;
            case BOTTOM:
                return this.b.x;
            default:
                throw new AssertionError(this.c.name());
        }
    }

    public final boolean a(ConstraintAnchor constraintAnchor) {
        if (constraintAnchor == null) {
            return false;
        }
        Type type = constraintAnchor.c;
        if (type != this.c) {
            switch (this.c) {
                case CENTER:
                    if (type == Type.BASELINE || type == Type.CENTER_X || type == Type.CENTER_Y) {
                        return false;
                    }
                    return true;
                case LEFT:
                case RIGHT:
                    boolean z = type == Type.LEFT || type == Type.RIGHT;
                    if (constraintAnchor.b instanceof Guideline) {
                        z = z || type == Type.CENTER_X;
                    }
                    return z;
                case TOP:
                case BOTTOM:
                    boolean z2 = type == Type.TOP || type == Type.BOTTOM;
                    if (constraintAnchor.b instanceof Guideline) {
                        z2 = z2 || type == Type.CENTER_Y;
                    }
                    return z2;
                case BASELINE:
                case CENTER_X:
                case CENTER_Y:
                case NONE:
                    return false;
                default:
                    throw new AssertionError(this.c.name());
            }
        } else if (this.c != Type.BASELINE || (constraintAnchor.b.v() && this.b.v())) {
            return true;
        } else {
            return false;
        }
    }
}
