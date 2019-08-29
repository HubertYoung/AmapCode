package defpackage;

import java.util.List;
import java.util.Map;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: fdj reason: default package */
/* compiled from: AntiAttackAfterFilter */
public final class fdj implements fdg {
    public final String a() {
        return "mtopsdk.AntiAttackAfterFilter";
    }

    public final String a(fdf fdf) {
        MtopResponse mtopResponse = fdf.c;
        if (419 != mtopResponse.getResponseCode()) {
            return "CONTINUE";
        }
        Map<String, List<String>> headerFields = mtopResponse.getHeaderFields();
        String a = fcz.a(headerFields, "location");
        fcz.a(headerFields, "x-location-ext");
        fee fee = fdf.a.c.x;
        if (fee != null) {
            fee.a(a);
        } else {
            TBSdkLog.d("mtopsdk.AntiAttackAfterFilter", fdf.h, "didn't register AntiAttackHandler.");
        }
        mtopResponse.setRetCode("ANDROID_SYS_API_41X_ANTI_ATTACK");
        mtopResponse.setRetMsg("哎哟喂,被挤爆啦,请稍后重试(419)!");
        if (TBSdkLog.a(LogEnable.WarnEnable)) {
            String str = fdf.h;
            StringBuilder sb = new StringBuilder("[doAfter] execute AntiAttackAfterFilter apiKey=");
            sb.append(fdf.b.getKey());
            TBSdkLog.c("mtopsdk.AntiAttackAfterFilter", str, sb.toString());
        }
        fed.a(fdf);
        return "STOP";
    }
}
