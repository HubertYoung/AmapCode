package com.alipay.android.phone.inside.commonbiz.report;

import android.content.Context;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.a.a.a;
import com.alipay.android.phone.inside.api.container.ILocationProvider;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.cashier.service.InsideServiceGetTid;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.model.BluetoothInfo;
import com.alipay.android.phone.inside.commonbiz.ids.model.CdmaModel;
import com.alipay.android.phone.inside.commonbiz.ids.model.GsmModel;
import com.alipay.android.phone.inside.commonbiz.ids.model.LocationInfo;
import com.alipay.android.phone.inside.commonbiz.ids.model.TelephoneInfo;
import com.alipay.android.phone.inside.commonbiz.ids.model.WifiInfo;
import com.alipay.android.phone.inside.commonbiz.report.facade.CommonBizFacadeFactory;
import com.alipay.android.phone.inside.commonbiz.report.model.CDMAInfoPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.CellTypeEnumPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.DeviceLocationReqPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.DeviceLocationResPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.GSMInfoPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.TidInfoPbPB;
import com.alipay.android.phone.inside.commonbiz.report.model.WifiInfoPbPB;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ReportLocationService {
    private static ReportLocationService a;
    /* access modifiers changed from: private */
    public Context b = LauncherApplication.a().getApplicationContext();
    private APSecuritySdk c;

    private ReportLocationService() {
    }

    public static ReportLocationService a() {
        synchronized (ReportLocationService.class) {
            try {
                if (a == null) {
                    a = new ReportLocationService();
                }
            }
        }
        return a;
    }

    public final void a(String str) {
        LoggerFactory.f().a((String) "ReportLocationService", (String) "begin report device location");
        final HashMap hashMap = null;
        try {
            if (!TextUtils.isEmpty(str)) {
                hashMap = new HashMap();
                hashMap.put("viewId", str);
            }
            if (!ReportConfig.a(this.b).a(".LocationCfg", "unifylogin$")) {
                LoggerFactory.f().a((String) "ReportLocationService", (String) "disallow report alive time");
            } else {
                new Thread(new Runnable() {
                    public void run() {
                        String str;
                        try {
                            DeviceInfo.b();
                            AppInfo.b();
                            DeviceLocationResPbPB reportDeviceLocationPb = CommonBizFacadeFactory.a().reportDeviceLocationPb(ReportLocationService.this.a(hashMap));
                            TraceLogger f = LoggerFactory.f();
                            if ("report location rpc end,result : ".concat(String.valueOf(reportDeviceLocationPb)) == null) {
                                str = "null";
                            } else {
                                StringBuilder sb = new StringBuilder("success = ");
                                sb.append(reportDeviceLocationPb.success);
                                sb.append(" clientConfig=");
                                sb.append(reportDeviceLocationPb.clientReportConfig);
                                sb.append(" errormsg=");
                                sb.append(reportDeviceLocationPb.errorMsg);
                                str = sb.toString();
                            }
                            f.a((String) "ReportLocationService", str);
                            ReportConfig.a(ReportLocationService.this.b).a(".LocationCfg", "unifylogin$", reportDeviceLocationPb.clientReportConfig);
                            ReportLocationService.a(ReportLocationService.this, reportDeviceLocationPb);
                        } catch (Throwable th) {
                            LoggerFactory.f().b((String) "ReportLocationService", th);
                        }
                    }
                }, "AliInside.reportDeviceLocation").start();
            }
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "ReportLocationService", th);
        }
    }

    /* access modifiers changed from: private */
    public DeviceLocationReqPbPB a(Map<String, String> map) {
        DeviceLocationReqPbPB deviceLocationReqPbPB = new DeviceLocationReqPbPB();
        Location c2 = c();
        try {
            LoggerFactory.d().b("commonbiz", BehaviorType.EVENT, "buildLbsLocationInfo|".concat(String.valueOf(c2)));
            LocationInfo c3 = OutsideConfig.c();
            double d = -1.0d;
            if (c2 != null) {
                deviceLocationReqPbPB.latitude = Double.valueOf(c2.getLatitude() <= 0.0d ? -1.0d : c2.getLatitude());
                deviceLocationReqPbPB.longitude = Double.valueOf(c2.getLongitude() <= 0.0d ? -1.0d : c2.getLongitude());
                deviceLocationReqPbPB.accuracy = Double.valueOf(c2.getAccuracy() <= 0.0f ? -1.0d : (double) c2.getAccuracy());
                deviceLocationReqPbPB.altitude = Double.valueOf(c2.getAltitude() <= 0.0d ? -1.0d : c2.getAltitude());
                deviceLocationReqPbPB.direction = Double.valueOf(c2.getBearing() <= 0.0f ? -1.0d : (double) c2.getBearing());
                if (c2.getSpeed() > 0.0f) {
                    d = (double) c2.getSpeed();
                }
                deviceLocationReqPbPB.speed = Double.valueOf(d);
            } else if (c3 != null) {
                deviceLocationReqPbPB.latitude = Double.valueOf(TextUtils.isEmpty(c3.d()) ? -1.0d : Double.valueOf(c3.d()).doubleValue());
                deviceLocationReqPbPB.longitude = Double.valueOf(TextUtils.isEmpty(c3.e()) ? -1.0d : Double.valueOf(c3.e()).doubleValue());
                deviceLocationReqPbPB.accuracy = Double.valueOf(TextUtils.isEmpty(c3.a()) ? -1.0d : Double.valueOf(c3.a()).doubleValue());
                deviceLocationReqPbPB.altitude = Double.valueOf(TextUtils.isEmpty(c3.b()) ? -1.0d : Double.valueOf(c3.b()).doubleValue());
                deviceLocationReqPbPB.direction = Double.valueOf(TextUtils.isEmpty(c3.c()) ? -1.0d : Double.valueOf(c3.c()).doubleValue());
                if (!TextUtils.isEmpty(c3.f())) {
                    d = Double.valueOf(c3.f()).doubleValue();
                }
                deviceLocationReqPbPB.speed = Double.valueOf(d);
            }
        } catch (Throwable th) {
            LoggerFactory.f().a("ReportLocationService", "buildLbsLocationInfo error", th);
        }
        Context context = this.b;
        if (this.c == null) {
            this.c = APSecuritySdk.getInstance(context);
        }
        TokenResult tokenResult = this.c.getTokenResult();
        deviceLocationReqPbPB.apdid = tokenResult != null ? tokenResult.apdid : "";
        deviceLocationReqPbPB.apdidToken = tokenResult != null ? tokenResult.apdidToken : "";
        deviceLocationReqPbPB.umidToken = tokenResult != null ? tokenResult.umidToken : "";
        deviceLocationReqPbPB.imei = DeviceInfo.a().p();
        deviceLocationReqPbPB.imsi = DeviceInfo.a().q();
        deviceLocationReqPbPB.extraInfos = null;
        deviceLocationReqPbPB.wifiInfos = d();
        deviceLocationReqPbPB.os = a.a;
        deviceLocationReqPbPB.osVersion = VERSION.RELEASE;
        deviceLocationReqPbPB.wifiConn = Boolean.valueOf(OutsideConfig.k());
        boolean z = false;
        deviceLocationReqPbPB.lbsOpen = Boolean.valueOf((OutsideConfig.c() != null && OutsideConfig.c().g()) || c2 != null);
        deviceLocationReqPbPB.currentMobileOperator = OutsideConfig.e() != null ? OutsideConfig.e().c() : "";
        deviceLocationReqPbPB.accessWirelessNetType = OutsideConfig.l();
        AppInfo.a();
        deviceLocationReqPbPB.source = AppInfo.d();
        deviceLocationReqPbPB.queryLbs = Boolean.FALSE;
        if (map != null) {
            deviceLocationReqPbPB.viewId = map.get("viewId");
        }
        try {
            IInsideService b2 = PluginManager.b(PhoneCashierPlugin.KEY_SERVICE_GET_TID);
            Bundle bundle = new Bundle();
            bundle.putBoolean("IsLoadLocal", true);
            Bundle bundle2 = (Bundle) b2.startForResult(bundle);
            if (bundle2 == null) {
                LoggerFactory.f().c((String) "ReportLocationService", (String) "调用移动快捷获取tid=null");
            } else {
                String string = bundle2.getString(InsideServiceGetTid.CASHIER_TID);
                String string2 = bundle2.getString(InsideServiceGetTid.CASHIER_TID_SEED);
                String string3 = bundle2.getString("IMEI");
                String string4 = bundle2.getString("IMSI");
                String string5 = bundle2.getString(InsideServiceGetTid.CASHIER_TID_VIRTUALTMEI);
                String string6 = bundle2.getString(InsideServiceGetTid.CASHIER_TID_VIRTUALIMSI);
                LoggerFactory.f().a((String) "ReportLocationService", String.format("调用移动快捷获取tid=%s, key=%s, imei=%s, imsi=%s, vimei=%s, vimsi=%s", new Object[]{string, string2, string3, string4, string5, string6}));
                TidInfoPbPB tidInfoPbPB = new TidInfoPbPB();
                tidInfoPbPB.appPackageName = this.b.getPackageName();
                DeviceInfo.a();
                tidInfoPbPB.deviceName = DeviceInfo.o();
                tidInfoPbPB.productId = AppInfo.a().e();
                tidInfoPbPB.productVersion = AppInfo.a().g();
                tidInfoPbPB.vimsi = string6;
                tidInfoPbPB.vimei = string5;
                tidInfoPbPB.imsi = string4;
                tidInfoPbPB.imei = string3;
                tidInfoPbPB.tid = string;
                tidInfoPbPB.clientKey = string2;
                tidInfoPbPB.utdid = DeviceInfo.a().d();
                deviceLocationReqPbPB.tidInfo = tidInfoPbPB;
            }
        } catch (Throwable th2) {
            LoggerFactory.f().b((String) "ReportLocationService", th2);
        }
        try {
            BluetoothInfo d2 = OutsideConfig.d();
            if (d2 != null) {
                if (!TextUtils.isEmpty(d2.b())) {
                    z = Boolean.valueOf(d2.b()).booleanValue();
                }
                deviceLocationReqPbPB.bluetoothOpen = Boolean.valueOf(z);
                deviceLocationReqPbPB.bluetoothMac = d2.a();
            }
        } catch (Throwable th3) {
            LoggerFactory.f().a((String) "ReportLocationService", th3);
        }
        try {
            TelephoneInfo e = OutsideConfig.e();
            if (e != null) {
                List<GsmModel> a2 = e.a();
                CdmaModel b3 = e.b();
                if (a2 != null && !a2.isEmpty()) {
                    deviceLocationReqPbPB.cellType = CellTypeEnumPbPB.GSM;
                    a(deviceLocationReqPbPB, a2);
                } else if (b3 != null) {
                    deviceLocationReqPbPB.cellType = CellTypeEnumPbPB.CDMA;
                    CDMAInfoPbPB cDMAInfoPbPB = new CDMAInfoPbPB();
                    int i = -1;
                    cDMAInfoPbPB.bsid = Integer.valueOf(TextUtils.isEmpty(b3.a()) ? -1 : Integer.valueOf(b3.a()).intValue());
                    cDMAInfoPbPB.nid = Integer.valueOf(TextUtils.isEmpty(b3.b()) ? -1 : Integer.valueOf(b3.b()).intValue());
                    cDMAInfoPbPB.rssi = Integer.valueOf(TextUtils.isEmpty(b3.c()) ? -1 : Integer.valueOf(b3.c()).intValue());
                    if (!TextUtils.isEmpty(b3.d())) {
                        i = Integer.valueOf(b3.d()).intValue();
                    }
                    cDMAInfoPbPB.sid = Integer.valueOf(i);
                    LinkedList linkedList = new LinkedList();
                    linkedList.add(cDMAInfoPbPB);
                    deviceLocationReqPbPB.cdmaInfos = linkedList;
                }
            }
        } catch (Throwable th4) {
            LoggerFactory.f().b("ReportLocationService", "buildBaseStation error", th4);
        }
        return deviceLocationReqPbPB;
    }

    private static Location c() {
        Location location;
        Throwable th;
        try {
            location = ((ILocationProvider) Class.forName("com.alipay.android.phone.inside.container.LocationProviderImpl").newInstance()).getLocation();
            try {
                LoggerFactory.d().b("commonbiz", BehaviorType.EVENT, "LocationProviderImpl");
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            location = null;
            th = th4;
            LoggerFactory.f().a("ReportLocationService", "getLocation error", th);
            return location;
        }
        return location;
    }

    private static void a(DeviceLocationReqPbPB deviceLocationReqPbPB, List<GsmModel> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            int i = 5;
            if (list.size() <= 5) {
                i = list.size();
            }
            for (int i2 = 0; i2 < i; i2++) {
                GsmModel gsmModel = list.get(i2);
                if (gsmModel != null) {
                    GSMInfoPbPB gSMInfoPbPB = new GSMInfoPbPB();
                    int i3 = -1;
                    gSMInfoPbPB.mnc = Integer.valueOf(TextUtils.isEmpty(gsmModel.a()) ? -1 : Integer.valueOf(gsmModel.a()).intValue());
                    gSMInfoPbPB.mcc = Integer.valueOf(TextUtils.isEmpty(gsmModel.b()) ? -1 : Integer.valueOf(gsmModel.b()).intValue());
                    gSMInfoPbPB.lac = Integer.valueOf(TextUtils.isEmpty(gsmModel.d()) ? -1 : Integer.valueOf(gsmModel.d()).intValue());
                    gSMInfoPbPB.cid = Integer.valueOf(TextUtils.isEmpty(gsmModel.c()) ? -1 : Integer.valueOf(gsmModel.c()).intValue());
                    if (!TextUtils.isEmpty(gsmModel.e())) {
                        i3 = Integer.valueOf(gsmModel.e()).intValue();
                    }
                    gSMInfoPbPB.rssi = Integer.valueOf(i3);
                    arrayList.add(gSMInfoPbPB);
                }
            }
        }
        deviceLocationReqPbPB.gsmInfos = arrayList;
    }

    private static List<WifiInfoPbPB> d() {
        ArrayList arrayList = new ArrayList();
        try {
            List<WifiInfo> f = OutsideConfig.f();
            if (f != null && f.size() > 0) {
                for (WifiInfo next : f) {
                    WifiInfoPbPB wifiInfoPbPB = new WifiInfoPbPB();
                    wifiInfoPbPB.rssi = Double.valueOf(TextUtils.isEmpty(next.c()) ? -1.0d : Double.valueOf(next.c()).doubleValue());
                    wifiInfoPbPB.ssid = next.b();
                    wifiInfoPbPB.wifiMac = next.a();
                    arrayList.add(wifiInfoPbPB);
                }
            }
        } catch (Throwable th) {
            LoggerFactory.f().a((String) "ReportLocationService", th);
        }
        return arrayList;
    }

    static /* synthetic */ void a(ReportLocationService reportLocationService, DeviceLocationResPbPB deviceLocationResPbPB) {
        if (!TextUtils.isEmpty(deviceLocationResPbPB.serverTime)) {
            try {
                reportLocationService.b.getSharedPreferences("deviceLock", 0).edit().putString("serverTimeDiff", String.valueOf(Long.parseLong(deviceLocationResPbPB.serverTime) - (System.currentTimeMillis() / 1000))).commit();
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
        }
    }
}
