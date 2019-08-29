package com.alipay.android.phone.inside.api.accountopenauth;

import android.os.Bundle;

public interface IFastOAuthService {
    boolean canShowFastPage(long j);

    void openH5Page(Bundle bundle);
}
