package com.ali.user.mobile.external.InSideService;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.accountbiz.sp.SecurityShareStore;
import com.ali.user.mobile.authlogin.AlipaySsoAPIFactory;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.external.AuthLoginResultActivity;
import com.ali.user.mobile.external.LoginSsoActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.autonavi.miniapp.plugin.lbs.H5Location;

public class LoginProcessAlipayParamsService implements IInsideService<Bundle, String> {
    private static final String TAG = "LoginProcessAlipayParamsService";

    public LoginProcessAlipayParamsService() {
        AliUserLog.c(TAG, "LoginProcessAlipayParamsService service constructor");
    }

    public void start(Bundle bundle) {
        AliUserLog.c(TAG, "LoginProcessAlipayParamsService start 1");
        throw new UnsupportedOperationException();
    }

    public void start(IInsideServiceCallback iInsideServiceCallback, Bundle bundle) {
        AliUserLog.c(TAG, "LoginProcessAlipayParamsService start 2.0");
        if (bundle == null) {
            AliUserLog.c(TAG, "params is null,return");
            throw new UnsupportedOperationException();
        }
        SecurityShareStore.a(LauncherApplication.a().getApplicationContext(), (String) "insideLoginType", bundle.getString("insideLoginType"));
        String string = bundle.getString(H5Location.REQUEST_TYPE);
        if ("getJumpAlipayParams".equals(string)) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                AliUserLog.c(TAG, "in main thread,invode this method can not in UIThread");
                throw new UnsupportedOperationException();
            } else {
                doGetJumpAlipayParams(iInsideServiceCallback);
            }
        } else if ("processAuthLoginResponse".equals(string)) {
            doProcessAuthResult(bundle, iInsideServiceCallback);
        } else {
            AliUserLog.c(TAG, "do nothing,request params has some problem");
            throw new UnsupportedOperationException();
        }
    }

    private void doProcessAuthResult(Bundle bundle, IInsideServiceCallback iInsideServiceCallback) {
        AliuserLoginContext.a(iInsideServiceCallback);
        Bundle bundle2 = bundle.getBundle("authLoginResult");
        Intent intent = new Intent();
        intent.putExtras(bundle2);
        intent.setClass(LauncherApplication.a(), AuthLoginResultActivity.class);
        intent.setFlags(268435456);
        LauncherApplication.a().startActivity(intent);
    }

    private void doGetJumpAlipayParams(final IInsideServiceCallback iInsideServiceCallback) {
        AliuserLoginContext.a((IInsideServiceCallback) new IInsideServiceCallback<Bundle>() {
            public void onException(Throwable th) {
            }

            public void onComplted(Bundle bundle) {
                if (iInsideServiceCallback == null) {
                    AliUserLog.c(LoginProcessAlipayParamsService.TAG, "LoginProcessAlipayParamsService callback is null");
                } else if ("success".equals(bundle.getString("loginStatus"))) {
                    String access$000 = LoginProcessAlipayParamsService.this.getCurrentLoginId();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("loginId", access$000);
                    bundle2.putString("loginStatus", "success");
                    iInsideServiceCallback.onComplted(bundle2);
                } else {
                    AliUserLog.c(LoginProcessAlipayParamsService.TAG, "sso 登录失败，获取二方授权信息");
                    LoginProcessAlipayParamsService.this.doGetAuthLoginInfoAndCallback(iInsideServiceCallback);
                }
            }
        });
        checkLoginState();
    }

    public String startForResult(Bundle bundle) {
        AliUserLog.c(TAG, "LoginProcessAlipayParamsService startForResult");
        throw new UnsupportedOperationException();
    }

    private void checkLoginState() {
        if (AntExtServiceManager.getAuthService(LauncherApplication.a().getApplicationContext()).isLogin()) {
            doCallback();
        } else {
            goLoginSsoLoginPage();
        }
    }

    /* access modifiers changed from: private */
    public void doGetAuthLoginInfoAndCallback(final IInsideServiceCallback iInsideServiceCallback) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Bundle a = AlipaySsoAPIFactory.a(LauncherApplication.a().getApplicationContext()).a();
                    Bundle bundle = new Bundle();
                    bundle.putBundle("authLoginInfo", a);
                    iInsideServiceCallback.onComplted(bundle);
                } catch (Throwable th) {
                    AliUserLog.b(LoginProcessAlipayParamsService.TAG, "doGetAuthLoginInfo error", th);
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public String getCurrentLoginId() {
        return AntExtServiceManager.getAccountService(LauncherApplication.a().getApplicationContext()).getCurrentLoginLogonId();
    }

    private void doCallback() {
        IInsideServiceCallback g = AliuserLoginContext.g();
        Bundle bundle = new Bundle();
        bundle.putString("loginStatus", "success");
        g.onComplted(bundle);
    }

    private void goLoginSsoLoginPage() {
        Intent intent = new Intent(LauncherApplication.a(), LoginSsoActivity.class);
        intent.setFlags(268435456);
        LauncherApplication.a().startActivity(intent);
    }
}
