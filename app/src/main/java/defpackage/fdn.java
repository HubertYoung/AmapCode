package defpackage;

import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: fdn reason: default package */
/* compiled from: NetworkErrorAfterFilter */
public final class fdn implements fdg {
    public final String a() {
        return "mtopsdk.NetworkErrorAfterFilter";
    }

    public final String a(fdf fdf) {
        MtopResponse mtopResponse = fdf.c;
        if (mtopResponse.getResponseCode() >= 0) {
            return "CONTINUE";
        }
        mtopResponse.setRetCode("ANDROID_SYS_NETWORK_ERROR");
        mtopResponse.setRetMsg(UserTrackerConstants.EM_NETWORK_ERROR);
        if (TBSdkLog.a(LogEnable.ErrorEnable)) {
            StringBuilder sb = new StringBuilder(128);
            sb.append("api=");
            sb.append(mtopResponse.getApi());
            sb.append(",v=");
            sb.append(mtopResponse.getV());
            sb.append(",retCode =");
            sb.append(mtopResponse.getRetCode());
            sb.append(",responseCode =");
            sb.append(mtopResponse.getResponseCode());
            sb.append(",responseHeader=");
            sb.append(mtopResponse.getHeaderFields());
            TBSdkLog.d("mtopsdk.NetworkErrorAfterFilter", fdf.h, sb.toString());
        }
        fed.a(fdf);
        return "STOP";
    }
}
