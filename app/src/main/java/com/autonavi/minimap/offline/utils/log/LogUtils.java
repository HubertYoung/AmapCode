package com.autonavi.minimap.offline.utils.log;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public class LogUtils {
    public static final String DATA_PATH = "offline";
    private static final Logger log = Logger.getLogger("LogUtils");

    public static String getLogPath(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append(File.separator);
            sb.append("offline");
            sb.append(File.separator);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(context.getFilesDir().getAbsolutePath());
        sb2.append(File.separator);
        sb2.append("offline");
        sb2.append(File.separator);
        return sb2.toString();
    }

    public static boolean isExistSd() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isExistFile(String str) {
        if (!isExistSd()) {
            return false;
        }
        return new File(str).exists();
    }
}
