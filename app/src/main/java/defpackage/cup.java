package defpackage;

import android.app.Application;
import android.text.TextUtils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.bundle.apm.data.DeviceInfoManager;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import org.json.JSONObject;

/* renamed from: cup reason: default package */
/* compiled from: Telescope */
public final class cup {
    private static cup a = null;
    private static boolean d = false;
    private cvh b = null;
    /* access modifiers changed from: private */
    public Application c = null;

    /* renamed from: cup$a */
    /* compiled from: Telescope */
    public static class a {
        public static String m;
        public static String n;
        public static String o;
        public int a = 1;
        public boolean b = false;
        public Application c = null;
        public String d = null;
        public String e = "";
        public String f = null;
        public Boolean g = Boolean.FALSE;
        public String h = "undefined";
        public String i = LocationParams.PARA_COMMON_DIU;
        public String j = "tid";
        public String k = LocationParams.PARA_COMMON_DIV;
        public JSONObject l = null;
        public cut p;

        static /* synthetic */ void d(a aVar) {
            aVar.c = null;
            aVar.a = 1;
            aVar.b = false;
        }
    }

    private cup(Application application) {
        this.c = application;
    }

    public static void a(a aVar) {
        if (!d) {
            try {
                if (!(aVar.c == null || aVar.d == null || aVar.e == null || aVar.f == null || aVar.p == null)) {
                    if (a.o != null) {
                        a = new cup(aVar.c);
                        cwl.a = aVar.a;
                        cwi.a = aVar.b;
                        cup cup = a;
                        cvb cvb = new cvb();
                        cvb.a = aVar.d;
                        cvb.b = aVar.e;
                        cvb.c = aVar.f;
                        cvb.d = aVar.h;
                        cvb.h = a.n;
                        cvb.g = a.m;
                        cvb.i = a.o;
                        cvb.e = aVar.g;
                        cvb.k = aVar.i;
                        cvb.j = aVar.j;
                        cvb.f = aVar.k;
                        cva.a(cvb);
                        DeviceInfoManager deviceInfoManager = com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a;
                        Application application = cup.c;
                        deviceInfoManager.v = application;
                        StringBuilder sb = new StringBuilder();
                        sb.append(cwe.a(application, "", false));
                        sb.append(File.separator);
                        sb.append("gpu_cpu.info");
                        deviceInfoManager.y = sb.toString();
                        deviceInfoManager.t();
                        deviceInfoManager.y();
                        deviceInfoManager.s();
                        deviceInfoManager.r();
                        deviceInfoManager.z();
                        deviceInfoManager.B();
                        if (deviceInfoManager.g == null) {
                            deviceInfoManager.v();
                        }
                        if (deviceInfoManager.e == null || deviceInfoManager.f == null) {
                            deviceInfoManager.u();
                        }
                        if (deviceInfoManager.h.intValue() == 0) {
                            deviceInfoManager.w();
                        }
                        if (deviceInfoManager.k == null || deviceInfoManager.i.floatValue() == 0.0f || deviceInfoManager.j.floatValue() == 0.0f) {
                            deviceInfoManager.x();
                        }
                        if (deviceInfoManager.m == null) {
                            deviceInfoManager.A();
                        }
                        deviceInfoManager.a();
                        cve.a(aVar.e, aVar.l);
                        cup.b = new cvh();
                        if (aVar.p != null) {
                            a.b.a = aVar.p;
                        } else {
                            a.b.a = cut.a;
                        }
                        defpackage.cvd.a.b.post(new Runnable() {
                            public final void run() {
                                HashMap hashMap = new HashMap();
                                if (cup.this.c != null) {
                                    hashMap.put("appId", cup.this.c.getPackageName());
                                } else {
                                    hashMap.put("appId", "com.autonavi.minimap");
                                }
                                hashMap.put("appKey", cva.a);
                                hashMap.put("appVersion", cva.b);
                                hashMap.put("packageName", cva.c);
                                hashMap.put("utdid", cva.d);
                                hashMap.put(LocationParams.PARA_COMMON_DIU, cva.j);
                                hashMap.put("tid", cva.i);
                                hashMap.put(LocationParams.PARA_COMMON_DIV, cva.k);
                                hashMap.put(LocationParams.PARA_COMMON_DIC, NetworkParam.getDic());
                                hashMap.put(LocationParams.PARA_COMMON_ADIU, NetworkParam.getAdiu());
                                HashMap hashMap2 = new HashMap();
                                hashMap2.put("isRooted", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.b()));
                                DeviceInfoManager deviceInfoManager = com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a;
                                if (TextUtils.isEmpty(deviceInfoManager.l)) {
                                    deviceInfoManager.a();
                                }
                                hashMap2.put("networkStatus", deviceInfoManager.l);
                                hashMap2.put("isEmulator", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.c()));
                                hashMap2.put("mobileBrand", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.r));
                                hashMap2.put("mobileModel", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.q));
                                hashMap2.put("apiLevel", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.s.intValue()));
                                hashMap2.put("osVersion", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.t));
                                hashMap2.put("storeTotalSize", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.q()));
                                hashMap2.put("deviceTotalMemory", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.d()));
                                hashMap2.put("memoryThreshold", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.e()));
                                hashMap2.put("cpuModel", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.g()));
                                hashMap2.put("cpuBrand", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.f()));
                                hashMap2.put("cpuArch", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.h()));
                                hashMap2.put("cpuProcessCount", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.i()));
                                hashMap2.put("cpuFreqArray", Arrays.toString(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.l()));
                                hashMap2.put("cpuMaxFreq", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.j()));
                                hashMap2.put("cpuMinFreq", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.k()));
                                hashMap2.put("gpuMaxFreq", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.m()));
                                hashMap2.put("screenWidth", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.n()));
                                hashMap2.put("screenHeight", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.o()));
                                hashMap2.put("screenDensity", String.valueOf(com.autonavi.minimap.bundle.apm.data.DeviceInfoManager.a.a.p()));
                                ReportManager.getInstance().initSuperLog(cup.this.c, hashMap, hashMap2);
                            }
                        });
                        cvf.a(cup.c, (cuu) cup.b);
                        cvf.a(cve.a());
                        d = true;
                        a.d(aVar);
                        return;
                    }
                }
                throw new RuntimeException("You must set application!");
            } catch (Throwable th) {
                th.printStackTrace();
                cwi.a("init", "build failed! check your initTTS params.", th);
            }
        }
    }

    public static boolean a() {
        return d;
    }

    public static void b() {
        if (d) {
            a.b.a(cuq.a(1));
        }
    }

    public static void c() {
        if (d) {
            a.b.a(cuq.a(2));
        }
    }
}
