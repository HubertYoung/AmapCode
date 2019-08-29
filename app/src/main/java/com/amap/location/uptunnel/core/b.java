package com.amap.location.uptunnel.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import com.amap.location.common.f.g;
import com.amap.location.common.f.h;
import com.amap.location.uptunnel.UpTunnel;
import com.amap.location.uptunnel.core.db.DBProvider;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.util.Calendar;

/* compiled from: UpTunnelContext */
public class b {
    private Context a;
    private DBProvider b;
    private SharedPreferences c;
    private Calendar d;

    public static String c(int i) {
        switch (i) {
            case 1:
                return NewHtcHomeBadger.COUNT;
            case 2:
                return "event";
            case 3:
                return "key_log";
            case 4:
                return ReportManager.LOG_PATH;
            case 5:
                return "data_block";
            default:
                return "";
        }
    }

    public b(Context context) {
        this.a = context;
        try {
            this.d = Calendar.getInstance();
            StringBuilder sb = new StringBuilder();
            sb.append(com.amap.location.common.b.e());
            sb.append("_tunnel");
            this.c = context.getSharedPreferences(sb.toString(), 0);
            this.b = DBProvider.a(context);
        } catch (Throwable unused) {
        }
    }

    public synchronized Context a() {
        try {
        }
        return this.a;
    }

    public synchronized long a(int i, int i2) {
        int i3 = this.c.getInt("last_upload_day_".concat(String.valueOf(i)), -1);
        this.d.setTimeInMillis(System.currentTimeMillis());
        int i4 = this.d.get(6);
        if (i4 != i3) {
            Editor edit = this.c.edit();
            edit.putInt("last_upload_day_".concat(String.valueOf(i)), i4);
            StringBuilder sb = new StringBuilder("uploaded_size_");
            sb.append(i);
            sb.append("_");
            sb.append(i2);
            edit.putLong(sb.toString(), 0);
            StringBuilder sb2 = new StringBuilder("uploaded_size_");
            sb2.append(i);
            sb2.append("_");
            sb2.append(i2 == 0 ? 1 : 0);
            edit.putLong(sb2.toString(), 0);
            edit.apply();
            return 0;
        }
        SharedPreferences sharedPreferences = this.c;
        StringBuilder sb3 = new StringBuilder("uploaded_size_");
        sb3.append(i);
        sb3.append("_");
        sb3.append(i2);
        return sharedPreferences.getLong(sb3.toString(), 0);
    }

    public synchronized long a(int i, int i2, long j) {
        int i3 = this.c.getInt("last_upload_day_".concat(String.valueOf(i)), -1);
        this.d.setTimeInMillis(System.currentTimeMillis());
        int i4 = this.d.get(6);
        if (i4 != i3) {
            Editor edit = this.c.edit();
            edit.putInt("last_upload_day_".concat(String.valueOf(i)), i4);
            StringBuilder sb = new StringBuilder("uploaded_size_");
            sb.append(i);
            sb.append("_");
            sb.append(i2);
            edit.putLong(sb.toString(), j);
            StringBuilder sb2 = new StringBuilder("uploaded_size_");
            sb2.append(i);
            sb2.append("_");
            sb2.append(i2 == 0 ? 1 : 0);
            edit.putLong(sb2.toString(), 0);
            edit.apply();
            return j;
        }
        SharedPreferences sharedPreferences = this.c;
        StringBuilder sb3 = new StringBuilder("uploaded_size_");
        sb3.append(i);
        sb3.append("_");
        sb3.append(i2);
        long j2 = sharedPreferences.getLong(sb3.toString(), 0);
        Editor edit2 = this.c.edit();
        StringBuilder sb4 = new StringBuilder("uploaded_size_");
        sb4.append(i);
        sb4.append("_");
        sb4.append(i2);
        long j3 = j2 + j;
        edit2.putLong(sb4.toString(), j3).apply();
        return j3;
    }

    public synchronized DBProvider b() {
        try {
        }
        return this.b;
    }

    public synchronized String a(int i) {
        StringBuilder sb;
        try {
            sb = new StringBuilder();
            if (UpTunnel.sUseTestNet) {
                sb.append("http://aps.testing.amap.com/dataPipeline/uploadData");
            } else {
                sb.append("http://cgicol.amap.com/dataPipeline/uploadData");
            }
            sb.append("?");
            sb.append("channel=");
            sb.append(i == 1 ? "statistics" : "report");
            sb.append("&version=v1");
        }
        return sb.toString();
    }

    public synchronized int c() {
        try {
        }
        return h.a(this.a);
    }

    public static Uri b(int i) {
        switch (i) {
            case 1:
                return DBProvider.a((String) NewHtcHomeBadger.COUNT);
            case 2:
                return DBProvider.a((String) "event");
            case 3:
                return DBProvider.a((String) "key_log");
            case 4:
                return DBProvider.a((String) ReportManager.LOG_PATH);
            case 5:
                return DBProvider.a((String) "data_block");
            default:
                return null;
        }
    }

    public synchronized long d(int i) {
        Cursor a2;
        try {
            Uri b2 = b(i);
            if (i == 1) {
                return this.b.a(b2) * 24;
            }
            a2 = this.b.a(b2, new String[]{"sum(size)"}, null, null, null);
            if (a2 != null) {
                if (a2.moveToFirst()) {
                    long j = a2.getLong(0);
                    g.a(a2);
                    return j;
                }
            }
            g.a(a2);
            return -1;
        } catch (Throwable unused) {
        }
    }
}
