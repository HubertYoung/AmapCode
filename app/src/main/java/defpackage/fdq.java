package defpackage;

import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.util.MtopStatistics;

/* renamed from: fdq reason: default package */
/* compiled from: ExecuteCallBeforeFilter */
public final class fdq implements fdh {
    public final String a() {
        return "mtopsdk.ExecuteCallBeforeFilter";
    }

    public final String b(fdf fdf) {
        try {
            fdf.g.y = MtopStatistics.a();
            a aVar = fdf.a.c.J;
            if (aVar == null) {
                String str = fdf.h;
                StringBuilder sb = new StringBuilder("call Factory of mtopInstance is null.instanceId=");
                sb.append(fdf.a.b);
                TBSdkLog.d("mtopsdk.ExecuteCallBeforeFilter", str, sb.toString());
                MtopResponse mtopResponse = new MtopResponse("ANDROID_SYS_MTOP_MISS_CALL_FACTORY", "Mtop实例没有设置Call Factory");
                mtopResponse.setApi(fdf.b.getApiName());
                mtopResponse.setV(fdf.b.getVersion());
                fdf.c = mtopResponse;
                fed.a(fdf);
                return "STOP";
            }
            fge a = aVar.a(fdf.k);
            a.a(new ffi(fdf));
            if (fdf.f != null) {
                fdf.f.setCall(a);
            }
            return "CONTINUE";
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder("invoke call.enqueue of mtopInstance error,apiKey=");
            sb2.append(fdf.b.getKey());
            TBSdkLog.b("mtopsdk.ExecuteCallBeforeFilter", fdf.h, sb2.toString(), e);
            return "STOP";
        }
    }
}
