package com.ali.user.mobile.resolver;

import android.content.Context;
import com.ali.user.mobile.accountbiz.extservice.ServerConfigService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;

public class ConfigResolver {
    private static IConfigResolver a;

    private static IConfigResolver a(final Context context) {
        if (a == null) {
            synchronized (IConfigResolver.class) {
                if (a == null) {
                    a = new IConfigResolver() {
                        public final String a(String str) {
                            ServerConfigService configService = AntExtServiceManager.getConfigService(context);
                            if (configService == null) {
                                return "";
                            }
                            return configService.getConfig(str);
                        }
                    };
                }
            }
        }
        return a;
    }

    public static String a(Context context, String str) {
        if (a(context) == null) {
            return "";
        }
        return a(context).a(str);
    }
}
