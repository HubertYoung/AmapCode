package defpackage;

import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.common.MtopNetworkProp;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.domain.ProtocolEnum;

/* renamed from: fdp reason: default package */
/* compiled from: CheckRequestParamBeforeFilter */
public final class fdp implements fdh {
    public final String a() {
        return "mtopsdk.CheckRequestParamBeforeFilter";
    }

    public final String b(fdf fdf) {
        MtopResponse mtopResponse;
        MtopRequest mtopRequest = fdf.b;
        MtopNetworkProp mtopNetworkProp = fdf.d;
        String str = fdf.h;
        String str2 = null;
        if (mtopRequest == null) {
            str2 = "mtopRequest is invalid.mtopRequest=null";
            mtopResponse = new MtopResponse("ANDROID_SYS_MTOPCONTEXT_INIT_ERROR", str2);
        } else if (!mtopRequest.isLegalRequest()) {
            StringBuilder sb = new StringBuilder("mtopRequest is invalid. ");
            sb.append(mtopRequest.toString());
            str2 = sb.toString();
            mtopResponse = new MtopResponse(mtopRequest.getApiName(), mtopRequest.getVersion(), "ANDROID_SYS_MTOPCONTEXT_INIT_ERROR", str2);
        } else if (mtopNetworkProp == null) {
            str2 = "MtopNetworkProp is invalid.property=null";
            mtopResponse = new MtopResponse(mtopRequest.getApiName(), mtopRequest.getVersion(), "ANDROID_SYS_MTOPCONTEXT_INIT_ERROR", str2);
        } else {
            mtopResponse = null;
        }
        fdf.c = mtopResponse;
        if (fdd.a(str2) && TBSdkLog.a(LogEnable.ErrorEnable)) {
            TBSdkLog.d("mtopsdk.CheckRequestParamBeforeFilter", str, "[checkRequiredParam]".concat(String.valueOf(str2)));
        }
        if (mtopRequest != null && TBSdkLog.a(LogEnable.DebugEnable)) {
            StringBuilder sb2 = new StringBuilder("[checkRequiredParam]");
            sb2.append(mtopRequest.toString());
            TBSdkLog.a((String) "mtopsdk.CheckRequestParamBeforeFilter", str, sb2.toString());
        }
        fed.a(fdf);
        fff.a();
        if (!fff.e()) {
            TBSdkLog.c("mtopsdk.CheckRequestParamBeforeFilter", str, "[checkRequiredParam]MTOP SSL switch is false");
            fdf.d.protocol = ProtocolEnum.HTTP;
        }
        return mtopResponse == null ? "CONTINUE" : "STOP";
    }
}
