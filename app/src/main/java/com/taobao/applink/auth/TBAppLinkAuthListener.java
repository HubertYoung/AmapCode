package com.taobao.applink.auth;

import com.taobao.applink.auth.userinfo.TBAppLinkUserInfo;

public interface TBAppLinkAuthListener {
    void onFailure();

    void onSuccess(TBAppLinkUserInfo tBAppLinkUserInfo);
}
