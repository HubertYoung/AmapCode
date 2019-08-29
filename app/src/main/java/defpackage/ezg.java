package defpackage;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.List;
import java.util.Map;

/* renamed from: ezg reason: default package */
/* compiled from: OnNotificationClickTask */
final class ezg implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ Map b;
    final /* synthetic */ ezb c;

    ezg(ezb ezb, Context context, Map map) {
        this.c = ezb;
        this.a = context;
        this.b = map;
    }

    public final void run() {
        String packageName = this.a.getPackageName();
        try {
            List<RunningTaskInfo> runningTasks = ((ActivityManager) this.a.getSystemService(WidgetType.ACTIVITY)).getRunningTasks(100);
            if (runningTasks != null) {
                for (RunningTaskInfo runningTaskInfo : runningTasks) {
                    ComponentName componentName = runningTaskInfo.topActivity;
                    if (componentName.getPackageName().equals(packageName)) {
                        StringBuilder sb = new StringBuilder("topClassName=");
                        sb.append(componentName.getClassName());
                        fat.d("OnNotificationClickTask", sb.toString());
                        Intent intent = new Intent();
                        intent.setComponent(componentName);
                        intent.setFlags(270532608);
                        ezb.b(intent, this.b);
                        this.a.startActivity(intent);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            fat.a("OnNotificationClickTask", "start recentIntent is error", e);
        }
        Intent launchIntentForPackage = this.a.getPackageManager().getLaunchIntentForPackage(this.a.getPackageName());
        if (launchIntentForPackage != null) {
            launchIntentForPackage.setFlags(268435456);
            ezb.b(launchIntentForPackage, this.b);
            this.a.startActivity(launchIntentForPackage);
            return;
        }
        fat.a((String) "OnNotificationClickTask", (String) "LaunchIntent is null");
    }
}
