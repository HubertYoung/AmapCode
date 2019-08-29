package com.sina.weibo.sdk.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.network.IRequestService;
import com.sina.weibo.sdk.network.impl.RequestParam.Builder;
import com.sina.weibo.sdk.network.impl.RequestService;
import com.sina.weibo.sdk.network.target.SimpleTarget;

public class AccessTokenKeeper {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_UID = "uid";
    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";

    public static void writeAccessToken(Context context, Oauth2AccessToken oauth2AccessToken) {
        if (context != null && oauth2AccessToken != null) {
            Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 32768).edit();
            edit.putString("uid", oauth2AccessToken.getUid());
            edit.putString("access_token", oauth2AccessToken.getToken());
            edit.putString("refresh_token", oauth2AccessToken.getRefreshToken());
            edit.putLong("expires_in", oauth2AccessToken.getExpiresTime());
            edit.commit();
        }
    }

    public static Oauth2AccessToken readAccessToken(Context context) {
        if (context == null) {
            return null;
        }
        Oauth2AccessToken oauth2AccessToken = new Oauth2AccessToken();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, 32768);
        oauth2AccessToken.setUid(sharedPreferences.getString("uid", ""));
        oauth2AccessToken.setToken(sharedPreferences.getString("access_token", ""));
        oauth2AccessToken.setRefreshToken(sharedPreferences.getString("refresh_token", ""));
        oauth2AccessToken.setExpiresTime(sharedPreferences.getLong("expires_in", 0));
        return oauth2AccessToken;
    }

    public static void clear(Context context) {
        if (context != null) {
            Editor edit = context.getSharedPreferences(PREFERENCES_NAME, 32768).edit();
            edit.clear();
            edit.commit();
        }
    }

    public static void refreshToken(String str, final Context context, final RequestListener requestListener) {
        Oauth2AccessToken readAccessToken = readAccessToken(context);
        if (readAccessToken != null) {
            IRequestService instance = RequestService.getInstance();
            Builder builder = new Builder(context);
            builder.setShortUrl("https://api.weibo.com/oauth2/access_token");
            builder.addPostParam((String) "client_id", str);
            builder.addPostParam((String) "appKey", str);
            builder.addPostParam((String) "grant_type", (String) "refresh_token");
            builder.addPostParam((String) "refresh_token", readAccessToken.getRefreshToken());
            instance.asyncRequest(builder.build(), new SimpleTarget() {
                public final void onSuccess(String str) {
                    AccessTokenKeeper.writeAccessToken(context, Oauth2AccessToken.parseAccessToken(str));
                    if (requestListener != null) {
                        requestListener.onComplete(str);
                    }
                }

                public final void onFailure(Exception exc) {
                    if (requestListener != null) {
                        requestListener.onWeiboException(new WeiboException((Throwable) exc));
                    }
                }
            });
        }
    }
}
