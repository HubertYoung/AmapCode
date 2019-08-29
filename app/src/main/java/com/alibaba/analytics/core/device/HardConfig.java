package com.alibaba.analytics.core.device;

import android.content.Context;
import com.alibaba.analytics.utils.StringUtils;

public class HardConfig {
    private PersistentConfiguration commonPersistentConfigWR = null;
    private String configDir = null;
    private Context context = null;
    private String resourceIdentifier = null;

    public HardConfig(Context context2, String str, String str2) {
        this.context = context2;
        this.resourceIdentifier = str;
        this.configDir = str2;
    }

    public void release() {
        this.commonPersistentConfigWR = null;
    }

    public static PersistentConfiguration getDevicePersistentConfig(Context context2) {
        if (context2 == null) {
            return null;
        }
        PersistentConfiguration persistentConfiguration = new PersistentConfiguration(context2, Constants.GLOBAL_PERSISTENT_CONFIG_DIR, "Alvin3", false, true);
        return persistentConfiguration;
    }

    public static PersistentConfiguration getNewDevicePersistentConfig(Context context2) {
        if (context2 == null) {
            return null;
        }
        PersistentConfiguration persistentConfiguration = new PersistentConfiguration(context2, Constants.GLOBAL_PERSISTENT_CONFIG_DIR, "UTCommon", false, true);
        return persistentConfiguration;
    }

    public PersistentConfiguration getCommonPersistentConfig() {
        PersistentConfiguration persistentConfiguration = this.commonPersistentConfigWR != null ? this.commonPersistentConfigWR : null;
        if (persistentConfiguration != null) {
            return persistentConfiguration;
        }
        if (this.context == null || StringUtils.isEmpty(this.configDir)) {
            return null;
        }
        PersistentConfiguration persistentConfiguration2 = new PersistentConfiguration(this.context, this.configDir, "UTCommon", false, false);
        this.commonPersistentConfigWR = persistentConfiguration2;
        return persistentConfiguration2;
    }
}
