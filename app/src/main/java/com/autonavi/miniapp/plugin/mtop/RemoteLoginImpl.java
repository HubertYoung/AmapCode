package com.autonavi.miniapp.plugin.mtop;

import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.SourceType;
import com.alipay.mobile.securitycommon.aliauth.AliAuthResult;
import com.alipay.mobile.securitycommon.aliauth.AliAuthService;
import com.taobao.tao.remotebusiness.login.IRemoteLogin;
import com.taobao.tao.remotebusiness.login.LoginContext;
import com.taobao.tao.remotebusiness.login.onLoginListener;

public class RemoteLoginImpl implements IRemoteLogin {
    private static final String TAG = "RemoteLoginImpl";
    private boolean isLoging;
    private AliAuthResult mAliAuthResult;

    public boolean isSessionValid() {
        return true;
    }

    public void login(onLoginListener onloginlistener, boolean z) {
        this.isLoging = true;
        Bundle bundle = new Bundle();
        bundle.putString(Key.SOURCE_TYPE, SourceType.NATIVE);
        bundle.putBoolean(Key.SHOW_UI, false);
        AliAuthResult autoLogin = AliAuthService.getService().autoLogin(bundle);
        if (!autoLogin.success) {
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb = new StringBuilder("autoLogin fail! result = ");
            sb.append(autoLogin.toString());
            traceLogger.info(TAG, sb.toString());
            onloginlistener.onLoginFail();
        } else if (this.mAliAuthResult == autoLogin) {
            LoggerFactory.getTraceLogger().info(TAG, "autoLogin hit cache!");
            onloginlistener.onLoginSuccess();
            this.isLoging = false;
        } else {
            this.mAliAuthResult = autoLogin;
            TraceLogger traceLogger2 = LoggerFactory.getTraceLogger();
            StringBuilder sb2 = new StringBuilder("autoLogin result = ");
            sb2.append(autoLogin.toString());
            traceLogger2.info(TAG, sb2.toString());
            onloginlistener.onLoginSuccess();
        }
    }

    public boolean isLogining() {
        return this.isLoging;
    }

    public LoginContext getLoginContext() {
        if (this.mAliAuthResult == null) {
            return null;
        }
        LoginContext loginContext = new LoginContext();
        loginContext.userId = this.mAliAuthResult.tbUserId;
        loginContext.nickname = this.mAliAuthResult.tbNick;
        loginContext.sid = this.mAliAuthResult.sid;
        return loginContext;
    }
}
