package com.tencent.connect.auth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import android.widget.Toast;
import com.tencent.connect.a.a;
import com.tencent.connect.common.BaseApi;
import com.tencent.open.a.f;
import com.tencent.open.utils.ApkExternalInfoTool;
import com.tencent.open.utils.Global;
import com.tencent.tauth.IUiListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/* compiled from: ProGuard */
public class QQAuth {
    private AuthAgent a = new AuthAgent(this.b);
    private QQToken b;

    private QQAuth(String str, Context context) {
        f.c("openSDK_LOG.QQAuth", "new QQAuth() --start");
        this.b = new QQToken(str);
        a.c(context, this.b);
        f.c("openSDK_LOG.QQAuth", "new QQAuth() --end");
    }

    public static QQAuth createInstance(String str, Context context) {
        Global.setContext(context.getApplicationContext());
        f.c("openSDK_LOG.QQAuth", "QQAuth -- createInstance() --start");
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.tauth.AuthActivity"), 0);
            packageManager.getActivityInfo(new ComponentName(context.getPackageName(), "com.tencent.connect.common.AssistActivity"), 0);
            QQAuth qQAuth = new QQAuth(str, context);
            f.c("openSDK_LOG.QQAuth", "QQAuth -- createInstance()  --end");
            return qQAuth;
        } catch (NameNotFoundException e) {
            f.b("openSDK_LOG.QQAuth", "createInstance() error --end", e);
            Toast.makeText(context.getApplicationContext(), "请参照文档在Androidmanifest.xml加上AuthActivity和AssitActivity的定义 ", 1).show();
            return null;
        }
    }

    public int login(Activity activity, String str, IUiListener iUiListener) {
        f.c("openSDK_LOG.QQAuth", "login()");
        return login(activity, str, iUiListener, (String) "");
    }

    public int login(Activity activity, String str, IUiListener iUiListener, String str2) {
        f.c("openSDK_LOG.QQAuth", "-->login activity: ".concat(String.valueOf(activity)));
        return a(activity, null, str, iUiListener, str2);
    }

    public int login(Fragment fragment, String str, IUiListener iUiListener, String str2) {
        FragmentActivity activity = fragment.getActivity();
        f.c("openSDK_LOG.QQAuth", "-->login activity: ".concat(String.valueOf(activity)));
        return a(activity, fragment, str, iUiListener, str2);
    }

    private int a(Activity activity, Fragment fragment, String str, IUiListener iUiListener, String str2) {
        String str3;
        String packageName = activity.getApplicationContext().getPackageName();
        Iterator<ApplicationInfo> it = activity.getPackageManager().getInstalledApplications(128).iterator();
        while (true) {
            if (!it.hasNext()) {
                str3 = null;
                break;
            }
            ApplicationInfo next = it.next();
            if (packageName.equals(next.packageName)) {
                str3 = next.sourceDir;
                break;
            }
        }
        if (str3 != null) {
            try {
                String readChannelId = ApkExternalInfoTool.readChannelId(new File(str3));
                if (!TextUtils.isEmpty(readChannelId)) {
                    f.a("openSDK_LOG.QQAuth", "-->login channelId: ".concat(String.valueOf(readChannelId)));
                    return loginWithOEM(activity, str, iUiListener, readChannelId, readChannelId, "");
                }
            } catch (IOException e) {
                f.b("openSDK_LOG.QQAuth", "-->login get channel id exception.", e);
                e.printStackTrace();
            }
        }
        f.b("openSDK_LOG.QQAuth", "-->login channelId is null ");
        BaseApi.isOEM = false;
        return this.a.doLogin(activity, str, iUiListener, false, fragment);
    }

    @Deprecated
    public int loginWithOEM(Activity activity, String str, IUiListener iUiListener, String str2, String str3, String str4) {
        f.c("openSDK_LOG.QQAuth", "loginWithOEM");
        BaseApi.isOEM = true;
        if (str2.equals("")) {
            str2 = "null";
        }
        if (str3.equals("")) {
            str3 = "null";
        }
        if (str4.equals("")) {
            str4 = "null";
        }
        BaseApi.installChannel = str3;
        BaseApi.registerChannel = str2;
        BaseApi.businessId = str4;
        return this.a.doLogin(activity, str, iUiListener);
    }

    public int reAuth(Activity activity, String str, IUiListener iUiListener) {
        f.c("openSDK_LOG.QQAuth", "reAuth()");
        return this.a.doLogin(activity, str, iUiListener, true, null);
    }

    public void reportDAU() {
        this.a.a((IUiListener) null);
    }

    public void checkLogin(IUiListener iUiListener) {
        this.a.b(iUiListener);
    }

    public void logout(Context context) {
        f.c("openSDK_LOG.QQAuth", "logout() --start");
        CookieSyncManager.createInstance(context);
        setAccessToken(null, null);
        setOpenId(context, null);
        f.c("openSDK_LOG.QQAuth", "logout() --end");
    }

    public QQToken getQQToken() {
        return this.b;
    }

    public void setAccessToken(String str, String str2) {
        f.a("openSDK_LOG.QQAuth", "setAccessToken(), validTimeInSecond = ".concat(String.valueOf(str2)));
        this.b.setAccessToken(str, str2);
    }

    public boolean isSessionValid() {
        StringBuilder sb = new StringBuilder("isSessionValid(), result = ");
        sb.append(this.b.isSessionValid() ? "true" : "false");
        f.a("openSDK_LOG.QQAuth", sb.toString());
        return this.b.isSessionValid();
    }

    public void setOpenId(Context context, String str) {
        f.a("openSDK_LOG.QQAuth", "setOpenId() --start");
        this.b.setOpenId(str);
        a.d(context, this.b);
        f.a("openSDK_LOG.QQAuth", "setOpenId() --end");
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        f.c("openSDK_LOG.QQAuth", "onActivityResult() ,resultCode = ".concat(String.valueOf(i2)));
        return true;
    }
}
