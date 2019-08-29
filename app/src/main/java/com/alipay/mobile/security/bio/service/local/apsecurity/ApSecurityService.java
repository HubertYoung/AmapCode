package com.alipay.mobile.security.bio.service.local.apsecurity;

import android.content.Context;
import com.alipay.mobile.security.bio.service.local.LocalService;

public abstract class ApSecurityService extends LocalService {
    public static final String ACTION_APDIDTOKEN = "com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService.ApdidToken";
    public static String sApdidToken = "";

    public abstract String getApDidToken();

    public abstract void init(Context context);

    public static String getStaticApDidToken() {
        return sApdidToken;
    }

    public static void setStaticApDidToken(String str) {
        sApdidToken = str;
    }
}
