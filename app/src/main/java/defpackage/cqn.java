package defpackage;

import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Handler;

/* renamed from: cqn reason: default package */
/* compiled from: PreviewCallback */
final class cqn implements PreviewCallback {
    private static final String a = "cqn";
    private final cql b;
    private final boolean c;
    private Handler d;
    private int e;

    cqn(cql cql, boolean z) {
        this.b = cql;
        this.c = z;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Handler handler, int i) {
        this.d = handler;
        this.e = i;
    }

    public final void onPreviewFrame(byte[] bArr, Camera camera) {
        Point point = this.b.c;
        if (!this.c) {
            camera.setPreviewCallback(null);
        }
        if (this.d != null) {
            this.d.obtainMessage(this.e, point.x, point.y, bArr).sendToTarget();
            this.d = null;
        }
    }
}
