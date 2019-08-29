package defpackage;

import android.os.Handler;
import anetwork.network.cache.RpcCache;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.domain.ResponseSource;
import mtopsdk.mtop.util.MtopStatistics;
import mtopsdk.network.domain.Request;

/* renamed from: fen reason: default package */
/* compiled from: ExpiredCacheParser */
public final class fen implements fep {
    public final void a(ResponseSource responseSource, Handler handler) {
        final String str = responseSource.seqNo;
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) "mtopsdk.ExpiredCacheParser", str, (String) "[parse]ExpiredCacheParser parse called");
        }
        fdf fdf = responseSource.mtopContext;
        MtopStatistics mtopStatistics = fdf.g;
        mtopStatistics.j = 2;
        mtopStatistics.w = MtopStatistics.a();
        RpcCache rpcCache = responseSource.rpcCache;
        MtopResponse a = fel.a(rpcCache, fdf.b);
        a.setSource(MtopResponse.ResponseSource.EXPIRED_CACHE);
        mtopStatistics.x = MtopStatistics.a();
        a.setMtopStat(mtopStatistics);
        final few few = fdf.e;
        final Object obj = fdf.d.reqContext;
        if (few instanceof a) {
            final fer fer = new fer(a);
            fer.b = str;
            mtopStatistics.v = MtopStatistics.a();
            fel.a(mtopStatistics, a);
            if (!fdf.d.skipCacheCallback) {
                AnonymousClass1 r0 = new Runnable() {
                    public final void run() {
                        try {
                            ((a) few).onCached(fer, obj);
                        } catch (Exception e2) {
                            TBSdkLog.b("mtopsdk.ExpiredCacheParser", str, "do onCached callback error.", e2);
                        }
                    }
                };
                fed.a(handler, r0, fdf.h.hashCode());
            }
        }
        mtopStatistics.j = 3;
        Request request = fdf.k;
        if (request != null) {
            if (fdd.a(rpcCache.lastModified)) {
                request.a("if-modified-since", rpcCache.lastModified);
            }
            if (fdd.a(rpcCache.etag)) {
                request.a("if-none-match", rpcCache.etag);
            }
        }
        responseSource.cacheResponse = a;
    }
}
