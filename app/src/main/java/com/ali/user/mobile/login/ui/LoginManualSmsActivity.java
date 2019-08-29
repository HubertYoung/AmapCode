package com.ali.user.mobile.login.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.common.ui.AbsVerifySmsActivity;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.MsgLoginParam;
import com.ali.user.mobile.rpc.vo.mobilegw.login.LoginSendMSGResPb;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.security.ui.R;
import com.alipay.inside.android.phone.mrpc.core.RpcException;

public class LoginManualSmsActivity extends AbsVerifySmsActivity {
    private static final String TAG = "LoginManualSmsActivity";
    private String mLoginId;
    private String mToken;

    /* JADX WARNING: Removed duplicated region for block: B:12:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0053  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r6) {
        /*
            r5 = this;
            super.onCreate(r6)
            java.lang.String r6 = ""
            java.lang.String r0 = ""
            android.content.Intent r1 = r5.getIntent()     // Catch:{ Throwable -> 0x003d }
            java.lang.String r2 = "token"
            java.lang.String r1 = r1.getStringExtra(r2)     // Catch:{ Throwable -> 0x003d }
            r5.mToken = r1     // Catch:{ Throwable -> 0x003d }
            android.content.Intent r1 = r5.getIntent()     // Catch:{ Throwable -> 0x003d }
            java.lang.String r2 = "loginId"
            java.lang.String r1 = r1.getStringExtra(r2)     // Catch:{ Throwable -> 0x003d }
            r5.mLoginId = r1     // Catch:{ Throwable -> 0x003d }
            android.content.Intent r1 = r5.getIntent()     // Catch:{ Throwable -> 0x003d }
            java.lang.String r2 = "showAccount"
            java.lang.String r1 = r1.getStringExtra(r2)     // Catch:{ Throwable -> 0x003d }
            android.content.Intent r6 = r5.getIntent()     // Catch:{ Throwable -> 0x0038 }
            java.lang.String r2 = "countryCodeBetweenView"
            java.lang.String r6 = r6.getStringExtra(r2)     // Catch:{ Throwable -> 0x0038 }
            r0 = r6
            r6 = r1
            goto L_0x0045
        L_0x0038:
            r6 = move-exception
            r4 = r1
            r1 = r6
            r6 = r4
            goto L_0x003e
        L_0x003d:
            r1 = move-exception
        L_0x003e:
            java.lang.String r2 = "LoginManualSmsActivity"
            java.lang.String r3 = "get intent"
            com.ali.user.mobile.log.AliUserLog.b(r2, r3, r1)
        L_0x0045:
            java.lang.String r1 = r5.mLoginId
            boolean r1 = com.ali.user.mobile.util.StringUtil.e(r1)
            if (r1 == 0) goto L_0x0053
            r0 = 4
            java.lang.String r6 = com.ali.user.mobile.util.StringUtil.a(r6, r0)
            goto L_0x0067
        L_0x0053:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "-"
            r1.append(r0)
            r1.append(r6)
            java.lang.String r6 = r1.toString()
        L_0x0067:
            r5.initTip(r6)
            java.lang.String r6 = ""
            java.lang.String r0 = "LoginManualSmsActivity"
            java.lang.String r1 = "login"
            java.lang.String r2 = ""
            com.ali.user.mobile.log.LoggerUtils.a(r6, r0, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.login.ui.LoginManualSmsActivity.onCreate(android.os.Bundle):void");
    }

    public void verifySms(final String str) {
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                LoginManualSmsActivity.this.verifySmsAndLoginBackground(str);
            }
        });
    }

    /* access modifiers changed from: private */
    public void verifySmsAndLoginBackground(String str) {
        AliUserLog.c(TAG, "start to verify sms code");
        showProgress("");
        try {
            final LoginParam loginParam = new LoginParam();
            loginParam.loginAccount = this.mLoginId;
            loginParam.smsCode = str;
            loginParam.token = this.mToken;
            loginParam.validateTpye = "withsndmsg";
            final UnifyLoginRes a = AliuserLoginContext.d().a(loginParam);
            runOnUiThread(new Runnable() {
                public void run() {
                    LoginManualSmsActivity.this.afterBackgroundLogin(loginParam, a);
                }
            });
        } catch (RpcException e) {
            AliUserLog.c(TAG, "not client error,throw rpcExp");
            dismissProgress();
            throw e;
        }
    }

    /* access modifiers changed from: protected */
    public void afterBackgroundLogin(LoginParam loginParam, UnifyLoginRes unifyLoginRes) {
        if (unifyLoginRes == null) {
            dismissProgress();
            toast(getResources().getString(R.string.bC), 3000);
            LogAgent.a(new RuntimeException("loginRes == null after setDoublePassword"));
            return;
        }
        StringBuilder sb = new StringBuilder("doBackgroundLogin result,code:");
        sb.append(unifyLoginRes.code);
        sb.append(",msg:");
        sb.append(unifyLoginRes.msg);
        AliUserLog.c(TAG, sb.toString());
        if ("200".equals(unifyLoginRes.code) || "1000".equals(unifyLoginRes.code)) {
            AliuserLoginContext.f().onLoginPreFinish(unifyLoginRes);
            return;
        }
        dismissProgress();
        if ("6213".equals(unifyLoginRes.code)) {
            AliUserLog.c(TAG, "session timeout");
            alert(null, unifyLoginRes.msg, getResources().getString(R.string.H), null, null, null);
        } else if ("6408".equals(unifyLoginRes.code)) {
            AliUserLog.c(TAG, "session timeout");
            alert(null, unifyLoginRes.msg, getResources().getString(R.string.bX), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    LoginManualSmsActivity.this.clearInput();
                    LoginManualSmsActivity.this.hideHints();
                    LoginManualSmsActivity.this.sendSms();
                }
            }, null, null);
        } else if ("6409".equals(unifyLoginRes.code) || "6411".equals(unifyLoginRes.code)) {
            AliUserLog.c(TAG, "session timeout");
            showError(unifyLoginRes.msg);
            showHint();
        } else {
            AliUserLog.c(TAG, "doBackgroundLogin other error");
            Intent intent = getIntent();
            intent.putExtra("login_response", unifyLoginRes);
            intent.putExtra("login_param", loginParam);
            setResult(8194, intent);
            finish();
        }
    }

    public void sendSms() {
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                LoginManualSmsActivity.this.sendSmsBackground();
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendSmsBackground() {
        AliUserLog.c(TAG, "resend sms code");
        showProgress("");
        try {
            MsgLoginParam msgLoginParam = new MsgLoginParam();
            msgLoginParam.loginId = this.mLoginId;
            msgLoginParam.token = this.mToken;
            final LoginSendMSGResPb a = AliuserLoginContext.d().a(msgLoginParam);
            AliUserLog.c(TAG, String.format("initMsgLogin result, code:%s, memo:%s", new Object[]{a.resultCode, a.memo}));
            dismissProgress();
            runOnUiThread(new Runnable() {
                public void run() {
                    LoginManualSmsActivity.this.onSendSmsResponse(a);
                }
            });
        } catch (RpcException e) {
            dismissProgress();
            throw e;
        }
    }

    /* access modifiers changed from: private */
    public void onSendSmsResponse(LoginSendMSGResPb loginSendMSGResPb) {
        if (loginSendMSGResPb == null) {
            AliUserLog.d(TAG, "LoginSendMSGResPb=null");
            return;
        }
        showHint();
        if ("1000".equals(loginSendMSGResPb.resultCode)) {
            this.mToken = loginSendMSGResPb.token;
            startCountDown();
        } else if ("6410".equals(loginSendMSGResPb.resultCode)) {
            alert("", loginSendMSGResPb.memo, getString(R.string.bt), null, null, null);
        } else {
            showError(loginSendMSGResPb.memo);
        }
    }

    public void setAppId() {
        this.mAppId = "20000008";
    }
}
