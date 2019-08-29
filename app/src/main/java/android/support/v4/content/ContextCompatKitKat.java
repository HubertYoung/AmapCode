package android.support.v4.content;

import android.content.Context;
import java.io.File;

class ContextCompatKitKat {
    ContextCompatKitKat() {
    }

    public static File[] a(Context context) {
        return context.getExternalCacheDirs();
    }

    public static File[] a(Context context, String str) {
        return context.getExternalFilesDirs(str);
    }

    public static File[] b(Context context) {
        return context.getObbDirs();
    }
}
