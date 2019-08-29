package com.alipay.android.phone.inside.api.accountopenauth;

import android.os.Bundle;

public interface IAccountOAuthService {
    void getMCAuthLoginInfo(String str, String str2, String str3, IAccountOAuthCallback iAccountOAuthCallback);

    void openH5Page(Bundle bundle);
}
