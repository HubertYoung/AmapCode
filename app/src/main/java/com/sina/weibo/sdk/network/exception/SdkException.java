package com.sina.weibo.sdk.network.exception;

public class SdkException extends Exception {
    public SdkException(String str) {
        super(str);
    }

    public SdkException(Throwable th) {
        super(th);
    }
}
