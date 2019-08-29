package com.ali.user.mobile.external.InSideService;

import android.os.Bundle;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.accountbiz.extservice.AuthService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.accountbiz.sp.SecurityShareStore;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class LoginUserInfoService implements IInsideService<Void, Bundle> {
    public void start(Void voidR) throws Exception {
        throw new UnsupportedOperationException();
    }

    public void start(IInsideServiceCallback<Bundle> iInsideServiceCallback, Void voidR) throws Exception {
        if (iInsideServiceCallback != null) {
            iInsideServiceCallback.onComplted(startForResult((Void) null));
        }
    }

    public Bundle startForResult(Void voidR) throws Exception {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("loginId", SecurityShareStore.a(LauncherApplication.a(), "currentLogonId"));
            AuthService authService = AntExtServiceManager.getAuthService(LauncherApplication.a());
            if (authService != null) {
                bundle.putBoolean("isLogin", authService.isLogin());
                UserInfo userInfo = authService.getUserInfo();
                if (userInfo != null) {
                    bundle.putString("userId", userInfo.getUserId());
                    bundle.putString("nickName", userInfo.getNick());
                    bundle.putString("userAvatar", userInfo.getUserAvatar());
                }
            }
        } catch (Throwable th) {
            AliUserLog.b("LoginUserInfoService", "getUserInfo error ", th);
        }
        return bundle;
    }
}
