package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.amap.bundle.network.request.param.NetworkParam;

/* renamed from: cll reason: default package */
/* compiled from: SecurityGuard */
public final class cll extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "SecurityGuard";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        try {
            SecurityGuardManager.setGlobalUserData("channel", NetworkParam.getDic());
        } catch (SecException e) {
            e.printStackTrace();
        }
    }
}
