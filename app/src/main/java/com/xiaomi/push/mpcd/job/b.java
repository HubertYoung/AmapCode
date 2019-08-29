package com.xiaomi.push.mpcd.job;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.xiaomi.xmpush.thrift.d;

public class b extends f {
    public b(Context context, int i) {
        super(context, i);
    }

    public int a() {
        return 5;
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        try {
            for (RunningTaskInfo next : ((ActivityManager) this.d.getSystemService(WidgetType.ACTIVITY)).getRunningTasks(10)) {
                if (!(next == null || next.topActivity == null)) {
                    if (sb.length() > 0) {
                        sb.append(";");
                    }
                    sb.append(next.topActivity.getPackageName());
                }
            }
        } catch (Throwable unused) {
        }
        if (TextUtils.isEmpty(sb)) {
            return null;
        }
        return sb.toString();
    }

    public d d() {
        return d.AppActiveList;
    }
}
