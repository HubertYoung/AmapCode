package com.xiaomi.push.mpcd.job;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.xiaomi.channel.commonutils.misc.c;
import com.xiaomi.xmpush.thrift.d;
import java.util.Calendar;
import java.util.List;

public class i extends f {
    private SharedPreferences a;

    public i(Context context, int i) {
        super(context, i);
        this.a = context.getSharedPreferences("mipush_extra", 0);
    }

    public int a() {
        return 9;
    }

    public String b() {
        String str;
        try {
            ActivityManager activityManager = (ActivityManager) this.d.getSystemService(WidgetType.ACTIVITY);
            if (VERSION.SDK_INT < 21) {
                str = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            } else {
                Calendar instance = Calendar.getInstance();
                instance.add(5, -1);
                long timeInMillis = instance.getTimeInMillis();
                instance.add(5, 1);
                List<UsageStats> queryUsageStats = ((UsageStatsManager) this.d.getSystemService("usagestats")).queryUsageStats(0, timeInMillis, instance.getTimeInMillis());
                if (c.a(queryUsageStats)) {
                    return null;
                }
                long j = 0;
                String str2 = "";
                for (int i = 0; i < queryUsageStats.size(); i++) {
                    UsageStats usageStats = queryUsageStats.get(i);
                    if (usageStats.getLastTimeStamp() > j) {
                        j = usageStats.getLastTimeStamp();
                        str2 = usageStats.getPackageName();
                    }
                }
                str = str2;
            }
        } catch (Throwable unused) {
            str = null;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (TextUtils.equals(str, this.a.getString("ltapn", null))) {
            return "^";
        }
        this.a.edit().putString("ltapn", str).commit();
        return str;
    }

    public d d() {
        return d.TopApp;
    }
}
