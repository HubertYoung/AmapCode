package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.File;

/* renamed from: enn reason: default package */
/* compiled from: SoHotfixUtil */
final class enn {
    public static String a(enj enj, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(enj.c);
        sb.append("/");
        sb.append(i);
        return sb.toString();
    }

    public static String b(enj enj, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(enj.e);
        sb.append("/");
        sb.append(i);
        return sb.toString();
    }

    public static boolean a(Context context) {
        return context.getPackageName().equalsIgnoreCase(b(context));
    }

    private static String b(Context context) {
        int myPid = Process.myPid();
        for (RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
            if (next.pid == myPid) {
                return next.processName;
            }
        }
        return null;
    }

    public static void a(File file, long j) {
        if (!file.exists()) {
            enp.a(file, Token.SEPARATOR);
        }
        file.setLastModified(j);
    }
}
