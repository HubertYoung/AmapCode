package com.alipay.android.phone.mobilesdk.permission.guide;

import android.app.Activity;
import android.content.Context;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class PermissionGuideService extends ExternalService {
    public abstract PermissionStatus checkPermissionStatus(String str);

    public abstract PermissionStatus checkPermissionStatus(String str, boolean z);

    public abstract String getPermissionGuideContent(String str, PermissionType permissionType);

    public abstract boolean hasPermission(Context context, String str);

    public abstract boolean hasPermission(String str);

    public abstract boolean hasPermissionGuidePath(String str, PermissionType permissionType);

    public abstract boolean[] isPermissionGuideValid(String str, PermissionType[] permissionTypeArr);

    public abstract void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);

    public abstract void requestPermission(Activity activity, String[] strArr);

    public abstract void requestPermission(Activity activity, String[] strArr, boolean z);

    public abstract boolean startPermissionGuide(Activity activity, String str, PermissionType[] permissionTypeArr, PermissionGuideCallback permissionGuideCallback);

    public abstract boolean startPermissionGuide(Activity activity, String str, PermissionType[] permissionTypeArr, PermissionGuideCallback permissionGuideCallback, boolean z);

    public abstract boolean startPermissionGuide(Activity activity, String str, PermissionType[] permissionTypeArr, PermissionGuideCallback permissionGuideCallback, boolean z, boolean z2);

    public abstract boolean startPermissionGuide(String str, PermissionType[] permissionTypeArr, PermissionGuideCallback permissionGuideCallback);

    public abstract boolean startPermissionGuide(String str, PermissionType[] permissionTypeArr, PermissionGuideCallback permissionGuideCallback, boolean z);

    public abstract void startPermissionPathActivity(Activity activity, String str, PermissionType permissionType);

    public abstract void startPermissionPathActivity(String str, PermissionType permissionType);
}
