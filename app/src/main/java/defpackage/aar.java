package defpackage;

import com.amap.bundle.logs.AMapLog;

/* renamed from: aar reason: default package */
/* compiled from: ANetLogger */
public final class aar implements a {
    public final void a(String str, String str2) {
        AMapLog.trace("paas.network", str, str2);
    }

    public final void b(String str, String str2) {
        AMapLog.debug("paas.network", str, str2);
    }

    public final void c(String str, String str2) {
        AMapLog.info("paas.network", str, str2);
    }

    public final void d(String str, String str2) {
        AMapLog.warning("paas.network", str, str2);
    }

    public final void e(String str, String str2) {
        AMapLog.error("paas.network", str, str2);
    }
}
