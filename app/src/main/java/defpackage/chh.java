package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.io.File;

/* renamed from: chh reason: default package */
/* compiled from: Persister */
public final class chh {
    public static void a(String str) {
        new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).putStringValue("afp_splash_events", str);
    }

    public static String a() {
        return new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).getStringValue("afp_splash_events", "");
    }

    public static void b() {
        String absolutePath = AMapAppGlobal.getApplication().getExternalFilesDir(BaseIntentDispatcher.INTENT_CALL_SPLASH).getAbsolutePath();
        if (!TextUtils.isEmpty(absolutePath)) {
            File file = new File(absolutePath);
            if (file.exists()) {
                if (!file.isDirectory()) {
                    file.delete();
                    return;
                }
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    for (File file2 : listFiles) {
                        if (System.currentTimeMillis() - file2.lastModified() > FileCache.EXPIRE_TIME) {
                            file2.delete();
                        }
                    }
                }
            }
        }
    }
}
