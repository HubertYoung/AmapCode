package defpackage;

import android.content.Context;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;

/* renamed from: dbq reason: default package */
/* compiled from: PushConfig */
public final class dbq {
    public static boolean a() {
        return bim.aa().k((String) UploadConstants.STATUS_PUSH_RECEIVED);
    }

    public static void a(Context context, int i) {
        context.getSharedPreferences("sp_push", 4).edit().putInt("last_versioncode", i).apply();
    }

    public static void a(Context context, String str) {
        context.getSharedPreferences("sp_push", 4).edit().putString("last_versionname", str).apply();
    }

    public static void a(Context context) {
        context.getSharedPreferences("sp_push", 4).edit().putLong("last_time", System.currentTimeMillis()).apply();
    }
}
