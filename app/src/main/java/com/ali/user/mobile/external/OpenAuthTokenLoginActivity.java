package com.ali.user.mobile.external;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.ali.user.mobile.biz.AliUserSdkLoginBiz;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.security.ui.R;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.inside.android.phone.mrpc.core.RpcException;

public class OpenAuthTokenLoginActivity extends BaseExternalActivity {
    /* access modifiers changed from: private */
    public static final String TAG = "OpenAuthTokenLoginActivity";
    private static final String TRUST_LOGIN_BY_OPENAUTHTOKEN = "BY_OPEN_AUTH_TOKEN";
    /* access modifiers changed from: private */
    public boolean mIsNewOpenAuthFlow;
    /* access modifiers changed from: private */
    public String mMCUid = null;
    /* access modifiers changed from: private */
    public String mOpenAuthToken = null;
    /* access modifiers changed from: private */
    public String mOpenAuthUserId = null;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (LauncherApplication.a() == null) {
            AliUserLog.c(TAG, "LauncherApplication.getInstance() == null finish");
            finish();
            return;
        }
        showProgress("");
        Intent intent = getIntent();
        if (intent != null) {
            this.mOpenAuthToken = intent.getStringExtra("openAuthToken");
            this.mOpenAuthUserId = intent.getStringExtra("openAuthUserId");
            this.mMCUid = intent.getStringExtra("openMcUid");
            this.mIsNewOpenAuthFlow = intent.getBooleanExtra("isNewOpenAuthFlow", false);
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("mOpenAuthToken = ");
        sb.append(this.mOpenAuthToken);
        sb.append(" , mOpenAuthUserId = ");
        sb.append(this.mOpenAuthUserId);
        AliUserLog.c(str, sb.toString());
        AliUserSdkLoginBiz.a((Context) LauncherApplication.a());
        LoggerUtils.a("", "OpenAuthTokenLogin", "login", "");
        initRdsPage("");
        if (canOpenAuthLogin()) {
            doOpenAuthLogin();
        } else {
            delayFinishCurrentPage();
        }
    }

    public void onStart() {
        super.onStart();
    }

    private boolean canOpenAuthLogin() {
        return this.mIsNewOpenAuthFlow ? !TextUtils.isEmpty(this.mOpenAuthToken) && !TextUtils.isEmpty(this.mOpenAuthUserId) && !TextUtils.isEmpty(this.mMCUid) : !TextUtils.isEmpty(this.mOpenAuthToken) && !TextUtils.isEmpty(this.mOpenAuthUserId);
    }

    private void delayFinishCurrentPage() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                try {
                    OpenAuthTokenLoginActivity.this.dismissProgress();
                    OpenAuthTokenLoginActivity.this.finish();
                } catch (Throwable th) {
                    AliUserLog.b(OpenAuthTokenLoginActivity.TAG, "OpenAuthTokenLoginActivity finish error", th);
                }
            }
        }, 200);
    }

    private void doOpenAuthLogin() {
        AliUserLog.c(TAG, "method doOpenAuthLogin()");
        LoggerUtils.a("clicked", "trustlogin_by_openauthtoken", "UC-OAT_TRUSTLOGIN-LOG-170401-2", TRUST_LOGIN_BY_OPENAUTHTOKEN);
        showProgress("");
        new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (true) {
                    if (i >= 5) {
                        break;
                    } else if (!TextUtils.isEmpty(AppInfo.getInstance().getApdidToken())) {
                        AliUserLog.c(OpenAuthTokenLoginActivity.TAG, "等待apdid生成：i=".concat(String.valueOf(i)));
                        break;
                    } else {
                        SystemClock.sleep(800);
                        i++;
                    }
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        AliUserLog.c(OpenAuthTokenLoginActivity.TAG, "doOpenAuthLogin start");
                        LoginParam loginParam = new LoginParam();
                        OpenAuthTokenLoginActivity.this.getLoginParams(loginParam);
                        loginParam.token = OpenAuthTokenLoginActivity.this.mOpenAuthToken;
                        loginParam.validateTpye = "withopenauthtoken";
                        loginParam.addExternalParam("openAuthUserId", OpenAuthTokenLoginActivity.this.mOpenAuthUserId);
                        if (OpenAuthTokenLoginActivity.this.mIsNewOpenAuthFlow) {
                            loginParam.addExternalParam("openMcUid", OpenAuthTokenLoginActivity.this.mMCUid);
                            loginParam.addExternalParam("checkMcBind", "true");
                        }
                        OpenAuthTokenLoginActivity.this.doUnifyLogin(loginParam);
                    }
                });
            }
        }).start();
    }

    public void handleRpcException(RpcException rpcException) {
        LoggerUtils.a("clicked", "login_action_callback", "UC-ACTION-CALLBACK-170401-5", "rpc_error");
        toast(getResources().getString(R.string.cu), 0);
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
        if (unifyLoginRes == null || (!"6207".equals(unifyLoginRes.code) && !"6302".equals(unifyLoginRes.code))) {
            toast(getResources().getString(R.string.bE));
            dismissProgress();
            if (unifyLoginRes == null || !"6601".equals(unifyLoginRes.code)) {
                callback("login_failed");
            } else {
                callback("openAuthTokenInvalid");
            }
            delayFinishCurrentPage();
            return;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("登录返回状态码");
        sb.append(unifyLoginRes.code);
        AliUserLog.c(str, sb.toString());
        super.onLoginResponseError(unifyLoginRes);
    }

    public void onProcessVerifyUnSuccessResult(String str) {
        AliUserLog.c(TAG, "openauthtoken onProcessVerifyUnSuccessResult resultState:".concat(String.valueOf(str)));
        toast(getResources().getString(R.string.bE));
        dismissProgress();
        callback(str);
        delayFinishCurrentPage();
    }

    private void callback(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("loginStatus", str);
        IInsideServiceCallback g = AliuserLoginContext.g();
        if (g != null) {
            g.onComplted(bundle);
            AliuserLoginContext.a((IInsideServiceCallback) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
