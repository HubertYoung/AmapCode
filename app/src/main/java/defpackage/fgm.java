package defpackage;

import android.content.Context;
import java.util.concurrent.ExecutorService;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.network.domain.Request;

/* renamed from: fgm reason: default package */
/* compiled from: DefaultCallFactory */
public final class fgm implements a {
    ExecutorService a;

    public fgm(Context context, ExecutorService executorService) {
        this.a = executorService;
        try {
            fgg.a(context);
        } catch (Exception e) {
            TBSdkLog.b((String) "mtopsdk.DefaultCallFactory", (String) "call CookieManager.setup error.", (Throwable) e);
        }
    }

    public final fge a(Request request) {
        return new fgn(request, this.a);
    }
}
