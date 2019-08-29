package com.alipay.mobile.common.netsdkextdependapi.appinfo;

public interface AppInfoManager {
    String getProductId();

    String getProductVersion();

    String getReleaseType();

    String getTrackerID();

    boolean isDebuggable();

    boolean isReleaseTypeDev();

    boolean isReleaseTypeRC();
}
