package defpackage;

import com.alipay.mobile.h5container.api.H5PageData;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.io.File;

/* renamed from: dea reason: default package */
/* compiled from: Downloader */
public class dea {
    public static String a;
    private static volatile dea b;

    private dea() {
        a = AMapAppGlobal.getApplication().getExternalFilesDir(BaseIntentDispatcher.INTENT_CALL_SPLASH).getAbsolutePath();
    }

    public static dea a() {
        if (b == null) {
            synchronized (dea.class) {
                try {
                    if (b == null) {
                        b = new dea();
                    }
                }
            }
        }
        return b;
    }

    public static void a(chi chi, String str, String str2, File file, File file2, bjf bjf) {
        if (file.exists()) {
            file.setLastModified(System.currentTimeMillis());
            return;
        }
        String absolutePath = file2.getAbsolutePath();
        bjh.a().a(absolutePath);
        bjg bjg = new bjg(absolutePath);
        bjg.setUrl(str2);
        bjh.a().a(bjg, bjf);
        emd.a(chi, str, H5PageData.KEY_UC_START);
    }
}
