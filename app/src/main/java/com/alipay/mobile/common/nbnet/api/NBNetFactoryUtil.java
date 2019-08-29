package com.alipay.mobile.common.nbnet.api;

public final class NBNetFactoryUtil {
    public static final void enableMockUploadServerLimitedMode() {
        NBNetFactory.getDefault().getNBNetConfigHelper().enableMockUploadServerLimitedMode();
    }

    public static final void enableMockDownloadServerLimitedMode() {
        NBNetFactory.getDefault().getNBNetConfigHelper().enableMockDownloadServerLimitedMode();
    }

    public static final void disableMockUploadServerLimitedMode() {
        NBNetFactory.getDefault().getNBNetConfigHelper().disableMockUploadServerLimitedMode();
    }

    public static final void disableMockDownloadServerLimitedMode() {
        NBNetFactory.getDefault().getNBNetConfigHelper().disableMockDownloadServerLimitedMode();
    }

    public static final boolean isMockingUploadServerLimitedMode() {
        return NBNetFactory.getDefault().getNBNetConfigHelper().isMockingUploadServerLimitedMode();
    }

    public static final boolean isMockingDownloadServerLimitedMode() {
        return NBNetFactory.getDefault().getNBNetConfigHelper().isMockingDownloadServerLimitedMode();
    }
}
