package android.support.v4.content;

import android.content.Context;
import java.io.File;

class ContextCompatFroyo {
    ContextCompatFroyo() {
    }

    public static File a(Context context) {
        return context.getExternalCacheDir();
    }

    public static File a(Context context, String str) {
        return context.getExternalFilesDir(str);
    }
}
