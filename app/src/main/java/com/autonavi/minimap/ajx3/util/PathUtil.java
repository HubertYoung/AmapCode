package com.autonavi.minimap.ajx3.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.image.PictureParams;

public class PathUtil {
    private static String APP_PATH = null;
    private static final String DOMAIN_APP = "app://";
    private static final String DOMAIN_FILE = "file://";
    private static final String DOMAIN_SDCARD = "sdcard://";
    private static String SDCARD_PATH;
    private static boolean isInitialed;

    public static String processPath(@NonNull IAjxContext iAjxContext, @NonNull String str) {
        if (!initIfNeeded(iAjxContext.getNativeContext())) {
            return null;
        }
        String processPathInternal = processPathInternal(str);
        if (processPathInternal == null) {
            return null;
        }
        return Ajx.getInstance().lookupLoader(processPathInternal).processingPath(PictureParams.make(iAjxContext, processPathInternal, false));
    }

    private static boolean initIfNeeded(Context context) {
        if (!isInitialed && context != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(FileUtil.getExternalStorageDirectory());
            sb.append("/");
            SDCARD_PATH = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(context.getFilesDir().getPath());
            sb2.append("/");
            APP_PATH = sb2.toString();
            isInitialed = true;
        }
        return true;
    }

    private static String processPathInternal(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(DOMAIN_SDCARD)) {
            StringBuilder sb = new StringBuilder("file://");
            sb.append(SDCARD_PATH);
            sb.append(str.substring(9));
            return sb.toString();
        } else if (!str.startsWith(DOMAIN_APP)) {
            return str;
        } else {
            StringBuilder sb2 = new StringBuilder("file://");
            sb2.append(APP_PATH);
            sb2.append(str.substring(6));
            return sb2.toString();
        }
    }
}
