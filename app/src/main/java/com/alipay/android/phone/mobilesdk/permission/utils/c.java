package com.alipay.android.phone.mobilesdk.permission.utils;

import com.alipay.android.phone.mobilesdk.permission.guide.PermissionStatus;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionType;
import com.alipay.android.phone.mobilesdk.permission.sdk.IPermissionGuideDelegator;
import com.alipay.android.phone.mobilesdk.permission.sdk.PGDelegatorResult;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.List;

/* compiled from: DelegatorDispatcher */
public final class c {
    public static PermissionStatus a(List<IPermissionGuideDelegator> delegators, PermissionType permissionType) {
        if (delegators == null) {
            return null;
        }
        try {
            if (delegators.isEmpty()) {
                return null;
            }
            for (IPermissionGuideDelegator delegator : delegators) {
                if (delegator.getCarePermissionTypeList().contains(permissionType)) {
                    LoggerFactory.getTraceLogger().info("DelegatorDispatcher", "Delegator(" + delegator.getName() + ") consume checkPermissionStatus, permission: " + permissionType);
                    return delegator.checkPermissionStatus(permissionType);
                }
            }
            return null;
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("DelegatorDispatcher", "Dispatch checkPermissionStatus error", tr);
            return null;
        }
    }

    public static PGDelegatorResult a(List<IPermissionGuideDelegator> delegators, String bizType, PermissionType[] permissions, boolean force) {
        if (delegators == null) {
            return null;
        }
        try {
            if (delegators.isEmpty()) {
                return null;
            }
            if (permissions == null || permissions.length != 1) {
                LoggerFactory.getTraceLogger().error((String) "DelegatorDispatcher", (String) "Permission Guide Delegator support only single permission!");
                return null;
            }
            for (IPermissionGuideDelegator delegator : delegators) {
                if (delegator.getCarePermissionTypeList().contains(permissions[0])) {
                    LoggerFactory.getTraceLogger().info("DelegatorDispatcher", "Delegator(" + delegator.getName() + ") consume startPermissionGuide, permission: " + permissions[0]);
                    return delegator.overridePermissionGuideBehavior(bizType, permissions, force);
                }
            }
            return null;
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("DelegatorDispatcher", "Dispatch startPermissionGuide error", tr);
            return null;
        }
    }
}
