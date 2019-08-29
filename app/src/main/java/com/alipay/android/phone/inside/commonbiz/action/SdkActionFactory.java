package com.alipay.android.phone.inside.commonbiz.action;

import java.util.HashMap;

public class SdkActionFactory {
    private static final HashMap<String, SdkAction> a = new HashMap<>();

    public static void a(SdkAction sdkAction) {
        a.put(sdkAction.a(), sdkAction);
    }

    public static SdkAction a(String str) {
        return a.get(str);
    }
}
