package defpackage;

import android.os.Build.VERSION;
import com.autonavi.amap.app.AMapAppGlobal;

/* renamed from: cyd reason: default package */
/* compiled from: Config */
public final class cyd {

    /* renamed from: cyd$a */
    /* compiled from: Config */
    public static class a {
        public static final String a;

        static {
            String str;
            if (VERSION.SDK_INT >= 17) {
                StringBuilder sb = new StringBuilder();
                sb.append(AMapAppGlobal.getApplication().getApplicationContext().getApplicationInfo().dataDir);
                sb.append("/databases/");
                str = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder("/data/data/");
                sb2.append(AMapAppGlobal.getApplication().getApplicationContext().getPackageName());
                sb2.append("/databases/");
                str = sb2.toString();
            }
            a = str;
        }
    }
}
