package defpackage;

import android.content.Context;
import android.os.Build;
import com.ut.device.UTDevice;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.deviceid.domain.MtopSysNewDeviceIdRequest;
import mtopsdk.mtop.deviceid.domain.MtopSysNewDeviceIdResponse;
import mtopsdk.mtop.deviceid.domain.MtopSysNewDeviceIdResponseData;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.intf.Mtop;

/* renamed from: ffa reason: default package */
/* compiled from: DeviceIDManager */
public final class ffa {
    static Map<String, a> a = new HashMap();

    /* renamed from: ffa$a */
    /* compiled from: DeviceIDManager */
    class a {
        public boolean a;
        public Future<String> b;
        public String c;

        public a(Future<String> future) {
            this.b = future;
        }
    }

    /* renamed from: ffa$b */
    /* compiled from: DeviceIDManager */
    static class b {
        /* access modifiers changed from: private */
        public static final ffa a = new ffa(0);
    }

    /* synthetic */ ffa(byte b2) {
        this();
    }

    private ffa() {
    }

    public static ffa a() {
        return b.a;
    }

    public final Future<String> a(final Context context, final String str) {
        if (fdd.b(str)) {
            TBSdkLog.d("mtopsdk.DeviceIDManager", "appkey is null,get DeviceId error");
            return null;
        }
        a aVar = a.get(str);
        if (aVar != null) {
            Future<String> future = aVar.b;
            if (future != null && !future.isDone()) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb = new StringBuilder("[getDeviceID] appKey=");
                    sb.append(str);
                    sb.append(" return mLastFuture");
                    TBSdkLog.b("mtopsdk.DeviceIDManager", sb.toString());
                }
                return future;
            }
        }
        final FutureTask futureTask = new FutureTask(new Callable<String>() {
            public final /* synthetic */ Object call() throws Exception {
                String str;
                ffa ffa = ffa.this;
                Context context = context;
                String str2 = str;
                a aVar = ffa.a.get(str2);
                if (aVar != null && fdd.a(aVar.c)) {
                    str = aVar.c;
                } else if (context == null) {
                    str = null;
                } else {
                    fcy.a();
                    String a2 = fcy.a(context, "MtopConfigStore", "MTOPSDK_DEVICEID_STORE.".concat(String.valueOf(str2)), "deviceId");
                    fcy.a();
                    if ("1".equalsIgnoreCase(fcy.a(context, "MtopConfigStore", "MTOPSDK_DEVICEID_STORE.".concat(String.valueOf(str2)), "deviceId_created"))) {
                        a aVar2 = new a(null);
                        aVar2.c = a2;
                        aVar2.a = true;
                        ffa.a.put(str2, aVar2);
                    }
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        StringBuilder sb = new StringBuilder("[getLocalDeviceID]get DeviceId from store appkey=");
                        sb.append(str2);
                        sb.append("; deviceId=");
                        sb.append(a2);
                        TBSdkLog.b("mtopsdk.DeviceIDManager", sb.toString());
                    }
                    str = a2;
                }
                String a3 = ffa.a(context);
                if (fdd.b(str) || fdd.b(a3)) {
                    str = ffa.this.b(context, str);
                }
                if (fdd.a(str)) {
                    Mtop a4 = Mtop.a((String) "INNER", (Context) null);
                    if (str != null) {
                        a4.c.p = str;
                        fgy.a(a4.b, "deviceId", str);
                    }
                }
                return str;
            }
        });
        ffy.a(new Runnable() {
            public final void run() {
                futureTask.run();
            }
        });
        a.put(str, new a(futureTask));
        return futureTask;
    }

    public static String a(Context context) {
        String a2 = fgy.a((String) "utdid");
        if (fdd.a(a2)) {
            Mtop.a((String) "INNER", (Context) null).b(a2);
            return a2;
        } else if (context == null) {
            if (TBSdkLog.a(LogEnable.WarnEnable)) {
                TBSdkLog.c("mtopsdk.DeviceIDManager", "[getLocalUtdid] Context is null,get Utdid failed");
            }
            return null;
        } else {
            String utdid = UTDevice.getUtdid(context);
            Mtop.a((String) "INNER", (Context) null).b(utdid);
            return utdid;
        }
    }

    /* access modifiers changed from: 0000 */
    public final String b(Context context, String str) {
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b("mtopsdk.DeviceIDManager", "[getRemoteDeviceID] called!appkey=".concat(String.valueOf(str)));
        }
        String a2 = a(context);
        String a3 = fha.a(context);
        String b2 = fha.b(context);
        StringBuilder sb = new StringBuilder(64);
        if (fdd.a(a2)) {
            sb.append(a2);
        }
        if (fdd.a(a3)) {
            sb.append(a3);
        }
        if (fdd.a(b2)) {
            sb.append(b2);
        }
        String str2 = null;
        if (fdd.b(sb.toString())) {
            TBSdkLog.d("mtopsdk.DeviceIDManager", "[getRemoteDeviceID]device_global_id is blank");
            return null;
        }
        MtopSysNewDeviceIdRequest mtopSysNewDeviceIdRequest = new MtopSysNewDeviceIdRequest();
        mtopSysNewDeviceIdRequest.device_global_id = sb.toString();
        mtopSysNewDeviceIdRequest.new_device = true;
        mtopSysNewDeviceIdRequest.c0 = Build.BRAND;
        mtopSysNewDeviceIdRequest.c1 = Build.MODEL;
        mtopSysNewDeviceIdRequest.c2 = a3;
        mtopSysNewDeviceIdRequest.c3 = b2;
        mtopSysNewDeviceIdRequest.c4 = fha.d(context);
        mtopSysNewDeviceIdRequest.c5 = fha.b();
        mtopSysNewDeviceIdRequest.c6 = fha.c(context);
        MtopResponse syncRequest = Mtop.a((String) "INNER", (Context) null).a((ffb) mtopSysNewDeviceIdRequest, (String) null).setBizId(4099).syncRequest();
        if (syncRequest.isApiSuccess()) {
            try {
                BaseOutDo baseOutDo = (BaseOutDo) ffx.b(syncRequest.getBytedata(), MtopSysNewDeviceIdResponse.class);
                if (baseOutDo != null) {
                    String str3 = ((MtopSysNewDeviceIdResponseData) baseOutDo.getData()).device_id;
                    try {
                        if (fdd.a(str3) && context != null) {
                            fcy.a();
                            fcy.a(context, "MtopConfigStore", "MTOPSDK_DEVICEID_STORE.".concat(String.valueOf(str)), "deviceId", str3);
                            fcy.a();
                            fcy.a(context, "MtopConfigStore", "MTOPSDK_DEVICEID_STORE.".concat(String.valueOf(str)), "deviceId_created", "1");
                            a aVar = a.get(str);
                            if (aVar == null) {
                                aVar = new a(null);
                            }
                            aVar.c = str3;
                            aVar.a = true;
                            a.put(str, aVar);
                            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                                StringBuilder sb2 = new StringBuilder(32);
                                sb2.append("[saveDeviceIdToStore] appkey=");
                                sb2.append(str);
                                sb2.append("; deviceId=");
                                sb2.append(str3);
                                sb2.append("; mCreated=");
                                sb2.append("1");
                                TBSdkLog.b("mtopsdk.DeviceIDManager", sb2.toString());
                            }
                        }
                        str2 = str3;
                    } catch (Throwable th) {
                        th = th;
                        str2 = str3;
                        StringBuilder sb3 = new StringBuilder("[getRemoteDeviceID] error ---");
                        sb3.append(th.toString());
                        TBSdkLog.d("mtopsdk.DeviceIDManager", sb3.toString());
                        return str2;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                StringBuilder sb32 = new StringBuilder("[getRemoteDeviceID] error ---");
                sb32.append(th.toString());
                TBSdkLog.d("mtopsdk.DeviceIDManager", sb32.toString());
                return str2;
            }
        }
        return str2;
    }
}
