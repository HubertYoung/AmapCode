package defpackage;

import anetwork.network.cache.RpcCache;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.util.MtopStatistics;

/* renamed from: fel reason: default package */
/* compiled from: CacheStatusHandler */
public final class fel {
    protected static MtopResponse a(RpcCache rpcCache, MtopRequest mtopRequest) {
        MtopResponse mtopResponse = new MtopResponse();
        mtopResponse.setApi(mtopRequest.getApiName());
        mtopResponse.setV(mtopRequest.getVersion());
        mtopResponse.setBytedata(rpcCache.body);
        mtopResponse.setHeaderFields(rpcCache.header);
        mtopResponse.setResponseCode(200);
        fed.a(mtopResponse);
        return mtopResponse;
    }

    protected static void a(MtopStatistics mtopStatistics, MtopResponse mtopResponse) {
        if (mtopStatistics != null && mtopResponse != null) {
            MtopStatistics mtopStatistics2 = null;
            try {
                mtopStatistics2 = (MtopStatistics) mtopStatistics.clone();
            } catch (Exception e) {
                if (TBSdkLog.a(LogEnable.ErrorEnable)) {
                    TBSdkLog.b("mtopsdk.CacheStatusHandler", mtopStatistics.E, "[finishMtopStatisticsOnCache] clone MtopStatistics error.", e);
                }
            }
            if (mtopStatistics2 != null) {
                mtopResponse.setMtopStat(mtopStatistics2);
                mtopStatistics2.H = fcz.a(mtopResponse.getHeaderFields(), "x-s-traceid");
                mtopStatistics2.n = mtopResponse.getResponseCode();
                mtopStatistics2.o = mtopResponse.getRetCode();
                mtopStatistics2.b();
            }
        }
    }
}
