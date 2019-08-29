package com.alibaba.wireless.security.open.securityguardaccsadapter;

import android.content.Context;
import com.taobao.orange.OrangeConfig;

public class OrangeAdapter {
    private static final boolean DEBUG = false;
    private static final String SECURITYGUARD_ORANGE_NAMESPACE = "securityguard_orange_namespace";
    private static final String TAG = "OrangeAdapter";
    public static Context gContext;
    private static String[] mNameSpaces = {SECURITYGUARD_ORANGE_NAMESPACE};

    public static void setOrangeNameSpaces(String[] strArr) {
        mNameSpaces = strArr;
    }

    public static void registerListener(Context context) {
        gContext = context;
        OrangeConfig.getInstance().registerListener(mNameSpaces, new OrangeListener(), false);
    }
}
