package com.alipay.android.phone.inside.api.permission;

import android.content.Context;

public interface IPermissionManager {
    void precheck(Context context, IPermissionCallback iPermissionCallback);
}
