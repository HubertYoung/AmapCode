package com.sina.weibo.sdk.network.intercept;

import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.network.IRequestIntercept;
import com.sina.weibo.sdk.network.IRequestParam;
import com.sina.weibo.sdk.network.base.WbUserInfo;
import com.sina.weibo.sdk.network.base.WbUserInfoHelper;
import com.sina.weibo.sdk.network.exception.InterceptException;
import com.sina.weibo.wcfc.sobusiness.UtilitySo;

public class GuestParamInterception implements IRequestIntercept {
    public boolean needIntercept(IRequestParam iRequestParam, Bundle bundle) {
        String url = iRequestParam.getUrl();
        return TextUtils.isEmpty(url) || (!url.startsWith("https://api.weibo.cn/2/sdk/login") && !url.startsWith("http://api.weibo.cn/2/sdk/login"));
    }

    public boolean doIntercept(IRequestParam iRequestParam, Bundle bundle) throws InterceptException {
        WbUserInfo userInfo = WbUserInfoHelper.getInstance().getUserInfo(iRequestParam.getContext());
        if (userInfo != null) {
            bundle.putString("gsid", userInfo.getGsid());
            bundle.putString(Oauth2AccessToken.KEY_UID, userInfo.getUid());
            bundle.putString("s", UtilitySo.getInstance().calculateS(iRequestParam.getContext(), userInfo.getUid()));
        }
        return false;
    }
}
