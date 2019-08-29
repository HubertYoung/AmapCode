package com.ant.phone.imu.math;

public class Matrix3x3d {
    public double[] a = new double[9];

    private void a(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        this.a[0] = m00;
        this.a[1] = m01;
        this.a[2] = m02;
        this.a[3] = m10;
        this.a[4] = m11;
        this.a[5] = m12;
        this.a[6] = m20;
        this.a[7] = m21;
        this.a[8] = m22;
    }

    public final void a(Matrix3x3d o) {
        this.a[0] = o.a[0];
        this.a[1] = o.a[1];
        this.a[2] = o.a[2];
        this.a[3] = o.a[3];
        this.a[4] = o.a[4];
        this.a[5] = o.a[5];
        this.a[6] = o.a[6];
        this.a[7] = o.a[7];
        this.a[8] = o.a[8];
    }

    public final void a() {
        this.a[0] = 0.0d;
        this.a[1] = 0.0d;
        this.a[2] = 0.0d;
        this.a[3] = 0.0d;
        this.a[4] = 0.0d;
        this.a[5] = 0.0d;
        this.a[6] = 0.0d;
        this.a[7] = 0.0d;
        this.a[8] = 0.0d;
    }

    public final void b() {
        this.a[0] = 1.0d;
        this.a[1] = 0.0d;
        this.a[2] = 0.0d;
        this.a[3] = 0.0d;
        this.a[4] = 1.0d;
        this.a[5] = 0.0d;
        this.a[6] = 0.0d;
        this.a[7] = 0.0d;
        this.a[8] = 1.0d;
    }

    public final void a(double d) {
        this.a[0] = d;
        this.a[4] = d;
        this.a[8] = d;
    }

    public final double a(int row, int col) {
        return this.a[(row * 3) + col];
    }

    public final void a(int row, int col, double value) {
        this.a[(row * 3) + col] = value;
    }

    public final void a(int col, Vector3d v) {
        this.a[col] = v.a;
        this.a[col + 3] = v.b;
        this.a[col + 6] = v.c;
    }

    public final void b(double s) {
        for (int i = 0; i < 9; i++) {
            double[] dArr = this.a;
            dArr[i] = dArr[i] * s;
        }
    }

    public final void b(Matrix3x3d b) {
        for (int i = 0; i < 9; i++) {
            double[] dArr = this.a;
            dArr[i] = dArr[i] + b.a[i];
        }
    }

    public final void c(Matrix3x3d b) {
        for (int i = 0; i < 9; i++) {
            double[] dArr = this.a;
            dArr[i] = dArr[i] - b.a[i];
        }
    }

    public final void c() {
        double tmp = this.a[1];
        this.a[1] = this.a[3];
        this.a[3] = tmp;
        double tmp2 = this.a[2];
        this.a[2] = this.a[6];
        this.a[6] = tmp2;
        double tmp3 = this.a[5];
        this.a[5] = this.a[7];
        this.a[7] = tmp3;
    }

    public final void d(Matrix3x3d result) {
        double m1 = this.a[1];
        double m2 = this.a[2];
        double m3 = this.a[5];
        result.a[0] = this.a[0];
        result.a[1] = this.a[3];
        result.a[2] = this.a[6];
        result.a[3] = m1;
        result.a[4] = this.a[4];
        result.a[5] = this.a[7];
        result.a[6] = m2;
        result.a[7] = m3;
        result.a[8] = this.a[8];
    }

    public static void a(Matrix3x3d a2, Matrix3x3d b, Matrix3x3d result) {
        result.a[0] = a2.a[0] + b.a[0];
        result.a[1] = a2.a[1] + b.a[1];
        result.a[2] = a2.a[2] + b.a[2];
        result.a[3] = a2.a[3] + b.a[3];
        result.a[4] = a2.a[4] + b.a[4];
        result.a[5] = a2.a[5] + b.a[5];
        result.a[6] = a2.a[6] + b.a[6];
        result.a[7] = a2.a[7] + b.a[7];
        result.a[8] = a2.a[8] + b.a[8];
    }

    public static void b(Matrix3x3d a2, Matrix3x3d b, Matrix3x3d result) {
        result.a((a2.a[0] * b.a[0]) + (a2.a[1] * b.a[3]) + (a2.a[2] * b.a[6]), (a2.a[0] * b.a[1]) + (a2.a[1] * b.a[4]) + (a2.a[2] * b.a[7]), (a2.a[0] * b.a[2]) + (a2.a[1] * b.a[5]) + (a2.a[2] * b.a[8]), (a2.a[3] * b.a[0]) + (a2.a[4] * b.a[3]) + (a2.a[5] * b.a[6]), (a2.a[3] * b.a[1]) + (a2.a[4] * b.a[4]) + (a2.a[5] * b.a[7]), (a2.a[3] * b.a[2]) + (a2.a[4] * b.a[5]) + (a2.a[5] * b.a[8]), (a2.a[6] * b.a[0]) + (a2.a[7] * b.a[3]) + (a2.a[8] * b.a[6]), (a2.a[6] * b.a[1]) + (a2.a[7] * b.a[4]) + (a2.a[8] * b.a[7]), (a2.a[6] * b.a[2]) + (a2.a[7] * b.a[5]) + (a2.a[8] * b.a[8]));
    }

    public static void a(Matrix3x3d a2, Vector3d v, Vector3d result) {
        result.a = (a2.a[0] * v.a) + (a2.a[1] * v.b) + (a2.a[2] * v.c);
        result.b = (a2.a[3] * v.a) + (a2.a[4] * v.b) + (a2.a[5] * v.c);
        result.c = (a2.a[6] * v.a) + (a2.a[7] * v.b) + (a2.a[8] * v.c);
    }

    private double d() {
        return ((a(0, 0) * ((a(1, 1) * a(2, 2)) - (a(2, 1) * a(1, 2)))) - (a(0, 1) * ((a(1, 0) * a(2, 2)) - (a(1, 2) * a(2, 0))))) + (a(0, 2) * ((a(1, 0) * a(2, 1)) - (a(1, 1) * a(2, 0))));
    }

    public final boolean e(Matrix3x3d result) {
        double d = d();
        if (d == 0.0d) {
            return false;
        }
        double invdet = 1.0d / d;
        result.a(((this.a[4] * this.a[8]) - (this.a[7] * this.a[5])) * invdet, (-((this.a[1] * this.a[8]) - (this.a[2] * this.a[7]))) * invdet, ((this.a[1] * this.a[5]) - (this.a[2] * this.a[4])) * invdet, (-((this.a[3] * this.a[8]) - (this.a[5] * this.a[6]))) * invdet, ((this.a[0] * this.a[8]) - (this.a[2] * this.a[6])) * invdet, (-((this.a[0] * this.a[5]) - (this.a[3] * this.a[2]))) * invdet, ((this.a[3] * this.a[7]) - (this.a[6] * this.a[4])) * invdet, (-((this.a[0] * this.a[7]) - (this.a[6] * this.a[1]))) * invdet, ((this.a[0] * this.a[4]) - (this.a[3] * this.a[1])) * invdet);
        return true;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("{ ");
        for (int i = 0; i < 9; i++) {
            builder.append(Double.toString(this.a[i]));
            if (i < 8) {
                builder.append(", ");
            }
        }
        builder.append(" }");
        return builder.toString();
    }
}
