package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import java.lang.reflect.InvocationTargetException;

/* renamed from: bqe reason: default package */
/* compiled from: DexRunControl */
public class bqe {
    private static bqe c;
    public final String a;
    public final String b;

    private bqe() {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getFilesDir());
        sb.append("/dex/run");
        this.a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(FileUtil.getFilesDir());
        sb2.append("/dex/run/ready");
        this.b = sb2.toString();
    }

    public static bqe a() {
        if (c == null) {
            synchronized (bqe.class) {
                if (c == null) {
                    c = new bqe();
                }
            }
        }
        return c;
    }

    public static boolean a(Class cls) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String str;
        try {
            Context applicationContext = AMapAppGlobal.getApplication().getApplicationContext();
            if (applicationContext == null) {
                return false;
            }
            str = ahk.b(applicationContext).versionName;
            if (cls == null) {
                return false;
            }
            String str2 = (String) cls.getDeclaredMethod("getRunVersion", new Class[0]).invoke(cls.newInstance(), new Object[0]);
            if (TextUtils.isEmpty(str2) || !str2.equalsIgnoreCase(str)) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            str = "";
        }
    }
}
