package com.amap.bundle.blutils.platform;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.Arrays;
import java.util.Iterator;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ShortCutUtil {
    private static final char INVISIBLE_CHAR = '\u0000';
    private static final String TAG = "ShortcutUtil";

    public static void addMainShortCut(Context context, String str, int i, Class<?> cls) {
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra("duplicate", false);
        intent.putExtra("android.intent.extra.shortcut.NAME", str);
        intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(context, i));
        Intent intent2 = new Intent(context, cls);
        intent2.addCategory("android.intent.category.LAUNCHER");
        intent2.setAction("android.intent.action.MAIN");
        intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
        addShortcutCompat(context, intent);
    }

    public static void addToolboxShortcut(String str, Intent intent) {
        Application application = AMapAppGlobal.getApplication();
        Intent intent2 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent2.putExtra("duplicate", false);
        intent2.putExtra("android.intent.extra.shortcut.NAME", str);
        intent2.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(application, R.drawable.ic_save_shortcut));
        intent2.putExtra("android.intent.extra.shortcut.INTENT", intent);
        addShortcutCompat(application, intent2);
    }

    public static void deleteShortCut(String str, Activity activity) {
        if (VERSION.SDK_INT < 26) {
            Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
            intent.putExtra("android.intent.extra.shortcut.NAME", str);
            Intent intent2 = new Intent(activity, activity.getClass());
            intent2.addCategory("android.intent.category.LAUNCHER");
            intent2.setAction("android.intent.action.MAIN");
            intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
            activity.sendBroadcast(intent);
        }
    }

    public static boolean isSupportCompat(Context context) {
        if (VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService("shortcut")).isRequestPinShortcutSupported();
        }
        String str = Build.BRAND;
        return !"flyme".equalsIgnoreCase(str) && !"Meizu".equalsIgnoreCase(str) && !DeviceProperty.ALIAS_NUBIA.equalsIgnoreCase(str);
    }

    public static void addShortcutCompat(Context context, Intent intent) {
        addShortcutCompat(context, intent, false);
    }

    public static void addShortcutCompat(Context context, Intent intent, boolean z) {
        addShortcutCompat(context, intent, z, null);
    }

    public static void addShortcutCompat(Context context, Intent intent, boolean z, String str) {
        boolean z2;
        try {
            if (VERSION.SDK_INT >= 26) {
                ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService("shortcut");
                if (shortcutManager.isRequestPinShortcutSupported()) {
                    String stringExtra = intent.getStringExtra("android.intent.extra.shortcut.NAME");
                    if (TextUtils.isEmpty(str)) {
                        str = stringExtra;
                    }
                    ShortcutInfo build = new Builder(context, str).setIcon(buildShortcutIcon(context, intent)).setShortLabel(stringExtra).setLongLabel(stringExtra).setIntent((Intent) intent.getExtras().getParcelable("android.intent.extra.shortcut.INTENT")).build();
                    Iterator<ShortcutInfo> it = shortcutManager.getPinnedShortcuts().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().getId().equals(build.getId())) {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    Iterator<ShortcutInfo> it2 = shortcutManager.getDynamicShortcuts().iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (it2.next().getId().equals(build.getId())) {
                                z2 = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (!z2) {
                        shortcutManager.requestPinShortcut(build, null);
                        return;
                    } else if (z) {
                        shortcutManager.updateShortcuts(Arrays.asList(new ShortcutInfo[]{build}));
                    }
                }
                return;
            }
            context.sendBroadcast(intent);
        } catch (Exception unused) {
        }
    }

    private static int getIdFromShortcutIconResource(ShortcutIconResource shortcutIconResource, Context context) {
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(shortcutIconResource.packageName);
            if (resourcesForApplication != null) {
                return resourcesForApplication.getIdentifier(shortcutIconResource.resourceName, null, null);
            }
        } catch (Exception unused) {
        }
        return -1;
    }

    @TargetApi(23)
    private static Icon buildShortcutIcon(Context context, Intent intent) {
        if (intent.hasExtra("android.intent.extra.shortcut.ICON")) {
            return Icon.createWithBitmap((Bitmap) intent.getExtras().getParcelable("android.intent.extra.shortcut.ICON"));
        }
        return Icon.createWithResource(context, getIdFromShortcutIconResource((ShortcutIconResource) intent.getExtras().getParcelable("android.intent.extra.shortcut.ICON_RESOURCE"), context));
    }

    @Deprecated
    public static boolean hasShortCut(Context context, String str) {
        return hasShortCutCompat(context, str);
    }

    public static boolean hasShortCutCompat(Context context, String str) {
        if (VERSION.SDK_INT >= 26) {
            ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService("shortcut");
            for (ShortcutInfo id : shortcutManager.getPinnedShortcuts()) {
                if (id.getId().equals(str)) {
                    return true;
                }
            }
            for (ShortcutInfo id2 : shortcutManager.getDynamicShortcuts()) {
                if (id2.getId().equals(str)) {
                    return true;
                }
            }
        }
        return queryShortCut(context, str, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0087 A[SYNTHETIC, Splitter:B:31:0x0087] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean queryShortCut(android.content.Context r10, java.lang.String r11, java.util.concurrent.atomic.AtomicReference<java.lang.Throwable> r12) {
        /*
            java.lang.String r0 = defpackage.ahq.b(r10)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0029
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = defpackage.ahq.a(r10)
            r0.append(r1)
            java.lang.String r1 = ".permission.READ_SETTINGS"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String[] r1 = new java.lang.String[r2]
            r1[r3] = r0
            java.lang.String r0 = defpackage.ahq.a(r10, r1)
        L_0x0029:
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0041
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 8
            if (r0 >= r1) goto L_0x0038
            java.lang.String r0 = "com.android.launcher.settings"
            goto L_0x0041
        L_0x0038:
            r1 = 19
            if (r0 >= r1) goto L_0x003f
            java.lang.String r0 = "com.android.launcher2.settings"
            goto L_0x0041
        L_0x003f:
            java.lang.String r0 = "com.android.launcher3.settings"
        L_0x0041:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "content://"
            r1.<init>(r4)
            r1.append(r0)
            java.lang.String r0 = "/favorites?notify=true"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.net.Uri r5 = android.net.Uri.parse(r0)
            r0 = 0
            android.content.ContentResolver r4 = r10.getContentResolver()     // Catch:{ Exception -> 0x0084 }
            r6 = 0
            java.lang.String r7 = "title=?"
            java.lang.String[] r8 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0084 }
            r8[r3] = r11     // Catch:{ Exception -> 0x0084 }
            r9 = 0
            android.database.Cursor r10 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x0084 }
            if (r10 == 0) goto L_0x0077
            int r11 = r10.getCount()     // Catch:{ Exception -> 0x0074, all -> 0x0071 }
            goto L_0x0078
        L_0x0071:
            r11 = move-exception
            r0 = r10
            goto L_0x008b
        L_0x0074:
            r11 = move-exception
            r0 = r10
            goto L_0x0085
        L_0x0077:
            r11 = 0
        L_0x0078:
            if (r11 <= 0) goto L_0x007b
            goto L_0x007c
        L_0x007b:
            r2 = 0
        L_0x007c:
            if (r10 == 0) goto L_0x0081
            r10.close()
        L_0x0081:
            return r2
        L_0x0082:
            r11 = move-exception
            goto L_0x008b
        L_0x0084:
            r11 = move-exception
        L_0x0085:
            if (r12 == 0) goto L_0x0091
            r12.set(r11)     // Catch:{ all -> 0x0082 }
            goto L_0x0091
        L_0x008b:
            if (r0 == 0) goto L_0x0090
            r0.close()
        L_0x0090:
            throw r11
        L_0x0091:
            if (r0 == 0) goto L_0x0096
            r0.close()
        L_0x0096:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.blutils.platform.ShortCutUtil.queryShortCut(android.content.Context, java.lang.String, java.util.concurrent.atomic.AtomicReference):boolean");
    }
}
