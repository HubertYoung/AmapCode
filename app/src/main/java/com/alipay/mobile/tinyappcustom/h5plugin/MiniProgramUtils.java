package com.alipay.mobile.tinyappcustom.h5plugin;

import android.net.Uri;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.SchemeService;

public class MiniProgramUtils {
    public static int goToSchemeService(Uri uri) {
        SchemeService schemeService = (SchemeService) findServiceByInterface(SchemeService.class.getName());
        if (schemeService != null) {
            return schemeService.process(uri);
        }
        return 0;
    }

    public static <T> T findServiceByInterface(String name) {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(name);
    }
}
