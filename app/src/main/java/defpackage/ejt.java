package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: ejt reason: default package */
/* compiled from: TrainPlanDateUtil */
public class ejt {
    private static volatile ejt a;

    private ejt() {
    }

    public static ejt a() {
        if (a == null) {
            synchronized (ejt.class) {
                try {
                    if (a == null) {
                        a = new ejt();
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
            j = appContext != null ? appContext.getSharedPreferences("TrainDataSelected", 0).getLong("TrainData", 0) : 0;
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
        Editor edit = appContext.getSharedPreferences("TrainDataSelected", 0).edit();
        edit.putLong("TrainData", j);
        edit.apply();
        return true;
    }

    public final synchronized boolean c() {
        try {
            Context appContext = AMapPageUtil.getAppContext();
            if (appContext == null) {
                return false;
            }
            Editor edit = appContext.getSharedPreferences("TrainDataSelected", 0).edit();
            edit.putLong("TrainData", 0);
            edit.apply();
            return true;
        }
    }
}
