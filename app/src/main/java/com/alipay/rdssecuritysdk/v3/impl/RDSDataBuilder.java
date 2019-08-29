package com.alipay.rdssecuritysdk.v3.impl;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Native;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Dev;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Dev.Sensor;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Dev.Sensor.Data;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Env;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Loc;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Usr;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Usr.AD;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Usr.Action;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Sdk.Usr.Ua;
import com.alipay.rdssecuritysdk.v3.RdsRequestMessage.Taobao;
import com.alipay.rdssecuritysdk.v3.sensor.SensorCollectors;
import com.alipay.rdssecuritysdk.v3.sensor.SensorCollectors.SensorType;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.alipay.security.mobile.module.crypto.DigestUtil;
import com.alipay.security.mobile.module.deviceinfo.AppInfo;
import com.alipay.security.mobile.module.deviceinfo.DeviceInfo;
import com.alipay.security.mobile.module.deviceinfo.EnvironmentInfo;
import com.alipay.security.mobile.module.deviceinfo.LocationInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RDSDataBuilder {
    private TraceLogger a = LoggerFactory.f();
    private SensorCollectors b = null;
    private Usr c = new Usr();
    private Loc d = new Loc();
    private Dev e = new Dev();
    private Env f = new Env();
    private Taobao g = new Taobao();
    private String h = "";

    private RDSDataBuilder() {
    }

    public static RDSDataBuilder a() {
        return new RDSDataBuilder();
    }

    public final RdsRequestMessage b() {
        RdsRequestMessage rdsRequestMessage = new RdsRequestMessage();
        rdsRequestMessage._native = new Native();
        rdsRequestMessage._native.env = new Native.Env();
        rdsRequestMessage.sdk = new Sdk();
        rdsRequestMessage.sdk.dev = this.e;
        rdsRequestMessage.sdk.env = this.f;
        rdsRequestMessage.sdk.loc = this.d;
        rdsRequestMessage.taobao = this.g;
        rdsRequestMessage.sdk.usr = new Usr();
        rdsRequestMessage.sdk.usr.appkey = this.c.appver;
        rdsRequestMessage.sdk.usr.appname = this.c.appname;
        rdsRequestMessage.sdk.usr.appver = this.c.appver;
        rdsRequestMessage.sdk.usr.pubkey = this.c.pubkey;
        rdsRequestMessage.sdk.usr.sdkname = this.c.sdkname;
        rdsRequestMessage.sdk.usr.sdkver = this.c.sdkver;
        rdsRequestMessage.sdk.usr.user = this.c.user;
        Sensor sensor = rdsRequestMessage.sdk.dev.sensor;
        if (!(this.b == null || sensor == null || sensor.data == null)) {
            Map<String, List<String>> c2 = this.b.c();
            if (c2.containsKey(SensorType.ACCELEROMETER.getSensorName())) {
                sensor.data.Accelerometer = c2.get(SensorType.ACCELEROMETER.getSensorName());
            }
            if (c2.containsKey(SensorType.GRAVITY.getSensorName())) {
                sensor.data.Gravity = c2.get(SensorType.GRAVITY.getSensorName());
            }
            if (c2.containsKey(SensorType.GYROSCOPE.getSensorName())) {
                sensor.data.Gyroscope = c2.get(SensorType.GYROSCOPE.getSensorName());
            }
            if (c2.containsKey(SensorType.MAGNETIC.getSensorName())) {
                sensor.data.Magnetometer = c2.get(SensorType.MAGNETIC.getSensorName());
            }
            this.b.b();
        }
        Ua ua = new Ua();
        ua.action = new ArrayList();
        if (this.c.ua != null) {
            ua.num = this.c.ua.num;
            ua.t = this.c.ua.t;
            if (this.c.ua.action != null) {
                for (Action next : this.c.ua.action) {
                    Action action = new Action();
                    action.ad = new ArrayList();
                    action.cn = next.cn;
                    action.et = next.et;
                    action.num = next.num;
                    action.pn = next.pn;
                    action.seq = next.seq;
                    action.type = next.type;
                    action.t = next.t;
                    if (next.ad != null) {
                        for (AD next2 : next.ad) {
                            AD ad = new AD();
                            ad.pr = next2.pr;
                            ad.t = next2.t;
                            ad.r = next2.r;
                            ad.f = next2.f;
                            ad.key = next2.key;
                            ad.x = next2.x;
                            ad.y = next2.y;
                            action.ad.add(ad);
                        }
                    }
                    ua.action.add(action);
                }
            }
            rdsRequestMessage.sdk.usr.ua = ua;
        }
        rdsRequestMessage.extra1 = this.h;
        return rdsRequestMessage;
    }

    public final RDSDataBuilder a(Context context, String str, String str2, String str3, String str4, String str5) {
        try {
            this.c.appver = str;
            this.c.user = str2;
            this.c.appname = str3;
            this.c.sdkname = CONST.SDK_NAME;
            this.c.sdkver = CONST.SDK_VER;
            byte[] publicKey = AppInfo.getInstance().getPublicKey(context, str5);
            this.c.pubkey = DigestUtil.digestWithSha1(publicKey);
            this.c.appkey = str4;
        } catch (Throwable th) {
            TraceLogger traceLogger = this.a;
            StringBuilder sb = new StringBuilder("RDSDataBuilder::buildUsrInfo error happened, ");
            sb.append(CommonUtils.getStackString(th));
            traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        }
        return this;
    }

    public final RDSDataBuilder a(Ua ua) {
        this.c.ua = ua;
        return this;
    }

    public final RDSDataBuilder a(String str) {
        this.c.user = str;
        return this;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0085 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.alipay.rdssecuritysdk.v3.impl.RDSDataBuilder a(android.content.Context r6) {
        /*
            r5 = this;
            com.alipay.apmobilesecuritysdk.commonbiz.ApplistUtil$AppListScanResult r0 = com.alipay.apmobilesecuritysdk.commonbiz.ApplistUtil.b(r6)     // Catch:{ Throwable -> 0x008b }
            com.alipay.apmobilesecuritysdk.commonbiz.InjectScanUtil$InjectScanResult r1 = com.alipay.apmobilesecuritysdk.commonbiz.InjectScanUtil.a(r6)     // Catch:{ Throwable -> 0x008b }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x008b }
            r2.<init>()     // Catch:{ Throwable -> 0x008b }
            java.lang.String r3 = "_os"
            java.lang.String r4 = "android"
            r2.put(r3, r4)     // Catch:{ Throwable -> 0x008b }
            java.lang.String r3 = "_root"
            com.alipay.security.mobile.module.deviceinfo.EnvironmentInfo r4 = com.alipay.security.mobile.module.deviceinfo.EnvironmentInfo.getInstance()     // Catch:{ Throwable -> 0x008b }
            boolean r4 = r4.isRooted()     // Catch:{ Throwable -> 0x008b }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Throwable -> 0x008b }
            r2.put(r3, r4)     // Catch:{ Throwable -> 0x008b }
            java.lang.String r3 = "_appListVer"
            if (r0 != 0) goto L_0x002c
            java.lang.String r4 = ""
            goto L_0x002e
        L_0x002c:
            java.lang.String r4 = r0.a     // Catch:{ Throwable -> 0x008b }
        L_0x002e:
            r2.put(r3, r4)     // Catch:{ Throwable -> 0x008b }
            java.lang.String r3 = "_appList"
            if (r0 != 0) goto L_0x0038
            java.lang.String r0 = ""
            goto L_0x003a
        L_0x0038:
            java.lang.String r0 = r0.c     // Catch:{ Throwable -> 0x008b }
        L_0x003a:
            r2.put(r3, r0)     // Catch:{ Throwable -> 0x008b }
            java.lang.String r0 = "_injectListVer"
            if (r1 != 0) goto L_0x0044
            java.lang.String r3 = ""
            goto L_0x0046
        L_0x0044:
            java.lang.String r3 = r1.a     // Catch:{ Throwable -> 0x008b }
        L_0x0046:
            r2.put(r0, r3)     // Catch:{ Throwable -> 0x008b }
            java.lang.String r0 = "_injectList"
            if (r1 != 0) goto L_0x0050
            java.lang.String r1 = ""
            goto L_0x0052
        L_0x0050:
            java.lang.String r1 = r1.b     // Catch:{ Throwable -> 0x008b }
        L_0x0052:
            r2.put(r0, r1)     // Catch:{ Throwable -> 0x008b }
            java.lang.String r0 = "_mockLoc"
            com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getInstance()     // Catch:{ Throwable -> 0x008b }
            boolean r1 = com.alipay.security.mobile.module.deviceinfo.DeviceInfo.isAllowMockLocation(r6)     // Catch:{ Throwable -> 0x008b }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ Throwable -> 0x008b }
            r2.put(r0, r1)     // Catch:{ Throwable -> 0x008b }
            com.alipay.security.mobile.module.deviceinfo.DeviceInfo r0 = com.alipay.security.mobile.module.deviceinfo.DeviceInfo.getInstance()     // Catch:{ Throwable -> 0x0085 }
            byte[] r6 = r0.getSensorDigest(r6)     // Catch:{ Throwable -> 0x0085 }
            java.lang.String r0 = new java.lang.String     // Catch:{ Throwable -> 0x0085 }
            byte[] r6 = com.alipay.security.mobile.module.crypto.Hex.encode(r6)     // Catch:{ Throwable -> 0x0085 }
            r0.<init>(r6)     // Catch:{ Throwable -> 0x0085 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r6 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Throwable -> 0x0085 }
            java.lang.String r1 = "sensorDigest"
            r6.b(r1, r0)     // Catch:{ Throwable -> 0x0085 }
            java.lang.String r6 = "_sensorDigest"
            r2.put(r6, r0)     // Catch:{ Throwable -> 0x0085 }
        L_0x0085:
            java.lang.String r6 = r2.toString()     // Catch:{ Throwable -> 0x008b }
            r5.h = r6     // Catch:{ Throwable -> 0x008b }
        L_0x008b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.rdssecuritysdk.v3.impl.RDSDataBuilder.a(android.content.Context):com.alipay.rdssecuritysdk.v3.impl.RDSDataBuilder");
    }

    public final RDSDataBuilder b(Context context) {
        try {
            LocationInfo locationInfo = LocationInfo.getLocationInfo(context);
            DeviceInfo instance = DeviceInfo.getInstance();
            EnvironmentInfo instance2 = EnvironmentInfo.getInstance();
            this.d.active = Boolean.valueOf("true".equals(locationInfo.getIsWifiActive()));
            this.d.bssid = locationInfo.getBssid();
            this.d.cid = locationInfo.getCellId();
            this.d.acc = "";
            this.d.lac = locationInfo.getLac();
            this.d.omac = new ArrayList();
            this.d.la = locationInfo.getLatitude();
            this.d.lo = locationInfo.getLongitude();
            this.d.mnc = locationInfo.getMnc();
            this.d.mcc = locationInfo.getMcc();
            this.d.ssid = locationInfo.getSsid();
            this.d.strength = locationInfo.getWifiStrength();
            this.d.carrier = instance.getOperatorName(context);
            this.d.nettpye = instance2.getNetworkConnectionType(context);
            this.d.t = Long.valueOf(System.currentTimeMillis());
        } catch (Throwable th) {
            TraceLogger traceLogger = this.a;
            StringBuilder sb = new StringBuilder("RDSDataBuilder::buildLocationInfo error happened, ");
            sb.append(CommonUtils.getStackString(th));
            traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        }
        return this;
    }

    public final RDSDataBuilder a(Context context, String str, String str2, String str3, String str4, boolean z) {
        try {
            DeviceInfo instance = DeviceInfo.getInstance();
            EnvironmentInfo instance2 = EnvironmentInfo.getInstance();
            this.e.apdid = str;
            this.e.umid = str2;
            this.e.utdid = str3;
            this.e.tid = str4;
            this.e.imei = instance.getIMEI(context);
            this.e.imsi = instance.getIMSI(context);
            this.e.mac = instance.getMACAddress(context);
            this.e.px = instance.getScreenResolution(context);
            this.e.w = String.valueOf(instance.getScreenWidth(context));
            this.e.h = String.valueOf(instance.getScreenHeight(context));
            this.e.idfa = "";
            this.e.gss = instance2.getGsmSimState();
            this.e.gss2 = instance2.getGsmSimState2();
            this.e.usb = instance2.getUsbState();
            this.e.wi = instance2.getWifiInterface();
            if (z) {
                this.b = new SensorCollectors(context);
                this.b.a();
                Sensor sensor = new Sensor();
                sensor.data = new Data();
                sensor.t = Long.valueOf(System.currentTimeMillis());
                this.e.sensor = sensor;
            }
        } catch (Throwable th) {
            TraceLogger traceLogger = this.a;
            StringBuilder sb = new StringBuilder("RDSDataBuilder::buildLocationInfo error happened, ");
            sb.append(CommonUtils.getStackString(th));
            traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        }
        return this;
    }

    public final RDSDataBuilder c(Context context) {
        try {
            EnvironmentInfo instance = EnvironmentInfo.getInstance();
            DeviceInfo instance2 = DeviceInfo.getInstance();
            this.f.asdk = instance.getBuildVersionSDK();
            this.f.board = instance.getProductBoard();
            this.f.brand = instance.getProductBrand();
            this.f.device = instance.getProductDevice();
            this.f.displayid = instance.getBuildDisplayId();
            this.f.em = Boolean.valueOf(instance.isEmulator(context));
            this.f.manufacturer = instance.getProductManufacturer();
            this.f.model = instance.getProductModel();
            this.f.name = instance.getProductName();
            this.f.incremental = instance.getBuildVersionIncremental();
            this.f.os = "android";
            this.f.qemu = instance.getKernelQemu();
            this.f.osRelease = instance.getBuildVersionRelease();
            this.f.kerver = instance2.getKernelVersion();
            this.f.root = Boolean.valueOf(instance.isRooted());
            this.f.tags = instance.getBuildTags();
            this.f.processor = instance2.getCpuName();
            this.f.pf = instance2.getCpuFrequent();
            this.f.pn = String.valueOf(instance2.getCpuCount());
            this.f.pm = instance2.getCPUHardware();
        } catch (Throwable th) {
            TraceLogger traceLogger = this.a;
            StringBuilder sb = new StringBuilder("RDSDataBuilder::buildEnvironmentInfo error happened, ");
            sb.append(CommonUtils.getStackString(th));
            traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        }
        return this;
    }

    public final RDSDataBuilder d(Context context) {
        Map<String, String> secGuardWuaForRDS = DeviceInfo.getInstance().getSecGuardWuaForRDS(context);
        if (secGuardWuaForRDS != null) {
            try {
                this.g.t = secGuardWuaForRDS.get(LogItem.MM_C15_K4_TIME);
                this.g.appKey = secGuardWuaForRDS.get("appKey");
                this.g.version = secGuardWuaForRDS.get("version");
                this.g.wua = secGuardWuaForRDS.get("wua");
            } catch (Throwable th) {
                TraceLogger traceLogger = this.a;
                StringBuilder sb = new StringBuilder("RDSDataBuilder::buildWuaInfo error happened, ");
                sb.append(CommonUtils.getStackString(th));
                traceLogger.b((String) CONST.LOG_TAG, sb.toString());
            }
        }
        return this;
    }
}
