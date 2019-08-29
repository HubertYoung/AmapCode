package defpackage;

import android.content.Context;
import android.os.Environment;
import com.alipay.android.phone.a.a.a;
import java.io.File;

/* renamed from: etw reason: default package */
/* compiled from: StorageUtils */
final class etw {
    static File a(Context context) {
        String str;
        try {
            str = Environment.getExternalStorageState();
        } catch (NullPointerException unused) {
            str = "";
        }
        File file = null;
        if ("mounted".equals(str)) {
            File file2 = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), a.a), "data"), context.getPackageName()), "cache");
            if (file2.exists() || file2.mkdirs()) {
                file = file2;
            }
        }
        if (file == null) {
            file = context.getCacheDir();
        }
        if (file != null) {
            return file;
        }
        StringBuilder sb = new StringBuilder("/data/data/");
        sb.append(context.getPackageName());
        sb.append("/cache/");
        return new File(sb.toString());
    }
}
