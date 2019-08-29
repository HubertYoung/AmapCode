package com.alipay.mobile.common.nbnet.api;

public interface NBNetConfigHelper {
    void disableMockDownloadServerLimitedMode();

    void disableMockUploadServerLimitedMode();

    void enableMockDownloadServerLimitedMode();

    void enableMockUploadServerLimitedMode();

    boolean isMockingDownloadServerLimitedMode();

    boolean isMockingUploadServerLimitedMode();
}
