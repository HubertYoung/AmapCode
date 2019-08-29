package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* renamed from: dzs reason: default package */
/* compiled from: CoachDateUtil */
public class dzs {
    private static volatile dzs a;

    private dzs() {
    }

    public static dzs a() {
        if (a == null) {
            synchronized (dzs.class) {
                try {
                    if (a == null) {
                        a = new dzs();
                    }
                }
            }
        }
        return a;
    }

    public final synchronized long b() {
        long j;
        try {
            Context appContext = AMapPageUtil.getAppContext();
            j = appContext != null ? appContext.getSharedPreferences("CoachDateSelected", 0).getLong("CoachDate", 0) : 0;
            if (j == 0) {
                j = System.currentTimeMillis() + 86400000;
            }
        }
        return j;
    }

    public final synchronized boolean a(long j) {
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null) {
            return false;
        }
        Editor edit = appContext.getSharedPreferences("CoachDateSelected", 0).edit();
        edit.putLong("CoachDate", j);
        return edit.commit();
    }

    public final synchronized boolean c() {
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null) {
            return false;
        }
        Editor edit = appContext.getSharedPreferences("CoachDateSelected", 0).edit();
        edit.putLong("CoachDate", 0);
        return edit.commit();
    }

    public static String d() {
        new SimpleDateFormat("MM月dd日 E");
        Date date = new Date(System.currentTimeMillis());
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(5, ejs.a().a(1) - 1);
        return axt.a(instance.getTime().getTime());
    }

    public static String e() {
        return axt.a(System.currentTimeMillis());
    }

    public static String b(long j) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(j));
    }
}
