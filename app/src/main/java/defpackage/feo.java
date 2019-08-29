package defpackage;

import android.os.Handler;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.domain.ResponseSource;
import mtopsdk.mtop.util.MtopStatistics;

/* renamed from: feo reason: default package */
/* compiled from: FreshCacheParser */
public final class feo implements fep {
    public final void a(ResponseSource responseSource, Handler handler) {
        final String str = responseSource.seqNo;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) "mtopsdk.FreshCacheParser", str, (String) "[parse]FreshCacheParser parse called");
        }
        responseSource.requireConnection = false;
        fdf fdf = responseSource.mtopContext;
        MtopRequest mtopRequest = fdf.b;
        MtopStatistics mtopStatistics = fdf.g;
        mtopStatistics.j = 1;
        mtopStatistics.w = MtopStatistics.a();
        MtopResponse a = fel.a(responseSource.rpcCache, mtopRequest);
        a.setSource(MtopResponse.ResponseSource.FRESH_CACHE);
        mtopStatistics.x = MtopStatistics.a();
        a.setMtopStat(mtopStatistics);
        responseSource.cacheResponse = a;
        mtopStatistics.v = MtopStatistics.a();
        if (fdf.d.forceRefreshCache) {
            responseSource.requireConnection = true;
            final few few = fdf.e;
            if (few instanceof a) {
                final Object obj = fdf.d.reqContext;
                final fer fer = new fer(a);
                fer.b = str;
                fel.a(mtopStatistics, a);
                if (!fdf.d.skipCacheCallback) {
                    AnonymousClass1 r0 = new Runnable() {
                        public final void run() {
                            try {
                                ((a) few).onCached(fer, obj);
                            } catch (Exception e2) {
                                TBSdkLog.b("mtopsdk.FreshCacheParser", str, "do onCached callback error.", e2);
                            }
                        }
                    };
                    fed.a(handler, r0, fdf.h.hashCode());
                }
                mtopStatistics.j = 3;
            }
        }
    }
}
