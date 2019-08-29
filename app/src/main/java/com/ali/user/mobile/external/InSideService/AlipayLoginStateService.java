package com.ali.user.mobile.external.InSideService;

import android.text.TextUtils;
import com.ali.user.mobile.login.sso.impl.SsoServiceImpl;
import com.ali.user.mobile.login.sso.info.SsoLoginInfo;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;

public class AlipayLoginStateService implements IInsideService<Void, String> {
    private static final String HAS_LOGIN = "1";
    private static final String HAS_NOT_LOGIN = "0";

    public void start(Void voidR) throws Exception {
        throw new UnsupportedOperationException();
    }

    public void start(IInsideServiceCallback<String> iInsideServiceCallback, Void voidR) throws Exception {
        if (iInsideServiceCallback != null) {
            iInsideServiceCallback.onComplted(startForResult((Void) null));
        }
    }

    public String startForResult(Void voidR) throws Exception {
        return getLoginState();
    }

    private String getLoginState() {
        SsoLoginInfo a = SsoServiceImpl.a(LauncherApplication.a().getApplicationContext()).a();
        return (a == null || TextUtils.isEmpty(a.loginToken) || TextUtils.isEmpty(a.loginId)) ? "0" : "1";
    }
}
