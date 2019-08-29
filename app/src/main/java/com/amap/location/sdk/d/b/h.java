package com.amap.location.sdk.d.b;

import com.alibaba.wireless.security.SecExceptionCode;
import com.amap.location.e.e;
import com.autonavi.widget.ui.BalloonLayout;

/* compiled from: SdkLocationStrategy */
public class h implements e {
    private boolean a = false;

    public boolean c() {
        return true;
    }

    public int d() {
        return 45000;
    }

    public int e() {
        return 3000;
    }

    public int g() {
        return SecExceptionCode.SEC_ERROR_SIMULATORDETECT;
    }

    public boolean h() {
        return true;
    }

    public boolean i() {
        return false;
    }

    public boolean j() {
        return true;
    }

    public boolean k() {
        return true;
    }

    public long l() {
        return BalloonLayout.DEFAULT_DISPLAY_DURATION;
    }

    public long m() {
        return Long.MAX_VALUE;
    }

    public long o() {
        return 20000;
    }

    public long p() {
        return 10000;
    }

    public int q() {
        return 360;
    }

    public int r() {
        return 36000000;
    }

    public boolean a() {
        return !this.a;
    }

    public boolean b() {
        return !this.a;
    }

    public int f() {
        return this.a ? 3000 : 10000;
    }

    public long n() {
        return this.a ? 5000 : 20000;
    }

    public double s() {
        return this.a ? 1.0d : 0.618d;
    }

    public synchronized void a(boolean z) {
        this.a = z;
    }
}
