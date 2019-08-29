package defpackage;

import java.io.File;
import java.io.FileFilter;

/* renamed from: bnn reason: default package */
/* compiled from: CpuUtils */
public final class bnn {
    private static final FileFilter a = new FileFilter() {
        public final boolean accept(File file) {
            String name = file.getName();
            if (!name.startsWith("cpu")) {
                return false;
            }
            for (int i = 3; i < name.length(); i++) {
                if (name.charAt(i) < '0' || name.charAt(i) > '9') {
                    return false;
                }
            }
            return true;
        }
    };

    public static int a() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(a).length;
        } catch (NullPointerException | SecurityException unused) {
            return -1;
        }
    }
}
