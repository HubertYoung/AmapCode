package defpackage;

import android.app.Application;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import com.autonavi.profile.ProfileBroadcastReceiver;

/* renamed from: clj reason: default package */
/* compiled from: Profile */
public final class clj extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "Profile";
    }

    public final void a(Application application) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.autonavi.minimap.debug.profile");
        application.registerReceiver(new ProfileBroadcastReceiver(), intentFilter);
    }
}
