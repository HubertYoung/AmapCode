package com.xiaomi.network;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import android.net.wifi.WifiManager;
import android.os.Process;
import android.text.TextUtils;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.network.a;
import com.xiaomi.channel.commonutils.network.c;
import com.xiaomi.channel.commonutils.network.d;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class HostManager {
    private static HostManagerFactory factory = null;
    protected static boolean hostLoaded = false;
    protected static Context sAppContext;
    private static String sAppName;
    private static String sAppVersion;
    private static HostManager sInstance;
    protected static Map<String, Fallback> sReservedHosts = new HashMap();
    private final long MAX_REQUEST_FAILURE_CNT;
    private String currentISP;
    private long lastRemoteRequestTimestamp;
    protected Map<String, Fallbacks> mHostsMapping;
    private long remoteRequestFailureCount;
    private HostFilter sHostFilter;
    protected HttpGet sHttpGetter;
    private String sUserId;

    public interface HostManagerFactory {
        HostManager a(Context context, HostFilter hostFilter, HttpGet httpGet, String str);
    }

    public interface HttpGet {
        String a(String str);
    }

    protected HostManager(Context context, HostFilter hostFilter, HttpGet httpGet, String str) {
        this(context, hostFilter, httpGet, str, null, null);
    }

    protected HostManager(Context context, HostFilter hostFilter, HttpGet httpGet, String str, String str2, String str3) {
        this.mHostsMapping = new HashMap();
        this.sUserId = "0";
        this.remoteRequestFailureCount = 0;
        this.MAX_REQUEST_FAILURE_CNT = 15;
        this.lastRemoteRequestTimestamp = 0;
        this.currentISP = "isp_prov_city_country_ip";
        this.sHttpGetter = httpGet;
        this.sHostFilter = hostFilter == null ? new a(this) : hostFilter;
        this.sUserId = str;
        sAppName = str2 == null ? context.getPackageName() : str2;
        sAppVersion = str3 == null ? getVersionName() : str3;
    }

    public static void addReservedHost(String str, String str2) {
        Fallback fallback = sReservedHosts.get(str);
        synchronized (sReservedHosts) {
            if (fallback == null) {
                try {
                    Fallback fallback2 = new Fallback(str);
                    fallback2.a((long) FileCache.EXPIRE_TIME);
                    fallback2.b(str2);
                    sReservedHosts.put(str, fallback2);
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                fallback.b(str2);
            }
        }
    }

    static String getActiveNetworkLabel() {
        if (sAppContext == null) {
            return "unknown";
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) sAppContext.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "unknown";
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "unknown";
            }
            if (activeNetworkInfo.getType() == 1) {
                WifiManager wifiManager = (WifiManager) sAppContext.getSystemService("wifi");
                if (!(wifiManager == null || wifiManager.getConnectionInfo() == null)) {
                    StringBuilder sb = new StringBuilder("WIFI-");
                    sb.append(wifiManager.getConnectionInfo().getSSID());
                    return sb.toString();
                }
                return "unknown";
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(activeNetworkInfo.getTypeName());
            sb2.append("-");
            sb2.append(activeNetworkInfo.getSubtypeName());
            return sb2.toString();
        } catch (Throwable unused) {
        }
    }

    public static synchronized HostManager getInstance() {
        HostManager hostManager;
        synchronized (HostManager.class) {
            if (sInstance == null) {
                throw new IllegalStateException("the host manager is not initialized yet.");
            }
            hostManager = sInstance;
        }
        return hostManager;
    }

    private String getVersionName() {
        try {
            PackageInfo packageInfo = sAppContext.getPackageManager().getPackageInfo(sAppContext.getPackageName(), 16384);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (Exception unused) {
        }
        return "0";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void init(android.content.Context r9, com.xiaomi.network.HostFilter r10, com.xiaomi.network.HostManager.HttpGet r11, java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            java.lang.Class<com.xiaomi.network.HostManager> r0 = com.xiaomi.network.HostManager.class
            monitor-enter(r0)
            android.content.Context r1 = r9.getApplicationContext()     // Catch:{ all -> 0x002f }
            sAppContext = r1     // Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x000d
            sAppContext = r9     // Catch:{ all -> 0x002f }
        L_0x000d:
            com.xiaomi.network.HostManager r1 = sInstance     // Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x002d
            com.xiaomi.network.HostManager$HostManagerFactory r1 = factory     // Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x0025
            com.xiaomi.network.HostManager r1 = new com.xiaomi.network.HostManager     // Catch:{ all -> 0x002f }
            r2 = r1
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r8 = r14
            r2.<init>(r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x002f }
            sInstance = r1     // Catch:{ all -> 0x002f }
            monitor-exit(r0)
            return
        L_0x0025:
            com.xiaomi.network.HostManager$HostManagerFactory r13 = factory     // Catch:{ all -> 0x002f }
            com.xiaomi.network.HostManager r9 = r13.a(r9, r10, r11, r12)     // Catch:{ all -> 0x002f }
            sInstance = r9     // Catch:{ all -> 0x002f }
        L_0x002d:
            monitor-exit(r0)
            return
        L_0x002f:
            r9 = move-exception
            monitor-exit(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.network.HostManager.init(android.content.Context, com.xiaomi.network.HostFilter, com.xiaomi.network.HostManager$HttpGet, java.lang.String, java.lang.String, java.lang.String):void");
    }

    static String obfuscate(String str) {
        try {
            int length = str.length();
            byte[] bytes = str.getBytes("UTF-8");
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                byte b2 = b & 240;
                if (b2 != 240) {
                    bytes[i] = (byte) (((b & 15) ^ ((byte) (((b >> 4) + length) & 15))) | b2);
                }
            }
            return new String(bytes);
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    /* JADX INFO: finally extract failed */
    private ArrayList<Fallback> requestRemoteFallbacks(ArrayList<String> arrayList) {
        boolean z;
        JSONObject jSONObject;
        JSONArray jSONArray;
        JSONObject jSONObject2;
        ArrayList<String> arrayList2 = arrayList;
        purge();
        synchronized (this.mHostsMapping) {
            try {
                checkHostMapping();
                for (String next : this.mHostsMapping.keySet()) {
                    if (!arrayList2.contains(next)) {
                        arrayList2.add(next);
                    }
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        boolean isEmpty = sReservedHosts.isEmpty();
        synchronized (sReservedHosts) {
            try {
                z = isEmpty;
                for (Object obj : sReservedHosts.values().toArray()) {
                    Fallback fallback = (Fallback) obj;
                    if (!fallback.b()) {
                        sReservedHosts.remove(fallback.b);
                        z = true;
                    }
                }
            } catch (Throwable th2) {
                while (true) {
                    throw th2;
                }
            }
        }
        if (!arrayList2.contains(getHost())) {
            arrayList2.add(getHost());
        }
        ArrayList<Fallback> arrayList3 = new ArrayList<>(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList3.add(null);
        }
        try {
            String str = d.e(sAppContext) ? "wifi" : "wap";
            String remoteFallbackJSON = getRemoteFallbackJSON(arrayList2, str, this.sUserId, z);
            if (!TextUtils.isEmpty(remoteFallbackJSON)) {
                JSONObject jSONObject3 = new JSONObject(remoteFallbackJSON);
                b.b(remoteFallbackJSON);
                if ("OK".equalsIgnoreCase(jSONObject3.getString("S"))) {
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("R");
                    String string = jSONObject4.getString("province");
                    String string2 = jSONObject4.getString("city");
                    String string3 = jSONObject4.getString("isp");
                    String string4 = jSONObject4.getString(OnNativeInvokeListener.ARG_IP);
                    String string5 = jSONObject4.getString("country");
                    JSONObject jSONObject5 = jSONObject4.getJSONObject(str);
                    if (str.equals("wap")) {
                        str = getActiveNetworkLabel();
                    }
                    StringBuilder sb = new StringBuilder("get bucket: ip = ");
                    sb.append(string4);
                    sb.append(" net = ");
                    sb.append(string3);
                    sb.append(str);
                    sb.append(" hosts = ");
                    sb.append(jSONObject5.toString());
                    b.a(sb.toString());
                    int i2 = 0;
                    while (i2 < arrayList.size()) {
                        String str2 = arrayList2.get(i2);
                        JSONArray optJSONArray = jSONObject5.optJSONArray(str2);
                        if (optJSONArray == null) {
                            b.a("no bucket found for ".concat(String.valueOf(str2)));
                            jSONObject = jSONObject5;
                        } else {
                            Fallback fallback2 = new Fallback(str2);
                            int i3 = 0;
                            while (i3 < optJSONArray.length()) {
                                String string6 = optJSONArray.getString(i3);
                                if (!TextUtils.isEmpty(string6)) {
                                    jSONObject2 = jSONObject5;
                                    jSONArray = optJSONArray;
                                    fallback2.a(new c(string6, optJSONArray.length() - i3));
                                } else {
                                    jSONArray = optJSONArray;
                                    jSONObject2 = jSONObject5;
                                }
                                i3++;
                                jSONObject5 = jSONObject2;
                                optJSONArray = jSONArray;
                            }
                            jSONObject = jSONObject5;
                            arrayList3.set(i2, fallback2);
                            fallback2.g = string5;
                            fallback2.c = string;
                            fallback2.e = string3;
                            fallback2.f = string4;
                            fallback2.d = string2;
                            if (jSONObject4.has("stat-percent")) {
                                fallback2.a(jSONObject4.getDouble("stat-percent"));
                            }
                            if (jSONObject4.has("stat-domain")) {
                                fallback2.c(jSONObject4.getString("stat-domain"));
                            }
                            if (jSONObject4.has("ttl")) {
                                fallback2.a(((long) jSONObject4.getInt("ttl")) * 1000);
                            }
                            setCurrentISP(fallback2.e());
                        }
                        i2++;
                        jSONObject5 = jSONObject;
                    }
                    JSONObject optJSONObject = jSONObject4.optJSONObject("reserved");
                    if (optJSONObject != null) {
                        long j = FileCache.EXPIRE_TIME;
                        if (jSONObject4.has("reserved-ttl")) {
                            j = ((long) jSONObject4.getInt("reserved-ttl")) * 1000;
                        }
                        Iterator<String> keys = optJSONObject.keys();
                        while (keys.hasNext()) {
                            String next2 = keys.next();
                            JSONArray optJSONArray2 = optJSONObject.optJSONArray(next2);
                            if (optJSONArray2 == null) {
                                b.a("no bucket found for ".concat(String.valueOf(next2)));
                            } else {
                                Fallback fallback3 = new Fallback(next2);
                                fallback3.a(j);
                                for (int i4 = 0; i4 < optJSONArray2.length(); i4++) {
                                    String string7 = optJSONArray2.getString(i4);
                                    if (!TextUtils.isEmpty(string7)) {
                                        fallback3.a(new c(string7, optJSONArray2.length() - i4));
                                    }
                                }
                                synchronized (sReservedHosts) {
                                    if (this.sHostFilter.a(next2)) {
                                        sReservedHosts.put(next2, fallback3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("failed to get bucket ");
            sb2.append(e.getMessage());
            b.a(sb2.toString());
        } catch (Throwable th3) {
            throw th3;
        }
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            Fallback fallback4 = arrayList3.get(i5);
            if (fallback4 != null) {
                updateFallbacks(arrayList2.get(i5), fallback4);
            }
        }
        persist();
        return arrayList3;
    }

    public static synchronized void setHostManagerFactory(HostManagerFactory hostManagerFactory) {
        synchronized (HostManager.class) {
            factory = hostManagerFactory;
            sInstance = null;
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkHostMapping() {
        synchronized (this.mHostsMapping) {
            if (hostLoaded) {
                return true;
            }
            hostLoaded = true;
            this.mHostsMapping.clear();
            try {
                String loadHosts = loadHosts();
                if (!TextUtils.isEmpty(loadHosts)) {
                    fromJSON(loadHosts);
                    b.b("loading the new hosts succeed");
                    return true;
                }
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder("load bucket failure: ");
                sb.append(th.getMessage());
                b.a(sb.toString());
            }
        }
        return false;
    }

    public void clear() {
        synchronized (this.mHostsMapping) {
            this.mHostsMapping.clear();
        }
    }

    /* access modifiers changed from: protected */
    public void fromJSON(String str) {
        synchronized (this.mHostsMapping) {
            this.mHostsMapping.clear();
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("ver") != 2) {
                throw new JSONException("Bad version");
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("data");
            for (int i = 0; i < optJSONArray.length(); i++) {
                Fallbacks fromJSON = new Fallbacks().fromJSON(optJSONArray.getJSONObject(i));
                this.mHostsMapping.put(fromJSON.getHost(), fromJSON);
            }
            JSONArray optJSONArray2 = jSONObject.optJSONArray("reserved");
            for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                Fallback a = new Fallback("").a(optJSONArray2.getJSONObject(i2));
                sReservedHosts.put(a.b, a);
            }
        }
    }

    public Fallback getFallbacksByHost(String str) {
        return getFallbacksByHost(str, true);
    }

    public Fallback getFallbacksByHost(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        } else if (!this.sHostFilter.a(str)) {
            return null;
        } else {
            Fallback localFallback = getLocalFallback(str);
            if (localFallback != null && localFallback.b()) {
                return localFallback;
            }
            if (z && d.c(sAppContext)) {
                Fallback requestRemoteFallback = requestRemoteFallback(str);
                if (requestRemoteFallback != null) {
                    return requestRemoteFallback;
                }
            }
            return new b(this, str, localFallback);
        }
    }

    public Fallback getFallbacksByURL(String str) {
        if (!TextUtils.isEmpty(str)) {
            return getFallbacksByHost(new URL(str).getHost(), true);
        }
        throw new IllegalArgumentException("the url is empty");
    }

    /* access modifiers changed from: protected */
    public String getHost() {
        return "resolver.msg.xiaomi.net";
    }

    /* access modifiers changed from: protected */
    public Fallback getLocalFallback(String str) {
        Fallbacks fallbacks;
        synchronized (this.mHostsMapping) {
            checkHostMapping();
            fallbacks = this.mHostsMapping.get(str);
        }
        if (fallbacks != null) {
            Fallback fallback = fallbacks.getFallback();
            if (fallback != null) {
                return fallback;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public String getProcessName() {
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) sAppContext.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo next : runningAppProcesses) {
                if (next.pid == Process.myPid()) {
                    return next.processName;
                }
            }
        }
        return "com.xiaomi";
    }

    /* access modifiers changed from: protected */
    public String getRemoteFallbackJSON(ArrayList<String> arrayList, String str, String str2, boolean z) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<c> arrayList3 = new ArrayList<>();
        arrayList3.add(new a("type", str));
        if (str.equals("wap")) {
            arrayList3.add(new a("conpt", obfuscate(d.k(sAppContext))));
        }
        if (z) {
            arrayList3.add(new a("reserved", "1"));
        }
        arrayList3.add(new a("uuid", str2));
        arrayList3.add(new a("list", com.xiaomi.channel.commonutils.string.d.a((Collection<?>) arrayList, (String) ",")));
        Fallback localFallback = getLocalFallback("resolver.msg.xiaomi.net");
        String format = String.format(Locale.US, "http://%1$s/gslb/?ver=4.0", new Object[]{"resolver.msg.xiaomi.net"});
        if (localFallback == null) {
            arrayList2.add(format);
            synchronized (sReservedHosts) {
                Fallback fallback = sReservedHosts.get("resolver.msg.xiaomi.net");
                if (fallback != null) {
                    Iterator<String> it = fallback.a(true).iterator();
                    while (it.hasNext()) {
                        arrayList2.add(String.format(Locale.US, "http://%1$s/gslb/?ver=4.0", new Object[]{it.next()}));
                    }
                }
            }
        } else {
            arrayList2 = localFallback.a(format);
        }
        Iterator<String> it2 = arrayList2.iterator();
        IOException e = null;
        while (it2.hasNext()) {
            Builder buildUpon = Uri.parse(it2.next()).buildUpon();
            for (c cVar : arrayList3) {
                buildUpon.appendQueryParameter(cVar.a(), cVar.b());
            }
            try {
                return this.sHttpGetter == null ? d.a(sAppContext, new URL(buildUpon.toString())) : this.sHttpGetter.a(buildUpon.toString());
            } catch (IOException e2) {
                e = e2;
            }
        }
        if (e == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("network exception: ");
        sb.append(e.getMessage());
        b.a(sb.toString());
        throw e;
    }

    /* access modifiers changed from: protected */
    public String loadHosts() {
        BufferedReader bufferedReader;
        try {
            File file = new File(sAppContext.getFilesDir(), getProcessName());
            if (file.isFile()) {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            sb.append(readLine);
                        } else {
                            String sb2 = sb.toString();
                            com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
                            return sb2;
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        StringBuilder sb3 = new StringBuilder("load host exception ");
                        sb3.append(th.getMessage());
                        b.a(sb3.toString());
                        com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
                        throw th;
                    }
                }
            } else {
                com.xiaomi.channel.commonutils.file.a.a((Reader) null);
                return null;
            }
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
            com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
            throw th;
        }
    }

    public void persist() {
        synchronized (this.mHostsMapping) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(sAppContext.openFileOutput(getProcessName(), 0)));
                String jSONObject = toJSON().toString();
                if (!TextUtils.isEmpty(jSONObject)) {
                    bufferedWriter.write(jSONObject);
                }
                bufferedWriter.close();
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("persist bucket failure: ");
                sb.append(e.getMessage());
                b.a(sb.toString());
            }
        }
    }

    public void purge() {
        synchronized (this.mHostsMapping) {
            for (Fallbacks purge : this.mHostsMapping.values()) {
                purge.purge(true);
            }
            while (true) {
                for (boolean z = false; !z; z = true) {
                    for (String next : this.mHostsMapping.keySet()) {
                        if (this.mHostsMapping.get(next).getFallbacks().isEmpty()) {
                            this.mHostsMapping.remove(next);
                        }
                    }
                }
            }
        }
    }

    public void refreshFallbacks() {
        ArrayList arrayList;
        synchronized (this.mHostsMapping) {
            checkHostMapping();
            arrayList = new ArrayList(this.mHostsMapping.keySet());
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Fallbacks fallbacks = this.mHostsMapping.get(arrayList.get(size));
                if (!(fallbacks == null || fallbacks.getFallback() == null)) {
                    arrayList.remove(size);
                }
            }
        }
        ArrayList<Fallback> requestRemoteFallbacks = requestRemoteFallbacks(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            if (requestRemoteFallbacks.get(i) != null) {
                updateFallbacks((String) arrayList.get(i), requestRemoteFallbacks.get(i));
            }
        }
    }

    /* access modifiers changed from: protected */
    public Fallback requestRemoteFallback(String str) {
        if (System.currentTimeMillis() - this.lastRemoteRequestTimestamp > this.remoteRequestFailureCount * 60 * 1000) {
            this.lastRemoteRequestTimestamp = System.currentTimeMillis();
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            Fallback fallback = requestRemoteFallbacks(arrayList).get(0);
            if (fallback != null) {
                this.remoteRequestFailureCount = 0;
                return fallback;
            } else if (this.remoteRequestFailureCount < 15) {
                this.remoteRequestFailureCount++;
            }
        }
        return null;
    }

    public void setCurrentISP(String str) {
        this.currentISP = str;
    }

    /* access modifiers changed from: protected */
    public JSONObject toJSON() {
        JSONObject jSONObject;
        synchronized (this.mHostsMapping) {
            jSONObject = new JSONObject();
            jSONObject.put("ver", 2);
            JSONArray jSONArray = new JSONArray();
            for (Fallbacks json : this.mHostsMapping.values()) {
                jSONArray.put(json.toJSON());
            }
            jSONObject.put("data", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            for (Fallback f : sReservedHosts.values()) {
                jSONArray2.put(f.f());
            }
            jSONObject.put("reserved", jSONArray2);
        }
        return jSONObject;
    }

    public void updateFallbacks(String str, Fallback fallback) {
        if (TextUtils.isEmpty(str) || fallback == null) {
            StringBuilder sb = new StringBuilder("the argument is invalid ");
            sb.append(str);
            sb.append(", ");
            sb.append(fallback);
            throw new IllegalArgumentException(sb.toString());
        } else if (this.sHostFilter.a(str)) {
            synchronized (this.mHostsMapping) {
                checkHostMapping();
                if (this.mHostsMapping.containsKey(str)) {
                    this.mHostsMapping.get(str).addFallback(fallback);
                } else {
                    Fallbacks fallbacks = new Fallbacks(str);
                    fallbacks.addFallback(fallback);
                    this.mHostsMapping.put(str, fallbacks);
                }
            }
        }
    }
}
