package defpackage;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.io.File;

/* renamed from: ahk reason: default package */
/* compiled from: AppUtil */
public final class ahk {
    public static File a(Context context) {
        File filesDir = context.getFilesDir();
        if (filesDir == null) {
            filesDir = context.getDir(AutoJsonUtils.JSON_FILES, 0);
        }
        if (filesDir == null) {
            StringBuilder sb = new StringBuilder("/data/data/");
            sb.append(context.getPackageName());
            sb.append("/app_files");
            filesDir = new File(sb.toString());
        }
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        return filesDir;
    }

    public static PackageInfo b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception unused) {
            return null;
        }
    }
}
