package com.amap.bundle.network.request.param;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.device.DeviceInfo;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.util.RomUtil;
import com.autonavi.server.aos.serverkey;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.ta.audid.Constants;
import com.ut.device.UTDevice;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NetworkParam {
    private static String A = aaf.b("ts_mqtt_url_ssl");
    private static String B = aaf.b(ConfigerHelper.SEARCH_AOS_URL_KEY);
    private static String C = aaf.b(ConfigerHelper.OPERATIONAL_URL_KEY);
    private static String D = getdai();
    private static String E;
    private static String F;
    private static String G;
    private static d H;
    private static final d I = new d() {
        public final String a() {
            return "";
        }

        public final String b() {
            return "";
        }

        public final String c() {
            return "";
        }

        public final String d() {
            return "";
        }

        public final String e() {
            return "";
        }

        public final String f() {
            return "";
        }

        public final String g() {
            return "";
        }

        public final String h() {
            return "";
        }

        public final String i() {
            return "";
        }

        public final String j() {
            return "";
        }
    };
    private static Context a = AMapAppGlobal.getApplication();
    private static String b = getDiv();
    private static String c;
    private static String d = getDip();
    private static String e = getDic();
    private static String f = getDiu();
    private static String g;
    private static String h;
    private static String i = getMac();
    private static String j = getIsn();
    private static String k;
    private static volatile long l;
    private static volatile long m;
    private static volatile long n;
    private static String o = getTaobaoID();
    private static String p = getDib();
    private static String q;
    private static String r = getBid();
    private static String s = getAeTraffic();
    private static String t = aaf.b(ConfigerHelper.AOS_URL_KEY);
    private static String u = aaf.b(ConfigerHelper.DRIVE_AOS_URL_KEY);
    private static String v = aaf.b(ConfigerHelper.AOS_SNS_URL_KEY);
    private static String w = aaf.b("aos_sync_url");
    private static String x = aaf.b(ConfigerHelper.AOS_TS_POLLING_URL_KEY);
    private static String y = aaf.b("ts_polling_https_url");
    private static String z = aaf.b("ts_mqtt_url");

    public static String getDib() {
        if (TextUtils.isEmpty(p)) {
            p = b().f();
        }
        return p;
    }

    public static String getAeTraffic() {
        if (TextUtils.isEmpty(s)) {
            s = b().e();
        }
        return s;
    }

    public static String getCsid() {
        return UUID.randomUUID().toString();
    }

    public static String getBid() {
        String str = "";
        String str2 = Build.MANUFACTURER;
        if ("HUAWEI".equalsIgnoreCase(str2)) {
            str = ahu.a(a, "ro.autonavi.bid", "");
        } else if (RomUtil.ROM_OPPO.equalsIgnoreCase(str2)) {
            str = a("data/etc/appchannel/amapconfig.ini");
        } else if ("XIAOMI".equalsIgnoreCase(str2)) {
            String channelPath = getChannelPath(a.getPackageName());
            if (!TextUtils.isEmpty(channelPath)) {
                str = a(channelPath);
            } else {
                str = null;
            }
        }
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        return a("system/etc/amapconfig.ini");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x004f A[SYNTHETIC, Splitter:B:30:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x005a A[SYNTHETIC, Splitter:B:37:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0066 A[SYNTHETIC, Splitter:B:44:0x0066] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:34:0x0055=Splitter:B:34:0x0055, B:27:0x004a=Splitter:B:27:0x004a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.lang.String r4) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            boolean r4 = r0.exists()
            if (r4 != 0) goto L_0x0014
            return r1
        L_0x0014:
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0048, all -> 0x0045 }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0048, all -> 0x0045 }
            r2.<init>(r0)     // Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0048, all -> 0x0045 }
            r4.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0053, IOException -> 0x0048, all -> 0x0045 }
            java.lang.String r0 = r4.readLine()     // Catch:{ FileNotFoundException -> 0x0043, IOException -> 0x0041 }
            if (r0 == 0) goto L_0x003d
            java.lang.String r2 = "bid="
            boolean r3 = r0.startsWith(r2)     // Catch:{ FileNotFoundException -> 0x0043, IOException -> 0x0041 }
            if (r3 == 0) goto L_0x003d
            int r2 = r2.length()     // Catch:{ FileNotFoundException -> 0x0043, IOException -> 0x0041 }
            java.lang.String r0 = r0.substring(r2)     // Catch:{ FileNotFoundException -> 0x0043, IOException -> 0x0041 }
            r4.close()     // Catch:{ IOException -> 0x0038 }
            goto L_0x003c
        L_0x0038:
            r4 = move-exception
            r4.printStackTrace()
        L_0x003c:
            return r0
        L_0x003d:
            r4.close()     // Catch:{ IOException -> 0x005e }
            goto L_0x0062
        L_0x0041:
            r0 = move-exception
            goto L_0x004a
        L_0x0043:
            r0 = move-exception
            goto L_0x0055
        L_0x0045:
            r0 = move-exception
            r4 = r1
            goto L_0x0064
        L_0x0048:
            r0 = move-exception
            r4 = r1
        L_0x004a:
            r0.printStackTrace()     // Catch:{ all -> 0x0063 }
            if (r4 == 0) goto L_0x0062
            r4.close()     // Catch:{ IOException -> 0x005e }
            goto L_0x0062
        L_0x0053:
            r0 = move-exception
            r4 = r1
        L_0x0055:
            r0.printStackTrace()     // Catch:{ all -> 0x0063 }
            if (r4 == 0) goto L_0x0062
            r4.close()     // Catch:{ IOException -> 0x005e }
            goto L_0x0062
        L_0x005e:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0062:
            return r1
        L_0x0063:
            r0 = move-exception
        L_0x0064:
            if (r4 == 0) goto L_0x006e
            r4.close()     // Catch:{ IOException -> 0x006a }
            goto L_0x006e
        L_0x006a:
            r4 = move-exception
            r4.printStackTrace()
        L_0x006e:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.network.request.param.NetworkParam.a(java.lang.String):java.lang.String");
    }

    public static String getChannelPath(String str) {
        try {
            return (String) Class.forName("miui.os.MiuiInit").getMethod("getMiuiChannelPath", new Class[]{String.class}).invoke(null, new Object[]{str});
        } catch (Exception unused) {
            return "";
        }
    }

    public static LinkedHashMap<String, String> getNetworkParamMap(String str) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(LocationParams.PARA_COMMON_DIV, getDiv());
        String siv = getSiv();
        if (TextUtils.isEmpty(siv)) {
            siv = getDiv();
        }
        linkedHashMap.put("siv", siv);
        linkedHashMap.put("siv", getSiv());
        linkedHashMap.put(LocationParams.PARA_COMMON_DIP, getDip());
        linkedHashMap.put(LocationParams.PARA_COMMON_DIC, getDic());
        linkedHashMap.put(LocationParams.PARA_COMMON_DIU, getDiu());
        String adiu = getAdiu();
        if (!TextUtils.isEmpty(adiu)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_ADIU, adiu);
        }
        String adiu_extras = getAdiu_extras();
        if (!TextUtils.isEmpty(adiu_extras)) {
            linkedHashMap.put("adiu_extras", adiu_extras);
        }
        linkedHashMap.put(LocationParams.PARA_COMMON_DIU2, getMac());
        linkedHashMap.put(LocationParams.PARA_COMMON_DIU3, getIsn());
        linkedHashMap.put("dai", getdai());
        linkedHashMap.put(LocationParams.PARA_COMMON_CIFA, getCifa());
        String sa = getSa();
        if (!TextUtils.isEmpty(sa)) {
            linkedHashMap.put("sa", sa);
        }
        linkedHashMap.put("session", String.valueOf(getSession()));
        linkedHashMap.put("appstartid", String.valueOf(getAppstartid()));
        linkedHashMap.put("stepid", String.valueOf(genStepId()));
        linkedHashMap.put("channel", serverkey.getAosChannel());
        String a2 = a();
        if (a2 != null) {
            linkedHashMap.put(Oauth2AccessToken.KEY_UID, a2);
        }
        String spm = getSpm();
        if (!TextUtils.isEmpty(spm)) {
            linkedHashMap.put("spm", spm);
        }
        String taobaoID = getTaobaoID();
        o = taobaoID;
        if (taobaoID != null) {
            linkedHashMap.put("tid", getTaobaoID());
        }
        String dib = getDib();
        p = dib;
        if (!TextUtils.isEmpty(dib)) {
            linkedHashMap.put(ConfigerHelper.CONF_DIB_KEY, p);
        }
        linkedHashMap.put("client_network_class", NetworkReachability.c().toString());
        linkedHashMap.put(LocationParams.PARA_COMMON_DIBV, getDibv());
        linkedHashMap.put("BID_F", r == null ? "" : r);
        Map<String, String> a3 = aag.a(str);
        if (a3 != null) {
            for (String next : a3.keySet()) {
                String str2 = a3.get(next);
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(str2)) {
                    linkedHashMap.put(next, str2);
                }
            }
        }
        linkedHashMap.put(ConfigerHelper.AETRAFFIC_KEY, getAeTraffic());
        String d2 = b().d();
        if (!TextUtils.isEmpty(d2)) {
            linkedHashMap.put("ct_id", d2);
        }
        return linkedHashMap;
    }

    public static LinkedHashMap<String, String> getNetworkOptParamMap(String str) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        Set<String> c2 = aao.d().b().c();
        if (c2.contains(LocationParams.PARA_COMMON_DIV)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_DIV, getDiv());
        }
        if (c2.contains("siv")) {
            String siv = getSiv();
            if (TextUtils.isEmpty(siv)) {
                siv = getDiv();
            }
            linkedHashMap.put("siv", siv);
        }
        if (c2.contains(LocationParams.PARA_COMMON_DIP)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_DIP, getDip());
        }
        if (c2.contains(LocationParams.PARA_COMMON_DIC)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_DIC, getDic());
        }
        if (c2.contains(LocationParams.PARA_COMMON_DIU)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_DIU, getDiu());
        }
        if (c2.contains(LocationParams.PARA_COMMON_ADIU)) {
            String adiu = getAdiu();
            if (!TextUtils.isEmpty(adiu)) {
                linkedHashMap.put(LocationParams.PARA_COMMON_ADIU, adiu);
            }
        }
        if (c2.contains(LocationParams.PARA_COMMON_DIU2)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_DIU2, i);
        }
        if (c2.contains(LocationParams.PARA_COMMON_DIU3)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_DIU3, j);
        }
        if (c2.contains("dai")) {
            linkedHashMap.put("dai", getdai());
        }
        if (c2.contains(LocationParams.PARA_COMMON_CIFA)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_CIFA, getOptCifa());
        }
        if (c2.contains("sa")) {
            String sa = getSa();
            if (!TextUtils.isEmpty(sa)) {
                linkedHashMap.put("sa", sa);
            }
        }
        if (c2.contains("session")) {
            linkedHashMap.put("session", String.valueOf(getSession()));
        }
        if (c2.contains("appstartid")) {
            linkedHashMap.put("appstartid", String.valueOf(getAppstartid()));
        }
        if (c2.contains("stepid")) {
            linkedHashMap.put("stepid", String.valueOf(genStepId()));
        }
        if (c2.contains("channel")) {
            linkedHashMap.put("channel", serverkey.getAosChannel());
        }
        if (c2.contains(Oauth2AccessToken.KEY_UID)) {
            String a2 = a();
            if (a2 != null) {
                linkedHashMap.put(Oauth2AccessToken.KEY_UID, a2);
            }
        }
        if (c2.contains("spm")) {
            String spm = getSpm();
            if (spm != null && spm.length() > 0) {
                linkedHashMap.put("spm", spm);
            }
        }
        if (c2.contains("tid")) {
            String taobaoID = getTaobaoID();
            o = taobaoID;
            if (taobaoID != null) {
                linkedHashMap.put("tid", o);
            }
        }
        if (c2.contains(ConfigerHelper.CONF_DIB_KEY)) {
            String dib = getDib();
            p = dib;
            if (dib != null && p.length() > 0) {
                linkedHashMap.put(ConfigerHelper.CONF_DIB_KEY, p);
            }
        }
        if (c2.contains("client_network_class") && a != null) {
            linkedHashMap.put("client_network_class", NetworkReachability.c().toString());
        }
        if (c2.contains(LocationParams.PARA_COMMON_DIBV)) {
            linkedHashMap.put(LocationParams.PARA_COMMON_DIBV, getDibv());
        }
        if (c2.contains("BID_F")) {
            linkedHashMap.put("BID_F", r == null ? "" : r);
        }
        Map<String, String> a3 = aag.a(str);
        if (a3 != null) {
            for (String next : a3.keySet()) {
                String str2 = a3.get(next);
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(str2)) {
                    linkedHashMap.put(next, str2);
                }
            }
        }
        if (c2.contains(ConfigerHelper.AETRAFFIC_KEY)) {
            linkedHashMap.put(ConfigerHelper.AETRAFFIC_KEY, getAeTraffic());
        }
        if (c2.contains("ct_id")) {
            String d2 = b().d();
            if (!TextUtils.isEmpty(d2)) {
                linkedHashMap.put("ct_id", d2);
            }
        }
        return linkedHashMap;
    }

    public static io getAosCommonParam(String str, int i2) {
        io ioVar = new io();
        ioVar.b.put("x-gen", getGenID());
        if (i2 == -1) {
            return ioVar;
        }
        boolean c2 = aao.d().c();
        boolean a2 = aao.d().a(str);
        if (a2) {
            ioVar.b.put("amap_opt", c2 ? "1" : "0");
        }
        if (!c2 || !a2) {
            ioVar.a.putAll(getNetworkParamMap(str));
        } else {
            ioVar.a.putAll(getNetworkOptParamMap(str));
        }
        return ioVar;
    }

    @Deprecated
    public static String getNetworkParam(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder("&div=");
        sb.append(b);
        sb.append("&dip=");
        sb.append(d);
        sb.append("&dic=");
        sb.append(e);
        sb.append("&diu=");
        sb.append(f);
        stringBuffer.append(sb.toString());
        String adiu = getAdiu();
        if (!TextUtils.isEmpty(adiu)) {
            stringBuffer.append("&adiu=".concat(String.valueOf(adiu)));
        }
        String adiu_extras = getAdiu_extras();
        if (!TextUtils.isEmpty(adiu_extras)) {
            stringBuffer.append("&adiu_extras=".concat(String.valueOf(adiu_extras)));
        }
        StringBuilder sb2 = new StringBuilder("&diu2=");
        sb2.append(i);
        stringBuffer.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("&diu3=");
        sb3.append(j);
        stringBuffer.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder("&dai=");
        sb4.append(getdai());
        stringBuffer.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder("&cifa=");
        sb5.append(getCifa());
        stringBuffer.append(sb5.toString());
        String sa = getSa();
        if (!TextUtils.isEmpty(sa)) {
            stringBuffer.append("&sa=".concat(String.valueOf(sa)));
        }
        StringBuilder sb6 = new StringBuilder("&session=");
        sb6.append(getSession());
        stringBuffer.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder("&appstartid=");
        sb7.append(getAppstartid());
        stringBuffer.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder("&stepid=");
        sb8.append(genStepId());
        stringBuffer.append(sb8.toString());
        String a2 = a();
        if (a2 != null && a2.length() > 0) {
            stringBuffer.append("&uid=".concat(String.valueOf(a2)));
        }
        String spm = getSpm();
        if (spm != null && spm.length() > 0) {
            stringBuffer.append("&spm=".concat(String.valueOf(spm)));
        }
        if (o != null) {
            StringBuilder sb9 = new StringBuilder("&tid=");
            sb9.append(Uri.encode(o, "UTF-8"));
            stringBuffer.append(sb9.toString());
        }
        if (p != null && p.length() > 0) {
            StringBuilder sb10 = new StringBuilder("&dib=");
            sb10.append(Uri.encode(p, "UTF-8"));
            stringBuffer.append(sb10.toString());
        }
        StringBuilder sb11 = new StringBuilder("&client_network_class=");
        sb11.append(NetworkReachability.c());
        stringBuffer.append(sb11.toString());
        if (getDibv() != null) {
            StringBuilder sb12 = new StringBuilder("&dibv=");
            sb12.append(Uri.encode(getDibv(), "UTF-8"));
            stringBuffer.append(sb12.toString());
        } else {
            stringBuffer.append("&dibv=00");
        }
        StringBuilder sb13 = new StringBuilder("&BID_F=");
        sb13.append(r == null ? "" : r);
        stringBuffer.append(sb13.toString());
        Map<String, String> a3 = aag.a(str);
        if (a3 != null) {
            for (String next : a3.keySet()) {
                String str2 = a3.get(next);
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(str2)) {
                    StringBuilder sb14 = new StringBuilder("&");
                    sb14.append(next);
                    sb14.append("=");
                    sb14.append(Uri.encode(str2, "UTF-8"));
                    stringBuffer.append(sb14.toString());
                }
            }
        }
        StringBuilder sb15 = new StringBuilder("&aetraffic=");
        sb15.append(getAeTraffic());
        stringBuffer.append(sb15.toString());
        String d2 = b().d();
        if (!TextUtils.isEmpty(d2)) {
            StringBuilder sb16 = new StringBuilder("&ct_id=");
            sb16.append(Uri.encode(d2, "UTF-8"));
            stringBuffer.append(sb16.toString());
        }
        return stringBuffer.toString();
    }

    @Deprecated
    public static String getNetworkParamForSns(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder("&div=");
        sb.append(b);
        sb.append("&dip=");
        sb.append(d);
        sb.append("&dic=");
        sb.append(e);
        sb.append("&diu=");
        sb.append(f);
        stringBuffer.append(sb.toString());
        String adiu = getAdiu();
        if (!TextUtils.isEmpty(adiu)) {
            stringBuffer.append("&adiu=".concat(String.valueOf(adiu)));
        }
        String adiu_extras = getAdiu_extras();
        if (!TextUtils.isEmpty(adiu_extras)) {
            stringBuffer.append("&adiu_extras=".concat(String.valueOf(adiu_extras)));
        }
        StringBuilder sb2 = new StringBuilder("&diu2=");
        sb2.append(i);
        stringBuffer.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("&diu3=");
        sb3.append(j);
        stringBuffer.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder("&dai=");
        sb4.append(getdai());
        stringBuffer.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder("&cifa=");
        sb5.append(getCifa());
        stringBuffer.append(sb5.toString());
        String sa = getSa();
        if (!TextUtils.isEmpty(sa)) {
            stringBuffer.append("&sa=".concat(String.valueOf(sa)));
        }
        StringBuilder sb6 = new StringBuilder("&session=");
        sb6.append(getSession());
        stringBuffer.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder("&appstartid=");
        sb7.append(getAppstartid());
        stringBuffer.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder("&stepid=");
        sb8.append(genStepId());
        stringBuffer.append(sb8.toString());
        String a2 = a();
        if (a2 != null && a2.length() > 0) {
            stringBuffer.append("&uid=".concat(String.valueOf(a2)));
        }
        String spm = getSpm();
        if (spm != null && spm.length() > 0) {
            stringBuffer.append("&spm=".concat(String.valueOf(spm)));
        }
        if (o != null) {
            StringBuilder sb9 = new StringBuilder("&tid=");
            sb9.append(o);
            stringBuffer.append(sb9.toString());
        }
        if (p != null && p.length() > 0) {
            StringBuilder sb10 = new StringBuilder("&dib=");
            sb10.append(p);
            stringBuffer.append(sb10.toString());
        }
        StringBuilder sb11 = new StringBuilder("&client_network_class=");
        sb11.append(NetworkReachability.c());
        stringBuffer.append(sb11.toString());
        if (getDibv() != null) {
            StringBuilder sb12 = new StringBuilder("&dibv=");
            sb12.append(getDibv());
            stringBuffer.append(sb12.toString());
        } else {
            stringBuffer.append("&dibv=00");
        }
        StringBuilder sb13 = new StringBuilder("&BID_F=");
        sb13.append(r == null ? "" : r);
        stringBuffer.append(sb13.toString());
        Map<String, String> a3 = aag.a(str);
        if (a3 != null) {
            for (String next : a3.keySet()) {
                String str2 = a3.get(next);
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(str2)) {
                    StringBuilder sb14 = new StringBuilder("&");
                    sb14.append(next);
                    sb14.append("=");
                    sb14.append(Uri.encode(str2, "UTF-8"));
                    stringBuffer.append(sb14.toString());
                }
            }
        }
        StringBuilder sb15 = new StringBuilder("&aetraffic=");
        sb15.append(getAeTraffic());
        stringBuffer.append(sb15.toString());
        String d2 = b().d();
        if (!TextUtils.isEmpty(d2)) {
            stringBuffer.append("&ct_id=".concat(String.valueOf(d2)));
        }
        return stringBuffer.toString();
    }

    public static String getAosServerUrl() {
        if (TextUtils.isEmpty(t)) {
            t = aaf.b(ConfigerHelper.AOS_URL_KEY);
        }
        return t;
    }

    public static String getDriveAosServerUrl() {
        if (TextUtils.isEmpty(u)) {
            u = aaf.b(ConfigerHelper.DRIVE_AOS_URL_KEY);
        }
        return u;
    }

    public static String getSearchAosServerUrl() {
        if (TextUtils.isEmpty(B)) {
            B = aaf.b(ConfigerHelper.SEARCH_AOS_URL_KEY);
        }
        return B;
    }

    public static String getOperationalServerUrl() {
        if (TextUtils.isEmpty(C)) {
            C = aaf.b(ConfigerHelper.OPERATIONAL_URL_KEY);
        }
        return C;
    }

    public static String getAosSnsUrl() {
        if (TextUtils.isEmpty(v)) {
            v = aaf.b(ConfigerHelper.AOS_SNS_URL_KEY);
        }
        return v;
    }

    public static String getAosSyncUrl() {
        if (TextUtils.isEmpty(w)) {
            w = aaf.b("aos_sync_url");
        }
        return w;
    }

    public static String getAosTsPollingUrl() {
        if (TextUtils.isEmpty(x)) {
            x = aaf.b(ConfigerHelper.AOS_TS_POLLING_URL_KEY);
        }
        return x;
    }

    public static String getAosTsPollingHttpsUrl() {
        if (TextUtils.isEmpty(y)) {
            y = aaf.b("ts_polling_https_url");
        }
        return y;
    }

    public static String getAosTsMqttUrl() {
        if (TextUtils.isEmpty(z)) {
            z = aaf.b("ts_mqtt_url");
        }
        return z;
    }

    public static String getAosTsMqttUrlSSL() {
        if (TextUtils.isEmpty(A)) {
            A = aaf.b("ts_mqtt_url_ssl");
        }
        return A;
    }

    public static String getdai() {
        if (D != null) {
            return D;
        }
        String c2 = agm.c(a);
        D = c2;
        return c2;
    }

    public static String getdsn() {
        if (E != null) {
            return E;
        }
        String a2 = agm.a();
        E = a2;
        return a2;
    }

    public static String getdcs() {
        if (F != null) {
            return F;
        }
        String b2 = agm.b();
        F = b2;
        return b2;
    }

    public static String getIsn() {
        if (j != null) {
            return j;
        }
        SharedPreferences b2 = b("SharedPreferences");
        String string = b2 != null ? b2.getString("isn", getGuid()) : getGuid();
        if (string == null) {
            string = "";
        }
        String a2 = agy.a(string);
        if (a2 == null) {
            a2 = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(string);
        sb.append("-");
        sb.append(a2);
        j = sb.toString();
        if (b2 == null) {
            return j;
        }
        Editor edit = b2.edit();
        edit.putString("isn", string);
        edit.apply();
        return j;
    }

    public static String getGuid() {
        try {
            String uuid = UUID.randomUUID().toString();
            try {
                return uuid.replace("-", "");
            } catch (Exception unused) {
                return uuid;
            }
        } catch (Exception unused2) {
            return "";
        }
    }

    private static SharedPreferences b(String str) {
        return new MapSharePreference(str).sharedPrefs();
    }

    public static String getMac() {
        if (i != null) {
            return i;
        }
        SharedPreferences b2 = b("SharedPreferences");
        String string = b2 != null ? b2.getString("user_mac", "") : "";
        if (string == null || string.length() <= 0) {
            string = agq.d(a);
            if (!(string == null || string.length() <= 0 || b2 == null)) {
                Editor edit = b2.edit();
                edit.putString("user_mac", string);
                edit.apply();
            }
        }
        i = string;
        return string;
    }

    public static String getCifa() {
        return DeviceInfo.getInstance(a).toString();
    }

    public static String getOptCifa() {
        return DeviceInfo.getInstance(a).toShortString();
    }

    public static String getDiv() {
        if (TextUtils.isEmpty(b)) {
            b = b().i();
        }
        return b;
    }

    public static String getSiv() {
        if (TextUtils.isEmpty(c)) {
            c = b().j();
        }
        return c;
    }

    public static String getDip() {
        if (TextUtils.isEmpty(d)) {
            d = b().g();
        }
        return d;
    }

    public static String getDic() {
        if (TextUtils.isEmpty(e)) {
            e = b().h();
        }
        return e;
    }

    public static String getDiu() {
        if (!TextUtils.isEmpty(f)) {
            return f;
        }
        String a2 = kn.a(a);
        f = a2;
        return a2;
    }

    public static String getAdiu() {
        if (TextUtils.isEmpty(g)) {
            g = b().a();
        }
        return g;
    }

    public static String getAdiu_extras() {
        if (TextUtils.isEmpty(h)) {
            h = b().b();
        }
        return h;
    }

    public static String getGenID() {
        km a2 = km.a();
        return TextUtils.isEmpty(a2.a) ? "gen000000000000000000000000000000000" : a2.a;
    }

    public static String getDibv() {
        String str;
        if (q == null) {
            PackageInfo b2 = ahk.b(AMapAppGlobal.getApplication());
            if (b2 == null) {
                str = "";
            } else {
                String str2 = b2.versionName;
                if (str2 == null) {
                    str = "";
                } else {
                    int lastIndexOf = str2.lastIndexOf(32);
                    if (lastIndexOf != -1) {
                        str2 = str2.substring(0, lastIndexOf);
                    }
                    String[] split = str2.split("[.]");
                    if (split.length < 4) {
                        str = "";
                    } else {
                        str = split[3];
                    }
                }
            }
            q = str;
        }
        if (q == null) {
            q = "00";
        }
        return q;
    }

    public static String getSa() {
        return k;
    }

    public static void setSa(String str) {
        if (str != null) {
            try {
                if (!"amap".equals(str)) {
                    k = Uri.encode(str, "UTF-8");
                    return;
                }
            } catch (Exception unused) {
                return;
            }
        }
        k = null;
    }

    public static long getSession() {
        if (l == 0) {
            l = lf.a();
        }
        return l;
    }

    public static String getComboId() {
        String diu = getDiu();
        if (!TextUtils.isEmpty(diu)) {
            return diu;
        }
        String adiu = getAdiu();
        return TextUtils.isEmpty(adiu) ? getTaobaoID() : adiu;
    }

    public static String getSpm() {
        String str = b;
        String str2 = f;
        String str3 = i;
        String str4 = j;
        StringBuilder sb = new StringBuilder();
        sb.append(l);
        return serverkey.getSpm(str, str2, str3, str4, sb.toString());
    }

    public static long getStepId() {
        return n;
    }

    public static long genStepId() {
        long j2 = n + 1;
        n = j2;
        return j2;
    }

    public static void clearSession() {
        l = 0;
        n = 0;
    }

    public static String getTaobaoID() {
        if (TextUtils.isEmpty(o)) {
            try {
                o = UTDevice.getUtdid(a);
            } catch (Exception unused) {
                return Constants.UTDID_INVALID;
            }
        }
        return o;
    }

    public static String getDeviceToken(Context context) {
        if (G != null) {
            return G;
        }
        SharedPreferences b2 = b("SharedPreferences");
        String string = b2 != null ? b2.getString("device_token", "") : "";
        if (string == null || string.length() <= 0) {
            String d2 = agq.d(context);
            if (d2 == null || d2.length() <= 0) {
                String isn = getIsn();
                j = isn;
                if (isn == null || j.length() <= 0) {
                    return G;
                }
                String diu = getDiu();
                StringBuilder sb = new StringBuilder();
                sb.append(diu);
                sb.append("-");
                sb.append(j.substring(0, j.indexOf("-")));
                G = sb.toString();
                if (b2 != null) {
                    Editor edit = b2.edit();
                    edit.putString("device_token", G);
                    edit.commit();
                }
                return G;
            }
            String diu2 = getDiu();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(diu2);
            sb2.append("-");
            sb2.append(d2);
            G = sb2.toString();
            if (b2 != null) {
                Editor edit2 = b2.edit();
                edit2.putString("device_token", G);
                edit2.commit();
            }
            return G;
        }
        G = string;
        return string;
    }

    private static String a() {
        return b().c();
    }

    public static long getAppstartid() {
        if (m == 0) {
            m = lf.a();
        }
        return m;
    }

    public static synchronized void clearAppstartid() {
        synchronized (NetworkParam.class) {
            m = 0;
        }
    }

    @NonNull
    private static d b() {
        if (H != null) {
            return H;
        }
        d b2 = aaf.b();
        H = b2;
        if (b2 == null) {
            return I;
        }
        return H;
    }
}
