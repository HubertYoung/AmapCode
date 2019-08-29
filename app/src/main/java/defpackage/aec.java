package defpackage;

import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;

/* renamed from: aec reason: default package */
/* compiled from: NetWorkCallBack */
public abstract class aec {
    public void error(int i, String str) {
    }

    public void error(Throwable th, boolean z) {
    }

    public void callback(InfoliteResult infoliteResult) {
        if (infoliteResult == null) {
            error(0, (String) null);
        }
    }
}
