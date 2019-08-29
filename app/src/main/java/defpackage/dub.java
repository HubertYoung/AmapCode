package defpackage;

import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Handler;

/* renamed from: dub reason: default package */
/* compiled from: PreviewCallback */
final class dub implements PreviewCallback {
    private final dtr a;
    private Handler b;
    private int c;

    dub(dtr dtr) {
        this.a = dtr;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Handler handler, int i) {
        this.b = handler;
        this.c = i;
    }

    public final void onPreviewFrame(byte[] bArr, Camera camera) {
        Point point = this.a.b;
        Handler handler = this.b;
        if (point != null && handler != null) {
            handler.obtainMessage(this.c, point.x, point.y, bArr).sendToTarget();
            this.b = null;
        }
    }
}
