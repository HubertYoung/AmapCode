package defpackage;

import android.support.annotation.NonNull;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.network.domain.Request;

/* renamed from: fdr reason: default package */
/* compiled from: NetworkConvertBeforeFilter */
public final class fdr implements fdh {
    private ffp a;

    public final String a() {
        return "mtopsdk.NetworkConvertBeforeFilter";
    }

    public fdr(@NonNull ffp ffp) {
        this.a = ffp;
    }

    public final String b(fdf fdf) {
        Request a2 = this.a.a(fdf);
        fdf.k = a2;
        if (a2 != null) {
            return "CONTINUE";
        }
        fdf.c = new MtopResponse(fdf.b.getApiName(), fdf.b.getVersion(), "ANDROID_SYS_NETWORK_REQUEST_CONVERT_ERROR", "网络Request转换失败");
        fed.a(fdf);
        return "STOP";
    }
}
