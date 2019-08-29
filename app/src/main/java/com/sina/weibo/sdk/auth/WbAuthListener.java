package com.sina.weibo.sdk.auth;

public interface WbAuthListener {
    void cancel();

    void onFailure(WbConnectErrorMessage wbConnectErrorMessage);

    void onSuccess(Oauth2AccessToken oauth2AccessToken);
}
