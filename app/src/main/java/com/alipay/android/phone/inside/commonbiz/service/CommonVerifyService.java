package com.alipay.android.phone.inside.commonbiz.service;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.commonbiz.verify.AccountIdentityVerify;
import com.alipay.android.phone.inside.commonbiz.verify.SecurityIdentityVerify;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;
import com.alipay.sdk.util.e;

public class CommonVerifyService extends AbstractInsideService<Bundle, Bundle> {
    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        Bundle bundle = (Bundle) obj;
        Context context = getContext();
        Bundle bundle2 = new Bundle();
        boolean z = true;
        if (!(ApkVerifyTool.checkAlipayStatus(context, 110) == WalletStatusEnum.SUCCESS)) {
            LoggerFactory.f().e("inside", "CommonVerifyService::startForResult > alipay status=false");
            bundle2.putString("verifyState", "alipay_not_install");
            return bundle2;
        }
        String string = bundle.getString("loginId", "");
        if (TextUtils.isEmpty(string)) {
            LoggerFactory.f().b((String) "inside", (String) "CommonVerifyService::startForResult > in_params_login_id empty!");
            string = RunningConfig.h();
        }
        LoggerFactory.f().b((String) "inside", "CommonVerifyService::startForResult > loginId:".concat(String.valueOf(string)));
        String string2 = bundle.getString("RELEASE_RISK_TYPE", "");
        if (TextUtils.equals(string2, "NEED_VERIFY")) {
            LoggerFactory.f().e("inside", "CommonVerifyService::startForResult > NEED_VERIFY");
            String string3 = bundle.getString(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID, "");
            AccountIdentityVerify accountIdentityVerify = new AccountIdentityVerify();
            String string4 = bundle.getString("verifyMode", "");
            if (TextUtils.isEmpty(string4)) {
                string4 = "token";
            }
            z = accountIdentityVerify.a(context, string, string4, string3);
        } else if (TextUtils.equals(string2, "SECURITY_NEED_CHECK")) {
            LoggerFactory.f().e("inside", "CommonVerifyService::startForResult > SECURITY_NEED_CHECK");
            String string5 = bundle.getString("securityCheckUrl", "");
            LoggerFactory.f().e("inside", "CommonVerifyService::startForResult > securityCheckUrl > ".concat(String.valueOf(string5)));
            z = new SecurityIdentityVerify().a(context, string, string5);
        }
        LoggerFactory.f().e("inside", "CommonVerifyService::startForResult > success=".concat(String.valueOf(z)));
        bundle2.putString("verifyState", z ? "success" : e.b);
        return bundle2;
    }
}
