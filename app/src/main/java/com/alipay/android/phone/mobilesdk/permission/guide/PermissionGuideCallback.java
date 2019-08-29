package com.alipay.android.phone.mobilesdk.permission.guide;

public interface PermissionGuideCallback {
    void onPermissionGuideResult(PermissionType[] permissionTypeArr, PermissionGuideResult[] permissionGuideResultArr);
}
