package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.autonavi.minimap.LeakCanaryUtil;
import com.autonavi.minimap.LogUploadUtil;

/* renamed from: ckp reason: default package */
/* compiled from: CommonModuleUtilsInit */
public final class ckp extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "CommonModuleUtilsInit";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        LeakCanaryUtil.setImpl(new chd());
        LogUploadUtil.setImpl(new che());
        adw.a(new chj());
    }
}
