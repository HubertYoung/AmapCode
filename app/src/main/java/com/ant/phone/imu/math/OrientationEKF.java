package com.ant.phone.imu.math;

public class OrientationEKF {
    private Matrix3x3d A = new Matrix3x3d();
    private Vector3d B = new Vector3d();
    private Matrix3x3d C = new Matrix3x3d();
    private Matrix3x3d D = new Matrix3x3d();
    private Matrix3x3d E = new Matrix3x3d();
    private Matrix3x3d F = new Matrix3x3d();
    private Matrix3x3d G = new Matrix3x3d();
    private Matrix3x3d H = new Matrix3x3d();
    private Vector3d I = new Vector3d();
    private Vector3d J = new Vector3d();
    private Vector3d K = new Vector3d();
    private Matrix3x3d L = new Matrix3x3d();
    private Matrix3x3d M = new Matrix3x3d();
    private Matrix3x3d N = new Matrix3x3d();
    private boolean O;
    private double[] a = new double[16];
    private Matrix3x3d b = new Matrix3x3d();
    private Matrix3x3d c = new Matrix3x3d();
    private Matrix3x3d d = new Matrix3x3d();
    private Matrix3x3d e = new Matrix3x3d();
    private Matrix3x3d f = new Matrix3x3d();
    private Matrix3x3d g = new Matrix3x3d();
    private Matrix3x3d h = new Matrix3x3d();
    private Matrix3x3d i = new Matrix3x3d();
    private Matrix3x3d j = new Matrix3x3d();
    private Vector3d k = new Vector3d();
    private Vector3d l = new Vector3d();
    private Vector3d m = new Vector3d();
    private Vector3d n = new Vector3d();
    private Vector3d o = new Vector3d();
    private Vector3d p = new Vector3d();
    private Vector3d q = new Vector3d();
    private long r;
    private final Vector3d s = new Vector3d();
    private double t = 0.0d;
    private double u = 0.0d;
    private float v;
    private boolean w = false;
    private int x;
    private boolean y = true;
    private Matrix3x3d z = new Matrix3x3d();

    public OrientationEKF() {
        a();
    }

    public final void a() {
        this.r = 0;
        this.b.b();
        this.c.b();
        this.d.a();
        this.d.a(25.0d);
        this.e.a();
        this.e.a(1.0d);
        this.f.a();
        this.f.a(0.0625d);
        this.g.a();
        this.g.a(0.5625d);
        this.h.a();
        this.i.a();
        this.j.a();
        this.k.a();
        this.l.a();
        this.m.a();
        this.n.a();
        this.o.a();
        this.p.a(0.0d, 0.0d, 9.81d);
        this.q.a(0.0d, 1.0d, 0.0d);
        this.O = false;
    }

    public final double[] a(double secondsAfterLastGyroEvent) {
        Vector3d pmu = this.B;
        pmu.a(this.s);
        pmu.a(-secondsAfterLastGyroEvent);
        Matrix3x3d so3PredictedMotion = this.z;
        So3Util.a(pmu, so3PredictedMotion);
        Matrix3x3d so3PredictedState = this.A;
        Matrix3x3d.b(so3PredictedMotion, this.b, so3PredictedState);
        return a(so3PredictedState);
    }

    public final synchronized void a(Vector3d gyro, long sensorTimeStamp) {
        if (this.r != 0) {
            float dT = ((float) (sensorTimeStamp - this.r)) * 1.0E-9f;
            if (dT > 0.04f) {
                dT = this.y ? this.v : 0.01f;
            } else {
                a(dT);
            }
            this.n.a(gyro);
            this.n.a((double) (-dT));
            So3Util.a(this.n, this.c);
            Matrix3x3d.b(this.c, this.b, this.b);
            b();
            this.C.a(this.e);
            this.C.b((double) (dT * dT));
            this.d.b(this.C);
        }
        this.r = sensorTimeStamp;
        this.s.a(gyro);
    }

    private void b(double currentAccelNorm) {
        double currentAccelNormChange = Math.abs(currentAccelNorm - this.t);
        this.t = currentAccelNorm;
        this.u = (0.5d * currentAccelNormChange) + (this.u * 0.5d);
        double accelNoiseSigma = Math.min(7.0d, 0.75d + (6.25d * (this.u / 0.15d)));
        this.g.a(accelNoiseSigma * accelNoiseSigma);
    }

    public final synchronized void a(Vector3d acc) {
        this.l.a(acc);
        b(this.l.c());
        if (this.O) {
            a(this.b, this.k);
            for (int dof = 0; dof < 3; dof++) {
                Vector3d delta = this.K;
                delta.a();
                delta.a(dof, 1.0E-7d);
                So3Util.a(delta, this.D);
                Matrix3x3d.b(this.D, this.b, this.E);
                a(this.E, this.I);
                Vector3d.a(this.k, this.I, this.J);
                this.J.a(1.0E7d);
                this.i.a(dof, this.J);
            }
            this.i.d(this.F);
            Matrix3x3d.b(this.d, this.F, this.G);
            Matrix3x3d.b(this.i, this.G, this.H);
            Matrix3x3d.a(this.H, this.g, this.h);
            this.h.e(this.F);
            this.i.d(this.G);
            Matrix3x3d.b(this.G, this.F, this.H);
            Matrix3x3d.b(this.d, this.H, this.j);
            Matrix3x3d.a(this.j, this.k, this.o);
            Matrix3x3d.b(this.j, this.i, this.F);
            this.G.b();
            this.G.c(this.F);
            Matrix3x3d.b(this.G, this.d, this.F);
            this.d.a(this.F);
            So3Util.a(this.o, this.c);
            Matrix3x3d.b(this.c, this.b, this.b);
            b();
        } else {
            So3Util.a(this.p, this.l, this.b);
            this.O = true;
        }
    }

    private double[] a(Matrix3x3d so3) {
        for (int r2 = 0; r2 < 3; r2++) {
            for (int c2 = 0; c2 < 3; c2++) {
                this.a[(c2 * 4) + r2] = so3.a(r2, c2);
            }
        }
        this.a[3] = 0.0d;
        this.a[7] = 0.0d;
        this.a[11] = 0.0d;
        this.a[12] = 0.0d;
        this.a[13] = 0.0d;
        this.a[14] = 0.0d;
        this.a[15] = 1.0d;
        return this.a;
    }

    private void a(float timeStep) {
        if (!this.w) {
            this.v = timeStep;
            this.x = 1;
            this.w = true;
            return;
        }
        this.v = (0.95f * this.v) + (0.050000012f * timeStep);
        int i2 = this.x + 1;
        this.x = i2;
        if (i2 > 10) {
            this.y = true;
        }
    }

    private void b() {
        this.c.d(this.L);
        Matrix3x3d.b(this.d, this.L, this.M);
        Matrix3x3d.b(this.c, this.M, this.d);
        this.c.b();
    }

    private void a(Matrix3x3d so3SensorFromWorldPred, Vector3d result) {
        Matrix3x3d.a(so3SensorFromWorldPred, this.p, this.m);
        So3Util.a(this.m, this.l, this.N);
        So3Util.a(this.N, result);
    }
}
