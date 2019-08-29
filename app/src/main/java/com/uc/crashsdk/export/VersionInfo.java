package com.uc.crashsdk.export;

/* compiled from: ProGuard */
public class VersionInfo {
    public String mBuildSeq = "150101000000";
    public String mSubVersion = "beta";
    public String mVersion = "1.0.0.0";

    public VersionInfo() {
    }

    public VersionInfo(VersionInfo versionInfo) {
        this.mVersion = versionInfo.mVersion;
        this.mSubVersion = versionInfo.mSubVersion;
        this.mBuildSeq = versionInfo.mBuildSeq;
    }
}
