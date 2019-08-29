package com.alipay.mobile.beehive.photo.util;

import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class PhotoConfig {
    private static final String GRID_SIZE = "beehive_photo_grid_size";
    private static final String IN_EDIT_SHOW = "beehive_photo_inEdit_isShow";
    private static final String TAG = PhotoConfig.class.getSimpleName();
    private static PhotoConfig mInstance;
    private ConfigService configService = ((ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ConfigService.class.getName()));

    public static synchronized PhotoConfig getInstance() {
        PhotoConfig photoConfig;
        synchronized (PhotoConfig.class) {
            try {
                if (mInstance == null) {
                    mInstance = new PhotoConfig();
                }
                photoConfig = mInstance;
            }
        }
        return photoConfig;
    }

    private PhotoConfig() {
    }

    public int getGridSizeConfig() {
        String string_size = this.configService.getConfig(GRID_SIZE);
        if (TextUtils.isEmpty(string_size)) {
            return 0;
        }
        PhotoLogger.info(TAG, "grid size is " + string_size);
        return Integer.valueOf(this.configService.getConfig(GRID_SIZE)).intValue();
    }

    public boolean getShowInEditConfig() {
        if (TextUtils.equals(this.configService.getConfig(IN_EDIT_SHOW), "false")) {
            return false;
        }
        return true;
    }
}
