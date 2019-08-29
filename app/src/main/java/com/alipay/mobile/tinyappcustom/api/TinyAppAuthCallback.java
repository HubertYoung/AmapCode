package com.alipay.mobile.tinyappcustom.api;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthCallback;

public interface TinyAppAuthCallback {
    void getMCAuthLoginInfo(String str, Bundle bundle, IAccountOAuthCallback iAccountOAuthCallback);

    void getMCAuthLoginInfo(String str, IAccountOAuthCallback iAccountOAuthCallback);

    String getMcBindAlipayUid();
}
