package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.ResultType;
import com.amap.bundle.cloudconfig.aocs.model.ConfigModule;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressFBWarnings({"WMI_WRONG_MAP_ITERATOR", "UW_UNCOND_WAIT", "WA_NOT_IN_LOOP"})
/* renamed from: lo reason: default package */
/* compiled from: CloudConfigService */
public class lo {
    private static a d;
    private static volatile lo e;
    public ln a;
    public Handler b;
    /* access modifiers changed from: private */
    public volatile Map<String, List<lp>> c = new HashMap();

    /* renamed from: lo$a */
    /* compiled from: CloudConfigService */
    public interface a {
        GeoPoint a();

        GeoPoint b();
    }

    /* renamed from: lo$b */
    /* compiled from: CloudConfigService */
    public class b implements com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a {
        public b() {
        }

        public final boolean a(ArrayList<lq> arrayList) {
            lo.a(lo.this, (ArrayList) arrayList);
            return true;
        }

        public final void a(int i, List<String> list) {
            if (i == ResultType.RESULT_NETWORK_ERROR.getCode() || i == ResultType.RESULT_NO_NETWORK.getCode() || i == ResultType.RESULT_PARSE_FAIL.getCode() || i == ResultType.RESULT_SERVER_ERROR.getCode()) {
                lo.a(lo.this, (List) list);
            }
        }
    }

    private lo() {
    }

    public static lo a() {
        if (e == null) {
            synchronized (lo.class) {
                try {
                    if (e == null) {
                        e = new lo();
                    }
                }
            }
        }
        return e;
    }

    public final void a(String str, @NonNull lp lpVar) {
        if (this.a != null && this.a.f.get()) {
            lpVar.onConfigResultCallBack(4, a(str));
        }
        synchronized (this) {
            if (this.c.containsKey(str)) {
                this.c.get(str).add(lpVar);
            } else {
                List synchronizedList = Collections.synchronizedList(new ArrayList());
                synchronizedList.add(lpVar);
                this.c.put(str, synchronizedList);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0023, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(java.lang.String r2, defpackage.lp r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.Map<java.lang.String, java.util.List<lp>> r0 = r1.c     // Catch:{ all -> 0x0026 }
            boolean r0 = r0.containsKey(r2)     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0024
            if (r3 != 0) goto L_0x000c
            goto L_0x0024
        L_0x000c:
            java.util.Map<java.lang.String, java.util.List<lp>> r0 = r1.c     // Catch:{ all -> 0x0026 }
            java.lang.Object r0 = r0.get(r2)     // Catch:{ all -> 0x0026 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x0026 }
            r0.remove(r3)     // Catch:{ all -> 0x0026 }
            int r3 = r0.size()     // Catch:{ all -> 0x0026 }
            if (r3 != 0) goto L_0x0022
            java.util.Map<java.lang.String, java.util.List<lp>> r3 = r1.c     // Catch:{ all -> 0x0026 }
            r3.remove(r2)     // Catch:{ all -> 0x0026 }
        L_0x0022:
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            return
        L_0x0024:
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            return
        L_0x0026:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0026 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.lo.b(java.lang.String, lp):void");
    }

    public final String a(String str) {
        if (!e("getModuleConfig() not init")) {
            return "";
        }
        ConfigModule a2 = this.a.a(str);
        String str2 = null;
        if (a2 != null) {
            str2 = a2.getValue();
        }
        if (bno.a) {
            StringBuilder sb = new StringBuilder("getModuleConfig-");
            sb.append(str);
            sb.append(",value:");
            sb.append(str2);
            AMapLog.debug("paas.cloudconfig", "CloudConfigService", sb.toString());
        }
        return str2;
    }

    public final String b(String str) {
        if (Looper.myLooper() == Looper.getMainLooper() && bno.a) {
            throw new RuntimeException("请勿在主线程调用此方法");
        } else if (!e("getModuleConfigSync() not init")) {
            return "";
        } else {
            this.a.a();
            ConfigModule a2 = this.a.a(str);
            String str2 = null;
            if (a2 != null) {
                str2 = a2.getValue();
            }
            if (bno.a) {
                StringBuilder sb = new StringBuilder("getModuleConfigSync-");
                sb.append(str);
                sb.append(",value:");
                sb.append(str2);
                AMapLog.debug("paas.cloudconfig", "CloudConfigService", sb.toString());
            }
            return str2;
        }
    }

    public final void c(String str) {
        if (e("removeModuleConfigCache() not init")) {
            ln lnVar = this.a;
            lnVar.d.remove(str);
            lnVar.a((Map<String, ConfigModule>) lnVar.d);
        }
    }

    public final void d(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        a((List<String>) arrayList);
    }

    public final void a(List<String> list) {
        if (e("updateModuleConfig() not init")) {
            this.a.a(list, (com.amap.bundle.cloudconfig.aocs.CloudConfigRequest.a) new b());
        }
    }

    public static a b() {
        return d;
    }

    public static void a(a aVar) {
        d = aVar;
    }

    public final boolean e(String str) {
        if (this.a != null) {
            return true;
        }
        if (bno.a) {
            throw new IllegalStateException("未调用初始化方法");
        }
        AMapLog.logErrorNative(AMapLog.GROUP_COMMON, "P0080", "CloudConfigService", str);
        return false;
    }

    static /* synthetic */ void a(lo loVar, ArrayList arrayList) {
        if (arrayList != null && arrayList.size() > 0 && loVar.e("callConfigListeners() not init") && loVar.a.f.get()) {
            for (int i = 0; i < arrayList.size(); i++) {
                lq lqVar = (lq) arrayList.get(i);
                List<lp> list = loVar.c.get(lqVar.a);
                if (list != null) {
                    for (lp lpVar : list) {
                        if (lpVar != null) {
                            String a2 = loVar.a(lqVar.a);
                            if (lqVar.b == 2) {
                                lpVar.onConfigCallBack(2);
                            } else {
                                lpVar.onConfigResultCallBack(lqVar.b, a2);
                            }
                        }
                    }
                }
            }
        }
    }

    static /* synthetic */ void a(lo loVar, List<String> list) {
        if (list == null) {
            synchronized (loVar) {
                list = new ArrayList<>(loVar.c.keySet());
            }
        }
        for (String str : list) {
            List<lp> list2 = loVar.c.get(str);
            if (list2 != null) {
                for (lp lpVar : list2) {
                    if (lpVar != null) {
                        lpVar.onConfigCallBack(-1);
                    }
                }
            }
        }
    }
}
