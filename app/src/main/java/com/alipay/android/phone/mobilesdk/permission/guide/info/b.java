package com.alipay.android.phone.mobilesdk.permission.guide.info;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilesdk.permission.BuildConfig;
import com.alipay.android.phone.mobilesdk.permission.c.a.C0096a;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionStatus;
import com.alipay.android.phone.mobilesdk.permission.guide.f;
import com.alipay.mobile.antui.dialog.AUImageDialog;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/* compiled from: PermissionChecker */
public final class b {
    /* access modifiers changed from: private */
    public static Set<String> a = new TreeSet();
    /* access modifiers changed from: private */
    public static Map<String, c> b = new HashMap();

    /* compiled from: PermissionChecker */
    static class a {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;

        a() {
        }
    }

    public static void a() {
        a((Runnable) new Runnable() {
            public final void run() {
                b.a = LoggerFactory.getLogContext().getApplicationContext().getSharedPreferences("first_guide_file", 0).getStringSet("first_guide_key", new TreeSet());
            }
        });
    }

    public static void a(String[] permissions) {
        a.addAll(Arrays.asList(permissions));
        a((Runnable) new Runnable() {
            public final void run() {
                Editor editor = LoggerFactory.getLogContext().getApplicationContext().getSharedPreferences("first_guide_file", 0).edit();
                editor.putStringSet("first_guide_key", b.a);
                editor.commit();
            }
        });
    }

    private static void a(Runnable runnable) {
        ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).execute(runnable);
    }

    public static PermissionStatus a(Context context) {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        if (VERSION.SDK_INT < 23) {
            return status;
        }
        try {
            return ContextCompat.checkSelfPermission(context, "android.permission.READ_CONTACTS") == 0 ? PermissionStatus.GRANTED : PermissionStatus.DENIED;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static PermissionStatus b(Context context) {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        if (VERSION.SDK_INT < 23) {
            return status;
        }
        try {
            return ContextCompat.checkSelfPermission(context, "android.permission.CAMERA") == 0 ? PermissionStatus.GRANTED : PermissionStatus.DENIED;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static PermissionStatus b() {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        try {
            return k() ? PermissionStatus.GRANTED : PermissionStatus.DENIED;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static PermissionStatus c() {
        return l() ? PermissionStatus.GRANTED : PermissionStatus.DENIED;
    }

    public static PermissionStatus c(Context context) {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        if (VERSION.SDK_INT < 23) {
            return status;
        }
        try {
            return ContextCompat.checkSelfPermission(context, "android.permission.RECORD_AUDIO") == 0 ? PermissionStatus.GRANTED : PermissionStatus.DENIED;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static PermissionStatus d() {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        try {
            int notificationPermission = f.b();
            if (notificationPermission == 0 && "false".equalsIgnoreCase(f.a((String) "switcherTreatNotificationUnknownStatusEnabled"))) {
                notificationPermission = 2;
            }
            return PermissionStatus.fromValue(notificationPermission);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static PermissionStatus e() {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        try {
            return PermissionStatus.fromValue(f.c());
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static PermissionStatus d(Context context) {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        try {
            return PermissionStatus.fromValue(f.a(context));
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static PermissionStatus f() {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        try {
            return f.a() ? PermissionStatus.GRANTED : PermissionStatus.DENIED;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static PermissionStatus g() {
        PermissionStatus status = PermissionStatus.NOT_SURE;
        try {
            return PermissionStatus.fromValue(f.d());
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) "Permissions", e);
            return status;
        }
    }

    public static int h() {
        try {
            Method methodGLP = Class.forName("com.alipay.mobile.common.lbs.LBSCommonUtil").getDeclaredMethod("getLocationPermission", new Class[0]);
            methodGLP.setAccessible(true);
            return ((Integer) methodGLP.invoke(null, new Object[0])).intValue();
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", "can't get lbs info from lbs-bundle, errMsg: " + tr.getMessage());
            return -1;
        }
    }

    private static boolean k() {
        try {
            Method methodGLP = Class.forName("com.alipay.mobile.common.lbs.LBSCommonUtil").getDeclaredMethod("isAppPermissionOPen", new Class[0]);
            methodGLP.setAccessible(true);
            return ((Boolean) methodGLP.invoke(null, new Object[0])).booleanValue();
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", "can't get lbs info from lbs-bundle, errMsg: " + tr.getMessage());
            return false;
        }
    }

    private static boolean l() {
        try {
            Method methodGLP = Class.forName("com.alipay.mobile.common.lbs.LBSCommonUtil").getDeclaredMethod("isGpsSwitchOPen", new Class[0]);
            methodGLP.setAccessible(true);
            return ((Boolean) methodGLP.invoke(null, new Object[0])).booleanValue();
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", "can't get lbs info from lbs-bundle, errMsg: " + tr.getMessage());
            return false;
        }
    }

    public static boolean a(Context context, String permission) {
        if (TextUtils.isEmpty(permission) || context == null) {
            LoggerFactory.getTraceLogger().info("Permissions", "hasPermission, permission is empty");
            return false;
        }
        if (VERSION.SDK_INT >= 23) {
            if ("android.permission.ACCESS_COARSE_LOCATION".equals(permission)) {
                return k();
            }
            if (ContextCompat.checkSelfPermission(context, permission) != 0) {
                return false;
            }
        }
        return true;
    }

    private static String b(String[] pArray) {
        StringBuilder sBuilder = new StringBuilder("");
        for (String permission : pArray) {
            if (!TextUtils.isEmpty(permission)) {
                sBuilder.append(permission).append("_");
            }
        }
        return sBuilder.toString();
    }

    public static void a(Activity activity, String[] permissions, int requestCode, boolean hasOnRequestPermissionsResultMethod) {
        if (permissions != null && permissions.length != 0) {
            if (VERSION.SDK_INT < 23) {
                LoggerFactory.getTraceLogger().info("Permissions", "requestPermission, version smaller");
                return;
            }
            String[] deniedPermissionArray = a((Context) activity, permissions);
            if (deniedPermissionArray == null) {
                LoggerFactory.getTraceLogger().info("Permissions", "requestPermission, deniedPermissionArray is empty");
            } else {
                b(activity, deniedPermissionArray, requestCode, hasOnRequestPermissionsResultMethod);
            }
        }
    }

    private static void b(Activity activity, String[] deniedPermissionArray, int requestCode, boolean hasOnRequestPermissionsResultMethod) {
        boolean isAllIn = d(deniedPermissionArray);
        LoggerFactory.getTraceLogger().info("Permissions", "requestPermissionInMainThread=" + isAllIn);
        if (isAllIn) {
            b(activity, deniedPermissionArray, requestCode);
            return;
        }
        final String firstPermission = deniedPermissionArray[0];
        LoggerFactory.getTraceLogger().info("Permissions", "requestPermissionInMainThread, firstPermission=" + firstPermission);
        final c guideData = new c();
        guideData.b = "T";
        guideData.c = d.a.get(firstPermission);
        b.put(b(deniedPermissionArray) + requestCode, guideData);
        if (m()) {
            LoggerFactory.getTraceLogger().info("Permissions", "isFirstGuideOpen=true");
            guideData.a = "T";
            final Activity activity2 = activity;
            final String[] strArr = deniedPermissionArray;
            final int i = requestCode;
            final boolean z = hasOnRequestPermissionsResultMethod;
            activity.runOnUiThread(new Runnable() {
                public final void run() {
                    b.b(activity2, strArr, firstPermission, i, guideData, z);
                }
            });
            return;
        }
        guideData.a = "F";
        b(activity, deniedPermissionArray, requestCode);
        b(activity, firstPermission, guideData, hasOnRequestPermissionsResultMethod);
    }

    /* access modifiers changed from: private */
    public static void b(final Activity activity, final String firstPermission, final c guideData, boolean hasOnRequestPermissionsResultMethod) {
        if (!hasOnRequestPermissionsResultMethod) {
            ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).acquireScheduledExecutor().schedule(new Runnable() {
                public final void run() {
                    b.b.remove(guideData);
                    if (b.a((Context) activity, firstPermission)) {
                        guideData.f = "1";
                    } else {
                        guideData.f = "0";
                    }
                    b.b(guideData);
                }
            }, 20000, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: private */
    public static void b(Activity activity, String[] permissions, String firstPermission, int requestCode, c guideData, boolean hasOnRequestPermissionsResultMethod) {
        a info;
        Map configMap = n();
        if (permissions.length == 1) {
            info = configMap.get(guideData.c);
        } else {
            info = configMap.get("multi");
        }
        if (info == null) {
            info = o();
        }
        LoggerFactory.getTraceLogger().info("Permissions", "showFirstGuildeDialog, start");
        guideData.d = info.b;
        guideData.e = info.c;
        final AUImageDialog dialog = AUImageDialog.getInstance(activity);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouch(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle(info.b);
        dialog.setSubTitle(info.c);
        Context context = LauncherApplicationAgent.getInstance().getBaseContext();
        ImageView imageView = dialog.getLogoImageView();
        if (TextUtils.isEmpty(info.d)) {
            imageView.setVisibility(0);
            imageView.setImageDrawable(LauncherApplicationAgent.getInstance().getBundleContext().getResourcesByBundle(BuildConfig.BUNDLE_NAME).getDrawable(C0096a.permission_guide));
        } else {
            imageView.setBackgroundColor(0);
            imageView.getLayoutParams().height = DensityUtil.dip2px(context, 130.0f);
            imageView.getLayoutParams().width = DensityUtil.dip2px(context, 162.0f);
            ((MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaImageService.class.getName())).loadOriginalImage(info.d, imageView, null, null, "antbasic_permissions");
        }
        dialog.setConfirmBtnText(info.e);
        final String[] strArr = permissions;
        final Activity activity2 = activity;
        final int i = requestCode;
        final String str = firstPermission;
        final c cVar = guideData;
        final boolean z = hasOnRequestPermissionsResultMethod;
        dialog.setOnConfirmBtnClick(new OnClickListener() {
            public final void onClick(View v) {
                b.a(strArr);
                b.b(activity2, strArr, i);
                dialog.dismissWithoutAnim();
                b.b(activity2, str, cVar, z);
            }
        });
        dialog.setCloseButtonVisibility(8);
        dialog.showWithoutAnim();
        LoggerFactory.getTraceLogger().info("Permissions", "showFirstGuildeDialog, end");
    }

    private static boolean m() {
        String value = f.a((String) "auth_config_dialog_open");
        LoggerFactory.getTraceLogger().info("Permissions", "isFirstGuideOpen, value=" + value);
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        try {
            if (Integer.parseInt(value) == 1) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "parse error");
            return false;
        }
    }

    private static Map<String, a> n() {
        String[] split;
        String value = f.a((String) "auth_config_dialogInfo");
        LoggerFactory.getTraceLogger().info("Permissions", "value=" + value);
        Map retMap = new HashMap();
        if (!TextUtils.isEmpty(value)) {
            try {
                for (String parentString : value.split(",")) {
                    if (!TextUtils.isEmpty(parentString)) {
                        String[] childString = parentString.split("\\|");
                        if (childString.length >= 5) {
                            a info = c(childString);
                            retMap.put(info.a, info);
                        }
                    }
                }
            } catch (Throwable th) {
                LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "parse error");
            }
        }
        return retMap;
    }

    private static a o() {
        return c(new String[]{"default", "高德本地服务", "允许地理位置权限，高德能更好的为您提供服务", "下一步", ""});
    }

    private static a c(String[] array) {
        a info = new a();
        info.a = a(array[0]);
        info.b = a(array[1]);
        info.c = a(array[2]);
        info.e = a(array[3]);
        info.d = a(array[4]);
        return info;
    }

    private static String a(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    public static void a(int requestCode, String[] permissions, int[] grantResults) {
        String key = b(permissions) + requestCode;
        c guideData = b.get(key);
        b.remove(key);
        if (guideData == null) {
            LoggerFactory.getTraceLogger().info("Permissions", "onRequestPermissionsResult, guideData error!");
            return;
        }
        guideData.f = String.valueOf(grantResults[0] + 1);
        b(guideData);
    }

    /* access modifiers changed from: private */
    public static void b(c guideData) {
        if (!"F".equals(guideData.b)) {
            Behavor behavor = new Behavor();
            behavor.setBehaviourPro("PermissionGuide");
            behavor.setSeedID("AuthApplyGuide");
            behavor.setParam1(guideData.a);
            behavor.setParam2(guideData.c);
            behavor.setParam3(guideData.f);
            behavor.addExtParam("isfirst", guideData.b);
            behavor.addExtParam("pg_title", guideData.d);
            behavor.addExtParam("pg_content", guideData.e);
            LoggerFactory.getBehavorLogger().event(null, behavor);
            StringBuilder message = new StringBuilder("PermissionGuide");
            message.append(", seedID: AuthApplyGuide");
            message.append(", isShowDialog: ").append(guideData.a);
            message.append(", permission: ").append(guideData.c);
            message.append(", user_behavior: ").append(guideData.f);
            message.append(", isfirst: ").append(guideData.b);
            message.append(", pg_title: ").append(guideData.d);
            message.append(", pg_content: ").append(guideData.e);
            LoggerFactory.getTraceLogger().info("Permissions", message.toString());
        }
    }

    /* access modifiers changed from: private */
    public static void b(Activity activity, String[] permissions, int requestCode) {
        if (VERSION.SDK_INT >= 23) {
            activity.requestPermissions(permissions, requestCode);
        }
    }

    private static String[] a(Context context, String[] permissions) {
        if (VERSION.SDK_INT >= 23) {
            List deniedPermissionList = new ArrayList();
            for (String permission : permissions) {
                if (!a(context, permission)) {
                    deniedPermissionList.add(permission);
                }
            }
            if (deniedPermissionList.size() > 0) {
                return (String[]) deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
            }
        }
        return null;
    }

    private static boolean d(String[] permissions) {
        for (String permission : permissions) {
            if (!TextUtils.isEmpty(permission) && !a.contains(permission)) {
                return false;
            }
        }
        return true;
    }
}
