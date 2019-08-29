package android.support.v4.content;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.io.File;

class ContextCompatApi21 {
    ContextCompatApi21() {
    }

    public static Drawable a(Context context, int i) {
        return context.getDrawable(i);
    }

    public static File a(Context context) {
        return context.getNoBackupFilesDir();
    }

    public static File b(Context context) {
        return context.getCodeCacheDir();
    }
}
