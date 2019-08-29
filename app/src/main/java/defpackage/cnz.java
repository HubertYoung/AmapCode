package defpackage;

import android.text.TextUtils;
import android.util.Log;
import java.io.File;

/* renamed from: cnz reason: default package */
/* compiled from: FileUtil */
public final class cnz {
    public static boolean a() {
        try {
            File file = new File("/data/data/com.autonavi.minimap/files/useajx.txt");
            if (!file.exists()) {
                return true;
            }
            return file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            new StringBuilder("--FileUtil.rmAjxTip :").append(Log.getStackTraceString(e));
            return false;
        }
    }

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        if (TextUtils.isEmpty(str2)) {
            return str;
        }
        char charAt = str.charAt(str.length() - 1);
        char charAt2 = str2.charAt(0);
        if (charAt == File.separatorChar && charAt2 == File.separatorChar) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str2.substring(1));
            return sb.toString();
        } else if (charAt == File.separatorChar || charAt2 == File.separatorChar) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(str2);
            return sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(File.separatorChar);
            sb3.append(str2);
            return sb3.toString();
        }
    }
}
