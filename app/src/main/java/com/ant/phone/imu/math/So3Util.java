package com.ant.phone.imu.math;

public class So3Util {
    private static Vector3d a = new Vector3d();
    private static Vector3d b = new Vector3d();
    private static Vector3d c = new Vector3d();
    private static Vector3d d = new Vector3d();
    private static Vector3d e = new Vector3d();
    private static Matrix3x3d f = new Matrix3x3d();
    private static Matrix3x3d g = new Matrix3x3d();
    private static Vector3d h = new Vector3d();
    private static Vector3d i = new Vector3d();

    public static void a(Vector3d a2, Vector3d b2, Matrix3x3d result) {
        Vector3d.b(a2, b2, b);
        if (b.c() != 0.0d) {
            c.a(a2);
            d.a(b2);
            b.b();
            c.b();
            d.b();
            Matrix3x3d r1 = f;
            r1.a(0, c);
            r1.a(1, b);
            Vector3d.b(b, c, a);
            r1.a(2, a);
            Matrix3x3d r2 = g;
            r2.a(0, d);
            r2.a(1, b);
            Vector3d.b(b, d, a);
            r2.a(2, a);
            r1.c();
            Matrix3x3d.b(r2, r1, result);
        } else if (Vector3d.a(a2, b2) >= 0.0d) {
            result.b();
        } else {
            Vector3d.b(a2, e);
            b(e, result);
        }
    }

    private static void b(Vector3d v, Matrix3x3d result) {
        i.a(v);
        i.a(3.141592653589793d / i.c());
        a(i, 0.0d, 0.20264236728467558d, result);
    }

    public static void a(Vector3d w, Matrix3x3d result) {
        double kA;
        double kB;
        double thetaSq = Vector3d.a(w, w);
        double theta = Math.sqrt(thetaSq);
        if (thetaSq < 1.0E-8d) {
            kA = 1.0d - (0.1666666716337204d * thetaSq);
            kB = 0.5d;
        } else if (thetaSq < 1.0E-6d) {
            kB = 0.5d - (0.0416666679084301d * thetaSq);
            kA = 1.0d - ((0.1666666716337204d * thetaSq) * (1.0d - (0.1666666716337204d * thetaSq)));
        } else {
            double invTheta = 1.0d / theta;
            kA = Math.sin(theta) * invTheta;
            kB = (1.0d - Math.cos(theta)) * invTheta * invTheta;
        }
        a(w, kA, kB, result);
    }

    public static void a(Matrix3x3d so3, Vector3d result) {
        double cosAngle = (((so3.a(0, 0) + so3.a(1, 1)) + so3.a(2, 2)) - 1.0d) * 0.5d;
        result.a((so3.a(2, 1) - so3.a(1, 2)) / 2.0d, (so3.a(0, 2) - so3.a(2, 0)) / 2.0d, (so3.a(1, 0) - so3.a(0, 1)) / 2.0d);
        double sinAngleAbs = result.c();
        if (cosAngle > 0.7071067811865476d) {
            if (sinAngleAbs > 0.0d) {
                result.a(Math.asin(sinAngleAbs) / sinAngleAbs);
            }
        } else if (cosAngle > -0.7071067811865476d) {
            result.a(Math.acos(cosAngle) / sinAngleAbs);
        } else {
            double angle = 3.141592653589793d - Math.asin(sinAngleAbs);
            double d0 = so3.a(0, 0) - cosAngle;
            double d2 = so3.a(1, 1) - cosAngle;
            double d22 = so3.a(2, 2) - cosAngle;
            Vector3d r2 = h;
            if (d0 * d0 > d2 * d2 && d0 * d0 > d22 * d22) {
                r2.a(d0, (so3.a(1, 0) + so3.a(0, 1)) / 2.0d, (so3.a(0, 2) + so3.a(2, 0)) / 2.0d);
            } else if (d2 * d2 > d22 * d22) {
                r2.a((so3.a(1, 0) + so3.a(0, 1)) / 2.0d, d2, (so3.a(2, 1) + so3.a(1, 2)) / 2.0d);
            } else {
                r2.a((so3.a(0, 2) + so3.a(2, 0)) / 2.0d, (so3.a(2, 1) + so3.a(1, 2)) / 2.0d, d22);
            }
            if (Vector3d.a(r2, result) < 0.0d) {
                r2.a(-1.0d);
            }
            r2.b();
            r2.a(angle);
            result.a(r2);
        }
    }

    private static void a(Vector3d w, double kA, double kB, Matrix3x3d result) {
        double wx2 = w.a * w.a;
        double wy2 = w.b * w.b;
        double wz2 = w.c * w.c;
        result.a(0, 0, 1.0d - ((wy2 + wz2) * kB));
        result.a(1, 1, 1.0d - ((wx2 + wz2) * kB));
        result.a(2, 2, 1.0d - ((wx2 + wy2) * kB));
        double a2 = kA * w.c;
        double b2 = kB * w.a * w.b;
        result.a(0, 1, b2 - a2);
        result.a(1, 0, b2 + a2);
        double a3 = kA * w.b;
        double b3 = kB * w.a * w.c;
        result.a(0, 2, b3 + a3);
        result.a(2, 0, b3 - a3);
        double a4 = kA * w.a;
        double b4 = kB * w.b * w.c;
        result.a(1, 2, b4 - a4);
        result.a(2, 1, b4 + a4);
    }
}
