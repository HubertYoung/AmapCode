package defpackage;

import com.amap.bundle.network.request.param.NetworkParam;

/* renamed from: zg reason: default package */
/* compiled from: AosPhaseListener */
public final class zg implements a {
    public final void a(bph bph, io ioVar) {
        if (!(bph == null || ioVar == null)) {
            try {
                bph.requestStatistics.i = Long.parseLong(ioVar.a.get("stepid"));
            } catch (Throwable unused) {
                bph.requestStatistics.i = NetworkParam.getStepId();
            }
        }
    }
}
