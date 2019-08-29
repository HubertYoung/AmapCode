package com.sina.weibo.sdk.network;

public interface RequestCancelable {
    void cancelRequest();

    boolean isCancelRequest();
}
