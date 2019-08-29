package com.alipay.mobile.tinyappcustom.h5plugin.navigation;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MiniAppConfig {
    private static final String a = MiniAppConfig.class.getSimpleName();
    private boolean b;
    private boolean c;
    private List<String> d;

    private static class MiniAppConfigInner {
        public static MiniAppConfig sInstance = new MiniAppConfig(0);

        private MiniAppConfigInner() {
        }
    }

    /* synthetic */ MiniAppConfig(byte b2) {
        this();
    }

    public static MiniAppConfig getInstance() {
        return MiniAppConfigInner.sInstance;
    }

    private MiniAppConfig() {
        this.b = false;
        this.c = true;
        this.d = new ArrayList();
        ConfigService configService = (ConfigService) H5Utils.findServiceByInterface(ConfigService.class.getName());
        H5Log.d(a, "configService = " + configService);
        if (configService != null) {
            a(configService.getConfig("ta_navigate_app_debug"));
            String configValue = configService.getConfig("ta_cfg");
            if (!TextUtils.isEmpty(configValue)) {
                JSONObject value = JSON.parseObject(configValue);
                b(value.getString("antns"));
                c(value.getString("ntmpw"));
            }
        }
    }

    private void a(String navigateAppDebug) {
        if (!TextUtils.isEmpty(navigateAppDebug)) {
            this.b = "1".equals(navigateAppDebug);
        }
    }

    private void b(String value) {
        if (TextUtils.isEmpty(value)) {
            H5Log.d(a, "initAllowedNaviToNonSubjectMiniProgram..value is empty");
        } else {
            this.c = "1".equals(value);
        }
    }

    private void c(String value) {
        try {
            H5Log.d(a, "initNaviToMiniProgramWhitelist..value=" + value);
            if (TextUtils.isEmpty(value)) {
                H5Log.d(a, "initNaviToMiniProgramWhitelist..value is empty");
                return;
            }
            String[] whitelist = value.split(",");
            if (whitelist.length <= 0) {
                H5Log.d(a, "initNaviToMiniProgramWhitelist..list is empty");
            } else {
                this.d = Arrays.asList(whitelist);
            }
        } catch (Throwable e) {
            H5Log.e(a, "initNaviToMiniProgramWhitelist..e=" + e);
        }
    }

    public boolean isNavigateAppDebug() {
        return this.b;
    }

    public boolean allowedNaviToNonSubjectMiniProgram() {
        return this.c;
    }

    public List<String> getNaviToMiniProgramWhitelist() {
        return this.d;
    }
}
