package com.alipay.mobile.security.bio.service;

public abstract class BioUploadService extends BioService {
    public static final int FAIL = 2001;
    public static final int FAIL_RETRY = 2002;
    public static final int NETWORK_ERROR = 3001;
    public static final int NETWORK_NULL_ERROR = 3002;
    public static final int QUEUE_FULL_ERROR = 4001;
    public static final int SUCC = 1001;
    public static final int SUCC_CONTINUE = 1002;

    public abstract void addCallBack(BioUploadCallBack bioUploadCallBack);

    public abstract void clearUp();

    public abstract boolean isFulled();

    public abstract void setZimId(String str);

    public abstract int upload(BioUploadItem bioUploadItem);
}
