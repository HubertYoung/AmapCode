package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.ae.data.DataService;

/* renamed from: lm reason: default package */
/* compiled from: GlobalDBUtil */
public final class lm {
    public static int a() {
        try {
            return DataService.getInstance().getGlobalDBDataVersion();
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("getGlobalDBDataVersion()");
            sb.append(th.getMessage());
            AMapLog.error("paas.cityinfo", "GlobalDBUtil", sb.toString());
            return 0;
        }
    }
}
