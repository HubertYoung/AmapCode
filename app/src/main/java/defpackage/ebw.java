package defpackage;

import android.os.Environment;
import com.autonavi.common.model.POI;
import java.io.File;

/* renamed from: ebw reason: default package */
/* compiled from: FootNaviDataResult */
public final class ebw {
    public POI a;
    public POI b;

    public static String a(String str) {
        String str2 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append("navishare");
            File file = new File(externalStorageDirectory, sb.toString());
            if (!file.exists() && !file.mkdir()) {
                return null;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/");
            sb2.append(str);
            str2 = sb2.toString();
        }
        return str2;
    }
}
