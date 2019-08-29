package defpackage;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import com.autonavi.minimap.app.init.Adiu$1;

/* renamed from: ckj reason: default package */
/* compiled from: Adiu */
public final class ckj extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "Adiu";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        ik.a().a((Context) application);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.permission.WRITE_EXTERNAL_STORAGE");
        LocalBroadcastManager.getInstance(application).registerReceiver(new Adiu$1(this, application), intentFilter);
    }
}
