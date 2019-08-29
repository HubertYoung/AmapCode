package com.alipay.mobile.common.netsdkextdependapi.appinfo;

public class AppInfoManagerAdapter implements AppInfoManager {
    public String getProductVersion() {
        return "UnknownProductVersion";
    }

    public String getTrackerID() {
        return "UnknowTrackerID";
    }

    public String getProductId() {
        return "UnknowProductId";
    }

    public String getReleaseType() {
        return "UnknowReleaseType";
    }

    public boolean isReleaseTypeDev() {
        return false;
    }

    public boolean isReleaseTypeRC() {
        return false;
    }

    public boolean isDebuggable() {
        return false;
    }
}
