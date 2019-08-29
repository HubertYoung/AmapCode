package com.amap.api.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import com.amap.location.e.d.f;
import com.amap.location.sdk.a.e;
import com.amap.location.sdk.d.b;
import com.amap.location.sdk.e.c;
import com.amap.location.sdk.e.d;
import com.amap.location.sdk.fusion.ILocationCallback;
import com.amap.location.sdk.fusion.ILocationService.Stub;
import com.amap.location.sdk.fusion.IStatusCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.amap.location.uptunnel.UpTunnel;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class LocationServiceImpl extends Stub {
    private a<ILocationCallback> a = null;
    /* access modifiers changed from: private */
    public Location b = null;
    /* access modifiers changed from: private */
    public volatile b c;
    private com.amap.location.sdk.b.b d;
    private final Object e = new Object();
    private volatile JSONObject f;
    /* access modifiers changed from: private */
    public IStatusCallback g;
    private List<Runnable> h = new ArrayList();
    /* access modifiers changed from: private */
    public Context i;
    private LocationListener j = new LocationListener() {
        public void onLocationChanged(Location location) {
            LocationServiceImpl.this.b = location;
            LocationServiceImpl.this.c();
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            if ("sub_gps_switch".equals(str)) {
                IStatusCallback d = LocationServiceImpl.this.g;
                if (d != null) {
                    try {
                        d.a(str, (long) i, System.currentTimeMillis(), null);
                        return;
                    } catch (Throwable unused) {
                    }
                }
                return;
            }
            LocationServiceImpl.this.a(str, i, bundle);
        }

        public void onProviderEnabled(String str) {
            LocationServiceImpl.this.c(str);
        }

        public void onProviderDisabled(String str) {
            LocationServiceImpl.this.d(str);
        }
    };

    final class a<E extends IInterface> extends RemoteCallbackList<E> {
        private a() {
        }

        public final void onCallbackDied(E e, Object obj) {
            super.onCallbackDied(e, obj);
            unregister(e);
            LocationServiceImpl.this.e();
            com.amap.location.common.d.a.c("mainservice", "AMap RemoteCallbackList died:".concat(String.valueOf(e)));
        }
    }

    public LocationServiceImpl(Context context) {
        this.i = context;
    }

    public void a(int i2, long j2, float f2, boolean z, ILocationCallback iLocationCallback) throws RemoteException {
        synchronized (this.e) {
            if (this.a == null) {
                this.a = new a<>();
            }
            if (iLocationCallback != null) {
                this.a.register(iLocationCallback);
            }
        }
        synchronized (this.h) {
            if (this.c != null) {
                this.c.a(i2, j2, f2, z);
            } else {
                List<Runnable> list = this.h;
                final int i3 = i2;
                final long j3 = j2;
                final float f3 = f2;
                final boolean z2 = z;
                AnonymousClass1 r1 = new Runnable() {
                    public void run() {
                        LocationServiceImpl.this.c.a(i3, j3, f3, z2);
                    }
                };
                list.add(r1);
            }
        }
    }

    public void a(ILocationCallback iLocationCallback) throws RemoteException {
        synchronized (this.e) {
            if (!(this.a == null || iLocationCallback == null)) {
                this.a.unregister(iLocationCallback);
            }
        }
        e();
    }

    public void a(IStatusCallback iStatusCallback) throws RemoteException {
        this.g = iStatusCallback;
    }

    public void a(String str) throws RemoteException {
        com.amap.location.icecream.b.a().b(str);
        try {
            final JSONObject jSONObject = new JSONObject(str);
            final boolean z = this.f == null || !this.f.optString("tid", "").equals(jSONObject.optString("tid", ""));
            final boolean z2 = this.f == null || !this.f.optString(LocationParams.PARA_COMMON_DIU, "").equals(jSONObject.optString(LocationParams.PARA_COMMON_DIU, ""));
            final boolean z3 = this.f == null || !this.f.optString(LocationParams.PARA_COMMON_ADIU, "").equals(jSONObject.optString(LocationParams.PARA_COMMON_ADIU, ""));
            if (z || z2 || z3) {
                final String optString = jSONObject.optString("tid", "");
                final String optString2 = jSONObject.optString(LocationParams.PARA_COMMON_DIU, "");
                final String optString3 = jSONObject.optString(LocationParams.PARA_COMMON_ADIU, "");
                if (!TextUtils.isEmpty(optString) || !TextUtils.isEmpty(optString2)) {
                    AnonymousClass2 r4 = new Thread("setDeviceInfo") {
                        public void run() {
                            try {
                                if (z && !TextUtils.isEmpty(optString)) {
                                    com.amap.location.common.a.a(LocationServiceImpl.this.i, optString);
                                }
                                if (z2 && !TextUtils.isEmpty(optString2)) {
                                    com.amap.location.common.a.a(optString2);
                                }
                                if (z3 && !TextUtils.isEmpty(optString3)) {
                                    com.amap.location.common.a.b(LocationServiceImpl.this.i, optString3);
                                }
                            } catch (Exception e2) {
                                com.amap.location.common.d.a.a((Throwable) e2);
                            }
                        }
                    };
                    r4.start();
                }
            }
            if ("amap_auto".equalsIgnoreCase(b.c())) {
                String optString4 = jSONObject.optString(LocationParams.PARA_AUTO_LOG_PATH, "");
                if (!TextUtils.isEmpty(optString4)) {
                    c.a(optString4);
                    d.a(this.i);
                }
            }
            e.b(str);
            synchronized (this.h) {
                if (this.c != null) {
                    this.c.a(jSONObject, true);
                } else {
                    this.h.add(new Runnable() {
                        public void run() {
                            LocationServiceImpl.this.c.a(jSONObject, true);
                        }
                    });
                }
            }
            if (this.d != null) {
                this.d.a(jSONObject);
            }
            this.f = jSONObject;
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }

    public String b(String str) throws RemoteException {
        if (this.c != null) {
            return this.c.a(str);
        }
        return null;
    }

    public void a() throws RemoteException {
        if (this.d != null) {
            this.d.c();
        }
    }

    public void a(String str, int i2, int i3, String str2) throws RemoteException {
        char c2 = 65535;
        try {
            int hashCode = str.hashCode();
            boolean z = true;
            if (hashCode != -659101405) {
                if (hashCode != -143757817) {
                    if (hashCode != 76710336) {
                        if (hashCode == 884854752) {
                            if (str.equals(LocationParams.PARA_COMMAND_NAVI)) {
                                c2 = 0;
                            }
                        }
                    } else if (str.equals(LocationParams.PARA_COMMAND_AMAP_CLOUD)) {
                        c2 = 2;
                    }
                } else if (str.equals(LocationParams.PARA_FEEDBAK_ENGINE)) {
                    c2 = 3;
                }
            } else if (str.equals(LocationParams.PARA_COMMAND_TESTURL)) {
                c2 = 1;
            }
            switch (c2) {
                case 0:
                    b bVar = this.c;
                    if (i2 <= 0) {
                        z = false;
                    }
                    bVar.a(z);
                    return;
                case 1:
                    if (!TextUtils.isEmpty(str2)) {
                        JSONObject jSONObject = new JSONObject(str2);
                        UpTunnel.sUseTestNet = jSONObject.optBoolean(LocationParams.PARA_UPTUNNEL_USETEST, false);
                        com.amap.location.a.a.a.a = jSONObject.optBoolean(LocationParams.PARA_CLOUD_USETEST, false);
                        com.amap.location.b.a.a = jSONObject.optBoolean(LocationParams.PARA_COLLECTION_USETEST, false);
                        com.amap.location.offline.c.a = jSONObject.optBoolean(LocationParams.PARA_OFFLINE_USETEST, false);
                        this.c.b(jSONObject.optBoolean(LocationParams.PARA_NL_USETEST, false));
                        String optString = jSONObject.optString(LocationParams.PARA_AOS_SERVER, "");
                        if (!TextUtils.isEmpty(optString)) {
                            f.a(optString);
                        }
                        String optString2 = jSONObject.optString(LocationParams.PARA_APS_SERVER, "");
                        if (!TextUtils.isEmpty(optString2)) {
                            f.b(optString2);
                        }
                        return;
                    }
                    break;
                case 2:
                    if (!TextUtils.isEmpty(str2)) {
                        try {
                            com.amap.location.sdk.e.a.a(this.i, new JSONObject(str2));
                            return;
                        } catch (Exception e2) {
                            com.amap.location.common.d.a.a((Throwable) e2);
                            return;
                        }
                    }
                    break;
                case 3:
                    d.a();
                    com.amap.location.sdk.b.a.d.a(com.amap.location.uptunnel.a.c.a(4));
                    com.amap.location.sdk.b.a.d.a(com.amap.location.uptunnel.a.c.a(3));
                    break;
            }
        } catch (Exception e3) {
            com.amap.location.common.d.a.a((Throwable) e3);
        }
    }

    public void a(b bVar) {
        synchronized (this.h) {
            if (bVar != null) {
                try {
                    this.c = bVar;
                    if (this.h.size() > 0) {
                        Handler handler = new Handler();
                        for (Runnable post : this.h) {
                            handler.post(post);
                        }
                        this.h.clear();
                    }
                } finally {
                }
            }
        }
    }

    public void a(com.amap.location.sdk.b.b bVar) {
        this.d = bVar;
    }

    public LocationListener b() {
        return this.j;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005b, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean c() {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.Object r0 = r7.e     // Catch:{ all -> 0x005f }
            monitor-enter(r0)     // Catch:{ all -> 0x005f }
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r1 = r7.a     // Catch:{ all -> 0x005c }
            r2 = 0
            if (r1 != 0) goto L_0x0016
            r7.d()     // Catch:{ all -> 0x005c }
            java.lang.String r1 = "mainservice"
            java.lang.String r3 = "call back list is null so stop request"
            com.amap.location.common.d.a.c(r1, r3)     // Catch:{ all -> 0x005c }
            monitor-exit(r0)     // Catch:{ all -> 0x005c }
            monitor-exit(r7)
            return r2
        L_0x0016:
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r1 = r7.a     // Catch:{ all -> 0x005c }
            int r1 = r1.beginBroadcast()     // Catch:{ all -> 0x005c }
        L_0x001c:
            if (r2 >= r1) goto L_0x0047
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r3 = r7.a     // Catch:{ Exception -> 0x002c }
            android.os.IInterface r3 = r3.getBroadcastItem(r2)     // Catch:{ Exception -> 0x002c }
            com.amap.location.sdk.fusion.ILocationCallback r3 = (com.amap.location.sdk.fusion.ILocationCallback) r3     // Catch:{ Exception -> 0x002c }
            android.location.Location r4 = r7.b     // Catch:{ Exception -> 0x002c }
            r3.a(r4)     // Catch:{ Exception -> 0x002c }
            goto L_0x0044
        L_0x002c:
            r3 = move-exception
            java.lang.String r4 = "mainservice"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005c }
            java.lang.String r6 = "callback error:"
            r5.<init>(r6)     // Catch:{ all -> 0x005c }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005c }
            r5.append(r3)     // Catch:{ all -> 0x005c }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x005c }
            com.amap.location.common.d.a.c(r4, r3)     // Catch:{ all -> 0x005c }
        L_0x0044:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0047:
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r2 = r7.a     // Catch:{ all -> 0x005c }
            r2.finishBroadcast()     // Catch:{ all -> 0x005c }
            if (r1 > 0) goto L_0x0058
            r7.d()     // Catch:{ all -> 0x005c }
            java.lang.String r1 = "mainservice"
            java.lang.String r2 = "call back which can brd is null so stop request"
            com.amap.location.common.d.a.c(r1, r2)     // Catch:{ all -> 0x005c }
        L_0x0058:
            monitor-exit(r0)     // Catch:{ all -> 0x005c }
            monitor-exit(r7)
            r0 = 1
            return r0
        L_0x005c:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005c }
            throw r1     // Catch:{ all -> 0x005f }
        L_0x005f:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.service.LocationServiceImpl.c():boolean");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0059, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean a(java.lang.String r8, int r9, android.os.Bundle r10) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.Object r0 = r7.e     // Catch:{ all -> 0x005d }
            monitor-enter(r0)     // Catch:{ all -> 0x005d }
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r1 = r7.a     // Catch:{ all -> 0x005a }
            r2 = 0
            if (r1 != 0) goto L_0x0016
            r7.d()     // Catch:{ all -> 0x005a }
            java.lang.String r8 = "mainservice"
            java.lang.String r9 = "call back status list is null so stop request"
            com.amap.location.common.d.a.c(r8, r9)     // Catch:{ all -> 0x005a }
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            monitor-exit(r7)
            return r2
        L_0x0016:
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r1 = r7.a     // Catch:{ all -> 0x005a }
            int r1 = r1.beginBroadcast()     // Catch:{ all -> 0x005a }
        L_0x001c:
            if (r2 >= r1) goto L_0x0045
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r3 = r7.a     // Catch:{ Exception -> 0x002a }
            android.os.IInterface r3 = r3.getBroadcastItem(r2)     // Catch:{ Exception -> 0x002a }
            com.amap.location.sdk.fusion.ILocationCallback r3 = (com.amap.location.sdk.fusion.ILocationCallback) r3     // Catch:{ Exception -> 0x002a }
            r3.a(r8, r9, r10)     // Catch:{ Exception -> 0x002a }
            goto L_0x0042
        L_0x002a:
            r3 = move-exception
            java.lang.String r4 = "mainservice"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005a }
            java.lang.String r6 = "callback status error:"
            r5.<init>(r6)     // Catch:{ all -> 0x005a }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005a }
            r5.append(r3)     // Catch:{ all -> 0x005a }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x005a }
            com.amap.location.common.d.a.c(r4, r3)     // Catch:{ all -> 0x005a }
        L_0x0042:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0045:
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r8 = r7.a     // Catch:{ all -> 0x005a }
            r8.finishBroadcast()     // Catch:{ all -> 0x005a }
            if (r1 > 0) goto L_0x0056
            r7.d()     // Catch:{ all -> 0x005a }
            java.lang.String r8 = "mainservice"
            java.lang.String r9 = "call back status which can brd is null so stop request"
            com.amap.location.common.d.a.c(r8, r9)     // Catch:{ all -> 0x005a }
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            monitor-exit(r7)
            r8 = 1
            return r8
        L_0x005a:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            throw r8     // Catch:{ all -> 0x005d }
        L_0x005d:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.service.LocationServiceImpl.a(java.lang.String, int, android.os.Bundle):boolean");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0059, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean c(java.lang.String r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.Object r0 = r7.e     // Catch:{ all -> 0x005d }
            monitor-enter(r0)     // Catch:{ all -> 0x005d }
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r1 = r7.a     // Catch:{ all -> 0x005a }
            r2 = 0
            if (r1 != 0) goto L_0x0016
            r7.d()     // Catch:{ all -> 0x005a }
            java.lang.String r8 = "mainservice"
            java.lang.String r1 = "call back provider enable list is null so stop request"
            com.amap.location.common.d.a.c(r8, r1)     // Catch:{ all -> 0x005a }
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            monitor-exit(r7)
            return r2
        L_0x0016:
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r1 = r7.a     // Catch:{ all -> 0x005a }
            int r1 = r1.beginBroadcast()     // Catch:{ all -> 0x005a }
        L_0x001c:
            if (r2 >= r1) goto L_0x0045
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r3 = r7.a     // Catch:{ Exception -> 0x002a }
            android.os.IInterface r3 = r3.getBroadcastItem(r2)     // Catch:{ Exception -> 0x002a }
            com.amap.location.sdk.fusion.ILocationCallback r3 = (com.amap.location.sdk.fusion.ILocationCallback) r3     // Catch:{ Exception -> 0x002a }
            r3.a(r8)     // Catch:{ Exception -> 0x002a }
            goto L_0x0042
        L_0x002a:
            r3 = move-exception
            java.lang.String r4 = "mainservice"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005a }
            java.lang.String r6 = "callback provider enable error:"
            r5.<init>(r6)     // Catch:{ all -> 0x005a }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005a }
            r5.append(r3)     // Catch:{ all -> 0x005a }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x005a }
            com.amap.location.common.d.a.c(r4, r3)     // Catch:{ all -> 0x005a }
        L_0x0042:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0045:
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r8 = r7.a     // Catch:{ all -> 0x005a }
            r8.finishBroadcast()     // Catch:{ all -> 0x005a }
            if (r1 > 0) goto L_0x0056
            r7.d()     // Catch:{ all -> 0x005a }
            java.lang.String r8 = "mainservice"
            java.lang.String r1 = "call back provider enable which can brd is null so stop request"
            com.amap.location.common.d.a.c(r8, r1)     // Catch:{ all -> 0x005a }
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            monitor-exit(r7)
            r8 = 1
            return r8
        L_0x005a:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            throw r8     // Catch:{ all -> 0x005d }
        L_0x005d:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.service.LocationServiceImpl.c(java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0059, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean d(java.lang.String r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.Object r0 = r7.e     // Catch:{ all -> 0x005d }
            monitor-enter(r0)     // Catch:{ all -> 0x005d }
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r1 = r7.a     // Catch:{ all -> 0x005a }
            r2 = 0
            if (r1 != 0) goto L_0x0016
            r7.d()     // Catch:{ all -> 0x005a }
            java.lang.String r8 = "mainservice"
            java.lang.String r1 = "call back provider disable list is null so stop request"
            com.amap.location.common.d.a.c(r8, r1)     // Catch:{ all -> 0x005a }
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            monitor-exit(r7)
            return r2
        L_0x0016:
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r1 = r7.a     // Catch:{ all -> 0x005a }
            int r1 = r1.beginBroadcast()     // Catch:{ all -> 0x005a }
        L_0x001c:
            if (r2 >= r1) goto L_0x0045
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r3 = r7.a     // Catch:{ Exception -> 0x002a }
            android.os.IInterface r3 = r3.getBroadcastItem(r2)     // Catch:{ Exception -> 0x002a }
            com.amap.location.sdk.fusion.ILocationCallback r3 = (com.amap.location.sdk.fusion.ILocationCallback) r3     // Catch:{ Exception -> 0x002a }
            r3.b(r8)     // Catch:{ Exception -> 0x002a }
            goto L_0x0042
        L_0x002a:
            r3 = move-exception
            java.lang.String r4 = "mainservice"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005a }
            java.lang.String r6 = "callback provider disable error:"
            r5.<init>(r6)     // Catch:{ all -> 0x005a }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x005a }
            r5.append(r3)     // Catch:{ all -> 0x005a }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x005a }
            com.amap.location.common.d.a.c(r4, r3)     // Catch:{ all -> 0x005a }
        L_0x0042:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0045:
            com.amap.api.service.LocationServiceImpl$a<com.amap.location.sdk.fusion.ILocationCallback> r8 = r7.a     // Catch:{ all -> 0x005a }
            r8.finishBroadcast()     // Catch:{ all -> 0x005a }
            if (r1 > 0) goto L_0x0056
            r7.d()     // Catch:{ all -> 0x005a }
            java.lang.String r8 = "mainservice"
            java.lang.String r1 = "call back provider disable which can brd is null so stop request"
            com.amap.location.common.d.a.c(r8, r1)     // Catch:{ all -> 0x005a }
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            monitor-exit(r7)
            r8 = 1
            return r8
        L_0x005a:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            throw r8     // Catch:{ all -> 0x005d }
        L_0x005d:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.service.LocationServiceImpl.d(java.lang.String):boolean");
    }

    private void d() {
        e();
        synchronized (this.e) {
            try {
                if (this.a != null) {
                    this.a.kill();
                }
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
            this.a = null;
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        synchronized (this.h) {
            if (this.c != null) {
                this.c.a();
            } else {
                this.h.add(new Runnable() {
                    public void run() {
                        LocationServiceImpl.this.c.a();
                    }
                });
            }
        }
    }
}
