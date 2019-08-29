package defpackage;

import com.amap.bundle.blutils.PathManager;
import java.io.File;

/* renamed from: afa reason: default package */
/* compiled from: PathUtil */
public final class afa {
    public static String a() {
        String b = PathManager.a().b();
        if (b == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append("/autonavi/data/poiv5/");
        final String sb2 = sb.toString();
        ahm.a(new Runnable() {
            public final void run() {
                File file = new File(sb2);
                if (!file.exists() || !file.isDirectory()) {
                    file.mkdirs();
                }
            }
        });
        return sb2;
    }
}
