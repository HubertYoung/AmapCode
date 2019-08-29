package com.ali.user.mobile.external;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.ali.user.mobile.authlogin.AlipaySsoAPIFactory;
import com.ali.user.mobile.authlogin.auth.IAlipaySSOAuthLoginAPI;
import com.ali.user.mobile.authlogin.auth.IAlipaySSOEventHandler;
import com.ali.user.mobile.authlogin.common.AuthLoginUtils;
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

public class AuthLoginResultActivity extends BaseExternalActivity {
    private static final String TAG = "AuthLoginResultActivity";

    class AlipaySSOEventHandlerImpl implements IAlipaySSOEventHandler {
        public void dismissProgress() {
        }

        public void showProgress() {
        }

        AlipaySSOEventHandlerImpl() {
        }

        public void startLogin(String str) {
            AuthLoginResultActivity.this.goLogin(str);
        }

        public void onAuthFailed(final int i) {
            AuthLoginResultActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    AuthLoginResultActivity.this.onProcessAuthResultError(String.valueOf(i));
                }
            });
        }
    }

    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            handleResultIntent();
        } catch (Throwable unused) {
            AliUserLog.c(TAG, "oncreate error");
            finish();
        }
    }

    public void onNewIntent(Intent intent) {
        try {
            super.onNewIntent(intent);
            setIntent(intent);
            handleResultIntent();
        } catch (Throwable unused) {
            AliUserLog.c(TAG, "onNewIntent error");
            finish();
        }
    }

    private void handleResultIntent() {
        try {
            LauncherApplication.a(this);
            AliUserSdkLoginBiz.a((Context) LauncherApplication.a());
            showProgress("");
            IAlipaySSOAuthLoginAPI a = AlipaySsoAPIFactory.a(getApplicationContext());
            Intent intent = getIntent();
            if (a.a(intent)) {
                a.a(intent, (IAlipaySSOEventHandler) new AlipaySSOEventHandlerImpl());
                return;
            }
            AliUserLog.c(TAG, "notalicall");
            LoggerUtils.a("clicked", "login_action_callback", "UC-ACTION-CALLBACK-170401-2", "anc");
            delayFinishCurrentPage();
        } catch (Throwable th) {
            AliUserLog.a(TAG, "handleResultIntent", th);
            onProcessAuthResultError("process auth result error");
        }
    }

    /* access modifiers changed from: private */
    public void goLogin(String str) {
        LoggerUtils.a("clicked", "authlogin_resultpage", "UC-AUTHLOGIN-LOG-190516", "withauthtoken");
        LoginParam loginParam = new LoginParam();
        loginParam.validateTpye = "withauthtoken";
        loginParam.token = str;
        StringBuilder sb = new StringBuilder();
        AppInfo instance = AppInfo.getInstance();
        String authApdidToken = instance.getAuthApdidToken();
        if (TextUtils.isEmpty(authApdidToken)) {
            authApdidToken = instance.getApdidToken();
        }
        sb.append("{\"apdidToken\":\"");
        sb.append(authApdidToken);
        sb.append("\"}");
        loginParam.addExternalParam("devKeySet", sb.toString());
        loginParam.addExternalParam("appKey", AuthLoginUtils.a());
        doUnifyLogin(loginParam);
    }

    public void onStart() {
        super.onStart();
        initRdsPage("");
    }

    /* access modifiers changed from: protected */
    public void onProcessAuthResultError(String str) {
        AliUserLog.c(TAG, "onProcessAuthResultError errorType is ".concat(String.valueOf(str)));
        LoggerUtils.a("clicked", "login_action_callback", "UC-ACTION-CALLBACK-170401-5", str);
        toast(getResources().getString(R.string.ct), 0);
        callback("other_failed");
        delayFinishCurrentPage();
    }

    public void onLoginResponseSuccess(UnifyLoginRes unifyLoginRes) {
        super.onLoginResponseSuccess(unifyLoginRes);
        dismissProgress();
        delayFinishCurrentPage();
    }

    public void onLoginResponseError(UnifyLoginRes unifyLoginRes) {
        toast(getResources().getString(R.string.bE));
        dismissProgress();
        callback("login_failed");
        delayFinishCurrentPage();
    }

    public void onProcessVerifyUnSuccessResult(String str) {
        AliUserLog.c(TAG, "authlogin onProcessVerifyUnSuccessResult resultState:".concat(String.valueOf(str)));
        toast(getResources().getString(R.string.bE));
        dismissProgress();
        callback(str);
        delayFinishCurrentPage();
    }

    public void handleRpcException(RpcException rpcException) {
        toast(getResources().getString(R.string.cu), 1);
        dismissProgress();
        callback("rpc_failed");
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
    public void delayFinishCurrentPage() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                try {
                    AuthLoginResultActivity.this.dismissProgress();
                    AuthLoginResultActivity.this.finish();
                    AuthLoginResultActivity.this.overridePendingTransition(17432576, 17432577);
                } catch (Throwable th) {
                    AliUserLog.b(AuthLoginResultActivity.TAG, "AuthLoginResultActivity finish error", th);
                }
            }
        }, 200);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (4 == i) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}
