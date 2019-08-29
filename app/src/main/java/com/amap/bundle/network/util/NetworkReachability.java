package com.amap.bundle.network.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class NetworkReachability {
    private static Context a = null;
    private static volatile NetworkType b = NetworkType.NONE;
    private static volatile String c = "";
    private static volatile String d = "";
    private static volatile Pair<String, Integer> e = null;
    private static volatile boolean f = false;
    private static volatile boolean g = false;
    private static volatile boolean h = false;
    private static WifiManager i;
    private static ConnectivityManager j;
    private static List<WeakReference<a>> k = Collections.synchronizedList(new ArrayList());
    private static BroadcastReceiver l = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            NetworkReachability.d(context);
        }
    };

    public enum NetworkType {
        UNKNOWN(-1, "UNKNOWN"),
        NONE(0, "NONE"),
        G2(1, "2G"),
        G3(2, "3G"),
        G4(3, "4G"),
        WIFI(4, "WIFI");
        
        private String mDescription;
        private int mValue;

        private NetworkType(int i, String str) {
            this.mValue = i;
            this.mDescription = str;
        }

        public final String toString() {
            return String.valueOf(this.mValue);
        }

        public final String description() {
            return this.mDescription;
        }

        public final boolean isMobile() {
            return this == G2 || this == G3 || this == G4;
        }

        public final boolean isWifi() {
            return this == WIFI;
        }
    }

    public interface a {
        void a(NetworkType networkType);
    }

    private NetworkReachability() {
    }

    public static synchronized void a(a aVar) {
        synchronized (NetworkReachability.class) {
            if (aVar != null) {
                e();
                k.add(new WeakReference(aVar));
                g();
            }
        }
    }

    public static synchronized void b(a aVar) {
        synchronized (NetworkReachability.class) {
            if (aVar != null) {
                for (WeakReference weakReference : new ArrayList(k)) {
                    a aVar2 = (a) weakReference.get();
                    if (aVar2 == null || aVar2 == aVar) {
                        k.remove(weakReference);
                    }
                }
            }
        }
    }

    public static boolean a() {
        e();
        if (a == null) {
            a = aaf.a();
        }
        NetworkInfo a2 = a(a);
        boolean z = false;
        if (a2 != null && a2.isConnected() && a2.getType() == 1) {
            z = true;
        }
        if (z != f) {
            d(a);
        }
        return z;
    }

    public static boolean b() {
        boolean z;
        e();
        if (a == null) {
            a = aaf.a();
        }
        NetworkInfo a2 = a(a);
        if (a2 != null) {
            z = a2.isConnected();
        } else {
            z = false;
        }
        if (z != g) {
            d(a);
        }
        return z;
    }

    public static NetworkType c() {
        e();
        return b;
    }

    public static boolean d() {
        e();
        return (b == NetworkType.WIFI && e != null) || (b.isMobile() && c.contains("wap"));
    }

    @Nullable
    public static NetworkInfo a(@NonNull Context context) {
        try {
            if (j == null) {
                synchronized (NetworkReachability.class) {
                    if (j == null) {
                        j = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
                    }
                }
            }
            return j.getActiveNetworkInfo();
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("getNetworkInfo:");
            sb.append(th.getLocalizedMessage());
            bpv.e("NetworkReachability", sb.toString());
            return null;
        }
    }

    @Nullable
    @SuppressLint({"MissingPermission"})
    private static WifiInfo c(@NonNull Context context) {
        try {
            if (i == null) {
                synchronized (NetworkReachability.class) {
                    if (i == null) {
                        i = (WifiManager) context.getApplicationContext().getSystemService("wifi");
                    }
                }
            }
            return i.getConnectionInfo();
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("getWifiInfo:");
            sb.append(th.getLocalizedMessage());
            bpv.e("NetworkReachability", sb.toString());
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x004f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void e() {
        /*
            java.lang.Class<com.amap.bundle.network.util.NetworkReachability> r0 = com.amap.bundle.network.util.NetworkReachability.class
            monitor-enter(r0)
            boolean r1 = h     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)
            return
        L_0x0009:
            android.content.Context r1 = defpackage.aaf.a()     // Catch:{ all -> 0x0050 }
            a = r1     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x004e
            java.lang.Class<com.amap.bundle.network.util.NetworkReachability> r2 = com.amap.bundle.network.util.NetworkReachability.class
            monitor-enter(r2)     // Catch:{ all -> 0x0050 }
            boolean r3 = h     // Catch:{ all -> 0x004b }
            if (r3 != 0) goto L_0x0046
            android.content.IntentFilter r3 = new android.content.IntentFilter     // Catch:{ all -> 0x004b }
            r3.<init>()     // Catch:{ all -> 0x004b }
            java.lang.String r4 = "android.net.conn.CONNECTIVITY_CHANGE"
            r3.addAction(r4)     // Catch:{ all -> 0x004b }
            android.content.BroadcastReceiver r4 = l     // Catch:{ Exception -> 0x002b }
            r1.registerReceiver(r4, r3)     // Catch:{ Exception -> 0x002b }
            r3 = 1
            h = r3     // Catch:{ Exception -> 0x002b }
            goto L_0x0046
        L_0x002b:
            r3 = move-exception
            r4 = 0
            h = r4     // Catch:{ all -> 0x004b }
            java.lang.String r4 = "NetworkReachability"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x004b }
            java.lang.String r6 = "registerReceiver failed:"
            r5.<init>(r6)     // Catch:{ all -> 0x004b }
            java.lang.String r3 = r3.getLocalizedMessage()     // Catch:{ all -> 0x004b }
            r5.append(r3)     // Catch:{ all -> 0x004b }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x004b }
            defpackage.bpv.e(r4, r3)     // Catch:{ all -> 0x004b }
        L_0x0046:
            monitor-exit(r2)     // Catch:{ all -> 0x004b }
            d(r1)     // Catch:{ all -> 0x0050 }
            goto L_0x004e
        L_0x004b:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x004b }
            throw r1     // Catch:{ all -> 0x0050 }
        L_0x004e:
            monitor-exit(r0)
            return
        L_0x0050:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.network.util.NetworkReachability.e():void");
    }

    /* access modifiers changed from: private */
    public static synchronized void d(Context context) {
        NetworkType networkType;
        String str;
        synchronized (NetworkReachability.class) {
            NetworkType networkType2 = b;
            String str2 = c;
            String str3 = d;
            if (context != null) {
                try {
                    NetworkInfo a2 = a(context);
                    if (a2 == null || !a2.isConnected()) {
                        g = false;
                        f = false;
                        a(NetworkType.NONE);
                    } else {
                        g = true;
                        if (a2.getType() == 0) {
                            f = false;
                            String subtypeName = a2.getSubtypeName();
                            String replace = !TextUtils.isEmpty(subtypeName) ? subtypeName.replace(Token.SEPARATOR, "") : "";
                            switch (a2.getSubtype()) {
                                case 0:
                                    networkType = NetworkType.G2;
                                    break;
                                case 1:
                                case 2:
                                case 4:
                                case 7:
                                case 11:
                                case 16:
                                    networkType = NetworkType.G2;
                                    break;
                                case 3:
                                case 5:
                                case 6:
                                case 8:
                                case 9:
                                case 10:
                                case 12:
                                case 14:
                                case 15:
                                case 17:
                                    networkType = NetworkType.G3;
                                    break;
                                case 13:
                                case 18:
                                case 19:
                                    networkType = NetworkType.G4;
                                    break;
                                default:
                                    if (!replace.equalsIgnoreCase("TD-SCDMA") && !replace.equalsIgnoreCase("WCDMA") && !replace.equalsIgnoreCase("CDMA2000")) {
                                        networkType = NetworkType.G2;
                                        break;
                                    } else {
                                        networkType = NetworkType.G3;
                                        break;
                                    }
                                    break;
                            }
                            a(networkType);
                            String extraInfo = a2.getExtraInfo();
                            if (!TextUtils.isEmpty(extraInfo)) {
                                String lowerCase = extraInfo.toLowerCase(Locale.US);
                                str = lowerCase.contains("cmwap") ? "cmwap" : lowerCase.contains("uniwap") ? "uniwap" : lowerCase.contains("3gwap") ? "3gwap" : lowerCase.contains("ctwap") ? "ctwap" : lowerCase.contains("cmnet") ? "cmnet" : lowerCase.contains("uninet") ? "uninet" : lowerCase.contains("3gnet") ? "3gnet" : lowerCase.contains("ctnet") ? "ctnet" : "unknown";
                            } else {
                                str = "unknown";
                            }
                            c = str;
                        } else if (a2.getType() == 1) {
                            f = true;
                            a(NetworkType.WIFI);
                            WifiInfo c2 = c(context);
                            if (c2 != null) {
                                d = c2.getSSID();
                            }
                            e = f();
                        } else {
                            f = false;
                            a(NetworkType.UNKNOWN);
                        }
                    }
                    if (b != networkType2 || !c.equalsIgnoreCase(str2) || !d.equalsIgnoreCase(str3)) {
                        for (WeakReference weakReference : new ArrayList(k)) {
                            a aVar = (a) weakReference.get();
                            if (aVar != null) {
                                aVar.a(b);
                            }
                        }
                        g();
                        if (bpv.a(4)) {
                            StringBuilder sb = new StringBuilder("NetworkStatus changed:");
                            sb.append(b.description());
                            bpv.c("NetworkReachability", sb.toString());
                        }
                    }
                } catch (Exception e2) {
                    bpv.e("NetworkReachability", "updateNetworkState:".concat(String.valueOf(e2)));
                }
            }
        }
    }

    private static Pair<String, Integer> f() {
        try {
            String property = System.getProperty("http.proxyHost");
            if (!TextUtils.isEmpty(property)) {
                return Pair.create(property, Integer.valueOf(Integer.parseInt(System.getProperty("http.proxyPort"))));
            }
        } catch (NumberFormatException unused) {
        }
        return null;
    }

    private static void a(NetworkType networkType) {
        b = networkType;
        c = "";
        d = "";
        e = null;
    }

    private static void g() {
        for (WeakReference weakReference : new ArrayList(k)) {
            if (weakReference.get() == null) {
                k.remove(weakReference);
            }
        }
    }
}
