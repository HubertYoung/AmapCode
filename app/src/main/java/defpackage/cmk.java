package defpackage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;

/* renamed from: cmk reason: default package */
/* compiled from: DebugUtils */
public final class cmk {

    /* renamed from: cmk$a */
    /* compiled from: DebugUtils */
    public static class a {
        public static final String a;

        static {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().toString());
            sb.append("/Android/data/");
            sb.append(AMapAppGlobal.getApplication().getPackageName());
            sb.append("/files/js/");
            a = sb.toString();
        }
    }

    /* renamed from: cmk$b */
    /* compiled from: DebugUtils */
    public static class b {
        public static void a() {
            new Handler(AMapAppGlobal.getApplication().getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(AMapAppGlobal.getApplication(), "com.autonavi.map.activity.SplashActivity"));
                    PendingIntent activity = PendingIntent.getActivity(AMapAppGlobal.getApplication(), 0, intent, 268435456);
                    AlarmManager alarmManager = (AlarmManager) AMapAppGlobal.getApplication().getSystemService(NotificationCompat.CATEGORY_ALARM);
                    if (alarmManager != null) {
                        alarmManager.set(1, System.currentTimeMillis() + 1000, activity);
                    }
                    Process.killProcess(Process.myPid());
                }
            }, 1000);
        }
    }

    public static boolean a(File file) {
        try {
            if (file.isDirectory()) {
                for (File a2 : file.listFiles()) {
                    a(a2);
                }
                file.delete();
            } else if (file.exists()) {
                file.delete();
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
