package com.alipay.mobile.accountopenauth.biz.oauthstrategy;

import android.os.Bundle;
import com.alipay.mobile.accountopenauth.api.OAuthStrategy;

public class StrategyContext {
    private OAuthStrategy a;

    public StrategyContext(OAuthStrategy oAuthStrategy) {
        this.a = oAuthStrategy;
    }

    public final Bundle a(String str, String str2, Bundle bundle) {
        return this.a.a(str, str2, bundle);
    }
}
