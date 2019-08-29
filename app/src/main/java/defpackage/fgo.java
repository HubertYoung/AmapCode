package defpackage;

import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.statist.StatisticData;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.network.domain.NetworkStats;

/* renamed from: fgo reason: default package */
/* compiled from: NetworkListenerAdapter */
public final class fgo implements a, b, d {
    int a;
    Map<String, List<String>> b;
    final String c;
    a d = null;
    boolean e = false;
    ByteArrayOutputStream f = null;
    int g = 0;
    fge h;
    fgf i;
    private volatile boolean j = false;

    public fgo(fge fge, fgf fgf, String str) {
        this.h = fge;
        this.i = fgf;
        this.c = str;
    }

    public final boolean onResponseCode(int i2, Map<String, List<String>> map, Object obj) {
        this.a = i2;
        this.b = map;
        try {
            String a2 = fcz.a(this.b, "content-length");
            if (fdd.b(a2)) {
                a2 = fcz.a(this.b, "x-bin-length");
            }
            if (fdd.a(a2)) {
                this.g = Integer.parseInt(a2);
            }
        } catch (Exception unused) {
            TBSdkLog.d("mtopsdk.NetworkListenerAdapter", this.c, "[onResponseCode]parse Response HeaderField ContentLength error ");
        }
        return false;
    }

    public final void onFinished(a aVar, Object obj) {
        synchronized (this) {
            this.d = aVar;
            if (this.e || !this.j) {
                a(aVar, obj);
            }
        }
    }

    public final void onInputStreamGet(final ParcelableInputStream parcelableInputStream, final Object obj) {
        this.j = true;
        ffy.b(new Runnable() {
            public final void run() {
                ParcelableInputStream parcelableInputStream;
                try {
                    if (TBSdkLog.a(LogEnable.DebugEnable)) {
                        TBSdkLog.a((String) "mtopsdk.NetworkListenerAdapter", fgo.this.c, (String) "[onInputStreamGet]start to read input stream");
                    }
                    fgo.this.f = new ByteArrayOutputStream(parcelableInputStream.length() > 0 ? parcelableInputStream.length() : fgo.this.g);
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = parcelableInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        if (TBSdkLog.a(LogEnable.DebugEnable)) {
                            String str = fgo.this.c;
                            StringBuilder sb = new StringBuilder("[onInputStreamGet]data chunk content: ");
                            sb.append(new String(bArr, 0, read));
                            TBSdkLog.a((String) "mtopsdk.NetworkListenerAdapter", str, sb.toString());
                        }
                        fgo.this.f.write(bArr, 0, read);
                    }
                    if (parcelableInputStream != null) {
                        try {
                            parcelableInputStream = parcelableInputStream;
                            parcelableInputStream.close();
                        } catch (Exception unused) {
                        }
                    }
                } catch (Exception e) {
                    TBSdkLog.b("mtopsdk.NetworkListenerAdapter", fgo.this.c, "[onInputStreamGet]Read data from inputstream failed.", e);
                    fgo.this.f = null;
                    if (parcelableInputStream != null) {
                        parcelableInputStream = parcelableInputStream;
                    }
                } catch (Throwable th) {
                    if (parcelableInputStream != null) {
                        try {
                            parcelableInputStream.close();
                        } catch (Exception unused2) {
                        }
                    }
                    fgp.a((Closeable) fgo.this.f);
                    throw th;
                }
                fgp.a((Closeable) fgo.this.f);
                synchronized (fgo.this) {
                    if (fgo.this.d != null) {
                        fgo.this.a(fgo.this.d, obj);
                    } else {
                        fgo.this.e = true;
                    }
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public final void a(final a aVar, final Object obj) {
        ffy.a(this.c != null ? this.c.hashCode() : hashCode(), new Runnable() {
            public final void run() {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    fgo fgo = fgo.this;
                    a aVar = aVar;
                    if (fgo.i == null) {
                        TBSdkLog.d("mtopsdk.NetworkListenerAdapter", fgo.c, "[onFinishTask]networkCallback is null");
                    } else {
                        NetworkStats networkStats = null;
                        AnonymousClass3 r6 = new fgj(fgo.f != null ? fgo.f.toByteArray() : null) {
                            final /* synthetic */ byte[] a;

                            public final InputStream b() {
                                return null;
                            }

                            {
                                this.a = r2;
                            }

                            public final long a() throws IOException {
                                if (this.a != null) {
                                    return (long) this.a.length;
                                }
                                return 0;
                            }

                            public final byte[] c() throws IOException {
                                return this.a;
                            }
                        };
                        StatisticData c2 = aVar.c();
                        if (c2 != null) {
                            networkStats = new NetworkStats();
                            networkStats.resultCode = c2.resultCode;
                            networkStats.isRequestSuccess = c2.isRequestSuccess;
                            networkStats.host = c2.host;
                            networkStats.ip_port = c2.ip_port;
                            networkStats.connectionType = c2.connectionType;
                            networkStats.isSSL = c2.isSSL;
                            networkStats.oneWayTime_ANet = c2.oneWayTime_ANet;
                            networkStats.firstDataTime = c2.firstDataTime;
                            networkStats.sendWaitTime = c2.sendBeforeTime;
                            networkStats.recDataTime = c2.recDataTime;
                            networkStats.sendSize = c2.sendSize;
                            networkStats.recvSize = c2.totalSize;
                            networkStats.serverRT = c2.serverRT;
                            networkStats.dataSpeed = c2.dataSpeed;
                            networkStats.retryTimes = c2.retryTime;
                        }
                        a aVar2 = new a();
                        aVar2.a = fgo.h.a();
                        aVar2.b = aVar.a();
                        aVar2.c = aVar.b();
                        aVar2.d = fgo.b;
                        aVar2.e = r6;
                        aVar2.f = networkStats;
                        fgo.i.a(aVar2.a());
                    }
                    if (TBSdkLog.a(LogEnable.DebugEnable)) {
                        String str = fgo.this.c;
                        StringBuilder sb = new StringBuilder("[callFinish] execute onFinishTask time[ms] ");
                        sb.append(System.currentTimeMillis() - currentTimeMillis);
                        TBSdkLog.a((String) "mtopsdk.NetworkListenerAdapter", str, sb.toString());
                    }
                } catch (Exception e) {
                    TBSdkLog.b("mtopsdk.NetworkListenerAdapter", fgo.this.c, "[callFinish]execute onFinishTask error.", e);
                }
            }
        });
    }
}
