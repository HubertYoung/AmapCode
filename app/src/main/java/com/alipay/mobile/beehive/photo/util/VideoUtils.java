package com.alipay.mobile.beehive.photo.util;

import android.text.TextUtils;
import com.alipay.mobile.beehive.photo.data.VideoEditInfo;
import java.io.File;

public class VideoUtils {
    public static final String BIZ_BEEHIVE_VIDEO_EDIT = "";
    public static String FILE_SCHEME = "file://";

    public static String getVideoAbsPath(VideoEditInfo videoEditInfo) {
        String ret = videoEditInfo.path;
        if (TextUtils.isEmpty(ret) || !ret.startsWith(FILE_SCHEME)) {
            return ret;
        }
        return ret.substring(FILE_SCHEME.length());
    }

    public static String addFileScheme(String absPath) {
        String ret = absPath;
        if (absPath.startsWith(File.separator)) {
            return FILE_SCHEME + absPath;
        }
        return ret;
    }

    public static int[] getWidthAndHeightConsiderRotation(int w, int h, int rotation) {
        int[] ret = {w, h};
        if (rotation == 90 || rotation == 270) {
            ret[0] = h;
            ret[1] = w;
        }
        return ret;
    }

    public static boolean isLocalFile(String path) {
        return !TextUtils.isEmpty(path) && (path.startsWith(FILE_SCHEME) || path.startsWith(File.separator));
    }
}
