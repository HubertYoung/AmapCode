package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import com.ali.user.mobile.accountbiz.extservice.ServerConfigService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.accountbiz.sp.AUSharedPreferences;
import com.ali.user.mobile.accountbiz.sp.SharedPreferencesManager;
import com.ali.user.mobile.log.AliUserLog;

public class ServerConfigServiceImpl extends BaseExtService implements ServerConfigService {
    private static final String GROUP_ID = "server_config";
    private static ServerConfigService msConfigService;

    private ServerConfigServiceImpl(Context context) {
        super(context);
    }

    public static ServerConfigService getInstance(Context context) {
        if (msConfigService == null) {
            synchronized (ServerConfigServiceImpl.class) {
                try {
                    if (msConfigService == null) {
                        msConfigService = new ServerConfigServiceImpl(context);
                    }
                }
            }
        }
        return msConfigService;
    }

    public String getConfig(String str) {
        String a = SharedPreferencesManager.a(this.mContext, GROUP_ID, 0).a(str, "");
        StringBuilder sb = new StringBuilder("key=");
        sb.append(str);
        sb.append(",value=");
        sb.append(a);
        AliUserLog.c("getconfig-Config-Server", sb.toString());
        return a;
    }

    public void putConfig(String str, String str2) {
        StringBuilder sb = new StringBuilder("key=");
        sb.append(str);
        sb.append(",value=");
        sb.append(str2);
        AliUserLog.c("putConfig-Config-Server", sb.toString());
        AUSharedPreferences a = SharedPreferencesManager.a(this.mContext, GROUP_ID, 0);
        a.b(str, str2);
        a.b();
    }
}
