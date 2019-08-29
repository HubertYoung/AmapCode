package com.alipay.mobile.accountopenauth.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.style.URLSpan;
import android.view.View;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.accountopenauth.IFastOAuthService;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;

public class TaoUrlSpan extends URLSpan {
    private static final String TAG = "TaoUrlSpan";
    private Context context;
    private String mTitle;

    public TaoUrlSpan(String str) {
        super(str);
    }

    public TaoUrlSpan setContext(Context context2) {
        this.context = context2;
        return this;
    }

    public TaoUrlSpan setTitle(String str) {
        this.mTitle = str;
        return this;
    }

    public void onClick(View view) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("url", getURL());
            StringBuilder sb = new StringBuilder("Url=");
            sb.append(getURL());
            OAuthTraceLogger.a((String) TAG, sb.toString());
            IFastOAuthService fastOAuthService = AccountOAuthServiceManager.getInstance().getFastOAuthService();
            if (fastOAuthService != null) {
                OAuthTraceLogger.a((String) TAG, (String) "fastOAuthService is not null");
                fastOAuthService.openH5Page(bundle);
            }
        } catch (Throwable th) {
            OAuthTraceLogger.a(TAG, "agreements click error", th);
        }
    }
}
