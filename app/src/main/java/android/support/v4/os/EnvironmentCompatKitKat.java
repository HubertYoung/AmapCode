package android.support.v4.os;

import android.os.Environment;
import java.io.File;

class EnvironmentCompatKitKat {
    EnvironmentCompatKitKat() {
    }

    public static String a(File file) {
        return Environment.getStorageState(file);
    }
}
