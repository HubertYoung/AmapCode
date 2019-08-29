package defpackage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.GnssStatus;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.jni.ae.pos.DriveModeObserver;
import com.autonavi.jni.ae.pos.LocGPSHistory;
import com.autonavi.jni.ae.pos.LocGSVData;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.ae.pos.LocListener;
import com.autonavi.jni.ae.pos.LocManager;
import com.autonavi.jni.ae.pos.LocNGMListener;
import com.autonavi.jni.ae.pos.LocNemaInfo;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/* renamed from: anf reason: default package */
/* compiled from: LocWrapper */
public class anf {
    public static boolean a = true;
    /* access modifiers changed from: private */
    public static final String b = "anf";
    private static volatile boolean c = false;
    /* access modifiers changed from: private */
    public static CopyOnWriteArraySet<LocListener> d = new CopyOnWriteArraySet<>();
    private static boolean e = true;
    private static MapSharePreference f = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private static boolean g = true;

    public static long a() {
        if (c) {
            return -1;
        }
        long init = LocManager.init();
        c = true;
        e = true;
        boolean z = bno.a;
        LocManager.setLogSwitch(z, !z, bno.a ? 1 : 0);
        if (c) {
            String str = Build.BRAND;
            String str2 = Build.MODEL;
            String str3 = VERSION.RELEASE;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(":");
            sb.append(str2);
            sb.append(":");
            sb.append(str3);
            String sb2 = sb.toString();
            LocManager.setUserDevInfo(0, TextUtils.isEmpty(NetworkParam.getAdiu()) ? NetworkParam.getDiu() : NetworkParam.getAdiu());
            LocManager.setUserDevInfo(1, sb2);
            SensorManager sensorManager = (SensorManager) AMapAppGlobal.getApplication().getSystemService("sensor");
            if (sensorManager != null) {
                List<Sensor> sensorList = sensorManager.getSensorList(-1);
                if (sensorList != null && !sensorList.isEmpty()) {
                    for (Sensor next : sensorList) {
                        int type = next.getType();
                        if (type == 1 || type == 2 || type == 3 || type == 4) {
                            StringBuilder sb3 = new StringBuilder("");
                            sb3.append(next.getName());
                            sb3.append(":");
                            sb3.append(next.getVendor());
                            sb3.append(":");
                            sb3.append(next.getVersion());
                            LocManager.setUserDevInfo(2, sb3.toString().replaceAll(Token.SEPARATOR, "_"));
                        }
                    }
                }
            }
        }
        if (c) {
            LocManager.setAMapStatu(0);
        }
        a(0, -1);
        final MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        mapSharePreference.sharedPrefs().registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
            public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                if ("LOC_LOG_SWITCH".equals(str)) {
                    mapSharePreference.sharedPrefs().unregisterOnSharedPreferenceChangeListener(this);
                    if (bno.a) {
                        AMapLog.d(anf.b, "pos engine switch is on");
                    }
                    anf.a(sharedPreferences);
                    mapSharePreference.putBooleanValue("LOC_LOG_SWITCH", false);
                }
            }
        });
        return init;
    }

    public static void a(LocListener locListener) {
        d.remove(locListener);
    }

    public static void a(LocListener locListener, int i) {
        if (c) {
            d.add(locListener);
            if (e) {
                LocManager.addLocListener(new LocListener() {
                    public final void updateNaviInfo(LocInfo locInfo) {
                        Iterator it = anf.d.iterator();
                        while (it.hasNext()) {
                            ((LocListener) it.next()).updateNaviInfo(locInfo);
                        }
                    }
                }, i);
                e = false;
            }
        }
    }

    public static void b() {
        if (c) {
            LocManager.uninit();
        }
        c = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x00b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void c() {
        /*
            boolean r0 = c
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            r1 = 0
            android.location.GpsStatus r0 = r0.getGpsStatus(r1)
            if (r0 == 0) goto L_0x00e3
            com.autonavi.jni.ae.pos.LocGSVData r1 = new com.autonavi.jni.ae.pos.LocGSVData
            r1.<init>()
            r2 = 0
            r1.nType = r2
            int r3 = r0.getMaxSatellites()
            java.lang.Iterable r0 = r0.getSatellites()
            java.util.Iterator r0 = r0.iterator()
        L_0x0024:
            boolean r4 = r0.hasNext()
            r5 = 16
            if (r4 == 0) goto L_0x00ae
            if (r2 > r3) goto L_0x00ae
            java.lang.Object r4 = r0.next()
            android.location.GpsSatellite r4 = (android.location.GpsSatellite) r4
            boolean r6 = r4.usedInFix()
            if (r6 == 0) goto L_0x0024
            if (r2 >= r5) goto L_0x00aa
            int[] r5 = r1.nRPN
            int r6 = r4.getPrn()
            r5[r2] = r6
            int[] r5 = r1.nElevation
            float r6 = r4.getElevation()
            int r6 = (int) r6
            r5[r2] = r6
            int[] r5 = r1.nAzimuth
            float r6 = r4.getAzimuth()
            int r6 = (int) r6
            r5[r2] = r6
            int[] r5 = r1.nSNR
            float r4 = r4.getSnr()
            int r4 = (int) r4
            r5[r2] = r4
            boolean r4 = defpackage.bno.a
            if (r4 == 0) goto L_0x00aa
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "setGSVData "
            r4.<init>(r5)
            r4.append(r2)
            java.lang.String r5 = "| "
            r4.append(r5)
            int[] r5 = r1.nRPN
            r5 = r5[r2]
            r4.append(r5)
            java.lang.String r5 = "|"
            r4.append(r5)
            int[] r5 = r1.nElevation
            r5 = r5[r2]
            r4.append(r5)
            java.lang.String r5 = "|"
            r4.append(r5)
            int[] r5 = r1.nAzimuth
            r5 = r5[r2]
            r4.append(r5)
            java.lang.String r5 = "|"
            r4.append(r5)
            int[] r5 = r1.nSNR
            r5 = r5[r2]
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = b
            com.amap.bundle.logs.AMapLog.d(r5, r4)
        L_0x00aa:
            int r2 = r2 + 1
            goto L_0x0024
        L_0x00ae:
            if (r2 >= r5) goto L_0x00b1
            r5 = r2
        L_0x00b1:
            r1.nNum = r5
            int r0 = r1.nNum
            if (r0 <= 0) goto L_0x00e3
            long r3 = android.os.SystemClock.elapsedRealtime()
            r1.ticktime = r3
            java.lang.String r0 = b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "setGSVData: "
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r2 = "|"
            r3.append(r2)
            int r2 = r1.nNum
            r3.append(r2)
            java.lang.String r2 = "\n==========================="
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            com.amap.bundle.logs.AMapLog.d(r0, r2)
            com.autonavi.jni.ae.pos.LocManager.setGSVData(r1)
        L_0x00e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.anf.c():void");
    }

    public static void a(GnssStatus gnssStatus) {
        int i;
        if (c && VERSION.SDK_INT >= 24 && gnssStatus != null) {
            LocGSVData locGSVData = new LocGSVData();
            int i2 = 0;
            locGSVData.nType = 0;
            int satelliteCount = gnssStatus.getSatelliteCount();
            int i3 = 0;
            while (true) {
                i = 16;
                if (i2 >= satelliteCount) {
                    break;
                }
                if (gnssStatus.usedInFix(i2)) {
                    if (i3 < 16) {
                        locGSVData.nRPN[i3] = gnssStatus.getSvid(i2);
                        locGSVData.nElevation[i3] = (int) gnssStatus.getElevationDegrees(i2);
                        locGSVData.nAzimuth[i3] = (int) gnssStatus.getAzimuthDegrees(i2);
                        locGSVData.nSNR[i3] = (int) gnssStatus.getCn0DbHz(i2);
                        if (bno.a) {
                            StringBuilder sb = new StringBuilder("setGSVData ");
                            sb.append(i3);
                            sb.append("| ");
                            sb.append(locGSVData.nRPN[i3]);
                            sb.append(MergeUtil.SEPARATOR_KV);
                            sb.append(locGSVData.nElevation[i3]);
                            sb.append(MergeUtil.SEPARATOR_KV);
                            sb.append(locGSVData.nAzimuth[i3]);
                            sb.append(MergeUtil.SEPARATOR_KV);
                            sb.append(locGSVData.nSNR[i3]);
                            AMapLog.d(b, sb.toString());
                        }
                    }
                    i3++;
                }
                i2++;
            }
            if (i3 < 16) {
                i = i3;
            }
            locGSVData.nNum = i;
            if (locGSVData.nNum > 0) {
                locGSVData.ticktime = SystemClock.elapsedRealtime();
                String str = b;
                StringBuilder sb2 = new StringBuilder("setGSVData: ");
                sb2.append(i3);
                sb2.append(MergeUtil.SEPARATOR_KV);
                sb2.append(locGSVData.nNum);
                sb2.append("\n===========================");
                AMapLog.d(str, sb2.toString());
                LocManager.setGSVData(locGSVData);
            }
        }
    }

    public static void a(long j, String str) {
        if (c) {
            LocNemaInfo locNemaInfo = new LocNemaInfo();
            locNemaInfo.localTickTime = 0;
            locNemaInfo.pNemaInfo = str;
            locNemaInfo.tickTime = j;
            LocManager.setLocNemaInfo(locNemaInfo);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x005b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0146  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0165  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0189  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01a3  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01df  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01ed  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0272  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.location.Location r17, boolean r18) {
        /*
            boolean r0 = c
            if (r0 == 0) goto L_0x02b4
            if (r17 == 0) goto L_0x02b4
            java.lang.String r1 = r17.getProvider()
            if (r1 != 0) goto L_0x000e
            goto L_0x02b4
        L_0x000e:
            com.autonavi.jni.ae.pos.GpsInfo r1 = new com.autonavi.jni.ae.pos.GpsInfo
            r1.<init>()
            r2 = -999(0xfffffffffffffc19, float:NaN)
            r1.subSourType = r2
            java.lang.String r2 = r17.getProvider()
            int r3 = r2.hashCode()
            r4 = -1184229805(0xffffffffb96a1653, float:-2.2324295E-4)
            r5 = 2
            r6 = -1
            r7 = 1
            r8 = 0
            if (r3 == r4) goto L_0x0047
            r4 = 102570(0x190aa, float:1.43731E-40)
            if (r3 == r4) goto L_0x003d
            r4 = 1843485230(0x6de15a2e, float:8.7178935E27)
            if (r3 == r4) goto L_0x0033
            goto L_0x0051
        L_0x0033:
            java.lang.String r3 = "network"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0051
            r2 = 1
            goto L_0x0052
        L_0x003d:
            java.lang.String r3 = "gps"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0051
            r2 = 0
            goto L_0x0052
        L_0x0047:
            java.lang.String r3 = "indoor"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0051
            r2 = 2
            goto L_0x0052
        L_0x0051:
            r2 = -1
        L_0x0052:
            r3 = 0
            r9 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            switch(r2) {
                case 0: goto L_0x00c3;
                case 1: goto L_0x00aa;
                case 2: goto L_0x005c;
                default: goto L_0x005b;
            }
        L_0x005b:
            return
        L_0x005c:
            r1.sourtype = r7
            android.os.Bundle r2 = r17.getExtras()
            if (r2 == 0) goto L_0x00a6
            java.lang.String r4 = "idrGcjLon"
            double r11 = r2.getDouble(r4)
            java.lang.String r4 = "idrGcjLat"
            double r13 = r2.getDouble(r4)
            r15 = 0
            int r4 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r4 <= 0) goto L_0x00cf
            int r4 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r4 <= 0) goto L_0x00cf
            com.autonavi.jni.ae.pos.LocDoorIn r4 = new com.autonavi.jni.ae.pos.LocDoorIn
            r4.<init>()
            double r11 = r11 * r9
            int r11 = (int) r11
            r4.lon = r11
            double r13 = r13 * r9
            int r11 = (int) r13
            r4.lat = r11
            java.lang.String r11 = "poiid"
            java.lang.String r12 = ""
            java.lang.String r11 = r2.getString(r11, r12)
            r4.poiId = r11
            java.lang.String r11 = "floor"
            java.lang.String r12 = ""
            java.lang.String r2 = r2.getString(r11, r12)
            r4.floor = r2
            r4.zLevel = r8
            long r11 = android.os.SystemClock.elapsedRealtime()
            r4.ticktime = r11
            goto L_0x00a7
        L_0x00a6:
            r4 = r3
        L_0x00a7:
            r1.subSourType = r6
            goto L_0x00d0
        L_0x00aa:
            r1.sourtype = r7
            android.os.Bundle r2 = r17.getExtras()     // Catch:{ Throwable -> 0x00cf }
            java.lang.String r4 = "retype"
            java.lang.String r6 = "-999"
            java.lang.String r2 = r2.getString(r4, r6)     // Catch:{ Throwable -> 0x00cf }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x00cf }
            int r2 = r2.intValue()     // Catch:{ Throwable -> 0x00cf }
            r1.subSourType = r2     // Catch:{ Throwable -> 0x00cf }
            goto L_0x00cf
        L_0x00c3:
            if (r18 == 0) goto L_0x00cb
            r1.sourtype = r7
            r2 = -5
            r1.subSourType = r2
            goto L_0x00cf
        L_0x00cb:
            r1.sourtype = r8
            r1.subSourType = r8
        L_0x00cf:
            r4 = r3
        L_0x00d0:
            com.autonavi.jni.ae.pos.LocManager.setDoorIn(r4)
            java.util.Calendar r2 = java.util.Calendar.getInstance()
            long r11 = r17.getTime()
            r2.setTimeInMillis(r11)
            int r4 = r2.get(r7)
            r1.year = r4
            int r4 = r2.get(r5)
            int r4 = r4 + r7
            r1.month = r4
            r4 = 5
            int r4 = r2.get(r4)
            r1.day = r4
            r4 = 11
            int r4 = r2.get(r4)
            r1.hour = r4
            r4 = 12
            int r4 = r2.get(r4)
            r1.minute = r4
            r4 = 13
            int r2 = r2.get(r4)
            r1.second = r2
            double r4 = r17.getLongitude()
            double r4 = r4 * r9
            int r2 = (int) r4
            r1.lon = r2
            double r4 = r17.getLatitude()
            double r4 = r4 * r9
            int r2 = (int) r4
            r1.lat = r2
            float r2 = r17.getAccuracy()
            double r4 = (double) r2
            r1.accuracy = r4
            double r4 = r17.getAltitude()
            r1.alt = r4
            float r2 = r17.getBearing()
            double r4 = (double) r2
            r1.angle = r4
            float r2 = r17.getSpeed()
            double r4 = (double) r2
            r11 = 4615288898129284301(0x400ccccccccccccd, double:3.6)
            double r4 = r4 * r11
            r1.speed = r4
            double r4 = r1.speed
            r11 = 4629137466983448576(0x403e000000000000, double:30.0)
            int r2 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r2 < 0) goto L_0x014d
            double r4 = r1.speed
            r11 = 4613937818241073152(0x4008000000000000, double:3.0)
            double r4 = r4 + r11
            r1.speed = r4
        L_0x014d:
            long r4 = r17.getTime()
            r1.ticktime = r4
            r2 = 78
            r1.ns = r2
            r4 = 69
            r1.ew = r4
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            android.location.GpsStatus r3 = r4.getGpsStatus(r3)
            if (r3 == 0) goto L_0x0189
            int r4 = r3.getMaxSatellites()
            java.lang.Iterable r3 = r3.getSatellites()
            java.util.Iterator r3 = r3.iterator()
            r5 = 0
        L_0x0172:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x018a
            if (r5 > r4) goto L_0x018a
            java.lang.Object r6 = r3.next()
            android.location.GpsSatellite r6 = (android.location.GpsSatellite) r6
            boolean r6 = r6.usedInFix()
            if (r6 == 0) goto L_0x0172
            int r5 = r5 + 1
            goto L_0x0172
        L_0x0189:
            r5 = 0
        L_0x018a:
            r1.satnum = r5
            r3 = 4606281698874543309(0x3feccccccccccccd, double:0.9)
            r1.hdop = r3
            r1.vdop = r3
            r1.pdop = r3
            r3 = 65
            r1.status = r3
            r1.mode = r2
            android.os.Bundle r2 = r17.getExtras()
            if (r2 == 0) goto L_0x01cc
            java.lang.String r3 = "hasgcj"
            boolean r3 = r2.getBoolean(r3, r8)     // Catch:{ Exception -> 0x01cc }
            if (r3 == 0) goto L_0x01cd
            java.lang.String r4 = "gcjlng"
            r5 = 1
            double r11 = r2.getDouble(r4, r5)     // Catch:{ Exception -> 0x01cd }
            java.lang.String r4 = "gcjlat"
            double r13 = r2.getDouble(r4, r5)     // Catch:{ Exception -> 0x01cd }
            int r2 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r2 == 0) goto L_0x01cc
            int r2 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r2 == 0) goto L_0x01cc
            double r11 = r11 * r9
            int r2 = (int) r11     // Catch:{ Exception -> 0x01cd }
            r1.lon = r2     // Catch:{ Exception -> 0x01cd }
            double r13 = r13 * r9
            int r2 = (int) r13     // Catch:{ Exception -> 0x01cd }
            r1.lat = r2     // Catch:{ Exception -> 0x01cd }
            goto L_0x01cd
        L_0x01cc:
            r3 = 0
        L_0x01cd:
            li r2 = defpackage.li.a()
            double r4 = r17.getLongitude()
            double r9 = r17.getLatitude()
            lj r0 = r2.b(r4, r9)
            if (r3 != 0) goto L_0x01ea
            if (r0 == 0) goto L_0x01e9
            boolean r0 = r0.a()
            if (r0 != 0) goto L_0x01e9
            r3 = 1
            goto L_0x01ea
        L_0x01e9:
            r3 = 0
        L_0x01ea:
            if (r3 == 0) goto L_0x01ed
            goto L_0x01ee
        L_0x01ed:
            r7 = 0
        L_0x01ee:
            byte r0 = (byte) r7
            r1.encrypted = r0
            ku r0 = defpackage.ku.a()
            java.lang.String r2 = "NaviMonitor"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "["
            r4.<init>(r5)
            java.lang.String r5 = b
            r4.append(r5)
            java.lang.String r5 = "] setGPSInfo:hasGcj "
            r4.append(r5)
            r4.append(r3)
            java.lang.String r3 = ", lon "
            r4.append(r3)
            int r3 = r1.lon
            r4.append(r3)
            java.lang.String r3 = ", lat "
            r4.append(r3)
            int r3 = r1.lat
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            r0.c(r2, r3)
            java.lang.String r0 = "HwMMgps"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = " setGPSInfo: time "
            r2.<init>(r3)
            long r3 = r1.ticktime
            r2.append(r3)
            java.lang.String r3 = " ,type "
            r2.append(r3)
            int r3 = r1.sourtype
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            defpackage.ewt.c(r0, r2)
            java.lang.String r0 = "speed Q: "
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "setGPSInfo gpsInfo.ticktime "
            r2.<init>(r3)
            long r3 = r1.ticktime
            r2.append(r3)
            java.lang.String r3 = "gpsInfo.speed"
            r2.append(r3)
            double r3 = r1.speed
            r2.append(r3)
            java.lang.String r3 = ", type: "
            r2.append(r3)
            int r3 = r1.sourtype
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            defpackage.anh.a(r0, r2)
            boolean r0 = defpackage.epk.a
            if (r0 == 0) goto L_0x02b0
            java.lang.String r0 = b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "传入引擎位置："
            r2.<init>(r3)
            long r3 = r1.ticktime
            r2.append(r3)
            java.lang.String r3 = ","
            r2.append(r3)
            int r3 = r1.sourtype
            r2.append(r3)
            java.lang.String r3 = ","
            r2.append(r3)
            int r3 = r1.subSourType
            r2.append(r3)
            java.lang.String r3 = ","
            r2.append(r3)
            int r3 = r1.lat
            r2.append(r3)
            java.lang.String r3 = ","
            r2.append(r3)
            int r3 = r1.lon
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            defpackage.epk.a(r0, r2)
        L_0x02b0:
            com.autonavi.jni.ae.pos.LocManager.setGpsInfo(r1)
            return
        L_0x02b4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.anf.a(android.location.Location, boolean):void");
    }

    public static Location a(com.autonavi.ae.location.LocInfo locInfo) {
        String str;
        if (locInfo == null) {
            return null;
        }
        switch (locInfo.sourType) {
            case 0:
                str = WidgetType.GPS;
                break;
            case 1:
                if (locInfo.stDoorInPos != null && locInfo.stDoorInPos.lat != 0 && locInfo.stDoorInPos.lon != 0) {
                    str = IndoorLocationProvider.NAME;
                    break;
                } else {
                    str = "network";
                    break;
                }
                break;
            case 2:
                str = IndoorLocationProvider.NAME;
                break;
            default:
                return null;
        }
        Location location = new Location(str);
        if (locInfo.stPos != null) {
            if (locInfo.stPos.lon == 0 || locInfo.stPos.lat == 0) {
                return null;
            }
            location.setLongitude((((double) locInfo.stPos.lon) / 1000000.0d) / 3.6d);
            location.setLatitude((((double) locInfo.stPos.lat) / 1000000.0d) / 3.6d);
        }
        location.setSpeed((float) locInfo.speed);
        location.setAltitude(locInfo.alt);
        location.setAccuracy((float) locInfo.posAcc);
        location.setBearing((float) locInfo.course);
        if (IndoorLocationProvider.NAME.equalsIgnoreCase(str) && locInfo.stDoorInPos != null) {
            if (locInfo.stDoorInPos.lat == 0 || locInfo.stDoorInPos.lon == 0 || TextUtils.isEmpty(locInfo.strPoiid) || TextUtils.isEmpty(locInfo.strFloor)) {
                return null;
            }
            Bundle bundle = new Bundle();
            bundle.putDouble(LocationInstrument.INDOOR_LOCATION_LAT, ((double) locInfo.stDoorInPos.lat) / 1000000.0d);
            bundle.putDouble(LocationInstrument.INDOOR_LOCATION_LON, ((double) locInfo.stDoorInPos.lon) / 1000000.0d);
            bundle.putString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, locInfo.strPoiid);
            bundle.putString("floor", locInfo.strFloor);
            location.setExtras(bundle);
        }
        Bundle extras = location.getExtras();
        if (extras == null) {
            extras = new Bundle();
        }
        long currentTimeMillis = System.currentTimeMillis();
        extras.putLong(LocationInstrument.LOCATION_EXTRAS_KEY_SYSTIME, currentTimeMillis);
        extras.putDouble("render_accuracy", locInfo.showPosAcc);
        extras.putSerializable(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_POS, locInfo.MatchRoadPos);
        extras.putDouble(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_COURSE, locInfo.MatchRoadCourse);
        extras.putInt(LocationInstrument.LOCATION_EXTRAS_KEY_COURSE_TYPE, locInfo.CourseType);
        extras.putDouble(LocationInstrument.LOCATION_EXTRAS_KEY_GPS_COURSE, locInfo.GpsCourse);
        extras.putDouble(LocationInstrument.LOCATION_EXTRAS_KEY_COMPASS_COURSE, locInfo.CompassCourse);
        extras.putDouble(LocationInstrument.LOCATION_EXTRAS_KEY_COURSE_ACC, locInfo.courseAcc);
        extras.putFloat(LocationInstrument.LOCATION_EXTRAS_KEY_ERROR_DIST, locInfo.errorDist);
        extras.putInt(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_POS_TYPE, locInfo.matchPosType);
        extras.putFloat(LocationInstrument.LOCATION_EXTRAS_KEY_GPS_COURE_ACC, locInfo.gpsCoureAcc);
        extras.putFloat(LocationInstrument.LOCATION_EXTRAS_KEY_FITTING_COURSE, locInfo.fittingCourse);
        extras.putFloat(LocationInstrument.LOCATION_EXTRAS_KEY_FITTING_COURSE_ACC, locInfo.fittingCourseAcc);
        extras.putFloat(LocationInstrument.LOCATION_EXTRAS_KEY_ROAD_COURSE, locInfo.roadCourse);
        location.setExtras(extras);
        if (locInfo.ticktime == 0) {
            location.setTime(currentTimeMillis);
        } else {
            Calendar instance = Calendar.getInstance();
            instance.set(1, locInfo.year);
            instance.set(2, locInfo.mouth - 1);
            instance.set(5, locInfo.day);
            instance.set(11, locInfo.hour);
            instance.set(12, locInfo.minute);
            instance.set(13, locInfo.second);
            location.setTime(instance.getTimeInMillis());
        }
        return location;
    }

    public static String d() {
        return LocManager.getVersion();
    }

    public static void a(double d2, long j) {
        if (c) {
            LocManager.setPressure(d2, j);
        }
    }

    public static void b(double d2, long j) {
        if (c) {
            LocManager.setCompass(d2, j);
        }
    }

    public static void e() {
        if (c) {
            LocManager.requestCallBackPos(1);
        }
    }

    public static void a(LocNGMListener locNGMListener) {
        if (c) {
            LocManager.addNGMListener(locNGMListener);
        }
    }

    public static void a(int i, int i2) {
        if (c) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(", ");
            sb.append(i2);
            AMapLog.d("setScene : ", sb.toString());
            LocManager.setScene(i, i2);
        }
    }

    public static void a(DriveModeObserver driveModeObserver) {
        if (c) {
            LocManager.addDriveModeObserver(driveModeObserver);
        }
    }

    public static void b(DriveModeObserver driveModeObserver) {
        if (c) {
            LocManager.removeDriveModeObserver(driveModeObserver);
        }
    }

    public static void a(LocGPSHistory locGPSHistory) {
        if (c) {
            LocManager.getGPSHistory(locGPSHistory);
        }
    }

    static /* synthetic */ void a(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            boolean z = sharedPreferences.getBoolean("log_switch", false);
            g = !z;
            LocManager.setLogSwitch(z, sharedPreferences.getBoolean("log_encrypt", true), sharedPreferences.getInt("log_level", 0));
        }
    }
}
