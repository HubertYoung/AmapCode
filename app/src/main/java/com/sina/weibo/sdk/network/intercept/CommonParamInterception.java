package com.sina.weibo.sdk.network.intercept;

import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.HttpManager;
import com.sina.weibo.sdk.network.IRequestIntercept;
import com.sina.weibo.sdk.network.IRequestParam;
import com.sina.weibo.sdk.network.IRequestParam.RequestType;
import com.sina.weibo.sdk.network.exception.InterceptException;
import com.sina.weibo.sdk.sso.WeiboSsoManager;
import com.sina.weibo.sdk.utils.LogUtil;

public class CommonParamInterception implements IRequestIntercept {
    public static String aidInfo;
    private static String appKey;

    public boolean needIntercept(IRequestParam iRequestParam, Bundle bundle) {
        return true;
    }

    public static void setAppKey(String str) {
        appKey = str;
    }

    public boolean doIntercept(IRequestParam iRequestParam, Bundle bundle) throws InterceptException {
        Bundle bundle2;
        if (TextUtils.isEmpty(aidInfo)) {
            aidInfo = WeiboSsoManager.getInstance().getAid(iRequestParam.getContext(), appKey);
        }
        if (TextUtils.isEmpty(aidInfo)) {
            throw new InterceptException("aid get error");
        }
        if (!TextUtils.isEmpty(aidInfo)) {
            bundle.putString("aid", aidInfo);
        }
        if (iRequestParam.getMethod() == RequestType.GET) {
            bundle2 = iRequestParam.getGetBundle();
        } else {
            bundle2 = iRequestParam.getPostBundle();
        }
        String str = "";
        Object obj = bundle2.get("access_token");
        Object obj2 = bundle2.get(Oauth2AccessToken.KEY_REFRESH_TOKEN);
        Object obj3 = bundle2.get("phone");
        if (obj != null && (obj instanceof String)) {
            str = (String) obj;
        } else if (obj2 != null && (obj2 instanceof String)) {
            str = (String) obj2;
        } else if (obj3 != null && (obj3 instanceof String)) {
            str = (String) obj3;
        }
        String timestamp = getTimestamp();
        bundle.putString("oauth_timestamp", timestamp);
        bundle.putString("oauth_sign", HttpManager.getOauthSign(iRequestParam.getContext(), aidInfo, str, appKey, timestamp));
        StringBuilder sb = new StringBuilder();
        sb.append(aidInfo);
        sb.append("  ");
        sb.append(timestamp);
        sb.append("  ");
        sb.append(appKey);
        sb.append("   ");
        sb.append(str);
        LogUtil.e("weiboSdk param", sb.toString());
        return false;
    }

    private static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
