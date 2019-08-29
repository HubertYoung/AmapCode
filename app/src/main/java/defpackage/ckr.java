package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.autonavi.minimap.LeakCanaryUtil;

/* renamed from: ckr reason: default package */
/* compiled from: DebugTool */
public final class ckr extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "DebugTool";
    }

    public final void a(Application application) {
        bqs.a().a(new adv(application));
        new chf();
        LeakCanaryUtil.install(application);
    }
}
