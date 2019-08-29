package defpackage;

import android.location.Location;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.server.aos.serverkey;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

/* renamed from: ku reason: default package */
/* compiled from: AELogUtil */
public final class ku {
    public static final Executor a = new ahn(1);
    private static final Executor d = new ahn(1);
    public Timer b;
    public TimerTask c;
    private String e;
    /* access modifiers changed from: private */
    public SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    /* renamed from: ku$a */
    /* compiled from: AELogUtil */
    static class a {
        /* access modifiers changed from: private */
        public static ku a = new ku();
    }

    public static ku a() {
        return a.a;
    }

    /* access modifiers changed from: private */
    public String a(String str) {
        if (TextUtils.isEmpty(this.e)) {
            this.e = PathManager.a().a(DirType.LOG);
        }
        if (this.e != null && this.e.contains("autonavi/log")) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().toString());
            sb.append("/");
            sb.append(this.e.substring(this.e.indexOf("autonavi/log")));
            this.e = sb.toString();
        }
        try {
            File file = new File(this.e);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception unused) {
        }
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.e);
            sb2.append("/");
            sb2.append(str);
            sb2.append(".txt");
            return sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder("client-");
        sb3.append(this.f.format(Long.valueOf(System.currentTimeMillis())));
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(this.e);
        sb5.append("/");
        sb5.append(sb4);
        sb5.append(".txt");
        return sb5.toString();
    }

    public final void a(String str, Location location) {
        if (bno.a && location != null) {
            a(str, location.toString());
        }
    }

    public final void a(final String str, final String str2) {
        if (bno.a) {
            d.execute(new Runnable() {
                public final void run() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ku.b());
                    sb.append("::");
                    sb.append(str);
                    sb.append("|  ");
                    sb.append(str2);
                    sb.append("\n");
                    FileUtil.writeStrToFileByAppend(ku.this.a((String) ""), sb.toString());
                }
            });
        }
    }

    public final void b(final String str, final String str2) {
        boolean z = !new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("TAXI_LOG_SWITCH", false);
        if (bno.a || !z) {
            a.execute(new Runnable() {
                public final void run() {
                    StringBuilder sb = new StringBuilder("taxi2_");
                    sb.append(str);
                    sb.append("_");
                    sb.append(ku.this.f.format(Long.valueOf(System.currentTimeMillis())));
                    String sb2 = sb.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(ku.b());
                    sb3.append(" : ");
                    sb3.append(str2);
                    String sb4 = sb3.toString();
                    String a2 = ku.this.a(sb2);
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(serverkey.amapEncode(sb4));
                    sb5.append("\n");
                    FileUtil.writeStrToFileByAppend(a2, sb5.toString());
                }
            });
        }
    }

    public final void c(final String str, final String str2) {
        if (bno.a) {
            a.execute(new Runnable() {
                public final void run() {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ku.b());
                    sb.append("::");
                    sb.append(str);
                    sb.append("|  ");
                    sb.append(str2);
                    sb.append("\n");
                    FileUtil.writeStrToFileByAppend(ku.this.a(str), sb.toString());
                }
            });
        }
    }

    public static String b() {
        return lf.a(new Date(System.currentTimeMillis()));
    }
}
