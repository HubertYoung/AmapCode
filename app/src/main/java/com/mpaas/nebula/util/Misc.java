package com.mpaas.nebula.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class Misc {
    public static final String NEBULA_BIZ = "com-mpaas-nebula-adapter-mpaasnebulaadapter";

    public static String getStringValueFromMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (!(ai == null || ai.metaData == null)) {
                return ai.metaData.getString(key);
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) "MpassNebulaMisc", (Throwable) e);
        }
        return null;
    }
}
