package com.mpaas.nebula.adapter.api;

public class MPNebulaOfflineInfo {
    public String appId;
    public String offLineFileName;
    public String version;

    public MPNebulaOfflineInfo() {
    }

    public MPNebulaOfflineInfo(String offLineFileName2, String appId2, String version2) {
        this.offLineFileName = offLineFileName2;
        this.appId = appId2;
        this.version = version2;
    }
}
