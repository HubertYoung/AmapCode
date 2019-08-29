package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

/* renamed from: anm reason: default package */
/* compiled from: MEnvCheckUtil */
public final class anm {
    private static boolean a = false;
    private static String b = "com.autonavi.map.activity.SplashActivity";

    public static void a(boolean z) {
        a = z;
    }

    public static void a(Context context) {
        if (!a) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(context, b));
            intent.addFlags(268435456);
            context.startActivity(intent);
            Process.killProcess(Process.myPid());
        }
    }
}
