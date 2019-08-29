package defpackage;

import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.List;

/* renamed from: cra reason: default package */
/* compiled from: SaveSyncAutoDataController */
public class cra {
    private static cra a;

    /* renamed from: cra$a */
    /* compiled from: SaveSyncAutoDataController */
    public interface a {
        void a(String str, String str2);
    }

    private cra() {
    }

    public static cra a() {
        cra cra;
        synchronized (cra.class) {
            try {
                if (a == null) {
                    a = new cra();
                }
                cra = a;
            }
        }
        return cra;
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        List<PackageInfo> installedPackages = AMapAppGlobal.getApplication().getPackageManager().getInstalledPackages(0);
        if (installedPackages == null || installedPackages.size() == 0) {
            return false;
        }
        for (PackageInfo next : installedPackages) {
            if (next != null && TextUtils.equals(str, next.packageName)) {
                return true;
            }
        }
        return false;
    }

    public static void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("save_auto_preference", 0).edit();
            edit.putString("auto_id_key", str);
            edit.apply();
        }
    }
}
