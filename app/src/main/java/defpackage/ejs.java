package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: ejs reason: default package */
/* compiled from: UpdateTimeUtil */
public class ejs {
    private static volatile ejs a;

    private ejs() {
    }

    public static ejs a() {
        if (a == null) {
            synchronized (ejs.class) {
                try {
                    if (a == null) {
                        a = new ejs();
                    }
                }
            }
        }
        return a;
    }

    private synchronized long d(int i) {
        long j;
        Context appContext = AMapPageUtil.getAppContext();
        j = 0;
        if (appContext != null) {
            SharedPreferences sharedPreferences = appContext.getSharedPreferences("STATION_LIST", 0);
            StringBuilder sb = new StringBuilder();
            sb.append(e(i));
            sb.append("station_updated_timestamp");
            j = sharedPreferences.getLong(sb.toString(), 0);
        }
        return j;
    }

    public final synchronized boolean a(int i, long j) {
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null) {
            return false;
        }
        Editor edit = appContext.getSharedPreferences("STATION_LIST", 0).edit();
        StringBuilder sb = new StringBuilder();
        sb.append(e(i));
        sb.append("station_updated_timestamp");
        edit.putLong(sb.toString(), j);
        return edit.commit();
    }

    public final synchronized int a(int i) {
        int i2;
        int i3 = 30;
        if (i != 0 && i == 1) {
            i3 = 7;
        }
        try {
            Context appContext = AMapPageUtil.getAppContext();
            i2 = 0;
            if (appContext != null) {
                SharedPreferences sharedPreferences = appContext.getSharedPreferences("STATION_LIST", 0);
                StringBuilder sb = new StringBuilder();
                sb.append(e(i));
                sb.append("station_presell_date");
                i2 = sharedPreferences.getInt(sb.toString(), i3);
            }
        }
        return i2;
    }

    public final synchronized boolean a(int i, int i2) {
        if (i2 <= 0) {
            return false;
        }
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null) {
            return false;
        }
        Editor edit = appContext.getSharedPreferences("STATION_LIST", 0).edit();
        StringBuilder sb = new StringBuilder();
        sb.append(e(i));
        sb.append("station_presell_date");
        edit.putInt(sb.toString(), i2);
        return edit.commit();
    }

    public final synchronized boolean a(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null) {
            return false;
        }
        Editor edit = appContext.getSharedPreferences("STATION_LIST", 0).edit();
        StringBuilder sb = new StringBuilder();
        sb.append(e(i));
        sb.append("station_updated_version");
        edit.putString(sb.toString(), str);
        return edit.commit();
    }

    public final synchronized String b(int i) {
        String str;
        str = "";
        try {
            Context appContext = AMapPageUtil.getAppContext();
            if (appContext != null) {
                SharedPreferences sharedPreferences = appContext.getSharedPreferences("STATION_LIST", 0);
                StringBuilder sb = new StringBuilder();
                sb.append(e(i));
                sb.append("station_updated_version");
                str = sharedPreferences.getString(sb.toString(), "");
            }
        }
        return str;
    }

    public final synchronized boolean c(int i) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            long d = d(i);
            if (currentTimeMillis - d > 86400000 || d == 0) {
                return true;
            }
            return false;
        }
    }

    private static String e(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i == 0 ? "train" : "coach");
        sb.append("_");
        return sb.toString();
    }
}
