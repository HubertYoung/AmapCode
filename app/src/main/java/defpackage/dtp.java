package defpackage;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.Handler;

/* renamed from: dtp reason: default package */
/* compiled from: AutoFocusCallback */
final class dtp implements AutoFocusCallback {
    private static final String a = "dtp";
    private Handler b;
    private int c;

    dtp() {
    }

    /* access modifiers changed from: 0000 */
    public final void a(Handler handler, int i) {
        this.b = handler;
        this.c = i;
    }

    public final void onAutoFocus(boolean z, Camera camera) {
        if (this.b != null) {
            this.b.sendMessageDelayed(this.b.obtainMessage(this.c, Boolean.valueOf(z)), 1500);
            this.b = null;
        }
    }
}
