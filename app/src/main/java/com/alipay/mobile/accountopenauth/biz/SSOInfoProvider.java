package com.alipay.mobile.accountopenauth.biz;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class SSOInfoProvider {
    public static Bundle a() {
        Bundle bundle = new Bundle();
        try {
            IInsideService b = PluginManager.b("ACCOUNT_SSO_INFO_SERVICE");
            if (b != null) {
                return (Bundle) b.startForResult(Boolean.TRUE);
            }
            return bundle;
        } catch (Throwable th) {
            LoggerFactory.f().a("SSOInfoProvider", "getVerifiedSSOInfo", th);
            return bundle;
        }
    }

    public static Bundle b() {
        Bundle bundle = new Bundle();
        try {
            IInsideService b = PluginManager.b("ACCOUNT_SSO_INFO_SERVICE");
            if (b != null) {
                return (Bundle) b.startForResult(Boolean.FALSE);
            }
            return bundle;
        } catch (Throwable th) {
            LoggerFactory.f().a("SSOInfoProvider", "getNonVerifiedSSOInfo", th);
            return bundle;
        }
    }
}
