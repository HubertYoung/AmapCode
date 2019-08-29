package defpackage;

import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import com.autonavi.annotation.BundleInterface;

@BundleInterface(awi.class)
/* renamed from: awl reason: default package */
/* compiled from: PoiSelectServiceImpl */
public class awl implements awi {
    private awj a;

    public final void a(Intent intent, awj awj) {
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (microApplicationContext.getTopApplication() != null) {
            microApplicationContext.startActivity((MicroApplication) microApplicationContext.getTopApplication(), intent);
        } else {
            Context context = (Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
            if (context == null) {
                context = LauncherApplicationAgent.getInstance().getApplicationContext();
                intent.setFlags(268435456);
            }
            if (context != null) {
                context.startActivity(intent);
            }
        }
        this.a = awj;
    }

    public final awj a() {
        return this.a;
    }

    public final void b() {
        this.a = null;
    }
}
