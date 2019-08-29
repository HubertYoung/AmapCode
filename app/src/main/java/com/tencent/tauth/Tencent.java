package com.tencent.tauth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.SocialApi;
import com.tencent.open.a.f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.open.utils.SystemUtils;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class Tencent {
    public static final int REQUEST_LOGIN = 10001;
    private static final String TAG = "openSDK_LOG.Tencent";
    private static Tencent sInstance;
    private final QQAuth mQQAuth;

    public void releaseResource() {
    }

    private Tencent(String str, Context context) {
        Global.setContext(context.getApplicationContext());
        this.mQQAuth = QQAuth.createInstance(str, context);
    }

    public static synchronized Tencent createInstance(String str, Context context) {
        synchronized (Tencent.class) {
            Global.setContext(context.getApplicationContext());
            f.c(TAG, "createInstance()  -- start");
            if (sInstance == null) {
                sInstance = new Tencent(str, context);
            } else if (!str.equals(sInstance.getAppId())) {
                sInstance.logout(context);
                sInstance = new Tencent(str, context);
            }
            if (!checkManifestConfig(context, str)) {
                return null;
            }
            f.c(TAG, "createInstance()  -- end");
            Tencent tencent = sInstance;
            return tencent;
        }
    }

    private static boolean checkManifestConfig(Context context, String str) {
        try {
            context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
            try {
                context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
                return true;
            } catch (NameNotFoundException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("没有在AndroidManifest.xml中检测到com.tencent.connect.common.AssistActivity,请加上com.tencent.connect.common.AssistActivity,详细信息请查看官网文档.");
                sb.append("\n配置示例如下: \n<activity\n     android:name=\"com.tencent.connect.common.AssistActivity\"\n     android:screenOrientation=\"behind\"\n     android:theme=\"@android:style/Theme.Translucent.NoTitleBar\"\n     android:configChanges=\"orientation|keyboardHidden\">\n</activity>");
                f.e(TAG, "AndroidManifest.xml 没有检测到com.tencent.connect.common.AssistActivity\n".concat(String.valueOf(sb.toString())));
                return false;
            }
        } catch (NameNotFoundException unused2) {
            StringBuilder sb2 = new StringBuilder("没有在AndroidManifest.xml中检测到com.tencent.tauth.AuthActivity,请加上com.tencent.tauth.AuthActivity,并配置<data android:scheme=\"tencent");
            sb2.append(str);
            sb2.append("\" />,详细信息请查看官网文档.");
            String sb3 = sb2.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb3);
            sb4.append("\n配置示例如下: \n<activity\n     android:name=\"com.tencent.tauth.AuthActivity\"\n     android:noHistory=\"true\"\n     android:launchMode=\"singleTask\">\n<intent-filter>\n    <action android:name=\"android.intent.action.VIEW\" />\n    <category android:name=\"android.intent.category.DEFAULT\" />\n    <category android:name=\"android.intent.category.BROWSABLE\" />\n    <data android:scheme=\"tencent");
            sb4.append(str);
            sb4.append("\" />\n</intent-filter>\n</activity>");
            f.e(TAG, "AndroidManifest.xml 没有检测到com.tencent.tauth.AuthActivity".concat(String.valueOf(sb4.toString())));
            return false;
        }
    }

    public int login(Activity activity, String str, IUiListener iUiListener) {
        f.c(TAG, "login() with activity, scope is ".concat(String.valueOf(str)));
        return this.mQQAuth.login(activity, str, iUiListener);
    }

    public int login(Fragment fragment, String str, IUiListener iUiListener) {
        f.c(TAG, "login() with fragment, scope is ".concat(String.valueOf(str)));
        return this.mQQAuth.login(fragment, str, iUiListener, (String) "");
    }

    public int loginServerSide(Activity activity, String str, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder("loginServerSide() with activity, scope = ");
        sb.append(str);
        sb.append(",server_side");
        f.c(TAG, sb.toString());
        QQAuth qQAuth = this.mQQAuth;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(",server_side");
        return qQAuth.login(activity, sb2.toString(), iUiListener);
    }

    public int loginServerSide(Fragment fragment, String str, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder("loginServerSide() with fragment, scope = ");
        sb.append(str);
        sb.append(",server_side");
        f.c(TAG, sb.toString());
        QQAuth qQAuth = this.mQQAuth;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(",server_side");
        return qQAuth.login(fragment, sb2.toString(), iUiListener, (String) "");
    }

    public int loginWithOEM(Activity activity, String str, IUiListener iUiListener, String str2, String str3, String str4) {
        f.c(TAG, "loginWithOEM() with activity, scope = ".concat(String.valueOf(str)));
        return this.mQQAuth.loginWithOEM(activity, str, iUiListener, str2, str3, str4);
    }

    public void logout(Context context) {
        f.c(TAG, "logout()");
        this.mQQAuth.getQQToken().setAccessToken(null, "0");
        this.mQQAuth.getQQToken().setOpenId(null);
    }

    public int reAuth(Activity activity, String str, IUiListener iUiListener) {
        f.c(TAG, "reAuth() with activity, scope = ".concat(String.valueOf(str)));
        return this.mQQAuth.reAuth(activity, str, iUiListener);
    }

    public void reportDAU() {
        this.mQQAuth.reportDAU();
    }

    public void checkLogin(IUiListener iUiListener) {
        f.c(TAG, "checkLogin()");
        this.mQQAuth.checkLogin(iUiListener);
    }

    public int invite(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(TAG, "invite()");
        new SocialApi(this.mQQAuth.getQQToken()).invite(activity, bundle, iUiListener);
        return 0;
    }

    public int story(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(TAG, "story()");
        new SocialApi(this.mQQAuth.getQQToken()).story(activity, bundle, iUiListener);
        return 0;
    }

    public int gift(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(TAG, "gift()");
        new SocialApi(this.mQQAuth.getQQToken()).gift(activity, bundle, iUiListener);
        return 0;
    }

    public int ask(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(TAG, "ask()");
        new SocialApi(this.mQQAuth.getQQToken()).ask(activity, bundle, iUiListener);
        return 0;
    }

    public void requestAsync(String str, Bundle bundle, String str2, IRequestListener iRequestListener, Object obj) {
        f.c(TAG, "requestAsync()");
        HttpUtils.requestAsync(this.mQQAuth.getQQToken(), Global.getContext(), str, bundle, str2, iRequestListener);
    }

    public JSONObject request(String str, Bundle bundle, String str2) throws IOException, JSONException, NetworkUnavailableException, HttpStatusException {
        f.c(TAG, "request()");
        return HttpUtils.request(this.mQQAuth.getQQToken(), Global.getContext(), str, bundle, str2);
    }

    public void shareToQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(TAG, "shareToQQ()");
        new QQShare(activity, this.mQQAuth.getQQToken()).shareToQQ(activity, bundle, iUiListener);
    }

    public void shareToQzone(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(TAG, "shareToQzone()");
        new QzoneShare(activity, this.mQQAuth.getQQToken()).shareToQzone(activity, bundle, iUiListener);
    }

    public void publishToQzone(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(TAG, "publishToQzone()");
        new QzonePublish(activity, this.mQQAuth.getQQToken()).publishToQzone(activity, bundle, iUiListener);
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        f.c(TAG, "onActivityResult() deprecated, will do nothing");
        return false;
    }

    public static boolean onActivityResultData(int i, int i2, Intent intent, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder("onActivityResultData() reqcode = ");
        sb.append(i);
        sb.append(", resultcode = ");
        sb.append(i2);
        sb.append(", data = null ? ");
        boolean z = false;
        sb.append(intent == null);
        sb.append(", listener = null ? ");
        if (iUiListener == null) {
            z = true;
        }
        sb.append(z);
        f.c(TAG, sb.toString());
        return UIListenerManager.getInstance().onActivityResult(i, i2, intent, iUiListener);
    }

    public boolean isSessionValid() {
        return this.mQQAuth.isSessionValid();
    }

    public String getAppId() {
        return this.mQQAuth.getQQToken().getAppId();
    }

    public String getAccessToken() {
        return this.mQQAuth.getQQToken().getAccessToken();
    }

    public long getExpiresIn() {
        return this.mQQAuth.getQQToken().getExpireTimeInSecond();
    }

    public String getOpenId() {
        return this.mQQAuth.getQQToken().getOpenId();
    }

    @Deprecated
    public void handleLoginData(Intent intent, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder("handleLoginData() data = null ? ");
        boolean z = false;
        sb.append(intent == null);
        sb.append(", listener = null ? ");
        if (iUiListener == null) {
            z = true;
        }
        sb.append(z);
        f.c(TAG, sb.toString());
        UIListenerManager.getInstance().handleDataToListener(intent, iUiListener);
    }

    public static void handleResultData(Intent intent, IUiListener iUiListener) {
        StringBuilder sb = new StringBuilder("handleResultData() data = null ? ");
        boolean z = false;
        sb.append(intent == null);
        sb.append(", listener = null ? ");
        if (iUiListener == null) {
            z = true;
        }
        sb.append(z);
        f.c(TAG, sb.toString());
        UIListenerManager.getInstance().handleDataToListener(intent, iUiListener);
    }

    public void setAccessToken(String str, String str2) {
        f.a(TAG, "setAccessToken(), expiresIn = ".concat(String.valueOf(str2)));
        this.mQQAuth.setAccessToken(str, str2);
    }

    public void setOpenId(String str) {
        f.a(TAG, "setOpenId() --start");
        this.mQQAuth.setOpenId(Global.getContext(), str);
        f.a(TAG, "setOpenId() --end");
    }

    public boolean isReady() {
        return isSessionValid() && getOpenId() != null;
    }

    public QQToken getQQToken() {
        return this.mQQAuth.getQQToken();
    }

    public boolean isSupportSSOLogin(Activity activity) {
        if (SystemUtils.getAppVersionName(activity, "com.tencent.mobileqq") == null) {
            return false;
        }
        return SystemUtils.checkMobileQQ(activity);
    }
}
