package defpackage;

import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.global.SDKUtils;

/* renamed from: fdx reason: default package */
/* compiled from: FlowLimitDuplexFilter */
public final class fdx implements fdg, fdh {
    public final String a() {
        return "mtopsdk.FlowLimitDuplexFilter";
    }

    public final String b(fdf fdf) {
        if (fdf.d != null && fdf.d.priorityFlag) {
            return "CONTINUE";
        }
        MtopRequest mtopRequest = fdf.b;
        String key = mtopRequest.getKey();
        if (fdb.a.contains(key) || !feg.a(key, SDKUtils.getCorrectionTime())) {
            return "CONTINUE";
        }
        fdf.c = new MtopResponse(mtopRequest.getApiName(), mtopRequest.getVersion(), "ANDROID_SYS_API_FLOW_LIMIT_LOCKED", "哎哟喂,被挤爆啦,请稍后重试(420)");
        if (TBSdkLog.a(LogEnable.WarnEnable)) {
            TBSdkLog.c("mtopsdk.FlowLimitDuplexFilter", fdf.h, "[doBefore] execute FlowLimitDuplexFilter apiKey=".concat(String.valueOf(key)));
        }
        fed.a(fdf);
        return "STOP";
    }

    public final String a(fdf fdf) {
        MtopResponse mtopResponse = fdf.c;
        if (420 != mtopResponse.getResponseCode()) {
            return "CONTINUE";
        }
        String key = fdf.b.getKey();
        feg.b(key, SDKUtils.getCorrectionTime());
        fed.a(mtopResponse);
        if (fdd.b(mtopResponse.getRetCode())) {
            fdf.c.setRetCode("ANDROID_SYS_API_FLOW_LIMIT_LOCKED");
            fdf.c.setRetMsg("哎哟喂,被挤爆啦,请稍后重试(420)");
        }
        if (TBSdkLog.a(LogEnable.WarnEnable)) {
            String str = fdf.h;
            StringBuilder sb = new StringBuilder("[doAfter] execute FlowLimitDuplexFilter apiKey=");
            sb.append(key);
            sb.append(" ,retCode=");
            sb.append(mtopResponse.getRetCode());
            TBSdkLog.c("mtopsdk.FlowLimitDuplexFilter", str, sb.toString());
        }
        fed.a(fdf);
        return "STOP";
    }
}
