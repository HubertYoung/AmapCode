package com.sina.weibo.sdk.share;

public interface WbShareCallback {
    void onWbShareCancel();

    void onWbShareFail();

    void onWbShareSuccess();
}
