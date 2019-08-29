package com.amap.location.sdk.a;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.text.TextUtils;
import com.alipay.mobile.mrtc.api.constants.APCallCode;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.sdk.d.b.d;
import com.amap.location.sdk.e.b;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: PartnerLocationProvider */
public class e {
    public static JSONObject a = b.a();
    com.amap.location.sdk.d.a b = new com.amap.location.sdk.d.a() {
        public void a(int i) {
        }

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void onLocationChanged(Location location) {
            synchronized (e.this.o) {
                e.this.f = location;
                e.this.o.notifyAll();
            }
        }
    };
    com.amap.location.sdk.d.a c = new com.amap.location.sdk.d.a() {
        public void a(int i) {
        }

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void onLocationChanged(Location location) {
            try {
                if (e.this.v != null) {
                    e.this.v.onLocationChanged(location);
                }
                if (e.this.u != null) {
                    e.this.u.onLocationChanged(location);
                }
            } catch (Exception unused) {
            }
        }
    };
    /* access modifiers changed from: private */
    public Context d;
    /* access modifiers changed from: private */
    public Location e;
    /* access modifiers changed from: private */
    public Location f = null;
    /* access modifiers changed from: private */
    public com.amap.location.sdk.d.b.e g;
    /* access modifiers changed from: private */
    public d h;
    /* access modifiers changed from: private */
    public HandlerThread i;
    /* access modifiers changed from: private */
    public boolean j;
    /* access modifiers changed from: private */
    public Handler k;
    /* access modifiers changed from: private */
    public Location l;
    private final PhoneStateListener m = new a();
    /* access modifiers changed from: private */
    public final Object n = new Object();
    /* access modifiers changed from: private */
    public final Object o = new Object();
    /* access modifiers changed from: private */
    public final Object p = new Object();
    /* access modifiers changed from: private */
    public int q = 0;
    /* access modifiers changed from: private */
    public com.amap.api.service.locationprovider.a r;
    /* access modifiers changed from: private */
    public final HashMap<String, Long> s = new HashMap<>();
    /* access modifiers changed from: private */
    public final HashMap<String, Long> t = new HashMap<>();
    /* access modifiers changed from: private */
    public LocationListener u;
    /* access modifiers changed from: private */
    public LocationListener v;
    private final Runnable w = new Runnable() {
        public void run() {
            com.amap.location.common.d.a.b("partnerloc", "destroy");
            if (e.this.g != null) {
                e.this.g.d();
            }
        }
    };

    /* compiled from: PartnerLocationProvider */
    class a extends PhoneStateListener {
        private a() {
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            synchronized (e.this.n) {
                int c = com.amap.location.g.a.a.a(e.this.d).c();
                if (c == 4) {
                    e.this.q = signalStrength.getCdmaDbm();
                } else {
                    if (!(c == 5 || c == 6)) {
                        if (c != 12) {
                            e.this.q = (signalStrength.getGsmSignalStrength() * 2) + APCallCode.CALL_ERROR_INNER;
                        }
                    }
                    e.this.q = signalStrength.getEvdoDbm();
                }
                e.this.n.notifyAll();
            }
        }
    }

    public void a(Context context) {
        this.d = context;
        this.i = new HandlerThread("partnerloc") {
            /* access modifiers changed from: protected */
            public void onLooperPrepared() {
                synchronized (e.this.i) {
                    e.this.k = new Handler(getLooper());
                    if (e.this.j) {
                        e.this.k.post(new Runnable() {
                            public void run() {
                                AnonymousClass1.this.quit();
                            }
                        });
                        return;
                    }
                    try {
                        e.this.g = new com.amap.location.sdk.d.b.e(e.this.d, e.this.c, e.a, true);
                        e.this.h = new d(e.this.d, e.this.b, getLooper());
                        e.this.r = a.a(e.this.d);
                        StringBuilder sb = new StringBuilder("partner thread:");
                        sb.append(getThreadId());
                        com.amap.location.common.d.a.b("partnerloc", sb.toString());
                    } catch (Throwable th) {
                        com.amap.location.common.d.a.a(th);
                    }
                }
            }
        };
        this.i.start();
    }

    public void a() {
        synchronized (this.i) {
            if (this.k != null) {
                this.k.removeCallbacksAndMessages(null);
                this.k.post(new Runnable() {
                    public void run() {
                        if (e.this.g != null) {
                            e.this.g.d();
                        }
                        e.this.i.quit();
                    }
                });
            } else {
                this.j = true;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0050 A[Catch:{ Exception -> 0x0172 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0073 A[Catch:{ Exception -> 0x0172 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0116 A[Catch:{ Exception -> 0x0172 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int a(android.os.Bundle r12) {
        /*
            r11 = this;
            r0 = 0
            java.lang.String r1 = "opensdk"
            boolean r1 = r12.containsKey(r1)     // Catch:{ Exception -> 0x0172 }
            if (r1 != 0) goto L_0x000b
            r12 = -1
            return r12
        L_0x000b:
            android.content.Context r1 = r11.d     // Catch:{ Exception -> 0x0172 }
            int r2 = android.os.Binder.getCallingPid()     // Catch:{ Exception -> 0x0172 }
            java.lang.String r1 = com.amap.location.sdk.a.a.a(r1, r2)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r2 = "type"
            java.lang.String r2 = r12.getString(r2)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r3 = "fine"
            boolean r3 = r3.equals(r2)     // Catch:{ Exception -> 0x0172 }
            r4 = 1
            if (r3 == 0) goto L_0x0027
        L_0x0025:
            r3 = 0
            goto L_0x0045
        L_0x0027:
            java.lang.String r3 = "corse"
            boolean r3 = r3.equals(r2)     // Catch:{ Exception -> 0x0172 }
            if (r3 == 0) goto L_0x0031
            r3 = 1
            goto L_0x0045
        L_0x0031:
            java.lang.String r3 = "wifi"
            boolean r3 = r3.equals(r2)     // Catch:{ Exception -> 0x0172 }
            if (r3 == 0) goto L_0x003c
            r3 = 2
            goto L_0x0045
        L_0x003c:
            java.lang.String r3 = "cell"
            boolean r3 = r3.equals(r2)     // Catch:{ Exception -> 0x0172 }
            if (r3 == 0) goto L_0x0025
            r3 = 3
        L_0x0045:
            r5 = 0
            java.lang.String r6 = ""
            java.lang.String r7 = "appkey"
            boolean r7 = r12.containsKey(r7)     // Catch:{ Exception -> 0x0172 }
            if (r7 != 0) goto L_0x0073
            if (r1 == 0) goto L_0x0063
            android.content.Context r4 = r11.d     // Catch:{ Exception -> 0x0172 }
            com.amap.api.service.locationprovider.a r7 = r11.r     // Catch:{ Exception -> 0x0172 }
            boolean r4 = com.amap.location.sdk.a.a.a(r4, r7, r1, r3)     // Catch:{ Exception -> 0x0172 }
            if (r4 != 0) goto L_0x005d
            goto L_0x0063
        L_0x005d:
            java.lang.String r1 = com.amap.location.sdk.a.a.a()     // Catch:{ Exception -> 0x0172 }
            goto L_0x010d
        L_0x0063:
            java.lang.String r12 = "partnerloc"
            java.lang.String r2 = "invalid facetId:"
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r1 = r2.concat(r1)     // Catch:{ Exception -> 0x0172 }
            com.amap.location.common.d.a.c(r12, r1)     // Catch:{ Exception -> 0x0172 }
            return r0
        L_0x0073:
            java.lang.String r1 = com.amap.location.sdk.a.a.b()     // Catch:{ Exception -> 0x0172 }
            if (r3 == r4) goto L_0x008b
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0172 }
            r4.<init>()     // Catch:{ Exception -> 0x0172 }
            java.lang.String r6 = "error"
            java.lang.String r7 = "invaid type"
            r4.put(r6, r7)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r6 = r4.toString()     // Catch:{ Exception -> 0x0172 }
            goto L_0x010d
        L_0x008b:
            java.lang.String r4 = "appkey"
            java.lang.String r4 = r12.getString(r4)     // Catch:{ Exception -> 0x0172 }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0172 }
            if (r5 != 0) goto L_0x00a7
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x0172 }
            byte[] r4 = com.amap.location.sdk.a.d.a(r4)     // Catch:{ Exception -> 0x0172 }
            byte[] r4 = com.amap.location.sdk.a.f.a(r4, r1)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r7 = "UTF-8"
            r5.<init>(r4, r7)     // Catch:{ Exception -> 0x0172 }
            goto L_0x00a8
        L_0x00a7:
            r5 = r4
        L_0x00a8:
            boolean r4 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0172 }
            if (r4 == 0) goto L_0x00bf
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0172 }
            r4.<init>()     // Catch:{ Exception -> 0x0172 }
            java.lang.String r6 = "error"
            java.lang.String r7 = "empty appkey"
            r4.put(r6, r7)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r6 = r4.toString()     // Catch:{ Exception -> 0x0172 }
            goto L_0x010d
        L_0x00bf:
            java.lang.Object r4 = r11.p     // Catch:{ Exception -> 0x0172 }
            monitor-enter(r4)     // Catch:{ Exception -> 0x0172 }
            java.util.HashMap<java.lang.String, java.lang.Long> r7 = r11.s     // Catch:{ all -> 0x016f }
            boolean r7 = r7.containsKey(r5)     // Catch:{ all -> 0x016f }
            if (r7 == 0) goto L_0x010c
            java.util.HashMap<java.lang.String, java.lang.Long> r7 = r11.s     // Catch:{ all -> 0x016f }
            java.lang.Object r7 = r7.get(r5)     // Catch:{ all -> 0x016f }
            java.lang.Long r7 = (java.lang.Long) r7     // Catch:{ all -> 0x016f }
            long r7 = r7.longValue()     // Catch:{ all -> 0x016f }
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x016f }
            int r9 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r9 >= 0) goto L_0x0107
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ all -> 0x016f }
            r6.<init>()     // Catch:{ all -> 0x016f }
            java.lang.String r9 = "error"
            java.lang.String r10 = "refused"
            r6.put(r9, r10)     // Catch:{ all -> 0x016f }
            java.lang.String r9 = "expire"
            r6.put(r9, r7)     // Catch:{ all -> 0x016f }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x016f }
            java.lang.String r7 = "partnerloc"
            java.lang.String r8 = "black appkey is:"
            java.lang.String r9 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x016f }
            java.lang.String r8 = r8.concat(r9)     // Catch:{ all -> 0x016f }
            java.lang.String r8 = com.amap.location.common.d.a.a(r8)     // Catch:{ all -> 0x016f }
            com.amap.location.common.d.a.c(r7, r8)     // Catch:{ all -> 0x016f }
            goto L_0x010c
        L_0x0107:
            java.util.HashMap<java.lang.String, java.lang.Long> r7 = r11.s     // Catch:{ all -> 0x016f }
            r7.remove(r5)     // Catch:{ all -> 0x016f }
        L_0x010c:
            monitor-exit(r4)     // Catch:{ all -> 0x016f }
        L_0x010d:
            r12.clear()     // Catch:{ Exception -> 0x0172 }
            boolean r4 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x0172 }
            if (r4 == 0) goto L_0x013d
            java.lang.String r4 = "partnerloc"
            java.lang.String r7 = "req type:"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r2 = r7.concat(r2)     // Catch:{ Exception -> 0x0172 }
            com.amap.location.common.d.a.b(r4, r2)     // Catch:{ Exception -> 0x0172 }
            switch(r3) {
                case 0: goto L_0x0139;
                case 1: goto L_0x0134;
                case 2: goto L_0x012f;
                case 3: goto L_0x012a;
                default: goto L_0x0129;
            }     // Catch:{ Exception -> 0x0172 }
        L_0x0129:
            goto L_0x013d
        L_0x012a:
            java.lang.String r6 = r11.b()     // Catch:{ Exception -> 0x0172 }
            goto L_0x013d
        L_0x012f:
            java.lang.String r6 = r11.d()     // Catch:{ Exception -> 0x0172 }
            goto L_0x013d
        L_0x0134:
            java.lang.String r6 = r11.c(r5)     // Catch:{ Exception -> 0x0172 }
            goto L_0x013d
        L_0x0139:
            java.lang.String r6 = r11.c()     // Catch:{ Exception -> 0x0172 }
        L_0x013d:
            java.lang.String r2 = com.amap.location.sdk.a.a.c()     // Catch:{ Exception -> 0x0172 }
            java.lang.String r3 = "UTF-8"
            byte[] r3 = r2.getBytes(r3)     // Catch:{ Exception -> 0x0172 }
            byte[] r1 = com.amap.location.sdk.a.f.b(r3, r1)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r3 = "UTF-8"
            byte[] r2 = r2.getBytes(r3)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r3 = "UTF-8"
            byte[] r3 = r6.getBytes(r3)     // Catch:{ Exception -> 0x0172 }
            byte[] r2 = com.amap.location.common.f.a.a(r2, r3)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r3 = "key"
            java.lang.String r1 = com.amap.location.sdk.a.d.a(r1)     // Catch:{ Exception -> 0x0172 }
            r12.putString(r3, r1)     // Catch:{ Exception -> 0x0172 }
            java.lang.String r1 = "result"
            java.lang.String r2 = com.amap.location.sdk.a.d.a(r2)     // Catch:{ Exception -> 0x0172 }
            r12.putString(r1, r2)     // Catch:{ Exception -> 0x0172 }
            goto L_0x0176
        L_0x016f:
            r12 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x016f }
            throw r12     // Catch:{ Exception -> 0x0172 }
        L_0x0172:
            r12 = move-exception
            com.amap.location.common.d.a.a(r12)
        L_0x0176:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.a.e.a(android.os.Bundle):int");
    }

    private String c() {
        Location location;
        if (this.h == null) {
            return "";
        }
        try {
            synchronized (this.o) {
                if (this.f == null || System.currentTimeMillis() - this.f.getTime() >= 5000) {
                    this.h.a(0, 0.0f);
                    try {
                        this.o.wait(5000);
                    } catch (InterruptedException e2) {
                        com.amap.location.common.d.a.a((Throwable) e2);
                    }
                    this.h.a();
                }
                location = this.f;
            }
            if (location != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("time", location.getTime());
                jSONObject.put("lat", location.getLatitude());
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, location.getLongitude());
                jSONObject.put("alt", location.getAltitude());
                jSONObject.put("acc", (double) location.getAccuracy());
                jSONObject.put("dir", (double) location.getBearing());
                jSONObject.put("mod", "GPS");
                jSONObject.put("type", "WGS84");
                return jSONObject.toString();
            }
            com.amap.location.common.d.a.c("partnerloc", "get gps failed");
            return "";
        } catch (Exception e3) {
            com.amap.location.common.d.a.a((Throwable) e3);
        }
    }

    private String c(final String str) {
        Location location;
        try {
            if (this.g != null) {
                if (this.k != null) {
                    synchronized (this.p) {
                        this.k.removeCallbacks(this.w);
                        if (this.e == null || System.currentTimeMillis() - this.e.getTime() >= 5000 || (str != null && (!this.t.containsKey(str) || this.t.get(str).longValue() <= System.currentTimeMillis()))) {
                            if (str != null) {
                                this.t.remove(str);
                            }
                            this.e = null;
                            this.u = new LocationListener() {
                                public void onProviderDisabled(String str) {
                                }

                                public void onProviderEnabled(String str) {
                                }

                                public void onStatusChanged(String str, int i, Bundle bundle) {
                                }

                                public void onLocationChanged(Location location) {
                                    synchronized (e.this.p) {
                                        if (str != null) {
                                            try {
                                                e.a.put("reversegeo", "0");
                                            } catch (JSONException e) {
                                                com.amap.location.common.d.a.a((Throwable) e);
                                            }
                                            e.this.g.a(e.a, true);
                                        }
                                        e.this.t.put(str, Long.valueOf(System.currentTimeMillis() + 3600000));
                                        e.this.e = location;
                                        e.this.p.notifyAll();
                                    }
                                }
                            };
                            this.g.a((com.amap.location.sdk.d.b.e.a) new com.amap.location.sdk.d.b.e.a() {
                                public void a(AmapLoc amapLoc) {
                                    if (amapLoc != null) {
                                        String rdesc = amapLoc.getRdesc();
                                        if (rdesc != null && rdesc.startsWith("60,")) {
                                            try {
                                                long parseLong = (Long.parseLong(rdesc.substring(3)) * 60 * 1000) + System.currentTimeMillis();
                                                synchronized (e.this.p) {
                                                    e.this.s.put(str, Long.valueOf(parseLong));
                                                }
                                                StringBuilder sb = new StringBuilder("server add blacklist");
                                                sb.append(com.amap.location.common.d.a.a(str));
                                                com.amap.location.common.d.a.b("partnerloc", sb.toString());
                                            } catch (Exception e) {
                                                com.amap.location.common.d.a.a((Throwable) e);
                                            }
                                        }
                                    }
                                    synchronized (e.this.p) {
                                        e.this.p.notifyAll();
                                    }
                                }
                            });
                            this.k.postDelayed(new Runnable() {
                                /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
                                /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0029 */
                                /* Code decompiled incorrectly, please refer to instructions dump. */
                                public void run() {
                                    /*
                                        r4 = this;
                                        org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0047 }
                                        r0.<init>()     // Catch:{ Throwable -> 0x0047 }
                                        java.lang.String r1 = r10     // Catch:{ Exception -> 0x0029 }
                                        if (r1 == 0) goto L_0x0022
                                        java.lang.String r1 = "channel"
                                        java.lang.String r2 = "openapi_locate"
                                        r0.put(r1, r2)     // Catch:{ Exception -> 0x0029 }
                                        java.lang.String r1 = "appkey"
                                        java.lang.String r2 = r10     // Catch:{ Exception -> 0x0029 }
                                        r0.put(r1, r2)     // Catch:{ Exception -> 0x0029 }
                                        org.json.JSONObject r1 = com.amap.location.sdk.a.e.a     // Catch:{ Exception -> 0x0029 }
                                        java.lang.String r2 = "reversegeo"
                                        java.lang.String r3 = "1"
                                        r1.put(r2, r3)     // Catch:{ Exception -> 0x0029 }
                                        goto L_0x0029
                                    L_0x0022:
                                        java.lang.String r1 = "channel"
                                        java.lang.String r2 = "amap7a"
                                        r0.put(r1, r2)     // Catch:{ Exception -> 0x0029 }
                                    L_0x0029:
                                        org.json.JSONObject r1 = com.amap.location.sdk.a.e.a     // Catch:{ Throwable -> 0x0047 }
                                        java.lang.String r2 = "aosextra"
                                        r1.put(r2, r0)     // Catch:{ Throwable -> 0x0047 }
                                        com.amap.location.sdk.a.e r0 = com.amap.location.sdk.a.e.this     // Catch:{ Throwable -> 0x0047 }
                                        com.amap.location.sdk.d.b.e r0 = r0.g     // Catch:{ Throwable -> 0x0047 }
                                        org.json.JSONObject r1 = com.amap.location.sdk.a.e.a     // Catch:{ Throwable -> 0x0047 }
                                        r2 = 1
                                        r0.a(r1, r2)     // Catch:{ Throwable -> 0x0047 }
                                        com.amap.location.sdk.a.e r0 = com.amap.location.sdk.a.e.this     // Catch:{ Throwable -> 0x0047 }
                                        com.amap.location.sdk.d.b.e r0 = r0.g     // Catch:{ Throwable -> 0x0047 }
                                        r1 = 0
                                        r0.a(r1, r1)     // Catch:{ Throwable -> 0x0047 }
                                        return
                                    L_0x0047:
                                        r0 = move-exception
                                        com.amap.location.common.d.a.a(r0)
                                        return
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.a.e.AnonymousClass8.run():void");
                                }
                            }, 10);
                            try {
                                this.p.wait(5000);
                            } catch (InterruptedException e2) {
                                com.amap.location.common.d.a.a((Throwable) e2);
                            }
                            this.g.a((com.amap.location.sdk.d.b.e.a) null);
                            this.g.a();
                            this.k.postDelayed(this.w, 60000);
                        }
                        location = this.e;
                        this.u = null;
                    }
                    if (location != null) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("time", location.getTime());
                        jSONObject.put("lat", location.getLatitude());
                        jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, location.getLongitude());
                        jSONObject.put("alt", location.getAltitude());
                        jSONObject.put("acc", (double) location.getAccuracy());
                        jSONObject.put("dir", (double) location.getBearing());
                        jSONObject.put("mod", "NETWORK");
                        try {
                            Bundle extras = location.getExtras();
                            jSONObject.put("type", "WGS84");
                            jSONObject.put("poiname", extras.getString("poiname"));
                            jSONObject.put("desc", extras.getString("desc"));
                            jSONObject.put("citycode", extras.getString("citycode"));
                            jSONObject.put(AutoJsonUtils.JSON_ADCODE, extras.getString(AutoJsonUtils.JSON_ADCODE));
                            jSONObject.put("country", extras.getString("country"));
                            jSONObject.put("province", extras.getString("province"));
                            jSONObject.put("city", extras.getString("city"));
                            jSONObject.put("district", extras.getString("district"));
                            jSONObject.put("road", extras.getString("road"));
                            jSONObject.put("street", extras.getString("street"));
                            jSONObject.put("pid", extras.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
                            jSONObject.put("flr", extras.getString("floor"));
                        } catch (Exception e3) {
                            com.amap.location.common.d.a.a((Throwable) e3);
                        }
                        return jSONObject.toString();
                    } else if (str != null) {
                        synchronized (this.p) {
                            if (this.s.containsKey(str)) {
                                long longValue = this.s.get(str).longValue();
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("error", "refused");
                                jSONObject2.put("expire", longValue);
                                String jSONObject3 = jSONObject2.toString();
                                return jSONObject3;
                            }
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put("error", com.alipay.sdk.util.e.b);
                            return jSONObject4.toString();
                        }
                    } else {
                        com.amap.location.common.d.a.b("partnerloc", "net loc failed");
                        return "";
                    }
                }
            }
            return "";
        } catch (Exception e4) {
            com.amap.location.common.d.a.a((Throwable) e4);
        }
    }

    private String d() {
        JSONObject jSONObject = new JSONObject();
        try {
            WifiManager wifiManager = (WifiManager) this.d.getSystemService("wifi");
            String str = null;
            if (wifiManager.isWifiEnabled()) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    str = connectionInfo.getBSSID();
                }
            }
            List<ScanResult> b2 = com.amap.location.g.d.a.a(this.d).b();
            JSONArray jSONArray = new JSONArray();
            if (b2 != null && b2.size() > 0) {
                for (ScanResult next : b2) {
                    if (!TextUtils.isEmpty(next.BSSID) && !TextUtils.isEmpty(next.SSID) && !"00:00:00:00:00:00".equals(next.BSSID)) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("bssid", next.BSSID);
                        jSONObject2.put("ssid", d.a(next.SSID.getBytes("UTF-8")));
                        jSONObject2.put("rssi", next.level);
                        jSONObject2.put("time", System.currentTimeMillis());
                        if (next.BSSID.equals(str)) {
                            jSONObject2.put("connected", true);
                        } else {
                            jSONObject2.put("connected", false);
                        }
                        jSONArray.put(jSONObject2);
                    }
                }
            }
            jSONObject.put("wifi", jSONArray);
            StringBuilder sb = new StringBuilder("wifi size :");
            sb.append(jSONArray.length());
            com.amap.location.common.d.a.b("partnerloc", sb.toString());
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
        return jSONObject.toString();
    }

    private void e() {
        synchronized (this.n) {
            try {
                com.amap.location.g.a.a.a(this.d).a(this.m, 256, 0, null);
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
            try {
                this.n.wait(5000);
            } catch (InterruptedException e3) {
                com.amap.location.common.d.a.a((Throwable) e3);
            }
            try {
                com.amap.location.g.a.a.a(this.d).a(this.m, 0, 0, null);
            } catch (Exception e4) {
                com.amap.location.common.d.a.a((Throwable) e4);
            }
        }
        return;
    }

    /* JADX WARNING: Removed duplicated region for block: B:113:0x01e0 A[Catch:{ Exception -> 0x035b }] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x01e7 A[Catch:{ Exception -> 0x035b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String b() {
        /*
            r17 = this;
            r1 = r17
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r3 = ""
            java.lang.String r4 = ""
            org.json.JSONArray r5 = new org.json.JSONArray     // Catch:{ Exception -> 0x035b }
            r5.<init>()     // Catch:{ Exception -> 0x035b }
            r7 = 268435455(0xfffffff, float:2.5243547E-29)
            r9 = 65535(0xffff, float:9.1834E-41)
            int r10 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x01d3 }
            r11 = 17
            if (r10 < r11) goto L_0x01da
            int r10 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x01d3 }
            r11 = 23
            if (r10 < r11) goto L_0x002e
            android.content.Context r10 = r1.d     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r11 = "android.permission.ACCESS_COARSE_LOCATION"
            int r10 = r10.checkSelfPermission(r11)     // Catch:{ Throwable -> 0x01d3 }
            if (r10 == 0) goto L_0x002e
            goto L_0x01da
        L_0x002e:
            android.content.Context r10 = r1.d     // Catch:{ Throwable -> 0x01d3 }
            com.amap.location.g.a.a r10 = com.amap.location.g.a.a.a(r10)     // Catch:{ Throwable -> 0x01d3 }
            java.util.List r10 = r10.b()     // Catch:{ Throwable -> 0x01d3 }
            if (r10 == 0) goto L_0x01da
            int r11 = r10.size()     // Catch:{ Throwable -> 0x01d3 }
            if (r11 <= 0) goto L_0x01da
            java.util.Iterator r10 = r10.iterator()     // Catch:{ Throwable -> 0x01d3 }
        L_0x0044:
            boolean r11 = r10.hasNext()     // Catch:{ Throwable -> 0x01d3 }
            if (r11 == 0) goto L_0x01da
            java.lang.Object r11 = r10.next()     // Catch:{ Throwable -> 0x01d3 }
            android.telephony.CellInfo r11 = (android.telephony.CellInfo) r11     // Catch:{ Throwable -> 0x01d3 }
            if (r11 == 0) goto L_0x01ce
            boolean r12 = r11.isRegistered()     // Catch:{ Throwable -> 0x01d3 }
            long r13 = r11.getTimeStamp()     // Catch:{ Throwable -> 0x01d3 }
            boolean r15 = r11 instanceof android.telephony.CellInfoGsm     // Catch:{ Throwable -> 0x01d3 }
            if (r15 == 0) goto L_0x00b3
            android.telephony.CellInfoGsm r11 = (android.telephony.CellInfoGsm) r11     // Catch:{ Throwable -> 0x01d3 }
            android.telephony.CellIdentityGsm r15 = r11.getCellIdentity()     // Catch:{ Throwable -> 0x01d3 }
            if (r15 == 0) goto L_0x01ce
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d3 }
            r6.<init>()     // Catch:{ Throwable -> 0x01d3 }
            int r8 = r15.getMcc()     // Catch:{ Throwable -> 0x01d3 }
            r6.append(r8)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x01d3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ae }
            r3.<init>()     // Catch:{ Throwable -> 0x00ae }
            int r8 = r15.getMnc()     // Catch:{ Throwable -> 0x00ae }
            r3.append(r8)     // Catch:{ Throwable -> 0x00ae }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00ae }
            int r4 = r15.getLac()     // Catch:{ Throwable -> 0x00a9 }
            if (r4 <= 0) goto L_0x00a6
            if (r4 >= r9) goto L_0x00a6
            int r8 = r15.getCid()     // Catch:{ Throwable -> 0x00a9 }
            if (r8 <= 0) goto L_0x00a6
            if (r8 >= r9) goto L_0x00a6
            android.telephony.CellSignalStrengthGsm r11 = r11.getCellSignalStrength()     // Catch:{ Throwable -> 0x00a9 }
            int r11 = r11.getDbm()     // Catch:{ Throwable -> 0x00a9 }
            r16 = r4
            r4 = r3
            r3 = r6
        L_0x00a2:
            r6 = r16
            goto L_0x019a
        L_0x00a6:
            r4 = r3
            r3 = r6
            goto L_0x0044
        L_0x00a9:
            r0 = move-exception
            r4 = r3
            r8 = r6
            goto L_0x01d5
        L_0x00ae:
            r0 = move-exception
            r3 = r0
            r8 = r6
            goto L_0x01d6
        L_0x00b3:
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x01d3 }
            r8 = 18
            if (r6 < r8) goto L_0x010c
            boolean r6 = r11 instanceof android.telephony.CellInfoWcdma     // Catch:{ Throwable -> 0x01d3 }
            if (r6 == 0) goto L_0x010c
            android.telephony.CellInfoWcdma r11 = (android.telephony.CellInfoWcdma) r11     // Catch:{ Throwable -> 0x01d3 }
            android.telephony.CellIdentityWcdma r6 = r11.getCellIdentity()     // Catch:{ Throwable -> 0x01d3 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d3 }
            r8.<init>()     // Catch:{ Throwable -> 0x01d3 }
            int r15 = r6.getMcc()     // Catch:{ Throwable -> 0x01d3 }
            r8.append(r15)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x01d3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0109 }
            r3.<init>()     // Catch:{ Throwable -> 0x0109 }
            int r15 = r6.getMnc()     // Catch:{ Throwable -> 0x0109 }
            r3.append(r15)     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0109 }
            int r4 = r6.getLac()     // Catch:{ Throwable -> 0x0105 }
            if (r4 <= 0) goto L_0x0101
            if (r4 >= r9) goto L_0x0101
            int r6 = r6.getCid()     // Catch:{ Throwable -> 0x0105 }
            if (r6 <= 0) goto L_0x0101
            if (r6 >= r7) goto L_0x0101
            android.telephony.CellSignalStrengthWcdma r11 = r11.getCellSignalStrength()     // Catch:{ Throwable -> 0x0105 }
            int r11 = r11.getDbm()     // Catch:{ Throwable -> 0x0105 }
        L_0x00fb:
            r16 = r4
            r4 = r3
            r3 = r8
            r8 = r6
            goto L_0x00a2
        L_0x0101:
            r4 = r3
            r3 = r8
            goto L_0x0044
        L_0x0105:
            r0 = move-exception
            r4 = r3
            goto L_0x01d5
        L_0x0109:
            r0 = move-exception
            goto L_0x01d5
        L_0x010c:
            boolean r6 = r11 instanceof android.telephony.CellInfoLte     // Catch:{ Throwable -> 0x01d3 }
            if (r6 == 0) goto L_0x014f
            android.telephony.CellInfoLte r11 = (android.telephony.CellInfoLte) r11     // Catch:{ Throwable -> 0x01d3 }
            android.telephony.CellIdentityLte r6 = r11.getCellIdentity()     // Catch:{ Throwable -> 0x01d3 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d3 }
            r8.<init>()     // Catch:{ Throwable -> 0x01d3 }
            int r15 = r6.getMcc()     // Catch:{ Throwable -> 0x01d3 }
            r8.append(r15)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x01d3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0109 }
            r3.<init>()     // Catch:{ Throwable -> 0x0109 }
            int r15 = r6.getMnc()     // Catch:{ Throwable -> 0x0109 }
            r3.append(r15)     // Catch:{ Throwable -> 0x0109 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0109 }
            int r4 = r6.getTac()     // Catch:{ Throwable -> 0x0105 }
            if (r4 <= 0) goto L_0x0101
            if (r4 >= r9) goto L_0x0101
            int r6 = r6.getCi()     // Catch:{ Throwable -> 0x0105 }
            if (r6 <= 0) goto L_0x0101
            if (r6 >= r7) goto L_0x0101
            android.telephony.CellSignalStrengthLte r11 = r11.getCellSignalStrength()     // Catch:{ Throwable -> 0x0105 }
            int r11 = r11.getDbm()     // Catch:{ Throwable -> 0x0105 }
            goto L_0x00fb
        L_0x014f:
            boolean r6 = r11 instanceof android.telephony.CellInfoCdma     // Catch:{ Throwable -> 0x01d3 }
            if (r6 == 0) goto L_0x01ce
            android.telephony.CellInfoCdma r11 = (android.telephony.CellInfoCdma) r11     // Catch:{ Throwable -> 0x01d3 }
            android.telephony.CellIdentityCdma r6 = r11.getCellIdentity()     // Catch:{ Throwable -> 0x01d3 }
            android.content.Context r8 = r1.d     // Catch:{ Throwable -> 0x01d3 }
            com.amap.location.g.a.a r8 = com.amap.location.g.a.a.a(r8)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r8 = r8.e()     // Catch:{ Throwable -> 0x01d3 }
            if (r8 == 0) goto L_0x0172
            int r15 = r8.length()     // Catch:{ Throwable -> 0x01d3 }
            r7 = 3
            if (r15 < r7) goto L_0x0172
            r15 = 0
            java.lang.String r8 = r8.substring(r15, r7)     // Catch:{ Throwable -> 0x01d3 }
            r3 = r8
        L_0x0172:
            int r7 = r6.getSystemId()     // Catch:{ Throwable -> 0x01d3 }
            if (r7 < 0) goto L_0x01ce
            r8 = 32767(0x7fff, float:4.5916E-41)
            if (r7 > r8) goto L_0x01ce
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x01d3 }
            int r4 = r6.getNetworkId()     // Catch:{ Throwable -> 0x01ca }
            if (r4 < 0) goto L_0x01c8
            if (r4 > r9) goto L_0x01c8
            int r8 = r6.getBasestationId()     // Catch:{ Throwable -> 0x01ca }
            if (r8 < 0) goto L_0x01c8
            if (r8 > r9) goto L_0x01c8
            android.telephony.CellSignalStrengthCdma r6 = r11.getCellSignalStrength()     // Catch:{ Throwable -> 0x01ca }
            int r11 = r6.getDbm()     // Catch:{ Throwable -> 0x01ca }
            r6 = r4
            r4 = r7
        L_0x019a:
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ Throwable -> 0x01d3 }
            r7.<init>()     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r15 = "mcc"
            r7.put(r15, r3)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r15 = "mnc"
            r7.put(r15, r4)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r15 = "lac"
            r7.put(r15, r6)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r6 = "cid"
            r7.put(r6, r8)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r6 = "rssi"
            r7.put(r6, r11)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r6 = "connected"
            r7.put(r6, r12)     // Catch:{ Throwable -> 0x01d3 }
            java.lang.String r6 = "time"
            r7.put(r6, r13)     // Catch:{ Throwable -> 0x01d3 }
            r5.put(r7)     // Catch:{ Throwable -> 0x01d3 }
            goto L_0x01ce
        L_0x01c8:
            r4 = r7
            goto L_0x01ce
        L_0x01ca:
            r0 = move-exception
            r8 = r3
            r4 = r7
            goto L_0x01d5
        L_0x01ce:
            r7 = 268435455(0xfffffff, float:2.5243547E-29)
            goto L_0x0044
        L_0x01d3:
            r0 = move-exception
            r8 = r3
        L_0x01d5:
            r3 = r0
        L_0x01d6:
            com.amap.location.common.d.a.a(r3)     // Catch:{ Exception -> 0x035b }
            r3 = r8
        L_0x01da:
            int r6 = r5.length()     // Catch:{ Exception -> 0x035b }
            if (r6 <= 0) goto L_0x01e7
            java.lang.String r3 = "cell"
            r2.put(r3, r5)     // Catch:{ Exception -> 0x035b }
            goto L_0x0360
        L_0x01e7:
            r17.e()     // Catch:{ Exception -> 0x035b }
            r6 = 0
            android.content.Context r7 = r1.d     // Catch:{ Exception -> 0x01f6 }
            com.amap.location.g.a.a r7 = com.amap.location.g.a.a.a(r7)     // Catch:{ Exception -> 0x01f6 }
            android.telephony.CellLocation r7 = r7.a()     // Catch:{ Exception -> 0x01f6 }
            r6 = r7
        L_0x01f6:
            if (r6 == 0) goto L_0x033e
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x035b }
            boolean r10 = r6 instanceof android.telephony.gsm.GsmCellLocation     // Catch:{ Exception -> 0x035b }
            r11 = 1
            if (r10 == 0) goto L_0x0265
            android.telephony.gsm.GsmCellLocation r6 = (android.telephony.gsm.GsmCellLocation) r6     // Catch:{ Exception -> 0x035b }
            android.content.Context r10 = r1.d     // Catch:{ Exception -> 0x035b }
            com.amap.location.g.a.a r10 = com.amap.location.g.a.a.a(r10)     // Catch:{ Exception -> 0x035b }
            java.lang.String r10 = r10.e()     // Catch:{ Exception -> 0x035b }
            if (r10 == 0) goto L_0x0220
            int r12 = r10.length()     // Catch:{ Exception -> 0x035b }
            r13 = 5
            if (r12 < r13) goto L_0x0220
            r12 = 3
            r14 = 0
            java.lang.String r3 = r10.substring(r14, r12)     // Catch:{ Exception -> 0x035b }
            java.lang.String r4 = r10.substring(r12, r13)     // Catch:{ Exception -> 0x035b }
        L_0x0220:
            int r10 = r6.getLac()     // Catch:{ Exception -> 0x035b }
            int r6 = r6.getCid()     // Catch:{ Exception -> 0x035b }
            if (r10 <= 0) goto L_0x02c9
            if (r10 >= r9) goto L_0x02c9
            if (r6 <= 0) goto L_0x02c9
            if (r6 == r9) goto L_0x02c9
            r12 = 268435455(0xfffffff, float:2.5243547E-29)
            if (r6 >= r12) goto L_0x02c9
            org.json.JSONObject r12 = new org.json.JSONObject     // Catch:{ Exception -> 0x035b }
            r12.<init>()     // Catch:{ Exception -> 0x035b }
            java.lang.String r13 = "mcc"
            r12.put(r13, r3)     // Catch:{ Exception -> 0x035b }
            java.lang.String r13 = "mnc"
            r12.put(r13, r4)     // Catch:{ Exception -> 0x035b }
            java.lang.String r13 = "lac"
            r12.put(r13, r10)     // Catch:{ Exception -> 0x035b }
            java.lang.String r10 = "cid"
            r12.put(r10, r6)     // Catch:{ Exception -> 0x035b }
            java.lang.String r6 = "rssi"
            int r10 = r1.q     // Catch:{ Exception -> 0x035b }
            r12.put(r6, r10)     // Catch:{ Exception -> 0x035b }
            java.lang.String r6 = "connected"
            r12.put(r6, r11)     // Catch:{ Exception -> 0x035b }
            java.lang.String r6 = "time"
            r12.put(r6, r7)     // Catch:{ Exception -> 0x035b }
            r5.put(r12)     // Catch:{ Exception -> 0x035b }
            goto L_0x02c9
        L_0x0265:
            boolean r10 = r6 instanceof android.telephony.cdma.CdmaCellLocation     // Catch:{ Exception -> 0x035b }
            if (r10 == 0) goto L_0x02c9
            android.telephony.cdma.CdmaCellLocation r6 = (android.telephony.cdma.CdmaCellLocation) r6     // Catch:{ Exception -> 0x035b }
            android.content.Context r4 = r1.d     // Catch:{ Exception -> 0x035b }
            com.amap.location.g.a.a r4 = com.amap.location.g.a.a.a(r4)     // Catch:{ Exception -> 0x035b }
            java.lang.String r4 = r4.e()     // Catch:{ Exception -> 0x035b }
            if (r4 == 0) goto L_0x0283
            int r10 = r4.length()     // Catch:{ Exception -> 0x035b }
            r12 = 3
            if (r10 < r12) goto L_0x0283
            r10 = 0
            java.lang.String r3 = r4.substring(r10, r12)     // Catch:{ Exception -> 0x035b }
        L_0x0283:
            int r4 = r6.getSystemId()     // Catch:{ Exception -> 0x035b }
            java.lang.String r10 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x035b }
            int r12 = r6.getNetworkId()     // Catch:{ Exception -> 0x035b }
            int r6 = r6.getBaseStationId()     // Catch:{ Exception -> 0x035b }
            if (r4 < 0) goto L_0x02c8
            if (r12 < 0) goto L_0x02c8
            if (r6 < 0) goto L_0x02c8
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x035b }
            r4.<init>()     // Catch:{ Exception -> 0x035b }
            java.lang.String r13 = "mcc"
            r4.put(r13, r3)     // Catch:{ Exception -> 0x035b }
            java.lang.String r13 = "mnc"
            r4.put(r13, r10)     // Catch:{ Exception -> 0x035b }
            java.lang.String r13 = "lac"
            r4.put(r13, r12)     // Catch:{ Exception -> 0x035b }
            java.lang.String r12 = "cid"
            r4.put(r12, r6)     // Catch:{ Exception -> 0x035b }
            java.lang.String r6 = "rssi"
            int r12 = r1.q     // Catch:{ Exception -> 0x035b }
            r4.put(r6, r12)     // Catch:{ Exception -> 0x035b }
            java.lang.String r6 = "connected"
            r4.put(r6, r11)     // Catch:{ Exception -> 0x035b }
            java.lang.String r6 = "time"
            r4.put(r6, r7)     // Catch:{ Exception -> 0x035b }
            r5.put(r4)     // Catch:{ Exception -> 0x035b }
        L_0x02c8:
            r4 = r10
        L_0x02c9:
            android.content.Context r6 = r1.d     // Catch:{ Exception -> 0x035b }
            com.amap.location.g.a.a r6 = com.amap.location.g.a.a.a(r6)     // Catch:{ Exception -> 0x035b }
            java.util.List r6 = r6.f()     // Catch:{ Exception -> 0x035b }
            if (r6 == 0) goto L_0x033e
            int r10 = r6.size()     // Catch:{ Exception -> 0x035b }
            if (r10 <= 0) goto L_0x033e
            java.util.Iterator r6 = r6.iterator()     // Catch:{ Exception -> 0x035b }
        L_0x02df:
            boolean r10 = r6.hasNext()     // Catch:{ Exception -> 0x035b }
            if (r10 == 0) goto L_0x033e
            java.lang.Object r10 = r6.next()     // Catch:{ Exception -> 0x035b }
            android.telephony.NeighboringCellInfo r10 = (android.telephony.NeighboringCellInfo) r10     // Catch:{ Exception -> 0x035b }
            int r11 = r10.getLac()     // Catch:{ Exception -> 0x035b }
            int r12 = r10.getCid()     // Catch:{ Exception -> 0x035b }
            int r10 = r10.getRssi()     // Catch:{ Exception -> 0x035b }
            int r10 = r10 * 2
            int r10 = r10 + -113
            if (r11 <= 0) goto L_0x0339
            if (r11 >= r9) goto L_0x0339
            if (r12 <= 0) goto L_0x0339
            if (r12 == r9) goto L_0x0339
            r13 = 268435455(0xfffffff, float:2.5243547E-29)
            if (r12 >= r13) goto L_0x0337
            org.json.JSONObject r14 = new org.json.JSONObject     // Catch:{ Exception -> 0x035b }
            r14.<init>()     // Catch:{ Exception -> 0x035b }
            java.lang.String r15 = "mcc"
            r14.put(r15, r3)     // Catch:{ Exception -> 0x035b }
            java.lang.String r15 = "mnc"
            r14.put(r15, r4)     // Catch:{ Exception -> 0x035b }
            java.lang.String r15 = "lac"
            r14.put(r15, r11)     // Catch:{ Exception -> 0x035b }
            java.lang.String r11 = "cid"
            r14.put(r11, r12)     // Catch:{ Exception -> 0x035b }
            java.lang.String r11 = "rssi"
            r14.put(r11, r10)     // Catch:{ Exception -> 0x035b }
            java.lang.String r10 = "connected"
            r11 = 0
            r14.put(r10, r11)     // Catch:{ Exception -> 0x035b }
            java.lang.String r10 = "time"
            r14.put(r10, r7)     // Catch:{ Exception -> 0x035b }
            r5.put(r14)     // Catch:{ Exception -> 0x035b }
            goto L_0x02df
        L_0x0337:
            r11 = 0
            goto L_0x02df
        L_0x0339:
            r11 = 0
            r13 = 268435455(0xfffffff, float:2.5243547E-29)
            goto L_0x02df
        L_0x033e:
            java.lang.String r3 = "partnerloc"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x035b }
            java.lang.String r6 = "get cell size:"
            r4.<init>(r6)     // Catch:{ Exception -> 0x035b }
            int r6 = r5.length()     // Catch:{ Exception -> 0x035b }
            r4.append(r6)     // Catch:{ Exception -> 0x035b }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x035b }
            com.amap.location.common.d.a.b(r3, r4)     // Catch:{ Exception -> 0x035b }
            java.lang.String r3 = "cell"
            r2.put(r3, r5)     // Catch:{ Exception -> 0x035b }
            goto L_0x0360
        L_0x035b:
            r0 = move-exception
            r3 = r0
            com.amap.location.common.d.a.a(r3)
        L_0x0360:
            java.lang.String r2 = r2.toString()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.a.e.b():java.lang.String");
    }

    public JSONObject a(final String str) {
        JSONObject jSONObject = new JSONObject();
        if (TextUtils.isEmpty(str)) {
            str = "amap7";
        }
        if (this.g == null || this.k == null) {
            return jSONObject;
        }
        try {
            this.l = null;
            synchronized (this.p) {
                this.k.removeCallbacks(this.w);
                this.v = new LocationListener() {
                    public void onProviderDisabled(String str) {
                    }

                    public void onProviderEnabled(String str) {
                    }

                    public void onStatusChanged(String str, int i, Bundle bundle) {
                    }

                    public void onLocationChanged(Location location) {
                        StringBuilder sb = new StringBuilder("geofence loc success, Longitude:");
                        sb.append(location.getLongitude());
                        sb.append(",Latitude:");
                        sb.append(location.getLatitude());
                        com.amap.location.common.d.a.b("partnerloc", com.amap.location.common.d.a.a(sb.toString()));
                        e.this.l = location;
                        synchronized (e.this.p) {
                            e.this.p.notifyAll();
                        }
                    }
                };
                this.k.postDelayed(new Runnable() {
                    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
                    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x000c */
                    /* JADX WARNING: Removed duplicated region for block: B:8:0x0010 A[Catch:{ Throwable -> 0x0035 }] */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void run() {
                        /*
                            r3 = this;
                            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0035 }
                            r0.<init>()     // Catch:{ Throwable -> 0x0035 }
                            java.lang.String r1 = "channel"
                            java.lang.String r2 = r8     // Catch:{ Exception -> 0x000c }
                            r0.put(r1, r2)     // Catch:{ Exception -> 0x000c }
                        L_0x000c:
                            org.json.JSONObject r1 = com.amap.location.sdk.a.e.a     // Catch:{ Throwable -> 0x0035 }
                            if (r1 != 0) goto L_0x0017
                            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0035 }
                            r1.<init>()     // Catch:{ Throwable -> 0x0035 }
                            com.amap.location.sdk.a.e.a = r1     // Catch:{ Throwable -> 0x0035 }
                        L_0x0017:
                            org.json.JSONObject r1 = com.amap.location.sdk.a.e.a     // Catch:{ Throwable -> 0x0035 }
                            java.lang.String r2 = "aosextra"
                            r1.put(r2, r0)     // Catch:{ Throwable -> 0x0035 }
                            com.amap.location.sdk.a.e r0 = com.amap.location.sdk.a.e.this     // Catch:{ Throwable -> 0x0035 }
                            com.amap.location.sdk.d.b.e r0 = r0.g     // Catch:{ Throwable -> 0x0035 }
                            org.json.JSONObject r1 = com.amap.location.sdk.a.e.a     // Catch:{ Throwable -> 0x0035 }
                            r2 = 1
                            r0.a(r1, r2)     // Catch:{ Throwable -> 0x0035 }
                            com.amap.location.sdk.a.e r0 = com.amap.location.sdk.a.e.this     // Catch:{ Throwable -> 0x0035 }
                            com.amap.location.sdk.d.b.e r0 = r0.g     // Catch:{ Throwable -> 0x0035 }
                            r1 = 0
                            r0.a(r1, r1)     // Catch:{ Throwable -> 0x0035 }
                            return
                        L_0x0035:
                            r0 = move-exception
                            com.amap.location.common.d.a.a(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.a.e.AnonymousClass10.run():void");
                    }
                }, 10);
                try {
                    this.p.wait(5000);
                } catch (InterruptedException e2) {
                    com.amap.location.common.d.a.a((Throwable) e2);
                }
                this.g.a();
                this.k.postDelayed(this.w, 60000);
            }
            Location location = this.l;
            this.v = null;
            if (location != null) {
                jSONObject.put(DictionaryKeys.CTRLXY_X, location.getLongitude());
                jSONObject.put(DictionaryKeys.CTRLXY_Y, location.getLatitude());
                jSONObject.put("precision", (double) location.getAccuracy());
            } else {
                com.amap.location.common.d.a.b("partnerloc", "get geofence loc failed");
            }
        } catch (Exception e3) {
            com.amap.location.common.d.a.a((Throwable) e3);
        }
        return jSONObject;
    }

    public static void b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            a = jSONObject;
            b.a(jSONObject);
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }
}
