package defpackage;

import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.util.MtopStatistics;

/* renamed from: fdm reason: default package */
/* compiled from: ExecuteCallbackAfterFilter */
public final class fdm implements fdg {
    public final String a() {
        return "mtopsdk.ExecuteCallbackAfterFilter";
    }

    public final String a(fdf fdf) {
        MtopStatistics mtopStatistics = fdf.g;
        MtopResponse mtopResponse = fdf.c;
        String str = fdf.h;
        feu feu = new feu(mtopResponse);
        feu.b = str;
        mtopStatistics.H = fcz.a(mtopResponse.getHeaderFields(), "x-s-traceid");
        mtopStatistics.o = mtopResponse.getRetCode();
        mtopStatistics.n = mtopResponse.getResponseCode();
        mtopStatistics.q = mtopResponse.getMappingCode();
        mtopStatistics.b();
        few few = fdf.e;
        try {
            if (few instanceof b) {
                ((b) few).onFinished(feu, fdf.d.reqContext);
            }
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("call MtopFinishListener error,apiKey=");
            sb.append(fdf.b.getKey());
            TBSdkLog.b("mtopsdk.ExecuteCallbackAfterFilter", str, sb.toString(), th);
        }
        return "CONTINUE";
    }
}
