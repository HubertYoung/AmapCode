package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.autonavi.ae.AEUtil;

/* renamed from: ckv reason: default package */
/* compiled from: GLEngine */
public final class ckv extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "GLEngine";
    }

    public final void a(Application application) {
        ckb.a((String) "GLEngine#doInitialization");
        try {
            Class.forName(AEUtil.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
