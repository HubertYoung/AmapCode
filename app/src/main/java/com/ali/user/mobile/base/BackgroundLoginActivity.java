package com.ali.user.mobile.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.service.UserLoginService;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.inside.android.phone.mrpc.core.RpcException;

public abstract class BackgroundLoginActivity extends BaseActivity {
    private static final String TAG = "BaseLoginActivity";
    protected Handler mHandler;
    protected LoginParam mLoginParam;
    protected String mToken;
    protected UserLoginService mUserLoginService;

    /* access modifiers changed from: protected */
    public boolean handleException(Exception exc) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onLoginFail(UnifyLoginRes unifyLoginRes) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean onLoginSuccess(UnifyLoginRes unifyLoginRes) {
        return false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mToken = getIntent().getStringExtra("token");
        this.mLoginParam = (LoginParam) getIntent().getSerializableExtra("login_param");
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mUserLoginService = AliuserLoginContext.d();
    }

    /* access modifiers changed from: protected */
    public void doBackgroundLogin() {
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                AliUserLog.c(BackgroundLoginActivity.TAG, "doBackgroundLogin");
                try {
                    BackgroundLoginActivity.this.afterBackgroundLogin(BackgroundLoginActivity.this.mUserLoginService.a(BackgroundLoginActivity.this.prepareLoginParam()));
                } catch (RpcException e) {
                    if (!BackgroundLoginActivity.this.handleException(e)) {
                        AliUserLog.c(BackgroundLoginActivity.TAG, "not client error,throw rpcExp");
                        BackgroundLoginActivity.this.dismissProgress();
                        throw e;
                    }
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public LoginParam prepareLoginParam() {
        this.mLoginParam.token = this.mToken;
        this.mLoginParam.validateTpye = "withchecktoken";
        return this.mLoginParam;
    }

    /* access modifiers changed from: protected */
    public void afterBackgroundLogin(final UnifyLoginRes unifyLoginRes) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (unifyLoginRes == null) {
                    BackgroundLoginActivity.this.dismissProgress();
                    BackgroundLoginActivity.this.toast(BackgroundLoginActivity.this.getResources().getString(R.string.bC), 3000);
                    LogAgent.a(new RuntimeException("loginRes == null after setDoublePassword"));
                    return;
                }
                StringBuilder sb = new StringBuilder("doBackgroundLogin result,code:");
                sb.append(unifyLoginRes.code);
                sb.append(",msg:");
                sb.append(unifyLoginRes.msg);
                AliUserLog.c(BackgroundLoginActivity.TAG, sb.toString());
                BackgroundLoginActivity.this.mToken = unifyLoginRes.token;
                if (!"200".equals(unifyLoginRes.code) && !"1000".equals(unifyLoginRes.code)) {
                    BackgroundLoginActivity.this.dismissProgress();
                    if (!BackgroundLoginActivity.this.onLoginFail(unifyLoginRes)) {
                        if ("6213".equals(unifyLoginRes.code)) {
                            AliUserLog.c(BackgroundLoginActivity.TAG, "session timeout");
                            BackgroundLoginActivity.this.alertResult(unifyLoginRes.msg, SecExceptionCode.SEC_ERROR_GENERIC_AVMP_UNKNOWN_ERROR);
                            return;
                        }
                        AliUserLog.c(BackgroundLoginActivity.TAG, "doBackgroundLogin other error");
                        BackgroundLoginActivity.this.toast(unifyLoginRes.msg, 3000);
                    }
                } else if (!BackgroundLoginActivity.this.onLoginSuccess(unifyLoginRes)) {
                    AliuserLoginContext.f().onLoginPreFinish(unifyLoginRes);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void alertResult(String str, final int i) {
        alert(null, str, getResources().getString(R.string.H), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                BackgroundLoginActivity.this.setResult(i);
                BackgroundLoginActivity.this.finish();
            }
        }, null, null);
    }
}
