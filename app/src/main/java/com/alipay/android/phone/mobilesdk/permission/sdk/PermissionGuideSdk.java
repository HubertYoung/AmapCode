package com.alipay.android.phone.mobilesdk.permission.sdk;

import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionType;
import com.alipay.android.phone.mobilesdk.permission.guide.a;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PermissionGuideSdk {
    public static final String TAG = "PermissionGuideSdk";
    private static PermissionGuideSdk mInstance;
    private String mAppCode = "wallet";
    private List<IPermissionGuideDelegator> mDelegators = Collections.emptyList();
    private DefaultGuideConfigure mGuideConfigure = new a();

    public interface DefaultGuideConfigure {
        String getDefaultGuideContent(PermissionType permissionType);

        String getDefaultGuideTitle(PermissionType permissionType);

        String getTextForConfirm();

        String getTextForGoToSettings();
    }

    private PermissionGuideSdk() {
    }

    private static PermissionGuideSdk getInstance() {
        if (mInstance == null) {
            synchronized (PermissionGuideSdk.class) {
                if (mInstance == null) {
                    mInstance = new PermissionGuideSdk();
                }
            }
        }
        return mInstance;
    }

    public static void init(String appCode, DefaultGuideConfigure configure) {
        PermissionGuideSdk sdk = getInstance();
        sdk.mAppCode = (String) com.alipay.android.phone.mobilesdk.permission.utils.a.a(appCode, "PermissionGuideSdk, appCode can't be null!");
        sdk.mGuideConfigure = (DefaultGuideConfigure) com.alipay.android.phone.mobilesdk.permission.utils.a.a(configure, "PermissionGuideSdk, configure can't be null!");
    }

    public static DefaultGuideConfigure getDefaultGuideConfigure() {
        return getInstance().mGuideConfigure;
    }

    public static String getAppCode() {
        return getInstance().mAppCode;
    }

    public static boolean registerDelegator(IPermissionGuideDelegator delegator) {
        synchronized (PermissionGuideSdk.class) {
            if (delegator != null) {
                try {
                    if (delegator.getCarePermissionTypeList() != null && !delegator.getCarePermissionTypeList().isEmpty() && !TextUtils.isEmpty(delegator.getName())) {
                        List careList = delegator.getCarePermissionTypeList();
                        List<IPermissionGuideDelegator> oldDelegators = getInstance().mDelegators;
                        for (IPermissionGuideDelegator carePermissionTypeList : oldDelegators) {
                            Iterator<PermissionType> it = carePermissionTypeList.getCarePermissionTypeList().iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    PermissionType type = it.next();
                                    if (careList.contains(type)) {
                                        LoggerFactory.getTraceLogger().error((String) TAG, "Register permission guide delegator error, duplicate care list! delegator: " + delegator.getName() + ", duplicated permission: " + type);
                                        return false;
                                    }
                                }
                            }
                        }
                        List newDelegators = new ArrayList(oldDelegators.size() + 1);
                        Collections.copy(newDelegators, oldDelegators);
                        newDelegators.add(delegator);
                        getInstance().mDelegators = Collections.unmodifiableList(newDelegators);
                        LoggerFactory.getTraceLogger().info(TAG, "Register permission guide delegator successful, delegator: " + delegator.getName() + ", care permissions: " + delegator.getCarePermissionTypeList());
                        return true;
                    }
                } catch (Throwable tr) {
                    LoggerFactory.getTraceLogger().error(TAG, "Register permission guide delegator error", tr);
                    return false;
                }
            }
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "Register permission guide delegator error, delegator is invalid!");
            return false;
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:18:0x0037=Splitter:B:18:0x0037, B:10:0x0019=Splitter:B:10:0x0019} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void unregisterDelegator(com.alipay.android.phone.mobilesdk.permission.sdk.IPermissionGuideDelegator r7) {
        /*
            java.lang.Class<com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk> r4 = com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk.class
            monitor-enter(r4)
            if (r7 != 0) goto L_0x0007
            monitor-exit(r4)     // Catch:{ all -> 0x001b }
        L_0x0006:
            return
        L_0x0007:
            com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk r3 = getInstance()     // Catch:{ Throwable -> 0x0039 }
            java.util.List<com.alipay.android.phone.mobilesdk.permission.sdk.IPermissionGuideDelegator> r1 = r3.mDelegators     // Catch:{ Throwable -> 0x0039 }
            boolean r3 = r1.isEmpty()     // Catch:{ Throwable -> 0x0039 }
            if (r3 != 0) goto L_0x0019
            boolean r3 = r1.contains(r7)     // Catch:{ Throwable -> 0x0039 }
            if (r3 != 0) goto L_0x001e
        L_0x0019:
            monitor-exit(r4)     // Catch:{ all -> 0x001b }
            goto L_0x0006
        L_0x001b:
            r3 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x001b }
            throw r3
        L_0x001e:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0039 }
            int r3 = r1.size()     // Catch:{ Throwable -> 0x0039 }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x0039 }
            java.util.Collections.copy(r0, r1)     // Catch:{ Throwable -> 0x0039 }
            r0.remove(r7)     // Catch:{ Throwable -> 0x0039 }
            com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk r3 = getInstance()     // Catch:{ Throwable -> 0x0039 }
            java.util.List r5 = java.util.Collections.unmodifiableList(r0)     // Catch:{ Throwable -> 0x0039 }
            r3.mDelegators = r5     // Catch:{ Throwable -> 0x0039 }
        L_0x0037:
            monitor-exit(r4)     // Catch:{ all -> 0x001b }
            goto L_0x0006
        L_0x0039:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x001b }
            java.lang.String r5 = "PermissionGuideSdk"
            java.lang.String r6 = "Unregister permission guide delegator error"
            r3.error(r5, r6, r2)     // Catch:{ all -> 0x001b }
            goto L_0x0037
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk.unregisterDelegator(com.alipay.android.phone.mobilesdk.permission.sdk.IPermissionGuideDelegator):void");
    }

    public static List<IPermissionGuideDelegator> getDelegators() {
        return getInstance().mDelegators;
    }
}
