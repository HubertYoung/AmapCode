package com.autonavi.gds.a;

import java.util.Calendar;

/* compiled from: NativeCoordOffset */
public class a {
    static char[] a = {0, 0, 0, 0, 0, 0, 0, 0, 31, 0, 0, 0, '<', 0, 0, 0, '[', 0, 0, 0, 'y', 0, 0, 0, 152, 0, 0, 0, 182, 0, 0, 0, 213, 0, 0, 0, 244, 0, 0, 0, 18, 1, 0, 0, '1', 1, 0, 0, 'O', 1, 0, 0};
    static char[] b = {0, 0, 0, 0, 0, 0, 0, 0, 31, 0, 0, 0, ';', 0, 0, 0, 'Z', 0, 0, 0, 'x', 0, 0, 0, 151, 0, 0, 0, 181, 0, 0, 0, 212, 0, 0, 0, 243, 0, 0, 0, 17, 1, 0, 0, '0', 1, 0, 0, 'N', 1, 0, 0};
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private double h;
    private double i;
    private double j;
    private double k;
    private double l;
    private double m;

    private double a(double d2) {
        boolean z;
        double d3 = d2;
        if (d3 < 0.0d) {
            d3 = -d3;
            z = true;
        } else {
            z = false;
        }
        double d4 = d3 - (((double) ((int) (d3 / 6.28318530717959d))) * 6.28318530717959d);
        if (d4 > 3.141592653589793d) {
            d4 -= 3.141592653589793d;
            z = !z;
        }
        double d5 = d4 * d4;
        double d6 = d5 * d4;
        double d7 = d6 * d5;
        double d8 = d7 * d5;
        double d9 = d8 * d5;
        double d10 = (((((2.75573192239859E-6d * d9) + (d7 * 0.00833333333333333d)) + d4) - (d6 * 0.166666666666667d)) - (d8 * 1.98412698412698E-4d)) - ((d9 * d5) * 2.50521083854417E-8d);
        return z ? -d10 : d10;
    }

    public a() {
        b();
        c();
    }

    private long a(int i2, long j2, long j3) {
        this.f = i2;
        this.g = i2;
        double d2 = (double) i2;
        this.h = d2 - ((d2 * 0.357d) / 0.357d);
        if (i2 == 0) {
            this.h = 4.5990759394707507E18d;
        }
        double d3 = (double) j2;
        this.i = d3;
        double d4 = (double) j3;
        this.k = d4;
        this.j = d3;
        this.l = d4;
        this.m = 4.6139378182410732E18d;
        return j3;
    }

    private double a() {
        double d2 = (this.h * 3.14159269E8d) + 4.53806245E8d;
        double d3 = (d2 - ((double) (((int) (d2 * 0.5d)) * 2))) * 0.5d;
        this.h = d3;
        return d3;
    }

    private int a(int[] iArr, int[] iArr2) {
        if (this.d == 0) {
            return 0;
        }
        iArr[0] = this.d;
        iArr2[0] = this.c;
        return 1;
    }

    private int a(int i2, int i3, int i4) {
        int i5;
        boolean z = false;
        if ((i2 & 3) == 0 && (i2 % 100 != 0 || i2 % 400 == 0)) {
            z = true;
        }
        if (z) {
            i5 = a[i3 * 4] + i4;
        } else {
            i5 = b[i3 * 4] + i4;
        }
        int i6 = i2 - 1;
        return (((((i6 / 400) + (i6 / 4)) + (i6 * 365)) + i5) - 1) - (i6 / 100);
    }

    private int b(int[] iArr, int[] iArr2) {
        int a2 = a(iArr, iArr2);
        if (a2 != 0) {
            return a2;
        }
        Calendar instance = Calendar.getInstance();
        int timeInMillis = (int) (instance.getTimeInMillis() / 1000);
        int a3 = a(instance.get(1), instance.get(2) + 1, instance.get(5)) - this.e;
        iArr[0] = a3 / 7;
        int i2 = timeInMillis / 86400;
        iArr2[0] = ((timeInMillis % 86400) + ((a3 * 86400) % 7)) * 1000;
        return i2;
    }

    private double b(double d2, double d3) {
        double d4 = d2 * d2;
        double sqrt = Math.sqrt(d4);
        if (d4 != sqrt) {
            sqrt = Math.sqrt(d4);
        }
        double sqrt2 = Math.sqrt(sqrt);
        if (sqrt2 != d4) {
            sqrt2 = Math.sqrt(sqrt);
        }
        double a2 = (((a(6.283185307179588d * d2) * 20.0d) + (a(18.84955592153876d * d2) * 20.0d)) * 0.6667d) + (d3 * d2 * 0.1d) + (d4 * 0.1d) + d3 + d3 + d2 + 300.0d + (sqrt2 * 0.1d);
        double a3 = a(1.047197551196598d * d2) * 40.0d;
        return (((a(d2 * 0.1047197551196598d) * 300.0d) + (a(0.2617993877991495d * d2) * 150.0d)) * 0.6667d) + ((a3 + (a(3.141592653589794d * d2) * 20.0d)) * 0.6667d) + a2;
    }

    private void b() {
        this.c = 0;
        this.d = 0;
        this.e = 0;
    }

    private double c(double d2, double d3) {
        double d4 = d2 * 0.0174532925199433d;
        double a2 = 1.0d - (a(d4) * (a(d4) * 0.00669342d));
        double sqrt = Math.sqrt(a2);
        if (sqrt != a2) {
            sqrt = Math.sqrt(a2);
        }
        return (d3 * 180.0d) / ((6335552.7273521d / (a2 * sqrt)) * 3.1415926d);
    }

    public double a(double d2, double d3) {
        double d4 = ((d2 + d2) - 0.044921875d) + (3.0d * d3) + (d3 * 0.2d * d3) + (0.1d * d2 * d3);
        double d5 = d2 * d2;
        double sqrt = Math.sqrt(d5);
        if (sqrt != d5) {
            sqrt = Math.sqrt(d5);
        }
        double sqrt2 = Math.sqrt(sqrt);
        if (sqrt2 != d5) {
            sqrt2 = Math.sqrt(sqrt);
        }
        return (((a(d3 * 0.1047197551196598d) * 320.0d) + (a(0.2617993877991495d * d3) * 160.0d)) * 0.6667d) + (((a(1.047197551196598d * d3) * 40.0d) + (a(3.141592653589794d * d3) * 20.0d)) * 0.6667d) + (((a(d2 * 6.283185307179588d) * 20.0d) + (a(18.84955592153876d * d2) * 20.0d)) * 0.6667d) + d4 + (sqrt2 * 0.2d);
    }

    private double d(double d2, double d3) {
        double d4 = d2 * 0.0174532925199433d;
        double a2 = 1.0d - (a(d4) * (a(d4) * 0.00669342d));
        double sqrt = Math.sqrt(a2);
        if (a2 != sqrt) {
            sqrt = Math.sqrt(a2);
        }
        return (d3 * 180.0d) / ((Math.cos(d4) * (6378245.0d / sqrt)) * 3.1415926d);
    }

    private int a(int i2, long j2, long j3, int i3, int i4, int i5, double[] dArr, double[] dArr2) {
        int i6 = i3;
        int i7 = i5;
        long j4 = j2;
        double d2 = (double) j4;
        double d3 = d2 / 3686400.0d;
        double d4 = d2;
        double d5 = (double) j3;
        double d6 = d5 / 3686400.0d;
        if (i6 > 5000 || d3 < 72.004d || d3 > 137.8347d || d6 < 0.8293d || d6 > 55.8271d) {
            dArr[0] = 0.0d;
            dArr2[0] = 0.0d;
            return -27137;
        } else if (i2 != 0) {
            this.g = i7;
            double d7 = d6 - 35.0d;
            double d8 = d3 - 105.0d;
            double b2 = b(d8, d7);
            double a2 = a(d8, d7);
            double d9 = ((double) i6) * 0.001d;
            double d10 = ((double) i7) * 0.0174532925199433d;
            dArr[0] = (d(d6, a() + a(d10) + b2 + d9) + d3) * 3686400.0d;
            dArr2[0] = (c(d6, a() + a(d10) + a2 + d9) + d6) * 3686400.0d;
            return 0;
        } else {
            a(i7, j4, j3);
            dArr[0] = d4;
            dArr2[0] = d5;
            return 0;
        }
    }

    public int a(long j2, long j3, double[] dArr, double[] dArr2) {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        b(iArr, iArr2);
        int a2 = a(1, j2, j3, 50, iArr[0], iArr2[0], dArr, dArr2);
        if (a2 != 0) {
            dArr[0] = 2.147483647E9d;
            dArr2[0] = 2.147483647E9d;
        }
        return a2;
    }

    public int a(double d2, double d3, double[] dArr, double[] dArr2) {
        double[] dArr3 = new double[1];
        double[] dArr4 = new double[1];
        a((long) (d2 * 3686400.0d), (long) (d3 * 3686400.0d), dArr3, dArr4);
        dArr[0] = dArr3[0] / 3686400.0d;
        int intValue = new Double(dArr2[0]).intValue();
        dArr2[0] = dArr4[0] / 3686400.0d;
        return intValue;
    }

    private int c() {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        b(iArr, iArr2);
        int i2 = iArr[0];
        return a(0, 429096960, 147087360, 50, i2, iArr2[0], new double[1], new double[1]);
    }
}
