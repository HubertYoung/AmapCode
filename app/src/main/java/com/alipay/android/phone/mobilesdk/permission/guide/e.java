package com.alipay.android.phone.mobilesdk.permission.guide;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.permission.utils.h;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Arrays;

/* compiled from: PermissionGuideUtils */
public final class e {
    private static String b(Context ctx, String uri) {
        String replaced = uri;
        try {
            if (replaced.contains("${appPackage}")) {
                replaced = replaced.replace("${appPackage}", ctx.getPackageName());
            }
            if (replaced.contains("${appUid}")) {
                replaced = replaced.replace("${appUid}", Integer.toString(ctx.getApplicationInfo().uid));
            }
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", tr);
        }
        LoggerFactory.getTraceLogger().info("Permissions", "replace placeholder, before: " + uri + ", after: " + replaced);
        return replaced;
    }

    public static Intent a(Context context, String uri) {
        Intent intent = null;
        if (TextUtils.isEmpty(uri)) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "uri == null");
        } else {
            try {
                Intent intent_ = Intent.parseUri(b(context, uri), 0);
                if (intent_ == null) {
                    LoggerFactory.getTraceLogger().warn((String) "Permissions", "uri=" + uri + " => Intent.parseUri(uri) == null");
                } else {
                    ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent_, 0);
                    if (resolveInfo == null) {
                        LoggerFactory.getTraceLogger().warn((String) "Permissions", "uri=" + uri + " => resolveInfo == null");
                    } else if (resolveInfo.activityInfo == null) {
                        LoggerFactory.getTraceLogger().warn((String) "Permissions", "uri=" + uri + " => resolveInfo.activityInfo == null.");
                    } else {
                        intent = intent_;
                    }
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            }
        }
        LoggerFactory.getTraceLogger().info("Permissions", "uri=" + uri + " => Intent.parseUri(uri)=" + intent);
        return intent;
    }

    public static Intent a(Context context) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, 0);
            if (resolveInfo == null) {
                LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "getAppDetailsIntent => resolveInfo == null");
                return null;
            } else if (resolveInfo.activityInfo != null) {
                return intent;
            } else {
                LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "getAppDetailsIntent => resolveInfo.activityInfo == null.");
                return null;
            }
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return null;
        }
    }

    public static String a(String key) {
        if (!key.contains(",")) {
            return key;
        }
        String[] ary = key.split("_");
        return a(ary[0], ary[1].split(","));
    }

    public static String a(String bizType, String... permissions) {
        return bizType + "_" + a(permissions);
    }

    public static String a(String... permissions) {
        Arrays.sort(permissions);
        return h.a(permissions, "-");
    }

    public static int a(PermissionType[] permissions, String permissionName) {
        int N = permissions.length;
        for (int i = 0; i < N; i++) {
            if (TextUtils.equals(permissions[i].name(), permissionName)) {
                return i;
            }
        }
        return -1;
    }
}
