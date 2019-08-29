package defpackage;

import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.common.MtopNetworkProp;
import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: fdo reason: default package */
/* compiled from: TimeCalibrationAfterFilter */
public final class fdo implements fdg {
    public final String a() {
        return "mtopsdk.TimeCalibrationAfterFilter";
    }

    public final String a(fdf fdf) {
        MtopResponse mtopResponse = fdf.c;
        MtopNetworkProp mtopNetworkProp = fdf.d;
        if (mtopResponse.isExpiredRequest() && !mtopNetworkProp.timeCalibrated) {
            mtopNetworkProp.timeCalibrated = true;
            mtopNetworkProp.skipCacheCallback = true;
            try {
                String a = fcz.a(mtopResponse.getHeaderFields(), "x-systime");
                if (fdd.a(a)) {
                    fgy.c("t_offset", String.valueOf(Long.parseLong(a) - (System.currentTimeMillis() / 1000)));
                    fdy fdy = fdf.a.c.K;
                    if (fdy != null) {
                        new fds(null);
                        fdy.a("mtopsdk.ProtocolParamBuilderBeforeFilter", fdf);
                        return "STOP";
                    }
                }
            } catch (Exception e) {
                TBSdkLog.b("mtopsdk.TimeCalibrationAfterFilter", fdf.h, "parse x-systime from mtop response header error", e);
            }
        }
        return "CONTINUE";
    }
}
