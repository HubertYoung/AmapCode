package defpackage;

import android.app.Application;
import android.os.SystemClock;
import android.support.annotation.NonNull;

/* renamed from: clk reason: default package */
/* compiled from: RecordingStartTimeInit */
public final class clk extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "RecordingStartTimeInit";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        ckc.b();
        ckc.a = SystemClock.elapsedRealtime();
    }
}
