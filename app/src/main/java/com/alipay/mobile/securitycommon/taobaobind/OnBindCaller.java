package com.alipay.mobile.securitycommon.taobaobind;

import android.os.Bundle;

public interface OnBindCaller {
    void onBindError(Bundle bundle);

    void onBindSuccess(Bundle bundle);

    void onPostRpc();

    void onPreRpc();
}
