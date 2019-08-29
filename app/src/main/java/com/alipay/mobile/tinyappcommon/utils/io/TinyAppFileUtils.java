package com.alipay.mobile.tinyappcommon.utils.io;

import android.text.TextUtils;
import com.alipay.mobile.nebula.util.H5Log;
import java.io.File;
import java.nio.channels.FileLock;

public final class TinyAppFileUtils {
    private static final String TAG = "TinyAppFileUtils";

    public static boolean containsRelativeParentPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        if (path.startsWith("../") || path.endsWith("/..") || path.contains("/../")) {
            return true;
        }
        return false;
    }

    public static boolean deleteFile(File file, String checkableRoot) {
        if (file == null || !file.exists()) {
            return false;
        }
        try {
            String path = file.getCanonicalPath();
            if (TextUtils.equals(path, "/")) {
                return false;
            }
            try {
                String absPath = file.getAbsolutePath();
                if (TextUtils.equals(absPath, "/")) {
                    return false;
                }
                if (!TextUtils.isEmpty(checkableRoot)) {
                    try {
                        if (!absPath.startsWith(checkableRoot) && !path.startsWith(checkableRoot)) {
                            return false;
                        }
                    } catch (Exception e) {
                        H5Log.e((String) TAG, (Throwable) e);
                    }
                }
                return file.delete();
            } catch (Exception e2) {
                H5Log.e((String) TAG, (Throwable) e2);
                return false;
            }
        } catch (Exception e3) {
            H5Log.e((String) TAG, (Throwable) e3);
            return false;
        }
    }

    public static void releaseQuietly(FileLock lock) {
        if (lock != null) {
            try {
                lock.release();
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
    }
}
