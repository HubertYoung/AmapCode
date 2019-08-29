package com.amap.location.common.f;

import com.autonavi.gds.a.a;

/* compiled from: CoordUtil */
public class b {
    private static final a a = new a();

    private static final boolean a(double d, double d2) {
        return d < 180.0d && d > -180.0d && d2 > -90.0d && d2 < 90.0d;
    }

    public static final boolean a(double d, double d2, double[] dArr, double[] dArr2) {
        if (!a(d2, d) || dArr == null || dArr2 == null || dArr.length <= 0 || dArr2.length <= 0) {
            return false;
        }
        try {
            a.a(d2, d, dArr2, dArr);
            return a(dArr2[0], dArr[0]);
        } catch (Exception unused) {
            return false;
        }
    }

    public static final boolean b(double d, double d2, double[] dArr, double[] dArr2) {
        if (!a(d2, d) || dArr == null || dArr2 == null || dArr.length <= 0 || dArr2.length <= 0) {
            return false;
        }
        try {
            a.a(d2, d, dArr2, dArr);
            if (!a(dArr2[0], dArr[0])) {
                return false;
            }
            dArr[0] = (d + d) - dArr[0];
            dArr2[0] = (d2 + d2) - dArr2[0];
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
