package defpackage;

import com.alipay.android.phone.mobilesdk.socketcraft.api.DefaultWebSocketClient;
import com.amap.bundle.logs.AMapLog;

/* renamed from: zw reason: default package */
/* compiled from: StartSceneManger */
public final class zw {
    private static int a = 6000;
    /* access modifiers changed from: private */
    public static Runnable b;

    public static void a(boolean z) {
        if (zy.a().b()) {
            zx.a().b();
        }
        if (b != null) {
            aho.b(b);
        }
        a = z ? 6000 : DefaultWebSocketClient.MIN_CONNECTION_TIMEOUT;
        zt.a().a(z ? "launch_cold" : "launch_hot", true);
        b(new Runnable() {
            public final void run() {
                zt.a().a("launch_cold", false);
                zt.a().a("launch_hot", false);
                if (zw.b == this) {
                    zw.b(zw.b);
                }
                zu.a(false);
                if (bno.a) {
                    AMapLog.e("StartScene ", " OUT ================ >>");
                }
            }
        });
        zu.a(true);
    }

    /* access modifiers changed from: private */
    public static synchronized void b(Runnable runnable) {
        synchronized (zw.class) {
            b = runnable;
        }
    }

    public static void a() {
        if (zy.a().b()) {
            zx.a().c();
        }
        if (bno.a) {
            AMapLog.e("StartScene SceneManagerhttplog", "onDrawFrameFirst stop");
        }
        aho.a(b, (long) a);
    }
}
