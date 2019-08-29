package com.alipay.mobile.common.logging.api;

public interface LogDAUTracker {
    public static final String BIZ_TYPE = "DAU";

    void clearExpireData();

    boolean isUploadedToday(String str);

    void updateSpmUploadState(String str);
}
