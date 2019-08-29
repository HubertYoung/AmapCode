package com.ali.user.mobile.external;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.ali.user.mobile.biz.AliUserSdkLoginBiz;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.sso.impl.SsoServiceImpl;
import com.ali.user.mobile.login.sso.info.SsoLoginInfo;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.utils.CommonUtil;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.inside.android.phone.mrpc.core.RpcException;

public class LoginSsoActivity extends BaseExternalActivity {
    private static final int ALIPAY_NOT_INSTALL = 1001;
    private static final int DO_SSO_TOKEN_LOGIN = 1000;
    private static final int JUDGE_FLOW_ERROR = 1003;
    private static final String LOGIN_BY_SSO_TOKEN = "BY_SSO_TOKEN";
    private static final int SSOINFO_IS_NULL = 1004;
    private static final int SSO_LOGIN_ERROR = 1005;
    /* access modifiers changed from: private */
    public static final String TAG = "LoginSsoActivity";
    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1000:
                    LoginSsoActivity.this.doSsoTokenLogin((SsoLoginInfo) message.obj);
                    return;
                case 1001:
                case 1003:
                case 1004:
                case 1005:
                    IInsideServiceCallback g = AliuserLoginContext.g();
                    if (g != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("loginStatus", "login_failed");
                        g.onComplted(bundle);
                        AliuserLoginContext.a((IInsideServiceCallback) null);
                    }
                    LoginSsoActivity.this.delayFinishCurrentPage();
                    break;
            }
        }
    };
    private SsoServiceImpl mSsoService;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (LauncherApplication.a() == null) {
            AliUserLog.c(TAG, "LauncherApplication.getInstance() == null finish");
            finish();
            return;
        }
        showProgress("");
        AliUserLog.c(TAG, "init login pre check");
        this.mSsoService = SsoServiceImpl.a(getApplicationContext());
        AliUserSdkLoginBiz.a((Context) LauncherApplication.a());
        LoggerUtils.a("", "LoginPreCheckActivity", "login", "");
        initRdsPage("");
        loginFlowJudge();
    }

    public void onStart() {
        super.onStart();
    }

    private void loginFlowJudge() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (!LoginSsoActivity.this.isAlipayInstall()) {
                        LoginSsoActivity.this.handler.sendEmptyMessage(1001);
                        return;
                    }
                    SsoLoginInfo access$100 = LoginSsoActivity.this.getSSOLoginInfo();
                    if (access$100 == null) {
                        LoginSsoActivity.this.handler.sendEmptyMessage(1004);
                    } else {
                        LoginSsoActivity.this.handler.sendMessage(LoginSsoActivity.this.handler.obtainMessage(1000, access$100));
                    }
                } catch (Throwable th) {
                    AliUserLog.b(LoginSsoActivity.TAG, "check error", th);
                    LoginSsoActivity.this.handler.sendEmptyMessage(1003);
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public boolean isAlipayInstall() {
        return CommonUtil.a(LauncherApplication.a().getApplicationContext());
    }

    /* access modifiers changed from: private */
    public SsoLoginInfo getSSOLoginInfo() {
        SsoLoginInfo a = this.mSsoService.a();
        if (a == null || TextUtils.isEmpty(a.loginToken) || TextUtils.isEmpty(a.loginId)) {
            return null;
        }
        SsoLoginInfo a2 = this.mSsoService.a(a);
        if (a2 == null || !a2.isDirectLogin.booleanValue() || TextUtils.isEmpty(a2.loginToken) || TextUtils.isEmpty(a2.loginId)) {
            return null;
        }
        return a2;
    }

    /* access modifiers changed from: private */
    public void delayFinishCurrentPage() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                try {
                    LoginSsoActivity.this.dismissProgress();
                    LoginSsoActivity.this.finish();
                } catch (Throwable th) {
                    AliUserLog.b(LoginSsoActivity.TAG, "LoginPreCheckActivity finish error", th);
                }
            }
        }, 200);
    }

    /* access modifiers changed from: private */
    public void doSsoTokenLogin(SsoLoginInfo ssoLoginInfo) {
        AliUserLog.c(TAG, "doSsoTokenLogin");
        LoggerUtils.a("clicked", "ssologin_page", "UC-SSOLOGIN-LOG-190516", LOGIN_BY_SSO_TOKEN);
        showProgress("");
        LoginParam loginParam = new LoginParam();
        loginParam.loginAccount = ssoLoginInfo.loginId;
        loginParam.token = ssoLoginInfo.loginToken;
        loginParam.validateTpye = "withlogintoken";
        doUnifyLogin(loginParam);
    }

    public void handleRpcException(RpcException rpcException) {
        LoggerUtils.a("clicked", "login_action_callback", "UC-ACTION-CALLBACK-170401-5", "rpc_error");
        AliUserLog.c(TAG, "handleRpcException rpc异常，请稍后再试");
        Bundle bundle = new Bundle();
        bundle.putString("loginStatus", "cancel");
        IInsideServiceCallback g = AliuserLoginContext.g();
        if (g != null) {
            g.onComplted(bundle);
            AliuserLoginContext.a((IInsideServiceCallback) null);
        }
        delayFinishCurrentPage();
    }

    public void onLoginResponseSuccess(UnifyLoginRes unifyLoginRes) {
        super.onLoginResponseSuccess(unifyLoginRes);
        dismissProgress();
        delayFinishCurrentPage();
    }

    public void onLoginResponseError(UnifyLoginRes unifyLoginRes) {
        this.handler.sendEmptyMessage(1005);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
