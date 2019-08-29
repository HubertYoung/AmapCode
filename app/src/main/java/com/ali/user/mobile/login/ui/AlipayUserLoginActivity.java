package com.ali.user.mobile.login.ui;

import android.content.Intent;
import android.os.Bundle;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.accountbiz.sp.SharedPreferencesManager;
import com.ali.user.mobile.biz.AliUserSdkLoginBiz;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public class AlipayUserLoginActivity extends AliUserLoginActivity {
    private static final String TAG = "AlipayUserLoginActivity";

    public void onCreate(Bundle bundle) {
        StringBuilder sb = new StringBuilder("liangzi.dlz onCreate:");
        sb.append(bundle);
        sb.append(",instance:");
        sb.append(this);
        AliUserLog.c(TAG, sb.toString());
        AliUserLog.c(TAG, "try to create instance of AliUserSdkLoginBiz in activity");
        LauncherApplication.a(this);
        AliUserSdkLoginBiz.a(getApplicationContext());
        super.onCreate(bundle);
        loginTrace(getIntent());
        LoggerUtils.a("", TAG, "login", "");
    }

    public void onResume() {
        AliUserLog.c(TAG, "onResume, instance:".concat(String.valueOf(this)));
        super.onResume();
    }

    public void onPause() {
        AliUserLog.c(TAG, "onPause, instance:".concat(String.valueOf(this)));
        super.onPause();
    }

    public void onDestroy() {
        AliUserLog.c(TAG, "onDestroy, instance:".concat(String.valueOf(this)));
        super.onDestroy();
        if (!this.mIsLoginSuccess && !isLoginForCashier() && !isFromFirstPage()) {
            AliUserLog.c(TAG, "未登录被销毁，通知登录失败");
            notifyAuthCenter(false, false);
        }
    }

    private boolean isLoginForCashier() {
        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey("findLoginAppWhenCashier")) {
            return false;
        }
        boolean z = extras.getBoolean("findLoginAppWhenCashier");
        AliUserLog.c(TAG, String.format("this login from phonecashier:%s", new Object[]{Boolean.valueOf(!z)}));
        if (!z) {
            return true;
        }
        return false;
    }

    public boolean onLoginResponse(UnifyLoginRes unifyLoginRes) {
        if (!"2003".equals(unifyLoginRes.code)) {
            return super.onLoginResponse(unifyLoginRes);
        }
        dismissProgress();
        return false;
    }

    public void handleRpcException(RpcException rpcException) {
        super.handleRpcException(rpcException);
    }

    public void finishAndNotify() {
        if (isFromFirstPage()) {
            AliUserLog.c(TAG, "从引导页调起登录页，后退的时候不释放锁");
        } else {
            AliUserLog.c(TAG, "通知等待的登陆线程并且退出登陆");
            notifyAuthCenter(false, false);
        }
        super.finishAndNotify();
    }

    private boolean isFromFirstPage() {
        return "firstpage".equals(getIntent().getStringExtra("flag"));
    }

    private void notifyAuthCenter(boolean z, boolean z2) {
        AliUserLog.c(TAG, String.format("notifyAuthCenter,loginSuccess:%s,aotoLogin:%s", new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2)}));
        try {
            AntExtServiceManager.getAuthService(getApplicationContext()).notifyUnlockLoginApp(z, z2);
        } catch (Exception e) {
            AliUserLog.a((String) TAG, (Throwable) e);
        }
    }

    private void loginTrace(Intent intent) {
        boolean z;
        LoginParam loginParam;
        if (intent == null || intent.getExtras() == null) {
            loginParam = null;
            z = false;
        } else {
            loginParam = (LoginParam) intent.getExtras().getSerializable("login_param");
            z = intent.getExtras().getBoolean("from_register");
        }
        String str = loginParam == null ? "loginWithOutUserName-" : "loginWithUserName-";
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(z ? "aliuser.regist" : "aliuser.login");
        writeLoginLog(sb.toString());
    }

    private void writeLoginLog(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(MergeUtil.SEPARATOR_KV);
        stringBuffer.append(sb.toString());
        String isAutoLoginTrace = getIsAutoLoginTrace();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(stringBuffer.toString());
        sb2.append(isAutoLoginTrace);
        writeLoginLogInternal(sb2.toString());
    }

    private String getIsAutoLoginTrace() {
        try {
            return SharedPreferencesManager.a(AliUserInit.b(), "isAutoLogin", 0).a("trace", "");
        } catch (Exception e) {
            AliUserLog.b((String) TAG, e.getMessage());
            return "";
        }
    }

    private void writeLoginLogInternal(String str) {
        LogAgent.f("UC-ZHAQ-56", "loginTrace", str, "", "");
    }
}
