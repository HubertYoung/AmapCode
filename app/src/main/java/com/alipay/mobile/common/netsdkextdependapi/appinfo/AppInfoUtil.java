package com.alipay.mobile.common.netsdkextdependapi.appinfo;

public final class AppInfoUtil {
    public static final String getProductVersion() {
        return a().getProductVersion();
    }

    public static final String getTrackerID() {
        return a().getTrackerID();
    }

    public static final String getProductId() {
        return a().getProductId();
    }

    public static final String getReleaseType() {
        return a().getReleaseType();
    }

    public static final boolean isReleaseTypeDev() {
        return a().isReleaseTypeDev();
    }

    public static final boolean isReleaseTypeRC() {
        return a().isReleaseTypeRC();
    }

    public static final boolean isDebuggable() {
        return a().isDebuggable();
    }

    private static final AppInfoManager a() {
        return (AppInfoManager) AppInfoManagerFactory.getInstance().getDefaultBean();
    }
}
