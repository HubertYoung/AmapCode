package com.alipay.android.phone.mobilesdk.permission.sdk;

import com.alipay.android.phone.mobilesdk.permission.guide.PermissionStatus;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionType;
import java.util.List;

public interface IPermissionGuideDelegator {
    PermissionStatus checkPermissionStatus(PermissionType permissionType);

    List<PermissionType> getCarePermissionTypeList();

    String getName();

    PGDelegatorResult overridePermissionGuideBehavior(String str, PermissionType[] permissionTypeArr, boolean z);
}
