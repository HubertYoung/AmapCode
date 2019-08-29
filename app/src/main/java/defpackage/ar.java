package defpackage;

import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import com.autonavi.widget.ui.BalloonLayout;

/* renamed from: ar reason: default package */
/* compiled from: BandWidthSampler */
public final class ar {
    static int a = 0;
    static long b = 0;
    static long c = 0;
    static long d = 0;
    static long e = 0;
    static long f = 0;
    static double g = 0.0d;
    static double h = 0.0d;
    static double i = 0.0d;
    static double j = 40.0d;
    private static volatile boolean l = false;
    public int k;
    /* access modifiers changed from: private */
    public int m;
    /* access modifiers changed from: private */
    public at n;

    /* renamed from: ar$a */
    /* compiled from: BandWidthSampler */
    static class a {
        static ar a = new ar(0);
    }

    /* synthetic */ ar(byte b2) {
        this();
    }

    public static ar a() {
        return a.a;
    }

    private ar() {
        this.k = 5;
        this.m = 0;
        this.n = new at();
        NetworkStatusHelper.a((anet.channel.status.NetworkStatusHelper.a) new anet.channel.status.NetworkStatusHelper.a() {
            public final void a(NetworkStatus networkStatus) {
                ar.this.n.a();
                ar.f = 0;
                ar.this.b();
            }
        });
    }

    public final synchronized void b() {
        try {
            cl.b("awcn.BandWidthSampler", "[startNetworkMeter]", null, "NetworkStatus", NetworkStatusHelper.a());
            if (NetworkStatusHelper.a() == NetworkStatus.G2) {
                l = false;
            } else {
                l = true;
            }
        } catch (Exception e2) {
            cl.a("awcn.BandWidthSampler", "startNetworkMeter fail.", null, e2, new Object[0]);
        }
    }

    public final void a(long j2, long j3, long j4) {
        if (l) {
            if (cl.a(1)) {
                cl.a("awcn.BandWidthSampler", "onDataReceived", null, "mRequestStartTime", Long.valueOf(j2), "mRequestFinishedTime", Long.valueOf(j3), "mRequestDataSize", Long.valueOf(j4));
            }
            if (j4 > BalloonLayout.DEFAULT_DISPLAY_DURATION && j2 < j3) {
                final long j5 = j4;
                final long j6 = j3;
                final long j7 = j2;
                AnonymousClass2 r1 = new Runnable() {
                    public final void run() {
                        int i;
                        long j;
                        char c2;
                        ar.a++;
                        ar.e += j5;
                        if (ar.a == 1) {
                            ar.d = j6 - j7;
                        }
                        if (ar.a >= 2 && ar.a <= 3) {
                            if (j7 >= ar.c) {
                                ar.d += j6 - j7;
                            } else if (j7 < ar.c && j6 >= ar.c) {
                                long j2 = ar.d + (j6 - j7);
                                ar.d = j2;
                                ar.d = j2 - (ar.c - j7);
                            }
                        }
                        ar.b = j7;
                        ar.c = j6;
                        if (ar.a == 3) {
                            at a2 = ar.this.n;
                            double d2 = ((double) ar.e) / ((double) ar.d);
                            if (d2 >= 8.0d) {
                                if (a2.a == 0) {
                                    a2.i = d2;
                                    a2.h = a2.i;
                                    a2.d = a2.h * 0.1d;
                                    a2.c = a2.h * 0.02d;
                                    a2.e = a2.h * 0.1d * a2.h;
                                } else if (a2.a == 1) {
                                    a2.j = d2;
                                    a2.h = a2.j;
                                } else {
                                    double d3 = d2 - a2.j;
                                    a2.i = a2.j;
                                    a2.j = d2;
                                    a2.b = d2 / 0.95d;
                                    a2.g = a2.b - (a2.h * 0.95d);
                                    double sqrt = Math.sqrt(a2.d);
                                    if (a2.g >= 4.0d * sqrt) {
                                        a2.g = (a2.g * 0.75d) + (sqrt * 2.0d);
                                        c2 = 1;
                                    } else if (a2.g <= -4.0d * sqrt) {
                                        a2.g = (sqrt * -1.0d) + (a2.g * 0.75d);
                                        c2 = 2;
                                    } else {
                                        c2 = 0;
                                    }
                                    a2.d = Math.min(Math.max(Math.abs((a2.d * 1.05d) - ((a2.g * 0.0025d) * a2.g)), a2.d * 0.8d), a2.d * 1.25d);
                                    a2.f = a2.e / ((a2.e * 0.9025d) + a2.d);
                                    a2.h = a2.h + (d3 * 1.0526315789473684d) + (a2.f * a2.g);
                                    if (c2 == 1) {
                                        a2.h = Math.min(a2.h, a2.b);
                                    } else if (c2 == 2) {
                                        a2.h = Math.max(a2.h, a2.b);
                                    }
                                    a2.e = (1.0d - (a2.f * 0.95d)) * (a2.e + a2.c);
                                }
                                if (a2.h < 0.0d) {
                                    a2.k = a2.j * 0.7d;
                                    a2.h = a2.k;
                                } else {
                                    a2.k = a2.h;
                                }
                            } else if (a2.a == 0) {
                                a2.k = d2;
                            }
                            ar.i = (double) ((long) a2.k);
                            ar.f++;
                            ar.this.m = ar.this.m + 1;
                            if (ar.f > 30) {
                                ar.this.n.a();
                                ar.f = 3;
                            }
                            double d4 = (ar.i * 0.68d) + (ar.h * 0.27d) + (ar.g * 0.05d);
                            ar.g = ar.h;
                            ar.h = ar.i;
                            if (ar.i < ar.g * 0.65d || ar.i > ar.g * 2.0d) {
                                ar.i = d4;
                            }
                            int i2 = 5;
                            if (cl.a(1)) {
                                cl.a("awcn.BandWidthSampler", "NetworkSpeed", null, "mKalmanDataSize", Long.valueOf(ar.e), "mKalmanTimeUsed", Long.valueOf(ar.d), "speed", Double.valueOf(ar.i), "mSpeedKalmanCount", Long.valueOf(ar.f));
                            }
                            if (ar.this.m > 5 || ar.f == 2) {
                                aq.a().a(ar.i);
                                ar.this.m = 0;
                                ar arVar = ar.this;
                                if (ar.i < ar.j) {
                                    i2 = 1;
                                }
                                arVar.k = i2;
                                i = 0;
                                cl.b("awcn.BandWidthSampler", "NetworkSpeed notification!", null, "Send Network quality notification.");
                                j = 0;
                            } else {
                                j = 0;
                                i = 0;
                            }
                            ar.d = j;
                            ar.e = j;
                            ar.a = i;
                        }
                    }
                };
                ck.a(r1);
            }
        }
    }
}
