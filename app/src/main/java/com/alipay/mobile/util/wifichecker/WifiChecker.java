package com.alipay.mobile.util.wifichecker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobileappcommon.biz.rpc.checkwifi.model.ClientCheckWifiReq;
import com.alipay.mobileappcommon.biz.rpc.checkwifi.model.ClientCheckWifiRes;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WifiChecker {
    private static WifiChecker a;
    private Context b;
    private final Map<String, WifiMeta> c = new ConcurrentHashMap();
    private CheckWifiFacade d;

    class WifiMeta implements Parcelable {
        private long a;
        private String b;
        private String c;
        private String d;
        private String e;

        public WifiMeta() {
            this.a = 0;
            this.a = System.currentTimeMillis();
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public final boolean a() {
            return System.currentTimeMillis() - this.a > 172800000;
        }

        public final String b() {
            return this.b;
        }

        public final void a(String wifiID) {
            this.b = wifiID;
        }

        public final void b(String wifiName) {
            this.c = wifiName;
        }

        public final void c(String wifiMacAddr) {
            this.d = wifiMacAddr;
        }

        public final String c() {
            return this.e;
        }

        public final void d(String flag) {
            this.e = flag;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.b);
            dest.writeString(this.c);
            dest.writeString(this.d);
            dest.writeString(this.e);
            dest.writeLong(this.a);
        }

        public final boolean e(String string) {
            boolean result = false;
            if (string == null) {
                return false;
            }
            try {
                if (string.length() == 0) {
                    return false;
                }
                String[] parts = string.split(",");
                if (parts.length != 5) {
                    return false;
                }
                this.b = parts[0];
                this.c = parts[1];
                this.d = parts[2];
                this.e = parts[3];
                this.a = Long.parseLong(parts[4]);
                if (WifiChecker.f(this.b)) {
                    result = false;
                } else {
                    result = true;
                }
                return result;
            } catch (Exception ex) {
                WifiChecker.e("fromString,ex:" + ex);
            }
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.b);
            stringBuffer.append(",");
            stringBuffer.append(this.c);
            stringBuffer.append(",");
            stringBuffer.append(this.d);
            stringBuffer.append(",");
            stringBuffer.append(this.e);
            stringBuffer.append(",");
            stringBuffer.append(this.a);
            return stringBuffer.toString();
        }
    }

    private WifiChecker() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static WifiChecker a(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (WifiChecker.class) {
            try {
                if (a == null) {
                    a = new WifiChecker(context);
                }
            }
        }
        return a;
    }

    private WifiChecker(Context context) {
        this.b = context;
        a(context, this.c, (String) "wificache");
    }

    public final boolean a() {
        boolean isTrueWifi = true;
        try {
            if (!b()) {
                return false;
            }
            WifiInfo wifiInfo = ((WifiManager) this.b.getSystemService("wifi")).getConnectionInfo();
            if (wifiInfo != null) {
                String bssid = wifiInfo.getBSSID();
                String ssid = wifiInfo.getSSID();
                String macId = c(bssid);
                e("checkWifi,bssid:" + bssid + ",ssid:" + ssid + ",macId:" + macId);
                if (f(macId) || macId.equals("000000000000")) {
                    e("checkWifi,macId is " + macId + " , just return true");
                    return false;
                }
                isTrueWifi = a(macId, d(ssid), bssid);
                e("checkWifi,isTrueWifi:" + isTrueWifi);
            }
            return isTrueWifi;
        } catch (Exception e) {
        }
    }

    private boolean b() {
        try {
            NetworkInfo activeNetInfo = ((ConnectivityManager) this.b.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetInfo != null && activeNetInfo.isConnected() && activeNetInfo.getState() == State.CONNECTED && activeNetInfo.getType() == 1) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    private boolean a(String macId, String ssid, String bssid) {
        boolean isTrueWifi;
        if (f(macId)) {
            return true;
        }
        if (this.c.containsKey(macId)) {
            WifiMeta meta = this.c.get(macId);
            e("checkWifi,has cached");
            if (meta.a()) {
                e("checkWifi,mac id:" + macId + " is expired!");
                this.c.remove(macId);
            } else if (!meta.c().equals("true")) {
                return false;
            } else {
                return true;
            }
        }
        String result = a(ssid, bssid);
        if (f(result) || result.equals("1")) {
            isTrueWifi = false;
        } else {
            isTrueWifi = true;
        }
        if (f(result)) {
            return isTrueWifi;
        }
        WifiMeta meta2 = new WifiMeta();
        meta2.a(macId);
        meta2.c(bssid);
        meta2.b(ssid);
        meta2.d(isTrueWifi ? "true" : "false");
        a(this.b, this.c, meta2);
        return isTrueWifi;
    }

    private String a(String ssid, String bssid) {
        String result = "";
        e("checkWifi,check from server ssid:" + ssid + ",bssid:" + bssid);
        if (ssid == null || bssid == null) {
            return result;
        }
        try {
            ClientCheckWifiReq request = new ClientCheckWifiReq();
            request.bssid = bssid;
            request.ssid = ssid;
            this.d = (CheckWifiFacade) ((RpcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RpcService.class.getName())).getRpcProxy(CheckWifiFacade.class);
            ClientCheckWifiRes res = this.d.checkWIFI(request);
            if (res != null) {
                result = res.isWIFI;
            }
            e("checkWifiFromServer,result:" + result);
        } catch (RpcException e) {
            e("checkWifiFromServer,ex:" + e);
        }
        return result;
    }

    private static String c(String bssid) {
        if (f(bssid)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        try {
            for (String part : bssid.split(":")) {
                sb.append(part.toUpperCase());
            }
        } catch (Exception ex) {
            e("getWifiId,bssid:" + bssid + ",ex:" + ex);
        }
        return sb.toString();
    }

    private static String d(String ssid) {
        if (ssid == null) {
            return "";
        }
        return ssid.replaceAll("\"", "");
    }

    private static void a(Context context, Map<String, WifiMeta> map, String name) {
        File file = a(context, name);
        if (file != null && file.exists()) {
            BufferedReader reader = null;
            try {
                BufferedReader reader2 = new BufferedReader(new FileReader(file));
                try {
                    String magic = reader2.readLine();
                    String version = reader2.readLine();
                    if (f(magic) || f(version)) {
                        a((Closeable) reader2);
                    } else if (!magic.equals("wifi.cache")) {
                        a((Closeable) reader2);
                    } else {
                        while (true) {
                            String line = reader2.readLine();
                            if (line != null) {
                                a(map, line);
                            } else {
                                e("readCaches,map:" + map);
                                a((Closeable) reader2);
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    exception = e;
                    reader = reader2;
                } catch (Throwable th) {
                    th = th;
                    reader = reader2;
                    a((Closeable) reader);
                    throw th;
                }
            } catch (Exception e2) {
                exception = e2;
                try {
                    file.delete();
                    e("readCaches,ex:" + exception);
                    a((Closeable) reader);
                } catch (Throwable th2) {
                    th = th2;
                    a((Closeable) reader);
                    throw th;
                }
            }
        }
    }

    private static void a(Map<String, WifiMeta> map, String line) {
        if (map != null && !f(line)) {
            WifiMeta resMeta = new WifiMeta();
            if (resMeta.e(line)) {
                map.put(resMeta.b(), resMeta);
            }
        }
    }

    private static void a(Context context, Map<String, WifiMeta> map, WifiMeta meta) {
        if (map != null && !map.containsKey(meta.b())) {
            e("syncCaches,map:" + map + ",wifiId:" + meta.b());
            if (map.size() >= 10) {
                e("syncCaches,map.size():" + map.size() + ",clear!");
                map.clear();
            }
            map.put(meta.b(), meta);
            b(context, map, "wificache");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void b(android.content.Context r10, java.util.Map<java.lang.String, com.alipay.mobile.util.wifichecker.WifiChecker.WifiMeta> r11, java.lang.String r12) {
        /*
            r6 = 0
            java.io.File r4 = a(r10, r12)
            if (r4 == 0) goto L_0x0009
            if (r11 != 0) goto L_0x000a
        L_0x0009:
            return
        L_0x000a:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r9 = ".tmp"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            java.io.File r7 = a(r10, r8)
            if (r7 == 0) goto L_0x0009
            r0 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x00a6 }
            java.io.FileWriter r8 = new java.io.FileWriter     // Catch:{ IOException -> 0x00a6 }
            r8.<init>(r7)     // Catch:{ IOException -> 0x00a6 }
            r1.<init>(r8)     // Catch:{ IOException -> 0x00a6 }
            java.lang.String r8 = "wifi.cache"
            r1.write(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            r8 = 10
            r1.write(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            java.lang.String r8 = "1.0"
            r1.write(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            r8 = 10
            r1.write(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            java.util.Collection r8 = r11.values()     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            java.util.Iterator r5 = r8.iterator()     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
        L_0x004a:
            boolean r8 = r5.hasNext()     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            if (r8 == 0) goto L_0x0083
            java.lang.Object r2 = r5.next()     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            com.alipay.mobile.util.wifichecker.WifiChecker$WifiMeta r2 = (com.alipay.mobile.util.wifichecker.WifiChecker.WifiMeta) r2     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            java.lang.String r8 = r2.toString()     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            r1.write(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            r8 = 10
            r1.write(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            goto L_0x004a
        L_0x0063:
            r3 = move-exception
            r0 = r1
        L_0x0065:
            r4.delete()     // Catch:{ all -> 0x009e }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x009e }
            java.lang.String r9 = "writeCaches,ex:"
            r8.<init>(r9)     // Catch:{ all -> 0x009e }
            java.lang.StringBuilder r8 = r8.append(r3)     // Catch:{ all -> 0x009e }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x009e }
            e(r8)     // Catch:{ all -> 0x009e }
            a(r0)
        L_0x007d:
            if (r6 == 0) goto L_0x0009
            r7.renameTo(r4)
            goto L_0x0009
        L_0x0083:
            r1.flush()     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            r6 = 1
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            java.lang.String r9 = "writeCaches,map:"
            r8.<init>(r9)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            java.lang.StringBuilder r8 = r8.append(r11)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            e(r8)     // Catch:{ IOException -> 0x0063, all -> 0x00a3 }
            a(r1)
            r0 = r1
            goto L_0x007d
        L_0x009e:
            r8 = move-exception
        L_0x009f:
            a(r0)
            throw r8
        L_0x00a3:
            r8 = move-exception
            r0 = r1
            goto L_0x009f
        L_0x00a6:
            r3 = move-exception
            goto L_0x0065
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.util.wifichecker.WifiChecker.b(android.content.Context, java.util.Map, java.lang.String):void");
    }

    private static void a(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e("closeStream,ex:" + e);
            }
        }
    }

    private static File a(Context context, String name) {
        try {
            File file = new File(context.getFilesDir(), "download.service.cache");
            if (!file.exists()) {
                file.mkdirs();
            }
            return new File(file, name);
        } catch (Exception e) {
            e("getCachePath,ex:" + e);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static final void e(String message) {
        LoggerFactory.getTraceLogger().info("WifiChecker", message);
    }

    /* access modifiers changed from: private */
    public static boolean f(String string) {
        return string == null || string.length() == 0;
    }
}
