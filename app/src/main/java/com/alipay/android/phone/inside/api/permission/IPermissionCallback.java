package com.alipay.android.phone.inside.api.permission;

import android.os.Bundle;

public interface IPermissionCallback {
    public static final String KEY_CLASS_NAME = "className";
    public static final String KEY_PACKAGE_NAME = "packageName";

    void onEnd(boolean z);

    boolean onStartActivity(Bundle bundle);
}
