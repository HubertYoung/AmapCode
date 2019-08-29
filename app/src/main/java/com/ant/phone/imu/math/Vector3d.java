package com.ant.phone.imu.math;

public class Vector3d {
    public double a;
    public double b;
    public double c;

    public final void a(double xx, double yy, double zz) {
        this.a = xx;
        this.b = yy;
        this.c = zz;
    }

    public final void a(int i, double val) {
        if (i == 0) {
            this.a = val;
        } else if (i == 1) {
            this.b = val;
        } else {
            this.c = val;
        }
    }

    public final void a() {
        this.c = 0.0d;
        this.b = 0.0d;
        this.a = 0.0d;
    }

    public final void a(Vector3d other) {
        this.a = other.a;
        this.b = other.b;
        this.c = other.c;
    }

    public final void a(double s) {
        this.a *= s;
        this.b *= s;
        this.c *= s;
    }

    public final void b() {
        double d = c();
        if (d != 0.0d) {
            a(1.0d / d);
        }
    }

    public static double a(Vector3d a2, Vector3d b2) {
        return (a2.a * b2.a) + (a2.b * b2.b) + (a2.c * b2.c);
    }

    public final double c() {
        return Math.sqrt((this.a * this.a) + (this.b * this.b) + (this.c * this.c));
    }

    public static void a(Vector3d a2, Vector3d b2, Vector3d result) {
        result.a(a2.a - b2.a, a2.b - b2.b, a2.c - b2.c);
    }

    public static void b(Vector3d a2, Vector3d b2, Vector3d result) {
        result.a((a2.b * b2.c) - (a2.c * b2.b), (a2.c * b2.a) - (a2.a * b2.c), (a2.a * b2.b) - (a2.b * b2.a));
    }

    public static void b(Vector3d v, Vector3d result) {
        int k = b(v) - 1;
        if (k < 0) {
            k = 2;
        }
        result.a();
        result.a(k, 1.0d);
        b(v, result, result);
        result.b();
    }

    private static int b(Vector3d v) {
        double xAbs = Math.abs(v.a);
        double yAbs = Math.abs(v.b);
        double zAbs = Math.abs(v.c);
        if (xAbs > yAbs) {
            if (xAbs > zAbs) {
                return 0;
            }
            return 2;
        } else if (yAbs > zAbs) {
            return 1;
        } else {
            return 2;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("{ ");
        builder.append(Double.toString(this.a));
        builder.append(", ");
        builder.append(Double.toString(this.b));
        builder.append(", ");
        builder.append(Double.toString(this.c));
        builder.append(" }");
        return builder.toString();
    }
}
