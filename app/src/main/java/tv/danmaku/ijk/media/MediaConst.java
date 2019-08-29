package tv.danmaku.ijk.media;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MediaConst {
    public static final int CAMERA_TYPE_BEAUTY = 2;
    public static final int CAMERA_TYPE_GLES = 3;
    public static final int CAMERA_TYPE_NORMAL = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CameraType {
    }

    public static boolean camereTypeIn(int obj, int... srcs) {
        if (srcs == null || srcs.length <= 0) {
            return false;
        }
        for (int i : srcs) {
            if (obj == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBeauty(int obj) {
        return obj == 2;
    }
}
