package com.alipay.mobile.nebula.provider;

import java.util.Map;

public interface H5TinyDebugModeProvider {
    void addRecentAppForDebugMode(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    Map<String, String> getAppDebugModeAndVersion(String str);
}
