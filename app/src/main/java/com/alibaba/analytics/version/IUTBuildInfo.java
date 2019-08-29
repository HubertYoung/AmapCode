package com.alibaba.analytics.version;

public interface IUTBuildInfo {
    String getBuildID();

    String getFullSDKVersion();

    String getGitCommitID();

    String getShortSDKVersion();

    boolean isTestMode();
}
