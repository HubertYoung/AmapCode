package com.amap.location.offline.d;

import android.content.Context;
import com.amap.location.common.b;
import com.amap.location.offline.c;
import com.amap.location.uptunnel.ConfigContainer;
import com.amap.location.uptunnel.UpTunnel;

/* compiled from: UpTunnelWrapper */
public class a {
    public static void a(int i) {
        UpTunnel.addCount(i);
    }

    public static void a(int i, byte[] bArr) {
        UpTunnel.reportEvent(i, bArr);
    }

    public static void a(Context context, final c cVar, com.amap.location.offline.a aVar) {
        if (cVar.b == 4 && cVar.k && aVar.a()) {
            b.a(4);
            b.a(cVar.d);
            b.d(cVar.c);
            com.amap.location.common.a.b(cVar.e);
            b.c(cVar.j);
            b.b(cVar.i);
            ConfigContainer configContainer = new ConfigContainer();
            configContainer.mHttpClient = cVar.n;
            configContainer.mEventTunnelConfig = new com.amap.location.uptunnel.b.b() {
                public final int a() {
                    return 10;
                }

                public final void b() {
                }

                public final int f() {
                    return 10000;
                }

                public final long c() {
                    if (cVar.o != null) {
                        return cVar.o.a;
                    }
                    return 100;
                }

                public final long d() {
                    if (cVar.o != null) {
                        return cVar.o.e;
                    }
                    return 300000;
                }

                public final long e() {
                    if (cVar.o != null) {
                        return cVar.o.d;
                    }
                    return 60000;
                }

                public final long a(int i) {
                    if (cVar.o != null) {
                        return cVar.o.f;
                    }
                    return 1000;
                }

                public final long g() {
                    if (cVar.o != null) {
                        return cVar.o.b;
                    }
                    return 100000;
                }

                public final long h() {
                    if (cVar.o != null) {
                        return cVar.o.c;
                    }
                    return 864000000;
                }

                public final long b(int i) {
                    if (cVar.o != null) {
                        return cVar.o.g;
                    }
                    return 5000;
                }

                public final boolean c(int i) {
                    if (i == 1) {
                        return true;
                    }
                    boolean z = false;
                    if (i != 0) {
                        return false;
                    }
                    if (cVar.o != null) {
                        z = cVar.o.h;
                    }
                    return z;
                }
            };
            configContainer.mCountTunnelConfig = new com.amap.location.uptunnel.b.a() {
                public final long a() {
                    return 10;
                }

                public final void b() {
                }

                public final int f() {
                    return 10000;
                }

                public final long c() {
                    if (cVar.o != null) {
                        return cVar.o.a;
                    }
                    return 100;
                }

                public final long d() {
                    if (cVar.o != null) {
                        return cVar.o.e;
                    }
                    return 300000;
                }

                public final long e() {
                    if (cVar.o != null) {
                        return cVar.o.d;
                    }
                    return 60000;
                }

                public final long a(int i) {
                    if (cVar.o != null) {
                        return cVar.o.f;
                    }
                    return 1000;
                }

                public final long g() {
                    if (cVar.o != null) {
                        return cVar.o.b;
                    }
                    return 100000;
                }

                public final long h() {
                    if (cVar.o != null) {
                        return cVar.o.c;
                    }
                    return 864000000;
                }

                public final long b(int i) {
                    if (cVar.o != null) {
                        return cVar.o.g;
                    }
                    return 5000;
                }

                public final boolean c(int i) {
                    if (i == 1) {
                        return true;
                    }
                    boolean z = false;
                    if (i != 0) {
                        return false;
                    }
                    if (cVar.o != null) {
                        z = cVar.o.h;
                    }
                    return z;
                }
            };
            UpTunnel.init(context, configContainer);
        }
    }

    public static void a(c cVar) {
        if (cVar != null && cVar.b == 4) {
            UpTunnel.destroy();
        }
    }
}
