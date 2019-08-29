package com.sina.weibo.sdk.network.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class WbUserInfoHelper {
    private static WbUserInfoHelper userInfoHelper;
    private WbUserInfo userInfo;

    public static WbUserInfoHelper getInstance() {
        if (userInfoHelper == null) {
            userInfoHelper = new WbUserInfoHelper();
        }
        return userInfoHelper;
    }

    public WbUserInfo getUserInfo(Context context) {
        if (context == null) {
            return null;
        }
        if (this.userInfo == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("wb_sdk_user_key", 0);
            String string = sharedPreferences.getString(Oauth2AccessToken.KEY_UID, null);
            String string2 = sharedPreferences.getString("gsid", null);
            String string3 = sharedPreferences.getString("token", null);
            if (!TextUtils.isEmpty(string2)) {
                this.userInfo = new WbUserInfo(string2, string, string3);
            }
        }
        return this.userInfo;
    }

    public void clearUserInfo() {
        this.userInfo = null;
    }
}
