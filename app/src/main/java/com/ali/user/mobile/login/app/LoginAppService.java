package com.ali.user.mobile.login.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.accountbiz.accountmanager.safelogout.LogoutBiz;
import com.ali.user.mobile.accountbiz.extservice.AccountService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.biz.AliUserSdkLoginBiz;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.mobile.common.share.widget.ResUtils;

public class LoginAppService {
    private static final String TAG = "LoginApp";
    private static LoginAppService mLoginAppService;
    /* access modifiers changed from: private */
    public AliUserSdkLoginBiz aliUserSdkLoginBiz;
    private Context mContext;
    /* access modifiers changed from: private */
    public Bundle mParams = null;

    private LoginAppService() {
    }

    public static LoginAppService getInstance() {
        if (mLoginAppService == null) {
            synchronized (LoginAppService.class) {
                try {
                    if (mLoginAppService == null) {
                        mLoginAppService = new LoginAppService();
                    }
                }
            }
        }
        return mLoginAppService;
    }

    public void startLoginPage(final Context context, Bundle bundle) {
        this.mContext = context.getApplicationContext();
        this.mParams = bundle;
        AliUserLog.c(TAG, String.format("aliuser login, param:%s", new Object[]{this.mParams}));
        if (this.mParams != null && "sms".equals(this.mParams.getString(ResUtils.STYLE))) {
            AliUserLog.c(TAG, "外部唤起短信登录");
            if ("tablauncher".equals(this.mParams.getString("LoginSource"))) {
                AliUserLog.c(TAG, "tablauncher调起的登录不做任何检查，直接放过");
            } else {
                String string = this.mParams.getString("loginId");
                String currentLoginId = getCurrentLoginId();
                boolean isLogin = isLogin();
                AliUserLog.c(TAG, String.format("extraLoginId:%s, curLoginId:%s, isLogin:%s", new Object[]{string, currentLoginId, Boolean.valueOf(isLogin)}));
                if (!TextUtils.isEmpty(string) && string.equals(currentLoginId) && isLogin) {
                    return;
                }
            }
        }
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                AliUserLog.c(LoginAppService.TAG, "checkTaobaoAuthStatus in urgent thread");
                Bundle access$000 = LoginAppService.this.mParams != null ? LoginAppService.this.mParams : new Bundle();
                if (LoginAppService.this.aliUserSdkLoginBiz == null) {
                    AliUserLog.c(LoginAppService.TAG, "try to create instance of AliUserSdkLoginBiz in loginApp");
                    LoginAppService.this.aliUserSdkLoginBiz = AliUserSdkLoginBiz.a(context);
                }
                LoginAppService.this.aliUserSdkLoginBiz.a(access$000);
                LoginAppService.this.aliUserSdkLoginBiz.a();
                AliUserLog.c(LoginAppService.TAG, "start aliuser sdk login 12345678 - end");
                LoginAppService.this.sendBroadcast("com.alipay.security.startlogin", "state=startLoginApp");
                LoginAppService.this.checkLogoutForStart();
                AliUserLog.c(LoginAppService.TAG, "init: sendBroadcast action=com.alipay.security.startlogin");
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkLogoutForStart() {
        if (this.mParams != null && "Y".equalsIgnoreCase(this.mParams.getString("isNeedSendLogout"))) {
            AliUserLog.c(TAG, "启动账密前发现isNeedSendLogout标记，清isLogin标记，发送logtou广播");
            AntExtServiceManager.getAccountService(this.mContext).setCurrentLoginState("false");
            new LogoutBiz().a();
        }
    }

    /* access modifiers changed from: private */
    public void sendBroadcast(String str, String str2) {
        Intent intent = new Intent();
        intent.setAction(str);
        intent.putExtra("data", str2);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(intent);
    }

    private String getCurrentLoginId() {
        AccountService accountService = AntExtServiceManager.getAccountService(this.mContext);
        if (accountService == null) {
            return "";
        }
        return accountService.getCurrentLoginLogonId();
    }

    private boolean isLogin() {
        return AntExtServiceManager.getAuthService(this.mContext).isLogin();
    }
}
