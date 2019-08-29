package com.alipay.security.mobile.module.deviceinfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Base64;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationInfo {
    public static final long REQUEST_LOCATE_INTERVAL = 300000;
    static final int SECURITY_EAP = 3;
    static final int SECURITY_NONE = 0;
    static final int SECURITY_PSK = 2;
    static final int SECURITY_WEP = 1;
    private String bssid;
    private String cellId;
    private volatile int cellRssi = 0;
    private Context context;
    private String isWifiActive;
    private String lac;
    private String latitude;
    private String longitude;
    private String mcc;
    private String mnc;
    private String ssid;
    private String wifiStrength;

    private LocationInfo() {
    }

    public static LocationInfo getLocationInfo(Context context2) {
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.context = context2;
        setCellInfos(context2, locationInfo);
        setLocationInfos(context2, locationInfo);
        setWifiInfos(context2, locationInfo);
        return locationInfo;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x006d A[Catch:{ Throwable -> 0x0175 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void setLocationInfos(android.content.Context r14, com.alipay.security.mobile.module.deviceinfo.LocationInfo r15) {
        /*
            java.lang.Class<android.content.Context> r0 = android.content.Context.class
            java.lang.String r1 = "Z2V0U3lzdGVtU2VydmljZQ=="
            java.lang.String r1 = decodeString(r1)     // Catch:{ Throwable -> 0x0175 }
            r2 = 1
            java.lang.Class[] r3 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            r5 = 0
            r3[r5] = r4     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r6 = "location"
            r4[r5] = r6     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object r0 = invoke(r14, r0, r1, r3, r4)     // Catch:{ Throwable -> 0x0175 }
            r1 = 2
            r3 = 0
            if (r0 == 0) goto L_0x006a
            java.lang.String r4 = "android.support.v4.content.ContextCompat"
            java.lang.Class r4 = java.lang.Class.forName(r4)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r6 = "checkSelfPermission"
            java.lang.Class[] r7 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class<android.content.Context> r8 = android.content.Context.class
            r7[r5] = r8     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r7[r2] = r8     // Catch:{ Throwable -> 0x0175 }
            java.lang.reflect.Method r4 = r4.getMethod(r6, r7)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0175 }
            r6[r5] = r14     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r7 = "android.permission.ACCESS_COARSE_LOCATION"
            r6[r2] = r7     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object r4 = r4.invoke(r3, r6)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ Throwable -> 0x0175 }
            int r4 = r4.intValue()     // Catch:{ Throwable -> 0x0175 }
            if (r4 != 0) goto L_0x006a
            java.lang.String r4 = "aXNQcm92aWRlckVuYWJsZWQ="
            java.lang.String r4 = decodeString(r4)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r6[r5] = r7     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r8 = "gps"
            r7[r5] = r8     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object r4 = invoke(r0, r4, r6, r7)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ Throwable -> 0x0175 }
            if (r4 == 0) goto L_0x006a
            boolean r4 = r4.booleanValue()     // Catch:{ Throwable -> 0x0175 }
            if (r4 == 0) goto L_0x006a
            r4 = 1
            goto L_0x006b
        L_0x006a:
            r4 = 0
        L_0x006b:
            if (r4 == 0) goto L_0x00fa
            com.alipay.security.mobile.module.deviceinfo.SecLocationListener r4 = new com.alipay.security.mobile.module.deviceinfo.SecLocationListener     // Catch:{ Throwable -> 0x0175 }
            r4.<init>()     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r6 = "cmVxdWVzdExvY2F0aW9uVXBkYXRlcw=="
            java.lang.String r6 = decodeString(r6)     // Catch:{ Throwable -> 0x0175 }
            r7 = 5
            java.lang.Class[] r8 = new java.lang.Class[r7]     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            r8[r5] = r9     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class r9 = java.lang.Long.TYPE     // Catch:{ Throwable -> 0x0175 }
            r8[r2] = r9     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class r9 = java.lang.Float.TYPE     // Catch:{ Throwable -> 0x0175 }
            r8[r1] = r9     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class<android.location.LocationListener> r9 = android.location.LocationListener.class
            r10 = 3
            r8[r10] = r9     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class<android.os.Looper> r9 = android.os.Looper.class
            r11 = 4
            r8[r11] = r9     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r9 = "network"
            r7[r5] = r9     // Catch:{ Throwable -> 0x0175 }
            r12 = 300000(0x493e0, double:1.482197E-318)
            java.lang.Long r9 = java.lang.Long.valueOf(r12)     // Catch:{ Throwable -> 0x0175 }
            r7[r2] = r9     // Catch:{ Throwable -> 0x0175 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x0175 }
            r7[r1] = r9     // Catch:{ Throwable -> 0x0175 }
            r7[r10] = r4     // Catch:{ Throwable -> 0x0175 }
            android.os.Looper r4 = android.os.Looper.getMainLooper()     // Catch:{ Throwable -> 0x0175 }
            r7[r11] = r4     // Catch:{ Throwable -> 0x0175 }
            invoke(r0, r6, r8, r7)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r4 = "removeUpdates"
            invoke(r0, r4, r3, r3)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r4 = "Z2V0TGFzdEtub3duTG9jYXRpb24="
            java.lang.String r4 = decodeString(r4)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x0175 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r6[r5] = r7     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r8 = "network"
            r7[r5] = r8     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object r0 = invoke(r0, r4, r6, r7)     // Catch:{ Throwable -> 0x0175 }
            if (r0 == 0) goto L_0x00fa
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0175 }
            r4.<init>()     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r5 = "getLatitude"
            java.lang.Object r5 = invoke(r0, r5, r3, r3)     // Catch:{ Throwable -> 0x0175 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0175 }
            r15.setLatitude(r4)     // Catch:{ Throwable -> 0x0175 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0175 }
            r4.<init>()     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r5 = "getLatitude"
            java.lang.Object r0 = invoke(r0, r5, r3, r3)     // Catch:{ Throwable -> 0x0175 }
            r4.append(r0)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r0 = r4.toString()     // Catch:{ Throwable -> 0x0175 }
            r15.setLongitude(r0)     // Catch:{ Throwable -> 0x0175 }
            goto L_0x00fb
        L_0x00fa:
            r2 = 0
        L_0x00fb:
            java.lang.String r0 = "phone"
            java.lang.Object r14 = r14.getSystemService(r0)     // Catch:{ Throwable -> 0x0175 }
            android.telephony.TelephonyManager r14 = (android.telephony.TelephonyManager) r14     // Catch:{ Throwable -> 0x0175 }
            if (r2 != 0) goto L_0x0174
            int r0 = r14.getPhoneType()     // Catch:{ Throwable -> 0x0175 }
            if (r0 != r1) goto L_0x0174
            java.lang.Class<android.telephony.TelephonyManager> r0 = android.telephony.TelephonyManager.class
            java.lang.String r1 = "Z2V0Q2VsbExvY2F0aW9u"
            java.lang.String r1 = decodeString(r1)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object r14 = invoke(r14, r0, r1, r3, r3)     // Catch:{ Throwable -> 0x0175 }
            if (r14 == 0) goto L_0x0174
            java.lang.String r0 = r15.getLatitude()     // Catch:{ Throwable -> 0x0175 }
            boolean r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r0)     // Catch:{ Throwable -> 0x0175 }
            if (r0 == 0) goto L_0x0174
            java.lang.String r0 = r15.getLongitude()     // Catch:{ Throwable -> 0x0175 }
            boolean r0 = com.alipay.security.mobile.module.commonutils.CommonUtils.isBlank(r0)     // Catch:{ Throwable -> 0x0175 }
            if (r0 == 0) goto L_0x0174
            java.lang.String r0 = "Z2V0QmFzZVN0YXRpb25MYXRpdHVkZQ=="
            java.lang.String r0 = decodeString(r0)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object r0 = invoke(r14, r0, r3, r3)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ Throwable -> 0x0175 }
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r1 = "Z2V0QmFzZVN0YXRpb25Mb25naXR1ZGU="
            java.lang.String r1 = decodeString(r1)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Object r14 = invoke(r14, r1, r3, r3)     // Catch:{ Throwable -> 0x0175 }
            java.lang.Integer r14 = (java.lang.Integer) r14     // Catch:{ Throwable -> 0x0175 }
            int r14 = r14.intValue()     // Catch:{ Throwable -> 0x0175 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0175 }
            r1.<init>()     // Catch:{ Throwable -> 0x0175 }
            double r2 = (double) r0     // Catch:{ Throwable -> 0x0175 }
            r4 = 4669142098048450560(0x40cc200000000000, double:14400.0)
            double r2 = r2 / r4
            r1.append(r2)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r0 = r1.toString()     // Catch:{ Throwable -> 0x0175 }
            r15.setLatitude(r0)     // Catch:{ Throwable -> 0x0175 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0175 }
            r0.<init>()     // Catch:{ Throwable -> 0x0175 }
            double r1 = (double) r14     // Catch:{ Throwable -> 0x0175 }
            double r1 = r1 / r4
            r0.append(r1)     // Catch:{ Throwable -> 0x0175 }
            java.lang.String r14 = r0.toString()     // Catch:{ Throwable -> 0x0175 }
            r15.setLongitude(r14)     // Catch:{ Throwable -> 0x0175 }
        L_0x0174:
            return
        L_0x0175:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.LocationInfo.setLocationInfos(android.content.Context, com.alipay.security.mobile.module.deviceinfo.LocationInfo):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:61:0x00c5 A[Catch:{ Throwable -> 0x00d1 }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00c6 A[Catch:{ Throwable -> 0x00d1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void setCellInfos(android.content.Context r11, com.alipay.security.mobile.module.deviceinfo.LocationInfo r12) {
        /*
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r11.getSystemService(r0)     // Catch:{ Throwable -> 0x00bb }
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0     // Catch:{ Throwable -> 0x00bb }
            int r1 = r0.getPhoneType()     // Catch:{ Throwable -> 0x00bb }
            r2 = 2
            if (r1 != r2) goto L_0x0011
            r1 = 2
            goto L_0x0012
        L_0x0011:
            r1 = 1
        L_0x0012:
            java.lang.String r3 = ""
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            java.lang.String r6 = ""
            r7 = 0
            r8 = 3
            r9 = 0
            if (r1 != r2) goto L_0x0064
            java.lang.Class<android.telephony.TelephonyManager> r1 = android.telephony.TelephonyManager.class
            java.lang.String r2 = "Z2V0Q2VsbExvY2F0aW9u"
            java.lang.String r2 = decodeString(r2)     // Catch:{ Exception -> 0x0062 }
            java.lang.Object r1 = invoke(r0, r1, r2, r9, r9)     // Catch:{ Exception -> 0x0062 }
            if (r1 == 0) goto L_0x0062
            java.lang.String r2 = "getNetworkId"
            java.lang.Object r2 = invoke(r1, r2, r9, r9)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r0 = r0.getNetworkOperator()     // Catch:{ Exception -> 0x0061 }
            if (r0 == 0) goto L_0x004a
            java.lang.String r6 = ""
            boolean r6 = r0.equals(r6)     // Catch:{ Exception -> 0x0061 }
            if (r6 != 0) goto L_0x004a
            java.lang.String r0 = r0.substring(r7, r8)     // Catch:{ Exception -> 0x0061 }
            r3 = r0
        L_0x004a:
            java.lang.String r0 = "getSystemId"
            java.lang.Object r0 = invoke(r1, r0, r9, r9)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r4 = "getBaseStationId"
            java.lang.Object r1 = invoke(r1, r4, r9, r9)     // Catch:{ Exception -> 0x005f }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x005f }
            r5 = r1
        L_0x005f:
            r6 = r2
            goto L_0x00af
        L_0x0061:
            r6 = r2
        L_0x0062:
            r0 = r4
            goto L_0x00af
        L_0x0064:
            java.lang.Class<android.telephony.TelephonyManager> r1 = android.telephony.TelephonyManager.class
            java.lang.String r2 = "Z2V0Q2VsbExvY2F0aW9u"
            java.lang.String r2 = decodeString(r2)     // Catch:{ Exception -> 0x0062 }
            java.lang.Object r1 = invoke(r0, r1, r2, r9, r9)     // Catch:{ Exception -> 0x0062 }
            if (r1 == 0) goto L_0x0062
            java.lang.String r2 = r0.getNetworkOperator()     // Catch:{ Exception -> 0x0062 }
            if (r2 == 0) goto L_0x0096
            java.lang.String r10 = ""
            boolean r2 = r2.equals(r10)     // Catch:{ Exception -> 0x0062 }
            if (r2 != 0) goto L_0x0096
            java.lang.String r2 = r0.getNetworkOperator()     // Catch:{ Exception -> 0x0062 }
            java.lang.String r2 = r2.substring(r7, r8)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r0 = r0.getNetworkOperator()     // Catch:{ Exception -> 0x0094 }
            r3 = 5
            java.lang.String r0 = r0.substring(r8, r3)     // Catch:{ Exception -> 0x0094 }
            r4 = r0
            r3 = r2
            goto L_0x0096
        L_0x0094:
            r3 = r2
            goto L_0x0062
        L_0x0096:
            java.lang.String r0 = "getCid"
            java.lang.Object r0 = invoke(r1, r0, r9, r9)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Exception -> 0x0062 }
            java.lang.String r2 = "getLac"
            java.lang.Object r1 = invoke(r1, r2, r9, r9)     // Catch:{ Exception -> 0x00ad }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x00ad }
            r5 = r0
            r6 = r1
            goto L_0x0062
        L_0x00ad:
            r5 = r0
            goto L_0x0062
        L_0x00af:
            r12.setMcc(r3)     // Catch:{ Throwable -> 0x00bb }
            r12.setMnc(r0)     // Catch:{ Throwable -> 0x00bb }
            r12.setCellId(r5)     // Catch:{ Throwable -> 0x00bb }
            r12.setLac(r6)     // Catch:{ Throwable -> 0x00bb }
        L_0x00bb:
            java.lang.String r0 = "phone"
            java.lang.Object r11 = r11.getSystemService(r0)     // Catch:{ Throwable -> 0x00d1 }
            android.telephony.TelephonyManager r11 = (android.telephony.TelephonyManager) r11     // Catch:{ Throwable -> 0x00d1 }
            if (r11 != 0) goto L_0x00c6
            return
        L_0x00c6:
            com.alipay.security.mobile.module.deviceinfo.LocationInfo$1 r0 = new com.alipay.security.mobile.module.deviceinfo.LocationInfo$1     // Catch:{ Throwable -> 0x00d1 }
            r0.<init>(r11)     // Catch:{ Throwable -> 0x00d1 }
            r12 = 256(0x100, float:3.59E-43)
            r11.listen(r0, r12)     // Catch:{ Throwable -> 0x00d1 }
            return
        L_0x00d1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.deviceinfo.LocationInfo.setCellInfos(android.content.Context, com.alipay.security.mobile.module.deviceinfo.LocationInfo):void");
    }

    private static void setWifiInfos(Context context2, LocationInfo locationInfo) {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) context2.getSystemService("connectivity")).getNetworkInfo(1);
            StringBuilder sb = new StringBuilder();
            sb.append(networkInfo.isConnected());
            locationInfo.setIsWifiActive(sb.toString());
            WifiManager wifiManager = (WifiManager) context2.getSystemService("wifi");
            if (wifiManager.isWifiEnabled()) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                locationInfo.setBssid(connectionInfo.getBSSID());
                locationInfo.setSsid(Base64.encodeToString(connectionInfo.getSSID().getBytes(), 8));
                StringBuilder sb2 = new StringBuilder();
                sb2.append(connectionInfo.getRssi());
                locationInfo.setWifiStrength(sb2.toString());
            }
        } catch (Exception unused) {
        }
    }

    private static int getSecurity(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (wifiConfiguration.allowedKeyManagement.get(2) || wifiConfiguration.allowedKeyManagement.get(3)) {
            return 3;
        }
        if (wifiConfiguration.wepKeys[0] != null) {
            return 1;
        }
        return 0;
    }

    private static WifiConfiguration getWifiConfigurationFromSSID(Context context2, String str) {
        if (context2 == null || str == null) {
            return null;
        }
        WifiManager wifiManager = (WifiManager) context2.getSystemService("wifi");
        if (wifiManager == null) {
            return null;
        }
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks == null) {
            return null;
        }
        for (WifiConfiguration next : configuredNetworks) {
            if (next.SSID.equals(str)) {
                return next;
            }
        }
        return null;
    }

    public boolean getCellConnectivity() {
        return this.cellRssi != 0;
    }

    public double getCellRssi() {
        return (double) this.cellRssi;
    }

    public void setCellRssi(int i) {
        this.cellRssi = i;
    }

    public List<Map<String, String>> getWifiListNearby() {
        ArrayList arrayList = new ArrayList();
        if (this.context == null) {
            return arrayList;
        }
        WifiManager wifiManager = (WifiManager) this.context.getSystemService("wifi");
        if (wifiManager == null) {
            return arrayList;
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (scanResults == null) {
            return arrayList;
        }
        for (ScanResult next : scanResults) {
            HashMap hashMap = new HashMap();
            hashMap.put("wifiMac", next.BSSID == null ? "" : next.BSSID);
            hashMap.put("ssid", next.SSID);
            StringBuilder sb = new StringBuilder();
            sb.append(next.level);
            hashMap.put("rssi", sb.toString());
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    public boolean isGPSOpen() {
        if (this.context == null) {
            return false;
        }
        try {
            Object invoke = invoke(this.context, Context.class, decodeString("Z2V0U3lzdGVtU2VydmljZQ=="), new Class[]{String.class}, new Object[]{"location"});
            if (invoke != null) {
                if (((Integer) Class.forName("android.support.v4.content.ContextCompat").getMethod("checkSelfPermission", new Class[]{Context.class, String.class}).invoke(null, new Object[]{this.context, "android.permission.ACCESS_COARSE_LOCATION"})).intValue() == 0) {
                    Boolean bool = (Boolean) invoke(invoke, decodeString("aXNQcm92aWRlckVuYWJsZWQ="), new Class[]{String.class}, new Object[]{WidgetType.GPS});
                    if (bool == null || !bool.booleanValue()) {
                        return false;
                    }
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        return false;
    }

    public String getIpAddress(Context context2) {
        try {
            WifiManager wifiManager = (WifiManager) context2.getSystemService("wifi");
            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
                StringBuilder sb = new StringBuilder();
                sb.append(ipAddress & 255);
                sb.append(".");
                sb.append((ipAddress >> 8) & 255);
                sb.append(".");
                sb.append((ipAddress >> 16) & 255);
                sb.append(".");
                sb.append((ipAddress >> 24) & 255);
                return sb.toString();
            }
        } catch (Exception unused) {
        }
        return "";
    }

    public boolean isWifiEncrypted() {
        if (this.context == null) {
            return false;
        }
        WifiManager wifiManager = (WifiManager) this.context.getSystemService("wifi");
        if (wifiManager == null) {
            return false;
        }
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (connectionInfo == null) {
            return false;
        }
        WifiConfiguration wifiConfigurationFromSSID = getWifiConfigurationFromSSID(this.context, connectionInfo.getSSID());
        if (wifiConfigurationFromSSID == null || getSecurity(wifiConfigurationFromSSID) == 0) {
            return false;
        }
        return true;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String str) {
        this.latitude = str;
    }

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String str) {
        this.bssid = str;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public String getIsWifiActive() {
        return this.isWifiActive;
    }

    public void setIsWifiActive(String str) {
        this.isWifiActive = str;
    }

    public String getWifiStrength() {
        return this.wifiStrength;
    }

    public void setWifiStrength(String str) {
        this.wifiStrength = str;
    }

    public String getMcc() {
        return this.mcc;
    }

    public void setMcc(String str) {
        this.mcc = str;
    }

    public String getMnc() {
        return this.mnc;
    }

    public void setMnc(String str) {
        this.mnc = str;
    }

    public String getCellId() {
        return this.cellId;
    }

    public void setCellId(String str) {
        this.cellId = str;
    }

    public String getLac() {
        return this.lac;
    }

    public void setLac(String str) {
        this.lac = str;
    }

    private static String decodeString(String str) throws UnsupportedEncodingException {
        return new String(Base64.decode(str, 2), "UTF-8");
    }

    private static Object invoke(Object obj, String str, Class<?>[] clsArr, Object[] objArr) {
        return invoke(obj, obj.getClass(), str, clsArr, objArr);
    }

    private static Object invoke(Object obj, Class<?> cls, String str, Class<?>[] clsArr, Object[] objArr) {
        try {
            Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
            if (declaredMethod == null) {
                declaredMethod = cls.getMethod(str, clsArr);
            }
            if (declaredMethod != null) {
                return declaredMethod.invoke(obj, objArr);
            }
        } catch (Throwable th) {
            LoggerFactory.f().c(LocationInfo.class.getName(), th);
        }
        return null;
    }
}
