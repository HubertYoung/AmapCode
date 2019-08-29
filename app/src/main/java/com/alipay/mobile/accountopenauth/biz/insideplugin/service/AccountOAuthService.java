package com.alipay.mobile.accountopenauth.biz.insideplugin.service;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.accountopenauth.biz.SSOInfoProvider;
import com.alipay.mobile.accountopenauth.biz.oauthstrategy.H5OAuthStrategy;
import com.alipay.mobile.accountopenauth.biz.oauthstrategy.H5RegOAuthStrategy;
import com.alipay.mobile.accountopenauth.biz.oauthstrategy.StrategyContext;
import com.alipay.mobile.accountopenauth.biz.oauthstrategy.WalletOAuthStrategy;
import com.alipay.mobile.accountopenauth.common.CommonUtil;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import org.json.JSONObject;

public class AccountOAuthService implements IInsideService<JSONObject, Bundle> {
    public /* synthetic */ void start(IInsideServiceCallback iInsideServiceCallback, Object obj) throws Exception {
        JSONObject jSONObject = (JSONObject) obj;
        if (jSONObject == null || iInsideServiceCallback == null) {
            OAuthTraceLogger.a((String) "AccountOAuthService", (String) "oauth param is null");
            Bundle bundle = new Bundle();
            bundle.putString("resultCode", "AUTH_FAILED");
            if (iInsideServiceCallback != null) {
                iInsideServiceCallback.onComplted(bundle);
            }
            return;
        }
        String optString = jSONObject.optString("authUrl");
        String optString2 = jSONObject.optString("phoneNumber");
        Bundle b = SSOInfoProvider.b();
        String string = b.getString("token");
        String string2 = b.getString("loginId");
        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
            OAuthBehaviorLogger.a("action", "Enter_OpenAuthLogin_Web_Direct", "oauth", "", "", BehaviorType.EVENT);
            iInsideServiceCallback.onComplted(new StrategyContext(new H5OAuthStrategy("oauth")).a(optString, "", b));
        } else if (CommonUtil.a((Context) LauncherApplication.a())) {
            OAuthBehaviorLogger.a("action", "Enter_OpenAuthLogin_Wallet", "oauth", "", "", BehaviorType.EVENT);
            iInsideServiceCallback.onComplted(new StrategyContext(new WalletOAuthStrategy("oauth")).a(optString, "", null));
        } else {
            OAuthBehaviorLogger.a("action", "Enter_OpenAuthLogin_Web_Reg", "oauth", "", "", BehaviorType.EVENT);
            StrategyContext strategyContext = new StrategyContext(new H5RegOAuthStrategy("oauth"));
            Bundle bundle2 = new Bundle();
            bundle2.putString("phoneNumber", optString2);
            iInsideServiceCallback.onComplted(strategyContext.a(optString, "", bundle2));
        }
    }

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ void start(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
