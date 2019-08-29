package defpackage;

import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: ffk reason: default package */
/* compiled from: NetworkPropertyServiceImpl */
public final class ffk implements ffj {
    public final void a(String str) {
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b("mtopsdk.NetworkPropertyServiceImpl", "[setUserId] set NetworkProperty UserId =".concat(String.valueOf(str)));
        }
        m.b(str);
    }

    public final void b(String str) {
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b("mtopsdk.NetworkPropertyServiceImpl", "[setTtid] set NetworkProperty ttid =".concat(String.valueOf(str)));
        }
        m.a(str);
    }
}
